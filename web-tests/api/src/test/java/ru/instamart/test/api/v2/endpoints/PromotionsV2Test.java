package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.PromotionsV2Request;
import ru.instamart.api.response.v2.ProductsV2Response;
import ru.instamart.api.response.v2.ReferralProgramV2Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Промо-акции")
public class PromotionsV2Test extends RestBase {

    @CaseId(17)
    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getReferralProgram() {
        response = PromotionsV2Request.ReferralProgram.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(ReferralProgramV2Response.class).getReferralProgram(),
                "Не вернулась инфа о реферальной программе");
    }

    @CaseId(292)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-smoke"}, description = "Существующий productId")
    public void testGetListOfProductWithValidProductId() {
        final Response response = PromotionsV2Request.PromoProducts.GET(2707, EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        final ProductsV2Response productsV2Response = response.as(ProductsV2Response.class);
        assertTrue(productsV2Response.getProducts().isEmpty());
    }

    @CaseId(293)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий productId")
    public void testGetListOfProductWithInvalidProductId() {
        final Response response = PromotionsV2Request.PromoProducts.GET(1, EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode404(response);
    }

    @CaseId(294)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий sid")
    public void testGetListOfProductWithInvalidSid() {
        final Response response = PromotionsV2Request.PromoProducts.GET(2707, 66666);
        checkStatusCode404(response);
    }
}
