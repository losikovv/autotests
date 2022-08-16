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
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.*;
import surgelevelevent.Surgelevelevent;

import java.util.UUID;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.kafka.configs.KafkaConfigs.configSurgeLevel;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class MinCartTest extends ShippingCalcBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer strategyId;
    private final String STORE_ID = UUID.randomUUID().toString();
    private final String CUSTOMER_ID = UUID.randomUUID().toString();
    private final String ANONYMOUS_ID = UUID.randomUUID().toString();
    private final String FIXED_SCRIPT_NAME = "Фиксированная цена, с подсказками и объяснением";
    private final String FIXED_SCRIPT_PARAMS = "{\"basicPrice\": \"%s\", \"bagIncrease\": \"0\", \"assemblyIncrease\": \"0\"}";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));

        strategyId = addStrategy(false, 0, DeliveryType.COURIER_DELIVERY.toString());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "4900"), 0, "delivery_price"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "14900"), 1, "delivery_price"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.29.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "19900"), 2, "delivery_price"), "{\"Max\": 300000, \"Min\": 0}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "9900"), 3, "delivery_price"), "{}", ConditionType.ALWAYS.name().toLowerCase()); // заменить эту строку строкой ниже после фикса HG-837
        //addCondition(addRule(strategyId, FIXED_SCRIPT_NAME, String.format(FIXED_SCRIPT_PARAMS, "9900"), 3, "delivery_price"), "{\"Max\": 1000000000000000, \"Min\": 300000}", ConditionType.ORDER_VALUE_RANGE.name().toLowerCase());
        addCondition(addRule(strategyId, "", "50000", 0, "min_cart"), "{\"Count\": 1}", ConditionType.FIRST_N_ORDERS.name().toLowerCase());
        addCondition(addRule(strategyId, "", "150000", 1, "min_cart"), "{\"platforms\": [{\"name\": \"SbermarketIOS\", \"version\": \"6.29.0\"}]}", ConditionType.PLATFORMS.name().toLowerCase());
        addCondition(addRule(strategyId, "", "100000", 2, "min_cart"), "{}", ConditionType.ALWAYS.name().toLowerCase());
        addBinding(strategyId, STORE_ID, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());
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
    @Issue("HG-844")
    @Story("Get Delivery Conditions")
    @Test(description = "Получение условий доставки для нескольких магазинов",
            groups = "dispatch-shippingcalc-smoke",
            enabled = false)
    public void getDeliveryConditionsMultipleStores() {
        String secondStore = UUID.randomUUID().toString();
        addBinding(strategyId, secondStore, Tenant.SBERMARKET.getId(), DeliveryType.COURIER_DELIVERY.toString());

        var request = GetDeliveryConditionsRequest.newBuilder()
                .addStores(Store.newBuilder()
                        .setId(STORE_ID)
                        .setLat(55.55f)
                        .setLon(55.55f)
                        .build())
                .addStores(Store.newBuilder()
                        .setId(secondStore)
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
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(response.getDeliveryConditions(0).getStoreId(), STORE_ID, "Не ожидаемый uuid магазина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getMinCartAmount(), 100000, "Не ожидаемая минимальная корзина");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadderCount(), 2, "Не ожидаемое кол-во ступеней в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponentsCount(), 3, "Не ожидаемое кол-во компонентов цены");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(0).getPriceComponents(0).getPrice(), 19900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(0).getLadder(1).getPriceComponents(0).getPrice(), 9900, "Не ожидаемая базовая цена в лесенке");
            softAssert.assertEquals(response.getDeliveryConditions(1).getStoreId(), secondStore, "Не ожидаемый uuid магазина");
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

        Surgelevelevent.SurgeEvent surgeEvent = Surgelevelevent.SurgeEvent.newBuilder()
                .setStoreId(storeWithSurge)
                .setMethod(Surgelevelevent.SurgeEvent.Method.ACTUAL)
                .setPastSurgeLevel(surgeLevel)
                .setPresentSurgeLevel(surgeLevel)
                .setFutureSurgeLevel(surgeLevel)
                .setStartedAt(getTimestamp())
                .setStepSurgeLevel(1)
                .build();
        kafka.publish(configSurgeLevel(), surgeEvent.toByteArray());

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

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(strategyId);
    }
}
