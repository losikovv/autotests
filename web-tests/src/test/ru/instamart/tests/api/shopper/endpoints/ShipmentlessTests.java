package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.shopper.Reason;
import instamart.api.requests.shopper.*;
import instamart.api.responses.shopper.*;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static instamart.api.checkpoints.ShopperApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class ShipmentlessTests extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        shopper.authorisation(UserManager.getDefaultShopper());
    }

    @CaseId(10)
    @Test(  description = "Получаем инфу о сборщике",
            groups = {"api-shopper-smoke","MRAutoCheck"})
    public void getShopper() {
        response = ShopperRequest.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(ShopperResponse.class)
                        .getData()
                        .getAttributes()
                        .getName(),
                "Не вернулась инфа о сборщике");
    }

    @CaseId(11)
    @Test(  description = "Получаем маршруты",
            groups = {"api-shopper-smoke"})
    public void getRoutes() {
        response = RoutesRequest.GET();
        assertStatusCode200(response);
    }

    @CaseId(12)
    @Test(  description = "Получаем смены сборщика",
            groups = {"api-shopper-smoke"})
    public void getShopperOperationShifts() {
        response = ShopperRequest.OperationShifts.GET();
        assertStatusCode200(response);
    }

    @CaseId(14)
    @Test(  description = "Получаем заказы водителя",
            groups = {"api-shopper-smoke"})
    public void getDriverShipments() {
        response = DriverRequest.Shipments.GET();
        assertStatusCode200(response);
    }

    @CaseId(15)
    @Test(  description = "Получаем причины отмен",
            groups = {"api-shopper-smoke"})
    public void getCancelReasons() {
        response = CancelReasonsRequest.GET();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины отмен");
    }

    @CaseId(15)
    @Test(  description = "Получаем причины уточнения",
            groups = {"api-shopper-smoke"})
    public void getClarifyReasons() {
        response = ClarifyReasonsRequest.GET();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины уточнения");
    }

    @CaseId(15)
    @Test(  description = "Получаем причины возврата",
            groups = {"api-shopper-smoke"})
    public void getReturnReasons() {
        response = ReturnReasonsRequest.GET();
        assertStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины возврата");
    }

    @CaseId(16)
    @Test(  description = "Получаем марс токен (стоки метро)",
            groups = {"api-shopper-smoke"})
    public void getMarsToken() {
        response = MarsTokenRequest.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(MarsTokenResponse.class).getAccess_token(),
                "Не вернулся марс токен");
    }

    @CaseId(18)
    @Test(  description = "Получаем заказы для упаковщика",
            groups = {"api-shopper-smoke"})
    public void getPackerShipments() {
        response = PackerRequest.Shipments.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы для упаковщика");
    }

    @CaseId(19)
    @Test(  description = "Получаем сборки упаковщика",
            groups = {"api-shopper-smoke"})
    public void getPackerAssemblies() {
        response = PackerRequest.Assemblies.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class).getData(),
                "Не вернулись сборки упаковщика");
    }

    @CaseId(20)
    @Test(  description = "Получаем инфу о текущей версии приложения",
            groups = {"api-shopper-smoke"})
    public void getCurrentAppVersion() {
        response = CurrentAppVersionRequest.GET();
        assertStatusCode200(response);
        assertNotNull(response.as(AppVersionResponse.class).getData().getAttributes().getMajor(),
                "Не вернулась инфа о текущей версии приложения");
    }

    @CaseId(21)
    @Test(  description = "Поиск товаров",
            groups = {"api-shopper-smoke"})
    public void getStoreOffers() {
        response = StoresRequest.Offers.GET(
                EnvironmentData.INSTANCE.getDefaultShopperSid(),
                "хлеб");
        assertStatusCode200(response);
        assertNotNull(response.as(OffersResponse.class).getData().get(0).getAttributes().getName(),
                "Не работает поиск товаров");
    }

    @CaseId(43)
    @Test(  description = "Обновление авторизации",
            groups = {"api-shopper-smoke"})
    public void postAuthRefresh() {
        response = AuthRequest.Refresh.POST();
        assertStatusCode200(response);
        final SessionsResponse sessionsResponse = response.as(SessionsResponse.class);
        assertNotNull(sessionsResponse.getData().getAttributes().getAccessToken(),
                "Не вернулся access_token");
        assertNotNull(sessionsResponse.getData().getAttributes().getRefreshToken(),
                "Не вернулся refresh_token");
        SessionFactory.getSession(SessionType.SHOPPER).setToken(sessionsResponse.getData().getAttributes().getAccessToken());
        SessionFactory.getSession(SessionType.SHOPPER).setRefreshToken(sessionsResponse.getData().getAttributes().getRefreshToken());
    }
}
