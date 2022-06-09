package ru.instamart.api.helper;

import io.qameta.allure.Step;
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
    public static ShippingcalcOuterClass.BindStrategyRequest getBindStrategyRequest(Integer strategyId, String storeId, String tenantId) {
        return ShippingcalcOuterClass.BindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(storeId)
                        .setTenantId(tenantId)
                        .build())
                .build();
    }

    @Step("Получаем запрос для отвязки стратегии к магазину")
    public static ShippingcalcOuterClass.UnbindStrategyRequest getUnbindStrategyRequest(Integer strategyId, String storeId, String tenantId) {
        return ShippingcalcOuterClass.UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(storeId)
                        .setTenantId(tenantId)
                        .build())
                .build();
    }

    @Step("Проверяем стратегию")
    public static void checkStrategy(Integer strategyId, String strategyName, Integer rulesAmount, Integer conditionsAmount, Integer conditionIndex) {
        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        List<RulesEntity> rules = RulesDao.INSTANCE.getRules(strategyId);
        ArrayList<Integer> rulesIds = new ArrayList();
        for (RulesEntity rule : rules) rulesIds.add(rule.getId());
        List<ConditionsEntity> conditions = ConditionsDao.INSTANCE.getConditions(rulesIds);

        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(strategyId, strategy.getId(), softAssert);
        compareTwoObjects(rules.size(), rulesAmount, softAssert);
        compareTwoObjects(conditions.size(), conditionsAmount, softAssert);
        compareTwoObjects(strategy.getName(), strategyName, softAssert);
        compareTwoObjects(conditions.get(conditionIndex).getParams(), "{\"Max\": 1000000000000000, \"Min\": 0}", softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем обновленную стратегию")
    public static void checkUpdatedStrategy(Integer strategyId, String strategyName, Integer rulesAmount, Integer conditionsAmount, Integer conditionIndex) {
        checkStrategy(strategyId, strategyName, rulesAmount, conditionsAmount, conditionIndex);
        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(strategyId);
        List<RulesEntity> deletedRules = RulesDao.INSTANCE.getDeletedRules(strategyId);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertFalse(strategy.getUpdatedAt().equals(strategy.getCreatedAt()), "Поле updated_at не обновилось");
        compareTwoObjects(deletedRules.size(), rulesAmount / 2, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем связку стратегии к магазину")
    public static void checkBind(Integer strategyId, String storeId, String tenantId) {
        StrategyBindingsEntity bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(bind, "Не нашли такую связку");
        compareTwoObjects(bind.getStrategyId(), strategyId, softAssert);
        softAssert.assertAll();
    }

    @Step("Проверяем отвязку стратегии от магазина")
    public static void checkUnbind(Integer strategyId, String storeId, String tenantId) {
        StrategyBindingsEntity bind = StrategyBindingsDao.INSTANCE.getStrategyBinding(strategyId, storeId, tenantId);

        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNull(bind, "Связка не удалилась");
        softAssert.assertAll();
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
