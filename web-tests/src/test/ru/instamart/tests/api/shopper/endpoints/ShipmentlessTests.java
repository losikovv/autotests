package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.common.RestBase;
import instamart.api.objects.shopper.Reason;
import instamart.api.requests.ShopperApiRequests;
import instamart.api.responses.shopper.*;
import instamart.core.common.AppManager;
import instamart.core.testdata.Users;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static instamart.api.checkpoints.ShopperApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class ShipmentlessTests extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        kraken.shopperApi().authorisation(Users.shopper());
    }

    @Test(  description = "Получаем инфу о сборщике",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 108)
    public void getShopper() {
        response = ShopperApiRequests.getShopper();
        assertStatusCode200(response);
        assertNotNull(response.as(ShopperResponse.class)
                        .getData()
                        .getAttributes()
                        .getName(),
                "Не вернулась инфа о сборщике");
    }

    @Test(  description = "Получаем маршруты",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 109)
    public void getRoutes() {
        response = ShopperApiRequests.getRoutes();
        assertStatusCode200(response);
        response.prettyPeek();
    }

    @Test(  description = "Получаем смены сборщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 110)
    public void getShopperOperationShifts() {
        response = ShopperApiRequests.getShopperOperationShifts();
        assertStatusCode200(response);
        response.prettyPeek();
    }

    @Test(  description = "Получаем заказы водителя",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 111)
    public void getDriverShipments() {
        response = ShopperApiRequests.getDriverShipments();
        assertStatusCode200(response);
        response.prettyPeek();
    }

    @Test(  description = "Получаем причины отмен",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 112)
    public void getCancelReasons() {
        response = ShopperApiRequests.getCancelReasons();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины отмен");
    }

    @Test(  description = "Получаем причины несоответсвия",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 112)
    public void getClarifyReasons() {
        response = ShopperApiRequests.getClarifyReasons();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины несоответствия");
    }

    @Test(  description = "Получаем причины возврата",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 112)
    public void getReturnReasons() {
        response = ShopperApiRequests.getReturnReasons();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины возврата");
    }

    @Test(  description = "Получаем марс токен (стоки метро)",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 113)
    public void getMarsToken() {
        response = ShopperApiRequests.getMarsToken();
        assertStatusCode200(response);
        assertNotNull(response.as(MarsTokenResponse.class).getAccess_token(),
                "Не вернулся марс токен");
    }

    @Test(  description = "Получаем заказы для упаковщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 116)
    public void getPackerShipments() {
        response = ShopperApiRequests.getPackerShipments();
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы для упаковщика");
    }

    @Test(  description = "Получаем сборки упаковщика",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 117)
    public void getPackerAssemblies() {
        response = ShopperApiRequests.getPackerAssemblies();
        assertStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class).getData(),
                "Не вернулись сборки упаковщика");
    }

    @Test(  description = "Получаем инфу о текущей версии приложения",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 118)
    public void getCurrentAppVersion() {
        response = ShopperApiRequests.getCurrentAppVersion();
        assertStatusCode200(response);
        assertNotNull(response.as(AppVersionResponse.class).getData().getAttributes().getMajor(),
                "Не вернулась инфа о текущей версии приложения");
    }

    @Test(  description = "Поиск товаров",
            groups = {"rest-smoke","rest-shopper-smoke"},
            priority = 119)
    public void getStoreOffers() {
        response = ShopperApiRequests.getStoreOffers(
                AppManager.environment.getDefaultShopperSid(),
                "хлеб");
        assertStatusCode200(response);
        assertNotNull(response.as(OffersResponse.class).getData().get(0).getAttributes().getName(),
                "Не работает поиск товаров");
    }
}
