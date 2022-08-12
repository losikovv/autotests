package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.PromotionLimitV2;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.response.v2.PromotionLimitV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Условия оплаты бонусами")
public final class PromotionLimitV2Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(309)
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Проверка условий оплаты бонусами")
    public void bonusPaymentTermsTest() {
        apiV2.dropAndFillCart(
                SessionFactory.getSession(SessionType.API_V2).getUserData(),
                EnvironmentProperties.DEFAULT_SID
        );
        String orderNumber = apiV2.getCurrentOrderNumber();
        final Response response = OrdersV2Request.PromotionLimit.GET(orderNumber);
        checkStatusCode200(response);
        apiV2.deleteAllShipments();
        final PromotionLimitV2 promotionLimit = response.as(PromotionLimitV2Response.class).getPromotionLimits().get(0);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(promotionLimit.getType(), "instacoins_value", "type оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertEquals(promotionLimit.getMaxValue(), 0, "max_value оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertEquals(promotionLimit.getStep(), 50, "step оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertEquals(promotionLimit.getMaxPaymentPercentage(), 50.0D, "max_payment_percentage оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertEquals(promotionLimit.getMaxPaymentAmount(), 5000.0D, "max_payment_amount оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertEquals(promotionLimit.getDescriptionHtml(), "<center><b>Оплата бонусами</b> не может превышать 50% от общей суммы заказа, и не более 5000&nbsp;₽</center>", "description_html оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertEquals(promotionLimit.getAccountAmount(), 0.0D, "account_amount оплаты бонусами не совпадает с ожидаемым");
        softAssert.assertAll();
    }

    @CaseId(310)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Условия оплаты бонусами для несуществующего заказа")
    public void termsOfPaymentWithBonusesforNonExistentParametres() {
        String orderNumber = "test";
        final Response response = OrdersV2Request.PromotionLimit.GET(orderNumber);
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }
}
