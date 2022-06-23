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
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkErrorText;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Отмены заказа")
public class OrderCancellationsV1Test extends RestBase {
    UserData user = UserManager.getQaUser();
    OrderV2 order = new OrderV2();
    OrderV2 orderToCancel = new OrderV2();
    OrderV2 cancelledOrder = new OrderV2();

    @BeforeClass(alwaysRun = true)
    public void prepareOrders() {
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

    @CaseId(2836)
    @Test(description = "Получение списка отмен отмененного заказа", groups = {"api-instamart-regress"})
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

    @CaseId(2837)
    @Test(description = "Получение списка отмен неотмененного заказа", groups = {"api-instamart-regress"})
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

    @CaseId(2838)
    @Test(description = "Попытка получения списка отмен неавторизованным пользователем", groups = {"api-instamart-regress"})
    public void getOrderCancellationsUnauthenticated() {
        SessionFactory.clearSession(SessionType.API_V1);

        Response response = OrderCancellationsAdminV1Request.GET(cancelledOrder.getNumber());
        checkStatusCode401(response);
    }

    @CaseId(2839)
    @Test(description = "Попытка получения списка отмен пользователем без прав администратора", groups = {"api-instamart-regress"})
    public void getOrderCancellationsUnauthorized() {
        apiV1.authByPhone(UserManager.getQaUser());

        Response response = OrderCancellationsAdminV1Request.GET(cancelledOrder.getNumber());
        checkStatusCode403(response);
    }

    @CaseId(2840)
    @Test(description = "Попытка получения списка отмен несуществующего заказа", groups = {"api-instamart-regress"})
    public void getOrderCancellationsNegative() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.GET(Generate.literalString(8));
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2841)
    @Test(description = "Успешная отмена заказа", groups = {"api-instamart-regress"})
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

    @CaseId(2842)
    @Test(description = "Попытка отмены заказа неавторизованным пользователем", groups = {"api-instamart-regress"})
    public void postOrderCancellationsUnauthenticated() {
        SessionFactory.clearSession(SessionType.API_V1);

        Response response = OrderCancellationsAdminV1Request.POST(orderToCancel.getNumber(), 1, "test");
        checkStatusCode401(response);
    }

    @CaseId(2843)
    @Test(description = "Попытка отмены заказа пользователем без прав администратора", groups = {"api-instamart-regress"})
    public void postOrderCancellationsUnauthorized() {
        apiV1.authByPhone(UserManager.getQaUser());

        Response response = OrderCancellationsAdminV1Request.POST(orderToCancel.getNumber(), 1, "test");
        checkStatusCode403(response);
    }

    @CaseId(2844)
    @Test(description = "Попытка отмены несуществующего заказа", groups = {"api-instamart-regress"})
    public void postOrderCancellationsNegative() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.POST(Generate.literalString(8), 1, "test");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2845)
    @Test(description = "Попытка отмены заказа - некорректный запрос", groups = {"api-instamart-regress"})
    public void postOrderCancellationsNegative422() {
        admin.authApi();

        Response response = OrderCancellationsAdminV1Request.POST(cancelledOrder.getNumber(), 1, "test");
        checkStatusCode422(response);
        checkErrorText(response, "Не удалось отменить заказ");
    }
}
