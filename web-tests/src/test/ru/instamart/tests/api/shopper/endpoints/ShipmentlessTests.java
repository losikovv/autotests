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
        shopper.authorisation(Users.shopper());
    }

    @Test(  description = "Получаем инфу о сборщике",
            groups = {"api-shopper-smoke"})
    public void getShopper() {
        response = ShopperApiRequests.Shopper.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(ShopperResponse.class)
                        .getData()
                        .getAttributes()
                        .getName(),
                "Не вернулась инфа о сборщике");
    }

    @Test(  description = "Получаем маршруты",
            groups = {"api-shopper-smoke"})
    public void getRoutes() {
        response = ShopperApiRequests.Routes.GET();
        assertStatusCode200(response);
        response.prettyPeek();
    }

    @Test(  description = "Получаем смены сборщика",
            groups = {"api-shopper-smoke"})
    public void getShopperOperationShifts() {
        response = ShopperApiRequests.Shopper.OperationShifts.GET();
        assertStatusCode200(response);
        response.prettyPeek();
    }

    @Test(  description = "Получаем заказы водителя",
            groups = {"api-shopper-smoke"})
    public void getDriverShipments() {
        response = ShopperApiRequests.Driver.Shipments.GET();
        assertStatusCode200(response);
        response.prettyPeek();
    }

    @Test(  description = "Получаем причины отмен",
            groups = {"api-shopper-smoke"})
    public void getCancelReasons() {
        response = ShopperApiRequests.CancelReasons.GET();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины отмен");
    }

    @Test(  description = "Получаем причины уточнения",
            groups = {"api-shopper-smoke"})
    public void getClarifyReasons() {
        response = ShopperApiRequests.ClarifyReasons.GET();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины уточнения");
    }

    @Test(  description = "Получаем причины возврата",
            groups = {"api-shopper-smoke"})
    public void getReturnReasons() {
        response = ShopperApiRequests.ReturnReasons.GET();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины возврата");
    }

    @Test(  description = "Получаем марс токен (стоки метро)",
            groups = {"api-shopper-smoke"})
    public void getMarsToken() {
        response = ShopperApiRequests.MarsToken.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(MarsTokenResponse.class).getAccess_token(),
                "Не вернулся марс токен");
    }

    @Test(  description = "Получаем заказы для упаковщика",
            groups = {"api-shopper-smoke"})
    public void getPackerShipments() {
        response = ShopperApiRequests.Packer.Shipments.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы для упаковщика");
    }

    @Test(  description = "Получаем сборки упаковщика",
            groups = {"api-shopper-smoke"})
    public void getPackerAssemblies() {
        response = ShopperApiRequests.Packer.Assemblies.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class).getData(),
                "Не вернулись сборки упаковщика");
    }

    @Test(  description = "Получаем инфу о текущей версии приложения",
            groups = {"api-shopper-smoke"})
    public void getCurrentAppVersion() {
        response = ShopperApiRequests.CurrentAppVersion.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(AppVersionResponse.class).getData().getAttributes().getMajor(),
                "Не вернулась инфа о текущей версии приложения");
    }

    @Test(  description = "Поиск товаров",
            groups = {"api-shopper-smoke"})
    public void getStoreOffers() {
        response = ShopperApiRequests.Stores.Offers.GET(
                AppManager.environment.getDefaultShopperSid(),
                "хлеб");
        assertStatusCode200(response);
        assertNotNull(response.as(OffersResponse.class).getData().get(0).getAttributes().getName(),
                "Не работает поиск товаров");
    }
}
