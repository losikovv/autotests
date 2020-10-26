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

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        UserData user = user();
        kraken.apiV2().registration(user);
        Order order = kraken.apiV2().order(user, AppManager.environment.getDefaultSid());
        ApiV2Checkpoints.assertIsDeliveryToday(order);
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
            priority = 101)
    public void postAssembly() {
        response = ShopperApiRequests.postAssembly(shipmentId);
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

    @Test(  description = "Получаем сборку по номеру",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 102,
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
            priority = 103)
    public void getShopperShipments() {
        response = ShopperApiRequests.getShopperShipments();
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы сборщика");
    }

    @Test(  description = "Получаем все сборки сборщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 104,
            dependsOnMethods = "postAssembly")
    public void getShopperAssemblies() {
        response = ShopperApiRequests.getShopperAssemblies();
        assertStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class)
                        .getData()
                        .get(0)
                        .getId(),
                "Не вернулись сборки сборщика");
    }

    @Test(  description = "Собираем товар",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 105,
            dependsOnMethods = {"postAssembly", "getAssembly"})
    public void patchAssemblyItem() {
        response = ShopperApiRequests.patchAssemblyItem(assemblyId, assemblyItemId, itemQty);
        assertStatusCode200(response);
        assertNotNull(response.as(AssemblyItemResponse.class).getData());
    }

    @Test(  description = "Получаем тикеты хелпдеска",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 106)
    public void getHelpdeskTickets() {
        response = ShopperApiRequests.getHelpdeskTickets(shipmentId);
        assertStatusCode200(response);
        assertNotNull(response.as(TicketsResponse.class).getData(),
                "Не вернулся заказ");
    }

    @Test(  description = "Получаем заказ",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 107)
    public void getShipment() {
        response = ShopperApiRequests.getShipment(shipmentId);
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentResponse.class)
                        .getData()
                        .getAttributes()
                        .getAddress()
                        .getAddress1(),
                "Не вернулся заказ");
    }

    @Test(  description = "Получаем предзамены",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 115,
            dependsOnMethods = "postAssembly")
    public void getAssemblyItemPrereplacements() {
        response = ShopperApiRequests.getAssemblyItemPrereplacements(assemblyItemId);
        assertStatusCode200(response);
        assertNotNull(response.as(PrereplacementsResponse.class).getPrereplacement(),
                "Не вернулись предзамены");
    }

    @Test(  description = "Получаем инфу о стоках товаров в заказе",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 120)
    public void getShipmentStock() {
        response = ShopperApiRequests.getShipmentStocks(shipmentId);
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentStocksResponse.class).getOffers().get(0).getStock(),
                "Не вернулась инфа о стоках товаров в заказе");
    }
}
