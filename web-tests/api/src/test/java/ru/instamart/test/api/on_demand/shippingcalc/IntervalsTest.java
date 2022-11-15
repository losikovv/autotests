package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.GetIntervalsSurgeRequest;
import shippingcalc.SetIntervalsSurgeRequest;
import shippingcalc.ShippingcalcGrpc;
import shippingcalc.SurgeInterval;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

@Epic("On Demand")
@Feature("ShippingCalc")
public class IntervalsTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private SurgeInterval firstInterval;
    private SurgeInterval secondInterval;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));

        firstInterval = SurgeInterval.newBuilder()
                .setLeftBoundary(0)
                .setRightBoundary(0.5f)
                .setPriceAddition(100)
                .setPercentAddition(5)
                .setMinCartAddition(100)
                .build();
        secondInterval = SurgeInterval.newBuilder()
                .setLeftBoundary(0.5f)
                .setRightBoundary(1)
                .setPriceAddition(200)
                .setPercentAddition(10)
                .setMinCartAddition(200)
                .build();
    }

    @CaseIDs(value = {@CaseId(284), @CaseId(282)})
    @Story("Set Intervals Surge")
    @Test(description = "Перезапись всех интервалов",
            groups = "dispatch-shippingcalc-smoke")
    public void setIntervalsSurge() {
        var request = SetIntervalsSurgeRequest.newBuilder()
                .addIntervals(firstInterval)
                .addIntervals(secondInterval)
                .build();
        var response = clientShippingCalc.setIntervalsSurge(request);

        Allure.step("Проверка успешного выполнения запроса", () -> {
            compareTwoObjects(response.toString(), "");
        });
    }

    @CaseId(285)
    @Story("Set Intervals Surge")
    @Test(description = "Получение ошибки при отрицательных значениях в правой и левой границах",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: Left boundary in 0 interval must not be less zero")
    public void setIntervalsSurgeNegativeBorders() {
        var request = SetIntervalsSurgeRequest.newBuilder()
                .addIntervals(SurgeInterval.newBuilder()
                        .setLeftBoundary(-1)
                        .setRightBoundary(1)
                        .setPriceAddition(100)
                        .setPercentAddition(5)
                        .setMinCartAddition(100)
                        .build())
                .build();
        clientShippingCalc.setIntervalsSurge(request);
    }

    @CaseId(279)
    @Story("Set Intervals Surge")
    @Test(description = "Получение списка интервалов surge",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "setIntervalsSurge")
    public void getIntervalsSurge() {
        var request = GetIntervalsSurgeRequest.newBuilder().build();
        var response = clientShippingCalc.getIntervalsSurge(request);

        Allure.step("Проверка успешного получения списка интервалов", () -> {
            compareTwoObjects(response.getIntervalsCount(), 2);
            compareTwoObjects(response.getIntervals(0), firstInterval);
            compareTwoObjects(response.getIntervals(1), secondInterval);
        });
    }
}
