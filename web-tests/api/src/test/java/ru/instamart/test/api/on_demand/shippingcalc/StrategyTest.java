package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.RulesDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.jdbc.entity.shippingcalc.StrategiesEntity;
import ru.instamart.kraken.enums.Tenant;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;
import shippingcalc.ShippingcalcOuterClass;

import java.util.List;
import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.ShippingCalcHelper.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class StrategyTest extends RestBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer strategyId;
    private Integer strategyIdWithDifferentScriptsInRules;
    private Integer strategyIdWithMultipleRulesAndConditions;
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final Integer FIRST_SCRIPT_ID = 8;
    private final Integer SECOND_SCRIPT_ID = 9;
    private final String SCRIPT_PARAMS = "{\"basicPrice\": \"10000\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";
    private final String FIRST_SCRIPT_PARAMS = "{\"basicPrice\": \"0\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";
    private final String SECOND_SCRIPT_PARAMS = "{\"baseMass\": \"30000\", \"basicPrice\": \"29900\", \"bagIncrease\": \"0\", \"basePositions\": \"100\", \"additionalMass\": \"1000\", \"assemblyIncrease\": \"0\", \"additionalPositions\": \"5\", \"additionalMassIncrease\": \"500\", \"additionalPositionsIncrease\": \"0\"}";
    private final String FIRST_PARAMS = "{\"Count\": 1}";
    private final String THIRD_PARAMS = "{\"Test\": \"shippingcalc_test\", \"Group\": \"control\"}";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
    }

    @CaseIDs(value = {@CaseId(46), @CaseId(60), @CaseId(74), @CaseId(335), @CaseId(339), @CaseId(333)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategy() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        var response = clientShippingCalc.createStrategy(request);
        strategyId = response.getStrategyId();
        checkStrategy(strategyId, "autotest", 2, 2, 0, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(63), @CaseId(61)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами на разные скрипты",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategyWithDifferentScriptsInRules() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.B2B_VALUE)
                .build();

        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithDifferentScriptsInRules = response.getStrategyId();
        checkStrategy(strategyIdWithDifferentScriptsInRules, "autotest", 3, 3, 1, ShippingcalcOuterClass.DeliveryType.B2B.toString());
    }

    @CaseIDs(value = {@CaseId(191), @CaseId(62), @CaseId(71), @CaseId(337), @CaseId(340)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategyWithMultipleRulesAndConditions() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(2)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(150000)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(2)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithMultipleRulesAndConditions = response.getStrategyId();
        checkStrategy(strategyIdWithMultipleRulesAndConditions, "autotest", 6, 10, 2, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(378)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки валидации параметров скрипта при создании стратегии",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: no required for script parameters")
    public void createStrategyNonValidScriptParams() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(FIRST_SCRIPT_ID, "{}", 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(179)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty")
    public void createStrategyWithNoName() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(180)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии идентификатора создателя",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator id cannot be empty")
    public void createStrategyWithNoCreatorId() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, "", "autotest", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(183)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии правил стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rules cannot be empty")
    public void createStrategyWithNoRules() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(66)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при несуществующем скрипте",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: scriptId 1234567890: entity not found")
    public void createStrategyWithNonExistentScript() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(184)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 unacceptable without valid script id")
    public void createStrategyWithNoScriptId() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(186)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии параметров скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has invalid params")
    public void createStrategyWithNoScriptParams() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(FIRST_SCRIPT_ID, "", 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(187)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has no conditions")
    public void createStrategyWithNoRuleConditions() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(188)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии параметров условий",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has invalid condition 0, invalid params")
    public void createStrategyWithNoConditionParams() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "", 0, "autotest", "autotest", "autotest", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(336)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии правила на минимальную корзину",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: min cart rules cannot be empty")
    public void createStrategyWithNoMinCartRule() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(338)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии условий минимальной корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has no conditions")
    public void createStrategyWithNoMinCartRuleConditions() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseIDs(value = {@CaseId(81), @CaseId(91), @CaseId(205), @CaseId(342), @CaseId(346), @CaseId(363)})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategy")
    public void updateStrategy() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 2, "{}", 10000, strategyId, "autotest-update", "autotest-update", "autotest-update", true, ShippingcalcOuterClass.DeliveryType.B2B_VALUE);
        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyId, "autotest-update", 4, 4, 2, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(94), @CaseId(92), @CaseId(334)})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с несколькими правилами на разные скрипты",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithDifferentScriptsInRules")
    public void updateStrategyWithDifferentScriptsInRules() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(10000)
                        .setPriority(2)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(true)
                .setPriority(100)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.B2B_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithDifferentScriptsInRules, "autotest-update", 6, 6, 3, ShippingcalcOuterClass.DeliveryType.B2B.toString());
    }

    @CaseIDs(value = {@CaseId(206), @CaseId(93), @CaseId(102), @CaseId(344), @CaseId(347)})
    @Story("Update Strategy")
    @Test(description = "Обновление стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithMultipleRulesAndConditions")
    public void updateStrategyWithMultipleRulesAndConditions() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(2)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(2)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(150000)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyIdWithMultipleRulesAndConditions)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(true)
                .setPriority(100)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithMultipleRulesAndConditions, "autotest-update", 12, 20, 11, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(379)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки валидации параметров скрипта при обновлении стратегии",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: no required for script parameters",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyNonValidScriptParams() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, "{}", 0, 2, "{}", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(192)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoName() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, strategyId, "autotest-update", "", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseIDs(value = {@CaseId(83), @CaseId(204)})
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: cannot check existence of strategy, entity not found")
    public void updateStrategyWithNonExistentStrategyId() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, 1234567890, "autotest-update", "autotest-update", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(193)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии идентификатора создателя",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator id cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoCreatorId() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, strategyId, "", "autotest-update", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(196)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии правил стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rules cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoRules() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(97)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении существующей стратегии с правилом на несуществующий скрипт",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: scriptId 1234567890: entity not found",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNonExistentScriptId() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, "{}", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(197)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 unacceptable without valid script id",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoScriptId() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(199)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has invalid params",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoScriptParams() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, "", 0, 2, "{}", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(200)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has no conditions",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoRuleConditions() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(201)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии параметров условий",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has invalid condition 0, invalid params",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoConditionParams() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(343)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии правила на минимальную корзину",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: min cart rules cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoMinCartRules() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(345)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии условий минимальной корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has no conditions",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoMinCartRuleConditions() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(ShippingcalcOuterClass.MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .build())
                .setStrategyId(strategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(138)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategy")
    public void bindStrategy() {
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(strategyId, FIRST_STORE_ID, Tenant.METRO.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
        checkBind(strategyId, FIRST_STORE_ID, Tenant.METRO.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(141)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к нескольким магазинам в массиве binds",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithDifferentScriptsInRules")
    public void bindStrategyMultipleStores() {
        ShippingcalcOuterClass.BindStrategyRequest request = ShippingcalcOuterClass.BindStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(FIRST_STORE_ID)
                        .setTenantId(Tenant.SBERMARKET.getId())
                        .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.B2B_VALUE)
                        .build())
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.OKEY.getId())
                        .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.B2B_VALUE)
                        .build())
                .build();

        clientShippingCalc.bindStrategy(request);
        checkBind(strategyIdWithDifferentScriptsInRules, FIRST_STORE_ID, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.B2B.toString());
        checkBind(strategyIdWithDifferentScriptsInRules, SECOND_STORE_ID, Tenant.OKEY.getId(), ShippingcalcOuterClass.DeliveryType.B2B.toString());
    }

    @CaseId(138)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину, у которого уже есть связка",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "bindStrategyMultipleStores")
    public void rebindStrategy() {
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.OKEY.getId(), ShippingcalcOuterClass.DeliveryType.B2B_VALUE, false);
        clientShippingCalc.bindStrategy(request);
        checkBind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.OKEY.getId(), ShippingcalcOuterClass.DeliveryType.B2B.toString());
    }

    @CaseId(366)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину с флагом replace_all",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithMultipleRulesAndConditions")
    public void bindStrategyWithReplaceAll() {
        addBinding(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
        addBinding(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.INSTAMART.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.METRO.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, true);

        clientShippingCalc.bindStrategy(request);

        checkBind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.METRO.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.INSTAMART.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(348)
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без id стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid StrategyId")
    public void bindStrategyWithNoStrategyId() {
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(0, FIRST_STORE_ID, "test", ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(349)
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без сущности binds",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty binds",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoBinds() {
        ShippingcalcOuterClass.BindStrategyRequest request = ShippingcalcOuterClass.BindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .build();

        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(144)
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без магазина",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot attach bind 0 without store id",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoStoreId() {
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(strategyId, "", "test", ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(146)
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без магазина",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot attach bind 0 without tenant id",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoTenantId() {
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(strategyId, FIRST_STORE_ID, "", ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(364)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину без типа доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoDeliveryType() {
        ShippingcalcOuterClass.BindStrategyRequest request = ShippingcalcOuterClass.BindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.OKEY.getId())
                        .build())
                .setReplaceAll(false)
                .build();

        clientShippingCalc.bindStrategy(request);
        checkBind(strategyId, SECOND_STORE_ID, Tenant.OKEY.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(376)
    @Story("Bind Strategy")
    @Test(description = "Получение ошибки при привязке к удаленной стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot bind strategy: entity not found",
            dependsOnMethods = "deleteStrategy")
    public void bindStrategyDeleted() {
        ShippingcalcOuterClass.BindStrategyRequest request = getBindStrategyRequest(strategyIdWithDifferentScriptsInRules, FIRST_STORE_ID, Tenant.SELGROS.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(155)
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от магазина с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = {"getStrategy", "getStrategiesWithAllFilter", "getStrategiesWithStoreFilter", "getStrategiesForStore"})
    public void unbindStrategy() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = getUnbindStrategyRequest(strategyId, FIRST_STORE_ID, Tenant.METRO.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
        checkUnbind(strategyId, FIRST_STORE_ID, Tenant.METRO.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(158)
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от нескольких магазинов в массиве binds",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = {"getStrategy", "bindStrategyMultipleStores"})
    public void unbindStrategyMultipleStores() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = ShippingcalcOuterClass.UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(FIRST_STORE_ID)
                        .setTenantId(Tenant.SBERMARKET.getId())
                        .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.B2B_VALUE)
                        .build())
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.INSTAMART.getId())
                        .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.B2B_VALUE)
                        .build())
                .build();

        clientShippingCalc.unbindStrategy(request);
        checkUnbind(strategyIdWithDifferentScriptsInRules, FIRST_STORE_ID, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.B2B.toString());
        checkUnbind(strategyIdWithDifferentScriptsInRules, SECOND_STORE_ID, Tenant.INSTAMART.getId(), ShippingcalcOuterClass.DeliveryType.B2B.toString());
    }

    @CaseId(159)
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без id стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid StrategyId")
    public void unbindStrategyWithNoStrategyId() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = getUnbindStrategyRequest(0, FIRST_STORE_ID, "test", ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @CaseId(350)
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без сущности binds",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty binds",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyWithNoBinds() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = ShippingcalcOuterClass.UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .build();

        clientShippingCalc.unbindStrategy(request);
    }

    @CaseId(161)
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без магазина",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot use bind 0 without store id",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyWithNoStoreId() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = getUnbindStrategyRequest(strategyId, "", "test", ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @CaseId(163)
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без тенанта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot use bind 0 without tenant id",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyWithNoTenantId() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = getUnbindStrategyRequest(strategyId, FIRST_STORE_ID, "", ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @CaseId(365)
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от магазина без типа доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategyWithNoDeliveryType")
    public void unbindStrategyWithNoDeliveryType() {
        ShippingcalcOuterClass.UnbindStrategyRequest request = ShippingcalcOuterClass.UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .addBinds(ShippingcalcOuterClass.StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.OKEY.getId())
                        .build())
                .build();

        clientShippingCalc.unbindStrategy(request);
        checkUnbind(strategyId, SECOND_STORE_ID, Tenant.OKEY.getId(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(118)
    @Story("Get Strategy")
    @Test(description = "Получение существующей стратегии",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "bindStrategy")
    public void getStrategy() {
        ShippingcalcOuterClass.GetStrategyRequest request = ShippingcalcOuterClass.GetStrategyRequest.newBuilder()
                .setStrategyId(strategyId)
                .build();
        var response = clientShippingCalc.getStrategy(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(response.getStrategy(), "В ответе пустая стратегия");
            compareTwoObjects(response.getStrategy().getStrategyId(), strategyId, softAssert);
            softAssert.assertTrue(response.getStrategy().getBindsCount() > 0, "В ответе пустой список привязок");
            softAssert.assertTrue(response.getRulesCount() > 0, "В ответе пустой список правил");
            softAssert.assertTrue(response.getRules(0).getConditionsCount() > 0, "В ответе пустой список условий для правил");
            softAssert.assertTrue(response.getMinCartRulesCount() > 0, "В ответе пустой список правил мин. корзины");
            softAssert.assertTrue(response.getMinCartRules(0).getConditionsCount() > 0, "В ответе пустой список условий для правил мин. корзины");
            softAssert.assertAll();
        });
    }

    @CaseId(120)
    @Story("Get Strategy")
    @Test(description = "Получение ошибки при отправке запроса с несуществующей стратегией",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot find strategy, entity not found")
    public void getStrategyNonExistent() {
        ShippingcalcOuterClass.GetStrategyRequest request = ShippingcalcOuterClass.GetStrategyRequest.newBuilder()
                .setStrategyId(1234567890)
                .build();

        clientShippingCalc.getStrategy(request);
    }

    @CaseId(375)
    @Story("Get Strategy")
    @Test(description = "Получение ошибки при запросе удаленной стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot find strategy, entity not found",
            dependsOnMethods = "deleteStrategy")
    public void getStrategyDeleted() {
        ShippingcalcOuterClass.GetStrategyRequest request = ShippingcalcOuterClass.GetStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .build();

        clientShippingCalc.getStrategy(request);
    }

    @CaseId(124)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий без фильтров",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategy")
    public void getStrategies() {
        ShippingcalcOuterClass.GetStrategiesRequest request = ShippingcalcOuterClass.GetStrategiesRequest.newBuilder().build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertNotNull(response.getStrategies(0).getName(), "В ответе пустое название стратегии");
            softAssert.assertTrue(response.getStrategies(0).getDeliveryTypeValue() > 0, "В ответе пустой тип доставки");
            softAssert.assertAll();
        });
    }

    @CaseId(256)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с комбинированным фильтром (имя, тип доставки, магазины)",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesWithAllFilter() {
        ShippingcalcOuterClass.GetStrategiesRequest request = ShippingcalcOuterClass.GetStrategiesRequest.newBuilder()
                .setStrategyName("aut")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .addStores(FIRST_STORE_ID)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertTrue(response.getStrategies(0).getName().contains("autotest"), "Не ожидаемое название стратегии");
            softAssert.assertTrue(response.getStrategies(0).getStoresCount() > 0, "В ответе пустой список привязок");
            softAssert.assertTrue(response.getStrategies(0).getStoresList().contains(FIRST_STORE_ID), "В ответе не нашли ожидаемую привязку");
            softAssert.assertEquals(response.getStrategies(0).getDeliveryTypeValue(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, "Не ожидаемый тип доставки");
            softAssert.assertAll();
        });
    }

    @CaseId(251)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по имени",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createStrategy")
    public void getStrategiesWithNameFilter() {
        ShippingcalcOuterClass.GetStrategiesRequest request = ShippingcalcOuterClass.GetStrategiesRequest.newBuilder()
                .setStrategyName("aut")
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertTrue(response.getStrategies(0).getName().contains("autotest"), "Не ожидаемое название стратегии");
            softAssert.assertAll();
        });
    }

    @CaseId(253)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по типу доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createStrategy")
    public void getStrategiesWithDeliveryTypeFilter() {
        ShippingcalcOuterClass.GetStrategiesRequest request = ShippingcalcOuterClass.GetStrategiesRequest.newBuilder()
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertEquals(response.getStrategies(0).getDeliveryTypeValue(), ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE, "Не ожидаемый тип доставки");
            softAssert.assertAll();
        });
    }

    @CaseId(254)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по магазину",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesWithStoreFilter() {
        ShippingcalcOuterClass.GetStrategiesRequest request = ShippingcalcOuterClass.GetStrategiesRequest.newBuilder()
                .addStores(FIRST_STORE_ID)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertTrue(response.getStrategies(0).getStoresCount() > 0, "В ответе пустой список привязок");
            softAssert.assertTrue(response.getStrategies(0).getStoresList().contains(FIRST_STORE_ID), "В ответе не нашли ожидаемую привязку");
            softAssert.assertAll();
        });
    }

    @CaseId(172)
    @Story("Get Strategies For Store")
    @Test(description = "Получение стратегии для магазина",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesForStore() {
        ShippingcalcOuterClass.GetStrategiesForStoreRequest request = ShippingcalcOuterClass.GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategy(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertEquals(response.getStrategy(0).getBinding().getStoreId(), FIRST_STORE_ID, "В ответе не нашли ожидаемую привязку");
            softAssert.assertEquals(response.getStrategy(0).getBinding().getTenantId(), Tenant.METRO.getId(), "В ответе не нашли ожидаемый тенант");
            softAssert.assertAll();
        });
    }

    @CaseId(175)
    @Story("Get Strategies For Store")
    @Test(description = "Получение пустого списка при отсутствии искомой стратегии",
            groups = "dispatch-shippingcalc-regress")
    public void getStrategiesForStoreNotFound() {
        ShippingcalcOuterClass.GetStrategiesForStoreRequest request = ShippingcalcOuterClass.GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant("test")
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегии в ответе", () -> compareTwoObjects(response.getStrategyCount(), 0));
    }

    @CaseId(351)
    @Story("Get Strategies For Store")
    @Test(description = "Получение ошибки при отсутствии магазина",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store id")
    public void getStrategiesForStoreWithoutStoreId() {
        ShippingcalcOuterClass.GetStrategiesForStoreRequest request = ShippingcalcOuterClass.GetStrategiesForStoreRequest.newBuilder()
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.getStrategiesForStore(request);
    }

    @CaseId(362)
    @Story("Get Strategies For Store")
    @Test(description = "Получение стратегий при отсутствии тенанта и способа доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategyMultipleStores")
    public void getStrategiesForStoreWithoutTenantAndDeliveryType() {
        ShippingcalcOuterClass.GetStrategiesForStoreRequest request = ShippingcalcOuterClass.GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .build();

        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегий в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategyCount() > 1, "В ответе не ожидамое кол-во стратегий");
            softAssert.assertTrue(response.getStrategy(0).getBinding().getStoreId().equals(FIRST_STORE_ID) && response.getStrategy(1).getBinding().getStoreId().equals(FIRST_STORE_ID), "В ответе не нашли ожидаемых привязок");
            softAssert.assertNotEquals(response.getStrategy(0).getStrategyId(), response.getStrategy(1).getStrategyId(), "В ответе не нашли ожидаемые id стратегий");
            softAssert.assertNotEquals(response.getStrategy(0).getBinding().getTenantId(), response.getStrategy(1).getBinding().getTenantId(), "В ответе не нашли ожидаемых тенантов");
            softAssert.assertNotEquals(response.getStrategy(0).getBinding().getDeliveryType(), response.getStrategy(1).getBinding().getDeliveryType(), "В ответе не нашли ожидаемых типов доставки");
            softAssert.assertAll();
        });
    }

    @CaseId(353)
    @Story("Get Strategies For Store")
    @Test(description = "Получение ошибки при не валидном способе доставки",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid delivery type")
    public void getStrategiesForStoreWithoutDeliveryType() {
        ShippingcalcOuterClass.GetStrategiesForStoreRequest request = ShippingcalcOuterClass.GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(99)
                .build();

        clientShippingCalc.getStrategiesForStore(request);
    }

    @CaseId(367)
    @Story("Delete Strategy")
    @Test(description = "Удаление стратегии без биндов",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "unbindStrategyMultipleStores")
    public void deleteStrategy() {
        ShippingcalcOuterClass.DeleteStrategyRequest request = getDeleteStrategyRequest(strategyIdWithDifferentScriptsInRules);
        var response = clientShippingCalc.deleteStrategy(request);

        StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(strategyIdWithDifferentScriptsInRules);
        List<RulesEntity> rules = RulesDao.INSTANCE.getRules(strategyIdWithDifferentScriptsInRules);
        List<RulesEntity> deletedRules = RulesDao.INSTANCE.getDeletedRules(strategyIdWithDifferentScriptsInRules);
        Allure.step("Проверка удаления стратегии", () -> {
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(response.toString(), "", softAssert);
            compareTwoObjects(rules.size(), deletedRules.size(), softAssert);
            softAssert.assertNotNull(strategy.getDeletedAt(), "Стратегия не помечена удаленной");
            softAssert.assertAll();
        });
    }

    @CaseId(369)
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки при удалении стратегии с биндами",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "UNKNOWN: usecase: delete strategy [0-9]+ is imposible, bindings must be empty",
            dependsOnMethods = "rebindStrategy")
    public void deleteStrategyWithBind() {
        ShippingcalcOuterClass.DeleteStrategyRequest request = getDeleteStrategyRequest(strategyIdWithMultipleRulesAndConditions);
        clientShippingCalc.deleteStrategy(request);
    }

    @CaseId(372)
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки при удалении несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "UNKNOWN: usecase: delele strategy 1234567890: entity not found")
    public void deleteStrategyNonExistent() {
        ShippingcalcOuterClass.DeleteStrategyRequest request = getDeleteStrategyRequest(1234567890);
        clientShippingCalc.deleteStrategy(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(strategyId);
        deleteCreatedStrategy(strategyIdWithDifferentScriptsInRules);
        deleteCreatedStrategy(strategyIdWithMultipleRulesAndConditions);
    }
}
