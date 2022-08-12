package ru.instamart.test.api.delivery_club.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.delivery_club.AvailableSlotDC;
import ru.instamart.api.model.delivery_club.NotificationDC;
import ru.instamart.api.model.delivery_club.SlotDC;
import ru.instamart.api.model.delivery_club.StoreZoneDC;
import ru.instamart.api.request.delivery_club.StoresDCRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.delivery_club.*;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserManager;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Slf4j
@Epic("Партнёры")
@Feature("Delivery Club")
public class StoresWithAuthDCTest extends RestBase {
    int sid = EnvironmentProperties.DEFAULT_SID;
    String orderNumber;
    String slotId;
    String productId;
    String orderStatus = "canceled";
    String notificationType = "paid_adjustment";
    int wrongSid = 0;
    String wrongOrderNumber = "000";
    String wrongSlotId = "000";
    String wrongProductId = "000";
    String wrongOrderStatus = "wrongStatus";
    String wrongNotificationType = "wrongNotificationType";


    @BeforeClass(alwaysRun = true)
    public void auth() {
        SessionFactory.createSessionToken(SessionType.DELIVERY_CLUB, UserManager.getDeliveryClubUser());
    }

    @CaseId(563)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение доступных слотов")
    public void getStoresSlotsAvailable200() {
        final Response response = StoresDCRequest.Slots.Available.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, AvailableSlotDC[].class);
        List<SlotDC> slots = Arrays.asList(response.as(SlotDC[].class));
        checkFieldIsNotEmpty(slots, "слоты в магазине");
        slotId = slots.get(0).getId();
    }

    @CaseId(574)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение доступных слотов из несуществующего магазина")
    public void getStoresSlotsAvailable404() {
        final Response response = StoresDCRequest.Slots.Available.GET(wrongSid);
        checkStatusCode404(response);
    }

    @CaseId(564)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех слотов")
    public void getStoresSlots200() {
        final Response response = StoresDCRequest.Slots.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SlotsDCResponse[].class);
    }

    @CaseId(575)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение всех слотов из несуществующего магазина")
    public void getStoresSlots404() {
        final Response response = StoresDCRequest.Slots.GET(wrongSid);
        checkStatusCode404(response);
    }

    @CaseId(565)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение стоков")
    public void getStoresStock200() {
        final Response response = StoresDCRequest.Stock.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoresStockDCResponse.class);
    }

    @CaseId(576)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение стоков из несуществующего магазина")
    public void getStoresStock404() {
        final Response response = StoresDCRequest.Stock.GET(wrongSid);
        checkStatusCode404(response);
    }

    @CaseId(566)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Создание нотификации для заказа")
    public void postStoresNotifications200() {
        final Response response = StoresDCRequest.Notifications.POST(sid, orderNumber, notificationType);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, NotificationDC.class);
    }

    @CaseId(577)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Создание нотификации для заказа в несуществующем магазине")
    public void postStoresNotifications404WrongSid() {
        final Response response = StoresDCRequest.Notifications.POST(wrongSid, orderNumber, notificationType);
        checkStatusCode404(response);
    }

    @CaseId(578)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Создание нотификации для несуществующего заказа")
    public void postStoresNotifications404WrongOrderNumber() {
        final Response response = StoresDCRequest.Notifications.POST(sid, wrongOrderNumber, notificationType);
        checkStatusCode404(response);
    }

    @CaseId(590)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Создание несуществующего типа нотификации для заказа")
    public void postStoresNotifications400WrongNotificationType() {
        final Response response = StoresDCRequest.Notifications.POST(sid, orderNumber, wrongNotificationType);
        checkStatusCode400(response);
    }

    @CaseId(567)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение зон доставки")
    public void getStoresZones200() {
        final Response response = StoresDCRequest.Zones.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreZoneDC[].class);
    }

    @CaseId(579)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение зон доставки из несуществующего магазина")
    public void getStoresZones404() {
        final Response response = StoresDCRequest.Zones.GET(wrongSid);
        checkStatusCode404(response);
    }

    @CaseId(568)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = {"getStoresSlotsAvailable200", "getStoresCatalogProducts200"},
            description = "Создание заказа")
    public void postStoresOrders200() {
        SessionFactory.createSessionToken(SessionType.DELIVERY_CLUB, UserManager.getDeliveryClubUser());
        Response response = StoresDCRequest.Orders.POST(sid, slotId, productId);

        for (int i = 0; i < 3 && response.statusCode() == 400 && response.as(ErrorResponse.class).getMessage().equals("slot_not_available"); i++) {
            log.error(response.getStatusLine());
            slotId = dc.getAvailableSlot(sid).getId();
            response = StoresDCRequest.Orders.POST(sid, slotId, productId);
        }
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderDCResponse.class);
        orderNumber = response.as(OrderDCResponse.class).getId();
    }

    @CaseId(580)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = {"getStoresSlotsAvailable200", "getStoresCatalogProducts200"},
            description = "Создание заказа в несуществующем магазине")
    public void postStoresOrders404WrongSid() {
        final Response response = StoresDCRequest.Orders.POST(wrongSid, slotId, productId);
        checkStatusCode404(response);
    }

    @CaseId(581)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = {"getStoresCatalogProducts200"},
            description = "Создание заказа в несуществующий слот")
    public void postStoresOrders400WrongSlotId() {
        final Response response = StoresDCRequest.Orders.POST(sid, wrongSlotId, productId);
        checkStatusCode400(response);
    }

    @Skip // todo сейчас возвращает 500 - похоже на баг
    @Issue("STF-9241")
    @CaseId(582)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = {"getStoresSlotsAvailable200"},
            description = "Создание заказа с несуществующим товаром")
    public void postStoresOrders404WrongProductId() {
        final Response response = StoresDCRequest.Orders.POST(sid, slotId, wrongProductId);
        checkStatusCode404(response);
    }

    @CaseId(569)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение категорий продуктов")
    public void getStoresCatalogCategories200() {
        final Response response = StoresDCRequest.Catalog.Categories.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CatalogCategoriesDCResponse.class);
    }

    @CaseId(583)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение категорий продуктов из несуществующего магазина")
    public void getStoresCatalogCategories404() {
        final Response response = StoresDCRequest.Catalog.Categories.GET(wrongSid);
        checkStatusCode404(response);
    }

    @CaseId(570)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение продуктов")
    public void getStoresCatalogProducts200() {
        final Response response = StoresDCRequest.Catalog.Products.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ProductsDCResponse.class);
        productId = response.as(ProductsDCResponse.class).getData().getProducts().get(0).getId();
    }

    @CaseId(584)
    @Story("Получение информации из магазинов")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение продуктов из несуществующего магазина")
    public void getStoresCatalogProducts404() {
        final Response response = StoresDCRequest.Catalog.Products.GET(wrongSid);
        checkStatusCode404(response);
    }

    @CaseId(571)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Получение информации о заказе по номеру")
    public void getStoresOrders200() {
        final Response response = StoresDCRequest.Orders.GET(sid, orderNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderDCResponse.class);
    }

    @CaseId(585)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Получение информации о заказе по номеру из несуществующего магазина")
    public void getStoresOrders404WrongSid() {
        final Response response = StoresDCRequest.Orders.GET(wrongSid, orderNumber);
        checkStatusCode404(response);
    }

    @CaseId(586)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Получение информации о заказе по несуществующему номеру")
    public void getStoresOrders404WrongOrderNumber() {
        final Response response = StoresDCRequest.Orders.GET(sid, wrongOrderNumber);
        checkStatusCode404(response);
    }

    @CaseId(572)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Изменение статуса заказа")
    public void putStoresOrdersStatus200() {
        final Response response = StoresDCRequest.Orders.Status.PUT(sid, orderNumber, orderStatus);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderDCResponse.class);
    }

    @CaseId(587)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Изменение статуса заказа в несуществующем магазине")
    public void putStoresOrdersStatus404WrongSid() {
        final Response response = StoresDCRequest.Orders.Status.PUT(wrongSid, orderNumber, orderStatus);
        checkStatusCode404(response);
    }

    @CaseId(588)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Изменение статуса несуществующего заказа")
    public void putStoresOrdersStatus404WrongOrderNumber() {
        final Response response = StoresDCRequest.Orders.Status.PUT(sid, wrongOrderNumber, orderStatus);
        checkStatusCode404(response);
    }

    @CaseId(589)
    @Story("Заказ")
    @Test(groups = {"api-instamart-regress"},
            dependsOnMethods = "postStoresOrders200",
            description = "Изменение несуществующего статуса заказа")
    public void putStoresOrdersStatus400WrongOrderStatus() {
        final Response response = StoresDCRequest.Orders.Status.PUT(sid, orderNumber, wrongOrderStatus);
        checkStatusCode400(response);
    }
}
