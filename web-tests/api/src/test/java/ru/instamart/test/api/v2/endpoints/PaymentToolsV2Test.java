package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.PaymentToolsV2Request;
import ru.instamart.api.response.v2.PaymentToolTypesV2Response;
import ru.instamart.api.response.v2.PaymentToolsV2Response;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Способы оплаты")
public class PaymentToolsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseId(20)
    @Test(description = "Получаем инфу о способах оплаты",
            groups = {"api-instamart-smoke", "api-instamart-prod"})
    public void getPaymentTools() {
        response = PaymentToolsV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(PaymentToolsV2Response.class).getPaymentTools(),
                "Не вернулась инфа о спобах оплаты");
    }

    @CaseId(372)
    @Test(groups = {"api-instamart-regress"},
            description = "Возможные типы способов оплаты для заказа с существущим id")
    public void getPaymentToolsWithOrder200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentData.INSTANCE.getDefaultSid());
        String orderNumber = apiV2.getCurrentOrderNumber();
        response = PaymentToolsV2Request.GET(orderNumber);
        checkStatusCode200(response);
        PaymentToolTypesV2Response paymentToolsV2Response = response.as(PaymentToolTypesV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getName(), "По счёту для бизнеса");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getDescription(), "Сборка и доставка заказа осуществляется только после полной предоплаты и поступления\r\n          денежных средств на наш счет");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getType(), "sber_bank_invoice");

        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getName(), "Картой онлайн");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getDescription(), "После оформления заказа мы спишем и вернем один рубль для проверки карты. \r\nПолная стоимость будет списана после сборки заказа в магазине.");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getType(), "cloud_payments_gateway");

        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(2).getName(), "Apple Pay");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(2).getType(), "apple_pay");

        softAssert.assertAll();
    }

    @CaseId(373)
    @Test(groups = {"api-instamart-regress"},
            description = "Возможные типы способов оплаты для заказа с несуществущим id")
    public void getPaymentToolsWithOrder404() {
        response = PaymentToolsV2Request.GET("failedOrderNumber");
        checkStatusCode404(response);
        errorAssert(response, "Заказ не существует");
    }
}
