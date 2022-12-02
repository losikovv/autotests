package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.BindingRulesDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.entity.shippingcalc.BindingRulesEntity;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.*;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.ShippingCalcHelper.addStrategy;

@Epic("On Demand")
@Feature("ShippingCalc")
public class AutobinderTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private int strategyId;
    private int secondStrategyId;
    private int ruleId;
    private int partialRuleId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
        strategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        secondStrategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
    }

    @CaseId(459)
    @Story("Create Binding Rule")
    @Test(description = "Создание правила для автобайндера с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void createBindingRule() {
        var request = CreateBindingRuleRequest.newBuilder()
                .setStrategyId(strategyId)
                .setTenantId("test")
                .setRegionId(1)
                .setRetailerId(1)
                .setLabelId(1)
                .setOndemand(true)
                .build();
        var response = clientShippingCalc.createBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleByStrategyAndTenant(strategyId, "test");
            ruleId = rule.getId();
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(rule.getRegionId().intValue(), 1, "Не верный регион");
            softAssert.assertEquals(rule.getRetailerId().intValue(), 1, "Не верный ритейлер");
            softAssert.assertEquals(rule.getLabelId().intValue(), 1, "Не верный лейбл");
            softAssert.assertTrue(rule.getOndemand(), "Не верный ондеманд");
            softAssert.assertNull(rule.getDeletedAt(), "Правило помечно удаленным");
            softAssert.assertAll();
        });
    }

    @CaseId(543)
    @Story("Create Binding Rule")
    @Test(description = "Создание правила для автобайндера с частью полей в запросе",
            groups = "dispatch-shippingcalc-regress")
    public void createBindingRulePartial() {
        var request = CreateBindingRuleRequest.newBuilder()
                .setStrategyId(strategyId)
                .setTenantId("test-partial")
                .setRegionId(1)
                .build();
        var response = clientShippingCalc.createBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleByStrategyAndTenant(strategyId, "test-partial");
            partialRuleId = rule.getId();
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(rule.getRegionId().intValue(), 1, "Не верный регион");
            softAssert.assertEquals(rule.getRetailerId().intValue(), 0, "Не верный ритейлер");
            softAssert.assertEquals(rule.getLabelId().intValue(), 0, "Не верный лейбл");
            softAssert.assertFalse(rule.getOndemand(), "Не верный ондеманд");
            softAssert.assertAll();
        });
    }

    @CaseId(461)
    @Story("Create Binding Rule")
    @Test(description = "Получение ошибки при создании правила для автобайндера без указания стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: invalid strategy id")
    public void createBindingRuleNoStrategyId() {
        var request = CreateBindingRuleRequest.newBuilder().build();
        clientShippingCalc.createBindingRule(request);
    }

    @CaseId(480)
    @Story("Create Binding Rule")
    @Test(description = "Получение ошибки при создании правила для автобайндера с указанием несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "FAILED_PRECONDITION: not found strategy for binding rule")
    public void createBindingRuleNotFoundStrategyId() {
        var request = CreateBindingRuleRequest.newBuilder().setStrategyId(Integer.MAX_VALUE).build();
        clientShippingCalc.createBindingRule(request);
    }

    @CaseId(466)
    @Story("Update Binding Rule")
    @Test(description = "Обновление существующего правила для автобайндера с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createBindingRule")
    public void updateBindingRule() {
        var request = UpdateBindingRuleRequest.newBuilder()
                .setBindingRuleId(ruleId)
                .setStrategyId(secondStrategyId)
                .setTenantId("test-updated")
                .setRegionId(2)
                .setRetailerId(2)
                .setLabelId(2)
                .setOndemand(false)
                .build();
        var response = clientShippingCalc.updateBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleById(ruleId);
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(rule.getStrategyId().intValue(), secondStrategyId, "Не верная стратегия");
            softAssert.assertEquals(rule.getTenantId(), "test-updated", "Не верный тенант");
            softAssert.assertEquals(rule.getRegionId().intValue(), 2, "Не верный регион");
            softAssert.assertEquals(rule.getRetailerId().intValue(), 2, "Не верный ритейлер");
            softAssert.assertEquals(rule.getLabelId().intValue(), 2, "Не верный лейбл");
            softAssert.assertFalse(rule.getOndemand(), "Не верный ондеманд");
            softAssert.assertAll();
        });
    }

    @CaseId(469)
    @Story("Update Binding Rule")
    @Test(description = "Проверка отсутствия обновления опциональных полей с пустым значением в запросе",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createBindingRulePartial")
    public void updateBindingRulePartial() {
        var request = UpdateBindingRuleRequest.newBuilder()
                .setBindingRuleId(partialRuleId)
                .setRetailerId(1)
                .build();
        var response = clientShippingCalc.updateBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleById(partialRuleId);
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(rule.getStrategyId().intValue(), strategyId, "Не верная стратегия");
            softAssert.assertEquals(rule.getTenantId(), "test-partial", "Не верный тенант");
            softAssert.assertEquals(rule.getRegionId().intValue(), 1, "Не верный регион");
            softAssert.assertEquals(rule.getRetailerId().intValue(), 1, "Не верный ритейлер");
            softAssert.assertEquals(rule.getLabelId().intValue(), 0, "Не верный лейбл");
            softAssert.assertFalse(rule.getOndemand(), "Не верный ондеманд");
            softAssert.assertAll();
        });
    }

    @CaseId(546)
    @Story("Update Binding Rule")
    @Test(description = "Получение ошибки при обновлении правила для автобайндера без указания хотя бы одного поля",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createBindingRulePartial",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: at least one field must be not empty")
    public void updateBindingRuleEmpty() {
        var request = UpdateBindingRuleRequest.newBuilder()
                .setBindingRuleId(partialRuleId)
                .build();
        clientShippingCalc.updateBindingRule(request);
    }

    @CaseId(544)
    @Story("Update Binding Rule")
    @Test(description = "Получение ошибки при обновлении правила для автобайндера с указанием несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createBindingRulePartial",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "FAILED_PRECONDITION: not found strategy for updating binding rule")
    public void updateBindingRuleNotFoundStrategyId() {
        var request = UpdateBindingRuleRequest.newBuilder()
                .setBindingRuleId(partialRuleId)
                .setStrategyId(Integer.MAX_VALUE)
                .build();
        clientShippingCalc.updateBindingRule(request);
    }

    @CaseId(474)
    @Story("Get Binding Rules")
    @Test(description = "Получение списка правил для автобайндера",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createBindingRule")
    public void getBindingRules() {
        var request = GetBindingRulesRequest.newBuilder().build();
        var response = clientShippingCalc.getBindingRules(request);

        Allure.step("Проверка списка правил в ответе", () -> {
            assertTrue(response.getBindingRulesCount() > 0, "Пустой ответ");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getBindingRules(0).getId() > 0, "Пустое id");
            softAssert.assertTrue(response.getBindingRules(0).getStrategyId() > 0, "Пустое strategy_id");
            softAssert.assertNotNull(response.getBindingRules(0).getShipping(), "Пустой shipping");
            softAssert.assertNotNull(response.getBindingRules(0).getTenantId(), "Пустой tenant_id");
            softAssert.assertAll();
        });
    }

    @CaseId(477)
    @Story("Delete Binding Rule")
    @Test(description = "Удаление существующего правила для автобайндера",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getBindingRules")
    public void deleteBindingRule() {
        var request = DeleteBindingRuleRequest.newBuilder()
                .setBindingRuleId(ruleId)
                .build();
        var response = clientShippingCalc.deleteBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleById(ruleId);
            assertNotNull(rule.getDeletedAt(), "правило не удалено");
        });
    }

    @CaseId(478)
    @Story("Delete Binding Rule")
    @Test(description = "Получение ошибки при удалении правила для автобайндера без указания стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid request: invalid binding rule id")
    public void deleteBindingRuleNoStrategyId() {
        var request = DeleteBindingRuleRequest.newBuilder()
                .build();
        clientShippingCalc.deleteBindingRule(request);
    }

    @CaseId(545)
    @Story("Delete Binding Rule")
    @Test(description = "Получение ошибки при удалении правила для автобайндера с указанием несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "FAILED_PRECONDITION: not found binding rule for delete")
    public void deleteBindingRuleNotFoundStrategyId() {
        var request = DeleteBindingRuleRequest.newBuilder()
                .setBindingRuleId(Integer.MAX_VALUE)
                .build();
        clientShippingCalc.deleteBindingRule(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        StrategiesDao.INSTANCE.delete(strategyId);
        StrategiesDao.INSTANCE.delete(secondStrategyId);
    }
}
