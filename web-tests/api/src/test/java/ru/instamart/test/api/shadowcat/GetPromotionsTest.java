package ru.instamart.test.api.shadowcat;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.testng.annotations.Test;
import ru.instamart.api.common.ShadowcatRestBase;
import ru.instamart.api.model.shadowcat.Promotion;
import ru.instamart.api.request.shadowcat.PromotionRequest.Promotions;
import ru.instamart.api.response.shadowcat.PromotionsResponse;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.enums.shadowcat.PromotionType.DISCOUNT;

@Slf4j
@Epic("Shadowcat")
@Feature("Работа с промоакциями")
public class GetPromotionsTest extends ShadowcatRestBase {
    private static int promoId;

    @CaseId(3)
    @Test(description = "Получение списка промоакций",
            groups = {"api-shadowcat"},
            priority = 1)
    public void getPromotionsList() {
        Response response = Promotions.GET();
        final List<Promotion> promotions = response.as(PromotionsResponse.class).getItems();
        promoId = promotions.get(0).getId();
        checkStatusCode200(response);
    }

    @CaseId(4)
    @Test(description = "Получение одной промоакций",
            groups = {"api-shadowcat"},
            priority = 2,
            dependsOnMethods = "getPromotionsList")
    public void getOnePromotion() {
        Response response = Promotions.GET(DISCOUNT.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, Promotion.class);
    }
}
