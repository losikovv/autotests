package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.PromotionsV2Request;
import ru.instamart.api.response.v2.ProductsV2Response;
import ru.instamart.api.response.v2.ReferralProgramV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Промо-акции")
public class PromotionsV2Test extends RestBase {

    @CaseId(824)
    @Story("Реферальная программа")
    @Test(groups = {"api-instamart-regress"}, description = "Общие сведения о реферальной программе")
    public void getReferralProgram() {
        final Response response = PromotionsV2Request.ReferralProgram.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ReferralProgramV2Response.class);
    }

    @CaseId(292)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий productId и существующий id магазина")
    public void testGetListOfProductWithValidProductId() {
        final Response response = PromotionsV2Request.PromoProducts.GET(2707, EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        assertTrue(productsV2Response.getProducts().isEmpty(), "Список продуктов вернулся не пустой");
    }

    @CaseId(293)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий productId")
    public void testGetListOfProductWithInvalidProductId() {
        final Response response = PromotionsV2Request.PromoProducts.GET(1, EnvironmentProperties.DEFAULT_SID);
        checkStatusCode404(response);
    }

    @CaseId(295)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий sid")
    public void testGetListOfProductWithInvalidSid() {
        final Response response = PromotionsV2Request.PromoProducts.GET(2707, 66666);
        checkStatusCode404(response);
    }
}
