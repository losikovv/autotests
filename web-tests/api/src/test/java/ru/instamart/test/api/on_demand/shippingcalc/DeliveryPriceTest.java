package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.AppVersion;
import ru.instamart.kraken.enums.Tenant;
import ru.instamart.redis.Redis;
import ru.instamart.redis.RedisManager;
import ru.instamart.redis.RedisService;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.*;

import java.util.List;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.K8sHelper.getPaasServiceEnvProp;
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class DeliveryPriceTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private int localStrategyId;
    private int conditionStrategyId;
    private int globalStrategyId;
    private int selfDeliveryStrategyId;
    private final String STORE_ID = UUID.randomUUID().toString();
    private final String SURGE_STORE_ID = UUID.randomUUID().toString();
    private final String SHIPMENT_ID = UUID.randomUUID().toString();
    private final String CUSTOMER_ID = UUID.randomUUID().toString();
    private final String ANONYMOUS_ID = UUID.randomUUID().toString();
    private final String ORDER_ID = UUID.randomUUID().toString();
    private boolean surgeDisabled;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));

        localStrategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(localStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "0"), 0, "delivery_price"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(localStrategyId, COMPLEX_SCRIPT_NAME, String.format(COMPLEX_SCRIPT_PARAMS, "19900"), 1, "delivery_price"), "{\"Max\": 100000, \"Min\": 0}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(localStrategyId, COMPLEX_SCRIPT_NAME, String.format(COMPLEX_SCRIPT_PARAMS, "9900"), 2, "delivery_price"), "{\"Max\": 300000, \"Min\": 100000}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(localStrategyId, COMPLEX_SCRIPT_NAME, String.format(COMPLEX_SCRIPT_PARAMS, "0"), 3, "delivery_price"), "{\"Max\": 1000000000000000, \"Min\": 300100}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(localStrategyId, "", minCartAmountFirst.toString(), 0, "min_cart"), "{\"Max\": 300000, \"Min\": 0}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(localStrategyId, "", minCartAmountSecond.toString(), 1, "min_cart"), "{\"Max\": 1000000000000000, \"Min\": 300100}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addBinding(localStrategyId, STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());
        addBinding(localStrategyId, SURGE_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());

        conditionStrategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(conditionStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "0"), 0, "delivery_price"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(conditionStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "9900"), 1, "delivery_price"), "{\"Max\": 2000, \"Min\": 0}", ConditionType.ORDER_DISTANCE_RANGE.name().toLowerCase());
        addCondition(addRule(conditionStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "14900"), 2, "delivery_price"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.28.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(conditionStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "19900"), 3, "delivery_price"), "{\"registered_after\": \"2022-06-20T12:00:00Z\"}", ConditionType.REGISTERED_AFTER.name().toLowerCase());
        Integer multipleConditionRuleId = addRule(conditionStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "0"), 4, "delivery_price");
        addCondition(multipleConditionRuleId, "{\"Count\": 2}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(multipleConditionRuleId, "{\"Max\": 1000000000000000, \"Min\": 300000}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(conditionStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "29900"), 5, "delivery_price"), "{}", ConditionType.ALWAYS.name().toLowerCase());
        addCondition(addRule(conditionStrategyId, "", minCartAmountThird.toString(), 0, "min_cart"), "{}", ConditionType.ALWAYS.name().toLowerCase());
        addBinding(conditionStrategyId, STORE_ID, Tenant.INSTAMART.getId(), DeliveryType.COURIER_DELIVERY.toString());
        addBinding(conditionStrategyId, SURGE_STORE_ID, Tenant.INSTAMART.getId(), DeliveryType.COURIER_DELIVERY.toString());

        globalStrategyId = addStrategy(true, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(globalStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "10001"), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(globalStrategyId, "", minCartAmountGlobal.toString(), 0, "min_cart"), "{}", "always");

        selfDeliveryStrategyId = addStrategy(false, 0, DeliveryType.SELF_DELIVERY.toString());
        addCondition(addRule(selfDeliveryStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "10001"), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(selfDeliveryStrategyId, "", minCartAmountFirst.toString(), 0, "min_cart"), "{}", "always");
        addBinding(selfDeliveryStrategyId, STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.SELF_DELIVERY.toString());
        addBinding(selfDeliveryStrategyId, SURGE_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.SELF_DELIVERY.toString());

        RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + SURGE_STORE_ID, String.format(REDIS_VALUE, SURGE_STORE_ID, SURGE_LEVEL, SURGE_LEVEL, SURGE_LEVEL, getZonedUTCDate()), 1000);

        List<String> serviceEnvProperties = getPaasServiceEnvProp(EnvironmentProperties.Env.SHIPPINGCALC_NAMESPACE, " | grep -e SURGE_DISABLED ");
        String envPropsStr = String.join("\n", serviceEnvProperties);
        String surgeDisabledStr = matchWithRegex("^SURGE_DISABLED=(.\\w+)$", envPropsStr, 1);
        surgeDisabled = surgeDisabledStr.equals("true");
    }

    @CaseIDs(value = {@CaseId(355), @CaseId(356), @CaseId(289), @CaseId(321), @CaseId(322), @CaseId(328), @CaseId(330), @CaseId(383), @CaseId(427), @CaseId(512)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по локальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceForLocalStrategy() {
        var request = getDeliveryPriceRequest(
                1, SHIPMENT_ID, true, 1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, minCartAmountFirst, 3, 4, 0, 0);
    }

    @CaseIDs(value = {@CaseId(242), @CaseId(381)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по глобальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceForGlobalStrategy() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, globalStrategyId, 10001, minCartAmountGlobal, 1, 0, 0, 0);
    }

    @CaseIDs(value = {@CaseId(248), @CaseId(325), @CaseId(329), @CaseId(382)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по более приоритетному правилу в стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceHighestPriorityRule() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 90000, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 0, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 0, minCartAmountFirst, 1, 0, 1, 0);
    }

    @CaseId(237)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при расчете цены для магазина, для которого не смогли найти стратегию",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "NOT_FOUND: cannot get delivery price for some shipments, no correct price strategy")
    public void getDeliveryPriceForStoreWithNoBinds() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), false, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.B2B_SELF_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(380)
    @Story("Get Delivery Price")
    @Test(description = "Получение цены доставки для мультизаказа",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceMultipleShipments() {
        String storeId = UUID.randomUUID().toString();
        addBinding(localStrategyId, storeId, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());

        var request = GetDeliveryPriceRequest.newBuilder()
                .addShipments(Shipment.newBuilder()
                        .setPositionsCount(1)
                        .setId(UUID.randomUUID().toString())
                        .setIsOndemand(true)
                        .setWeight(3000)
                        .setItemsCount(3)
                        .setPrice(299700)
                        .setStoreId(STORE_ID)
                        .setStatus("NEW")
                        .setRegionId(1)
                        .setSurgeDeliveryWindowAddition(0)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .addShipments(Shipment.newBuilder()
                        .setPositionsCount(1)
                        .setId(UUID.randomUUID().toString())
                        .setIsOndemand(true)
                        .setWeight(3000)
                        .setItemsCount(3)
                        .setPrice(70000)
                        .setStoreId(storeId)
                        .setStatus("NEW")
                        .setRegionId(1)
                        .setSurgeDeliveryWindowAddition(0)
                        .setLat(55.56f)
                        .setLon(55.56f)
                        .build())
                .setCustomer(Customer.newBuilder()
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
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
        checkDeliveryPrice(response, localStrategyId, 29800, minCartAmountFirst, 3, 4, 0, 0);
        checkDeliveryPrice(response, localStrategyId, 29800, minCartAmountFirst, 3, 4, 0, 1);
    }

    @CaseIDs(value = {@CaseId(240), @CaseId(274), @CaseId(359)})
    @Story("Get Delivery Price")
    @Test(description = "Создание оффера, фиксирующего стоимость доставки",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getDeliveryPriceForLocalStrategy")
    public void getDeliveryPriceOffer() {
        var offerRequest = getDeliveryPriceRequest(
                1, SHIPMENT_ID, true, 1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());
        var newRequest = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var offerResponse = clientShippingCalc.getDeliveryPrice(offerRequest);
        var newResponse = clientShippingCalc.getDeliveryPrice(newRequest);

        Allure.step("Проверяем расчитанную цену доставки по офферу", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(offerResponse.getIsOrderPossible(), "Заказ не возможен");
            softAssert.assertTrue(offerResponse.getShipments(0).getWeHadOffer(), "Получили цену не из оффера");
            softAssert.assertEquals(offerResponse.getTotalShippingPrice(), 19900, "Не ожидаемая цена доставки");
            softAssert.assertEquals(offerResponse.getShipments(0).getStrategyId(), localStrategyId, "Посчитали по неверной стратегии");
            softAssert.assertEquals(offerResponse.getShipments(0).getMinimalCartPrice(), minCartAmountFirst.intValue(), "Отдали неверную цену мин. корзины");
            softAssert.assertEquals(offerResponse.getShipments(0).getLadderCount(), 3, "В лесенке не ожидаемое кол-во ступеней");
            softAssert.assertEquals(offerResponse.getShipments(0).getHintsCount(), 4, "Не ожидаемое кол-во подсказок");
            softAssert.assertEquals(offerResponse.getShipments(0).getPriceExplanation().getPassedConditionsCount(), 0, "Не ожидаемое кол-во прошедших условий");
            softAssert.assertAll();
        });

        Allure.step("Проверяем новую расчитанную цену", () -> checkDeliveryPrice(newResponse, localStrategyId, 19900, minCartAmountFirst, 3, 4, 0, 0));
    }

    @CaseIDs({@CaseId(288), @CaseId(513), @CaseId(541)})
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены с наценкой surge",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithSurge() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITHOUT_THRESHOLDS, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 99, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем наценку по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), (float) SURGE_LEVEL, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 11990, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 31890, minCartAmountFirst + SURGE_LEVEL_ADDITION_DEFAULT, 3, 4, 0, 0);
    }

    @CaseId(537)
    @Story("Get Delivery Price")
    @Test(description = "Наценка по параметрам свитчбек-эксперимента региона",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceWithSwitchbackExperiment() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITH_SWITCHBACK, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 99, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем наценку по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), (float) SURGE_LEVEL, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 11990 + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 31890 + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, minCartAmountFirst + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_SWITCHBACK_ADDITION_DIFF, 3, 4, 0, 0);
    }

    @CaseId(538)
    @Story("Get Delivery Price")
    @Test(description = "Отсутствие наценки по параметрам свитчбек-эксперимента региона при не попадании в временной интервал",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceWithFutureSwitchbackExperiment() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITH_FUTURE_SWITCHBACK, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 99, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем наценку по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), (float) SURGE_LEVEL, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 11990, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 31890, minCartAmountFirst + SURGE_LEVEL_ADDITION_DEFAULT, 3, 4, 0, 0);
    }

    @CaseId(515)
    @Story("Get Delivery Price")
    @Test(description = "Проверка прокидывания не нулевой корзины для не on-demand магазина",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceWithMinCartForPlanned() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), false, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITH_SWITCHBACK, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 99, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        checkDeliveryPrice(response, localStrategyId, 19900, minCartAmountFirst, 3, 4, 0, 0);
    }

    @CaseIDs({@CaseId(540), @CaseId(539)})
    @Story("Get Delivery Price")
    @Test(description = "Наценка по параметрам трешхолдов региона, имеющихся в БД",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceWithSurgeRegionThreshold() {

        if (surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = true");
        }

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITH_THRESHOLDS, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 99, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем наценку по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), (float) SURGE_LEVEL, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 11990 + SURGE_LEVEL_THRESHOLD_ADDITION_DIFF, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 31890 + SURGE_LEVEL_THRESHOLD_ADDITION_DIFF, minCartAmountFirst + SURGE_LEVEL_ADDITION_DEFAULT + SURGE_LEVEL_THRESHOLD_ADDITION_DIFF, 3, 4, 0, 0);
    }

    @CaseId(511)
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены без наценки surge с флагом SURGE_DISABLED",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithoutSurgeFlag() {

        if (!surgeDisabled) {
            throw new SkipException("Пропускаем, потому что SURGE_DISABLED = false");
        }

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITHOUT_THRESHOLDS, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 99, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем наценку по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getShipments(0).getSurgeUsed(), "Surge использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), 0f, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 0, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 19900, minCartAmountFirst, 3, 4, 0, 0);
    }

    @CaseIDs({@CaseId(421), @CaseId(514)})
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены без наценки surge для не on-demand заказа",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithoutSurge() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), false, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITHOUT_THRESHOLDS, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 99, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем отсутствие наценки по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getShipments(0).getSurgeUsed(), "Surge использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), 0f, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 0, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 19900, 0, 3, 4, 0, 0);
    }

    @CaseId(422)
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены без наценки surge для новых клиентов",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithoutSurgeNewCustomers() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITHOUT_THRESHOLDS, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем отсутствие наценки по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getShipments(0).getSurgeUsed(), "Surge использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), 0f, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 0, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 19900, minCartAmountFirst, 3, 4, 0, 0);
    }

    @CaseId(422)
    @Story("Get Delivery Price")
    @Test(description = "Кэширование surgelevel на уровне customer+store",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getDeliveryPriceWithSurge")
    public void getDeliveryPriceWithStoreCustomerSurge() {
        Allure.step("Меняем уровень surge у магазина", () -> RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + SURGE_STORE_ID, String.format(REDIS_VALUE, SURGE_STORE_ID, 0, 0, 0, getZonedUTCDate()), 100));

        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 199900, SURGE_STORE_ID, "NEW", REGION_ID_WITHOUT_THRESHOLDS, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 99, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем получение изначального уровня surge и наценки по нему", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), (float) SURGE_LEVEL, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 10990, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, localStrategyId, 20890, minCartAmountFirst + SURGE_LEVEL_ADDITION_DEFAULT, 3, 4, 0, 0);
    }

    @CaseIDs({@CaseId(456), @CaseId(516)})
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены без наценки surge для самовывоза",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceWithNoSurgeSelfDelivery() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, SURGE_STORE_ID, "NEW", REGION_ID_WITHOUT_THRESHOLDS, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 99, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.SELF_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем отсутствие наценки по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getShipments(0).getSurgeUsed(), "Surge использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), 0f, "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 0, "Не верная наценка");
            softAssert.assertAll();
        });
        checkDeliveryPrice(response, selfDeliveryStrategyId, 10001, 0, 1, 0, 0, 0);
    }

    @CaseId(320)
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены с наценкой слота surge_delivery_window_addition",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithSurgeDeliveryWindowAddition() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, STORE_ID, "NEW", 1, 10000,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 29900, minCartAmountFirst, 3, 4, 0, 0);
    }

    @CaseId(389)
    @Story("Get Delivery Price")
    @Test(description = "Отсутствие прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithEmptyCustomerId() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, "", ANONYMOUS_ID, 0, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, minCartAmountFirst, 3, 4, 0, 0);
    }

    @CaseId(517)
    @Story("Get Delivery Price")
    @Test(description = "Проверка прокидывания нулевой корзины при пустых customer.id и customer.anonymous_id",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceWithEmptyCustomerAndAnonymousId() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, "", "", 0, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, 0, 3, 4, 0, 0);
    }

    @CaseIDs(value = {@CaseId(357), @CaseId(326), @CaseId(332)})
    @Story("Get Delivery Price")
    @Test(description = "Проверка расчета по самому дорогому правилу стратегии, когда не прошли ни по какому условию",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceMostExpensiveRule() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 1000, 1, 300099, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);

        Allure.step("Проверяем расчитанную цену доставки по самому дорогому правилу", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getIsOrderPossible(), "Заказ не возможен");
            softAssert.assertFalse(response.getShipments(0).getWeHadOffer(), "Получили цену из оффера");
            softAssert.assertEquals(response.getTotalShippingPrice(), 19900, "Не ожидаемая общая цена доставки");
            softAssert.assertEquals(response.getShipments(0).getStrategyId(), localStrategyId, "Посчитали по неверной стратегии");
            softAssert.assertEquals(response.getShipments(0).getMinimalCartPrice(), minCartAmountFirst.intValue(), "Отдали неверную цену мин. корзины");
            softAssert.assertEquals(response.getShipments(0).getLadderCount(), 1, "В лесенке не ожидаемое кол-во ступеней");
            softAssert.assertEquals(response.getShipments(0).getHintsCount(), 4, "Не ожидаемое кол-во подсказок");
            softAssert.assertEquals(response.getShipments(0).getPriceExplanation().getPassedConditionsCount(), 0, "Не ожидаемое кол-во прошедших условий");
            softAssert.assertAll();
        });
    }

    @CaseIDs(value = {@CaseId(360), @CaseId(331)})
    @Story("Get Delivery Price")
    @Test(description = "Проверка добавления ценовых компонентов из параметров скрипта",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceOverweight() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 40000, 1, 100000, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 14900, minCartAmountFirst, 3, 4, 0, 0);
        Allure.step("Проверяем наценку за перевес", () -> assertEquals(response.getShipments(0).getPriceExplanation().getPriceComponents(3).getPrice(), 5000, "Не ожидаемая наценка за перевес"));
    }

    @CaseId(208)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом order_id",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty order_id")
    public void getDeliveryPriceWithNoOrderId() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), false, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                "", false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(220)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом shipment_id",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty shipment id")
    public void getDeliveryPriceWithNoShipmentId() {
        var request = getDeliveryPriceRequest(
                1, "", false, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(231)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом positions_count",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: zero positions count")
    public void getDeliveryPriceWithNoPositionsCount() {
        var request = GetDeliveryPriceRequest.newBuilder()
                .addShipments(Shipment.newBuilder()
                        .setId(UUID.randomUUID().toString())
                        .setIsOndemand(false)
                        .setWeight(1000)
                        .setItemsCount(1)
                        .setPrice(99900)
                        .setStoreId(UUID.randomUUID().toString())
                        .setStatus("NEW")
                        .setRegionId(1)
                        .setSurgeDeliveryWindowAddition(0)
                        .setLat(55.55f)
                        .setLon(55.55f)
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
                .build();

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(225)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом store_id",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty shipment store_id")
    public void getDeliveryPriceWithNoStoreId() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), false, 1000, 1, 99900, "", "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(232)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом массиве shipments",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty shipments")
    public void getDeliveryPriceWithNoShipments() {
        var request = GetDeliveryPriceRequest.newBuilder()
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
                .build();

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(214)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом delivery_type",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: invalid delivery type")
    public void getDeliveryPriceWithNoDeliveryType() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), false, 1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 0,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseIDs(value = {@CaseId(385), @CaseId(388)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены доставки при прохождении условия ORDER_DISTANCE_RANGE",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceOrderDistanceRange() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 40000, 1, 100000, STORE_ID, "NEW", 1, 0,
                55.9311, 38.242613, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.9251, 38.242613,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.INSTAMART.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, conditionStrategyId, 9900, minCartAmountThird, 1, 0, 1, 0);
    }

    @CaseId(386)
    @Story("Get Delivery Price")
    @Test(description = "Получение цены доставки при прохождении условия PLATFORMS",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPricePlatforms() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 40000, 1, 100000, STORE_ID, "NEW", 1, 0,
                55.9311, 38.242613, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 50.9211, 30.232613,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.INSTAMART.getId(), AppVersion.IOS.getName(), AppVersion.IOS.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, conditionStrategyId, 14900, minCartAmountThird, 1, 0, 1, 0);
    }

    @CaseId(407)
    @Story("Get Delivery Price")
    @Test(description = "Получение цены доставки при прохождении условия REGISTERED_AFTER",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceRegisteredAfter() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 40000, 1, 100000, STORE_ID, "NEW", 1, 0,
                55.9311, 38.242613, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 50.9211, 30.232613,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.INSTAMART.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, conditionStrategyId, 19900, minCartAmountThird, 1, 0, 1, 0);
    }

    @CaseId(387)
    @Story("Get Delivery Price")
    @Test(description = "Получение цены доставки при прохождении комбинации условий",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceMultipleConditions() {
        var request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), true, 40000, 1, 300000, STORE_ID, "NEW", 1, 0,
                55.9311, 38.242613, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655635906, 50.9211, 30.232613,
                ORDER_ID, false, false, "Картой онлайн", true, DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.INSTAMART.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, conditionStrategyId, 0, minCartAmountThird, 2, 0, 1, 0);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(localStrategyId);
        deleteCreatedStrategy(conditionStrategyId);
        deleteCreatedStrategy(globalStrategyId);
        deleteCreatedStrategy(selfDeliveryStrategyId);
    }
}
