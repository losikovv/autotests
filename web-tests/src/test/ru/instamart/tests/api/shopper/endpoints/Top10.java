package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.shopper.AssemblyData;
import instamart.api.objects.v2.Order;
import instamart.api.requests.ShopperApiRequests;
import instamart.api.responses.shopper.*;
import instamart.core.common.AppManager;
import instamart.core.testdata.Users;
import instamart.ui.common.pagesdata.UserData;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Top10 extends RestBase {
    String shipmentId;
    String assemblyId;
    String itemId;
    Integer itemQty;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        UserData user = user();
        kraken.apiV2().registration(user);
        Order order = kraken.apiV2().order(user, AppManager.environment.getDefaultSid());
        kraken.apiV2().assertIsDeliveryToday(order);
        kraken.shopperApi().authorisation(Users.shopper());
        kraken.shopperApi().deleteCurrentAssembly();
        shipmentId = kraken.shopperApi().getShipmentId(order.getShipments().get(0).getNumber());
    }

    @AfterClass(alwaysRun = true)
    public void cleanup() {
        if (kraken.shopperApi().authorized())
            kraken.shopperApi().deleteCurrentAssembly();
    }

    @Test(  description = "Создаём сборку",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 1)
    public void postAssembly() {
        response = ShopperApiRequests.postAssembly(shipmentId);
        assertEquals(response.getStatusCode(), 200);
        AssemblyData assembly = response.as(AssemblyResponse.class).getData();
        assertNotNull(assembly.getId(), "Не вернулась сборка");

        assemblyId = assembly.getId();
        itemId = assembly.getRelationships()
                .getItems()
                .getData()
                .get(0)
                .getId();
    }

    @Test(  description = "Получаем сборку по номеру",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 2,
            dependsOnMethods = "postAssembly")
    public void getAssembly() {
        response = ShopperApiRequests.getAssembly(assemblyId);
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(itemQty = response.as(AssemblyResponse.class)
                        .getIncluded()
                        .get(0)
                        .getAttributes()
                        .getQty(),
                "Не вернулась сборка");
    }

    @Test(  description = "Получаем все заказы сборщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 3)
    public void getShopperShipments() {
        response = ShopperApiRequests.getShopperShipments();
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(shipmentId = response.as(ShipmentsResponse.class)
                        .getData()
                        .get(0)
                        .getId(),
                "Не вернулись заказы сборщика");
    }

    @Test(  description = "Получаем все сборки сборщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 4,
            dependsOnMethods = "postAssembly")
    public void getShopperAssemblies() {
        response = ShopperApiRequests.getShopperAssemblies();
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(AssembliesResponse.class)
                        .getData()
                        .get(0)
                        .getId(),
                "Не вернулись сборки сборщика");
    }

    @Test(  description = "Собираем товар",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 5,
            dependsOnMethods = {"postAssembly", "getAssembly"})
    public void patchAssemblyItem() {
        response = ShopperApiRequests.patchAssemblyItem(assemblyId, itemId, itemQty);
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(AssemblyItemResponse.class).getData());
    }

    @Test(  description = "Получаем тикеты хелпдеска",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 6)
    public void getHelpdeskTickets() {
        response = ShopperApiRequests.getHelpdeskTickets(shipmentId);
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(TicketsResponse.class).getData(),
                "Не вернулся заказ");
    }

    @Test(  description = "Получаем заказ",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 7)
    public void getShipment() {
        response = ShopperApiRequests.getShipment(shipmentId);
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(ShipmentResponse.class)
                        .getData()
                        .getAttributes()
                        .getAddress()
                        .getAddress1(),
                "Не вернулся заказ");
    }

    @Test(  description = "Получаем инфу о сборщике",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 8)
    public void getShopper() {
        response = ShopperApiRequests.getShopper();
        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(ShopperResponse.class)
                        .getData()
                        .getAttributes()
                        .getName(),
                "Не вернулась инфа о сборщике");
    }

    @Test(  description = "Получаем маршруты",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 9)
    public void getRoutes() {
        response = ShopperApiRequests.getRoutes();
        assertEquals(response.getStatusCode(), 200);
    }

    @Test(  description = "Получаем смены сборщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 10)
    public void getShopperOperationShifts() {
        response = ShopperApiRequests.getShopperOperationShifts();
        assertEquals(response.getStatusCode(), 200);
    }
}
