package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.MergeStatusV2Response;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.jdbc.dao.stf.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

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
        products = apiV2.getProductsFromEachDepartmentOnMainPage(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID);
    }

    @CaseId(1029)
    @Story("Мердж подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Добавление в подзаказ другого подзаказа")
    public void mergeShipments() {
        final Response response = LineItemsV2Request.POST(products.get(4).getId(), 1, apiV2.createOrder().getNumber());
        checkStatusCode200(response);
        OrderV2 newOrder = apiV2.createOrder();
        final Response responseForMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode200(responseForMerge);
        checkResponseJsonSchema(responseForMerge, OrderV2Response.class);
    }

    @CaseId(1030)
    @Story("Мердж подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
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

    @CaseId(1031)
    @Story("Мердж подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Добавление в подзаказ подзаказа с алкоголем",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithAlcoholShipment() {
        String orderNumber = apiV2.createOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(orderNumber, ShippingMethodV2.PICKUP.getMethod());
        final Response response = LineItemsV2Request.POST(SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID), 1, orderNumber);
        checkStatusCode200(response);
        OrderV2 newOrder = OrdersV2Request.GET(orderNumber).as(OrderV2Response.class).getOrder();
        final Response responseWithMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(responseWithMerge);
        Assert.assertTrue(responseWithMerge.asString().contains("\"shipping_category_id\":\"В заказе алкоголь. Его нельзя добавить к заказу с доставкой\""), "Пришел неверный текст ошибки");
    }

    @CaseId(1474)
    @Story("Статус мержа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Получение информации о статусе мержа для существующего заказа",
            dependsOnMethods = "mergeShipments")
    public void getMergeStatus() {
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        final Response response = OrdersV2Request.MergeStatus.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MergeStatusV2Response.class);
    }

    @CaseId(1475)
    @Story("Статус мержа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Получение информации о статусе мержа для несуществующего заказа")
    public void getMergeStatusOfNonexistentOrder() {
        final Response response = OrdersV2Request.MergeStatus.GET("failedOrder");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(2046)
    @Story("Мердж подзаказа")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Добавление в подзаказ подзаказа с аптечной продукцией",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithPharmaShipment() {
        SessionFactory.createSessionToken(SessionType.API_V2, userData);
        String orderNumber = apiV2.createOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(orderNumber, ShippingMethodV2.PICKUP.getMethod());
        final Response response = LineItemsV2Request.POST(SpreeProductsDao.INSTANCE.getOfferIdForPharma(EnvironmentProperties.DEFAULT_METRO_MOSCOW_SID), 1, orderNumber);
        checkStatusCode200(response);
        OrderV2 newOrder = OrdersV2Request.GET(orderNumber).as(OrderV2Response.class).getOrder();
        final Response responseWithMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(responseWithMerge);
        Assert.assertTrue(responseWithMerge.asString().contains("\"shipping_category_id\":\"В заказе лекарства. Их нельзя добавить к заказу с доставкой\""));
    }
}
