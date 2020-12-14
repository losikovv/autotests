package ru.instamart.tests.api.v1.contracts;

import instamart.api.common.RestBase;
import instamart.api.requests.ApiV1Requests;
import instamart.api.responses.v1.LineItemsResponse;
import instamart.api.responses.v1.ShipmentResponse;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Orders extends RestBase {

    private String shipmentUuid;
    private String orderNumber;
    private String shipmentNumber;
    private String productSku;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        UserData user = Users.apiUser();
        apiV2.registration(user);
        instamart.api.objects.v2.Order order = apiV2.order(user, EnvironmentData.INSTANCE.getDefaultSid());
        orderNumber = order.getNumber();
        shipmentNumber = order.getShipments().get(0).getNumber();

        ApiV1Requests.UserSessions.POST(Users.superadmin());
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        apiV2.cancelCurrentOrder();
    }

    @Test(  description = "Контрактный тест списка заказов",
            groups = "api-v2-regress")
    public void getOrders() {
        Response response = ApiV1Requests.Orders.GET();
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Orders.json"));
    }

    @Test(  description = "Контрактный тест инфы о заказе",
            groups = "api-v2-regress")
    public void getOrder() {
        Response response = ApiV1Requests.Orders.GET(orderNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Order.json"));
    }

    @Test(  description = "Контрактный тест инфы о шипменте",
            groups = "api-v2-regress")
    public void getShipment() {
        Response response = ApiV1Requests.Shipments.GET(shipmentNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Shipment.json"));

        shipmentUuid = response.as(ShipmentResponse.class).getShipment().getUuid();
    }

    @Test(  description = "Контрактный тест списка офферов в шипменте",
            groups = "api-v2-regress")
    public void getShipmentOffers() {
        Response response = ApiV1Requests.Shipments.Offers.GET(shipmentNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/ShipmentOffers.json"));
    }

    @Test(  description = "Контрактный тест списка лайн айтемов в шимпенте",
            groups = "api-v2-regress")
    public void getLineItems() {
        Response response = ApiV1Requests.LineItems.GET(shipmentNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/LineItems.json"));

        productSku = response.as(LineItemsResponse.class)
                .getLine_items()
                .get(0)
                .getOffer()
                .getProduct_sku();
    }

    @Test(  description = "Контрактный тест списка предзамен для товара из шипмента",
            groups = "api-v2-regress",
            dependsOnMethods = "getLineItems")
    public void getShipmentProductsPrereplacements() {
        Response response = ApiV1Requests.Shipments.Products.Prereplacements.GET(
                shipmentNumber,
                Long.parseLong(productSku));
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Prereplacements.json"));
    }

    @Test(  description = "Контрактный тест списка сэмплов в шипменте",
            groups = "api-v2-regress",
            dependsOnMethods = "getShipment")
    public void getShopperMarketingSampleItems() {
        Response response = ApiV1Requests.Shoppers.MarketingSampleItems.GET(shipmentUuid);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/MarketingSampleItems.json"));
    }

    @Test(  description = "Контрактный тест списка способов оплаты в заказе",
            groups = "api-v2-regress")
    public void getShopperOrderAvailablePaymentTools() {
        Response response = ApiV1Requests.Shoppers.OrderAvailablePaymentTools.GET(orderNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/PaymentTools.json"));
    }
}
