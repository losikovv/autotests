package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.LineItemV1;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.MultiretailerOrderV1Request;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.response.v1.MergeStatusV1Response;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.jdbc.dao.stf.DeliveryWindowsDao;
import ru.instamart.jdbc.dao.stf.OffersDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_SID;

@Epic("ApiV1")
@Feature("Заказы")
public class UsersShipmentsV1MergeStatusTests extends RestBase {
    private final int testSid = DEFAULT_SID;
    private UserData user;
    private LineItemV1 lineItem;
    private OrderV2 orderV2;
    private MultiretailerOrderV1Response orderV1;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, testSid);
        orderV2 = apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();
    }

    @TmsLink("1557")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusPositive() {
        apiV1.authByPhone(user);
        lineItem = apiV1.addItemToCart(apiV2.getProducts(testSid).get(0).getId());
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        orderV1 = response.as(MultiretailerOrderV1Response.class);

        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV1.getNumber());
        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);
        compareTwoObjects(mergeStatus.getShipments().get(0).getState(), "ok");
        compareTwoObjects(mergeStatus.getShipments().get(0).getShipmentId(), (long) lineItem.getShipmentId());
        compareTwoObjects(mergeStatus.getAvailableRetailers().get(0).getShipmentIds().get(0), (long) orderV2.getShipments().get(0).getId());
    }

    @TmsLink("1973")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | пустая корзина", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusEmptyCart() {
        apiV1.authByPhone(user);
        lineItem = apiV1.addItemToCart(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getId());
        apiV1.addItemToCart(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getId());
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        orderV1 = response.as(MultiretailerOrderV1Response.class);
        apiV1.removeItemFromCart(orderV1.getShipments().get(0).getLineItems().get(0).getId());

        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV1.getNumber());
        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);
        compareTwoObjects(mergeStatus.getShipments().size(), 0);
        compareTwoObjects(mergeStatus.getAvailableRetailers().size(), 0);
    }

    @TmsLink("1974")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | суммарный вес превышает ограничение доставки", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusOverWeight() {
        apiV1.authByPhone(user);
        lineItem = apiV1.addItemToCart(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getId());
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        orderV1 = response.as(MultiretailerOrderV1Response.class);
        var deliveryWindowId = orderV2.getShipments().get(0).getNextDeliveries().get(0).getId();
        DeliveryWindowsDao.INSTANCE.updateWeightLimits(deliveryWindowId, 0, 0, 0);

        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV1.getNumber());
        DeliveryWindowsDao.INSTANCE.updateWeightLimits(deliveryWindowId, 3000, 20000, 9999000);
        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);
        compareTwoObjects(mergeStatus.getShipments().get(0).getState(), "weight_error");
        compareTwoObjects(mergeStatus.getShipments().get(0).getShipmentId(), (long) lineItem.getShipmentId());
        compareTwoObjects(mergeStatus.getAvailableRetailers().get(0).getShipmentIds().get(0), (long) orderV2.getShipments().get(0).getId());
    }

    @TmsLink("1976")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования заказа | суммарное количество позиций превышает ограничение доставки", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusMaxCount() {
        apiV1.authByPhone(user);
        lineItem = apiV1.addItemToCart(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getId());
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        orderV1 = response.as(MultiretailerOrderV1Response.class);
        var deliveryWindowId = orderV2.getShipments().get(0).getNextDeliveries().get(0).getId();
        DeliveryWindowsDao.INSTANCE.updateItemsCountLimits(deliveryWindowId, 999, 0, 0);

        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV1.getNumber());
        DeliveryWindowsDao.INSTANCE.updateItemsCountLimits(deliveryWindowId, 999, 20, 9999);
        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);
        compareTwoObjects(mergeStatus.getShipments().get(0).getState(), "count_error");
        compareTwoObjects(mergeStatus.getShipments().get(0).getShipmentId(), (long) lineItem.getShipmentId());
        compareTwoObjects(mergeStatus.getAvailableRetailers().get(0).getShipmentIds().get(0), (long) orderV2.getShipments().get(0).getId());
    }

    @TmsLink("1975")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования | заказ имеет статус отличный от статуса “В корзине”", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusIncorrectOrderStatus() {
        apiV2.dropAndFillCart(user, testSid);
        apiV2.createOrder();
        apiV2.setDefaultAttributesAndCompleteOrder();

        apiV1.authByPhone(user);
        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV2.getNumber());

        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);
        compareTwoObjects(mergeStatus.getShipments().get(0).getState(), "wrong_order_status");
        compareTwoObjects(mergeStatus.getShipments().get(0).getShipmentId(), (long) orderV2.getShipments().get(0).getId());
        compareTwoObjects(mergeStatus.getAvailableRetailers().size(), 0);
    }

    @TmsLink("1977")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования | нет товаров в доставке", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusSold() {
        apiV1.authByPhone(user);
        lineItem = apiV1.addItemToCart(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getId());
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        orderV1 = response.as(MultiretailerOrderV1Response.class);

        OffersDao.INSTANCE.updateOfferPublished(lineItem.getOfferId(), 0);
        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV1.getNumber());
        OffersDao.INSTANCE.updateOfferPublished(lineItem.getOfferId(), 1);
        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);

        compareTwoObjects(mergeStatus.getShipments().get(0).getState(), "empty_shipment");
        compareTwoObjects(mergeStatus.getShipments().get(0).getShipmentId(), (long) lineItem.getShipmentId());
        compareTwoObjects(mergeStatus.getAvailableRetailers().get(0).getShipmentIds().get(0), (long) orderV2.getShipments().get(0).getId());
    }

    @TmsLink("1978")
    @Story("Дозаказ")
    @Test(description = "Проверка возможности редактирования | нет доступных для мерджа оформленных заказов", groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getMergeStatusNoActiveOrders() {
        apiV1.authByPhone(UserManager.getQaUser());
        lineItem = apiV1.addItemToCart(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(0).getId());
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        orderV1 = response.as(MultiretailerOrderV1Response.class);

        final Response mergeResponse = OrdersV1Request.MergeStatus.GET(orderV1.getNumber());
        checkStatusCode200(mergeResponse);
        checkResponseJsonSchema(mergeResponse, MergeStatusV1Response.class);
        MergeStatusV1Response mergeStatus = mergeResponse.as(MergeStatusV1Response.class);

        compareTwoObjects(mergeStatus.getShipments().get(0).getState(), "no_active_orders");
        compareTwoObjects(mergeStatus.getShipments().get(0).getShipmentId(), (long) lineItem.getShipmentId());
        compareTwoObjects(mergeStatus.getAvailableRetailers().size(), 0);
    }
}
