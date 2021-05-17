package ru.instamart.tests.api.shopper.app.endpoints;

import ru.instamart.api.common.RestBase;
import ru.instamart.api.helper.RegistrationHelper;
import ru.instamart.api.model.shopper.app.AssemblyDataSHP;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.api.response.shopper.app.*;
import ru.instamart.core.testdata.UserData;
import ru.instamart.core.testdata.UserManager;
import ru.instamart.core.testdata.pagesdata.EnvironmentData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkIsDeliveryToday;
import static ru.instamart.api.checkpoint.ShopperApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotNull;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShipmentfulShopperAppTest extends RestBase {
    String shipmentId;
    String assemblyId;
    String assemblyItemId;
    Integer itemQty;

    @BeforeClass(alwaysRun = true,
                 description = "Оформляем заказ")
    public void preconditions() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        OrderV2 order = apiV2.order(userData, EnvironmentData.INSTANCE.getDefaultSid());
        String isDeliveryToday = checkIsDeliveryToday(order);
        shopper.authorisation(UserManager.getDefaultShopper());
        shopper.deleteCurrentAssembly();
        shipmentId = shopper.getShipmentId(order.getShipments().get(0).getNumber(), isDeliveryToday);
    }

    @AfterClass(alwaysRun = true,
                description = "Удаляем текущую сборку")
    public void cleanup() {
        shopper.deleteCurrentAssembly();
    }

    @Story("Начало сборки")
    @CaseId(4)
    @Test(  description = "Проверяем импортировался ли заказ",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void createOrderImport() {
        //todo проверять сам вебхук
        assertNotNull(shipmentId);
    }

    @Story("Начало сборки")
    @CaseId(13)
    @Test(  description = "Создаём сборку",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void postAssembly200() {
        response = AssembliesSHPRequest.POST(shipmentId);
        checkStatusCode200(response);
        AssemblyDataSHP assembly = response.as(AssemblySHPResponse.class).getData();
        assertNotNull(assembly.getId(), "Не вернулась сборка");
        assemblyId = assembly.getId();
        assemblyItemId = assembly.getRelationships()
                .getItems()
                .getData()
                .get(0)
                .getId();
    }

    @Story("Получение информации о сборках")
    @CaseId(3)
    @Test(  description = "Получаем сборку по номеру",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getAssembly200() {
        response = AssembliesSHPRequest.GET(assemblyId);
        checkStatusCode200(response);
        assertNotNull(itemQty = response.as(AssemblySHPResponse.class)
                        .getIncluded()
                        .get(0)
                        .getAttributes()
                        .getQty(),
                "Не вернулась сборка");
    }

    @Story("Получение информации о заказах")
    @CaseId(5)
    @Test(  description = "Получаем все заказы для сборщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShopperShipments200() {
        response = ShopperSHPRequest.Shipments.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentsSHPResponse.class).getData(),
                "Не вернулись заказы сборщика");
    }

    @Story("Получение информации о сборках")
    @CaseId(6)
    @Test(  description = "Получаем все сборки сборщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getShopperAssemblies200() {
        response = ShopperSHPRequest.Assemblies.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(AssembliesSHPResponse.class)
                        .getData()
                        .get(0)
                        .getId(),
                "Не вернулись сборки сборщика");
    }

    @Story("Процесс сборки")
    @CaseId(7)
    @Test(  description = "Собираем товар",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = {"postAssembly200", "getAssembly200"})
    public void patchAssemblyItem200() {
        response = AssemblyItemsSHPRequest.PATCH(assemblyId, assemblyItemId, itemQty);
        checkStatusCode200(response);
        assertNotNull(response.as(AssemblyItemSHPResponse.class).getData());
    }

    @Story("Хелпдеск")
    @CaseId(8)
    @Test(  description = "Получаем тикеты хелпдеска",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getHelpdeskTickets200() {
        response = HelpdeskSHPRequest.Tickets.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(TicketsSHPResponse.class).getData(),
                "Не вернулись тикеты хелпдеска");
    }

    @Story("Получение информации о заказах")
    @CaseId(9)
    @Test(  description = "Получаем заказ по номеру",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShipment200() {
        response = ShipmentsSHPRequest.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentSHPResponse.class)
                        .getData()
                        .getAttributes()
                        .getAddress()
                        .getAddress1(),
                "Не вернулся заказ");
    }

    @Story("Получение информации о сборках")
    @CaseId(17)
    @Test(  description = "Получаем предзамены для позиций в сборке",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getAssemblyItemPrereplacements200() {
        response = AssemblyItemsSHPRequest.Prereplacements.GET(assemblyItemId);
        checkStatusCode200(response);
        assertNotNull(response.as(PrereplacementsSHPResponse.class).getPrereplacement(),
                "Не вернулись предзамены");
    }

    @Story("Получение информации о заказах")
    @CaseId(22)
    @Test(  description = "Получаем инфу о стоках товаров в заказе",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShipmentStock200() {
        response = ShipmentsSHPRequest.Stocks.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentStocksSHPResponse.class).getOffers().get(0).getStock(),
                "Не вернулась инфа о стоках товаров в заказе");
    }

    @Story("Оплата")
    @CaseId(44)
    @Test(  description = "Оплачиваем заказ через LifePay",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void putAssemblyLifePay200() {
        response = AssembliesSHPRequest.LifePay.PUT(assemblyId);
        checkStatusCode200(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(45)
    @Test(  description = "Получаем маркетинговые пробники для заказа",
            groups = {"api-shopper-smoke", "api-shopper-prod"},
            dependsOnMethods = "postAssembly200")
    public void getShipmentMarketingSampleItems200() {
        response = ShipmentsSHPRequest.MarketingSampleItems.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(MarketingSampleItemsSHPResponse.class).getMarketingSampleItems(),
                "Не вернулся массив маркетинговых пробников");
    }
}
