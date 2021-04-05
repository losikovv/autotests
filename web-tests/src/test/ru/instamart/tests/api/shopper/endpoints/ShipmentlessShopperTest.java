package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.shopper.Reason;
import instamart.api.requests.shopper.*;
import instamart.api.responses.shopper.*;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Arrays;

import static instamart.api.checkpoints.ShopperApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShipmentlessShopperTest extends RestBase {
    private final String phone = "79588128783";
    private final String code = "111111";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        shopper.authorisation(UserManager.getDefaultShopper());
    }

    @Story("Получение информации о сотруднике")
    @CaseId(10)
    @Test(  description = "Получаем инфу о сборщике",
            groups = {"api-shopper-smoke","MRAutoCheck"})
    public void getShopper200() {
        response = ShopperRequest.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ShopperResponse.class)
                        .getData()
                        .getAttributes()
                        .getName(),
                "Не вернулась инфа о сборщике");
    }

    @Story("Получение информации о маршрутах")
    @CaseId(11)
    @Test(  description = "Получаем маршруты",
            groups = {"api-shopper-smoke"})
    public void getRoutes200() {
        response = RoutesRequest.GET();
        checkStatusCode200(response);
    }

    @Story("Получение информации о сотруднике")
    @CaseId(12)
    @Test(  description = "Получаем смены сборщика",
            groups = {"api-shopper-smoke"})
    public void getShopperOperationShifts200() {
        response = ShopperRequest.OperationShifts.GET();
        checkStatusCode200(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(14)
    @Test(  description = "Получаем все заказы для водителя",
            groups = {"api-shopper-smoke"})
    public void getDriverShipments200() {
        response = DriverRequest.Shipments.GET();
        checkStatusCode200(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(15)
    @Test(  description = "Получаем причины отмен",
            groups = {"api-shopper-smoke"})
    public void getCancelReasons200() {
        response = CancelReasonsRequest.GET();
        checkStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины отмен");
    }

    @Story("Получение информации о причинах")
    @CaseId(15)
    @Test(  description = "Получаем причины уточнения",
            groups = {"api-shopper-smoke"})
    public void getClarifyReasons200() {
        response = ClarifyReasonsRequest.GET();
        checkStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины уточнения");
    }

    @Story("Получение информации о причинах")
    @CaseId(15)
    @Test(  description = "Получаем причины возврата",
            groups = {"api-shopper-smoke"})
    public void getReturnReasons200() {
        response = ReturnReasonsRequest.GET();
        checkStatusCode200(response);
        assertNotNull(Arrays.asList(response.as(Reason[].class)).get(0).getName(),
                "Не вернулись причины возврата");
    }

    @Story("Получение марс токена (стоки метро)")
    @CaseId(16)
    @Test(  description = "Получаем марс токен (стоки метро)",
            groups = {"api-shopper-smoke"})
    public void getMarsToken200() {
        response = MarsTokenRequest.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(MarsTokenResponse.class).getAccess_token(),
                "Не вернулся марс токен");
    }

    @Story("Получение информации о заказах")
    @CaseId(18)
    @Test(  description = "Получаем все заказы для упаковщика",
            groups = {"api-shopper-smoke"})
    public void getPackerShipments200() {
        response = PackerRequest.Shipments.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentsResponse.class).getData(),
                "Не вернулись заказы для упаковщика");
    }

    @Story("Получение информации о сборках")
    @CaseId(19)
    @Test(  description = "Получаем все сборки упаковщика",
            groups = {"api-shopper-smoke"})
    public void getPackerAssemblies200() {
        response = PackerRequest.Assemblies.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(AssembliesResponse.class).getData(),
                "Не вернулись сборки упаковщика");
    }

    @Story("Получение информации о приложении")
    @CaseId(20)
    @Test(  description = "Получаем инфу о текущей версии приложения",
            groups = {"api-shopper-smoke"})
    public void getCurrentAppVersion200() {
        response = CurrentAppVersionRequest.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(AppVersionResponse.class).getData().getAttributes().getMajor(),
                "Не вернулась инфа о текущей версии приложения");
    }

    @Story("Поиск")
    @CaseId(21)
    @Test(  description = "Поиск товаров",
            groups = {"api-shopper-smoke"})
    public void getStoreOffers200() {
        response = StoresRequest.Offers.GET(
                EnvironmentData.INSTANCE.getDefaultShopperSid(),
                "хлеб");
        checkStatusCode200(response);
        assertNotNull(response.as(OffersResponse.class).getData().get(0).getAttributes().getName(),
                "Не работает поиск товаров");
    }

    @Story("Авторизация")
    @CaseId(43)
    @Test(  description = "Обновление авторизации",
            groups = {"api-shopper-smoke"})
    public void postAuthRefresh200() {
        response = AuthRequest.Refresh.POST();
        checkStatusCode200(response);
        final SessionsResponse sessionsResponse = response.as(SessionsResponse.class);
        assertNotNull(sessionsResponse.getData().getAttributes().getAccessToken(),
                "Не вернулся access_token");
        assertNotNull(sessionsResponse.getData().getAttributes().getRefreshToken(),
                "Не вернулся refresh_token");
        SessionFactory.getSession(SessionType.SHOPPER).setToken(sessionsResponse.getData().getAttributes().getAccessToken());
        SessionFactory.getSession(SessionType.SHOPPER).setRefreshToken(sessionsResponse.getData().getAttributes().getRefreshToken());
    }

    @Story("Авторизация")
    @CaseId(46)
    @Test( description = "Отправка запроса для получения смс кодом для авторизации",
           groups = {"api-shopper-smoke"})
    public void postOtpsTokens200() {
        response = OtpsRequest.Tokens.POST(phone);
        checkStatusCode200(response);
    }

    @Story("Авторизация")
    @CaseId(47)
    @Test( description = "Авторизация по номеру телефона и коду из смс",
            groups = {"api-shopper-smoke"})
    public void postOtpsAuthorizations200() {
        response = OtpsRequest.Authorizations.POST(phone, code);

        checkStatusCode200(response);
        String accessToken = response.as(SessionsResponse.class).getData().getAttributes().getAccessToken();
        assertNotNull(accessToken, "Не вернулся access_token");
        assertNotEquals(accessToken, "", "Вернулся пустой access_token");

        String refreshToken = response.as(SessionsResponse.class).getData().getAttributes().getRefreshToken();
        assertNotNull(refreshToken, "Не вернулся refresh_token");
        assertNotEquals(refreshToken, "", "Вернулся пустой refresh_token");
        SessionFactory.getSession(SessionType.SHOPPER).setToken(accessToken);
        SessionFactory.getSession(SessionType.SHOPPER).setRefreshToken(refreshToken);
    }
}
