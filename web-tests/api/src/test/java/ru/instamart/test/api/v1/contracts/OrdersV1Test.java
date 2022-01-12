package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.*;
import ru.instamart.api.request.v1.*;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.OrderV1;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.response.v1.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
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
        Response response = OrdersV1Request.GET(1);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrdersV1Response.class);
    }

    @Story("Заказы")
    @CaseId(115)
    @Test(description = "Контрактный тест инфы о заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getOrder() {
        final Response response = OrdersV1Request.GET(orderNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderV1.class);
    }

    @Story("Заказы")
    @CaseId(116)
    @Test(description = "Контрактный тест инфы о шипменте",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getShipment() {
        final Response response = ShipmentsV1Request.GET(shipmentNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentV1Response.class);

        shipmentUuid = response.as(ShipmentV1Response.class).getShipment().getUuid();
    }

    @Story("Заказы")
    @CaseId(117)
    @Test(description = "Контрактный тест списка офферов в шипменте",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getShipmentOffers() {
        final Response response = ShipmentsV1Request.Offers.GET(shipmentNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentOffersV1Response.class);
    }

    @Story("Заказы")
    @CaseId(118)
    @Test(description = "Контрактный тест списка лайн айтемов в шимпенте",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getLineItems() {
        final Response response = LineItemsV1Request.GET(shipmentNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, LineItemsV1Response.class);
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
        final Response response = ShipmentsV1Request.Products.Prereplacements.GET(shipmentNumber, Long.parseLong(productSku));
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PreReplacementV1Response.class);
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
        final Response response = ShoppersV1Request.MarketingSampleItems.GET(shipmentUuid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MarketingSamplesItemsV1Response.class);
    }

    //todo убрать скип после выдачи прав SD-13260
    @Skip(onServer = Server.PRODUCTION)
    @Story("Заказы")
    @CaseId(121)
    @Test(description = "Контрактный тест списка способов оплаты в заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getShopperOrderAvailablePaymentTools() {
        final Response response = ShoppersV1Request.OrderAvailablePaymentTools.GET(orderNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AvailablePaymentToolsV1Response.class);
    }

    @Story("Заказы")
    @CaseId(43)
    @Test(description = "Получение информации о мультиритейлерном заказе",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getMultireteilerOrder() {
        final Response response = MultiretailerOrderV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MultiretailerOrderV1Response.class);
    }
}
