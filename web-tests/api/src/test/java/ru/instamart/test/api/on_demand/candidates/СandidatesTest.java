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
import ru.instamart.api.response.shifts.ShiftResponse;
import ru.instamart.kraken.data.StartPointsTenants;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.grpc.common.GrpcContentHosts.PAAS_CONTENT_OPERATIONS_CANDIDATES;
import static ru.instamart.kraken.util.TimeUtil.getTimestampFromString;

@Epic("On Demand")
@Feature("Candidates")
public class СandidatesTest extends RestBase {

    private CandidatesGrpc.CandidatesBlockingStub clientCandidates;
    private UserData user;

    @BeforeClass(alwaysRun = true)
    public void auth() {
        user = UserManager.getShp6Shopper1();
        shopperApp.authorisation(user);
        shiftsApi.startOfShift(StartPointsTenants.METRO_3);
        clientCandidates = CandidatesGrpc.newBlockingStub(grpc.createChannel(PAAS_CONTENT_OPERATIONS_CANDIDATES));
    }

    @AfterClass(alwaysRun = true)
    public void after() {
        List<ShiftResponse> shifts = shiftsApi.shifts();
        shifts.stream()
                .forEach(item -> shiftsApi.cancelShifts(item.getId()));
    }

    @CaseId(24)
    @Test(description = "Кандидаты находятся в пределах радиуса вокруг координаты магазина",
            groups = "dispatch-сandidates-smoke")
    public void candidatesAreWithinARadius() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.7012984)
                                .setLon(37.7283669)
                                .build())
                        .setRadius(100.00F)
                )
                .build();
        CandidatesOuterClass.SelectCandidatesResponse selectCandidatesResponse = clientCandidates.selectCandidates(requestBody);
        assertEquals(selectCandidatesResponse.getResults(0)
                .getCandidate(0)
                .getUuid(), user.getUuid(), "UUID кандидата не совпадает");
        compareTwoObjects(selectCandidatesResponse.getResults(0).getCandidate(0).getLastLocation().getLat(), StartPointsTenants.METRO_3.getLat());
        compareTwoObjects(selectCandidatesResponse.getResults(0).getCandidate(0).getLastLocation().getLon(), StartPointsTenants.METRO_3.getLon());
    }

    @CaseId(24)
    @Test(description = "Превышение даты/времени последней фиксации геолокации при фильтрации",
            groups = "dispatch-сandidates-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: geo_started_at should be for the last day"
    )
    public void exceedingTheDateTimeNegativeTest() {
        var requestBody = CandidatesOuterClass.SelectCandidatesRequest.newBuilder()
                .addFilter(CandidatesOuterClass.SelectCandidatesFilter.newBuilder()
                        .setTargetPoint(CandidatesOuterClass.CandidateLastLocation.newBuilder()
                                .setLat(55.7012984)
                                .setLon(37.7283669)
                                .setCreatedAt(getTimestampFromString("2021-11-22T04:04:05.999+03:00"))
                                .build())
                )
                .build();
        clientCandidates.selectCandidates(requestBody);
    }
}
