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
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV2")
@Feature("Отзывы о заказе")
public class ReviewIssuesWithoutAuthV2Test extends RestBase {
    private String orderNumber;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        OrderV2 order = apiV2.getCurrentOrder();
        orderNumber = order.getNumber();
    }
    @CaseId(1445)
    @Story("Получение списка возможных проблем для отзыва о заказе")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Получение списка возможных проблем для отзыва о существующем заказе без авторизации",
            priority = 1)
    public void getListIssuesForOrderWuthoutAuth() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = OrdersV2Request.ReviewIssues.GET(orderNumber);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }
}
