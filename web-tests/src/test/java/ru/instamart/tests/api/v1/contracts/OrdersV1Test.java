package ru.instamart.tests.api.v1.contracts;

import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.objects.v2.Order;
import ru.instamart.api.requests.ApiV1Requests;
import ru.instamart.api.responses.v1.LineItemsResponse;
import ru.instamart.api.responses.v1.ShipmentResponse;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.ui.common.pagesdata.EnvironmentData;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class OrdersV1Test extends RestBase {

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

        Response response = ApiV1Requests.UserSessions.POST(UserManager.getDefaultAdmin());
        checkStatusCode200(response);
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
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Orders.json"));
    }

    @CaseId(115)
    @Test(  description = "Контрактный тест инфы о заказе",
            groups = "api-instamart-regress")
    public void getOrder() {
        Response response = ApiV1Requests.Orders.GET(orderNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Order.json"));
    }

    @CaseId(116)
    @Test(  description = "Контрактный тест инфы о шипменте",
            groups = "api-instamart-regress")
    public void getShipment() {
        Response response = ApiV1Requests.Shipments.GET(shipmentNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Shipment.json"));

        shipmentUuid = response.as(ShipmentResponse.class).getShipment().getUuid();
    }

    @CaseId(117)
    @Test(  description = "Контрактный тест списка офферов в шипменте",
            groups = "api-instamart-regress")
    public void getShipmentOffers() {
        Response response = ApiV1Requests.Shipments.Offers.GET(shipmentNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/ShipmentOffers.json"));
    }

    @CaseId(118)
    @Test(  description = "Контрактный тест списка лайн айтемов в шимпенте",
            groups = "api-instamart-regress")
    public void getLineItems() {
        Response response = ApiV1Requests.LineItems.GET(shipmentNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/LineItems.json"));

        productSku = response.as(LineItemsResponse.class)
                .getLineItems()
                .get(0)
                .getOffer()
                .getProductSku();
    }

    @CaseId(119)
    @Test(  description = "Контрактный тест списка предзамен для товара из шипмента",
            groups = "api-instamart-regress",
            dependsOnMethods = "getLineItems")
    public void getShipmentProductsPrereplacements() {
        Response response = ApiV1Requests.Shipments.Products.Prereplacements.GET(
                shipmentNumber,
                Long.parseLong(productSku));
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Prereplacements.json"));
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
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/MarketingSampleItems.json"));
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
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/PaymentTools.json"));
    }
}
