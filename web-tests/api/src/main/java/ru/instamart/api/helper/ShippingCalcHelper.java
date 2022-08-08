package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.shippingcalc.*;
import ru.instamart.jdbc.entity.shippingcalc.*;
import shippingcalc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

public class ShippingCalcHelper {

    @Step("Получаем запрос для создания стратегии")
    public static CreateStrategyRequest getCreateStrategyRequest(
            Integer scriptId, String scriptParams, Integer priority, Integer conditionType, String params,
            Integer minCart, String creatorId, String name, String description, Boolean global, Integer deliveryType) {
        return CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(scriptId)
                        .setScriptParamValues(scriptParams)
                        .setPriority(priority)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(conditionType)
                                .setParams(params)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(minCart)
                        .setPriority(priority)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(conditionType)
                                .setParams(params)
                                .build())
                        .build())
                .setCreatorId(creatorId)
                .setName(name)
                .setGlobal(global)
                .setPriority(priority)
                .setDescription(description)
                .setDeliveryTypeValue(deliveryType)
                .build();
    }

    @Step("Получаем запрос для обновления стратегии")
    public static UpdateStrategyRequest getUpdateStrategyRequest(
            Integer scriptId, String scriptParams, Integer priority, Integer conditionType, String params,
            Integer minCart, Integer strategyId, String creatorId, String name, String description, Boolean global, Integer deliveryType) {
        return UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(scriptId)
                        .setScriptParamValues(scriptParams)
                        .setPriority(priority)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(conditionType)
                                .setParams(params)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(minCart)
                        .setPriority(priority)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(conditionType)
                                .setParams(params)
                                .build())
                        .build())
                .setStrategyId(strategyId)
                .setCreatorId(creatorId)
                .setName(name)
                .setGlobal(global)
                .setPriority(priority)
                .setDescription(description)
                .setDeliveryTypeValue(deliveryType)
                .build();
    }

    @Step("Получаем запрос для привязки стратегии к магазину")
    public static BindStrategyRequest getBindStrategyRequest(Integer strategyId, String storeId, String tenantId, Integer deliveryTypeValue, Boolean replaceAll) {
        return BindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(storeId)
                        .setTenantId(tenantId)
                        .setDeliveryTypeValue(deliveryTypeValue)
                        .build())
                .setReplaceAll(replaceAll)
                .build();
    }

    @Step("Получаем запрос для отвязки стратегии к магазину")
    public static UnbindStrategyRequest getUnbindStrategyRequest(Integer strategyId, String storeId, String tenantId, Integer deliveryTypeValue) {
        return UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(storeId)
                        .setTenantId(tenantId)
                        .setDeliveryTypeValue(deliveryTypeValue)
                        .build())
                .build();
    }

    @Step("Получаем запрос для получения цены доставки")
    public static GetDeliveryPriceRequest getDeliveryPriceRequest(
            Integer quantity, String productId, Integer productPrice, Integer discountPrice, Integer productWeight,
            String shipmentId, Boolean isOndemand, Integer shipmentWeight, Integer itemsCount, Integer shipmentPrice,
            String storeId, String status, Integer regionId, Integer surgeDeliveryWindowAddition, Double storeLat, Double storeLon,
            String customerId, String anonymousId, Integer ordersCount, Integer registeredAt, Double customerLat, Double customerLon,
            String orderId, Boolean isB2bOrder, Boolean isPromocode, String paymentMethod, Boolean hasPaymentMethod, Integer deliveryType,
            String tenantId, String platformName, String platformVersion) {
        return GetDeliveryPriceRequest.newBuilder()
                .addShipments(Shipment.newBuilder()
                        .addProducts(ProductRequest.newBuilder()
                                .setQuantity(quantity)
                                .setId(productId)
                                .setPrice(productPrice)
                                .setDiscountPrice(discountPrice)
                                .setWeight(productWeight)
                                .build())
                        .setId(shipmentId)
                        .setIsOndemand(isOndemand)
                        .setWeight(shipmentWeight)
                        .setItemsCount(itemsCount)
                        .setPrice(shipmentPrice)
                        .setStoreId(storeId)
                        .setStatus(status)
                        .setRegionId(regionId)
                        .setSurgeDeliveryWindowAddition(surgeDeliveryWindowAddition)
                        .setLat(storeLat.floatValue())
                        .setLon(storeLon.floatValue())
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(customerId)
                        .setAnonymousId(anonymousId)
                        .setOrdersCount(ordersCount)
                        .setRegisteredAt(registeredAt)
                        .setLat(customerLat.floatValue())
                        .setLon(customerLon.floatValue())
                        .build())
                .setOrderId(orderId)
                .setIsB2BOrder(isB2bOrder)
                .setIsPromocode(isPromocode)
                .setPaymentMethod(paymentMethod)
                .setHasPaymentMethod(hasPaymentMethod)
                .setDeliveryTypeValue(deliveryType)
                .setTenantId(tenantId)
                .setPlatformName(platformName)
                .setPlatformVersion(platformVersion)
                .build();
    }

    @Step("Получаем запрос для удаления стратегии")
    public static DeleteStrategyRequest getDeleteStrategyRequest(Integer strategyId) {
        return DeleteStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .build();
    }

    @Step("Проверяем стратегию")
    public static void checkStrategy(Integer strategyId, String strategyName, Integer rulesAmount, Integer conditionsAmount, Integer conditionIndex, String deliveryType) {
        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        List<RulesEntity> rules = RulesDao.INSTANCE.getRules(strategyId);
        ArrayList<Integer> rulesIds = new ArrayList();
        for (RulesEntity rule : rules) rulesIds.add(rule.getId());
        List<ConditionsEntity> conditions = ConditionsDao.INSTANCE.getConditions(rulesIds);
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(strategyId, strategy.getId(), softAssert);
        compareTwoObjects(shipping, strategy.getShipping(), softAssert);
        compareTwoObjects(rules.size(), rulesAmount, softAssert);
        compareTwoObjects(conditions.size(), conditionsAmount, softAssert);
        compareTwoObjects(strategy.getName(), strategyName, softAssert);
        compareTwoObjects(conditions.get(conditionIndex).getParams(), "{\"Max\": 1000000000000000, \"Min\": 0}", softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем обновленную стратегию")
    public static void checkUpdatedStrategy(Integer strategyId, String strategyName, Integer rulesAmount, Integer conditionsAmount, Integer conditionIndex, String shipping) {
        checkStrategy(strategyId, strategyName, rulesAmount, conditionsAmount, conditionIndex, shipping);
        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        List<RulesEntity> deletedRules = RulesDao.INSTANCE.getDeletedRules(strategyId);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(strategy.getUpdatedAt().equals(strategy.getCreatedAt()), "Поле updated_at не обновилось");
        compareTwoObjects(deletedRules.size(), rulesAmount / 2, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем связку стратегии к магазину")
    public static void checkBind(Integer strategyId, String storeId, String tenantId, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        StrategyBindingsEntity bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId, shipping);

        assertNotNull(bind, "Не нашли такую связку");
        compareTwoObjects(bind.getStrategyId(), strategyId);
    }

    @Step("Проверяем отвязку стратегии от магазина")
    public static void checkUnbind(Integer strategyId, String storeId, String tenantId, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        StrategyBindingsEntity bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId, shipping);

        assertNull(bind, "Связка не удалилась");
    }

    @Step("Проверяем расчитанную цену доставки")
    public static void checkDeliveryPrice(GetDeliveryPriceResponse response, int strategyId, long totalDeliveryPrice, long minCartPrice, int stepAmount, int hintAmount, int passedConditionAmount, int shipmentIndex) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getIsOrderPossible(), "Заказ не возможен");
        softAssert.assertFalse(response.getShipments(shipmentIndex).getWeHadOffer(), "Получили цену из оффера");
        softAssert.assertEquals(response.getTotalShippingPrice(), totalDeliveryPrice, "Не ожидаемая общая цена доставки");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getStrategyId(), strategyId, "Посчитали по неверной стратегии");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getMinimalCartPrice(), minCartPrice, "Отдали неверную цену мин. корзины");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getLadderCount(), stepAmount, "В лесенке не ожидаемое кол-во ступеней");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getHintsCount(), hintAmount, "Не ожидаемое кол-во подсказок");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getPriceExplanation().getPassedConditionsCount(), passedConditionAmount, "Не ожидаемое кол-во прошедших условий");
        softAssert.assertAll();
    }

    @Step("Добавляем стратегию в БД")
    public static Integer addStrategy(Boolean global, Integer priority, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        Integer strategyId = StrategiesDao.INSTANCE.addStrategy("autotest-insert", shipping, global, priority, "autotest-insert", "autotest-insert");
        Allure.step("Проверяем что стратегия добавилась", () -> assertTrue(strategyId > 0, "Пустое id стратегии"));
        return strategyId;
    }

    @Step("Добавляем правило к стратегии в БД")
    public static Integer addRule(Integer strategyId, String scriptName, String scriptParams, Integer priority, String ruleType) {
        Integer scriptId;
        if (!scriptName.isEmpty()) {
            scriptId = ScriptsDao.INSTANCE.getScriptByName(scriptName).getId();
        } else {
            scriptId = 0;
        }
        Integer ruleId = RulesDao.INSTANCE.addRule(strategyId, scriptId, scriptParams, priority, "autotest-insert", ruleType);
        Allure.step("Проверяем что правило добавилась", () -> assertTrue(ruleId > 0, "Пустое id правила"));
        return ruleId;
    }

    @Step("Добавляем условие к правилу в БД")
    public static void addCondition(Integer ruleId, String params, String conditionType) {
        boolean condition = ConditionsDao.INSTANCE.addCondition(ruleId, params, conditionType);
        Allure.step("Проверяем что условие добавилась", () -> assertTrue(condition, "Условие не создалось"));
    }

    @Step("Добавляем привязку магазина к стратегии")
    public static void addBinding(Integer strategyId, String storeId, String tenantId, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        Boolean binding = StrategyBindingsDao.INSTANCE.addStrategyBinding(strategyId, storeId, tenantId, shipping);
        Allure.step("Проверяем что связка добавилась", () -> assertTrue(binding, "Связка не добавилась"));
    }

    @Step("Удаляем созданную стратегию из БД")
    public static void deleteCreatedStrategy(Integer strategyId) {
        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        List<RulesEntity> rules = RulesDao.INSTANCE.getRules(strategyId);

        if (Objects.nonNull(strategy) && !rules.isEmpty()) {
            RulesDao.INSTANCE.delete(strategyId);
            StrategiesDao.INSTANCE.delete(strategyId);
        }
    }

    @Step("Получаем запрос для создания скрипта")
    public static CreateScriptRequest getCreateScriptRequest(String scriptName, String scriptBody, String creatorId) {
        return CreateScriptRequest.newBuilder()
                .setScriptName(scriptName)
                .setScriptBody(scriptBody)
                .setCreatorId(creatorId)
                .build();
    }

    @Step("Получаем запрос для обновления скрипта")
    public static UpdateScriptRequest getUpdateScriptRequest(
            Integer scriptId, String scriptName, String scriptBody, String creatorId) {
        return UpdateScriptRequest.newBuilder()
                .setScriptId(scriptId)
                .setScriptName(scriptName)
                .setScriptBody(scriptBody)
                .setCreatorId(creatorId)
                .build();
    }

    @Step("Проверяем скрипт")
    public static void checkScript(Integer scriptId, String scriptName, String state) {
        ScriptsEntity script = ScriptsDao.INSTANCE.getScriptById(scriptId);

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(scriptId, script.getId(), softAssert);
        compareTwoObjects(script.getName(), scriptName, softAssert);
        softAssert.assertNotNull(script.getCode(), "Code вернулся null");
        softAssert.assertNotNull(script.getRequiredParams(), "requiredParams вернулся null");
        compareTwoObjects(script.getState(), state, softAssert);
        assertNotNull(script, "Данные из БД вернулись пустые");
        softAssert.assertAll();
    }

    @Step("Проверяем обновленный скрипт")
    public static void checkUpdatedScript(Integer scriptId, String scriptName, String state) {
        checkScript(scriptId, scriptName, state);
        ScriptsEntity script = ScriptsDao.INSTANCE.getScriptById(scriptId);

        assertNotNull(script, "Данные из БД вернулись пустые");
        assertFalse(script.getUpdatedAt().equals(script.getCreatedAt()), "Поле updated_at не обновилось");
    }
}
