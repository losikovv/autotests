package ru.instamart.test.api.on_demand.candidates;


import candidates.CandidatesGrpc;
import candidates.CandidatesOuterClass;
import candidates.CandidatesOuterClass.Candidate.EmploymentType;
import candidates.CandidatesOuterClass.CandidateLastLocation;
import candidates.CandidatesOuterClass.CandidateTransport;
import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.candidates.CandidatesDao;
import ru.instamart.jdbc.dao.norns.LocationsDao;
import ru.instamart.jdbc.dao.stf.SpreeShipmentsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import workflow.ServiceGrpc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
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
    private OrderV2 order;
    private String shipmentUuid;
    private Timestamp createdAt;
    private ServiceGrpc.ServiceBlockingStub clientWorkflow;
    private String placeUuid = "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a";

    @BeforeClass(alwaysRun = true)
    public void auth() {
        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));

        user = UserManager.getShp6Universal1();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_9);

        user2 = UserManager.getShp6Universal2();
        shopperApp.authorisation(user2);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_9);

        // TODO выяснить падение создания маршрутя
        SessionFactory.makeSession(SessionType.API_V2);
        UserData userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        order = apiV2.orderOnDemand(userData, EnvironmentProperties.DEFAULT_SID);
        ThreadUtil.simplyAwait(10);
        shipmentUuid = SpreeShipmentsDao.INSTANCE.getShipmentByNumber(order.getShipments().get(0).getNumber()).getUuid();
//        String firstWorkflowUuid = getWorkflowUuid(order, shipmentUuid, getDateMinusSec(5), clientWorkflow);
//        AssignmentsEntity firstAssignmentsEntity = AssignmentsDao.INSTANCE.getAssignmentByWorkflowUuid(firstWorkflowUuid);
//        acceptWorkflowAndStart(firstAssignmentsEntity.getId().toString(), StartPointsTenants.METRO_9);

        user3 = UserManager.getShp6Universal3();
        shopperApp.authorisation(user3);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_3);

        user4 = UserManager.getShp6Universal4();
        shopperApp.authorisation(user4);
        shopperApp.createShopperShift(1, String.valueOf(LocalDateTime.now()), String.valueOf(LocalDateTime.now().plus(1, ChronoUnit.DAYS)), 55.91661, 37.54007);

        clientCandidates = CandidatesGrpc.newBlockingStub(grpc.createChannel(PAAS_CONTENT_OPERATIONS_CANDIDATES));
        ThreadUtil.simplyAwait(10);
        createdAt = LocationsDao.INSTANCE.getLastByUUID(user.getUuid()).getCreatedAt();

    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseId(24)
    @Test(enabled = false, description = "Кандидаты находятся в пределах радиуса вокруг координаты магазина", groups = "dispatch-candidates-smoke")
    public void candidatesAreWithinARadius() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.7012983) //55.7012983, 37.7283667
                                .setLon(37.7283667)
                                .build())
                        .setRadius(20000.00F))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка существования кандидатов.", () ->
                assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым"));
    }

    @CaseId(24)
    @Test(description = "Превышение даты/времени последней фиксации геолокации при фильтрации",
            groups = "dispatch-candidates-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: geo_started_at should be for the last day")
    public void exceedingTheDateTimeNegativeTest() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .setCreatedAt(getTimestampFromString("2021-11-22T00:04:05.999+03:00"))
                                .build()))
                .build();
        clientCandidates.selectCandidates(requestBody);
    }

    @CaseId(25)
    @Test(description = "Кандидаты находятся вне  радиуса вокруг координаты магазина", groups = "dispatch-candidates-smoke")
    public void candidatesAreOutOfRadius() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.775353) //координаты рядом с магазином магазина ,55.700905, 37.735048  55.723251, 37.718116
                                .setLon(37.592168)
                                .build())
                        .setRadius(2000.00F))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Кандидаты вернулись", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0, "UUID кандидата вернулся"));
    }

    @CaseId(26)
    @Test(description = "Устаревшая информация о геопозиции кандидатов", groups = "dispatch-candidates-smoke")
    public void candidatesAreOutOfRadiusShifts() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(58.683364) //58.683364, 37.927337
                                .setLon(37.927337)
                                .build())
                        .setRadius(200.00F))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка ответа. Ожидается что ответ придет пустым", () ->
                assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0,
                        "UUID кандидатов пришел не пустым"));
    }

    @CaseId(45)
    @Test(description = "Отбор кандидатов по дате/времени последней фиксации геопозиции", groups = "dispatch-candidates-smoke")
    public void candidatesAreWithinADateTime() {
        final var timestamp = toTimestamp(createdAt).getSeconds();
        final var currentTimestamp = getTimestamp().getSeconds();
        final var startDay = (currentTimestamp / 86400) * 86400;
        if (startDay > timestamp) throw new SkipException("Геопозиция зарегистрирована днем ранее");

        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .setCreatedAt(toTimestamp(createdAt))
                                .build()))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () ->
                assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым"));

    }


    @CaseId(30)
    @Test(description = "Отбор кандидатов по транспорту", groups = "dispatch-candidates-smoke")
    public void transportCandidate() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.7012983) //55.7012983, 37.7283667
                                .setLon(37.7283667)
                                .build())
                        .addTransports(CandidateTransport.BICYCLE) //2 = велосипед
                ).build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @CaseId(28)
    @Test(description = "Отбор кандидатов по роли", groups = "dispatch-candidates-smoke")
    public void roleCandidate() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addRoles(CandidatesOuterClass.CandidateRole.UNIVERSAL)
                ).build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @CaseId(29)
    @Test(description = "Отсутствие необходимой роли у кандидата", groups = "dispatch-candidates-smoke")
    public void withoutRoleCandidate() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addRoles(CandidatesOuterClass.CandidateRole.DRIVER))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов пустой", () ->
                assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0,
                        "UUID кандидата вернулся"));
    }

    @CaseIDs(
            {@CaseId(31), @CaseId(79)}
    )
    @Test(description = "Отсутствие необходимого транспорта у кандидата", groups = "dispatch-candidates-smoke")
    public void lackOfTransportCandidates() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addTransports(CandidateTransport.CAR))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        final var candidatesOutreCarList = selectCandidatesResponse.getResults(0).getCandidateList().stream()
                .filter(item -> !item.getTransport().equals(CandidateTransport.CAR))
                .collect(Collectors.toList());
        Allure.step("Проверка кандидатов. Список кандидатов пустой", () ->
                assertTrue(candidatesOutreCarList.size() == 0,
                        "UUID кандидата вернулся"));
    }

    @CaseId(32)
    @Test(description = "Отбор сборщиков в активной смене", groups = "dispatch-candidates-smoke")
    public void selectionShoppers() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setPlaceUuid("599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a"))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @Skip
    @CaseId(38)
    @Test(description = "Отбор кандидатов по нескольким фильтрам", groups = "dispatch-candidates-smoke")
    public void selectionByManyFilters() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .setRadius(200.00F)
                        .addRoles(CandidatesOuterClass.CandidateRole.UNIVERSAL))
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.857291)
                                .setLon(38.440348)
                                .build())
                        .setRadius(200.00F).addTransports(CandidateTransport.BICYCLE)).build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой и минимум два универсала", () -> {
            assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
            assertTrue(selectCandidatesResponse.getResults(1).getCandidateCount() > 0, "UUID кандидата вернулся пустым");
        });
    }

    @CaseIDs({@CaseId(44), @CaseId(92)})
    @Test(enabled = false,
            description = "Проверка полей 'role' и 'transport' в ответе (смена в сервисе смен)",
            groups = "dispatch-candidates-smoke")
    public void lackOfTransportCandidatesInRadius() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.7012983) //55.7012983, 37.7283667
                                .setLon(37.7283667)
                                .build())
                        .setRadius(5000.00f))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов пустой", () ->
                assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                        "UUID кандидата пустой"));
    }


    @CaseId(120)
    @Test(description = "Отбор кандидатов по очереди", groups = "dispatch-candidates-smoke")
    public void selectionByQueueSize() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.7012983) //55.7012983, 37.7283667
                                .setLon(37.7283667)
                                .build())
                        .setMaxQueueSize(1))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @CaseIDs({@CaseId(81), @CaseId(83)})
    @Test(description = "Отбор кандидатов по всем параметрам в фильтре", groups = "dispatch-candidates-smoke")
    public void selectionByFilters() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.7012983) //55.7012983, 37.7283667
                                .setLon(37.7283667)
                                .build())
                        .setPlaceUuid("599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a"))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody).getResults(0);
        var resultsList = selectCandidatesResponse.getCandidateList();

        var kraken4 = resultsList.stream()
                .filter(item -> item.getUuid().equals("d63b66b2-441e-4c41-85d2-64ff33daadf0"))
                .collect(Collectors.toList());

        Allure.step("Проверяем response на существование kraken4", () ->
                assertTrue(kraken4.size() > 0, "Kraken4 не вернулся в фильтре"));
        Allure.step("Проверка данных kraken4 ", () -> {
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(kraken4.get(0).getFullName(), "Kraken Test4", softAssert);
            compareTwoObjects(kraken4.get(0).getTransport(), CandidateTransport.CAR, softAssert);
            compareTwoObjects(kraken4.get(0).getEmploymentType(), EmploymentType.EXTERNAL_EMPLOYEE, softAssert);
            softAssert.assertAll();
        });
    }

    @CaseIDs({@CaseId(86), @CaseId(85)})
    @Test(enabled = false,
            description = "Отбор кандидатов по всем параметрам в фильтре", groups = "dispatch-candidates-smoke")
    public void selectionByAllFilters() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .setCreatedAt(toTimestamp(createdAt))
                                .build())
                        .setRadius(200.00F)
                        .setMaxQueueSize(1)
                        .addRoles(CandidatesOuterClass.CandidateRole.UNIVERSAL)
                        .addRoles(CandidatesOuterClass.CandidateRole.SHOPPER)
                        .addTransports(CandidateTransport.BICYCLE)
                        .addTransports(CandidateTransport.PEDESTRIAN)
                        .addTransports(CandidateTransport.CAR)
                        .setPlaceUuid("599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a"))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @CaseId(101)
    @Test(description = "Отбор сборщиков в активной смене", groups = "dispatch-candidates-smoke")
    public void blockingShoppers() {
        List<String> candidates = Arrays.asList(user.getUuid(), user2.getUuid(), user3.getUuid(), user4.getUuid());
        var requestBody = CandidatesOuterClass.SetCandidateStateRequest.newBuilder()
                .addAllCandidatesUuids(candidates)
                .setState(CandidatesOuterClass.CandidateState.BLOCKED)
                .build();
        var setCandidatesState = clientCandidates.setCandidatesState(requestBody);
        Allure.step("", () -> assertTrue(setCandidatesState.getResultsCount() > 0, "Информация с блокировками не вернулась"));
        final var candidatesDb = CandidatesDao.INSTANCE.getCandidateInUuid(candidates);
        //TODO актуализировать проверки
    }

    @CaseId(107)
    @Test(groups = {"dispatch-candidates-smoke"},
            description = "Отправить запрос на изменение статуса нескольких кандидатов в разных статусах")
    public void changeStatusSeveralCandidates() {
        List<String> candidates = Arrays.asList(user.getUuid(), user2.getUuid(), user3.getUuid(), user4.getUuid());
        final var request = CandidatesOuterClass.SetCandidateStateRequest.newBuilder()
                .addAllCandidatesUuids(candidates)
                .setState(CandidatesOuterClass.CandidateState.BLOCKED)
                .build();
        final var setCandidatesState = clientCandidates.setCandidatesState(request);
        Allure.step("", () -> assertTrue(setCandidatesState.getResultsCount() > 0, "Информация с блокировками не вернулась"));
        final var candidatesDb = CandidatesDao.INSTANCE.getCandidateInUuid(candidates);
        //TODO актуализировать проверки
    }


    @CaseIDs({@CaseId(110), @CaseId(111), @CaseId(112), @CaseId(115), @CaseId(116)})
    @Test(groups = {"dispatch-candidates-smoke"},
            description = "Checking the Shift ID in the Response")
    public void checkingShiftInResponse() {
        final var request = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidateLastLocation.newBuilder()
                                .setLat(StartPointsTenants.METRO_9.getLat())
                                .setLon(StartPointsTenants.METRO_9.getLat())
                        )
                        .setPlaceUuid(placeUuid)
                        .build())
                .build();
        final var selectCandidates = clientCandidates.selectCandidates(request);
        Allure.step("Проверка ответа selectCandidates", () -> {
            assertTrue(selectCandidates.getResultsCount() > 0, "Информация с блокировками не вернулась");
            final var softAssert = new SoftAssert();
            softAssert.assertNotNull(selectCandidates.getResults(0).getCandidate(0).getUuid(), "uuid кандидата не совпадает");
            softAssert.assertNotNull(selectCandidates.getResults(0).getCandidate(0).getTransport(), "Transport кандидата не null");
            softAssert.assertNotNull(selectCandidates.getResults(0).getCandidate(0).getEmploymentType(), "EmploymentType кандидата не null");
            softAssert.assertAll();
        });
    }

    @CaseId(129)
    @Test(groups = {"dispatch-candidates-smoke"},
            description = "Checking the Shift ID in the Response")
    public void selectionCandidatesWithActiveML() {
        final var request = CandidatesOuterClass.HaveActiveWorkflowRequest.newBuilder()
                .addCandidatesUuids(user4.getUuid())
                .build();
        final var activeWFS = clientCandidates.haveActiveWorkflow(request);
        Allure.step("", () -> {
            assertTrue(activeWFS.getResultsCount() > 0, "");
            assertFalse(activeWFS.getResultsMap().get(user4.getUuid()), "uuid кандидата не совпадает");
        });
    }
}

