package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.PromotionLimitV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Условия оплаты бонусами.")
public final class PromotionLimitV2Test extends RestBase {

    @BeforeMethod
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        apiV2.deleteAllShipments();
    }

    @CaseId(309)
    @Test(groups = {"api-instamart-regress"},
            description = "Проверка условий оплаты бонусами")
    public void bonusPaymentTermsTest() {
        apiV2.fillCart(
                SessionFactory.getSession(SessionType.API_V2_FB).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
        String orderNumber = apiV2.getCurrentOrderNumber();
        final Response response = OrdersV2Request.PromotionLimit.GET(orderNumber);
        checkStatusCode200(response);
        final PromotionLimitV2Response promotionLimitV2Response = response.as(PromotionLimitV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getType(), "instacoins_value");
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getMaxValue(), 0);
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getStep(), 50);
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getMaxPaymentPercentage(), 50.0D);
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getMaxPaymentAmount(), 5000.0D);
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getDescriptionHtml(), "<center><b>Оплата бонусами</b> не может превышать 50% от общей суммы заказа, и не более 5000&nbsp;₽</center>");
        softAssert.assertEquals(promotionLimitV2Response.getPromotionLimits().get(0).getAccountAmount(), 0.0D);
        softAssert.assertAll();
    }

    @CaseId(310)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            description = "Условия оплаты бонусами для несуществующего заказа")
    public void termsOfPaymentWithBonusesforNonExistentParametres() {
        String orderNumber = "test";
        final Response response = OrdersV2Request.PromotionLimit.GET(orderNumber);
        checkStatusCode404(response);
        errorAssert(response,"Заказ не существует");
    }
}
