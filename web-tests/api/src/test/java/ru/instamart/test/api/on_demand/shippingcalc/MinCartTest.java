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

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.helper.K8sHelper.getPaasServiceEnvProp;
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.kraken.util.StringUtil.matchWithRegex;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class MinCartTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private int localStrategyId;
    private int globalStrategyId;
    private int tempStrategyId;
    private final String STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String SURGE_STORE_ID = UUID.randomUUID().toString();
    private final String STORE_ID_GLOBAL = UUID.randomUUID().toString();
    private final String CUSTOMER_ID = UUID.randomUUID().toString();
    private final String ANONYMOUS_ID = UUID.randomUUID().toString();
    private Integer minCartAmountFirst = 100000;
    private Integer minCartAmountSecond = minCartAmountFirst - 1;
    private Integer minCartAmountThird = minCartAmountSecond - 1;
    private Integer minCartAmountFourth = minCartAmountThird - 1;
    private Integer minCartAmountGlobal = minCartAmountFirst + 1;
    private Integer deliveryPriceGlobal = 200000;
    private boolean minCartOnlyForSwitchback;

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

        globalStrategyId = addStrategy(true, 0, DeliveryType.B2B.toString());
        addCondition(addRule(globalStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, deliveryPriceGlobal.toString()), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(globalStrategyId, "", minCartAmountGlobal.toString(), 0, "min_cart"), "{}", "always");

        tempStrategyId = addStrategy(false, 0, DeliveryType.B2B_SELF_DELIVERY.toString());
        addCondition(addRule(tempStrategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "10002"), 0, "delivery_price"), "{}", "always");
        addCondition(addRule(tempStrategyId, "", minCartAmountFourth.toString(), 0, "min_cart"), "{}", "always");
        addBinding(tempStrategyId, SURGE_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY.toString());

        RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + SURGE_STORE_ID, String.format(REDIS_VALUE, SURGE_STORE_ID, surgeLevel, surgeLevel, surgeLevel, getZonedUTCDate()), 1000);

        List<String> serviceEnvProperties = getPaasServiceEnvProp(EnvironmentProperties.Env.SHIPPINGCALC_NAMESPACE, " | grep -e MIN_CART_ONLY_FOR_SWITCHBACK ");
        String envPropsStr = String.join("\n", serviceEnvProperties);
        String minCartOnlyForSwitchbackStr = matchWithRegex("^MIN_CART_ONLY_FOR_SWITCHBACK=(.\\w+)$", envPropsStr, 1);
        minCartOnlyForSwitchback = minCartOnlyForSwitchbackStr.equals("true");

        if (minCartOnlyForSwitchback) {
            Allure.step("Зануляем мин. корзину");
            minCartAmountFirst = 0;
            minCartAmountSecond = 0;
            minCartAmountThird = 0;
            minCartAmountFourth = 0;
            minCartAmountGlobal = 0;
        }
    }

    @CaseIDs(value = {@CaseId(411), @CaseId(415)})
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditions() {
        var request = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, minCartAmountThird, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(499)
    @Story("Get Delivery Conditions")
    @Test(description = "Создание оффера, фиксирующего условия доставки",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsFromCache() {
        String customerUuid = UUID.randomUUID().toString();
        int minCartAmountGlobalFromCache = minCartAmountGlobal;
        int deliveryPriceGlobalFromCache = deliveryPriceGlobal;

        var firstRequest = getDeliveryConditionsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);
        var firstResponse = clientShippingCalc.getDeliveryConditions(firstRequest);
        checkDeliveryConditions(firstResponse, STORE_ID_GLOBAL, minCartAmountGlobal, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> compareTwoObjects(firstResponse.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), deliveryPriceGlobal));

        Allure.step("Обновляем значение мин. корзины и цены доставки", () -> {
            boolean ruleUpdatedMinCart = RulesDao.INSTANCE.updateRuleParams("222222", globalStrategyId, 0, "min_cart");
            boolean ruleUpdatedDeliveryPrice = RulesDao.INSTANCE.updateRuleParams(String.format(FIXED_SCRIPT_PARAMS, "222222"), globalStrategyId, 0, "delivery_price");
            if (ruleUpdatedMinCart) {
                minCartAmountGlobal = 222222;
            }
            if (ruleUpdatedDeliveryPrice) {
                deliveryPriceGlobal = 222222;
            }
        });

        var secondRequest = getDeliveryConditionsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);
        var secondResponse = clientShippingCalc.getDeliveryConditions(secondRequest);
        checkDeliveryConditions(secondResponse, STORE_ID_GLOBAL, minCartAmountGlobalFromCache, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> compareTwoObjects(secondResponse.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), deliveryPriceGlobalFromCache));
    }

    @CaseId(412)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для нескольких магазинов",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsMultipleStores() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .addStores(Store.newBuilder()
                        .setId(SECOND_STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
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

    @CaseId(411)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки по глобальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsGlobalStrategy() {
        var request = getDeliveryConditionsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID_GLOBAL, minCartAmountGlobal, 1, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> compareTwoObjects(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), deliveryPriceGlobal));
    }

    @CaseId(414)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина с повышенным спросом",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsWithSurge() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + surgeLevelAddition, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
        Allure.step("Проверяем наличие повышенного спроса", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж выключен");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), (float) surgeLevel, "Не ожидаемый уровень сюрджа");
            softAssert.assertTrue(response.getDeliveryConditions(0).getSurge().getTtl() > 0, "Не ожидаемый уровень ttl сюрджа");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), 31890, "Не ожидаемая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getShippingPrice(), 20890, "Не ожидаемая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(449)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для не on-demand магазина",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryConditionsWithNoSurge() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
        Allure.step("Проверяем отсутствие повышенного спроса", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж включен");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), 0f, "Не ожидаемый уровень сюрджа");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), 19900, "Не ожидаемая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getShippingPrice(), 9900, "Не ожидаемая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(424)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для новых клиентов",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsWithoutSurgeNewCustomers() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
        Allure.step("Проверяем отсутствие повышенного спроса", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж включен");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), 19900, "Не ожидаемая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getShippingPrice(), 9900, "Не ожидаемая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(440)
    @Story("Get Delivery Conditions")
    @Test(description = "Кэширование surgelevel на уровне customer+store",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getDeliveryConditionsWithSurge")
    public void getDeliveryConditionsWithStoreCustomerSurge() {
        Allure.step("Меняем уровень surge у магазина", () -> RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + SURGE_STORE_ID, String.format(REDIS_VALUE, SURGE_STORE_ID, 0, 0, 0, getZonedUTCDate()), 1000));

        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountThird + surgeLevelAddition, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
        Allure.step("Проверяем наличие повышенного спроса", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж выключен");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), (float) surgeLevel, "Не ожидаемый уровень сюрджа");
            softAssert.assertTrue(response.getDeliveryConditions(0).getSurge().getTtl() > 0, "Не ожидаемый уровень ttl сюрджа");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), 31890, "Не ожидаемая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getShippingPrice(), 20890, "Не ожидаемая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(458)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для самовывоза",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryConditionsWithNoSurgeSelfDelivery() {
        var request = getDeliveryConditionsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), true);

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, SURGE_STORE_ID, minCartAmountFourth, 1, 3);
        Allure.step("Проверяем отсутствие повышенного спроса", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertFalse(response.getDeliveryConditions(0).getSurge().getIsOn(), "Сюрдж включен");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().getLevel(), 0f, "Не ожидаемый уровень сюрджа");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getShippingPrice(), 10002, "Не ожидаемая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(420)
    @Story("Get Delivery Conditions")
    @Test(description = "Проверка прохождений условий в правилах мин корзины",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsDifferentRules() {
        var firstRequest = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);
        var secondRequest = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE,
                AppVersion.IOS.getName(), AppVersion.IOS.getVersion(), false);

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

    @CaseId(416)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина у которого не нашли связку",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryConditionsNotFound() {
        String storeId = UUID.randomUUID().toString();
        var request = getDeliveryConditionsRequest(storeId, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE, AppVersion.WEB.getName(), AppVersion.WEB.getVersion(), false);

        var response = clientShippingCalc.getDeliveryConditions(request);

        Allure.step("Проверяем пустой ответ", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getStoreId(), storeId, "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getSurge().toString(), "", "Не пустой surge в ответе");
            softAssert.assertFalse(response.getDeliveryConditions(0).getLadderCount() > 0, "Не пустой ladder в ответе");
            softAssert.assertAll();
        });
    }

    @CaseId(417)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение ошибки при пустом stores",
            groups = "dispatch-shippingcalc-regress",
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

    @CaseId(418)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение ошибки при пустом tenant",
            groups = "dispatch-shippingcalc-regress",
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

    @CaseId(419)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение ошибки при невалидном delivery_type",
            groups = "dispatch-shippingcalc-regress",
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

    @CaseId(428)
    @Story("Get Delivery Conditions")
    @Test(description = "Отсутствие прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryConditionsNoCustomerId() {
        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
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
        checkDeliveryConditions(response, STORE_ID, minCartAmountThird, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseIDs(value = {@CaseId(391), @CaseId(453)})
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmounts() {
        var request = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, minCartAmountThird);
    }

    @CaseId(498)
    @Story("Get Min Cart Amounts")
    @Test(description = "Создание оффера, фиксирующего стоимость мин. корзины",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsFromCache() {
        String customerUuid = UUID.randomUUID().toString();
        int minCartAmountGlobalFromCache = minCartAmountGlobal;

        var firstRequest = getMinCartAmountsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);
        var firstResponse = clientShippingCalc.getMinCartAmounts(firstRequest);
        checkMinCartAmounts(firstResponse, STORE_ID_GLOBAL, minCartAmountGlobal);

        Allure.step("Обновляем значение мин. корзины", () -> {
            boolean ruleUpdatedMinCart = RulesDao.INSTANCE.updateRuleParams("111111", globalStrategyId, 0, "min_cart");
            if (ruleUpdatedMinCart) {
                minCartAmountGlobal = 111111;
            }
        });

        var secondRequest = getMinCartAmountsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, customerUuid, customerUuid,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);
        var secondResponse = clientShippingCalc.getMinCartAmounts(secondRequest);
        checkMinCartAmounts(secondResponse, STORE_ID_GLOBAL, minCartAmountGlobalFromCache);
    }

    @CaseId(392)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины по нескольким магазинам",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsMultipleStores() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .addStores(Store.newBuilder()
                        .setId(SECOND_STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
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

    @CaseId(438)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины по глобальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsGlobalStrategy() {
        var request = getMinCartAmountsRequest(STORE_ID_GLOBAL, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID_GLOBAL, minCartAmountGlobal);
    }

    @CaseId(413)
    @Story("Get Min Cart Amounts")
    @Test(description = "Проверка прохождений условий в правилах мин корзины",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsDifferentRules() {
        var firstRequest = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);
        var secondRequest = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);

        var firstResponse = clientShippingCalc.getMinCartAmounts(firstRequest);
        var secondResponse = clientShippingCalc.getMinCartAmounts(secondRequest);

        checkMinCartAmounts(firstResponse, STORE_ID, minCartAmountFirst);
        checkMinCartAmounts(secondResponse, STORE_ID, minCartAmountThird);
    }

    @CaseId(451)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины для магазина с повышенным спросом",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsWithSurge() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + surgeLevelAddition);
    }

    @CaseId(497)
    @Story("Get Min Cart Amounts")
    @Test(description = "Кэширование surgelevel на уровне customer+store",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getDeliveryConditionsWithStoreCustomerSurge")
    public void getMinCartAmountsWithStoreCustomerSurge() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + surgeLevelAddition);
    }

    @CaseId(452)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины без повышенного спроса для не on_demand магазина",
            groups = "dispatch-shippingcalc-regress")
    public void getMinCartAmountsWithSurgeNotOnDemand() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird);
    }

    @CaseId(454)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины для магазина без повышенного спроса для новых клиентов",
            groups = "dispatch-shippingcalc-regress")
    public void getMinCartAmountsWithSurgeNewCustomer() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, true);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird);
    }

    @CaseId(457)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины без повышенного спроса для самовывоза",
            groups = "dispatch-shippingcalc-regress")
    public void getMinCartAmountsWithNoSurgeSelfDelivery() {
        var request = getMinCartAmountsRequest(SURGE_STORE_ID, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE, true);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, SURGE_STORE_ID, minCartAmountThird + surgeLevelAddition);
        Allure.step("Проверяем отсутствия наценки surge для самовывоза", () -> compareTwoObjects(response.getMinCartAmounts(0).getAmountSelfDelivery(), minCartAmountFourth.longValue()));
    }

    @CaseId(450)
    @Story("Get Min Cart Amounts")
    @Test(description = "Проверка наличия мин. корзины для доставки курьером и самовывоза",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsSelfAndCourier() {
        String storeId = UUID.randomUUID().toString();
        addBinding(tempStrategyId, storeId, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY.toString());

        var request = getMinCartAmountsRequest(storeId, 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_VALUE, false);

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем отдачу мин. корзины магазина и для самовывоза и для доставки", () -> {
            assertTrue(response.getMinCartAmountsCount() > 0, "Пустая мин. корзина");
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getMinCartAmounts(0).getStoreId(), storeId, "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getMinCartAmounts(0).getAmountCourierDelivery(), minCartAmountGlobal.intValue(), "Не ожидаемая минимальная корзина для доставки");
            softAssert.assertEquals(response.getMinCartAmounts(0).getAmountSelfDelivery(), minCartAmountFourth.intValue(), "Не ожидаемая минимальная корзина для самовывоза");
            softAssert.assertAll();
        });
    }

    @CaseId(393)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка для магазина у которого не нашли связку",
            groups = "dispatch-shippingcalc-regress")
    public void getMinCartAmountsNotFound() {
        var request = getMinCartAmountsRequest(UUID.randomUUID().toString(), 55.55f, 55.55f, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.B2B_SELF_DELIVERY_VALUE, false);

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем пустое значение", () -> compareTwoObjects(response.getMinCartAmounts(0).getAmountSelfDelivery(), 0L));
    }

    @CaseId(398)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение ошибки при невалидном delivery_type",
            groups = "dispatch-shippingcalc-regress",
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

    @CaseId(395)
    @Story("Get Min Cart Amounts")
    @Test(description = "Отсутствие прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID",
            groups = "dispatch-shippingcalc-regress")
    public void getMinCartAmountsNoCustomerId() {
        var request = GetMinCartAmountsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
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
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, minCartAmountThird);
    }

    @CaseId(396)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка при пустом stores",
            groups = "dispatch-shippingcalc-regress",
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

    @CaseId(397)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка при пустом tenant",
            groups = "dispatch-shippingcalc-regress",
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
        deleteCreatedStrategy(tempStrategyId);
    }
}
