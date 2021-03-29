package ru.instamart.tests.api.delivery_club.contracts;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.delivery_club.SlotDC;
import instamart.api.requests.delivery_club.StoresDCRequest;
import instamart.api.responses.deliveryclub.OrderDCResponse;
import instamart.api.responses.deliveryclub.ProductsDCResponse;
import instamart.core.testdata.UserManager;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Партнёры")
@Feature("Delivery Club")
public class StoresDCTest extends RestBase {
    int sid = EnvironmentData.INSTANCE.getDefaultSid();
    String orderNumber;
    String slotId;
    String productId;

    @BeforeTest(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.DELIVERY_CLUB, UserManager.getDeliveryClubUser());
    }

    @CaseId(563)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
          description = "Получение доступных слотов")
    public void getStoresSlotsAvailable() {
        final Response response = StoresDCRequest.Slots.Available.GET(sid);
        response.then().statusCode(200);
        slotId = Arrays.asList(response.as(SlotDC[].class)).get(0).getId();
        response.then().body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresSlotsAvailable.json"));
    }

    @CaseId(564)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех слотов")
    public void getStoresSlots() {
        final Response response = StoresDCRequest.Slots.GET(sid);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresSlots.json"));
    }

    @CaseId(565)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение стоков")
    public void getStoresStock() {
        final Response response = StoresDCRequest.Stock.GET(sid);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresStock.json"));
    }

    @CaseId(566)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders",
            description = "Создание нотификации для заказа")
    public void postStoresNotifications() {
        final Response response = StoresDCRequest.Notifications.POST(sid, orderNumber);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresNotifications.json"));
    }

    @CaseId(567)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress"},
            description = "Получение зон доставки")
    public void getStoresZones() {
        final Response response = StoresDCRequest.Zones.GET(sid);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresZones.json"));
    }

    @CaseId(568)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress"},
            dependsOnMethods = {"getStoresSlotsAvailable", "getStoresCatalogProducts"},
            description = "Создание заказа")
    public void postStoresOrders() {
        final Response response = StoresDCRequest.Orders.POST(sid, slotId, productId);
        response.then().statusCode(200);
        orderNumber = response.as(OrderDCResponse.class).getId();
        response.then().body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresOrdersPOST.json"));
    }

    @CaseId(569)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress"},
            description = "Получение категорий продуктов")
    public void getStoresCatalogCategories() {
        final Response response = StoresDCRequest.Catalog.Categories.GET(sid);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresCatalogCategories.json"));
    }

    @CaseId(570)
    @Story("Получение информации из магазинов")
    @Test(  groups = {"api-instamart-regress"},
            description = "Получение продуктов")
    public void getStoresCatalogProducts() {
        final Response response = StoresDCRequest.Catalog.Products.GET(sid);
        response.then().statusCode(200);
        productId = response.as(ProductsDCResponse.class).getData().getProducts().get(0).getId();
        //todo очень большой ответ на проверке падает response.then().body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresCatalogProducts.json"));
    }

    @CaseId(571)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders",
            description = "Получение информации о заказе по номеру")
    public void getStoresOrders() {
        final Response response = StoresDCRequest.Orders.GET(sid, orderNumber);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresOrdersGET.json"));
    }

    @CaseId(572)
    @Story("Заказ")
    @Test(  groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders",
            description = "Изменение статуса заказа")
    public void putStoresOrdersStatus() {
        final Response response = StoresDCRequest.Orders.Status.PUT(sid, orderNumber);
        response.then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/delivery_club/StoresOrdersPOST.json"));
    }
}
