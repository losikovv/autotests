package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.api.helper.ShippingCalcHelper;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.jdbc.dao.shippingcalc.RulesDao;
import ru.instamart.kraken.enums.AppVersion;
import ru.instamart.kraken.enums.Tenant;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;
import shippingcalc.*;
import surgelevelevent.Surgelevelevent;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

@Epic("ShippingCalc")
@Feature("Min Cart & Delivery Conditions")
public class MinCartTest extends ShippingCalcBase {

    private final String STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String SURGE_STORE_ID = UUID.randomUUID().toString();
    private final String GRADE_SURGE_STORE_ID = UUID.randomUUID().toString();
    private final String STORE_ID_GLOBAL = UUID.randomUUID().toString();
    private final String CUSTOMER_ID = UUID.randomUUID().toString();
    private final String ANONYMOUS_ID = UUID.randomUUID().toString();
    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private int localStrategyId;
    private int globalStrategyId;
    private int selfDeliveryStrategyId;
    private boolean surgeDisabled;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));

        localStrategyId = addStrategy(false, 0, DeliveryType.B2B.toString());
        addCondition(addRule(localStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "4900"), 0, "delivery_price"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(localStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "14900"), 1, "delivery_price"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.29.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(localStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "19900"), 2, "delivery_price"), "{\"Max\": 300000, \"Min\": 0}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(localStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "9900"), 3, "delivery_price"), "{\"Max\": 1000000000000000, \"Min\": 300000}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(localStrategyId, "", minCartAmountFirst.toString(), 0, "min_cart"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(localStrategyId, "", minCartAmountSecond.toString(), 1, "min_cart"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.29.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(localStrategyId, "", minCartAmountThird.toString(), 2, "min_cart"), "{}", ConditionType.ALWAYS.name().toLowerCase());
        addBinding(localStrategyId, STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.B2B.toString());
        addBinding(localStrategyId, SECOND_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.B2B.toString());
        addBinding(localStrategyId, SURGE_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.B2B.toString());
        addBinding(localStrategyId, GRADE_SURGE_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.B2B.toString());

        globalStrategyId = addStrategy(true, 0, DeliveryType.B2B.toString());
        addCondition(addRule(globalStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, deliveryPriceGlobal.toString()), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(globalStrategyId, "", minCartAmountGlobal.toString(), 0, "min_cart"), "{}", "always");

        selfDeliveryStrategyId = addStrategy(false, 0, DeliveryType.B2B_SELF_DELIVERY.toString());
        addCondition(addRule(selfDeliveryStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "10002"), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(selfDeliveryStrategyId, "", minCartAmountFourth.toString(), 0, "min_cart"), "{}", "always");
        addBinding(selfDeliveryStrategyId, SURGE_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY.toString());

        RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + SURGE_STORE_ID, String.format(REDIS_VALUE, SURGE_STORE_ID, SURGE_LEVEL, SURGE_LEVEL, SURGE_LEVEL, getZonedUTCDate(), ""), 1000);
        RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + GRADE_SURGE_STORE_ID, String.format(REDIS_VALUE, GRADE_SURGE_STORE_ID, SURGE_LEVEL, SURGE_LEVEL, SURGE_LEVEL, getZonedUTCDate(), Surgelevelevent.SurgeEvent.Grade.MID.toString().toLowerCase()), 1000);

        surgeDisabled = ShippingCalcHelper.getInstance().isSurgeDisabled();
    }

    @TmsLinks(value = {@TmsLink("411"), @TmsLink("415")})
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина с валидными данными",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditions() {
        var request = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, 1, 1);

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, minCartAmountThird, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
    }

    @TmsLink("499")
    @Story("Get Delivery Conditions")
    @Test(description = "Создание оффера, фиксирующего условия доставки",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsFromCache() {
        String customerUuid = UUID.randomUUID().toString();
        Integer minCartAmountGlobalFromCache = minCartAmountGlobal;
        Integer deliveryPriceGlobalFromCache = deliveryPriceGlobal;

        var firstRequest = getDeliveryConditionsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, 1, 1);
        var firstResponse = clientShippingCalc.getDeliveryConditions(firstRequest);
        checkDeliveryConditions(firstResponse, STORE_ID_GLOBAL, minCartAmountGlobal, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> compareTwoObjects(firstResponse.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), deliveryPriceGlobal.longValue()));

        Allure.step("Обновляем значение мин. корзины и цены доставки", () -> {
            boolean ruleUpdatedMinCart = RulesDao.INSTANCE.updateRuleParams("222222", globalStrategyId, 0, "min_cart");
            boolean ruleUpdatedDeliveryPrice = RulesDao.INSTANCE.updateRuleParams(String.format(FIXED_SCRIPT_PARAMS, "222222"), globalStrategyId, 0, "delivery_price");
            if (ruleUpdatedMinCart) minCartAmountGlobal = 222222;
            if (ruleUpdatedDeliveryPrice) deliveryPriceGlobal = 222222;

        });

        var secondRequest = getDeliveryConditionsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, 1, 1);
        var secondResponse = clientShippingCalc.getDeliveryConditions(secondRequest);
        checkDeliveryConditions(secondResponse, STORE_ID_GLOBAL, minCartAmountGlobalFromCache, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> compareTwoObjects(secondResponse.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), deliveryPriceGlobalFromCache.longValue()));
    }

    @TmsLink("412")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для нескольких магазинов",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsMultipleStores() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .addStores(Store.newBuilder()
                        .setId(SECOND_STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryConditions(request);

        Allure.step("Проверяем условия доставки для нескольких магазинов", () -> {
            assertTrue(response.getDeliveryConditionsCount() > 0, "Пустые условия доставки");

            List<String> storeUuids = List.of(STORE_ID, SECOND_STORE_ID);

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(storeUuids.contains(response.getDeliveryConditions(0).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getMinCartAmount(), minCartAmountThird.intValue(), "Не ожидаемая минимальная корзина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadderCount(), 2, "Не ожидаемое кол-во ступеней в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponentsCount(), 3, "Не ожидаемое кол-во компонентов цены");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertTrue(storeUuids.contains(response.getDeliveryConditions(1).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(1).getMinCartAmount(), minCartAmountThird.intValue(), "Не ожидаемая минимальная корзина");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadderCount(), 2, "Не ожидаемое кол-во ступеней в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadder(0).getPriceComponentsCount(), 3, "Не ожидаемое кол-во компонентов цены");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @TmsLink("411")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки по глобальной стратегии",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsGlobalStrategy() {
        var request = getDeliveryConditionsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, 1, 1);

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID_GLOBAL, minCartAmountGlobal, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> compareTwoObjects(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), deliveryPriceGlobal.longValue()));
    }

    @TmsLinks({@TmsLink("414"), @TmsLink("526"), @TmsLink("606")})
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина с повышенным спросом",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSurge() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890, 20890);
    }

    @TmsLink("528")
    @Story("Get Delivery Conditions")
    @Test(description = "Наценка по параметрам свитчбека (полное попадание)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSwitchback() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_SWITCHBACK, RETAILER_ID_WITH_SWITCHBACK);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890 + SURGE_SWITCHBACK_ADDITION_DIFF, 20890 + SURGE_SWITCHBACK_ADDITION_DIFF);
    }

    @TmsLink("581")
    @Story("Get Delivery Conditions")
    @Test(description = "Наценка по параметрам свитчбека (попадание по вертикали)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSwitchbackVertical() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITH_SWITCHBACK);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890 + SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF, 20890 + SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF);
    }

    @TmsLink("582")
    @Story("Get Delivery Conditions")
    @Test(description = "Наценка по параметрам свитчбека (попадание по региону)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSwitchbackRegion() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_SWITCHBACK, RETAILER_ID_WITHOUT_SWITCHBACK);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_REGION_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890 + SURGE_SWITCHBACK_REGION_ADDITION_DIFF, 20890 + SURGE_SWITCHBACK_REGION_ADDITION_DIFF);
    }

    @TmsLink("605")
    @Story("Get Delivery Conditions")
    @Test(description = "Расчет цены с наценкой surge по grade",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithGradeSurge() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(GRADE_SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_GRADE, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, GRADE_SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_GRADE_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 19900 + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_GRADE_ADDITION_DIFF, 9900 + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_GRADE_ADDITION_DIFF);
    }

    @TmsLink("607")
    @Story("Get Delivery Conditions")
    @Test(description = "Расчет цены с наценкой surge не по grade (нет в redis)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithGradeSurgeNoRedis() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_GRADE, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890, 20890);
    }

    @TmsLink("529")
    @Story("Get Delivery Conditions")
    @Test(description = "Отсутствие наценки по параметрам свитчбека при не попадании в временной интервал",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithFutureSwitchback() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_FUTURE_SWITCHBACK, RETAILER_ID_WITHOUT_SWITCHBACK);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890, 20890);
    }

    @TmsLink("509")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для не on-demand магазина с не нулевой мин. корзиной",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithMinCartForPlanned() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false, REGION_ID_WITH_SWITCHBACK, RETAILER_ID_WITH_SWITCHBACK);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
    }

    @TmsLinks({@TmsLink("525"), @TmsLink("530")})
    @Story("Get Delivery Conditions")
    @Test(description = "Наценка по сюрдж-параметрам (полное попадание)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSurgeParameters() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_PARAMETERS, RETAILER_ID_WITH_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890 + SURGE_PARAMETERS_ADDITION_DIFF, 20890 + SURGE_PARAMETERS_ADDITION_DIFF);
    }

    @TmsLink("583")
    @Story("Get Delivery Conditions")
    @Test(description = "Наценка по сюрдж-параметрам (попадание по вертикали)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSurgeParametersVertical() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITH_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890 + SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF, 20890 + SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF);
    }

    @TmsLink("584")
    @Story("Get Delivery Conditions")
    @Test(description = "Наценка по сюрдж-параметрам (попадание по региону)",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithSurgeParametersRegion() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITH_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_REGION_ADDITION_DIFF, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890 + SURGE_PARAMETERS_REGION_ADDITION_DIFF, 20890 + SURGE_PARAMETERS_REGION_ADDITION_DIFF);
    }

    @TmsLink("507")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса с флагом SURGE_DISABLED",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithoutSurgeFlag() {

        if (!surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = false");
        }

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOff(response, 0f, 19900, 9900);
    }

    @TmsLink("449")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для не on-demand магазина",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithNoSurge() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, 0, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOff(response, 0f, 19900, 9900);
    }

    @TmsLink("424")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для новых клиентов",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithoutSurgeNewCustomers() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOff(response, 0f, 19900, 9900);
    }

    @TmsLink("440")
    @Story("Get Delivery Conditions")
    @Test(description = "Кэширование surgelevel на уровне customer+store",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getDeliveryConditionsWithSurge")
    public void getDeliveryConditionsWithStoreCustomerSurge() {
        Allure.step("Меняем уровень surge у магазина", () -> RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + SURGE_STORE_ID, String.format(REDIS_VALUE, SURGE_STORE_ID, 0, 0, 0, getZonedUTCDate(), ""), 1000));

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
        checkDeliveryConditionsSurgeOn(response, SURGE_LEVEL, 31890, 20890);
    }

    @TmsLink("458")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для самовывоза",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsWithNoSurgeSelfDelivery() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, 0, 1, 3);
        Allure.step("Проверяем отсутствие повышенного спроса", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж включен");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), 0f, "Не ожидаемый уровень сюрджа");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getTtl(), 0, "Не ожидаемый уровень ttl сюрджа");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), 10002, "Не ожидаемая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @TmsLink("420")
    @Story("Get Delivery Conditions")
    @Test(description = "Проверка прохождений условий в правилах мин корзины",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsDifferentRules() {
        var firstRequest = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true, 1, 1);
        var secondRequest = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.IOS.getName(), AppVersion.IOS.getVersion(), true, 1, 1);

        var firstResponse = clientShippingCalc.getDeliveryConditions(firstRequest);
        var secondResponse = clientShippingCalc.getDeliveryConditions(secondRequest);

        checkDeliveryConditions(firstResponse, STORE_ID, minCartAmountFirst, 1, 3);
        checkDeliveryConditions(secondResponse, STORE_ID, minCartAmountSecond, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(firstResponse.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 4900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(secondResponse.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 14900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @TmsLink("416")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина у которого не нашли связку",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsNotFound() {
        String storeId = UUID.randomUUID().toString();
        var request = getDeliveryConditionsRequest(storeId, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false, 1, 1);

        var response = clientShippingCalc.getDeliveryConditions(request);

        Allure.step("Проверяем пустой ответ", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getStoreId(), storeId, "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().toString(), "", "Не пустой surge в ответе");
            softAssert.assertFalse(response.getDeliveryConditions(0).getLadderCount() > 0, "Не пустой ladder в ответе");
            softAssert.assertAll();
        });
    }

    @TmsLink("417")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение ошибки при пустом stores",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: no stores in request")
    public void getDeliveryConditionsNoStores() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        clientShippingCalc.getDeliveryConditions(request);
    }

    @TmsLink("418")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение ошибки при пустом tenant",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getDeliveryConditionsNoTenant() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        clientShippingCalc.getDeliveryConditions(request);
    }

    @TmsLink("419")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение ошибки при невалидном delivery_type",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid delivery type")
    public void getDeliveryConditionsNoDeliveryType() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        clientShippingCalc.getDeliveryConditions(request);
    }

    @TmsLink("573")
    @Story("Get Delivery Conditions")
    @Test(description = "Прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID и попадании в вайтлист",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsNoCustomerIdWhitelist() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, minCartAmountFirst, 1, 3);
        assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 4900, "Не ожидаемая базовая цена в лесенке");
    }

    @TmsLink("428")
    @Story("Get Delivery Conditions")
    @Test(description = "Отсутствие прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID и не попадании в вайтлист",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsNoCustomerIdNoWhitelist() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName("test")
                .setPlatformVersion("test")
                .build();

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, minCartAmountThird, 2, 3);
        checkDeliveryConditionsTwoStepLadder(response, 19900, 9900);
    }

    @TmsLink("508")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина при пустых customer.id и customer.anonymous_id",
            groups = "ondemand-shippingcalc")
    public void getDeliveryConditionsNoCustomerAndAnonymousId() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setOrdersCount(99)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, 0, 1, 3);
    }

    @TmsLinks(value = {@TmsLink("391"), @TmsLink("453")})
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины с валидными данными",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmounts() {
        var request = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, minCartAmountThird);
    }

    @TmsLink("498")
    @Story("Get Min Cart Amounts")
    @Test(description = "Создание оффера, фиксирующего стоимость мин. корзины",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsFromCache() {
        String customerUuid = UUID.randomUUID().toString();
        int minCartAmountGlobalFromCache = minCartAmountGlobal;

        var firstRequest = getMinCartAmountsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);
        var firstResponse = clientShippingCalc.getMinCartAmounts(firstRequest);
        checkMinCartAmounts(firstResponse, STORE_ID_GLOBAL, minCartAmountGlobal);

        Allure.step("Обновляем значение мин. корзины", () -> {
            boolean ruleUpdatedMinCart = RulesDao.INSTANCE.updateRuleParams("111111", globalStrategyId, 0, "min_cart");
            if (ruleUpdatedMinCart) {
                minCartAmountGlobal = 111111;
            }
        });

        var secondRequest = getMinCartAmountsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);
        var secondResponse = clientShippingCalc.getMinCartAmounts(secondRequest);
        checkMinCartAmounts(secondResponse, STORE_ID_GLOBAL, minCartAmountGlobalFromCache);
    }

    @TmsLink("392")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины по нескольким магазинам",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsMultipleStores() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .addStores(Store.newBuilder()
                        .setId(SECOND_STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем условия доставки для нескольких магазинов", () -> {
            assertTrue(response.getMinCartAmountsCount() > 0, "Пустые мин. корзины");

            List<String> storeUuids = List.of(STORE_ID, SECOND_STORE_ID);

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(storeUuids.contains(response.getMinCartAmounts(0).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getMinCartAmounts(0).getAmountCourierDelivery(), minCartAmountThird.intValue(), "Не ожидаемая минимальная корзина");
            softAssert.assertTrue(storeUuids.contains(response.getMinCartAmounts(1).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getMinCartAmounts(1).getAmountCourierDelivery(), minCartAmountThird.intValue(), "Не ожидаемая минимальная корзина");
            softAssert.assertAll();
        });
    }

    @TmsLink("438")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины по глобальной стратегии",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsGlobalStrategy() {
        var request = getMinCartAmountsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID_GLOBAL, minCartAmountGlobal);
    }

    @TmsLink("413")
    @Story("Get Min Cart Amounts")
    @Test(description = "Проверка прохождений условий в правилах мин корзины",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsDifferentRules() {
        var firstRequest = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);
        var secondRequest = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);

        var firstResponse = clientShippingCalc.getMinCartAmounts(firstRequest);
        var secondResponse = clientShippingCalc.getMinCartAmounts(secondRequest);

        checkMinCartAmounts(firstResponse, STORE_ID, minCartAmountFirst);
        checkMinCartAmounts(secondResponse, STORE_ID, minCartAmountThird);
    }

    @TmsLinks({@TmsLink("451"), @TmsLink("535"), @TmsLink("609")})
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины для магазина с повышенным спросом",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSurge() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT);
    }

    @TmsLink("531")
    @Story("Get Min Cart Amounts")
    @Test(description = "Наценка по параметрам свитчбека (полное попадание)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSwitchback() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_SWITCHBACK, RETAILER_ID_WITH_SWITCHBACK);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_ADDITION_DIFF);
    }

    @TmsLink("585")
    @Story("Get Min Cart Amounts")
    @Test(description = "Наценка по параметрам свитчбека (попадание по вертикали)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSwitchbackVertical() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITH_SWITCHBACK);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_VERTICAL_ADDITION_DIFF);
    }

    @TmsLink("586")
    @Story("Get Min Cart Amounts")
    @Test(description = "Наценка по параметрам свитчбека (попадание по региону)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSwitchbackRegion() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_SWITCHBACK, RETAILER_ID_WITHOUT_SWITCHBACK);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_SWITCHBACK_REGION_ADDITION_DIFF);
    }

    @TmsLink("532")
    @Story("Get Min Cart Amounts")
    @Test(description = "Отсутствие наценки по параметрам свитчбека при не попадании в временной интервал",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithFutureSwitchback() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_FUTURE_SWITCHBACK, RETAILER_ID_WITHOUT_SWITCHBACK);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT);
    }

    @TmsLink("504")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение не нулевой минимальной корзины для не on_demand магазина",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithMinCartForPlanned() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false, REGION_ID_WITH_SWITCHBACK, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird);
    }

    @TmsLinks({@TmsLink("534"), @TmsLink("533")})
    @Story("Get Min Cart Amounts")
    @Test(description = "Наценка по сюрдж-параметрам (полное попадание)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSurgeParameters() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_PARAMETERS, RETAILER_ID_WITH_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_ADDITION_DIFF);
    }

    @TmsLink("587")
    @Story("Get Min Cart Amounts")
    @Test(description = "Наценка по сюрдж-параметрам (попадание по вертикали)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSurgeParametersVertical() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITH_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_VERTICAL_ADDITION_DIFF);
    }

    @TmsLink("588")
    @Story("Get Min Cart Amounts")
    @Test(description = "Наценка по сюрдж-параметрам (попадание по региону)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSurgeParametersRegion() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_REGION_ADDITION_DIFF);
    }

    @TmsLink("608")
    @Story("Get Min Cart Amounts")
    @Test(description = "Расчет цены с наценкой surge по grade",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithGradeSurge() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(GRADE_SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_GRADE, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, GRADE_SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_PARAMETERS_GRADE_ADDITION_DIFF);
    }

    @TmsLink("610")
    @Story("Get Min Cart Amounts")
    @Test(description = "Расчет цены с наценкой surge не по grade (нет в redis)",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithGradeSurgeNoRedis() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITH_GRADE, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT);
    }

    @TmsLink("506")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины без повышенного спроса с флагом SURGE_DISABLED",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithoutSurgeFlag() {

        if (!surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = false");
        }

        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird);
    }

    @TmsLink("497")
    @Story("Get Min Cart Amounts")
    @Test(description = "Кэширование surgelevel на уровне customer+store",
            groups = "ondemand-shippingcalc",
            dependsOnMethods = "getDeliveryConditionsWithStoreCustomerSurge")
    public void getMinCartAmountsWithStoreCustomerSurge() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT);
    }

    @TmsLink("452")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины без повышенного спроса для не on_demand магазина",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSurgeNotOnDemand() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, 0);
    }

    @TmsLink("454")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины для магазина без повышенного спроса для новых клиентов",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithSurgeNewCustomer() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird);
    }

    @TmsLink("457")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины без повышенного спроса для самовывоза",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsWithNoSurgeSelfDelivery() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE, true, REGION_ID_WITHOUT_PARAMETERS, RETAILER_ID_WITHOUT_PARAMETERS);

        var response = clientShippingCalc.getMinCartAmounts(request);
        if (!surgeDisabled) {
            checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + SURGE_LEVEL_ADDITION_DEFAULT);
        } else {
            checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird);
        }
        Allure.step("Проверяем отсутствия наценки surge для самовывоза", () -> compareTwoObjects(response.getMinCartAmounts(0).getAmountSelfDelivery(), 0L));
    }

    @TmsLink("450")
    @Story("Get Min Cart Amounts")
    @Test(description = "Проверка наличия мин. корзины для доставки курьером и самовывоза",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsSelfAndCourier() {
        String storeId = UUID.randomUUID().toString();
        addBinding(selfDeliveryStrategyId, storeId, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY.toString());

        var request = getMinCartAmountsRequest(storeId, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true, 1, 1);

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем отдачу мин. корзины магазина и для самовывоза и для доставки", () -> {
            assertTrue(response.getMinCartAmountsCount() > 0, "Пустая мин. корзина");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getMinCartAmounts(0).getStoreId(), storeId, "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getMinCartAmounts(0).getAmountCourierDelivery(), minCartAmountGlobal.intValue(), "Не ожидаемая минимальная корзина для доставки");
            softAssert.assertEquals(response.getMinCartAmounts(0).getAmountSelfDelivery(), 0, "Не ожидаемая минимальная корзина для самовывоза");
            softAssert.assertAll();
        });
    }

    @TmsLink("393")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка для магазина у которого не нашли связку",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsNotFound() {
        var request = getMinCartAmountsRequest(UUID.randomUUID().toString(), 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE, true, 1, 1);

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем пустое значение", () -> compareTwoObjects(response.getMinCartAmounts(0).getAmountSelfDelivery(), 0L));
    }

    @TmsLink("398")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение ошибки при невалидном delivery_type",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid delivery type")
    public void getMinCartAmountsNoDeliveryType() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .build();

        clientShippingCalc.getMinCartAmounts(request);
    }

    @TmsLink("589")
    @Story("Get Min Cart Amounts")
    @Test(description = "Прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID и попадании в вайтлист",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsNoCustomerIdWhitelist() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, minCartAmountFirst);
    }

    @TmsLink("395")
    @Story("Get Min Cart Amounts")
    @Test(description = "Отсутствие прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID и не попадании в вайтлист",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsNoCustomerIdNoWhitelist() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setAnonymousId(UUID.randomUUID().toString())
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .setPlatformName("test")
                .setPlatformVersion("test")
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, minCartAmountThird);
    }

    @TmsLink("505")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение нулевой минимальной корзины при пустых customer.id и customer.anonymous_id",
            groups = "ondemand-shippingcalc")
    public void getMinCartAmountsNoCustomerAndAnonymousId() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .setIsOndemand(true)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, 0);
    }

    @TmsLink("396")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка при пустом stores",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: no stores in request")
    public void getMinCartAmountsNoStores() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .build();

        clientShippingCalc.getMinCartAmounts(request);
    }

    @TmsLink("397")
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка при пустом tenant",
            groups = "ondemand-shippingcalc",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty tenant_id")
    public void getMinCartAmountsNoTenantId() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setDeliveryTypeValue(DeliveryType.B2B_VALUE)
                .build();

        clientShippingCalc.getMinCartAmounts(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(localStrategyId);
        deleteCreatedStrategy(globalStrategyId);
        deleteCreatedStrategy(selfDeliveryStrategyId);
    }
}
