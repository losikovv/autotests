package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.RulesDao;
import ru.instamart.jdbc.dao.shippingcalc.ScriptsDao;
import ru.instamart.jdbc.dao.shippingcalc.StrategiesDao;
import ru.instamart.jdbc.entity.shippingcalc.RulesEntity;
import ru.instamart.jdbc.entity.shippingcalc.StrategiesEntity;
import ru.instamart.kraken.enums.Tenant;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.*;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.ShippingCalcHelper.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class StrategyTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer localStrategyId;
    private Integer globalStrategyId;
    private Integer strategyIdWithDifferentScriptsInRules;
    private Integer strategyIdWithMultipleRulesAndConditions;
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final Integer FIRST_SCRIPT_ID = ScriptsDao.INSTANCE.getScriptByName(FIXED_SCRIPT_NAME).getId();
    private final Integer SECOND_SCRIPT_ID = ScriptsDao.INSTANCE.getScriptByName(COMPLEX_SCRIPT_NAME).getId();
    private final String SCRIPT_PARAMS = String.format(FIXED_SCRIPT_PARAMS, 10000);
    private final String FIRST_SCRIPT_PARAMS = String.format(FIXED_SCRIPT_PARAMS, 0);
    private final String SECOND_SCRIPT_PARAMS = String.format(COMPLEX_SCRIPT_PARAMS, 29900);
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
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        var response = clientShippingCalc.createStrategy(request);
        localStrategyId = response.getStrategyId();
        checkStrategy(localStrategyId, "autotest", 2, 2, 0, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(63), @CaseId(61), @CaseId(445)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами на разные скрипты",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyGlobal")
    public void createStrategyWithDifferentScriptsInRules() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithDifferentScriptsInRules = response.getStrategyId();
        checkStrategy(strategyIdWithDifferentScriptsInRules, "autotest", 3, 3, 1, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(191), @CaseId(62), @CaseId(71), @CaseId(337), @CaseId(340)})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategyWithMultipleRulesAndConditions() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(2)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(150000)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(2)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithMultipleRulesAndConditions = response.getStrategyId();
        checkStrategy(strategyIdWithMultipleRulesAndConditions, "autotest", 6, 10, 2, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(446)
    @Story("Create Strategy")
    @Test(description = "Создание глобальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void createStrategyGlobal() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 0, "{}", 0, "autotest", "autotest", "autotest", true, DeliveryType.SELF_DELIVERY_VALUE);
        var response = clientShippingCalc.createStrategy(request);
        globalStrategyId = response.getStrategyId();
        checkGlobalStrategy(globalStrategyId, "autotest", 2, 2, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(434)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании глобальной стратегии для типа доставки, который уже имеет глобальную стратегию",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyGlobal",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "ALREADY_EXISTS: global strategy for current shipping method already exists, entity already exists")
    public void createStrategyGlobalAlreadyExists() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 0, "{}", 0, "autotest", "autotest", "autotest", true, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(378)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки валидации параметров скрипта при создании стратегии",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: no required for script parameters")
    public void createStrategyNonValidScriptParams() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, "{}", 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(430)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании локальной стратегии, если у правил мин. корзины нет условия Always",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: strategy must contain at least one min cart rule with only always condition")
    public void createStrategyNoAlwaysMinCart() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(432)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании глобальной стратегии, если отсутствует условие Always для правил мин. корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: strategy must contain at least one min cart rule with only always condition")
    public void createStrategyGlobalNoAlwaysMinCart() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(true)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(435)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании глобальной стратегии, если отсутствует условие Always для правил цены",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: invalid rules list for global strategy, global strategy must contain at least one rule with only always condition")
    public void createStrategyGlobalNoAlwaysPriceRule() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(true)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(179)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty")
    public void createStrategyWithNoName() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(180)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии идентификатора создателя",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator id cannot be empty")
    public void createStrategyWithNoCreatorId() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, "", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(183)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии правил стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rules cannot be empty")
    public void createStrategyWithNoRules() {
        var request = CreateStrategyRequest.newBuilder()
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = getCreateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(184)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии скрипта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 unacceptable without valid script id")
    public void createStrategyWithNoScriptId() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, "", 0, 2, "{}", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(187)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has no conditions")
    public void createStrategyWithNoRuleConditions() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @CaseId(336)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии правила на минимальную корзину",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: min cart rules cannot be empty")
    public void createStrategyWithNoMinCartRule() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(379)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки валидации параметров скрипта при обновлении стратегии",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: no required for script parameters",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyNonValidScriptParams() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, "{}", 0, 2, "{}", 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(401)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: rules: priority 0 conflict")
    public void createStrategyWithNonUniqueRulesPriority() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(402)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах минимальной корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: priority 0 conflict")
    public void createStrategyWithNonUniqueMinCartRulesPriority() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseId(403)
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не уникальном значении минимальной корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: mincartvalue 50000 conflict")
    public void createStrategyWithNonUniqueMinCartRulesValue() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setCreatorId("autotest")
                .setName("autotest")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.createStrategy(request);
    }

    @CaseIDs(value = {@CaseId(81), @CaseId(91), @CaseId(205), @CaseId(342), @CaseId(346), @CaseId(363)})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategy")
    public void updateStrategy() {
        var request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 2, "{}", 10000, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.B2B_VALUE);
        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(localStrategyId, "autotest-update", 4, 4, 2, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(94), @CaseId(92), @CaseId(334)})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с несколькими правилами на разные скрипты",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithDifferentScriptsInRules")
    public void updateStrategyWithDifferentScriptsInRules() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(10000)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(100)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithDifferentScriptsInRules, "autotest-update", 6, 6, 3, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(206), @CaseId(93), @CaseId(102), @CaseId(344), @CaseId(347)})
    @Story("Update Strategy")
    @Test(description = "Обновление стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithMultipleRulesAndConditions")
    public void updateStrategyWithMultipleRulesAndConditions() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(2)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(2)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(1)
                                .setParams(FIRST_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(150000)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(3)
                                .setParams(THIRD_PARAMS)
                                .build())
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(strategyIdWithMultipleRulesAndConditions)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(100)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithMultipleRulesAndConditions, "autotest-update", 12, 20, 11, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(442)
    @Story("Update Strategy")
    @Test(description = "Нельзя изменить поле global, при обновлении стратегии",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyGlobal")
    public void updateStrategyGlobal() {
        var request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 0, "{}", 10000, globalStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
        checkUpdatedGlobalStrategy(globalStrategyId, "autotest-update", 4, 4, DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(431)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении локальной стратегии, если отсутствует правило мин. корзины с условием Always",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: strategy must contain at least one min cart rule with only always condition",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoAlwaysMinCart() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(433)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении глобальной стратегии, если отсутствует правило мин. корзины с условием Always",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: strategy must contain at least one min cart rule with only always condition",
            dependsOnMethods = "createStrategyGlobal")
    public void updateStrategyGlobalWithNoAlwaysMinCart() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(globalStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(true)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(436)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении глобальной стратегии, если отсутствует правило цены с условием Always",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: invalid rules list for global strategy, global strategy must contain at least one rule with only always condition",
            dependsOnMethods = "createStrategyGlobal")
    public void updateStrategyGlobalWithNoAlwaysPriceRule() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(FIRST_SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(0)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(globalStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(true)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
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
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, localStrategyId, "autotest-update", "", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @CaseIDs(value = {@CaseId(83), @CaseId(204)})
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: cannot check existence of strategy, entity not found")
    public void updateStrategyWithNonExistentStrategyId() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, 1234567890, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{}", 0, localStrategyId, "", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = UpdateStrategyRequest.newBuilder()
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = getUpdateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, "{}", 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, "", 0, 2, "{}", 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "", 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(404)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: rules: priority 0 conflict",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNonUniqueRulesPriority() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(405)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах минимальной корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: priority 0 conflict",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNonUniqueMinCartRulesPriority() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(406)
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не уникальном значении минимальной корзины",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: validation: mincartrules: mincartvalue 50000 conflict",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNonUniqueMinCartRulesValue() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams("{}")
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setGlobal(false)
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @CaseId(138)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategy")
    public void bindStrategy() {
        var request = getBindStrategyRequest(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
        checkBind(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(141)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к нескольким магазинам в массиве binds",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithDifferentScriptsInRules")
    public void bindStrategyMultipleStores() {
        var request = BindStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(FIRST_STORE_ID)
                        .setTenantId(Tenant.SBERMARKET.getId())
                        .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                        .build())
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.OKEY.getId())
                        .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                        .build())
                .build();

        clientShippingCalc.bindStrategy(request);
        checkBind(strategyIdWithDifferentScriptsInRules, FIRST_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.SELF_DELIVERY.toString());
        checkBind(strategyIdWithDifferentScriptsInRules, SECOND_STORE_ID, Tenant.OKEY.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(139)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину, у которого уже есть связка",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = {"createStrategy", "createStrategyWithMultipleRulesAndConditions"})
    public void rebindStrategy() {
        addBinding(localStrategyId, SECOND_STORE_ID, Tenant.SELGROS.getId(), DeliveryType.SELF_DELIVERY.toString());
        var request = getBindStrategyRequest(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.SELGROS.getId(), DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);

        checkBind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.SELGROS.getId(), DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(localStrategyId, SECOND_STORE_ID, Tenant.SELGROS.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(366)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину с флагом replace_all",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "createStrategyWithMultipleRulesAndConditions")
    public void bindStrategyWithReplaceAll() {
        addBinding(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.SELF_DELIVERY.toString());
        addBinding(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.INSTAMART.getId(), DeliveryType.SELF_DELIVERY.toString());
        var request = getBindStrategyRequest(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY_VALUE, true);

        clientShippingCalc.bindStrategy(request);

        checkBind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(strategyIdWithMultipleRulesAndConditions, SECOND_STORE_ID, Tenant.INSTAMART.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(348)
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без id стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid StrategyId")
    public void bindStrategyWithNoStrategyId() {
        var request = getBindStrategyRequest(0, FIRST_STORE_ID, "test", DeliveryType.SELF_DELIVERY_VALUE, false);
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
        var request = BindStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
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
        var request = getBindStrategyRequest(localStrategyId, "", "test", DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(146)
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без тенанта",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot attach bind 0 without tenant id",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoTenantId() {
        var request = getBindStrategyRequest(localStrategyId, FIRST_STORE_ID, "", DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(364)
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину без типа доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoDeliveryType() {
        var request = BindStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.AUCHAN.getId())
                        .build())
                .setReplaceAll(false)
                .build();

        clientShippingCalc.bindStrategy(request);
        checkBind(localStrategyId, SECOND_STORE_ID, Tenant.AUCHAN.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(376)
    @Story("Bind Strategy")
    @Test(description = "Получение ошибки при привязке к удаленной стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot bind strategy: entity not found",
            dependsOnMethods = "deleteStrategy")
    public void bindStrategyDeleted() {
        var request = getBindStrategyRequest(strategyIdWithDifferentScriptsInRules, FIRST_STORE_ID, Tenant.SELGROS.getId(), DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @CaseId(155)
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от магазина с валидными данными",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = {"getStrategy", "getStrategiesWithAllFilter", "getStrategiesWithStoreFilter", "getStrategiesForStore"})
    public void unbindStrategy() {
        var request = getUnbindStrategyRequest(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
        checkUnbind(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(158)
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от нескольких магазинов в массиве binds",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = {"getStrategy", "bindStrategyMultipleStores"})
    public void unbindStrategyMultipleStores() {
        var request = UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithDifferentScriptsInRules)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(FIRST_STORE_ID)
                        .setTenantId(Tenant.SBERMARKET.getId())
                        .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                        .build())
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.OKEY.getId())
                        .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                        .build())
                .build();

        clientShippingCalc.unbindStrategy(request);
        checkUnbind(strategyIdWithDifferentScriptsInRules, FIRST_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(strategyIdWithDifferentScriptsInRules, SECOND_STORE_ID, Tenant.OKEY.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(159)
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без id стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid StrategyId")
    public void unbindStrategyWithNoStrategyId() {
        var request = getUnbindStrategyRequest(0, FIRST_STORE_ID, "test", DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = UnbindStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
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
        var request = getUnbindStrategyRequest(localStrategyId, "", "test", DeliveryType.SELF_DELIVERY_VALUE);
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
        var request = getUnbindStrategyRequest(localStrategyId, FIRST_STORE_ID, "", DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @CaseId(365)
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от магазина без типа доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategyWithNoDeliveryType")
    public void unbindStrategyWithNoDeliveryType() {
        var request = UnbindStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(SECOND_STORE_ID)
                        .setTenantId(Tenant.METRO.getId())
                        .build())
                .build();

        clientShippingCalc.unbindStrategy(request);
        checkUnbind(localStrategyId, SECOND_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @CaseId(118)
    @Story("Get Strategy")
    @Test(description = "Получение существующей стратегии",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "bindStrategy")
    public void getStrategy() {
        var request = GetStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .build();
        var response = clientShippingCalc.getStrategy(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertNotNull(response.getStrategy(), "В ответе пустая стратегия");
            compareTwoObjects(response.getStrategy().getStrategyId(), localStrategyId, softAssert);
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
        var request = GetStrategyRequest.newBuilder()
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
        var request = GetStrategyRequest.newBuilder()
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
        var request = GetStrategiesRequest.newBuilder().build();
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
        var request = GetStrategiesRequest.newBuilder()
                .setStrategyName("aut")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
            softAssert.assertEquals(response.getStrategies(0).getDeliveryTypeValue(), DeliveryType.SELF_DELIVERY_VALUE, "Не ожидаемый тип доставки");
            softAssert.assertAll();
        });
    }

    @CaseId(251)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по имени",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "createStrategy")
    public void getStrategiesWithNameFilter() {
        var request = GetStrategiesRequest.newBuilder()
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
        var request = GetStrategiesRequest.newBuilder()
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertEquals(response.getStrategies(0).getDeliveryTypeValue(), DeliveryType.SELF_DELIVERY_VALUE, "Не ожидаемый тип доставки");
            softAssert.assertAll();
        });
    }

    @CaseId(254)
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по магазину",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesWithStoreFilter() {
        var request = GetStrategiesRequest.newBuilder()
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
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant("test")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
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
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.getStrategiesForStore(request);
    }

    @CaseId(362)
    @Story("Get Strategies For Store")
    @Test(description = "Получение стратегий при отсутствии тенанта и способа доставки",
            groups = "dispatch-shippingcalc-regress",
            dependsOnMethods = "bindStrategyMultipleStores")
    public void getStrategiesForStoreWithoutTenantAndDeliveryType() {
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .build();

        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегий в ответе", () -> {
            final SoftAssert softAssert = new SoftAssert();
            assertTrue(response.getStrategyCount() > 1, "В ответе не ожидамое кол-во стратегий");
            softAssert.assertTrue(response.getStrategy(0).getBinding().getStoreId().equals(FIRST_STORE_ID) && response.getStrategy(1).getBinding().getStoreId().equals(FIRST_STORE_ID), "В ответе не нашли ожидаемых привязок");
            softAssert.assertNotEquals(response.getStrategy(0).getStrategyId(), response.getStrategy(1).getStrategyId(), "В ответе не нашли ожидаемые id стратегий");
            softAssert.assertNotEquals(response.getStrategy(0).getBinding().getTenantId(), response.getStrategy(1).getBinding().getTenantId(), "В ответе не нашли ожидаемых тенантов");
            softAssert.assertEquals(response.getStrategy(0).getBinding().getDeliveryType(), DeliveryType.SELF_DELIVERY, "В ответе не нашли ожидаемых типов доставки");
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
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
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
        var request = getDeleteStrategyRequest(strategyIdWithDifferentScriptsInRules);
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
            expectedExceptionsMessageRegExp = "INTERNAL: cannot delete strategy: usecase: delete strategy [0-9]+ is imposible, bindings must be empty",
            dependsOnMethods = "rebindStrategy")
    public void deleteStrategyWithBind() {
        var request = getDeleteStrategyRequest(strategyIdWithMultipleRulesAndConditions);
        clientShippingCalc.deleteStrategy(request);
    }

    @CaseId(437)
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки, при удалении глобальной стратегии",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "PERMISSION_DENIED: unable to delete global strategy",
            dependsOnMethods = "updateStrategyGlobal")
    public void deleteStrategyGlobal() {
        var request = getDeleteStrategyRequest(globalStrategyId);
        clientShippingCalc.deleteStrategy(request);
    }

    @CaseId(372)
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки при удалении несуществующей стратегии",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: usecase: delele strategy 1234567890: entity not found")
    public void deleteStrategyNonExistent() {
        var request = getDeleteStrategyRequest(1234567890);
        clientShippingCalc.deleteStrategy(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(localStrategyId);
        deleteCreatedStrategy(globalStrategyId);
        deleteCreatedStrategy(strategyIdWithDifferentScriptsInRules);
        deleteCreatedStrategy(strategyIdWithMultipleRulesAndConditions);
    }
}
