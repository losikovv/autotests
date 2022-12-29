package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.PromotionCardCategoriesV1Request;
import ru.instamart.api.response.v1.PromotionCardCategoriesV1Response;
import ru.instamart.jdbc.dao.stf.PromotionCardCategoriesDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Промо-акции")
public class PromotionCardCategoriesV1Tests extends RestBase {

    @Story("Промо-карточки")
    @TmsLink("2136")
    @Test(description = "Получение списка категорий промокарточек",
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v1"})
    public void getPromotionCardsCategories() {
        final Response response = PromotionCardCategoriesV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PromotionCardCategoriesV1Response.class);
        if (!EnvironmentProperties.Env.isProduction()) {
            int countFromDb = PromotionCardCategoriesDao.INSTANCE.getCount();
            compareTwoObjects(response.as(PromotionCardCategoriesV1Response.class).getPromotionCardCategories().size(), countFromDb);
        }
    }
}
