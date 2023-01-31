package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.model.v3.PaymentToolV3;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.request.v3.CheckoutV3Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.api.response.v2.MergeStatusV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.jdbc.dao.stf.StoreConfigsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;

import java.util.Collections;
import java.util.List;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;
import static ru.instamart.kraken.config.EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID;

@Epic("ApiV2")
@Feature("Заказы (shipments)")
public class MergedShipmentsV2Test extends RestBase {

    private List<ProductV2> products;
    private OrderV2 order;
    private UserData userData;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        userData = SessionFactory.getSession(SessionType.API_V2).getUserData();
        products = apiV2.getProducts(DEFAULT_METRO_MOSCOW_SID);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
    }

    @TmsLink("1029")
    @Story("Мердж подзаказа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Добавление в подзаказ другого подзаказа")
    public void mergeShipments() {
        final Response response = LineItemsV2Request.POST(products.get(4).getId(), 1, apiV2.createOrder().getNumber());
        checkStatusCode200(response);
        OrderV2 newOrder = apiV2.createOrder();
        final Response responseForMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode200(responseForMerge);
        checkResponseJsonSchema(responseForMerge, OrderV2Response.class);
    }

    @TmsLink("1030")
    @Story("Мердж подзаказа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Добавление в подзаказ подзаказа из другого магазина",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithShipmentFromAnotherStore() {
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        OrderV2 newOrder = apiV2.createOrder();
        final Response response = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains("\"store_id\":\"Дозаказать можно только из того же магазина\""));
    }

    @TmsLink("1031")
    @Story("Мердж подзаказа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Добавление в подзаказ подзаказа с алкоголем",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithAlcoholShipment() {
        String orderNumber = apiV2.createOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(orderNumber, ShippingMethodV2.PICKUP.getMethod());
        final Response response = LineItemsV2Request.POST(SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(DEFAULT_METRO_MOSCOW_SID), 1, orderNumber);
        checkStatusCode200(response);
        OrderV2 newOrder = OrdersV2Request.GET(orderNumber).as(OrderV2Response.class).getOrder();
        final Response responseWithMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(responseWithMerge);
        Assert.assertTrue(responseWithMerge.asString().contains("\"shipping_category_id\":\"В заказе алкоголь. Его нельзя добавить к заказу с доставкой\""), "Пришел неверный текст ошибки");
    }

    @TmsLink("1474")
    @Story("Статус мержа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Получение информации о статусе мержа для существующего заказа",
            dependsOnMethods = "mergeShipments")
    public void getMergeStatus() {
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        final Response response = OrdersV2Request.MergeStatus.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MergeStatusV2Response.class);
    }

    @TmsLink("1475")
    @Story("Статус мержа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Получение информации о статусе мержа для несуществующего заказа")
    public void getMergeStatusOfNonexistentOrder() {
        final Response response = OrdersV2Request.MergeStatus.GET("failedOrder");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @TmsLink("2046")
    @Story("Мердж подзаказа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Добавление в подзаказ подзаказа с аптечной продукцией",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithPharmaShipment() {
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        String orderNumber = apiV2.createOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(orderNumber, ShippingMethodV2.PICKUP.getMethod());
        final Response response = LineItemsV2Request.POST(SpreeProductsDao.INSTANCE.getOfferIdForPharma(DEFAULT_METRO_MOSCOW_SID), 1, orderNumber);
        checkStatusCode200(response);
        OrderV2 newOrder = OrdersV2Request.GET(orderNumber).as(OrderV2Response.class).getOrder();
        final Response responseWithMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(responseWithMerge);
        Assert.assertTrue(responseWithMerge.asString().contains("\"shipping_category_id\":\"В заказе лекарства. Их нельзя добавить к заказу с доставкой\""));
    }

    @TmsLink("2151")
    @Story("Мердж подзаказа")
    @Test(description = "Попытка добавления продукта в заказ с алкогольной продукцией",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void mergeLineItemToOrderWithAlcohol() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(DEFAULT_METRO_MOSCOW_SID);

        //Костыль, подсмотрел в preconditions CheckoutPickupV3Test, без него 500 на CheckoutV3Request.Completion.POST
        apiV1.changeAddress(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.BY_COURIER.getMethod());

        apiV1.fillCart(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.PICKUP.getMethod(), offerId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        MultiretailerOrderV1Response multiRetailerOrder = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(multiRetailerOrder.getNumber(), Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
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

        SessionFactory.createSessionToken(SessionType.API_V2, user);
        var products = apiV2.getProducts(DEFAULT_METRO_MOSCOW_SID);

        final Response response = ShipmentsV2Request.LineItems.POST(multiRetailerOrder.getShipments().get(0).getNumber(), products.get(3).getId(), 1);
        checkStatusCode422(response);
        checkErrorField(response, "not_available_for_alcohol", "В заказе есть алкоголь. Можно оставить товар в корзине - если захотите добавить к следующему заказу");
    }

    @TmsLink("3062")
    @Story("Мердж подзаказа")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2"},
            description = "Попытка добавление товара в заказ | редактирование заказа запрещено")
    public void mergeShipmentsIfEditingNotAllowed() {
        StoreConfigsDao.INSTANCE.updateEditingSettings(EnvironmentProperties.DEFAULT_SID, 1, 1);
        OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        final Response response = LineItemsV2Request.POST(apiV2.getProducts(EnvironmentProperties.DEFAULT_SID).get(4).getId(), 1, apiV2.createOrder().getNumber());
        checkStatusCode200(response);
        OrderV2 newOrder = apiV2.createOrder();
        final Response responseForMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        StoreConfigsDao.INSTANCE.updateEditingSettings(EnvironmentProperties.DEFAULT_SID, 1, 0);
        checkStatusCode422(responseForMerge);
        checkErrorField(responseForMerge, "not_available_in_the_store", "В магазине нет такой возможности. Можно оставить товар в корзине — если захотите добавить к следующему заказу");
    }

    @TmsLink("2152")
    @Story("Мердж подзаказа")
    @Test(description = "Попытка добавления корзины в заказ с алкогольной продукцией",
            groups = {API_INSTAMART_REGRESS, "api-v3"})
    public void mergeOrderToOrderWithAlcohol() {
        UserData user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        long offerId = SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(DEFAULT_METRO_MOSCOW_SID);

        //Костыль, подсмотрел в preconditions CheckoutPickupV3Test, без него 500 на CheckoutV3Request.Completion.POST
        apiV1.changeAddress(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.BY_COURIER.getMethod());

        apiV1.fillCart(apiV2.getAddressBySidMy(DEFAULT_METRO_MOSCOW_SID), ShippingMethodV2.PICKUP.getMethod(), offerId);
        apiV1.addReplacementPolicy();
        apiV1.addDeliveryWindow();
        MultiretailerOrderV1Response multiRetailerOrder = apiV1.getMultiRetailerOrder();

        final Response initializeResponse = CheckoutV3Request.Initialization.POST(multiRetailerOrder.getNumber(), Collections.singletonList(multiRetailerOrder.getShipments().get(0).getNumber()));
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

        SessionFactory.createSessionToken(SessionType.API_V2, user);

        final Response response = LineItemsV2Request.POST(apiV2.getProducts(DEFAULT_METRO_MOSCOW_SID).get(0).getId(), 1, apiV2.createOrder().getNumber());
        checkStatusCode200(response);
        OrderV2 newOrder = apiV2.createOrder();

        final Response responseForMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), multiRetailerOrder.getShipments().get(0).getNumber());
        checkStatusCode422(responseForMerge);
        checkErrorField(responseForMerge, "not_available_for_alcohol", "В заказе есть алкоголь. Можно оставить товар в корзине - если захотите добавить к следующему заказу");
    }
}
