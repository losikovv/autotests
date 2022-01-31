package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.OrderV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.ShipmentsV2Request;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkReviewIssuesList;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
public class ReviewIssuesV2Test extends RestBase {

    private String orderNumber;
    private String shipmentNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        OrderV2 order = apiV2.getCurrentOrder();
        orderNumber = order.getNumber();
        shipmentNumber = order.getShipments().get(0).getNumber();
    }

    @CaseId(470)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о существующей доставке")
    public void getListIssuesForShipment() {
        final Response response = ShipmentsV2Request.ReviewIssues.GET(shipmentNumber);
        checkStatusCode200(response);
        checkReviewIssuesList(response);
    }

    @CaseId(471)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о не существующей доставке")
    public void getListIssuesForNonExistentShipment() {
        final Response response = ShipmentsV2Request.ReviewIssues.GET("failedShipmentNumber");
        checkStatusCode404(response);
        checkError(response, "Доставка не существует");
    }

    @CaseId(1446)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о существующей доставке без авторизации",
            priority = 1)
    public void getListIssuesForShipmentWithoutAuth() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = ShipmentsV2Request.ReviewIssues.GET(shipmentNumber);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseId(1440)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о существующем заказе")
    public void getListIssuesForOrder() {
        final Response response = OrdersV2Request.ReviewIssues.GET(orderNumber);
        checkStatusCode200(response);
        checkReviewIssuesList(response);
    }

    @CaseId(1441)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о не существующем заказе")
    public void getListIssuesForNonExistentOrder() {
        final Response response = OrdersV2Request.ReviewIssues.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(1445)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка возможных проблем для отзыва о существующем заказе без авторизации",
            priority = 1)
    public void getListIssuesForOrderWuthoutAuth() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = OrdersV2Request.ReviewIssues.GET(orderNumber);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }
}
