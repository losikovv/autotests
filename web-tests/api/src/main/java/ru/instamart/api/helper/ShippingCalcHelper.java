package ru.instamart.api.helper;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.Getter;
import org.testng.asserts.SoftAssert;
import ru.instamart.jdbc.dao.shippingcalc.*;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import shippingcalc.*;

import java.util.ArrayList;
import java.util.Objects;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.enums.BashCommands.ServiceEnvironmentProperties.SHIPPINGCALC_SURGE_DISABLED;
import static ru.instamart.api.helper.K8sHelper.getServiceEnvProp;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;
import static ru.instamart.kraken.util.StringUtil.substringToLastIndexOfStr;

public class ShippingCalcHelper {

    private static volatile ShippingCalcHelper INSTANCE;
    @Getter
    private final boolean surgeDisabled;

    private ShippingCalcHelper() {
        this.surgeDisabled = getSurgeDisabledFromK8s();
    }

    public static ShippingCalcHelper getInstance() {
        ShippingCalcHelper RESULT = INSTANCE;
        if (RESULT != null) {
            return INSTANCE;
        }
        synchronized (ShippingCalcHelper.class) {
            if (INSTANCE == null) {
                INSTANCE = new ShippingCalcHelper();
            }
            return INSTANCE;
        }
    }

    @Step("Получаем запрос для создания стратегии")
    public static CreateStrategyRequest getCreateStrategyRequest(final Integer scriptId, final String scriptParams, final Integer priority,
                                                                 final Integer conditionType, final String params, final Integer minCart,
                                                                 final String creatorId, final String name, final String description, final Boolean global,
                                                                 final Integer deliveryType) {
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
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
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
            final Integer scriptId, final String scriptParams, final Integer priority, final Integer conditionType, final String params,
            final Integer minCart, final Integer strategyId, final String creatorId, final String name, final String description,
            final Boolean global, final Integer deliveryType) {
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
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
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
    public static BindStrategyRequest getBindStrategyRequest(final Integer strategyId, final String storeId, final String tenantId, final Integer deliveryTypeValue, final Boolean replaceAll) {
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
    public static UnbindStrategyRequest getUnbindStrategyRequest(final Integer strategyId, final String storeId, final String tenantId, final Integer deliveryTypeValue) {
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
            final Integer positionsCount, final String shipmentId, final Boolean isOndemand, final Integer shipmentWeight, final Integer itemsCount, final Integer shipmentPrice,
            final String storeId, final String status, final Integer regionId, final Integer surgeDeliveryWindowAddition, final Double storeLat, final Double storeLon,
            final String customerId, final String anonymousId, final Integer ordersCount, final Integer registeredAt, final Double customerLat, final Double customerLon,
            final String orderId, final Boolean isB2bOrder, final Boolean isPromocode, final String paymentMethod, final Boolean hasPaymentMethod, final Integer deliveryType,
            final String tenantId, final String platformName, final String platformVersion) {
        return GetDeliveryPriceRequest.newBuilder()
                .addShipments(Shipment.newBuilder()
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
                        .setPositionsCount(positionsCount)
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
    public static DeleteStrategyRequest getDeleteStrategyRequest(final Integer strategyId) {
        return DeleteStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .build();
    }

    @Step("Проверяем стратегию")
    public static void checkStrategy(final Integer strategyId, final String strategyName, final Integer rulesAmount, final Integer conditionsAmount, final Integer conditionIndex, final String deliveryType) {
        final var strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        final var rules = RulesDao.INSTANCE.getRules(strategyId);
        final var rulesIds = new ArrayList<Integer>();
        for (RulesEntity rule : rules) rulesIds.add(rule.getId());
        final var conditions = ConditionsDao.INSTANCE.getConditions(rulesIds);
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(strategyId, strategy.getId(), softAssert);
        compareTwoObjects(shipping, strategy.getShipping(), softAssert);
        compareTwoObjects(rules.size(), rulesAmount, softAssert);
        compareTwoObjects(conditions.size(), conditionsAmount, softAssert);
        compareTwoObjects(strategy.getName(), strategyName, softAssert);
        compareTwoObjects(conditions.get(conditionIndex).getParams(), "{\"Max\": 1000000000000000, \"Min\": 0}", softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем стратегию")
    public static void checkGlobalStrategy(final Integer strategyId, final String strategyName, final Integer rulesAmount, final Integer conditionsAmount, final String deliveryType) {
        final var strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        final var rules = RulesDao.INSTANCE.getRules(strategyId);
        final var rulesIds = new ArrayList<Integer>();
        for (RulesEntity rule : rules) rulesIds.add(rule.getId());
        final var conditions = ConditionsDao.INSTANCE.getConditions(rulesIds);
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(strategyId, strategy.getId(), softAssert);
        compareTwoObjects(shipping, strategy.getShipping(), softAssert);
        compareTwoObjects(rules.size(), rulesAmount, softAssert);
        compareTwoObjects(conditions.size(), conditionsAmount, softAssert);
        compareTwoObjects(strategy.getName(), strategyName, softAssert);
        softAssert.assertTrue(strategy.getGlobal(), "Не глобальная стратегия");
        softAssert.assertAll();
    }

    @Step("Проверяем обновленную стратегию")
    public static void checkUpdatedStrategy(final Integer strategyId, final String strategyName, final Integer rulesAmount, final Integer conditionsAmount, final Integer conditionIndex, final String shipping) {
        checkStrategy(strategyId, strategyName, rulesAmount, conditionsAmount, conditionIndex, shipping);
        final var strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        final var deletedRules = RulesDao.INSTANCE.getDeletedRules(strategyId);

        assertNotEquals(strategy.getCreatedAt(), strategy.getUpdatedAt(), "Поле updated_at не обновилось");
        compareTwoObjects(deletedRules.size(), rulesAmount / 2);
    }

    @Step("Проверяем обновленную стратегию")
    public static void checkUpdatedGlobalStrategy(final Integer strategyId, final String strategyName, final Integer rulesAmount, final Integer conditionsAmount, final String shipping) {
        checkGlobalStrategy(strategyId, strategyName, rulesAmount, conditionsAmount, shipping);
        final var strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        final var deletedRules = RulesDao.INSTANCE.getDeletedRules(strategyId);

        assertNotEquals(strategy.getCreatedAt(), strategy.getUpdatedAt(), "Поле updated_at не обновилось");
        compareTwoObjects(deletedRules.size(), rulesAmount / 2);
    }

    @Step("Проверяем связку стратегии к магазину")
    public static void checkBind(final Integer strategyId, final String storeId, final String tenantId, final String deliveryType) {
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");
        final var bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId, shipping);

        assertNotNull(bind, "Не нашли такую связку");
        compareTwoObjects(bind.getStrategyId(), strategyId);
    }

    @Step("Проверяем отвязку стратегии от магазина")
    public static void checkUnbind(final Integer strategyId, final String storeId, final String tenantId, final String deliveryType) {
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");
        final var bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId, shipping);
        assertNull(bind, "Связка не удалилась");
    }

    @Step("Проверяем рассчитанную цену доставки")
    public static void checkDeliveryPrice(final GetDeliveryPriceResponse response, final int strategyId, final long totalDeliveryPrice, final long minCartPrice, final int stepAmount, final int hintAmount, final int passedConditionAmount, final int shipmentIndex) {
        assertTrue(response.getIsOrderPossible(), "Заказ не возможен");
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(response.getShipments(shipmentIndex).getWeHadOffer(), "Получили цену из оффера");
        softAssert.assertEquals(response.getTotalShippingPrice(), totalDeliveryPrice, "Не ожидаемая общая цена доставки");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getStrategyId(), strategyId, "Посчитали по неверной стратегии");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getMinimalCartPrice(), minCartPrice, "Отдали неверную цену мин. корзины");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getLadderCount(), stepAmount, "В лесенке не ожидаемое кол-во ступеней");
        softAssert.assertTrue(response.getShipments(shipmentIndex).getLadder(0).getPriceComponentsCount() > 0, "В лесенке нет ценовых компонентов");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getHintsCount(), hintAmount, "Не ожидаемое кол-во подсказок");
        softAssert.assertEquals(response.getShipments(shipmentIndex).getPriceExplanation().getPassedConditionsCount(), passedConditionAmount, "Не ожидаемое кол-во прошедших условий");
        softAssert.assertAll();
    }

    @Step("Проверяем наценку по surge в цене доставки")
    public static void checkDeliveryPriceSurgeOn(final GetDeliveryPriceResponse response, final float surgeLevel, final int surgeLevelAddition) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
        softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), surgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), surgeLevelAddition, "Не верная наценка");
        softAssert.assertAll();
    }

    @Step("Проверяем отсутствие наценку по surge в цене доставки")
    public static void checkDeliveryPriceSurgeOff(final GetDeliveryPriceResponse response, final float surgeLevel, final int surgeLevelAddition) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(response.getShipments(0).getSurgeUsed(), "Surge использовался при расчете цены");
        softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), surgeLevel, "Не верный surgelevel");
        softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), surgeLevelAddition, "Не верная наценка");
        softAssert.assertAll();
    }

    @Step("Добавляем стратегию в БД")
    public static Integer addStrategy(final Boolean global, final Integer priority, final String deliveryType) {
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");
        final var strategyId = StrategiesDao.INSTANCE.addStrategy("autotest-insert", shipping, global, priority, "autotest-insert", "autotest-insert");
        Allure.step("Проверяем что стратегия добавилась", () -> assertTrue(strategyId > 0, "Пустое id стратегии"));
        return strategyId;
    }

    @Step("Добавляем правило к стратегии в БД")
    public static Integer addRule(final Integer strategyId, final String scriptName, final String scriptParams, final Integer priority, final String ruleType) {
        final var scriptId = scriptName.isEmpty() ? 0 : ScriptsDao.INSTANCE.getScriptByName(scriptName).getId();

        final var ruleId = RulesDao.INSTANCE.addRule(strategyId, scriptId, scriptParams, priority, "autotest-insert", ruleType);
        Allure.step("Проверяем что правило добавилась", () -> assertTrue(ruleId > 0, "Пустое id правила"));
        return ruleId;
    }

    @Step("Добавляем условие к правилу в БД")
    public static void addCondition(final Integer ruleId, final String params, final String conditionType) {
        final var condition = ConditionsDao.INSTANCE.addCondition(ruleId, params, conditionType);
        Allure.step("Проверяем что условие добавилась", () -> assertTrue(condition, "Условие не создалось"));
    }

    @Step("Добавляем привязку магазина к стратегии")
    public static void addBinding(final Integer strategyId, final String storeId, final String tenantId, final String deliveryType) {
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");
        final var binding = StrategyBindingsDao.INSTANCE.addStrategyBinding(strategyId, storeId, tenantId, shipping);
        Allure.step("Проверяем что связка добавилась", () -> assertTrue(binding, "Связка не добавилась"));
    }

    @Step("Добавляем автобиндер-правило в БД")
    public static Integer addBindingRule(final Integer strategyId, final String deliveryType, final String tenantId, final Integer regionId, final Integer retailerId, final Boolean ondemand, final Integer labelId, final String deletedAt) {
        final var shipping = substringToLastIndexOfStr(deliveryType, "_");
        final var bindingRuleId = BindingRulesDao.INSTANCE.addBindingRule(strategyId, shipping, tenantId, regionId, retailerId, ondemand, labelId, deletedAt);
        Allure.step("Проверяем что автобиндер-правило добавилась", () -> assertTrue(bindingRuleId > 0, "Пустое id правила"));
        return bindingRuleId;
    }

    @Step("Удаляем созданную стратегию из БД")
    public static void deleteCreatedStrategy(final Integer strategyId) {
        final var strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        final var rulesList = RulesDao.INSTANCE.getRules(strategyId);

        if (Objects.nonNull(strategy) && !rulesList.isEmpty()) {
            RulesDao.INSTANCE.delete(strategyId);
            StrategiesDao.INSTANCE.delete(strategyId);
        }
    }

    @Step("Получаем запрос для создания скрипта")
    public static CreateScriptRequest getCreateScriptRequest(final String scriptName, final String scriptBody, final String creatorId) {
        return CreateScriptRequest.newBuilder()
                .setScriptName(scriptName)
                .setScriptBody(scriptBody)
                .setCreatorId(creatorId)
                .build();
    }

    @Step("Получаем запрос для обновления скрипта")
    public static UpdateScriptRequest getUpdateScriptRequest(
            final Integer scriptId, final String scriptName, final String scriptBody, final String creatorId) {
        return UpdateScriptRequest.newBuilder()
                .setScriptId(scriptId)
                .setScriptName(scriptName)
                .setScriptBody(scriptBody)
                .setCreatorId(creatorId)
                .build();
    }

    @Step("Проверяем скрипт")
    public static void checkScript(final Integer scriptId, final String scriptName, final String state) {
        final var scriptEntity = ScriptsDao.INSTANCE.getScriptById(scriptId);

        assertNotNull(scriptEntity, "Данные из БД вернулись пустые");
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(scriptId, scriptEntity.getId(), softAssert);
        compareTwoObjects(scriptEntity.getName(), scriptName, softAssert);
        softAssert.assertNotNull(scriptEntity.getCode(), "Code вернулся null");
        softAssert.assertNotNull(scriptEntity.getRequiredParams(), "requiredParams вернулся null");
        compareTwoObjects(scriptEntity.getState(), state, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем обновленный скрипт")
    public static void checkUpdatedScript(final Integer scriptId, final String scriptName, final String state) {
        checkScript(scriptId, scriptName, state);
        final var scriptEntity = ScriptsDao.INSTANCE.getScriptById(scriptId);

        assertNotNull(scriptEntity, "Данные из БД вернулись пустые");
        assertNotEquals(scriptEntity.getCreatedAt(), scriptEntity.getUpdatedAt(), "Поле updated_at не обновилось");
    }

    @Step("Получаем запрос для удаления скрипта")
    public static DeleteScriptRequest getDeleteScriptRequest(final Integer scriptId) {
        return DeleteScriptRequest.newBuilder()
                .setScriptId(scriptId)
                .build();
    }

    @Step("Получаем запрос для получения условий доставки магазина")
    public static GetDeliveryConditionsRequest getDeliveryConditionsRequest(final String storeId, final Float storeLat, final Float storeLon, final String customerId, final String anonymousId, final Integer ordersCount, final Integer registeredAt, final Float customerLat, final Float customerLon, final String tenant, final Integer deliveryTypeValue, final String platformName, final String platformVersion, final Boolean onDemand, final Integer regionId) {
        return GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(storeId)
                        .setLat(storeLat)
                        .setLon(storeLon)
                        .setIsOndemand(onDemand)
                        .setRegionId(regionId)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(customerId)
                        .setAnonymousId(anonymousId)
                        .setOrdersCount(ordersCount)
                        .setRegisteredAt(registeredAt)
                        .setLat(customerLat)
                        .setLon(customerLon)
                        .build())
                .setTenant(tenant)
                .setDeliveryTypeValue(deliveryTypeValue)
                .setPlatformName(platformName)
                .setPlatformVersion(platformVersion)
                .build();
    }

    @Step("Проверяем условия доставки магазина")
    public static void checkDeliveryConditions(final GetDeliveryConditionsResponse response, final String storeId, final int minCartAmount, final int ladderStepCount, final int priceComponentsCount) {
        assertTrue(response.getDeliveryConditionsCount() > 0, "Пустые условия доставки");
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getDeliveryConditions(0).getStoreId(), storeId, "Не ожидаемый uuid магазина");
        softAssert.assertEquals(response.getDeliveryConditions(0).getMinCartAmount(), minCartAmount, "Не ожидаемая минимальная корзина");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadderCount(), ladderStepCount, "Не ожидаемое кол-во ступеней в лесенке");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponentsCount(), priceComponentsCount, "Не ожидаемое кол-во компонентов цены");
        softAssert.assertAll();
    }

    @Step("Проверяем базовую цену в лесенке в условиях доставки магазина")
    public static void checkDeliveryConditionsTwoStepLadder(final GetDeliveryConditionsResponse response, final int firstStepPrice, final int secondStepPrice) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), firstStepPrice, "Не ожидаемая базовая цена в лесенке");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), secondStepPrice, "Не ожидаемая базовая цена в лесенке");
        softAssert.assertAll();
    }

    @Step("Проверяем наценку в условиях доставки магазина")
    public static void checkDeliveryConditionsSurgeOn(final GetDeliveryConditionsResponse response, final float surgeLevel, final int firstStepPrice, final int secondStepPrice) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж выключен");
        softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), surgeLevel, "Не ожидаемый уровень сюрджа");
        softAssert.assertTrue(response.getDeliveryConditions(0).getSurge().getTtl() > 0, "Не ожидаемый уровень ttl сюрджа");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), firstStepPrice, "Не ожидаемая цена в лесенке");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getShippingPrice(), secondStepPrice, "Не ожидаемая цена в лесенке");
        softAssert.assertAll();
    }

    @Step("Проверяем отсутствие наценки в условиях доставки магазина")
    public static void checkDeliveryConditionsSurgeOff(final GetDeliveryConditionsResponse response, final float surgeLevel, final int firstStepPrice, final int secondStepPrice) {
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж включен");
        softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), surgeLevel, "Не ожидаемый уровень сюрджа");
        softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getTtl(), 0, "Не ожидаемый уровень ttl сюрджа");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), firstStepPrice, "Не ожидаемая цена в лесенке");
        softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getShippingPrice(), secondStepPrice, "Не ожидаемая цена в лесенке");
        softAssert.assertAll();
    }

    @Step("Получаем запрос для получения мин. корзины магазина")
    public static GetMinCartAmountsRequest getMinCartAmountsRequest(final String storeId, final Float storeLat, final Float storeLon, final String customerId, final String anonymousId, final Integer ordersCount, final Integer registeredAt, final Float customerLat, final Float customerLon, final String tenant, final Integer deliveryTypeValue, final Boolean onDemand, final Integer regionId) {
        return GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(storeId)
                        .setLat(storeLat)
                        .setLon(storeLon)
                        .setIsOndemand(onDemand)
                        .setRegionId(regionId)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(customerId)
                        .setAnonymousId(anonymousId)
                        .setOrdersCount(ordersCount)
                        .setRegisteredAt(registeredAt)
                        .setLat(customerLat)
                        .setLon(customerLon)
                        .build())
                .setTenant(tenant)
                .setDeliveryTypeValue(deliveryTypeValue)
                .build();
    }

    @Step("Проверяем мин. корзину магазина")
    public static void checkMinCartAmounts(final GetMinCartAmountsResponse response, final String storeId, final int minCartAmount) {
        assertTrue(response.getMinCartAmountsCount() > 0, "Пустая мин. корзина");
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getMinCartAmounts(0).getStoreId(), storeId, "Не ожидаемый uuid магазина");
        softAssert.assertEquals(response.getMinCartAmounts(0).getAmountCourierDelivery(), minCartAmount, "Не ожидаемая минимальная корзина");
        softAssert.assertAll();
    }

    @Step("Смотрим выключен ли сюрдж")
    public static boolean getSurgeDisabledFromK8s() {
        final var envProp = getServiceEnvProp(EnvironmentProperties.Env.SHIPPINGCALC_NAMESPACE, SHIPPINGCALC_SURGE_DISABLED.get());
        final var surgeDisabled = matchWithRegex("^\\[SURGE_DISABLED=(.\\w+)\\]$", envProp.toString(), 1);
        return surgeDisabled.equals("true");
    }
}
