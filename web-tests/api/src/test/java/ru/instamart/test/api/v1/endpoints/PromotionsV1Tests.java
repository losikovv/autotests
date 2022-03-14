package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.PromotionsV1Request;
import ru.instamart.api.response.v1.CompensationPromotionsV1Response;
import ru.instamart.api.response.v1.FreeDeliveryV1Response;
import ru.instamart.api.response.v1.PromotionsV1Response;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Промо-акции")
public class PromotionsV1Tests extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(1430)
    @Story("Бесплатная доставка")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о бесплатной доставке")
    public void getFreeDeliveryPromotionsInfo() {
        final Response response = PromotionsV1Request.FreeDelivery.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, FreeDeliveryV1Response.class);
    }

    @Issue("B2C-6444")
    @CaseId(1430)
    @Story("Компенсации")
    @Test(groups = {"api-instamart-regress"}, enabled = false, // работает только с новыми ролями, но тогда отваливаются другие тесты
            description = "Получение информации о компенсациях")
    public void getCompensationPromotions() {
        final Response response = PromotionsV1Request.CompensationPromotions.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CompensationPromotionsV1Response.class);
    }

    @CaseId(1430)
    @Story("Компенсации")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Получение информации о промо-акциях")
    public void getPromotions() {
        final Response response = PromotionsV1Request.Promotions.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionsV1Response.class);
    }
}
