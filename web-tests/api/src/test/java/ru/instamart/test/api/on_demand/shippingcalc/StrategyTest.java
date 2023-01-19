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
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLinks;
import io.qameta.allure.TmsLink;
import shippingcalc.*;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.ShippingCalcHelper.*;

@Epic("ShippingCalc")
@Feature("Strategy")
public class StrategyTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer localStrategyId;
    private Integer globalStrategyId;
    private Integer strategyIdWithDifferentScriptsInRules;
    private Integer strategyIdWithMultipleRulesAndConditions;
    private Integer strategyIdWithReplace;
    private final String FIRST_STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final Integer FIRST_SCRIPT_ID = ScriptsDao.INSTANCE.getScriptByName(FIXED_SCRIPT_NAME).getId();
    private final Integer SECOND_SCRIPT_ID = ScriptsDao.INSTANCE.getScriptByName(COMPLEX_SCRIPT_NAME).getId();
    private final String SCRIPT_PARAMS = String.format(FIXED_SCRIPT_PARAMS, 10000);
    private final String FIRST_SCRIPT_PARAMS = String.format(FIXED_SCRIPT_PARAMS, 0);
    private final String SECOND_SCRIPT_PARAMS = String.format(COMPLEX_SCRIPT_PARAMS, 29900);
    private final String FIRST_PARAMS = "{\"Count\": 1}";
    private final String SECOND_PARAMS = "{\"Min\": 0, \"Max\": 1000000000000000}";
    private final String THIRD_PARAMS = "{\"Test\": \"shippingcalc_test\", \"Group\": \"control\"}";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannelWith(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC, 1024 * 1024 * 10));
    }

    @TmsLinks(value = {@TmsLink("46"), @TmsLink("60"), @TmsLink("74"), @TmsLink("335"), @TmsLink("339")})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с валидными данными",
            groups = "ondemand-shippingcalc")
    public void createStrategy() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        var response = clientShippingCalc.createStrategy(request);
        localStrategyId = response.getStrategyId();
        checkStrategy(localStrategyId, "autotest", 2, 2, 0, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLinks(value = {@TmsLink("63"), @TmsLink("61"), @TmsLink("445")})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами на разные скрипты",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
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

    @TmsLinks(value = {@TmsLink("191"), @TmsLink("62"), @TmsLink("71"), @TmsLink("337"), @TmsLink("340")})
    @Story("Create Strategy")
    @Test(description = "Создание стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "ondemand-shippingcalc")
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
                                .setParams(SECOND_PARAMS)
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
                                .setParams(SECOND_PARAMS)
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

    @TmsLink("446")
    @Story("Create Strategy")
    @Test(description = "Создание глобальной стратегии",
            groups = "ondemand-shippingcalc")
    public void createStrategyGlobal() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 0, "{}", 0, "autotest", "autotest", "autotest", true, DeliveryType.SELF_DELIVERY_VALUE);
        var response = clientShippingCalc.createStrategy(request);
        globalStrategyId = response.getStrategyId();
        checkGlobalStrategy(globalStrategyId, "autotest", 2, 2, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("434")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании глобальной стратегии для типа доставки, который уже имеет глобальную стратегию",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategyGlobal",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "ALREADY_EXISTS: global strategy for current shipping method already exists, entity already exists")
    public void createStrategyGlobalAlreadyExists() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 0, "{}", 0, "autotest", "autotest", "autotest", true, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("378")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки валидации параметров скрипта при создании стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: no required for script parameters")
    public void createStrategyNonValidScriptParams() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, "{}", 0, 2, SECOND_PARAMS, 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("430")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании локальной стратегии, если у правил мин. корзины нет условия Always",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
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

    @TmsLink("432")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании глобальной стратегии, если отсутствует условие Always для правил мин. корзины",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
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

    @TmsLink("435")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки, при создании глобальной стратегии, если отсутствует условие Always для правил цены",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
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

    @TmsLink("179")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty")
    public void createStrategyWithNoName() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, "autotest", "", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("180")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии идентификатора создателя",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator id cannot be empty")
    public void createStrategyWithNoCreatorId() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, "", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("183")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии правил стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rules cannot be empty")
    public void createStrategyWithNoRules() {
        var request = CreateStrategyRequest.newBuilder()
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
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

    @TmsLink("66")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при несуществующем скрипте",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot create strategy: scriptId 1234567890: entity not found")
    public void createStrategyWithNonExistentScript() {
        var request = getCreateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("184")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии скрипта",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 unacceptable without valid script id")
    public void createStrategyWithNoScriptId() {
        var request = CreateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
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

    @TmsLink("186")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии параметров скрипта",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has invalid params")
    public void createStrategyWithNoScriptParams() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, "", 0, 2, SECOND_PARAMS, 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("187")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("188")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии параметров условий",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: unexpected end of JSON input")
    public void createStrategyWithNoConditionParams() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("590")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не прохождении валидации в параметрах условий",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Count field is absent")
    public void createStrategyWithNoValidationParams() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 1, "{\"test\": 1}", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.createStrategy(request);
    }

    @TmsLink("336")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии правила на минимальную корзину",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("338")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при отсутствии условий минимальной корзины",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("401")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("402")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах минимальной корзины",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("403")
    @Story("Create Strategy")
    @Test(description = "Получение ошибки при не уникальном значении минимальной корзины",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("333")
    @Story("Create Strategy")
    @Test(description = "Проверка замены правой границы в условии ценового интервала (ORDER_VALUE_RANGE)",
            groups = "ondemand-shippingcalc")
    public void createStrategyReplaceOrderValueRange() {
        var request = getCreateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{\"Min\": 0, \"Max\": 0}", 0, "autotest", "autotest", "autotest", false, DeliveryType.SELF_DELIVERY_VALUE);
        var response = clientShippingCalc.createStrategy(request);
        strategyIdWithReplace = response.getStrategyId();
        checkStrategy(strategyIdWithReplace, "autotest", 2, 2, 0, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLinks(value = {@TmsLink("81"), @TmsLink("91"), @TmsLink("205"), @TmsLink("342"), @TmsLink("346"), @TmsLink("363")})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с валидными данными",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategy")
    public void updateStrategy() {
        var request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 2, SECOND_PARAMS, 10000, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.B2B_VALUE);
        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(localStrategyId, "autotest-update", 4, 4, 2, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLinks(value = {@TmsLink("94"), @TmsLink("92")})
    @Story("Update Strategy")
    @Test(description = "Обновление существующей стратегии с несколькими правилами на разные скрипты",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategyWithDifferentScriptsInRules")
    public void updateStrategyWithDifferentScriptsInRules() {
        var request = UpdateStrategyRequest.newBuilder()
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(SECOND_SCRIPT_ID)
                        .setScriptParamValues(SECOND_SCRIPT_PARAMS)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
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
                .setPriority(100)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithDifferentScriptsInRules, "autotest-update", 6, 6, 3, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLinks(value = {@TmsLink("206"), @TmsLink("93"), @TmsLink("102"), @TmsLink("344"), @TmsLink("347")})
    @Story("Update Strategy")
    @Test(description = "Обновление стратегии с несколькими правилами и несколькими условиями в этих правилах",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(strategyIdWithMultipleRulesAndConditions)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(100)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithMultipleRulesAndConditions, "autotest-update", 12, 20, 11, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("442")
    @Story("Update Strategy")
    @Test(description = "Нельзя изменить поле global, при обновлении стратегии",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategyGlobal")
    public void updateStrategyGlobal() {
        var request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 0, "{}", 10000, globalStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
        checkUpdatedGlobalStrategy(globalStrategyId, "autotest-update", 4, 4, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("431")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении локальной стратегии, если отсутствует правило мин. корзины с условием Always",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("433")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении глобальной стратегии, если отсутствует правило мин. корзины с условием Always",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(globalStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("436")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении глобальной стратегии, если отсутствует правило цены с условием Always",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
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
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("192")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии имени стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: strategy name cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoName() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, localStrategyId, "autotest-update", "", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLinks(value = {@TmsLink("83"), @TmsLink("204")})
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении несуществующей стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: cannot check existence of strategy, entity not found")
    public void updateStrategyWithNonExistentStrategyId() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, 1234567890, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("193")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии идентификатора создателя",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: creator id cannot be empty",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoCreatorId() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, localStrategyId, "", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("196")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии правил стратегии",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("97")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при обновлении существующей стратегии с правилом на несуществующий скрипт",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: scriptId 1234567890: entity not found",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNonExistentScriptId() {
        var request = getUpdateStrategyRequest(1234567890, SCRIPT_PARAMS, 0, 2, SECOND_PARAMS, 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("197")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии скрипта",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("199")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has invalid params",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoScriptParams() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, "", 0, 2, SECOND_PARAMS, 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("200")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии условий правила",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("201")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии параметров условий",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: unexpected end of JSON input",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoConditionParams() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "", 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("591")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не прохождении валидации в параметрах условий",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: rule 0 has condition 0 with validation error: invalid parameters structure, Max field is absent",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyWithNoValidationParams() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, SCRIPT_PARAMS, 0, 2, "{\"Min\": 1, \"test\": 1}", 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("343")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии правила на минимальную корзину",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("404")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addRules(NewRuleObject.newBuilder()
                        .setScriptId(FIRST_SCRIPT_ID)
                        .setScriptParamValues(SCRIPT_PARAMS)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("405")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не уникальном приоритете в правилах минимальной корзины",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(100000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("406")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при не уникальном значении минимальной корзины",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(0)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(50000)
                        .setPriority(1)
                        .addConditions(NewConditionObject.newBuilder()
                                .setConditionTypeValue(2)
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("345")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки при отсутствии условий минимальной корзины",
            groups = "ondemand-shippingcalc",
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
                                .setParams(SECOND_PARAMS)
                                .build())
                        .build())
                .addMinCartRules(MinCartRuleObject.newBuilder()
                        .setMinCartValue(0)
                        .setPriority(0)
                        .build())
                .setStrategyId(localStrategyId)
                .setCreatorId("autotest-update")
                .setName("autotest-update")
                .setPriority(0)
                .setDescription("autotest-update")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("372")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки, при обновлении удаленной стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: cannot check existence of strategy, entity not found",
            dependsOnMethods = "deleteStrategy")
    public void updateDeletedStrategy() {
        var request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 2, SECOND_PARAMS, 10000, localStrategyId, "autotest-deleted-update", "autotest-deleted-update", "autotest-deleted-update", false, DeliveryType.B2B_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("379")
    @Story("Update Strategy")
    @Test(description = "Получение ошибки валидации параметров скрипта при обновлении стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot update strategy: no required for script parameters",
            dependsOnMethods = "updateStrategy")
    public void updateStrategyNonValidScriptParams() {
        var request = getUpdateStrategyRequest(FIRST_SCRIPT_ID, "{}", 0, 2, SECOND_PARAMS, 0, localStrategyId, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.updateStrategy(request);
    }

    @TmsLink("334")
    @Story("Update Strategy")
    @Test(description = "Проверка замены правой границы в условии ценового интервала (ORDER_VALUE_RANGE)",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategyReplaceOrderValueRange")
    public void updateStrategyReplaceOrderValueRange() {
        var request = getUpdateStrategyRequest(SECOND_SCRIPT_ID, SECOND_SCRIPT_PARAMS, 100, 2, "{\"Min\": 0, \"Max\": 0}", 10000, strategyIdWithReplace, "autotest-update", "autotest-update", "autotest-update", false, DeliveryType.B2B_VALUE);
        clientShippingCalc.updateStrategy(request);
        checkUpdatedStrategy(strategyIdWithReplace, "autotest-update", 4, 4, 2, DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("138")
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину с валидными данными",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategy")
    public void bindStrategy() {
        var request = getBindStrategyRequest(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
        checkBind(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("141")
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к нескольким магазинам в массиве binds",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("139")
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину, у которого уже есть связка",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = {"bindStrategy", "createStrategyWithMultipleRulesAndConditions"})
    public void rebindStrategy() {
        var request = getBindStrategyRequest(strategyIdWithMultipleRulesAndConditions, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);

        checkBind(strategyIdWithMultipleRulesAndConditions, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
        checkUnbind(localStrategyId, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("366")
    @Skip // deprecated
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину с флагом replace_all",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("348")
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без id стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid StrategyId")
    public void bindStrategyWithNoStrategyId() {
        var request = getBindStrategyRequest(0, FIRST_STORE_ID, "test", DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @TmsLink("349")
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без сущности binds",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty binds",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoBinds() {
        var request = BindStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .build();

        clientShippingCalc.bindStrategy(request);
    }

    @TmsLink("144")
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без магазина",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot attach bind 0 without store id",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoStoreId() {
        var request = getBindStrategyRequest(localStrategyId, "", "test", DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @TmsLink("146")
    @Story("Bind Strategy")
    @Test(description = "Получении ошибки при привязке без тенанта",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot attach bind 0 without tenant id",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoTenantId() {
        var request = getBindStrategyRequest(localStrategyId, FIRST_STORE_ID, "", DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @TmsLink("364")
    @Story("Bind Strategy")
    @Test(description = "Привязка стратегии к магазину без типа доставки",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategy")
    public void bindStrategyWithNoDeliveryType() {
        var request = BindStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithMultipleRulesAndConditions)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(FIRST_STORE_ID)
                        .setTenantId(Tenant.AUCHAN.getId())
                        .build())
                .build();

        clientShippingCalc.bindStrategy(request);
        checkBind(strategyIdWithMultipleRulesAndConditions, FIRST_STORE_ID, Tenant.AUCHAN.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("376")
    @Story("Bind Strategy")
    @Test(description = "Получение ошибки при привязке к удаленной стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot bind strategy: repository.binding.bind: entity not found",
            dependsOnMethods = "deleteStrategy")
    public void bindStrategyDeleted() {
        var request = getBindStrategyRequest(localStrategyId, FIRST_STORE_ID, Tenant.SELGROS.getId(), DeliveryType.SELF_DELIVERY_VALUE, false);
        clientShippingCalc.bindStrategy(request);
    }

    @TmsLink("455")
    @Story("Unbind Strategy")
    @Skip // deprecated
    @Test(description = "Получение ошибки доступа при попытке отвязать стратегию",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "PERMISSION_DENIED: unbinding strategy is forbidden, you can only replace strategy in binding",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyPermissionDenied() {
        var request = getUnbindStrategyRequest(localStrategyId, FIRST_STORE_ID, "test", DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @TmsLink("155")
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от магазина с валидными данными",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = {"rebindStrategy", "getStrategy", "getStrategiesWithAllFilter", "getStrategiesForStore", "getStrategiesWithStoreFilter"})
    public void unbindStrategy() {
        var request = getUnbindStrategyRequest(strategyIdWithMultipleRulesAndConditions, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
        checkUnbind(strategyIdWithMultipleRulesAndConditions, FIRST_STORE_ID, Tenant.METRO.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("158")
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от нескольких магазинов в массиве binds",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = {"getStrategy", "bindStrategyMultipleStores", "getStrategiesForStoreWithoutTenantAndDeliveryType"})
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

    @TmsLink("159")
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без id стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid StrategyId")
    public void unbindStrategyWithNoStrategyId() {
        var request = getUnbindStrategyRequest(0, FIRST_STORE_ID, "test", DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @TmsLink("350")
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без сущности binds",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty binds",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyWithNoBinds() {
        var request = UnbindStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .build();

        clientShippingCalc.unbindStrategy(request);
    }

    @TmsLink("161")
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без магазина",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot use bind 0 without store id",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyWithNoStoreId() {
        var request = getUnbindStrategyRequest(localStrategyId, "", "test", DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @TmsLink("163")
    @Story("Unbind Strategy")
    @Test(description = "Получении ошибки при отвязке без тенанта",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot use bind 0 without tenant id",
            dependsOnMethods = "bindStrategy")
    public void unbindStrategyWithNoTenantId() {
        var request = getUnbindStrategyRequest(localStrategyId, FIRST_STORE_ID, "", DeliveryType.SELF_DELIVERY_VALUE);
        clientShippingCalc.unbindStrategy(request);
    }

    @TmsLink("365")
    @Story("Unbind Strategy")
    @Test(description = "Отвязка стратегии от магазина без типа доставки",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = {"bindStrategyWithNoDeliveryType", "deleteStrategyWithBind"})
    public void unbindStrategyWithNoDeliveryType() {
        var request = UnbindStrategyRequest.newBuilder()
                .setStrategyId(strategyIdWithMultipleRulesAndConditions)
                .addBinds(StrategyBinding.newBuilder()
                        .setStoreId(FIRST_STORE_ID)
                        .setTenantId(Tenant.AUCHAN.getId())
                        .build())
                .build();

        clientShippingCalc.unbindStrategy(request);
        checkUnbind(strategyIdWithMultipleRulesAndConditions, FIRST_STORE_ID, Tenant.AUCHAN.getId(), DeliveryType.SELF_DELIVERY.toString());
    }

    @TmsLink("118")
    @Story("Get Strategy")
    @Test(description = "Получение существующей стратегии",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "bindStrategy")
    public void getStrategy() {
        var request = GetStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .build();
        var response = clientShippingCalc.getStrategy(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertNotNull(response.getStrategy(), "В ответе пустая стратегия");
            final SoftAssert softAssert = new SoftAssert();
            compareTwoObjects(response.getStrategy().getStrategyId(), localStrategyId, softAssert);
            softAssert.assertTrue(response.getStrategy().getBindsCount() > 0, "В ответе пустой список привязок");
            softAssert.assertTrue(response.getRulesCount() > 0, "В ответе пустой список правил");
            softAssert.assertTrue(response.getRules(0).getConditionsCount() > 0, "В ответе пустой список условий для правил");
            softAssert.assertTrue(response.getMinCartRulesCount() > 0, "В ответе пустой список правил мин. корзины");
            softAssert.assertTrue(response.getMinCartRules(0).getConditionsCount() > 0, "В ответе пустой список условий для правил мин. корзины");
            softAssert.assertAll();
        });
    }

    @TmsLink("120")
    @Story("Get Strategy")
    @Test(description = "Получение ошибки при отправке запроса с несуществующей стратегией",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot find strategy, entity not found")
    public void getStrategyNonExistent() {
        var request = GetStrategyRequest.newBuilder()
                .setStrategyId(1234567890)
                .build();

        clientShippingCalc.getStrategy(request);
    }

    @TmsLink("375")
    @Story("Get Strategy")
    @Test(description = "Получение ошибки при запросе удаленной стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot find strategy, entity not found",
            dependsOnMethods = "deleteStrategy")
    public void getStrategyDeleted() {
        var request = GetStrategyRequest.newBuilder()
                .setStrategyId(localStrategyId)
                .build();

        clientShippingCalc.getStrategy(request);
    }

    @TmsLink("124")
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий без фильтров",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategy")
    public void getStrategies() {
        var request = GetStrategiesRequest.newBuilder().build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertNotNull(response.getStrategies(0).getName(), "В ответе пустое название стратегии");
            softAssert.assertTrue(response.getStrategies(0).getDeliveryTypeValue() > 0, "В ответе пустой тип доставки");
            softAssert.assertAll();
        });
    }

    @TmsLink("256")
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с комбинированным фильтром (имя, тип доставки, магазины)",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesWithAllFilter() {
        var request = GetStrategiesRequest.newBuilder()
                .setStrategyName("aut")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .addStores(FIRST_STORE_ID)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertTrue(response.getStrategies(0).getName().contains("autotest"), "Не ожидаемое название стратегии");
            softAssert.assertTrue(response.getStrategies(0).getStoresCount() > 0, "В ответе пустой список привязок");
            softAssert.assertTrue(response.getStrategies(0).getStoresList().contains(FIRST_STORE_ID), "В ответе не нашли ожидаемую привязку");
            softAssert.assertEquals(response.getStrategies(0).getDeliveryTypeValue(), DeliveryType.SELF_DELIVERY_VALUE, "Не ожидаемый тип доставки");
            softAssert.assertAll();
        });
    }

    @TmsLink("251")
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по имени",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategy")
    public void getStrategiesWithNameFilter() {
        var request = GetStrategiesRequest.newBuilder()
                .setStrategyName("aut")
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertTrue(response.getStrategies(0).getName().contains("autotest"), "Не ожидаемое название стратегии");
            softAssert.assertAll();
        });
    }

    @TmsLink("253")
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по типу доставки",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "createStrategy")
    public void getStrategiesWithDeliveryTypeFilter() {
        var request = GetStrategiesRequest.newBuilder()
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertEquals(response.getStrategies(0).getDeliveryTypeValue(), DeliveryType.SELF_DELIVERY_VALUE, "Не ожидаемый тип доставки");
            softAssert.assertAll();
        });
    }

    @TmsLink("254")
    @Story("Get Strategies")
    @Test(description = "Получение списка стратегий с фильтром по магазину",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesWithStoreFilter() {
        var request = GetStrategiesRequest.newBuilder()
                .addStores(FIRST_STORE_ID)
                .build();
        var response = clientShippingCalc.getStrategies(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertTrue(response.getStrategiesCount() > 0, "В ответе пустой список стратегий");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategies(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertTrue(response.getStrategies(0).getStoresCount() > 0, "В ответе пустой список привязок");
            softAssert.assertTrue(response.getStrategies(0).getStoresList().contains(FIRST_STORE_ID), "В ответе не нашли ожидаемую привязку");
            softAssert.assertAll();
        });
    }

    @TmsLink("172")
    @Story("Get Strategies For Store")
    @Test(description = "Получение стратегии для магазина",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "bindStrategy")
    public void getStrategiesForStore() {
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();
        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегии в ответе", () -> {
            assertNotNull(response.getStrategy(0), "Пустой ответ");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategy(0).getStrategyId() > 0, "В ответе пустое id стратегии");
            softAssert.assertEquals(response.getStrategy(0).getBinding().getStoreId(), FIRST_STORE_ID, "В ответе не нашли ожидаемую привязку");
            softAssert.assertEquals(response.getStrategy(0).getBinding().getTenantId(), Tenant.METRO.getId(), "В ответе не нашли ожидаемый тенант");
            softAssert.assertAll();
        });
    }

    @TmsLink("175")
    @Story("Get Strategies For Store")
    @Test(description = "Получение пустого списка при отсутствии искомой стратегии",
            groups = "ondemand-shippingcalc")
    public void getStrategiesForStoreNotFound() {
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .setTenant("test")
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегии в ответе", () -> compareTwoObjects(response.getStrategyCount(), 0));
    }

    @TmsLink("351")
    @Story("Get Strategies For Store")
    @Test(description = "Получение ошибки при отсутствии магазина",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty store id")
    public void getStrategiesForStoreWithoutStoreId() {
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setTenant(Tenant.METRO.getId())
                .setDeliveryTypeValue(DeliveryType.SELF_DELIVERY_VALUE)
                .build();

        clientShippingCalc.getStrategiesForStore(request);
    }

    @TmsLink("362")
    @Story("Get Strategies For Store")
    @Test(description = "Получение стратегий при отсутствии тенанта и способа доставки",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "bindStrategyMultipleStores")
    public void getStrategiesForStoreWithoutTenantAndDeliveryType() {
        GetStrategiesForStoreRequest request = GetStrategiesForStoreRequest.newBuilder()
                .setStoreId(FIRST_STORE_ID)
                .build();

        var response = clientShippingCalc.getStrategiesForStore(request);

        Allure.step("Проверка стратегий в ответе", () -> {
            assertTrue(response.getStrategyCount() > 0, "Пустой ответ");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getStrategyCount() > 1, "В ответе не ожидамое кол-во стратегий");
            softAssert.assertTrue(response.getStrategy(0).getBinding().getStoreId().equals(FIRST_STORE_ID) && response.getStrategy(1).getBinding().getStoreId().equals(FIRST_STORE_ID), "В ответе не нашли ожидаемых привязок");
            softAssert.assertEquals(response.getStrategy(0).getBinding().getDeliveryType(), DeliveryType.SELF_DELIVERY, "В ответе не нашли ожидаемых типов доставки");
            softAssert.assertAll();
        });
    }

    @TmsLink("353")
    @Story("Get Strategies For Store")
    @Test(description = "Получение ошибки при не валидном способе доставки",
            groups = "ondemand-shippingcalc",
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

    @TmsLink("367")
    @Story("Delete Strategy")
    @Test(description = "Удаление стратегии без биндов",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "rebindStrategy")
    public void deleteStrategy() {
        var request = getDeleteStrategyRequest(localStrategyId);
        var response = clientShippingCalc.deleteStrategy(request);


        Allure.step("Проверка удаления стратегии", () -> {
            compareTwoObjects(response.toString(), "");
            final SoftAssert softAssert = new SoftAssert();
            StrategiesEntity strategy = StrategiesDao.INSTANCE.getStrategy(localStrategyId);
            List<RulesEntity> rules = RulesDao.INSTANCE.getRules(localStrategyId);
            List<RulesEntity> deletedRules = RulesDao.INSTANCE.getDeletedRules(localStrategyId);
            compareTwoObjects(rules.size(), deletedRules.size(), softAssert);
            softAssert.assertNotNull(strategy.getDeletedAt(), "Стратегия не помечена удаленной");
            softAssert.assertAll();
        });
    }

    @TmsLink("369")
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки при удалении стратегии с биндами",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "FAILED_PRECONDITION: removing strategy [0-9]+ is not posible, bindings must be empty",
            dependsOnMethods = "rebindStrategy")
    public void deleteStrategyWithBind() {
        var request = getDeleteStrategyRequest(strategyIdWithMultipleRulesAndConditions);
        clientShippingCalc.deleteStrategy(request);
    }

    @TmsLink("437")
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки, при удалении глобальной стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "PERMISSION_DENIED: unable to delete global strategy",
            dependsOnMethods = "updateStrategyGlobal")
    public void deleteStrategyGlobal() {
        var request = getDeleteStrategyRequest(globalStrategyId);
        clientShippingCalc.deleteStrategy(request);
    }

    @TmsLink("373")
    @Story("Delete Strategy")
    @Test(description = "Получение ошибки при удалении несуществующей стратегии",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: cannot delete strategy 1234567890: entity not found")
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
        deleteCreatedStrategy(strategyIdWithReplace);
    }
}
