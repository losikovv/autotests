package ru.instamart.test.api.delivery_club.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.delivery_club.StoresDCRequest;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("Партнёры")
@Feature("Delivery Club")
public class StoresWithoutAuthDCTest extends RestBase {
    int sid = EnvironmentProperties.DEFAULT_SID;
    String orderNumber = "000";
    String slotId = "000";
    String productId = "000";
    String orderStatus = "canceled";
    String notificationType = "paid_adjustment";

    @BeforeMethod(alwaysRun = true)
    public void clearAuth() {
        SessionFactory.clearSession(SessionType.DELIVERY_CLUB);
    }

    @CaseId(591)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение доступных слотов без авторизации")
    public void getStoresSlotsAvailable401() {
        final Response response = StoresDCRequest.Slots.Available.GET(sid);
        checkStatusCode401(response);
    }

    @CaseId(592)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение всех слотов без авторизации")
    public void getStoresSlots401() {
        final Response response = StoresDCRequest.Slots.GET(sid);
        checkStatusCode401(response);
    }

    @CaseId(593)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение стоков без авторизации")
    public void getStoresStock401() {
        final Response response = StoresDCRequest.Stock.GET(sid);
        checkStatusCode401(response);
    }

    @CaseId(594)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Создание нотификации для заказа без авторизации")
    public void postStoresNotifications401() {
        final Response response = StoresDCRequest.Notifications.POST(sid, orderNumber, notificationType);
        checkStatusCode401(response);
    }

    @CaseId(595)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение зон доставки без авторизации")
    public void getStoresZones401() {
        final Response response = StoresDCRequest.Zones.GET(sid);
        checkStatusCode401(response);
    }

    @CaseId(596)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Создание заказа без авторизации")
    public void postStoresOrders401() {
        final Response response = StoresDCRequest.Orders.POST(sid, slotId, productId);
        checkStatusCode401(response);
    }

    @CaseId(597)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение категорий продуктов без авторизации")
    public void getStoresCatalogCategories401() {
        final Response response = StoresDCRequest.Catalog.Categories.GET(sid);
        checkStatusCode401(response);
    }

    @CaseId(598)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение продуктов без авторизации")
    public void getStoresCatalogProducts401() {
        final Response response = StoresDCRequest.Catalog.Products.GET(sid);
        checkStatusCode401(response);
    }

    @CaseId(599)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о заказе по номеру без авторизации")
    public void getStoresOrders401() {
        final Response response = StoresDCRequest.Orders.GET(sid, orderNumber);
        checkStatusCode401(response);
    }

    @CaseId(600)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Изменение статуса заказа без авторизации")
    public void putStoresOrdersStatus401() {
        final Response response = StoresDCRequest.Orders.Status.PUT(sid, orderNumber, orderStatus);
        checkStatusCode401(response);
    }
}
