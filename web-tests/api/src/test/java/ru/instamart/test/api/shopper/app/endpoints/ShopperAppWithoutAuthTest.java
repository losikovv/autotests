package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShopperAppWithoutAuthTest extends RestBase {
    String shipmentId = "000";
    String assemblyId = "000";
    String assemblyItemId = "000";
    Integer itemQty = 1;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.clearSession(SessionType.SHOPPER_APP);
    }

    @Story("Начало сборки")
    @CaseId(48)
    @Test(  description = "Создаём сборку без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postAssembly401() {
        response = AssembliesSHPRequest.POST(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(49)
    @Test(  description = "Получаем сборку по номеру без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getAssembly401() {
        response = AssembliesSHPRequest.GET(assemblyId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(50)
    @Test(  description = "Получаем все заказы для сборщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperShipments401() {
        response = ShopperSHPRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(52)
    @Test(  description = "Получаем все сборки сборщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperAssemblies401 () {
        response = ShopperSHPRequest.Assemblies.GET();
        checkStatusCode401(response);
    }

    @Story("Процесс сборки")
    @CaseId(53)
    @Test(  description = "Собираем товар без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void patchAssemblyItem401() {
        response = AssemblyItemsSHPRequest.PATCH(assemblyId, assemblyItemId, itemQty);
        checkStatusCode401(response);
    }

    @Story("Хелпдеск")
    @CaseId(54)
    @Test(  description = "Получаем тикеты хелпдеска без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getHelpdeskTickets401() {
        response = HelpdeskSHPRequest.Tickets.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(55)
    @Test(  description = "Получаем заказ по номеру без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShipment401() {
        response = ShipmentsSHPRequest.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(56)
    @Test(  description = "Получаем предзамены для позиций в сборке без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getAssemblyItemPrereplacements401() {
        response = AssemblyItemsSHPRequest.Prereplacements.GET(assemblyItemId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(57)
    @Test(  description = "Получаем инфу о стоках товаров в заказе без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShipmentStock401() {
        response = ShipmentsSHPRequest.Stocks.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Оплата")
    @CaseId(58)
    @Test(  description = "Оплачиваем заказ через LifePay без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void putAssemblyLifePay401() {
        response = AssembliesSHPRequest.LifePay.PUT(assemblyId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(59)
    @Test(  description = "Получаем маркетинговые пробники для заказа без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShipmentMarketingSampleItems401() {
        response = ShipmentsSHPRequest.MarketingSampleItems.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сотруднике")
    @CaseId(60)
    @Test(  description = "Получаем инфу о сборщике без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopper401() {
        response = ShopperSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о маршрутах")
    @CaseId(61)
    @Test(  description = "Получаем маршруты без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getRoutes401() {
        response = RoutesSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сотруднике")
    @CaseId(62)
    @Test(  description = "Получаем смены сборщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperOperationShifts401() {
        response = ShopperSHPRequest.OperationShifts.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(63)
    @Test(  description = "Получаем все заказы для водителя без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getDriverShipments401() {
        response = DriverSHPRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(64)
    @Test(  description = "Получаем причины отмен без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getCancelReasons401() {
        response = CancelReasonsSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(65)
    @Test(  description = "Получаем причины уточнения без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getClarifyReasons401() {
        response = ClarifyReasonsSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(66)
    @Test(  description = "Получаем причины возврата без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getReturnReasons401() {
        response = ReturnReasonsSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение марс токена (стоки метро)")
    @CaseId(67)
    @Test(  description = "Получаем марс токен (стоки метро) без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getMarsToken401() {
        response = MarsTokenSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(68)
    @Test(  description = "Получаем все заказы для упаковщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getPackerShipments401() {
        response = PackerSHPRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(69)
    @Test(  description = "Получаем все сборки упаковщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getPackerAssemblies401() {
        response = PackerSHPRequest.Assemblies.GET();
        checkStatusCode401(response);
    }

    @Story("Поиск")
    @CaseId(70)
    @Test(  description = "Поиск товаров без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getStoreOffers401() {
        response = StoresSHPRequest.Offers.GET(
                EnvironmentProperties.DEFAULT_SHOPPER_SID,
                "хлеб");
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @CaseId(71)
    @Test(  description = "Обновление авторизации без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postAuthRefresh401() {
        response = AuthSHPRequest.Refresh.POST();
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @CaseId(72)
    @Test( description = "Авторизация по номеру телефона и неправильному коду из смс",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postOtpsAuthorizations401WrongCode() {
        response = OtpsSHPRequest.Authorizations.POST("79588128783","123");
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @CaseId(73)
    @Test( description = "Авторизация по незарегистрированному номеру телефона и коду из смс",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postOtpsAuthorizations401WrongPhone() {
        response = OtpsSHPRequest.Authorizations.POST("79991119911", CoreProperties.DEFAULT_SMS);
        checkStatusCode401(response);
    }
}
