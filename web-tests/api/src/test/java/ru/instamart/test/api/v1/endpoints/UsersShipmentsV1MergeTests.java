package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.model.v1.UserShipmentV1;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.request.v1.UsersV1Request;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v1.UserShipmentV1Response;
import ru.instamart.jdbc.dao.stf.DeliveryWindowsDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.util.TimeUtil;

import java.util.Collections;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_SID;

@Epic("ApiV1")
@Feature("Заказы")
public class UsersShipmentsV1MergeTests extends RestBase {
    private final int testSid = DEFAULT_SID;
    private UserData user;
    private OrderV2 orderV2;

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        orderV2 = apiV2.createOrder();
    }

    @AfterClass(alwaysRun = true)
    public void restoreSettings() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 1, 0);
    }

    @Story("Дозаказ")
    @TmsLink("2229")
    @Test(description = "Получение активного заказа | проверка возможности редактирования заказа",
            groups = {API_INSTAMART_REGRESS})
    public void testMergePossibilityPositive() {
        apiV2.setDefaultAttributesAndCompleteOrderOnTomorrow();
        final Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertTrue(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : false'");
        Assert.assertNull(shipmentFromResponse.getMerge().getImpossibilityReason(), "В ответе не пустой блок 'impossibility_reason'");
    }

    @Story("Дозаказ")
    @TmsLink("2232")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе Собирается",
            groups = {API_INSTAMART_REGRESS})
    public void testMergeImpossibilityIncorrectStateCollecting() {
        apiV2.setDefaultAttributesAndCompleteOrderOnTomorrow();
        SpreeOrdersDao.INSTANCE.updateShipmentState(orderV2.getNumber(), StateV2.COLLECTING.getValue());
        Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "invalid_shipment_state_collecting");
    }

    @Story("Дозаказ")
    @TmsLink("2232")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе Собран",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityIncorrectStateReadyToShip() {
        apiV2.setDefaultAttributesAndCompleteOrderOnTomorrow();
        SpreeOrdersDao.INSTANCE.updateShipmentState(orderV2.getNumber(), StateV2.READY_TO_SHIP.getValue());
        response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "invalid_shipment_state_ready_to_ship");

    }

    @Story("Дозаказ")
    @TmsLink("2232")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе В пути",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityIncorrectStateShipping() {
        apiV2.setDefaultAttributesAndCompleteOrderOnTomorrow();
        SpreeOrdersDao.INSTANCE.updateShipmentState(orderV2.getNumber(), StateV2.SHIPPING.getValue());
        response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "invalid_shipment_state_shipping");
    }

    @Story("Дозаказ")
    @TmsLink("2233")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе Доставлен",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityIncorrectStateShipped() {
        apiV2.setDefaultAttributesAndCompleteOrderOnTomorrow();
        SpreeOrdersDao.INSTANCE.updateShipmentStateToShip(orderV2.getNumber(), TimeUtil.getDbDate());
        Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertNull(shipmentFromResponse.getMerge(), "В ответе присутствует непустой блок 'merge'");
    }

    @Story("Дозаказ")
    @TmsLink("2233")
    @Test(description = "Проверка возможности редактирования заказа | заказ в статусе Отменен",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityIncorrectStateCancelled() {
        apiV2.setDefaultAttributesAndCompleteOrderOnTomorrow();
        SpreeOrdersDao.INSTANCE.updateShipmentState(orderV2.getNumber(), StateV2.CANCELED.getValue());
        response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertNull(shipmentFromResponse.getMerge(), "В ответе присутствует непустой блок 'merge'");
    }

    @Story("Дозаказ")
    @TmsLink("2235")
    @Test(description = "Проверка возможности редактирования заказа | время редактирования заказа завершено",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityTimeIsOver() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 48, 0);
        apiV2.setDefaultAttributesAndCompleteOrder();
        Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 1, 0);
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "order_not_editable");
    }

    @Story("Дозаказ")
    @TmsLink("2230")
    @Test(description = "Проверка возможности редактирования заказа | заказ с перевесом",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityMaxWeight() {
        apiV2.setDefaultAttributesAndCompleteOrder();
        var deliveryWindowId = orderV2.getShipments().get(0).getNextDeliveries().get(0).getId();
        DeliveryWindowsDao.INSTANCE.updateWeightLimits(deliveryWindowId, 0, 1, 0);
        Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        DeliveryWindowsDao.INSTANCE.updateWeightLimits(deliveryWindowId, 3000, 20000, 99990000);
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "too_heavy");
    }

    @Story("Дозаказ")
    @TmsLink("2231")
    @Test(description = "Проверка возможности редактирования заказа | максимальное кол-во товаров в заказе",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityMaxCount() {
        apiV2.setDefaultAttributesAndCompleteOrder();
        var deliveryWindowId = orderV2.getShipments().get(0).getNextDeliveries().get(0).getId();
        DeliveryWindowsDao.INSTANCE.updateItemsCountLimits(deliveryWindowId, 999, 0, 0);
        Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        DeliveryWindowsDao.INSTANCE.updateItemsCountLimits(deliveryWindowId, 999, 20, 9999);
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "items_count_balance_reached");
    }

    @Story("Дозаказ")
    @TmsLink("2237")
    @Test(description = "Проверка возможности редактирования заказа | дозаказ в магазине отключен",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibility() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 0, 1);
        apiV2.setDefaultAttributesAndCompleteOrder();
        Response response = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        StoreConfigsDao.INSTANCE.updateEditingSettings(testSid, 1, 0);
        checkStatusCode200(response);
        UserShipmentV1 shipmentFromResponse = response.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "not_available_in_the_store");
    }

    @Story("Дозаказ")
    @TmsLink("2234")
    @Test(description = "Проверка возможности редактирования | заказ из аптеки",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityPharmacy() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForPharma(DEFAULT_METRO_MOSCOW_SID);

        //Костыль, подсмотрел в preconditions CheckoutPickupV3Test, без него 500 на CheckoutV3Request.Completion.POST
        apiV1.changeAddress(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.BY_COURIER.getMethod());

        apiV1.fillCart(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.PICKUP.getMethod(), offerId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        MultiretailerOrderV1Response multiRetailerOrder = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(
                multiRetailerOrder.getNumber(),
                Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        PaymentToolV3 paymentTool = apiV3.getPaymentTools(multiRetailerOrder.getNumber()).get(0);
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumber(multiRetailerOrder.getShipments().get(0).getNumber())
                .build();
        final Response paymentToolResponse = CheckoutV3Request.PUT(orderRequest, multiRetailerOrder.getNumber());
        checkStatusCode200(paymentToolResponse);

        final Response completionResponse = CheckoutV3Request.Completion.POST(multiRetailerOrder.getNumber(), Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode200(completionResponse);

        Response getResponse = UsersV1Request.GET(user.getId(), multiRetailerOrder.getShipments().get(0).getNumber());
        checkStatusCode200(getResponse);
        UserShipmentV1 shipmentFromResponse = getResponse.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "not_available_for_prescription_drugs");
    }

    @Story("Дозаказ")
    @TmsLink("2236")
    @Test(description = "Проверка возможности редактирования | заказ с алкоголем",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void testMergeImpossibilityAlcohol() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(DEFAULT_METRO_MOSCOW_SID);

        //Костыль, подсмотрел в preconditions CheckoutPickupV3Test, без него 500 на CheckoutV3Request.Completion.POST
        apiV1.changeAddress(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.BY_COURIER.getMethod());

        apiV1.fillCart(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.PICKUP.getMethod(), offerId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        MultiretailerOrderV1Response multiRetailerOrder = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(
                multiRetailerOrder.getNumber(),
                Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode(initializeResponse, 204);

        PaymentToolV3 paymentTool = apiV3.getPaymentTools(multiRetailerOrder.getNumber()).get(0);
        CheckoutV3Request.OrderRequest orderRequest = CheckoutV3Request.OrderRequest.builder()
                .order(CheckoutV3Request.Order.builder()
                        .paymentAttributes(CheckoutV3Request.PaymentAttributes.builder()
                                .paymentToolId(paymentTool.getId())
                                .build())
                        .build())
                .shipmentNumber(multiRetailerOrder.getShipments().get(0).getNumber())
                .build();
        final Response paymentToolResponse = CheckoutV3Request.PUT(orderRequest, multiRetailerOrder.getNumber());
        checkStatusCode200(paymentToolResponse);

        final Response completionResponse = CheckoutV3Request.Completion.POST(multiRetailerOrder.getNumber(), Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
        checkStatusCode200(completionResponse);

        Response getResponse = UsersV1Request.GET(user.getId(), multiRetailerOrder.getShipments().get(0).getNumber());
        checkStatusCode200(getResponse);
        UserShipmentV1 shipmentFromResponse = getResponse.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "not_available_for_alcohol");
    }

    @Story("Дозаказ")
    @TmsLink("2238")
    @Test(enabled = false, description = "Проверка возможности редактирования заказа | заказ в рассрочку",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    //Не удалось настроить оплату частями на кракене см. https://wiki.sbmt.io/pages/viewpage.action?pageId=3032882935
    public void testMergeImpossibilityDolyame() {
        orderV2 = apiV2.orderWithAmount(testSid, "Частями", 4000);

        Response getResponse = UsersV1Request.GET(user.getId(), orderV2.getShipments().get(0).getNumber());
        checkStatusCode200(getResponse);
        UserShipmentV1 shipmentFromResponse = getResponse.as(UserShipmentV1Response.class).getShipment();
        Assert.assertFalse(shipmentFromResponse.getMerge().isPossible(), "В блоке 'merge' передано '\"possible\" : true'");
        compareTwoObjects(shipmentFromResponse.getMerge().getImpossibilityReason().getCode(), "not_available_for_tinkoff_dolyame");
    }
}
