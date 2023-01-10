package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.SwitchbacksDao;
import shippingcalc.GetSwitchbacksRequest;
import shippingcalc.SetSwitchbacksRequest;
import shippingcalc.ShippingcalcGrpc;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.TimeUtil.getDbDate;

@Epic("ShippingCalc")
@Feature("Switchbacks")
public class SwitchbacksTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private final String SWITCHBACK_INTERVAL = "start_date_time,end_date_time,region_id,vertical,group,parameters\n" +
            "%s,%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n";
    private final String MULTIPLE_SWITCHBACK_INTERVAL = "start_date_time,end_date_time,region_id,vertical,group,parameters\n" +
            "%s,%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n" +
            "%s,%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n" +
            "%s,%s,%s,%s,test,\"{\"\"intervals\"\":[{\"\"left_boundary\"\":0,\"\"right_boundary\"\":1,\"\"price_addition\"\":0,\"\"percent_addition\"\":0,\"\"min_cart_addition\"\":0},{\"\"left_boundary\"\":1,\"\"right_boundary\"\":%s,\"\"price_addition\"\":%s,\"\"percent_addition\"\":%s,\"\"min_cart_addition\"\":%s},{\"\"left_boundary\"\":%s,\"\"right_boundary\"\":10,\"\"price_addition\"\":20000,\"\"percent_addition\"\":20,\"\"min_cart_addition\"\":20000}]}\"\n";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    @TmsLink("293")
    @Story("Set Switchbacks")
    @Test(description = "Запись switchback-интервала",
            groups = "ondemand-shippingcalc")
    public void setSwitchbacks() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData(String.format(SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        var response = clientShippingCalc.setSwitchbacks(request);

        Allure.step("Проверка успешного выполнения запроса", () -> assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ"));
    }

    @TmsLink("296")
    @Story("Set Switchbacks")
    @Test(description = "Перезапись всех switchback-интервалов",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "setSwitchbacks")
    public void setSwitchbacksReplace() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData(String.format(MULTIPLE_SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 2, 2, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 3, 3, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        var response = clientShippingCalc.setSwitchbacks(request);

        Allure.step("Проверка успешного выполнения запроса", () -> assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ"));
    }

    @TmsLink("294")
    @Story("Set Switchbacks")
    @Test(description = "Запись пустого списка switchback-интервалов",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getSwitchbacks")
    public void setSwitchbacksEmpty() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData("start_date_time,end_date_time,region_id,vertical,group,parameters")
                .setTypeValue(0)
                .build();
        var response = clientShippingCalc.setSwitchbacks(request);

        Allure.step("Проверка успешного выполнения запроса и состояния БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            final var switchbacks = SwitchbacksDao.INSTANCE.getSwitchbacks();
            assertEquals(switchbacks.size(), 0, "Не верное кол-во свитчбек-интервалов");
        });
    }

    @TmsLink("575")
    @Story("Set Switchbacks")
    @Test(description = "Запись switchback-интервала глобального по одному признаку (region_id или vertical)",
            groups = "ondemand-shippingcalc")
    public void setSwitchbacksOneGlobal() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData(String.format(SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), -1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        var response = clientShippingCalc.setSwitchbacks(request);

        Allure.step("Проверка успешного выполнения запроса", () -> assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ"));
    }

    @TmsLink("576")
    @Story("Set Switchbacks")
    @Test(description = "Получение ошибки при записи switchback-интервала глобального по обоим признакам (region_id и vertical)",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot set switchbacks: cannot extract switchback experiments from data received: error parsing record 0, region_id and vertical cannot be both below zero, too wide experment, experiments data parsing error")
    public void setSwitchbacksBothGlobal() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData(String.format(SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), -1, -1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        clientShippingCalc.setSwitchbacks(request);
    }

    @TmsLink("310")
    @Story("Set Switchbacks")
    @Test(description = "Получение ошибки при записи одинаковых switchback-интервалов",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot set switchbacks: cannot insert new switchbacks, ERROR: duplicate key value violates unique constraint.*")
    public void setSwitchbacksSameIntervals() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData(String.format(MULTIPLE_SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL,
                        getDbDate(), getDbDate(), 1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(0)
                .build();
        clientShippingCalc.setSwitchbacks(request);
    }

    @TmsLink("298")
    @Story("Set Switchbacks")
    @Test(description = "Получение ошибки при записи switchback-интервалов с некорректным типом источника",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot set switchbacks: cannot extract switchback experiments from data received: unknown type of data source, experiments data parsing error")
    public void setSwitchbacksInvalidType() {
        var request = SetSwitchbacksRequest.newBuilder()
                .setData(String.format(SWITCHBACK_INTERVAL,
                        getDbDate(), getDbDate(), 1, 1, SURGE_LEVEL, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL_PERCENT_ADDITION, SURGE_LEVEL_ADDITION_DEFAULT, SURGE_LEVEL))
                .setTypeValue(1)
                .build();
        clientShippingCalc.setSwitchbacks(request);
    }

    @TmsLink("297")
    @Story("Get Switchbacks")
    @Test(description = "Получение списка switchback-интервалов",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "setSwitchbacksReplace")
    public void getSwitchbacks() {
        var request = GetSwitchbacksRequest.newBuilder().build();
        var response = clientShippingCalc.getSwitchbacks(request);

        Allure.step("Проверка получения всех switchback-интервалов", () -> {
            assertTrue(response.getExperimentsCount() > 0, "В ответе пустой список интервалов");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getExperimentsCount() >= 3, "В ответе не ожидаемое кол-во параметров");
            softAssert.assertNotNull(response.getExperiments(0).getStartDateTime(), "В ответе пустая дата начала");
            softAssert.assertNotNull(response.getExperiments(0).getEndDateTime(), "В ответе пустая дата окончания");
            softAssert.assertNotNull(response.getExperiments(0).getGroup(), "В ответе пустая группа");
            softAssert.assertNotNull(response.getExperiments(0).getParameters(), "В ответе пустые параметры");
            softAssert.assertAll();
        });
    }
}