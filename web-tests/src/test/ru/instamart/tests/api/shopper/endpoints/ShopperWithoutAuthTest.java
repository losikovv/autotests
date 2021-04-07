package ru.instamart.tests.api.shopper.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.shopper.*;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.ShopperApiCheckpoints.checkStatusCode401;

@Epic("Shopper Mobile API")
@Feature("Endpoints")
public class ShopperWithoutAuthTest extends RestBase {
    String shipmentId = "000";
    String assemblyId = "000";
    String assemblyItemId = "000";
    Integer itemQty = 1;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
    }

    @Story("Начало сборки")
    @CaseId(48)
    @Test(  description = "Создаём сборку без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postAssembly401() {
        response = AssembliesRequest.POST(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(49)
    @Test(  description = "Получаем сборку по номеру без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getAssembly401() {
        response = AssembliesRequest.GET(assemblyId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(50)
    @Test(  description = "Получаем все заказы для сборщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperShipments401() {
        response = ShopperRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(52)
    @Test(  description = "Получаем все сборки сборщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperAssemblies401 () {
        response = ShopperRequest.Assemblies.GET();
        checkStatusCode401(response);
    }

    @Story("Процесс сборки")
    @CaseId(53)
    @Test(  description = "Собираем товар без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void patchAssemblyItem401() {
        response = AssemblyItemsRequest.PATCH(assemblyId, assemblyItemId, itemQty);
        checkStatusCode401(response);
    }

    @Story("Хелпдеск")
    @CaseId(54)
    @Test(  description = "Получаем тикеты хелпдеска без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getHelpdeskTickets401() {
        response = HelpdeskRequest.Tickets.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(55)
    @Test(  description = "Получаем заказ по номеру без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShipment401() {
        response = ShipmentsRequest.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(56)
    @Test(  description = "Получаем предзамены для позиций в сборке без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getAssemblyItemPrereplacements401() {
        response = AssemblyItemsRequest.Prereplacements.GET(assemblyItemId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(57)
    @Test(  description = "Получаем инфу о стоках товаров в заказе без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShipmentStock401() {
        response = ShipmentsRequest.Stocks.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Оплата")
    @CaseId(58)
    @Test(  description = "Оплачиваем заказ через LifePay без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void putAssemblyLifePay401() {
        response = AssembliesRequest.LifePay.PUT(assemblyId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(59)
    @Test(  description = "Получаем маркетинговые пробники для заказа без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShipmentMarketingSampleItems401() {
        response = ShipmentsRequest.MarketingSampleItems.GET(shipmentId);
        checkStatusCode401(response);
    }

    @Story("Получение информации о сотруднике")
    @CaseId(60)
    @Test(  description = "Получаем инфу о сборщике без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopper401() {
        response = ShopperRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о маршрутах")
    @CaseId(61)
    @Test(  description = "Получаем маршруты без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getRoutes401() {
        response = RoutesRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сотруднике")
    @CaseId(62)
    @Test(  description = "Получаем смены сборщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getShopperOperationShifts401() {
        response = ShopperRequest.OperationShifts.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(63)
    @Test(  description = "Получаем все заказы для водителя без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getDriverShipments401() {
        response = DriverRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(64)
    @Test(  description = "Получаем причины отмен без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getCancelReasons401() {
        response = CancelReasonsRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(65)
    @Test(  description = "Получаем причины уточнения без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getClarifyReasons401() {
        response = ClarifyReasonsRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о причинах")
    @CaseId(66)
    @Test(  description = "Получаем причины возврата без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getReturnReasons401() {
        response = ReturnReasonsRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение марс токена (стоки метро)")
    @CaseId(67)
    @Test(  description = "Получаем марс токен (стоки метро) без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getMarsToken401() {
        response = MarsTokenRequest.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о заказах")
    @CaseId(68)
    @Test(  description = "Получаем все заказы для упаковщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getPackerShipments401() {
        response = PackerRequest.Shipments.GET();
        checkStatusCode401(response);
    }

    @Story("Получение информации о сборках")
    @CaseId(69)
    @Test(  description = "Получаем все сборки упаковщика без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getPackerAssemblies401() {
        response = PackerRequest.Assemblies.GET();
        checkStatusCode401(response);
    }

    @Story("Поиск")
    @CaseId(70)
    @Test(  description = "Поиск товаров без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void getStoreOffers401() {
        response = StoresRequest.Offers.GET(
                EnvironmentData.INSTANCE.getDefaultShopperSid(),
                "хлеб");
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @CaseId(71)
    @Test(  description = "Обновление авторизации без авторизации",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postAuthRefresh401() {
        response = AuthRequest.Refresh.POST();
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @CaseId(72)
    @Test( description = "Авторизация по номеру телефона и неправильному коду из смс",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postOtpsAuthorizations401WrongCode() {
        response = OtpsRequest.Authorizations.POST("79588128783","123");
        checkStatusCode401(response);
    }

    @Story("Авторизация")
    @CaseId(73)
    @Test( description = "Авторизация по незарегистрированному номеру телефона и коду из смс",
            groups = {"api-shopper-regress", "api-shopper-prod"})
    public void postOtpsAuthorizations401WrongPhone() {
        response = OtpsRequest.Authorizations.POST("79991119911","111111");
        checkStatusCode401(response);
    }
}
