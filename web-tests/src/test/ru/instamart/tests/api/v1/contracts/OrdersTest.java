package ru.instamart.tests.api.v1.contracts;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.v2.Order;
import instamart.api.requests.ApiV1Requests;
import instamart.api.responses.v1.LineItemsResponse;
import instamart.api.responses.v1.ShipmentResponse;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrdersTest extends RestBase {

    private String shipmentUuid;
    private String orderNumber;
    private String shipmentNumber;
    private String productSku;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
        final Order order = apiV2.order(SessionFactory.getSession(SessionType.APIV2).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        orderNumber = order.getNumber();
        shipmentNumber = order.getShipments().get(0).getNumber();

        ApiV1Requests.UserSessions.POST(UserManager.getDefaultAdmin());
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        apiV2.cancelCurrentOrder();
    }

    @CaseId(114)
    @Test(  description = "Контрактный тест списка заказов",
            groups = "api-instamart-regress")
    public void getOrders() {
        Response response = ApiV1Requests.Orders.GET();
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Orders.json"));
    }

    @CaseId(115)
    @Test(  description = "Контрактный тест инфы о заказе",
            groups = "api-instamart-regress")
    public void getOrder() {
        Response response = ApiV1Requests.Orders.GET(orderNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Order.json"));
    }

    @CaseId(116)
    @Test(  description = "Контрактный тест инфы о шипменте",
            groups = "api-instamart-regress")
    public void getShipment() {
        Response response = ApiV1Requests.Shipments.GET(shipmentNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Shipment.json"));

        shipmentUuid = response.as(ShipmentResponse.class).getShipment().getUuid();
    }

    @CaseId(117)
    @Test(  description = "Контрактный тест списка офферов в шипменте",
            groups = "api-instamart-regress")
    public void getShipmentOffers() {
        Response response = ApiV1Requests.Shipments.Offers.GET(shipmentNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/ShipmentOffers.json"));
    }

    @CaseId(118)
    @Test(  description = "Контрактный тест списка лайн айтемов в шимпенте",
            groups = "api-instamart-regress")
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

    @CaseId(119)
    @Test(  description = "Контрактный тест списка предзамен для товара из шипмента",
            groups = "api-instamart-regress",
            dependsOnMethods = "getLineItems")
    public void getShipmentProductsPrereplacements() {
        Response response = ApiV1Requests.Shipments.Products.Prereplacements.GET(
                shipmentNumber,
                Long.parseLong(productSku));
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Prereplacements.json"));
    }

    @CaseId(120)
    @Test(  description = "Контрактный тест списка сэмплов в шипменте",
            groups = "api-instamart-regress",
            dependsOnMethods = "getShipment")
    public void getShopperMarketingSampleItems() {
        //todo убрать скип после выдачи прав SD-13260
        if (EnvironmentData.INSTANCE.getServer().equalsIgnoreCase("production")) {
            throw new SkipException("Скипаем тесты на проде");
        }
        Response response = ApiV1Requests.Shoppers.MarketingSampleItems.GET(shipmentUuid);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/MarketingSampleItems.json"));
    }

    @CaseId(121)
    @Test(  description = "Контрактный тест списка способов оплаты в заказе",
            groups = "api-instamart-regress")
    public void getShopperOrderAvailablePaymentTools() {
        //todo убрать скип после выдачи прав SD-13260
        if (EnvironmentData.INSTANCE.getServer().equalsIgnoreCase("production")) {
            throw new SkipException("Скипаем тесты на проде");
        }
        Response response = ApiV1Requests.Shoppers.OrderAvailablePaymentTools.GET(orderNumber);
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/PaymentTools.json"));
    }
}
