package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.ShippingCalcBase;
import ru.instamart.grpc.common.GrpcContentHosts;
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
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.kraken.util.TimeUtil.getZonedUTCDate;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class MinCartTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer strategyId;
    private final String STORE_ID = UUID.randomUUID().toString();
    private final String SECOND_STORE_ID = UUID.randomUUID().toString();
    private final String CUSTOMER_ID = UUID.randomUUID().toString();
    private final String ANONYMOUS_ID = UUID.randomUUID().toString();
    private final String FIXED_SCRIPT_NAME = "Фиксированная цена, с подсказками и объяснением";
    private final String FIXED_SCRIPT_PARAMS = "{\"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";
    private final String REDIS_VALUE = "{\"StoreID\":\"%s\",\"Method\":1,\"PastSurgeLever\":%d,\"PresentSurgeLevel\":%d,\"FutureSurgeLevel\":%d,\"StartedAt\":\"%s\",\"StepSurgeLevel\":1}";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));

        strategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "4900"), 0, "delivery_price"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "14900"), 1, "delivery_price"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.29.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "19900"), 2, "delivery_price"), "{\"Max\": 300000, \"Min\": 0}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "9900"), 3, "delivery_price"), "{\"Max\": 1000000000000000, \"Min\": 300000}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(strategyId, "", "50000", 0, "min_cart"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(strategyId, "", "150000", 1, "min_cart"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.29.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(strategyId, "", "100000", 2, "min_cart"), "{}", ConditionType.ALWAYS.name().toLowerCase());
        addBinding(strategyId, STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());
        addBinding(strategyId, SECOND_STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(411), @CaseId(415)})
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditions() {
        var request = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, 100000, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
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
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryConditions(request);

        Allure.step("Проверяем условия доставки для нескольких магазинов", () -> {
            assertTrue(response.getDeliveryConditionsCount() > 0, "Пустые условия доставки");

            List<String> storeUuids = List.of(STORE_ID, SECOND_STORE_ID);

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(storeUuids.contains(response.getDeliveryConditions(0).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getMinCartAmount(), 100000, "Не ожидаемая минимальная корзина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadderCount(), 2, "Не ожидаемое кол-во ступеней в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponentsCount(), 3, "Не ожидаемое кол-во компонентов цены");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertTrue(storeUuids.contains(response.getDeliveryConditions(1).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(1).getMinCartAmount(), 100000, "Не ожидаемая минимальная корзина");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadderCount(), 2, "Не ожидаемое кол-во ступеней в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadder(0).getPriceComponentsCount(), 3, "Не ожидаемое кол-во компонентов цены");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(1).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(414)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина с повышенным спросом",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsWithSurge() {
        String storeWithSurge = UUID.randomUUID().toString();
        addBinding(strategyId, storeWithSurge, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());
        RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + storeWithSurge, String.format(REDIS_VALUE, storeWithSurge, surgeLevel, surgeLevel, surgeLevel, getZonedUTCDate()), 100);

        var request = getDeliveryConditionsRequest(storeWithSurge, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                99, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, storeWithSurge, 100000, 2, 3);
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

    @CaseId(424)
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для магазина без повышенного спроса для новых клиентов",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsWithoutSurgeNewCustomers() {
        String storeWithSurge = UUID.randomUUID().toString();
        addBinding(strategyId, storeWithSurge, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());
        RedisService.set(RedisManager.getConnection(Redis.SHIPPINGCALC), "store:" + storeWithSurge, String.format(REDIS_VALUE, storeWithSurge, surgeLevel, surgeLevel, surgeLevel, getZonedUTCDate()), 100);

        var request = getDeliveryConditionsRequest(storeWithSurge, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryConditions(request);

        checkDeliveryConditions(response, storeWithSurge, 100000, 2, 3);
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

    @CaseId(420)
    @Story("Get Delivery Conditions")
    @Test(description = "Проверка прохождений условий в правилах мин корзины",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryConditionsDifferentRules() {
        var firstRequest = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE,
                AppVersion.WEB.getName(), AppVersion.WEB.getVersion());
        var secondRequest = getDeliveryConditionsRequest(STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE,
                AppVersion.IOS.getName(), AppVersion.IOS.getVersion());

        var firstResponse = clientShippingCalc.getDeliveryConditions(firstRequest);
        var secondResponse = clientShippingCalc.getDeliveryConditions(secondRequest);

        checkDeliveryConditions(firstResponse, STORE_ID, 50000, 1, 3);
        checkDeliveryConditions(secondResponse, STORE_ID, 150000, 1, 3);
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
        var request = getDeliveryConditionsRequest(storeId, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE, AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

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
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
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
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
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
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryConditions(request);
        checkDeliveryConditions(response, STORE_ID, 100000, 2, 3);
        Allure.step("Проверяем базовую цену в лесенке", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertAll();
        });
    }

    @CaseId(391)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение минимальной корзины с валидными данными",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmounts() {
        var request = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE);

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, 100000);
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
                        .setId(CUSTOMER_ID)
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(1)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем условия доставки для нескольких магазинов", () -> {
            assertTrue(response.getMinCartAmountsCount() > 0, "Пустые мин. корзины");

            List<String> storeUuids = List.of(STORE_ID, SECOND_STORE_ID);

            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(storeUuids.contains(response.getMinCartAmounts(0).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getMinCartAmounts(0).getAmount(), 100000, "Не ожидаемая минимальная корзина");
            softAssert.assertTrue(storeUuids.contains(response.getMinCartAmounts(1).getStoreId()), "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getMinCartAmounts(1).getAmount(), 100000, "Не ожидаемая минимальная корзина");
            softAssert.assertAll();
        });
    }

    @CaseId(413)
    @Story("Get Min Cart Amounts")
    @Test(description = "Проверка прохождений условий в правилах мин корзины",
            groups = "dispatch-shippingcalc-smoke")
    public void getMinCartAmountsDifferentRules() {
        var firstRequest = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE);
        var secondRequest = getMinCartAmountsRequest(STORE_ID, 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                1, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE);

        var firstResponse = clientShippingCalc.getMinCartAmounts(firstRequest);
        var secondResponse = clientShippingCalc.getMinCartAmounts(secondRequest);

        checkMinCartAmounts(firstResponse, STORE_ID, 50000);
        checkMinCartAmounts(secondResponse, STORE_ID, 100000);
    }

    @CaseId(393)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение пустого списка для магазина у которого не нашли связку",
            groups = "dispatch-shippingcalc-regress")
    public void getMinCartAmountsNotFound() {
        var request = getMinCartAmountsRequest(UUID.randomUUID().toString(), 55.55f, 55.55f, CUSTOMER_ID, ANONYMOUS_ID,
                0, 1655822708, 55.55f, 55.55f, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY_VALUE);

        var response = clientShippingCalc.getMinCartAmounts(request);

        Allure.step("Проверяем пустой ответ", () -> {
            compareTwoObjects(response.toString(), "");
        });
    }

    @CaseId(398)
    @Story("Get Min Cart Amounts")
    @Test(description = "Получение ошибки при невалидном delivery_type",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot get min cart amount: .*")
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
                        .setAnonymousId(ANONYMOUS_ID)
                        .setOrdersCount(0)
                        .setRegisteredAt(1655822708)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .setTenant(Tenant.SBERMARKET.getId())
                .setDeliveryTypeValue(DeliveryType.COURIER_DELIVERY_VALUE)
                .build();

        var response = clientShippingCalc.getMinCartAmounts(request);
        checkMinCartAmounts(response, STORE_ID, 100000);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(strategyId);
    }
}
