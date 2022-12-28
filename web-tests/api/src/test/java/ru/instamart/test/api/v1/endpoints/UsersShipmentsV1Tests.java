package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.v2.StateV2;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.UsersV1Request;
import ru.instamart.api.response.v1.MultiretailerOrderV1Response;
import ru.instamart.jdbc.dao.stf.ShipmentDelaysDao;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkUserShipmentFromResponse;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;
import static ru.instamart.kraken.util.TimeUtil.getDbDate;
import static ru.instamart.kraken.util.TimeUtil.getDbDeliveryDateFrom;

@Epic("ApiV1")
@Feature("Заказы")
public class UsersShipmentsV1Tests extends RestBase {

    private MultiretailerOrderV1Response order;
    private UserData user;
    private String shipmentNumber;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, EnvironmentProperties.DEFAULT_SID);
        order = apiV1.getMultiRetailerOrder();
        shipmentNumber = order.getShipments().get(0).getNumber();
    }

    @Story("Заказы пользователя")
    @CaseId(1333)
    @Test(description = "Получение информации о заказе пользователя в статусе pending",
            groups = {"api-instamart-smoke", "api-v1"})
    public void getPendingUserShipment() {
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Заказы пользователя")
    @CaseId(1333)
    @Test(description = "Получение информации о заказе пользователя в статусе ready",
            groups = {"api-instamart-smoke", "api-v1"},
            dependsOnMethods = "getPendingUserShipment")
    public void getReadyUserShipment() {
        apiV2.setDefaultAttributesAndCompleteOrder();
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.READY.getValue(), null);
    }

    @Story("Заказы пользователя")
    @CaseId(1338)
    @Test(description = "Получение информации о заказе пользователя в статусе ready - заказ задерживается",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getReadyUserShipment")
    public void getReadyUserShipmentWithDelay()  {
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.now().minusHours(1));
        ShipmentDelaysDao.INSTANCE.updateDeadlineDate(getDbDate(date), order.getShipments().get(0).getId());
        //computeExpectedDates(); - Поле notification_sent_at может обновляться в БД до 10 минут
        //sendNotifications(); TODO: после выполнения таски - проверить после добавления крон-джобы на стейдж
        ShipmentDelaysDao.INSTANCE.updateNotificationDate(getDbDate(date), order.getShipments().get(0).getId());
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.READY.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseIDs(value = {@CaseId(1299), @CaseId(1339)})
    @Test(description = "Получение информации о заказе пользователя в статусе collecting",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getReadyUserShipmentWithDelay")
    public void getCollectingUserShipment() {
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), StateV2.COLLECTING.getValue());
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.COLLECTING.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseIDs(value = {@CaseId(1300), @CaseId(1339)})
    @Test(description = "Получение информации о заказе пользователя в статусе ready_to_ship",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getCollectingUserShipment")
    public void getReadyToShipUserShipment() {
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), StateV2.READY_TO_SHIP.getValue());
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.READY_TO_SHIP.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseIDs(value = {@CaseId(1301), @CaseId(1340)})
    @Test(description = "Получение информации о заказе пользователя в статусе shipping",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getReadyToShipUserShipment")
    public void getShippingUserShipment() {
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), StateV2.SHIPPING.getValue());
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.SHIPPING.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseId(1335)
    @Test(description = "Получение информации о заказе пользователя в статусе shipped",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getShippingUserShipment")
    public void getShippedUserShipment() {
        SpreeOrdersDao.INSTANCE.updateShipmentStateToShip(order.getNumber(), getDbDeliveryDateFrom(0L));
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.SHIPPED.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseId(1302)
    @Test(description = "Получение информации о заказе пользователя в статусе canceled",
            groups = {API_INSTAMART_REGRESS, "api-v1"},
            dependsOnMethods = "getShippedUserShipment")
    public void getCanceledUserShipment() {
        OrderV2 order = apiV2.order(user, EnvironmentProperties.DEFAULT_SID);
        SpreeOrdersDao.INSTANCE.updateShipmentState(order.getNumber(), StateV2.CANCELED.getValue());
        final Response response = UsersV1Request.GET(user.getId(), order.getShipments().get(0).getNumber());
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.CANCELED.getValue(), null);
    }

    @Story("Заказы пользователя")
    @CaseId(1334)
    @Test(description = "Получение информации о несуществующем заказе пользователя",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getNonExistentUserShipment() {
        final Response response = UsersV1Request.GET(user.getId(), "failedShipmentNumber");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
