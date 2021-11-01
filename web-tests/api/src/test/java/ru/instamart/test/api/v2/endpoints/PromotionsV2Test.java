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
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.enums.Tenant;
import ru.instamart.kraken.listener.Run;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Промо-акции")
public class PromotionsV2Test extends RestBase {

    @Run(onTenant = Tenant.SBERMARKET)
    @Deprecated
    @Test(  description = "Получаем инфу о реферальной программе",
            groups = {})
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

    @CaseId(294)
    @Story("Список продуктов для активации промо")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Несуществующий sid")
    public void testGetListOfProductWithInvalidSid() {
        final Response response = PromotionsV2Request.PromoProducts.GET(2707, 66666);
        checkStatusCode404(response);
    }
}
