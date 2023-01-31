package ru.instamart.test.api.on_demand.candidates;

import candidates.CandidatesGrpc;
import candidates.CandidatesOuterClass;
import candidates.CandidatesOuterClass.CandidateRole;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static org.testng.Assert.assertTrue;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_CANDIDATES;

@Epic("On Demand")
@Feature("Candidates")
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

    @TmsLink("34")
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
                        .addRoles(CandidateRole.SHOPPER)
                        .build()
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);

        Allure.step("Проверка кандидатов. Список кандидатов не пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся пустым"));
    }

    @TmsLink("39")
    @Test(description = "Отсутствие необходимого транспорта у кандидата",
            groups = "dispatch-candidates-smoke")
    public void lackOfTransportCandidatesNonShopper() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(56.825221) //56.825221, 60.499397
                                .setLon(60.499397)
                                .build())
                        .setPlaceUuid("8be2be32-f4bb-4434-8c72-572736a966ee")
                        .addRoles(CandidateRole.UNIVERSAL)
                        .build()
                )
                .build();
        var selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        Allure.step("Проверка кандидатов. Список кандидатов пустой", () -> assertTrue(selectCandidatesResponse.getResults(0).getCandidateCount() > 0,
                "UUID кандидата вернулся не пустым"));
    }


    @TmsLink("79")
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
