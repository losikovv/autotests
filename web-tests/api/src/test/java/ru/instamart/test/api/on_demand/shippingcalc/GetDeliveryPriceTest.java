package ru.instamart.test.api.on_demand.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.instamart.kraken.enums.AppVersion;
import ru.instamart.kraken.enums.Tenant;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;
import shippingcalc.ShippingcalcOuterClass;
import surgelevelevent.Surgelevelevent;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.ShippingCalcHelper.*;
import static ru.instamart.kafka.configs.KafkaConfigs.configSurgeLevel;
import static ru.instamart.kraken.util.TimeUtil.getTimestamp;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class GetDeliveryPriceTest extends RestBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer localStrategyId;
    private Integer firstGlobalStrategyId;
    private Integer secondGlobalStrategyId;
    private final String STORE_ID = UUID.randomUUID().toString();
    private final String PRODUCT_ID = UUID.randomUUID().toString();
    private final String SHIPMENT_ID = UUID.randomUUID().toString();
    private final String CUSTOMER_ID = UUID.randomUUID().toString();
    private final String ANONYMOUS_ID = UUID.randomUUID().toString();
    private final String ORDER_ID = UUID.randomUUID().toString();

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
        localStrategyId = addStrategy(false, 0, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY.toString());
        firstGlobalStrategyId = addStrategy(true, 1, ShippingcalcOuterClass.DeliveryType.B2B.toString());
        secondGlobalStrategyId = addStrategy(true, 0, ShippingcalcOuterClass.DeliveryType.B2B.toString());
        addBinding(localStrategyId, STORE_ID, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY.toString());
    }

    @CaseIDs(value = {@CaseId(355), @CaseId(356), @CaseId(289), @CaseId(321), @CaseId(322), @CaseId(328), @CaseId(330)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по локальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceForLocalStrategy() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 99900, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, 100000, 3, 4, 0, 0);
    }

    @CaseIDs(value = {@CaseId(242), @CaseId(244)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по глобальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceForGlobalStrategy() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, UUID.randomUUID().toString(), false,
                1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.B2B_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondGlobalStrategyId, 19900, 100000, 3, 4, 0, 0);
    }

    @CaseIDs(value = {@CaseId(248), @CaseId(325), @CaseId(329)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по более приоритетному правилу в стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceHighestPriorityRule() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 90000, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 90000, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 0, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 0, 100000, 1, 0, 1, 0);
    }

    @CaseId(237)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при расчете цены для магазина, для которого не смогли найти стратегию",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot GetDeliveryPrice: can't calculate price for some shipments")
    public void getDeliveryPriceForStoreWithNoBinds() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, UUID.randomUUID().toString(), false,
                1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(380)
    @Story("Get Delivery Price")
    @Test(description = "Получение цены доставки для мультизаказа",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceMultipleShipments() {
        String storeId = UUID.randomUUID().toString();
        addBinding(localStrategyId, storeId, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY.toString());

        ShippingcalcOuterClass.GetDeliveryPriceRequest request = ShippingcalcOuterClass.GetDeliveryPriceRequest.newBuilder()
                .addShipments(ShippingcalcOuterClass.Shipment.newBuilder()
                        .addProducts(ShippingcalcOuterClass.ProductRequest.newBuilder()
                                .setQuantity(1)
                                .setId(UUID.randomUUID().toString())
                                .setPrice(99900)
                                .setDiscountPrice(0)
                                .setWeight(1000)
                                .build())
                        .addProducts(ShippingcalcOuterClass.ProductRequest.newBuilder()
                                .setQuantity(2)
                                .setId(UUID.randomUUID().toString())
                                .setPrice(99900)
                                .setDiscountPrice(0)
                                .setWeight(1000)
                                .build())
                        .setId(UUID.randomUUID().toString())
                        .setIsOndemand(false)
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
                .addShipments(ShippingcalcOuterClass.Shipment.newBuilder()
                        .addProducts(ShippingcalcOuterClass.ProductRequest.newBuilder()
                                .setQuantity(2)
                                .setId(UUID.randomUUID().toString())
                                .setPrice(10000)
                                .setDiscountPrice(0)
                                .setWeight(1000)
                                .build())
                        .addProducts(ShippingcalcOuterClass.ProductRequest.newBuilder()
                                .setQuantity(1)
                                .setId(UUID.randomUUID().toString())
                                .setPrice(50000)
                                .setDiscountPrice(0)
                                .setWeight(1000)
                                .build())
                        .setId(UUID.randomUUID().toString())
                        .setIsOndemand(false)
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
                .setCustomer(ShippingcalcOuterClass.Customer.newBuilder()
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
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE)
                .setTenantId(Tenant.SBERMARKET.getId())
                .setPlatformName(AppVersion.WEB.getName())
                .setPlatformVersion(AppVersion.WEB.getVersion())
                .build();

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 29800, 100000, 3, 4, 0, 0);
        checkDeliveryPrice(response, localStrategyId, 29800, 100000, 3, 4, 0, 1);
    }

    @CaseIDs(value = {@CaseId(240), @CaseId(274), @CaseId(1)})
    @Story("Get Delivery Price")
    @Test(description = "Создание оффера, фиксирующего стоимость доставки",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getDeliveryPriceForLocalStrategy")
    public void getDeliveryPriceOffer() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest offerRequest = getDeliveryPriceRequest(
                1, PRODUCT_ID, 99900, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());
        ShippingcalcOuterClass.GetDeliveryPriceRequest newRequest = getDeliveryPriceRequest(
                1, PRODUCT_ID, 99900, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var offerResponse = clientShippingCalc.getDeliveryPrice(offerRequest);
        var newResponse = clientShippingCalc.getDeliveryPrice(newRequest);

        Allure.step("Проверяем расчитанную цену доставки по офферу", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(offerResponse.getIsOrderPossible(), "Заказ не возможен");
            softAssert.assertTrue(offerResponse.getShipments(0).getWeHadOffer(), "Получили цену не из оффера");
            softAssert.assertEquals(offerResponse.getTotalShippingPrice(), 19900, "Не ожидаемая цена доставки");
            softAssert.assertEquals((Integer) offerResponse.getShipments(0).getStrategyId(), localStrategyId, "Посчитали по неверной стратегии");
            softAssert.assertEquals(offerResponse.getShipments(0).getMinimalCartPrice(), 100000, "Отдали неверную цену мин. корзины");
            softAssert.assertEquals(offerResponse.getShipments(0).getLadderCount(), 3, "В лесенке не ожидаемое кол-во ступеней");
            softAssert.assertEquals(offerResponse.getShipments(0).getHintsCount(), 4, "Не ожидаемое кол-во подсказок");
            softAssert.assertEquals(offerResponse.getShipments(0).getPriceExplanation().getPassedConditionsCount(), 0, "Не ожидаемое кол-во прошедших условий");
            softAssert.assertAll();

        });

        Allure.step("Проверяем новую расчитанную цену", () -> checkDeliveryPrice(newResponse, localStrategyId, 19900, 100000, 3, 4, 0, 0));
    }

    @CaseId(288)
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены с наценкой surge",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithSurge() {
        String storeId = UUID.randomUUID().toString();
        Integer surgeLevel = 5;
        addBinding(localStrategyId, storeId, Tenant.SBERMARKET.getId(), ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY.toString());

        ShippingcalcOuterClass.SetIntervalsSurgeRequest surgeIntervals = ShippingcalcOuterClass.SetIntervalsSurgeRequest.newBuilder()
                .addIntervals(ShippingcalcOuterClass.SurgeInterval.newBuilder()
                        .setLeftBoundary(0)
                        .setRightBoundary(1)
                        .setPriceAddition(0)
                        .setPercentAddition(0)
                        .build())
                .addIntervals(ShippingcalcOuterClass.SurgeInterval.newBuilder()
                        .setLeftBoundary(1)
                        .setRightBoundary(surgeLevel)
                        .setPriceAddition(10000)
                        .setPercentAddition(10)
                        .build())
                .addIntervals(ShippingcalcOuterClass.SurgeInterval.newBuilder()
                        .setLeftBoundary(surgeLevel)
                        .setRightBoundary(10)
                        .setPriceAddition(20000)
                        .setPercentAddition(20)
                        .build())
                .build();
        clientShippingCalc.setIntervalsSurge(surgeIntervals);

        Surgelevelevent.SurgeEvent surgeEvent = Surgelevelevent.SurgeEvent.newBuilder()
                .setStoreId(storeId)
                .setMethod(Surgelevelevent.SurgeEvent.Method.ACTUAL)
                .setPastSurgeLevel(surgeLevel)
                .setPresentSurgeLevel(surgeLevel)
                .setFutureSurgeLevel(surgeLevel)
                .setStartedAt(getTimestamp())
                .setStepSurgeLevel(1)
                .build();
        kafka.publish(configSurgeLevel(), surgeEvent.toByteArray());

        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 99900, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 99900, storeId, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, 100000, 3, 4, 0, 0); // наценка по surgelevel не добавляется к финальной цене (пока прибито гвоздями в коде), если тут начнет падать, нужно изменить ожидание на финальную цену с наценкой
        Allure.step("Проверяем наценку по surge", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(response.getShipments(0).getSurgeUsed(), "Surge не использовался при расчете цены");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevel(), surgeLevel.floatValue(), "Не верный surgelevel");
            softAssert.assertEquals(response.getShipments(0).getSurgeLevelAddition(), 11990, "Не верная наценка");
            softAssert.assertAll();
        });
    }

    @CaseId(320)
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены с наценкой слота surge_delivery_window_addition",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithSurgeDeliveryWindowAddition() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 99900, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 99900, STORE_ID, "NEW", 1, 10000,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 29900, 100000, 3, 4, 0, 0);
    }

    @CaseId(389)
    @Story("Get Delivery Price")
    @Test(description = "Отсутствие прохождения по правилу FIRST_N_ORDERS при пустом Customers.ID",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithEmptyCustomerId() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 99900, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 99900, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, "", ANONYMOUS_ID, 0, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, 100000, 3, 4, 0, 0);
    }

    @CaseIDs(value = {@CaseId(357), @CaseId(326), @CaseId(332)})
    @Story("Get Delivery Price")
    @Test(description = "Проверка расчета по самому дорогому правилу стратегии, когда не прошли ни по какому условию",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceMostExpensiveRule() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 300099, 0, 1000, SHIPMENT_ID, false,
                1000, 1, 300099, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 19900, 100000, 1, 4, 0, 0);
    }

    @CaseIDs(value = {@CaseId(360), @CaseId(331)})
    @Story("Get Delivery Price")
    @Test(description = "Проверка добавления ценовых компонентов из параметров скрипта",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceOverweight() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, PRODUCT_ID, 100000, 0, 40000, SHIPMENT_ID, false,
                40000, 1, 100000, STORE_ID, "NEW", 1, 0,
                55.55, 55.55, CUSTOMER_ID, ANONYMOUS_ID, 1, 1655822708, 55.57, 55.57,
                ORDER_ID, false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, localStrategyId, 14900, 100000, 3, 4, 0, 0);
        Allure.step("Проверяем наценку за перевес", () -> assertEquals(response.getShipments(0).getPriceExplanation().getPriceComponents(3).getPrice(), 5000, "Не ожидаемая наценка за перевес"));
    }

    @CaseId(208)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом order_id",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty order_id")
    public void getDeliveryPriceWithNoOrderId() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, UUID.randomUUID().toString(), false,
                1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                "", false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
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
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, "", false,
                1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseId(231)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при пустом products",
            groups = "dispatch-shippingcalc-regress",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INVALID_ARGUMENT: empty shipment products")
    public void getDeliveryPriceWithNoProducts() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = ShippingcalcOuterClass.GetDeliveryPriceRequest.newBuilder()
                .addShipments(ShippingcalcOuterClass.Shipment.newBuilder()
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
                .setCustomer(ShippingcalcOuterClass.Customer.newBuilder()
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
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE)
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
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, UUID.randomUUID().toString(), false,
                1000, 1, 99900, "", "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE,
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
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = ShippingcalcOuterClass.GetDeliveryPriceRequest.newBuilder()
                .setCustomer(ShippingcalcOuterClass.Customer.newBuilder()
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
                .setDeliveryTypeValue(ShippingcalcOuterClass.DeliveryType.COURIER_DELIVERY_VALUE)
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
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, UUID.randomUUID().toString(), false,
                1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 0,
                Tenant.SBERMARKET.getId(), AppVersion.WEB.getName(), AppVersion.WEB.getVersion());

        clientShippingCalc.getDeliveryPrice(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(localStrategyId);
        deleteCreatedStrategy(firstGlobalStrategyId);
        deleteCreatedStrategy(secondGlobalStrategyId);
    }
}
