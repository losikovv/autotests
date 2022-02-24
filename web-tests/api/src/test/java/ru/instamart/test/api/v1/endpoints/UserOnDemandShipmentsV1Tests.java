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
import ru.instamart.jdbc.dao.ShipmentDelaysDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkUserShipmentFromResponse;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.changeToCollecting;
import static ru.instamart.api.helper.K8sHelper.changeToShipping;
import static ru.instamart.kraken.util.TimeUtil.getDbDate;

@Epic("ApiV1")
@Feature("Заказы")
public class UserOnDemandShipmentsV1Tests extends RestBase {

    private OrderV2 order;
    private UserData user;
    private String shipmentNumber;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        user = UserManager.getQaUser();
        apiV1.authByPhone(user);
        apiV2.authByQA(user);
        order = apiV2.order(user, EnvironmentProperties.DEFAULT_ON_DEMAND_SID);
        shipmentNumber = order.getShipments().get(0).getNumber();
    }

    @Story("Заказы пользователя")
    @CaseId(1303)
    @Test(description = "Получение информации о заказе пользователя с быстрой доставкой в статусе ready - заказ задерживается",
            groups = {"api-instamart-regress"})
    public void getReadyUserShipmentWithDelay()  {
        LocalDateTime date = LocalDateTime.of(LocalDate.now(), LocalTime.now().minusMinutes(30));
        ShipmentDelaysDao.INSTANCE.updateNotificationDate(getDbDate(date), (long) order.getShipments().get(0).getId());
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.READY.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseId(1336)
    @Test(description = "Получение информации о заказе пользователя с быстрой доставкой в статусе collecting - заказ задерживается",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getReadyUserShipmentWithDelay")
    public void getCollectingUserShipmentWithDelay()  {
        changeToCollecting(shipmentNumber);
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.COLLECTING.getValue(), "Задерживаемся, но очень торопимся");
    }

    @Story("Заказы пользователя")
    @CaseId(1337)
    @Test(description = "Получение информации о заказе пользователя с быстрой доставкой в статусе shipping - заказ задерживается",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getCollectingUserShipmentWithDelay")
    public void getShippingUserShipmentWithDelay()  {
        changeToShipping(shipmentNumber);
        final Response response = UsersV1Request.GET(user.getId(), shipmentNumber);
        checkStatusCode200(response);
        checkUserShipmentFromResponse(response, order, user, StateV2.SHIPPING.getValue(), "Задерживаемся, но очень торопимся");
    }
}
