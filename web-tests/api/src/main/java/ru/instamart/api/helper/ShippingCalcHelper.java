package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.apache.commons.lang3.StringUtils;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.shippingcalc.ConditionsDao;
import ru.instamart.jdbc.dao.shippingcalc.RulesDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategyBindingsDao;
import ru.instamart.jdbc.entity.shippingcalc.ConditionsEntity;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.jdbc.entity.shippingcalc.StrategiesEntity;
import ru.instamart.jdbc.entity.shippingcalc.StrategyBindingsEntity;
import shippingcalc.ShippingcalcOuterClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;

public class ShippingCalcHelper {

    @Step("Получаем запрос для создания стратегии")
    public static ShippingcalcOuterClass.CreateStrategyRequest getCreateStrategyRequest(
            Integer scriptId, String scriptParams, Integer priority, Integer conditionType, String params,
            Integer minCart, String creatorId, String name, String description, Boolean global, Integer deliveryType) {
        return ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(scriptId)
                        .setScriptParamValues(scriptParams)
                        .setPriority(priority)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(conditionType)
                                .setParams(params)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(minCart)
                        .setPriority(priority)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
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
    public static ShippingcalcOuterClass.UpdateStrategyRequest getUpdateStrategyRequest(
            Integer scriptId, String scriptParams, Integer priority, Integer conditionType, String params,
            Integer minCart, Integer strategyId, String creatorId, String name, String description, Boolean global, Integer deliveryType) {
        return ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(scriptId)
                        .setScriptParamValues(scriptParams)
                        .setPriority(priority)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(conditionType)
                                .setParams(params)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(minCart)
                        .setPriority(priority)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
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
    public static ShippingcalcOuterClass.BindStrategyRequest getBindStrategyRequest(Integer strategyId, String storeId, String tenantId, Integer deliveryTypeValue, Boolean replaceAll) {
        return ShippingcalcOuterClass.BindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(storeId)
                        .setTenantId(tenantId)
                        .setDeliveryTypeValue(deliveryTypeValue)
                        .build())
                .setReplaceAll(replaceAll)
                .build();
    }

    @Step("Получаем запрос для отвязки стратегии к магазину")
    public static ShippingcalcOuterClass.UnbindStrategyRequest getUnbindStrategyRequest(Integer strategyId, String storeId, String tenantId, Integer deliveryTypeValue) {
        return ShippingcalcOuterClass.UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(storeId)
                        .setTenantId(tenantId)
                        .setDeliveryTypeValue(deliveryTypeValue)
                        .build())
                .build();
    }

    @Step("Получаем запрос для получения цены доставки")
    public static ShippingcalcOuterClass.GetDeliveryPriceRequest getDeliveryPriceRequest(
            Integer quantity, String productId, Integer productPrice, Integer discountPrice, Integer productWeight,
            String shipmentId, Boolean isOndemand, Integer shipmentWeight, Integer itemsCount, Integer shipmentPrice,
            String storeId, String status, Integer regionId, Integer surgeDeliveryWindowAddition, Double storeLat, Double storeLon,
            String customerId, String anonymousId, Integer ordersCount, Integer registeredAt, Double customerLat, Double customerLon,
            String orderId, Boolean isB2bOrder, Boolean isPromocode, String paymentMethod, Boolean hasPaymentMethod, Integer deliveryType,
            String tenantId, String platformName, String platformVersion) {
        return ShippingcalcOuterClass.GetDeliveryPriceRequest.newBuilder()
                .addShipments(ShippingcalcOuterClass.Shipment.newBuilder()
                        .addProducts(ShippingcalcOuterClass.ProductRequest.newBuilder()
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
                .setCustomer(ShippingcalcOuterClass.Customer.newBuilder()
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
    public static ShippingcalcOuterClass.DeleteStrategyRequest getDeleteStrategyRequest(Integer strategyId) {
        return ShippingcalcOuterClass.DeleteStrategyRequest.newBuilder()
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

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(bind, "Не нашли такую связку");
        compareTwoObjects(bind.getStrategyId(), strategyId, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем отвязку стратегии от магазина")
    public static void checkUnbind(Integer strategyId, String storeId, String tenantId, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        StrategyBindingsEntity bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId, shipping);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNull(bind, "Связка не удалилась");
        softAssert.assertAll();
    }

    @Step("Проверяем расчитанную цену доставки")
    public static void checkDeliveryPrice(ShippingcalcOuterClass.GetDeliveryPriceResponse response, int strategyId, long deliveryPrice, long minCartPrice, int stepAmount, int hintAmount, int passedConditionAmount) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getIsOrderPossible(), "Заказ не возможен");
        softAssert.assertFalse(response.getShipments(0).getWeHadOffer(), "Получили цену из оффера");
        softAssert.assertEquals(response.getTotalShippingPrice(), deliveryPrice, "Не ожидаемая цена доставки");
        softAssert.assertEquals(response.getShipments(0).getStrategyId(), strategyId, "Посчитали по неверной стратегии");
        softAssert.assertEquals(response.getShipments(0).getMinimalCartPrice(), minCartPrice, "Отдали неверную цену мин. корзины");
        softAssert.assertEquals(response.getShipments(0).getLadderCount(), stepAmount, "В лесенке не ожидаемое кол-во ступеней");
        softAssert.assertEquals(response.getShipments(0).getHintsCount(), hintAmount, "Не ожидаемое кол-во подсказок");
        softAssert.assertEquals(response.getShipments(0).getPriceExplanation().getPassedConditionsCount(), passedConditionAmount, "Не ожидаемое кол-во прошедших условий");
        softAssert.assertAll();
    }

    @Step("Добавляем стратегию в БД")
    public static Integer addStrategy(Boolean global, Integer priority, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        Integer strategyId = StrategiesDao.INSTANCE.addStrategy("autotest-insert", shipping, global, priority, "autotest-insert", "autotest-insert");
        Integer firstPriceRuleId = RulesDao.INSTANCE.addRule(strategyId, 8, "{\"basicPrice\": \"0\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}", 0, "autotest-insert", "delivery_price");
        Integer secondPriceRuleId = RulesDao.INSTANCE.addRule(strategyId, 9, "{\"baseMass\": \"30000\", \"basicPrice\": \"19900\", \"bagIncrease\": \"0\", \"basePositions\": \"100\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}", 1, "autotest-insert", "delivery_price");
        Integer thirdPriceRuleId = RulesDao.INSTANCE.addRule(strategyId, 9, "{\"baseMass\": \"30000\", \"basicPrice\": \"9900\", \"bagIncrease\": \"0\", \"basePositions\": \"100\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}", 2, "autotest-insert", "delivery_price");
        Integer fourthPriceRuleId = RulesDao.INSTANCE.addRule(strategyId, 9, "{\"baseMass\": \"30000\", \"basicPrice\": \"0\", \"bagIncrease\": \"0\", \"basePositions\": \"100\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}", 3, "autotest-insert", "delivery_price");
        Integer firstMinCartRuleId = RulesDao.INSTANCE.addRule(strategyId, 0, "100000", 0, "autotest-insert", "min_cart");
        Integer secondMinCartRuleId = RulesDao.INSTANCE.addRule(strategyId, 0, "50000", 1, "autotest-insert", "min_cart");
        boolean firstCondition = ConditionsDao.INSTANCE.addCondition(firstPriceRuleId, "{\"Count\": 1}", "first_n_orders");
        boolean secondCondition = ConditionsDao.INSTANCE.addCondition(secondPriceRuleId, "{\"Max\": 100000, \"Min\": 0}", "order_value_range");
        boolean thirdCondition = ConditionsDao.INSTANCE.addCondition(thirdPriceRuleId, "{\"Max\": 300000, \"Min\": 100000}", "order_value_range");
        boolean fourthCondition = ConditionsDao.INSTANCE.addCondition(fourthPriceRuleId, "{\"Max\": 1000000000000000, \"Min\": 300100}", "order_value_range"); // ожидаемая дырка в условиях
        boolean fifthCondition = ConditionsDao.INSTANCE.addCondition(firstMinCartRuleId, "{\"Max\": 300000, \"Min\": 0}", "order_value_range");
        boolean sixthCondition = ConditionsDao.INSTANCE.addCondition(secondMinCartRuleId, "{\"Max\": 1000000000000000, \"Min\": 300100}", "order_value_range"); // ожидаемая дырка в условиях
        Allure.step("Проверяем что стратегия добавилась", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(strategyId > 0, "Пустое id стратегии");
            softAssert.assertTrue(firstPriceRuleId > 0, "Пустое id правила");
            softAssert.assertTrue(secondPriceRuleId > 0, "Пустое id правила");
            softAssert.assertTrue(thirdPriceRuleId > 0, "Пустое id правила");
            softAssert.assertTrue(fourthPriceRuleId > 0, "Пустое id правила");
            softAssert.assertTrue(firstMinCartRuleId > 0, "Пустое id правила");
            softAssert.assertTrue(secondMinCartRuleId > 0, "Пустое id правила");
            softAssert.assertTrue(firstCondition, "Условие не создалось");
            softAssert.assertTrue(secondCondition, "Условие не создалось");
            softAssert.assertTrue(thirdCondition, "Условие не создалось");
            softAssert.assertTrue(fourthCondition, "Условие не создалось");
            softAssert.assertTrue(fifthCondition, "Условие не создалось");
            softAssert.assertTrue(sixthCondition, "Условие не создалось");
            softAssert.assertAll();
        });
        return strategyId;
    }

    @Step("Добавляем привязку магазина к стратегии")
    public static void addBinding(Integer strategyId, String storeId, String tenantId, String deliveryType) {
        String shipping = StringUtils.substringBefore(deliveryType, "_").toLowerCase();
        Boolean binding = StrategyBindingsDao.INSTANCE.addStrategyBinding(strategyId, storeId, tenantId, shipping);
        Allure.step("Проверяем что связка добавилась", () -> {
            assertTrue(binding, "Связка не добавилась");
        });
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
}
