package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.v2.Order;
import instamart.api.requests.ShopperApiRequests;
import instamart.api.responses.shopper.*;
import instamart.core.common.AppManager;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.ShopperApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class ShipmentfulTests extends RestBase {
    String shipmentId;
    String assemblyId;
    String assemblyItemId;
    Integer itemQty;

    @BeforeClass(alwaysRun = true,
                 description = "Оформляем заказ")
    public void preconditions() {
        UserData user = user();
        apiV2.registration(user);
        Order order = apiV2.order(user, AppManager.environment.getDefaultSid());
        ApiV2Checkpoints.assertIsDeliveryToday(order);
        shopper.authorisation(Users.shopper());
        shopper.deleteCurrentAssembly();
        shipmentId = shopper.getShipmentId(order.getShipments().get(0).getNumber());
    }

    @AfterClass(alwaysRun = true,
                description = "Удаляем текущую сборку")
    public void cleanup() {
        if (shopper.authorized())
            shopper.deleteCurrentAssembly();
    }

    @CaseId(22)
    @Test(  description = "Проверяем импортировался ли заказ",
            groups = {"api-shopper-smoke"})
    public void createOrderImport() {
        //todo проверять сам вебхук
        assertNotNull(shipmentId);
    }

    @CaseId(31)
    @Test(  description = "Создаём сборку",
            groups = {"api-shopper-smoke"})
    public void postAssembly() {
        response = ShopperApiRequests.Assemblies.POST(shipmentId);
        assertStatusCode200(response);
        AssemblyData assembly = response.as(AssemblyResponse.class).getData();
        assertNotNull(assembly.getId(), "Не вернулась сборка");
        assemblyId = assembly.getId();
        assemblyItemId = assembly.getRelationships()
                .getItems()
                .getData()
                .get(0)
                .getId();
    }

    @CaseId(21)
    @Test(  description = "Получаем сборку по номеру",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getAssembly() {
        response = ShopperApiRequests.Assemblies.GET(assemblyId);
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(itemQty = response.as(AssemblyResponse.class)
                        .getIncluded()
                        .get(0)
                        .getAttributes()
                        .getQty(),
                "Не вернулась сборка");
    }

    @CaseId(23)
    @Test(  description = "Получаем все заказы сборщика",
            groups = {"api-shopper-smoke"})
    public void getShopperShipments() {
        response = ShopperApiRequests.Shopper.Shipments.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы сборщика");
    }

    @CaseId(24)
    @Test(  description = "Получаем все сборки сборщика",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getShopperAssemblies() {
        response = ShopperApiRequests.Shopper.Assemblies.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class)
                        .getData()
                        .get(0)
                        .getId(),
                "Не вернулись сборки сборщика");
    }

    @CaseId(25)
    @Test(  description = "Собираем товар",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = {"postAssembly", "getAssembly"})
    public void patchAssemblyItem() {
        response = ShopperApiRequests.AssemblyItems.PATCH(assemblyId, assemblyItemId, itemQty);
        assertStatusCode200(response);
        assertNotNull(response.as(AssemblyItemResponse.class).getData());
    }

    @CaseId(26)
    @Test(  description = "Получаем тикеты хелпдеска",
            groups = {"api-shopper-smoke"})
    public void getHelpdeskTickets() {
        response = ShopperApiRequests.Helpdesk.Tickets.GET(shipmentId);
        assertStatusCode200(response);
        assertNotNull(response.as(TicketsResponse.class).getData(),
                "Не вернулся заказ");
    }

    @CaseId(27)
    @Test(  description = "Получаем заказ",
            groups = {"api-shopper-smoke"})
    public void getShipment() {
        response = ShopperApiRequests.Shipments.GET(shipmentId);
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentResponse.class)
                        .getData()
                        .getAttributes()
                        .getAddress()
                        .getAddress1(),
                "Не вернулся заказ");
    }

    @CaseId(35)
    @Test(  description = "Получаем предзамены",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postAssembly")
    public void getAssemblyItemPrereplacements() {
        response = ShopperApiRequests.AssemblyItems.Prereplacements.GET(assemblyItemId);
        assertStatusCode200(response);
        assertNotNull(response.as(PrereplacementsResponse.class).getPrereplacement(),
                "Не вернулись предзамены");
    }

    @CaseId(40)
    @Test(  description = "Получаем инфу о стоках товаров в заказе",
            groups = {"api-shopper-smoke"})
    public void getShipmentStock() {
        response = ShopperApiRequests.Shipments.Stocks.GET(shipmentId);
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentStocksResponse.class).getOffers().get(0).getStock(),
                "Не вернулась инфа о стоках товаров в заказе");
    }
}
