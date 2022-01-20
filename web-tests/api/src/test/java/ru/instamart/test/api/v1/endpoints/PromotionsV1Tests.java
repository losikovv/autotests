package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.PromotionsV1Request;
import ru.instamart.api.response.v1.FreeDeliveryV1Response;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Промо-акции")
public class PromotionsV1Tests extends RestBase {

    @CaseId(1430)
    @Story("Бесплатная доставка")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о бесплатной доставке")
    public void getFreeDeliveryPromotionsInfo() {
        SessionFactory.createSessionToken(SessionType.API_V1, SessionProvider.EMAIL, UserManager.getDefaultAdmin());
        final Response response = PromotionsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FreeDeliveryV1Response.class);
    }
}
