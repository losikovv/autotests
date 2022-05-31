package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.NotificationsV2Request;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusGroup400;

@Epic("ApiV2")
@Feature("Notifications")
public class NotificationsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @Story("Проверка оплаты")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "notificationsFailed",
            dataProviderClass = RestDataProvider.class,
            description = "Проверка оплаты в Sber SuperApp без тела запроса")
    public void notifications400(NotificationsV2Request.Events events) {
        final Response response = NotificationsV2Request.POST(events);
        checkStatusGroup400(response);
        checkError(response, "Отсутствует обязательный параметр 'event'");
    }
}
