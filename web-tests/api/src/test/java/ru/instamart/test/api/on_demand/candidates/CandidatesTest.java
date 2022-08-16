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
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_CANDIDATES;
import static ru.instamart.kraken.util.TimeUtil.getPastDateTime;
import static ru.instamart.kraken.util.TimeUtil.getTimestampFromString;

@Epic("On Demand")
@Feature("Candidates")
public class CandidatesTest extends RestBase {

    private CandidatesGrpc.CandidatesBlockingStub clientCandidates;
    private UserData user;
    private UserData user2;
    private UserData user3;
    private String timeStamp = getPastDateTime(900L);

    @BeforeClass(alwaysRun = true)
    public void auth() {

       user = UserManager.getShp6Shopper1();
       shopperApp.authorisation(user);
       shiftsApi.startOfShift(StartPointsTenants.METRO_9);
       shopperApp.sendCurrentLocator(55.915098,37.541685, null);

       user2 = UserManager.getShp6Shopper2();
       shopperApp.authorisation(user2);
       shiftsApi.startOfShift(StartPointsTenants.METRO_9);
       shopperApp.sendCurrentLocator(55.915098,37.541685, null);

       user3 = UserManager.getShp6Shopper3();
        shopperApp.authorisation(user3);
        shiftsApi.startOfShift(StartPointsTenants.METRO_3);
        shopperApp.sendCurrentLocator(55.857291,38.440348, null);

       clientCandidates = CandidatesGrpc.newBlockingStub(grpc.createChannel(PAAS_CONTENT_OPERATIONS_CANDIDATES));

    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    /*@AfterClass(alwaysRun = true)
    public void after() {
        List<ShiftResponse> shifts = shiftsApi.shifts();
        shifts.stream()
                .forEach(item -> shiftsApi.cancelShifts(item.getId()));
    }*/

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
        assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount()  <= 0, "UUID кандидата вернулся");
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
}

