package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.ShippingMethodsV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.LineItemsV2Request;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.api.response.v2.OrderV2Response;
import ru.instamart.jdbc.dao.SpreeOrdersDao;
import ru.instamart.jdbc.dao.SpreeProductsDao;
import ru.instamart.kraken.config.EnvironmentProperties;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode422;

public class MergedShipmentsV2Test extends RestBase {

    private List<ProductV2> products;
    private OrderV2 order;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        products = apiV2.getProductsFromEachDepartmentInStore(1);
        order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), 1);
    }


    @CaseId(1029)
    @Story("Повтор подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление в подзаказ другого подзаказа")
    public void mergeShipments() {
        final Response response = LineItemsV2Request.POST(products.get(4).getId(), 1, apiV2.getOpenOrder().getNumber());
        checkStatusCode200(response);
        OrderV2 newOrder = apiV2.getOpenOrder();
        final Response responseForMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode200(responseForMerge);
        checkResponseJsonSchema(responseForMerge, OrderV2Response.class);
    }

    @CaseId(1030)
    @Story("Повтор подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление в подзаказ подзаказа из другого магазина",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithShipmentFromAnotherStore() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        OrderV2 newOrder = apiV2.getOpenOrder();
        final Response response = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains("\"store_id\":\"Дозаказать можно только из того же магазина\""));
    }

    @CaseId(1031)
    @Story("Повтор подзаказа")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавление в подзаказ подзаказа с алкоголем",
            dependsOnMethods = "mergeShipments")
    public void mergeShipmentWithAlcoholShipment() {
        String orderNumber = apiV2.getOpenOrder().getNumber();
        SpreeOrdersDao.INSTANCE.updateShippingKind(orderNumber, ShippingMethodsV2.PICKUP.getMethod());
        final Response response = LineItemsV2Request.POST(SpreeProductsDao.INSTANCE.getOfferIdForAlcohol(1), 1, orderNumber);
        checkStatusCode200(response);
        OrderV2 newOrder = OrdersV2Request.GET(orderNumber).as(OrderV2Response.class).getOrder();
        final Response responseWithMerge = ShipmentsV2Request.Merge.POST(newOrder.getShipments().get(0).getNumber(), order.getShipments().get(0).getNumber());
        checkStatusCode422(responseWithMerge);
        Assert.assertTrue(responseWithMerge.asString().contains("\"shipping_category_id\":\"Категории доставки не совпадают или в дозаказе участвует алкоголь\""));
    }
}
