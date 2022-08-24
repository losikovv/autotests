package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ProductFeedbacksV2Request;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Product feedbacks")
public class ProductFeedbacksCreateV2Test extends RestBase {

    @BeforeClass(alwaysRun = true,
            description = "Авторизация и получение SKU")
    public void before() {
        UserData user = UserManager.getDefaultApiUser();
        SessionFactory.createSessionToken(SessionType.API_V2, SessionProvider.PHONE, user);
    }

    @Skip //TODO: возвращает 500
    @CaseIDs({@CaseId(2298), @CaseId(2299)})
    @Story("Создание отзыва на товар")
    @Test(groups = {"api-instamart-regress", "api-v2"},
            dataProvider = "sendProductFeedbacks",
            dataProviderClass = RestDataProvider.class,
            description = "Создание отзыва на товар")
    public void sendProductFeedbacks200(final ProductFeedbacksV2Request.Feedbacks feedbacks) {
        final Response response = ProductFeedbacksV2Request.POST(feedbacks);
        checkStatusCode200(response);
    }
}
