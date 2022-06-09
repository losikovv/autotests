package ru.instamart.test.api.dispatch.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;
import shippingcalc.ShippingcalcOuterClass;

import static ru.instamart.api.helper.ShippingCalcHelper.*;

@Epic("On Demand")
@Feature("ShippingCalc")
public class StrategyTest extends RestBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer strategyId;
    private Integer strategyIdWithDifferentScriptsInRules;
    private Integer strategyIdWithMultipleRulesAndConditions;
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
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, 2);
        var response = clientShippingCalc.createStrategy(request);
        strategyId = response.getStrategyId();
        checkStrategy(strategyId, "autotest", 2, 2, 0);
    }

    @CaseIDs(value = {@CaseId(63), @CaseId(61)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами на разные скрипты",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategyWithDifferentScriptsInRules() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(1)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(2)
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
                .setDeliveryTypeValue(2)
                .build();

        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithDifferentScriptsInRules = response.getStrategyId();
        checkStrategy(strategyIdWithDifferentScriptsInRules, "autotest", 3, 3, 1);
    }

    @CaseIDs(value = {@CaseId(191), @CaseId(62), @CaseId(71), @CaseId(337), @CaseId(340)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategyWithMultipleRulesAndConditions() {
        ShippingcalcOuterClass.CreateStrategyRequest request = ShippingcalcOuterClass.CreateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(1)
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
                        .setScriptId(2)
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
                .build();

        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithMultipleRulesAndConditions = response.getStrategyId();
        checkStrategy(strategyIdWithMultipleRulesAndConditions, "autotest", 6, 10, 2);
    }

    @CaseId(179)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty")
    public void createStrategyWithNoName() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "", "autotest", false, 2);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(180)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии идентификатора создателя",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator id cannot be empty")
    public void createStrategyWithNoCreatorId() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "{}", 0, "", "autotest", "autotest", false, 2);
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
                .setDeliveryTypeValue(2)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(66)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при несуществующем скрипте",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: cannot check scripts existence, cannot found next scripts: 1234567890, entity not found")
    public void createStrategyWithNonExistentScript() {
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, 2);
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
                .setDeliveryTypeValue(2)
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
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1, "", 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, 2);
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
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
        ShippingcalcOuterClass.CreateStrategyRequest request = getCreateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "", 0, "autotest", "autotest", "autotest", false, 2);
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseIDs(value = {@CaseId(81), @CaseId(91), @CaseId(205), @CaseId(342), @CaseId(346)})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategy")
    public void updateStrategy() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(2, SECOND_SCRIPT_PARAMS, 100, 2, "{}", 10000, strategyId, "autotest-update", "autotest-update", "autotest-update", true, 3);
        var response = clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyId, "autotest-update", 4, 4, 2);
    }

    @CaseIDs(value = {@CaseId(94), @CaseId(92), @CaseId(334)})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с несколькими правилами на разные скрипты",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithDifferentScriptsInRules")
    public void updateStrategyWithDifferentScriptsInRules() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(2)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(1)
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
                .setDeliveryTypeValue(3)
                .build();

        var response = clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithDifferentScriptsInRules, "autotest-update", 6, 6, 3);
    }

    @CaseIDs(value = {@CaseId(206), @CaseId(93), @CaseId(102), @CaseId(344), @CaseId(347)})
    @Story("Update Strategy")
    @Test(description = "Обновление стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithMultipleRulesAndConditions")
    public void updateStrategyWithMultipleRulesAndConditions() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = ShippingcalcOuterClass.UpdateStrategyRequest.newBuilder()
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(2)
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
                        .setScriptId(1)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(2)
                        .addConditions(ShippingcalcOuterClass.NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(ShippingcalcOuterClass.NewRuleObject.newBuilder()
                        .setScriptId(1)
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
                .setDeliveryTypeValue(3)
                .build();

        var response = clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithMultipleRulesAndConditions, "autotest-update", 12, 20, 11);
    }

    @CaseId(192)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoName() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "{}", 0, strategyId, "autotest-update", "", "autotest-update", false, 2);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseIDs(value = {@CaseId(83), @CaseId(204)})
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: cannot check existence of strategy, entity not found")
    public void updateStrategyWithNonExistentStrategyId() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "{}", 0, 1234567890, "autotest-update", "autotest-update", "autotest-update", false, 2);
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
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "{}", 0, strategyId, "", "autotest-update", "autotest-update", false, 2);
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
                .setDeliveryTypeValue(2)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(97)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении существующей стратегии с правилом на несуществующий скрипт",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: cannot check scripts existence, cannot found next scripts: 1234567890, entity not found",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNonExistentScriptId() {
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, "{}", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, 2);
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
                .setDeliveryTypeValue(2)
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
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1, "", 0, 2, "{}", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, 2);
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
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
        ShippingcalcOuterClass.UpdateStrategyRequest request = getUpdateStrategyRequest(1, SCRIPT_PARAMS, 0, 2, "", 0, strategyId, "autotest-update", "autotest-update", "autotest-update", false, 2);
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
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
                        .setScriptId(1)
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
                .setDeliveryTypeValue(2)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(strategyId);
        deleteCreatedStrategy(strategyIdWithDifferentScriptsInRules);
        deleteCreatedStrategy(strategyIdWithMultipleRulesAndConditions);
    }
}
