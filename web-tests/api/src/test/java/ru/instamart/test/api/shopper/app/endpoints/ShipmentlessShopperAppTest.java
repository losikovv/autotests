package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.shopper.app.ReasonSHP;
import ru.instamart.api.model.shopper.app.SessionSHP;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.api.response.shopper.app.*;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShipmentlessShopperAppTest extends RestBase {
    private final String phone = "79588128783";

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        shopperApp.authorisation(UserManager.getDefaultShopper());
    }


    @Story("Получение информации о сотруднике")
    @CaseId(10)
    @Test(description = "Получаем инфу о сборщике",
            groups = {"api-shopper-smoke", "MRAutoCheck", "api-shopper-prod"})
    public void getShopper200() {
        response = ShopperSHPRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShopperSHPResponse.class);
    }

    @Story("Получение информации о маршрутах")
    @CaseId(11)
    @Test(description = "Получаем маршруты",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getRoutes200() {
        response = RoutesSHPRequest.GET();
        checkStatusCode200(response);
    }

    @Story("Получение информации о сотруднике")
    @CaseId(12)
    @Test(description = "Получаем смены сборщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getShopperOperationShifts200() {
        response = ShopperSHPRequest.OperationShifts.GET();
        checkStatusCode200(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(14)
    @Test(description = "Получаем все заказы для водителя",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getDriverShipments200() {
        response = DriverSHPRequest.Shipments.GET();
        checkStatusCode200(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(15)
    @Test(description = "Получаем причины отмен",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getCancelReasons200() {
        response = CancelReasonsSHPRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ReasonSHP[].class);
    }

    @Story("Получение информации о причинах")
    @CaseId(15)
    @Test(description = "Получаем причины уточнения",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getClarifyReasons200() {
        response = ClarifyReasonsSHPRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ReasonSHP[].class);
    }

    @Story("Получение информации о причинах")
    @CaseId(15)
    @Test(description = "Получаем причины возврата",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getReturnReasons200() {
        response = ReturnReasonsSHPRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ReasonSHP[].class);
    }

    @Story("Получение марс токена (стоки метро)")
    @CaseId(16)
    @Test(description = "Получаем марс токен (стоки метро)",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getMarsToken200() {
        response = MarsTokenSHPRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, MarsTokenSHPResponse.class);
    }

    @Story("Получение информации о заказах")
    @CaseId(18)
    @Test(description = "Получаем все заказы для упаковщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getPackerShipments200() {
        response = PackerSHPRequest.Shipments.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ShipmentsSHPResponse.class).getData(),
                "Не вернулись заказы для упаковщика");

    }

    @Story("Получение информации о сборках")
    @CaseId(19)
    @Test(description = "Получаем все сборки упаковщика",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getPackerAssemblies200() {
        response = PackerSHPRequest.Assemblies.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AssembliesSHPResponse.class);
    }

    @Story("Получение информации о приложении")
    @CaseId(20)
    @Test(description = "Получаем инфу о текущей версии приложения",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getCurrentAppVersion200() {
        response = CurrentAppVersionSHPRequest.GET();
        checkStatusCode200(response);
        AppVersionSHPResponse.Data.Attributes attributes = response.as(AppVersionSHPResponse.class).getData().getAttributes();
        checkFieldIsNotEmpty(attributes.getMajor(), "информация о текущей версии приложения");
        checkFieldIsNotEmpty(attributes.getDownloadUrl(), "ссылка на последнюю сборку");
    }

    @Story("Поиск")
    @CaseId(21)
    @Test(description = "Поиск товаров",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void getStoreOffers200() {
        response = StoresSHPRequest.Offers.GET(
                EnvironmentProperties.DEFAULT_SHOPPER_SID,
                "хлеб");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersSHPResponse.class);
    }

    @Story("Авторизация")
    @CaseId(43)
    @Test(description = "Обновление авторизации",
            groups = {"api-shopper-smoke"})
    public void postAuthRefresh200() {
        response = AuthSHPRequest.Refresh.POST();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SessionsSHPResponse.class);
        SessionSHP.Data.Attributes attributes = response.as(SessionsSHPResponse.class).getData().getAttributes();
        SessionFactory.updateToken(SessionType.SHOPPER_APP, attributes.getAccessToken(), attributes.getRefreshToken());
    }

    @Story("Авторизация")
    @CaseId(46)
    @Test(description = "Отправка запроса для получения смс кодом для авторизации",
            groups = {"api-shopper-smoke", "api-shopper-prod"})
    public void postOtpsTokens200() {
        response = AuthSHPRequest.Login.POST(phone);
        checkStatusCode200(response);
    }

    @Story("Авторизация")
    @CaseId(47)
    @Test(description = "Авторизация по номеру телефона и коду из смс",
            groups = {"api-shopper-smoke"},
            dependsOnMethods = "postOtpsTokens200")
    public void postOtpsAuthorizations200() {
        response = AuthSHPRequest.Code.POST(phone, CoreProperties.DEFAULT_SMS);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, SessionsSHPResponse.class);
        SessionSHP.Data.Attributes attributes = response.as(SessionsSHPResponse.class).getData().getAttributes();
        SessionFactory.updateToken(SessionType.SHOPPER_APP, attributes.getAccessToken(), attributes.getRefreshToken());
    }

    @Story("Маршрут")
    @CaseId(106)
    @Test(description = "Запрос назначенных маршрутов без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void nextUncompletedRoute404() {
        final Response response = NextUncompletedRouteSHPRequest.GET();
        checkStatusCode404(response);
        assertEquals(response.as(ErrorSHPResponse.class).getErrors().get(0).getDetail(), "Маршрут не найден", "Неправильная ошибка");
    }
}
