package ru.instamart.test.api.admin_api;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v1.admin.OrderCancellationsAdminV1Request;
import ru.instamart.api.response.v1.admin.OrderCancellationV1Response;
import ru.instamart.api.response.v1.admin.OrderCancellationsV1Response;
import ru.instamart.jdbc.dao.stf.SpreeOrdersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Отмены заказа")
public final class OrderCancellationsV1Test extends RestBase {

    private UserData user;
    private OrderV2 order;
    private OrderV2 orderToCancel;
    private OrderV2 cancelledOrder;

    @BeforeClass(alwaysRun = true)
    public void prepareOrders() {
        this.user = UserManager.getQaUser();
        apiV2.authByQA(user);
        apiV2.dropAndFillCart(user, EnvironmentProperties.DEFAULT_SID);
        order = apiV2.setDefaultAttributesAndCompleteOrder();

        apiV2.dropAndFillCart(user, EnvironmentProperties.DEFAULT_SID);
        orderToCancel = apiV2.setDefaultAttributesAndCompleteOrder();

        apiV2.dropAndFillCart(user, EnvironmentProperties.DEFAULT_SID);
        cancelledOrder = apiV2.setDefaultAttributesAndCompleteOrder();
        apiV2.cancelOrder(cancelledOrder.getNumber());
    }

    @AfterClass(alwaysRun = true)
    public void cancelOrders() {
        apiV2.cancelOrder(order.getNumber());
    }

    @TmsLink("2836")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Получение списка отмен отмененного заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getOrderCancellationsCancelledOrder() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.GET(cancelledOrder.getNumber());
        checkStatusCode200(response);
        Allure.step("Проверка ответа", () -> {
                    final SoftAssert softAssert = new SoftAssert();
                    checkResponseJsonSchema(response, OrderCancellationsV1Response.class);
                    softAssert.assertEquals(
                            response.as(OrderCancellationsV1Response.class).getCancellations().size(),
                            1,
                            "Количество блоков отмен в ответе не равно 1");
                    softAssert.assertEquals(
                            response.as(OrderCancellationsV1Response.class).getCancellations().get(0).getInitiator().getContactEmail(),
                            user.getEmail(),
                            "Email инициатора в ответе не соответсвует ожидаемому");
                    softAssert.assertAll();
                }
        );
    }

    @TmsLink("2837")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Получение списка отмен неотмененного заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getOrderCancellationsNotCancelledOrder() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.GET(order.getNumber());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OrderCancellationsV1Response.class);
        Assert.assertEquals(
                response.as(OrderCancellationsV1Response.class).getCancellations().size(),
                0,
                "Для неотмененного заказа блок отмен в ответе не пуст");
    }

    @TmsLink("2838")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка получения списка отмен неавторизованным пользователем",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getOrderCancellationsUnauthenticated() {
        SessionFactory.clearSession(SessionType.API_V1);

        Response response = OrderCancellationsAdminV1Request.GET(cancelledOrder.getNumber());
        checkStatusCode401(response);
    }

    @TmsLink("2839")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка получения списка отмен пользователем без прав администратора",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getOrderCancellationsUnauthorized() {
        apiV1.authByPhone(UserManager.getQaUser());

        Response response = OrderCancellationsAdminV1Request.GET(cancelledOrder.getNumber());
        checkStatusCode403(response);
    }

    @TmsLink("2840")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка получения списка отмен несуществующего заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void getOrderCancellationsNegative() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.GET(Generate.literalString(8));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @TmsLink("2841")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Успешная отмена заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void postOrderCancellations() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.POST(orderToCancel.getNumber(), 1, "test");

        checkStatusCode200(response);

        checkResponseJsonSchema(response, OrderCancellationV1Response.class);
        Allure.step("Проверка ответа", () -> {
            final SoftAssert softAssert = new SoftAssert();
            softAssert.assertEquals(
                    response.as(OrderCancellationV1Response.class).getCancellation().getReason().getId(),
                    1,
                    "ID причины отмены в ответе отличается от отправленного в запросе");
            softAssert.assertEquals(
                    response.as(OrderCancellationV1Response.class).getCancellation().getReasonDetails(),
                    "test",
                    "Информация об отмене в ответе отличается от отправленной в запросе");
            softAssert.assertEquals(
                    SpreeOrdersDao.INSTANCE.getOrderByNumber(orderToCancel.getNumber()).getShipmentState(),
                    "canceled",
                    "Статус заказа в БД отличается от ожидаемого (отменен)");
            softAssert.assertAll();
        });
    }

    @TmsLink("2842")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка отмены заказа неавторизованным пользователем",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void postOrderCancellationsUnauthenticated() {
        SessionFactory.clearSession(SessionType.API_V1);

        Response response = OrderCancellationsAdminV1Request.POST(orderToCancel.getNumber(), 1, "test");
        checkStatusCode401(response);
    }

    @TmsLink("2843")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка отмены заказа пользователем без прав администратора",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void postOrderCancellationsUnauthorized() {
        apiV1.authByPhone(UserManager.getQaUser());

        Response response = OrderCancellationsAdminV1Request.POST(orderToCancel.getNumber(), 1, "test");
        checkStatusCode403(response);
    }

    @TmsLink("2844")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка отмены несуществующего заказа",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void postOrderCancellationsNegative() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.POST(Generate.literalString(8), 1, "test");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @TmsLink("2845")
    @Skip(onServer = Server.STAGING)
    @Test(description = "Попытка отмены заказа - некорректный запрос",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void postOrderCancellationsNegative422() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.POST(cancelledOrder.getNumber(), 1, "test");
        checkStatusCode422(response);
        checkErrorText(response, "Не удалось отменить заказ");
    }
}
