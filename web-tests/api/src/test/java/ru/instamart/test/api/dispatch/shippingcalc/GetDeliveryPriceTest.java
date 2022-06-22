package ru.instamart.test.api.dispatch.shippingcalc;

import io.grpc.StatusRuntimeException;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.grpc.common.GrpcContentHosts;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import shippingcalc.ShippingcalcGrpc;
import shippingcalc.ShippingcalcOuterClass;

import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.helper.ShippingCalcHelper.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
@Epic("On Demand")
@Feature("ShippingCalc")
public class GetDeliveryPriceTest extends RestBase {

    private ShippingcalcGrpc.ShippingcalcBlockingStub clientShippingCalc;
    private Integer firstLocalStrategyId;
    private Integer secondLocalStrategyId;
    private Integer firstGlobalStrategyId;
    private Integer secondGlobalStrategyId;
    private String storeId = UUID.randomUUID().toString();
    private String productId = UUID.randomUUID().toString();
    private String shipmentId = UUID.randomUUID().toString();
    private String customerId = UUID.randomUUID().toString();
    private String anonymousId = UUID.randomUUID().toString();
    private String orderId = UUID.randomUUID().toString();

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        clientShippingCalc = ShippingcalcGrpc.newBlockingStub(grpc.createChannel(GrpcContentHosts.PAAS_CONTENT_OPERATIONS_SHIPPINGCALC));
        firstLocalStrategyId = addStrategy(false, 1, "courier");
        secondLocalStrategyId = addStrategy(false, 0, "courier");
        firstGlobalStrategyId = addStrategy(true, 1, "b2b");
        secondGlobalStrategyId = addStrategy(true, 0, "b2b");
        addBinding(firstLocalStrategyId, storeId, "sbermarket");
        addBinding(secondLocalStrategyId, storeId, "sbermarket");
    }

    @CaseIDs(value = {@CaseId(355), @CaseId(354), @CaseId(356), @CaseId(289), @CaseId(321), @CaseId(322), @CaseId(328), @CaseId(330)})
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по локальной стратегии",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceForLocalStrategy() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, productId, 99900, 0, 1000, shipmentId, false,
                1000, 1, 99900, storeId, "NEW", 1, 0,
                55.55, 55.55, customerId, anonymousId, 1, 1655822708, 55.57, 55.57,
                orderId, false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondLocalStrategyId, 19900, 100000, 3, 4, 0);
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
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 3,
                "sbermarket", "Web", "2.3.4");

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondGlobalStrategyId, 19900, 100000, 3, 4, 0);
    }

    @CaseIDs(value = {@CaseId(248), @CaseId(325), @CaseId(329)})
    @Issue("HG-620")
    @Story("Get Delivery Price")
    @Test(description = "Получение цены по более приоритетному правилу в стратегии",
            groups = "dispatch-shippingcalc-smoke",
            enabled = false)
    public void getDeliveryPriceHighestPriorityRule() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, productId, 300100, 0, 1000, shipmentId, false,
                1000, 1, 300100, storeId, "NEW", 1, 0,
                55.55, 55.55, customerId, anonymousId, 0, 1655822708, 55.57, 55.57,
                orderId, false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondLocalStrategyId, 0, 50000, 1, 0, 1);
    }

    @CaseId(237)
    @Story("Get Delivery Price")
    @Test(description = "Получение ошибки при расчете цены для магазина, для которого не смогли найти стратегию",
            groups = "dispatch-shippingcalc-smoke",
            expectedExceptions = StatusRuntimeException.class,
            expectedExceptionsMessageRegExp = "INTERNAL: cannot GetDeliveryPrice: don't calculated price for some shipment")
    public void getDeliveryPriceForStoreWithNoBinds() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, UUID.randomUUID().toString(), 99900, 0, 1000, UUID.randomUUID().toString(), false,
                1000, 1, 99900, UUID.randomUUID().toString(), "NEW", 1, 0,
                55.55, 55.55, UUID.randomUUID().toString(), UUID.randomUUID().toString(), 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        clientShippingCalc.getDeliveryPrice(request);
    }

    @CaseIDs(value = {@CaseId(240), @CaseId(274), @CaseId(1)})
    @Story("Get Delivery Price")
    @Test(description = "Создание оффера, фиксирующего стоимость доставки",
            groups = "dispatch-shippingcalc-smoke",
            dependsOnMethods = "getDeliveryPriceForLocalStrategy")
    public void getDeliveryPriceOffer() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest offerRequest = getDeliveryPriceRequest(
                1, productId, 99900, 0, 1000, shipmentId, false,
                1000, 1, 99900, storeId, "NEW", 1, 0,
                55.55, 55.55, customerId, anonymousId, 1, 1655822708, 55.57, 55.57,
                orderId, false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");
        ShippingcalcOuterClass.GetDeliveryPriceRequest newRequest = getDeliveryPriceRequest(
                1, productId, 99900, 0, 1000, shipmentId, false,
                1000, 1, 99900, storeId, "NEW", 1, 0,
                55.55, 55.55, customerId, anonymousId, 1, 1655822708, 55.57, 55.57,
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        var offerResponse = clientShippingCalc.getDeliveryPrice(offerRequest);
        var newResponse = clientShippingCalc.getDeliveryPrice(newRequest);

        Allure.step("Проверяем расчитанную цену доставки по офферу", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertTrue(offerResponse.getIsOrderPossible(), "Заказ не возможен");
            softAssert.assertTrue(offerResponse.getShipments(0).getWeHadOffer(), "Получили цену не из оффера");
            softAssert.assertEquals(offerResponse.getTotalShippingPrice(), 19900, "Не ожидаемая цена доставки");
            softAssert.assertEquals((Integer) offerResponse.getShipments(0).getStrategyId(), secondLocalStrategyId, "Посчитали по неверной стратегии");
            softAssert.assertEquals(offerResponse.getShipments(0).getMinimalCartPrice(), 100000, "Отдали неверную цену мин. корзины");
            softAssert.assertEquals(offerResponse.getShipments(0).getLadderCount(), 3, "В лесенке не ожидаемое кол-во ступеней");
            softAssert.assertEquals(offerResponse.getShipments(0).getHintsCount(), 4, "Не ожидаемое кол-во подсказок");
            softAssert.assertEquals(offerResponse.getShipments(0).getPriceExplanation().getPassedConditionsCount(), 0, "Не ожидаемое кол-во прошедших условий");
            softAssert.assertAll();

        });

        Allure.step("Проверяем новую расчитанную цену", () -> {
            checkDeliveryPrice(newResponse, secondLocalStrategyId, 19900, 100000, 3, 4, 0);
        });
    }

    @CaseId(320)
    @Story("Get Delivery Price")
    @Test(description = "Расчет цены с наценкой слота surge_delivery_window_addition",
            groups = "dispatch-shippingcalc-smoke")
    public void getDeliveryPriceWithSurgeDeliveryWindowAddition() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, productId, 99900, 0, 1000, shipmentId, false,
                1000, 1, 99900, storeId, "NEW", 1, 10000,
                55.55, 55.55, customerId, anonymousId, 1, 1655822708, 55.57, 55.57,
                orderId, false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondLocalStrategyId, 29900, 100000, 3, 4, 0);
    }

    @CaseIDs(value = {@CaseId(357), @CaseId(326), @CaseId(332)})
    @Issue("HG-620")
    @Story("Get Delivery Price")
    @Test(description = "Проверка расчета по самому дорогому правилу стратегии, когда не прошли ни по какому условию",
            groups = "dispatch-shippingcalc-regress",
            enabled = false)
    public void getDeliveryPriceMostExpensiveRule() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, productId, 300099, 0, 1000, shipmentId, false,
                1000, 1, 300099, storeId, "NEW", 1, 0,
                55.55, 55.55, customerId, anonymousId, 1, 1655822708, 55.57, 55.57,
                orderId, false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondLocalStrategyId, 19900, 100000, 1, 4, 0);
    }

    @CaseIDs(value = {@CaseId(360), @CaseId(331)})
    @Story("Get Delivery Price")
    @Test(description = "Проверка добавления ценовых компонентов из параметров скрипта",
            groups = "dispatch-shippingcalc-regress")
    public void getDeliveryPriceOverweight() {
        ShippingcalcOuterClass.GetDeliveryPriceRequest request = getDeliveryPriceRequest(
                1, productId, 100000, 0, 40000, shipmentId, false,
                40000, 1, 100000, storeId, "NEW", 1, 0,
                55.55, 55.55, customerId, anonymousId, 1, 1655822708, 55.57, 55.57,
                orderId, false, false, "Картой онлайн", true, 2,
                "sbermarket", "Web", "2.3.4");

        var response = clientShippingCalc.getDeliveryPrice(request);
        checkDeliveryPrice(response, secondLocalStrategyId, 14900, 100000, 3, 4, 0);
        Allure.step("Проверяем наценку за перевес", () -> {
            assertEquals(response.getShipments(0).getPriceExplanation().getPriceComponents(3).getPrice(), 5000, "Не ожидаемая наценка за перевес");
        });
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
                "", false, false, "Картой онлайн", true, 3,
                "sbermarket", "Web", "2.3.4");

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
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 3,
                "sbermarket", "Web", "2.3.4");

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
                .setDeliveryTypeValue(2)
                .setTenantId("sbermarket")
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
                UUID.randomUUID().toString(), false, false, "Картой онлайн", true, 3,
                "sbermarket", "Web", "2.3.4");

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
                .setDeliveryTypeValue(2)
                .setTenantId("sbermarket")
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
                "sbermarket", "Web", "2.3.4");

        clientShippingCalc.getDeliveryPrice(request);
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        deleteCreatedStrategy(firstLocalStrategyId);
        deleteCreatedStrategy(secondLocalStrategyId);
        deleteCreatedStrategy(firstGlobalStrategyId);
        deleteCreatedStrategy(secondGlobalStrategyId);
    }
}
