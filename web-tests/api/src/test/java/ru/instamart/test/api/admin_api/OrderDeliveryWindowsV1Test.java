package ru.instamart.test.api.admin_api;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.OrderDeliveryWindowsAdminV1Request;
import ru.instamart.api.response.v1.admin.OrderDeliveryWindowsV1Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Окна доставки для заказа")
public final class OrderDeliveryWindowsV1Test extends RestBase {

    private UserData user;
    private OrderV2 order;

    @BeforeClass(alwaysRun = true)
    public void before() {
        this.user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, EnvironmentProperties.DEFAULT_SID);
        order = apiV2.setDefaultAttributesAndCompleteOrder();
    }

    @AfterClass(alwaysRun = true)
    public void cancelAllOrders() {
        apiV2.cancelOrder(order.getNumber());
    }

    @CaseId(2846)
    @Skip(onServer = Server.STAGING)
    @Test(description = "Получение списка окон доставок заказа",
            groups = {"api-instamart-regress"})
    public void getOrderDeliveryWindows() {
        admin.authApi();

        Response response = OrderDeliveryWindowsAdminV1Request.GET(order.getNumber());
        checkStatusCode200(response);
        Allure.step("Проверка ответа", () -> {
            checkResponseJsonSchema(response, OrderDeliveryWindowsV1Response.class);
            Assert.assertEquals(
                    response.as(OrderDeliveryWindowsV1Response.class).getDeliveryWindows().getShipments().get(0).getOrderNumber(),
                    order.getNumber(),
                    "Номер заказа в ответе отличается от ожидаемого");
        });
    }

    @CaseId(2847)
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка получения списка окон доставок заказа неавторизованным пользователем",
            groups = {"api-instamart-regress"})
    public void getOrderDeliveryWindowsUnauthenticated() {
        SessionFactory.clearSession(SessionType.API_V1);

        Response response = OrderDeliveryWindowsAdminV1Request.GET(order.getNumber());
        checkStatusCode401(response);
    }

    @CaseId(2848)
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка получения списка окон доставок заказа пользователем без прав администратора",
            groups = {"api-instamart-regress"})
    public void getOrderDeliveryWindowsUnauthorized() {
        apiV1.authByPhone(UserManager.getQaUser());

        Response response = OrderDeliveryWindowsAdminV1Request.GET(order.getNumber());
        checkStatusCode403(response);
    }

    @CaseId(2849)
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка получения списка окон доставок несуществующего заказа",
            groups = {"api-instamart-regress"})
    public void getOrderDeliveryWindowsNotFound() {
        admin.authApi();

        Response response = OrderDeliveryWindowsAdminV1Request.GET(Generate.literalString(8));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
