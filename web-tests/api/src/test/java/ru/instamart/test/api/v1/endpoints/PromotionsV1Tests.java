package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.AdminPromotionV1;
import ru.instamart.api.request.v1.PromotionsV1Request;
import ru.instamart.api.response.v1.FreeDeliveryV1Response;
import ru.instamart.api.response.v1.PromotionV1Response;
import ru.instamart.api.response.v1.PromotionsV1Response;
import ru.instamart.jdbc.dao.stf.SpreeActivatorsDao;
import ru.instamart.jdbc.entity.stf.SpreeActivatorsEntity;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Промо-акции")
public class PromotionsV1Tests extends RestBase {

    private Long promotionId;

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

    @CaseId(2245)
    @Story("Компенсации")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение информации о промо-акциях")
    public void getPromotions() {
        final Response response = PromotionsV1Request.Promotions.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionsV1Response.class);
        promotionId = response.as(PromotionsV1Response.class).getPromotions().get(0).getId();
    }

    @CaseId(2245)
    @Story("Компенсации")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение информации о промо-акции по id",
            dependsOnMethods = "getPromotions")
    public void getPromotion() {
        final Response response = PromotionsV1Request.Promotions.GET(promotionId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionV1Response.class);
        AdminPromotionV1 promotionFromResponse = response.as(PromotionV1Response.class).getPromotion();
        SpreeActivatorsEntity promotionFromDb = SpreeActivatorsDao.INSTANCE.findById(promotionId).get();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(promotionFromResponse.getServiceComment(), promotionFromDb.getServiceComment(), softAssert);
        compareTwoObjects(promotionFromResponse.getDescription(), promotionFromDb.getDescription(), softAssert);
        softAssert.assertAll();
    }
}
