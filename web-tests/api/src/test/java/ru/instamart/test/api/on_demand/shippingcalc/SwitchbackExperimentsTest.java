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
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.GetSwitchbackExperimentsRequest;
import shippingcalc.SetSwitchbackExperimentsRequest;
import shippingcalc.ShippingcalcGrpc;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.kraken.util.TimeUtil.getDbDate;

@Epic("ShippingCalc")
@Feature("SwitchbackExperiments")
public class SwitchbackExperimentsTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private final String SWITCHBACK_INTERVAL = "start_date_time,end_date_time,region_id,group,parameters\n" +
            "%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n";
    private final String MULTIPLE_SWITCHBACK_INTERVAL = "start_date_time,end_date_time,region_id,group,parameters\n" +
            "%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n" +
            "%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n" +
            "%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    @CaseId(293)
    @Story("Set Switchback Experiments")
    @Test(description = "Запись switchback-интервала",
            groups = "dispatch-shippingcalc-smoke")
    public void setSwitchbackExperiment() {
        var request = SetSwitchbackExperimentsRequest.newBuilder()
                .setData(String.format(SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        var response = clientShippingCalc.setSwitchbackExperiments(request);

        Allure.step("Проверка успешного выполнения запроса", () -> assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ"));
    }

    @CaseId(296)
    @Story("Set Switchback Experiments")
    @Test(description = "Перезапись всех switchback-интервалов",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "setSwitchbackExperiment")
    public void setSwitchbackExperimentReplace() {
        var request = SetSwitchbackExperimentsRequest.newBuilder()
                .setData(String.format(MULTIPLE_SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 2, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 3, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        var response = clientShippingCalc.setSwitchbackExperiments(request);

        Allure.step("Проверка успешного выполнения запроса", () -> assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ"));
    }

    @CaseId(310)
    @Story("Set Switchback Experiments")
    @Test(description = "Получение ошибки при записи одинаковых switchback-интервалов",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot set switchback experiments: cannot insert new switchback experiments, ERROR: duplicate key value violates unique constraint.*")
    public void setSwitchbackExperimentSameIntervals() {
        var request = SetSwitchbackExperimentsRequest.newBuilder()
                .setData(String.format(MULTIPLE_SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        clientShippingCalc.setSwitchbackExperiments(request);
    }

    @CaseId(298)
    @Story("Set Switchback Experiments")
    @Test(description = "Получение ошибки при записи switchback-интервалов с некорректным типом источника",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot set switchback experiments: cannot extract switchback experiments from data received: unknown type of data source, experiments data parsing error")
    public void setSwitchbackExperimentInvalidType() {
        var request = SetSwitchbackExperimentsRequest.newBuilder()
                .setData(String.format(SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(1)
                .build();
        clientShippingCalc.setSwitchbackExperiments(request);
    }

    @CaseId(297)
    @Story("Get Switchback Experiments")
    @Test(description = "Получение списка switchback-интервалов",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "setSwitchbackExperimentReplace")
    public void getSwitchbackExperiment() {
        var request = GetSwitchbackExperimentsRequest.newBuilder().build();
        var response = clientShippingCalc.getSwitchbackExperiments(request);

        Allure.step("Проверка получения всех switchback-интервалов", () -> compareTwoObjects(response.getExperimentsCount(), 3));
    }
}
