package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.app.AssemblySHP;
import ru.instamart.api.model.shopper.app.ShipmentSHP;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.api.response.shopper.app.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Objects;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkIsDeliveryToday;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShipmentfulShopperAppTest extends RestBase {
    private ShipmentSHP.Data shipment;
    private String assemblyId;
    private String assemblyItemId;
    private Integer itemQty;

    @BeforeClass(alwaysRun = true,
            description = "Получаем оформленный заказ")
    public void preconditions() {
        shopperApp.authorisation(UserManager.getDefaultShopper());
        shopperApp.deleteCurrentAssembly();
        String COMMENT = "SHP-TEST-SINGLE";
        shipment = shopperApp.getShipmentByComment(COMMENT);

        if (Objects.isNull(shipment)) {
            SessionFactory.makeSession(SessionType.API_V2);
            OrderV2 order = apiV2.order(
                    SessionFactory.getSession(SessionType.API_V2).getUserData(),
                    EnvironmentProperties.DEFAULT_SID,
                    1,
                    COMMENT);
            if (Objects.isNull(order)) throw new SkipException("Заказ не удалось оплатить");
            String errorMessageIfDeliveryIsNotToday = checkIsDeliveryToday(order);
            shipment = shopperApp.getShipment(order.getShipments().get(0).getNumber(), errorMessageIfDeliveryIsNotToday);
        }
    }

    @AfterClass(alwaysRun = true,
            description = "Удаляем текущую сборку")
    public void cleanup() {
        shopperApp.authorisation(UserManager.getDefaultShopper());
        shopperApp.deleteCurrentAssembly();
    }

    @Story("Начало сборки")
    @CaseId(4)
    @Test(description = "Проверяем импортировался ли заказ",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void createOrderImport() {
        //todo проверять сам вебхук

        checkFieldIsNotEmpty(shipment.getId(), "shipmentId");
    }

    @Story("Начало сборки")
    @CaseId(13)
    @Test(description = "Создаём сборку",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void postAssembly200() {
        response = AssembliesSHPRequest.POST(shipment.getId());
        checkStatusCode200(response);
        AssemblySHP.Data assembly = response.as(AssemblySHPResponse.class).getData();
        checkFieldIsNotEmpty(assembly.getId(), "сборка");
        assemblyId = assembly.getId();
        assemblyItemId = assembly.getRelationships()
                .getItems()
                .getData()
                .get(0)
                .getId();
    }

    @Story("Получение информации о сборках")
    @CaseId(3)
    @Test(description = "Получаем сборку по номеру",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getAssembly200() {
        response = AssembliesSHPRequest.GET(assemblyId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssemblySHPResponse.class);
        itemQty = response.as(AssemblySHPResponse.class)
                .getIncluded()
                .get(0)
                .getAttributes()
                .getQty();
    }

    @Story("Получение информации о заказах")
    @CaseId(5)
    @Test(description = "Получаем все заказы для сборщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShopperShipments200() {
        response = ShopperSHPRequest.Shipments.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentsSHPResponse.class);
    }

    @Story("Получение информации о заказах")
    @CaseId(105)
    @Test(enabled = false,
            description = "Получаем все заказы для сборщика",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperShipment200() {
        response = ShopperSHPRequest.Shipment.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentSHPResponse.class);
    }


    @Story("Получение информации о сборках")
    @CaseId(6)
    @Test(description = "Получаем все сборки сборщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getShopperAssemblies200() {
        response = ShopperSHPRequest.Assemblies.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssembliesSHPResponse.class);
    }

    @Story("Процесс сборки")
    @CaseId(7)
    @Test(description = "Собираем товар PATCH",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = {"postAssembly200", "getAssembly200"})
    public void patchAssemblyItem200() {
        response = AssemblyItemsSHPRequest.PATCH(assemblyId, assemblyItemId, itemQty);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssemblyItemSHPResponse.class);
    }

    @Story("Процесс сборки")
    @CaseId(7)
    @Test(description = "Собираем товар PUT",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = {"postAssembly200", "getAssembly200"})
    public void putAssemblyItem200() {
        response = AssemblyItemsSHPRequest.PUT(assemblyId, assemblyItemId, itemQty);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssemblyItemSHPResponse.class);
    }

    @Story("Хелпдеск")
    @CaseId(102)
    @Test(description = "Создаем новый тикет в хелпдеске",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void createHelpdeskTicket() {
        Response response = HelpdeskSHPRequest.Tickets.POST(shipment.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TicketSHPResponse.class);
        TicketSHPResponse ticketFromResponse = response.as(TicketSHPResponse.class);
        compareTwoObjects(ticketFromResponse.getData().getAttributes().getTitle(), shipment.getAttributes().getNumber() + " АПИ-АВТОТЕСТ");
    }

    @Story("Хелпдеск")
    @CaseId(8)
    @Test(description = "Получаем тикеты хелпдеска",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = {"createHelpdeskTicket"})
    public void getHelpdeskTickets200() {
        Response response = HelpdeskSHPRequest.Tickets.GET(shipment.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TicketsSHPResponse.class);
        TicketsSHPResponse ticketsFromResponse = response.as(TicketsSHPResponse.class);
        compareTwoObjects(ticketsFromResponse.getData().get(0).getAttributes().getTitle(), shipment.getAttributes().getNumber() + " АПИ-АВТОТЕСТ");
    }

    @Story("Получение информации о заказах")
    @CaseId(9)
    @Test(description = "Получаем заказ по номеру",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShipment200() {
        response = ShipmentsSHPRequest.GET(shipment.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentSHPResponse.class);
    }

    @Story("Получение информации о сборках")
    @CaseId(17)
    @Test(description = "Получаем предзамены для позиций в сборке",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getAssemblyItemPrereplacements200() {
        response = AssemblyItemsSHPRequest.Prereplacements.GET(assemblyItemId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PrereplacementsSHPResponse.class);
    }

    @Story("Получение информации о заказах")
    @CaseId(22)
    @Test(description = "Получаем инфу о стоках товаров в заказе",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShipmentStock200() {
        response = ShipmentsSHPRequest.Stocks.GET(shipment.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShipmentStocksSHPResponse.class);
    }

    @Story("Оплата")
    @CaseId(44)
    @Test(description = "Оплачиваем заказ через LifePay",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void putAssemblyLifePay200() {
        response = AssembliesSHPRequest.LifePay.PUT(assemblyId);
        checkStatusCode200(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(45)
    @Test(description = "Получаем маркетинговые пробники для заказа",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getShipmentMarketingSampleItems200() {
        response = ShipmentsSHPRequest.MarketingSampleItems.GET(shipment.getId());
        checkStatusCode200(response);
        assertNotNull(response.as(MarketingSampleItemsSHPResponse.class).getMarketingSampleItems(),
                "Не вернулся массив маркетинговых пробников"); //ATST-804
    }
}
