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
import ru.instamart.jdbc.entity.shippingcalc.BindingRulesEntity;
import ru.instamart.kraken.enums.AppVersion;
import ru.instamart.kraken.enums.Tenant;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.*;

import java.util.UUID;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.api.helper.ShippingCalcHelper.deleteCreatedStrategy;
import static ru.instamart.kraken.util.TimeUtil.getDateWithoutTimezone;

@Epic("ShippingCalc")
@Feature("Autobinder")
public class AutobinderTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private int strategyId;
    private int secondStrategyId;
    private int binderRuleId;
    private int binderPartialRuleId;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
        strategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "123"), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(strategyId, "", "123", 0, "min_cart"), "{}", "always");
        addBindingRule(strategyId, DeliveryType.COURIER_DELIVERY.toString(), Tenant.SBERMARKET.getId(), 1, null, null, null, null);
        addBindingRule(strategyId, DeliveryType.COURIER_DELIVERY.toString(), Tenant.INSTAMART.getId(), null, null, null, null, getDateWithoutTimezone());
        secondStrategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(secondStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "321"), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(secondStrategyId, "", "321", 0, "min_cart"), "{}", "always");
        addBindingRule(secondStrategyId, DeliveryType.COURIER_DELIVERY.toString(), Tenant.SBERMARKET.getId(), 1, null, null, 1, null);
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
            binderRuleId = rule.getId();
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
            binderPartialRuleId = rule.getId();
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
                .setBindingRuleId(binderRuleId)
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
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleById(binderRuleId);
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
                .setBindingRuleId(binderPartialRuleId)
                .setRetailerId(1)
                .build();
        var response = clientShippingCalc.updateBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleById(binderPartialRuleId);
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
                .setBindingRuleId(binderPartialRuleId)
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
                .setBindingRuleId(binderPartialRuleId)
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
                .setBindingRuleId(binderRuleId)
                .build();
        var response = clientShippingCalc.deleteBindingRule(request);

        Allure.step("Проверка успешного выполнения запроса и сохранения в БД", () -> {
            assertTrue(response.toString().isEmpty(), "Не ожидаемый ответ");
            BindingRulesEntity rule = BindingRulesDao.INSTANCE.getBindingRuleById(binderRuleId);
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

    @CaseId(485)
    @Story("Autobinder in GetDeliveryPrice")
    @Test(description = "Запрос по магазину, который не привязан к стратегиям, возвращает ответ по стратегии из подходящего правила",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceAutobinder() {
        String storeId =  UUID.randomUUID().toString();
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, storeId, "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, strategyId, 123, 123, 1, 0, 0, 0);
    }

    @CaseId(488)
    @Story("Autobinder in GetDeliveryPrice")
    @Test(description = "Запрос по магазину, который не привязан к стратегиям, возвращает ответ по стратегии из правила с наибольшим рангом",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceAutobinderHighestPriority() {
        var request = GetDeliveryPriceRequest.newBuilder()
                .addShipments(Shipment.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setIsOndemand(true)
                        .setWeight(1000)
                        .setItemsCount(1)
                        .setPrice(99900)
                        .setStoreId(UUID.randomUUID().toString())
                        .setStatus("NEW")
                        .setRegionId(1)
                        .setRetailerId(1)
                        .addStoreLabelsId(1)
                        .setSurgeDeliveryWindowAddition(0)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setPositionsCount(1)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.57f)
                        .setLon(55.57f)
                        .build())
                .setOrderId(UUID.randomUUID().toString())
                .setIsB2BOrder(false)
                .setIsPromocode(false)
                .setPaymentMethod("Картой онлайн")
                .setHasPaymentMethod(true)
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
                .setTenantId(Tenant.SBERMARKET.getId())
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondStrategyId, 321, 321, 1, 0, 0, 0);
    }

    @CaseId(486)
    @Story("Autobinder in GetDeliveryPrice")
    @Test(description = "Получение ошибки, при запросе по магазину, который не привязан к стратегиям, если правило не подходит",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot get delivery price for some shipments, no correct price strategy")
    public void getDeliveryPriceNotSuitableAutobinder() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 2, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(487)
    @Story("Autobinder in GetDeliveryPrice")
    @Test(description = "Получение ошибки, при запросе по магазину, который не привязан к стратегиям, если правило подходит, но удалено",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot get delivery price for some shipments, no correct price strategy")
    public void getDeliveryPriceAutobinderDeleted() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.INSTAMART.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(strategyId);
        deleteCreatedStrategy(secondStrategyId);
    }
}
