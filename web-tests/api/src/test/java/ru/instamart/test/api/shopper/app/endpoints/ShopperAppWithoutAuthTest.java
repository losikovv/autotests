package ru.instamart.test.api.shopper.app.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import io.qameta.allure.TmsLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.shopper.app.*;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.Group.API_SHOPPER_PROD;
import static ru.instamart.api.Group.API_SHOPPER_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShopperAppWithoutAuthTest extends RestBase {
    String shipmentId = "000";
    String assemblyId = "000";
    String assemblyItemId = "000";
    Integer itemQty = 1;
    String notificationsId = "2280";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.clearSession(SessionType.SHOPPER_APP);
    }

    @Story("Начало сборки")
    @TmsLink("48")
    @Test(description = "Создаём сборку без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void postAssembly401() {
        response = AssembliesSHPRequest.POST(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @TmsLink("49")
    @Test(description = "Получаем сборку по номеру без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getAssembly401() {
        response = AssembliesSHPRequest.GET(assemblyId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("50")
    @Test(description = "Получаем все заказы для сборщика без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopperShipments401() {
        response = ShopperSHPRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("105")
    @Test(description = "Получаем все заказы для сборщика без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopperShipment401() {
        response = ShopperSHPRequest.Shipment.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @TmsLink("52")
    @Test(description = "Получаем все сборки сборщика без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopperAssemblies401() {
        response = ShopperSHPRequest.Assemblies.GET();
        checkStatusCode401(response);
    }

    @Story("Процесс сборки")
    @TmsLink("53")
    @Test(description = "Собираем товар(PATCH) без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void patchAssemblyItem401() {
        response = AssemblyItemsSHPRequest.PATCH(assemblyId, assemblyItemId, itemQty);
        checkStatusCode401(response);
    }

    @Story("Процесс сборки")
    @TmsLink("53")
    @Test(description = "Собираем товар(PUT) без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void putAssemblyItem401() {
        response = AssemblyItemsSHPRequest.PUT(assemblyId, assemblyItemId, itemQty);
        checkStatusCode401(response);
    }

    @Story("Хелпдеск")
    @TmsLink("54")
    @Test(description = "Получаем тикеты хелпдеска без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getHelpdeskTickets401() {
        response = HelpdeskSHPRequest.Tickets.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("55")
    @Test(description = "Получаем заказ по номеру без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShipment401() {
        response = ShipmentsSHPRequest.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @TmsLink("56")
    @Test(description = "Получаем предзамены для позиций в сборке без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getAssemblyItemPrereplacements401() {
        response = AssemblyItemsSHPRequest.Prereplacements.GET(assemblyItemId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("57")
    @Test(description = "Получаем инфу о стоках товаров в заказе без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShipmentStock401() {
        response = ShipmentsSHPRequest.Stocks.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Оплата")
    @TmsLink("58")
    @Test(description = "Оплачиваем заказ через LifePay без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void putAssemblyLifePay401() {
        response = AssembliesSHPRequest.LifePay.PUT(assemblyId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("59")
    @Test(description = "Получаем маркетинговые пробники для заказа без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShipmentMarketingSampleItems401() {
        response = ShipmentsSHPRequest.MarketingSampleItems.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сотруднике")
    @TmsLink("60")
    @Test(description = "Получаем инфу о сборщике без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopper401() {
        response = ShopperSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о маршрутах")
    @TmsLink("61")
    @Test(description = "Получаем маршруты без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getRoutes401() {
        response = RoutesSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сотруднике")
    @TmsLink("62")
    @Test(description = "Получаем смены сборщика без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopperOperationShifts401() {
        response = ShopperSHPRequest.OperationShifts.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("63")
    @Test(description = "Получаем все заказы для водителя без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getDriverShipments401() {
        response = DriverSHPRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @TmsLink("64")
    @Test(description = "Получаем причины отмен без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getCancelReasons401() {
        response = CancelReasonsSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @TmsLink("65")
    @Test(description = "Получаем причины уточнения без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getClarifyReasons401() {
        response = ClarifyReasonsSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @TmsLink("66")
    @Test(description = "Получаем причины возврата без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getReturnReasons401() {
        response = ReturnReasonsSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение марс токена (стоки метро)")
    @TmsLink("67")
    @Test(description = "Получаем марс токен (стоки метро) без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getMarsToken401() {
        response = MarsTokenSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @TmsLink("68")
    @Test(description = "Получаем все заказы для упаковщика без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getPackerShipments401() {
        response = PackerSHPRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @TmsLink("69")
    @Test(description = "Получаем все сборки упаковщика без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getPackerAssemblies401() {
        response = PackerSHPRequest.Assemblies.GET();
        checkStatusCode401(response);
    }

    @Story("Поиск")
    @TmsLink("70")
    @Test(description = "Поиск товаров без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getStoreOffers401() {
        response = StoresSHPRequest.Offers.GET(
                EnvironmentProperties.DEFAULT_SHOPPER_SID,
                "хлеб");
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @TmsLink("71")
    @Test(description = "Обновление авторизации без авторизации",
            groups = {API_SHOPPER_REGRESS //, API_SHOPPER_PROD - ожидает B2C-7772
            })
    public void postAuthRefresh401() {
        response = AuthSHPRequest.Refresh.POST();
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @TmsLink("72")
    @Test(description = "Авторизация по номеру телефона и неправильному коду из смс",
            groups = {API_SHOPPER_REGRESS //, API_SHOPPER_PROD - ожидает B2C-7772
            })
    public void postOtpsAuthorizations422WrongCode() {
        response = AuthSHPRequest.Code.POST("79588128783", "123");
        checkStatusCode422(response);
    }

    @Story("Авторизация")
    @TmsLink("73")
    @Test(description = "Авторизация по незарегистрированному номеру телефона и коду из смс",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void postOtpsAuthorizations422WrongPhone() {
        response = AuthSHPRequest.Code.POST("79991119911", CoreProperties.DEFAULT_SMS);
        checkStatusCode422(response);
    }

    @Story("Сборки/отгрузки")
    @TmsLink("73")
    @Test(description = "Shopper shipments active get",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void postOtpsAuthorizations4011one() {
        response = ShopperSHPRequest.Shipments.Active.GET();
        checkStatusCode401(response);
    }

    @Story("Интеграция shp -> stf")
    @TmsLink("4")
    @Test(description = "Тест на импорт заказов из stf с filed order number",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void orderImport401() {
        final Response response = OrderSHPRequest.Import.POST("failedNumber");
        checkStatusCode401(response);
    }

    @Story("Маршрут")
    @TmsLink("106")
    @Test(description = "Запрос назначенных маршрутов без авторизации",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void nextUncompletedRoute401() {
        final Response response = NextUncompletedRouteSHPRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Сборки/отгрузки")
    @TmsLink("108")
    @Test(description = "Список активных сборок/отгрузок магазина текущего партнёра для универсалов",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void shopperDriverActive401() {
        final Response response = ShopperDriverSHPRequest.Shipments.Active.GET();
        checkStatusCode401(response);
    }

    @Story("Notifications")
    @TmsLink("107")
    @Test(description = "Отметка о прочтении уведомления без авторизации PATCH",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopperNotificationsPatch401() {
        response = ShopperSHPRequest.Notifications.PATCH(notificationsId);
        checkStatusCode401(response);
    }

    @Story("Notifications")
    @TmsLink("107")
    @Test(description = "Отметка о прочтении уведомления без авторизации PUT",
            groups = {API_SHOPPER_REGRESS, API_SHOPPER_PROD})
    public void getShopperNotificationsPut401() {
        response = ShopperSHPRequest.Notifications.PUT(notificationsId);
        checkStatusCode401(response);
    }
}
