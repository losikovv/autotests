package ru.instamart.test.api.on_demand.candidates;

import candidates.CandidatesGrpc;
import candidates.CandidatesOuterClass;
import io.qameta.allure.Allure;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.ThreadUtil;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertTrue;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_CANDIDATES;

public class CandidatesShopperTest extends RestBase {

    private CandidatesGrpc.CandidatesBlockingStub clientCandidates;
    private UserData user;
    private String placeUuid = "599ba7b7-0d2f-4e54-8b8e-ca5ed7c6ff8a";

    @BeforeClass(alwaysRun = true)
    public void auth() {
//        clientWorkflow = ServiceGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_WORKFLOW));
        user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
        shiftsApi.startOfShift(StartPointsTenants.METRO_9);

        user = UserManager.getShp6Universal4();
        shopperApp.authorisation(user);
        //Удаляем все смены
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();

        shiftsApi.startOfShift(StartPointsTenants.METRO_9);

        clientCandidates = CandidatesGrpc.newBlockingStub(grpc.createChannel(PAAS_CONTENT_OPERATIONS_CANDIDATES));
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        shiftsApi.cancelAllActiveShifts();
        shiftsApi.stopAllActiveShifts();
    }

    @CaseId(34)
    @Test(description = "Отсутствие необходимого транспорта у кандидата",
            groups = "dispatch-candidates-smoke")
    public void lackOfTransportCandidatesShopper() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915755) //55.915755, 37.541997
                                .setLon(37.541997)
                                .build())
                        .setPlaceUuid(placeUuid)
                        .addRoles(CandidatesOuterClass.CandidateRole.SHOPPER)
                        .build()
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);

        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @CaseId(39)
    @Test(description = "Отсутствие необходимого транспорта у кандидата",
            groups = "dispatch-candidates-smoke")
    public void lackOfTransportCandidatesNonShopper() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915755) //55.915755, 37.541997
                                .setLon(37.541997)
                                .build())
                        .setPlaceUuid(placeUuid)
                        .addRoles(CandidatesOuterClass.CandidateRole.DRIVER)
                        .build()
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0,
                "UUID кандидата вернулся не пустым"));
    }


    @CaseId(79)
    @Test(description = "Отсутствие необходимого транспорта у кандидата", groups = "dispatch-candidates-smoke")
    public void lackOfNecessaryTransportCandidates() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.915098)
                                .setLon(37.541685)
                                .build())
                        .addTransports(CandidatesOuterClass.CandidateTransport.TRUCK))
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов пустой", () ->
                assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() <= 0,
                        "UUID кандидата вернулся"));
    }
}
