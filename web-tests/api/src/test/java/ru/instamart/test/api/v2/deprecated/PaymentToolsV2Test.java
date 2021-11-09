package ru.instamart.test.api.v2.deprecated;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.PaymentToolsV2Request;
import ru.instamart.api.response.v2.PaymentToolTypesV2Response;
import ru.instamart.api.response.v2.PaymentToolsV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Способы оплаты")
@Deprecated
public class PaymentToolsV2Test extends RestBase {

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @Deprecated
    @Test(description = "Получаем инфу о способах оплаты",
            groups = {})
    public void getPaymentTools() {
        response = PaymentToolsV2Request.GET();
        checkStatusCode200(response);
        assertNotNull(response.as(PaymentToolsV2Response.class).getPaymentTools(),
                "Не вернулась инфа о спобах оплаты");
    }

    @Deprecated
    @Test(groups = {},
            description = "Возможные типы способов оплаты для заказа с существущим id")
    public void getPaymentToolsWithOrder200() {
        apiV2.fillCart(SessionFactory.getSession(SessionType.API_V2_FB).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String orderNumber = apiV2.getCurrentOrderNumber();
        response = PaymentToolsV2Request.GET(orderNumber);
        checkStatusCode200(response);
        PaymentToolTypesV2Response paymentToolsV2Response = response.as(PaymentToolTypesV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getName(), "По счёту для бизнеса", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getDescription(), "Сборка и доставка заказа осуществляется только после полной предоплаты и поступления\r\n          денежных средств на наш счет", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getType(), "sber_bank_invoice", "Способ оплаты пустой");

        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getName(), "Картой онлайн", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getDescription(), "После оформления заказа мы спишем и вернем один рубль для проверки карты. \r\nПолная стоимость будет списана после сборки заказа в магазине.", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getType(), "cloud_payments_gateway", "Способ оплаты пустой");

        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(2).getName(), "Apple Pay", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(2).getType(), "apple_pay", "Способ оплаты пустой");

        softAssert.assertAll();
    }

    @Deprecated
    @Test(groups = {},
            description = "Возможные типы способов оплаты для заказа с несуществущим id")
    public void getPaymentToolsWithOrder404() {
        response = PaymentToolsV2Request.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }
}
