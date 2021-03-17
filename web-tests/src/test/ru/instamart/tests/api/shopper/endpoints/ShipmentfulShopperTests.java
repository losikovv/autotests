package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.common.RestBase;
import instamart.api.helpers.RegistrationHelper;
import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.v2.Order;
import instamart.api.requests.shopper.*;
import instamart.api.responses.shopper.*;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkIsDeliveryToday;
import static instamart.api.checkpoints.ShopperApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShipmentfulShopperTests extends RestBase {
    String shipmentId;
    String assemblyId;
    String assemblyItemId;
    Integer itemQty;

    @BeforeClass(alwaysRun = true,
                 description = "Оформляем заказ")
    public void preconditions() {
        final UserData userData = UserManager.getUser();
        RegistrationHelper.registration(userData);
        Order order = apiV2.order(userData, EnvironmentData.INSTANCE.getDefaultSid());
        checkIsDeliveryToday(order);
        shopper.authorisation(UserManager.getDefaultShopper());
        shopper.deleteCurrentAssembly();
        shipmentId = shopper.getShipmentId(order.getShipments().get(0).getNumber());
    }

    @AfterClass(alwaysRun = true,
                description = "Удаляем текущую сборку")
    public void cleanup() {
        shopper.deleteCurrentAssembly();
    }

    @Story("Начало сборки")
    @CaseId(4)
    @Test(  description = "Проверяем импортировался ли заказ",
            groups = {"api-shopper-smoke"})
    public void createOrderImport() {
        //todo проверять сам вебхук
        assertNotNull(shipmentId);
    }

    @Story("Начало сборки")
    @CaseId(13)
    @Test(  description = "Создаём сборку",
            groups = {"api-shopper-smoke"})
    public void postAssembly() {
        response = AssembliesRequest.POST(shipmentId);
        checkStatusCode200(response);
        AssemblyData assembly = response.as(AssemblyResponse.class).getData();
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
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getAssembly() {
        response = AssembliesRequest.GET(assemblyId);
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(itemQty = response.as(AssemblyResponse.class)
                        .getIncluded()
                        .get(0)
                        .getAttributes()
                        .getQty(),
                "Не вернулась сборка");
    }

    @Story("Получение информации о заказах")
    @CaseId(5)
    @Test(  description = "Получаем все заказы для сборщика",
            groups = {"api-shopper-smoke"})
    public void getShopperShipments() {
        response = ShopperRequest.Shipments.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы сборщика");
    }

    @Story("Получение информации о сборках")
    @CaseId(6)
    @Test(  description = "Получаем все сборки сборщика",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getShopperAssemblies() {
        response = ShopperRequest.Assemblies.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class)
                        .getData()
                        .get(0)
                        .getId(),
                "Не вернулись сборки сборщика");
    }

    @Story("Процесс сборки")
    @CaseId(7)
    @Test(  description = "Собираем товар",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = {"postAssembly", "getAssembly"})
    public void patchAssemblyItem() {
        response = AssemblyItemsRequest.PATCH(assemblyId, assemblyItemId, itemQty);
        checkStatusCode200(response);
        assertNotNull(response.as(AssemblyItemResponse.class).getData());
    }

    @Story("Хелпдеск")
    @CaseId(8)
    @Test(  description = "Получаем тикеты хелпдеска",
            groups = {"api-shopper-smoke"})
    public void getHelpdeskTickets() {
        response = HelpdeskRequest.Tickets.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(TicketsResponse.class).getData(),
                "Не вернулись тикеты хелпдеска");
    }

    @Story("Получение информации о заказах")
    @CaseId(9)
    @Test(  description = "Получаем заказ по номеру",
            groups = {"api-shopper-smoke"})
    public void getShipment() {
        response = ShipmentsRequest.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentResponse.class)
                        .getData()
                        .getAttributes()
                        .getAddress()
                        .getAddress1(),
                "Не вернулся заказ");
    }

    @Story("Получение информации о сборках")
    @CaseId(17)
    @Test(  description = "Получаем предзамены для позиций в сборке",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getAssemblyItemPrereplacements() {
        response = AssemblyItemsRequest.Prereplacements.GET(assemblyItemId);
        checkStatusCode200(response);
        assertNotNull(response.as(PrereplacementsResponse.class).getPrereplacement(),
                "Не вернулись предзамены");
    }

    @Story("Получение информации о заказах")
    @CaseId(22)
    @Test(  description = "Получаем инфу о стоках товаров в заказе",
            groups = {"api-shopper-smoke"})
    public void getShipmentStock() {
        response = ShipmentsRequest.Stocks.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentStocksResponse.class).getOffers().get(0).getStock(),
                "Не вернулась инфа о стоках товаров в заказе");
    }

    @Story("Оплата")
    @CaseId(44)
    @Test(  description = "Оплачиваем заказ через LifePay",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void putAssemblyLifePay() {
        response = AssembliesRequest.LifePay.PUT(assemblyId);
        checkStatusCode200(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(45)
    @Test(  description = "Получаем маркетинговые пробники для заказа",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getShipmentMarketingSampleItems() {
        response = ShipmentsRequest.MarketingSampleItems.GET(shipmentId);
        checkStatusCode200(response);
        assertNotNull(response.as(MarketingSampleItemsResponse.class).getMarketing_sample_items(),
                "Не вернулся массив маркетинговых пробников");
    }
}
