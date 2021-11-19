package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.LineItemsV1Request;
import ru.instamart.api.request.v1.OrdersV1Request;
import ru.instamart.api.request.v1.ShipmentsV1Request;
import ru.instamart.api.request.v1.ShoppersV1Request;
import ru.instamart.api.response.v1.LineItemsV1Response;
import ru.instamart.api.response.v1.ShipmentV1Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.data.user.UserManager;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class OrdersV1Test extends RestBase {
    private String shipmentUuid;
    private String orderNumber;
    private String shipmentNumber;
    private String productSku;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        final OrderV2 order = apiV2.order(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        if (order == null) throw new SkipException("Заказ не удалось оплатить");
        orderNumber = order.getNumber();
        shipmentNumber = order.getShipments().get(0).getNumber();
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        apiV2.cancelCurrentOrder();
    }

    @Story("Заказы")
    @CaseId(114)
    @Test(description = "Контрактный тест списка заказов",
            groups = "api-instamart-regress")
    public void getOrders() {
        Response response = OrdersV1Request.GET();
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Orders.json"));
    }

    @Story("Заказы")
    @CaseId(115)
    @Test(description = "Контрактный тест инфы о заказе",
            groups = "api-instamart-regress")
    public void getOrder() {
        Response response = OrdersV1Request.GET(orderNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Order.json"));
    }

    @Story("Заказы")
    @CaseId(116)
    @Test(description = "Контрактный тест инфы о шипменте",
            groups = "api-instamart-regress")
    public void getShipment() {
        Response response = ShipmentsV1Request.GET(shipmentNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Shipment.json"));

        shipmentUuid = response.as(ShipmentV1Response.class).getShipment().getUuid();
    }

    @Story("Заказы")
    @CaseId(117)
    @Test(description = "Контрактный тест списка офферов в шипменте",
            groups = "api-instamart-regress")
    public void getShipmentOffers() {
        Response response = ShipmentsV1Request.Offers.GET(shipmentNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/ShipmentOffers.json"));
    }

    @Story("Заказы")
    @CaseId(118)
    @Test(description = "Контрактный тест списка лайн айтемов в шимпенте",
            groups = "api-instamart-regress")
    public void getLineItems() {
        Response response = LineItemsV1Request.GET(shipmentNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/LineItems.json"));
        productSku = response.as(LineItemsV1Response.class)
                .getLineItems()
                .get(0)
                .getOffer()
                .getProductSku();
    }

    @Story("Заказы")
    @CaseId(119)
    @Test(description = "Контрактный тест списка предзамен для товара из шипмента",
            groups = "api-instamart-regress",
            dependsOnMethods = "getLineItems")
    public void getShipmentProductsPrereplacements() {
        Response response = ShipmentsV1Request.Products.Prereplacements.GET(shipmentNumber, Long.parseLong(productSku));
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Prereplacements.json"));
    }

    //todo убрать скип после выдачи прав SD-13260
    @Skip(onServer = Server.PRODUCTION)
    @Issues({@Issue("INFRADEV-3167"), @Issue("STF-9483")})
    @Story("Заказы")
    @CaseId(120)
    @Test(enabled = false,
            description = "Контрактный тест списка сэмплов в шипменте",
            groups = "api-instamart-regress",
            dependsOnMethods = "getShipment")
    public void getShopperMarketingSampleItems() {
        Response response = ShoppersV1Request.MarketingSampleItems.GET(shipmentUuid);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/MarketingSampleItems.json"));
    }

    //todo убрать скип после выдачи прав SD-13260
    @Skip(onServer = Server.PRODUCTION)
    @Story("Заказы")
    @CaseId(121)
    @Test(description = "Контрактный тест списка способов оплаты в заказе",
            groups = "api-instamart-regress")
    public void getShopperOrderAvailablePaymentTools() {
        Response response = ShoppersV1Request.OrderAvailablePaymentTools.GET(orderNumber);
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/PaymentTools.json"));
    }
}
