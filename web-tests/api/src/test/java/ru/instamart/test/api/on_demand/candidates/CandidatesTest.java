package ru.instamart.test.api.on_demand.candidates;


import candidates.CandidatesGrpc;
import candidates.CandidatesOuterClass;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.jdbc.dao.workflow.AssignmentsDao;
import ru.instamart.jdbc.entity.workflow.AssignmentsEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;
import workflow.ServiceGrpc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.helper.WorkflowHelper.acceptWorkflowAndStart;
import static ru.instamart.api.helper.WorkflowHelper.getWorkflowUuid;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_CANDIDATES;
import static ru.instamart.kraken.util.TimeUtil.*;

@Epic("On Demand")
@Feature("Candidates")
public class CandidatesTest extends RestBase {

    private CandidatesGrpc.CandidatesBlockingStub clientCandidates;
    private UserData user;
    private UserData user2;
    private UserData user3;
    private UserData user4;
    private String timeStamp = getPastDateTime(900L);
    private OrderV2 order;
    private String shipmentUuid;
    private ServiceGrpc.ServiceBlockingStub clientWorkflow;

    @BeforeClass(alwaysRun = true)
    public void auth() {
        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));

        user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_9);
        shopperApp.sendCurrentLocator(55.915098, 37.541685, null);

        user2 = UserManager.getShp6Shopper2();
        shopperApp.authorisation(user2);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_9);
        shopperApp.sendCurrentLocator(55.915098, 37.541685, null);

       SessionFactory.makeSession(SessionType.API_V2);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        order = apiV2.order(userData, EnvironmentProperties.DEFAULT_SID);
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
        String firstWorkflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(5), clientWorkflow);
        AssignmentsEntity firstAssignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(firstWorkflowUuid);
        acceptWorkflowAndStart(firstAssignmentsEntity.getId().toString(), StartPointsTenants.METRO_9);


        user3 = UserManager.getShp6Shopper3();
        shopperApp.authorisation(user3);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_3);
        shopperApp.sendCurrentLocator(55.857291, 38.440348, null);

        user4 = UserManager.getShp6Shopper4();
        shopperApp.authorisation(user4);
        shopperApp.createShopperShift(
                1,
                String.valueOf(LocalDateTime.now()),
                String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.DAYS)),
                55.91661, 37.54007);


        clientCandidates = CandidatesGrpc.newBlockingStub(grpc.createChannel(PAAS_CONTENT_OPERATIONS_CANDIDATES));


    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseId(24)
    @Test(description = "Кандидаты находятся в пределах радиуса вокруг координаты магазина",
            groups = "dispatch-candidates-smoke")
    public void candidatesAreWithinARadius() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .setRadius(200.00F)
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
    }

    @CaseId(25)
    @Test(description = "Кандидаты находятся вне  радиуса вокруг координаты магазина",
            groups = "dispatch-candidates-smoke")
    public void candidatesAreOutOfRadius() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.775353) //координаты рядом с магазином магазина ,55.700905, 37.735048  55.723251, 37.718116
                                .setLon(37.592168)
                                .build())
                        .setRadius(200.00F)
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0, "UUID кандидата вернулся");
    }

    @CaseId(24)
    @Test(description = "Превышение даты/времени последней фиксации геолокации при фильтрации",
            groups = "dispatch-candidates-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: geo_started_at should be for the last day"
    )
    public void exceedingTheDateTimeNegativeTest() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .setCreatedAt(getTimestampFromString("2021-11-22T00:04:05.999+03:00"))
                                .build())
                )
                .build();
        clientCandidates.selectCandidates(requestBody);
    }

    @CaseId(45)
    @Test(description = "Отбор кандидатов по дате/времени последней фиксации геопозиции",
            groups = "dispatch-candidates-smoke")
    public void candidatesAreWithinADateTime() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .setCreatedAt(getTimestampFromString(timeStamp))
                                .build())
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");

    }


    @CaseId(30)
    @Test(description = "Отбор кандидатов по транспорту", groups = "dispatch-candidates-smoke")
    public void transportCandidate() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addTransports(CandidatesOuterClass.CandidateTransport.BICYCLE) //2 = велосипед
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
    }

    @CaseId(28)
    @Test(description = "Отбор кандидатов по роли", groups = "dispatch-candidates-smoke")
    public void roleCandidate() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addRoles(CandidatesOuterClass.CandidateRole.UNIVERSAL) //2 = универсал
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
    }

    @CaseId(31)
    @Test(description = "Отсутствие необходимого транспорта у кандидата",
            groups = "dispatch-candidates-smoke")
    public void LackOfTransportCandidates() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addTransports(CandidatesOuterClass.CandidateTransport.PEDESTRIAN)
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0, "UUID кандидата вернулся");

    }

    @CaseId(29)
    @Test(description = "Отсутствие необходимой роли у кандидата", groups = "dispatch-candidates-smoke")
    public void withoutRoleCandidate() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addRoles(CandidatesOuterClass.CandidateRole.DRIVER)
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0, "UUID кандидата вернулся");
    }

    @CaseId(38)
    @Test(description = "Отбор кандидатов по нескольким фильтрам", groups = "dispatch-candidates-smoke")
    public void SelectionByManyFilters() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .setRadius(200.00F)
                        .addRoles(CandidatesOuterClass.CandidateRole.UNIVERSAL)
                )
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.857291)
                                .setLon(38.440348)
                                .build())
                        .setRadius(200.00F)
                        .addTransports(CandidatesOuterClass.CandidateTransport.BICYCLE)
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
        assertTrue(selectCandidatesResponse.getResults(1).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
    }

    @CaseId(120)
    @Test(description = "Отбор кандидатов по очереди", groups = "dispatch-candidates-smoke")
    public void SelectionByQueueSize() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .setMaxQueueSize(1)
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
    }

    @CaseId(86)
    @Test(description = "Отбор кандидатов по всем параметрам в фильтре", groups = "dispatch-candidates-smoke")
    public void SelectionByAllFilters() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .setCreatedAt(getTimestampFromString(timeStamp))
                                .build())
                        .setRadius(200.00F)
                        .setMaxQueueSize(1)
                        .addRoles(CandidatesOuterClass.CandidateRole.UNIVERSAL)
                        .addRoles(CandidatesOuterClass.CandidateRole.SHOPPER)
                        .addTransports(CandidatesOuterClass.CandidateTransport.BICYCLE)
                        .addTransports(CandidatesOuterClass.CandidateTransport.PEDESTRIAN)
                        .addTransports(CandidatesOuterClass.CandidateTransport.CAR)
                        .setPlaceUuid("599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a")
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
    }
     @CaseId(32)
    @Test(description = "Отбор сборщиков в активной смене", groups = "dispatch-candidates-smoke")

    public void SelectionShoppers() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                                .setPlaceUuid("599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a")
                )
                .build();
         var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
         assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");

    }
}

