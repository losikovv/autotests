package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.enums.v2.PaymentToolV2;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.OrdersV2Request;
import ru.instamart.api.request.v2.PaymentToolsV2Request;
import ru.instamart.api.request.v2.PaymentToolsWithTypesV2Request;
import ru.instamart.api.response.v2.PaymentToolTypesV2Response;
import ru.instamart.api.response.v2.PaymentToolsV2Response;
import ru.instamart.api.response.v2.PaymentToolsWithTypesV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Способы оплаты")
public class PaymentToolsV2Test extends RestBase {

    private String orderNumber;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
        orderNumber = apiV2.createOrder().getNumber();
    }

    @CaseId(1115)
    @Story("Способы оплаты")
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod", "api-v2"},
            description = "Получить способы оплаты")
    public void getPaymentMethods() {
        final Response response = PaymentToolsV2Request.GET();
        checkStatusCode200(response);
        PaymentToolsV2Response paymentToolsV2Response = response.as(PaymentToolsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        paymentToolsV2Response.getPaymentTools()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfKeyIsPresent(value.getType()), "Способ оплаты пустой"));
        paymentToolsV2Response.getPaymentTools()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfNameIsPresent(value.getName()), "Способ оплаты пустой"));
        softAssert.assertAll();
    }

    @CaseId(682)
    @Story("Способы оплаты с типами")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Существующий номер заказа")
    public void getPaymentMethodsWithTypes() {
        final Response response = OrdersV2Request.PaymentToolsWithTypes.GET(orderNumber);
        checkStatusCode200(response);
        PaymentToolsWithTypesV2Response paymentToolsWithTypesV2Response = response.as(PaymentToolsWithTypesV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        paymentToolsWithTypesV2Response.getPaymentTools()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfKeyIsPresent(value.getType()), "Тип способа оплаты пустой"));
        paymentToolsWithTypesV2Response.getPaymentTools()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfNameIsPresent(value.getName()), "Имя способа оплаты пустое"));
        paymentToolsWithTypesV2Response.getPaymentToolTypes()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfKeyIsPresent(value.getType()), "Тип способа оплаты пустой"));
        paymentToolsWithTypesV2Response.getPaymentToolTypes()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfNameIsPresent(value.getName()), "Имя способа оплаты пустое"));
        paymentToolsWithTypesV2Response.getPaymentToolTypes()
                .forEach(value -> softAssert.assertNotNull(PaymentToolV2.getIfNameIsPresent(value.getName()), "Описание способа оплаты пустое"));
        softAssert.assertAll();
    }

    @CaseId(820)
    @Story("Способы оплаты с типами")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Несуществующий номер заказа")
    public void getPaymentMethodsWithTypesForNonexistingOrder() {
        final Response response = OrdersV2Request.PaymentToolsWithTypes.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @Skip
    @CaseId(1116)
    @Story("Способы оплаты")
    @Deprecated
    @Test(groups = {"api-instamart-regress", "api-v2"},
            description = "Существующий номер заказа")
    public void getPaymentToolsWithOrder200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String orderNumber = apiV2.getCurrentOrderNumber();
        final Response response = PaymentToolsV2Request.GET(orderNumber);
        checkStatusCode200(response);
        PaymentToolTypesV2Response paymentToolsV2Response = response.as(PaymentToolTypesV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getName(), "По счёту для бизнеса", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getDescription(), "Сборка и доставка заказа осуществляется только после полной предоплаты и поступления\r\n          денежных средств на наш счет", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(0).getType(), "sber_bank_invoice", "Способ оплаты пустой");

        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getName(), "Картой онлайн", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getDescription(), "После оформления заказа мы спишем и вернём 11 рублей для проверки карты. Полная стоимость будет списана после сборки заказа в магазине.", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(1).getType(), "cloud_payments_gateway", "Способ оплаты пустой");

        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(2).getName(), "Apple Pay", "Способ оплаты пустой");
        softAssert.assertEquals(paymentToolsV2Response.getPaymentToolTypes().get(2).getType(), "apple_pay", "Способ оплаты пустой");

        softAssert.assertAll();
    }

    @Skip
    @CaseId(1117)
    @Story("Способы оплаты")
    @Deprecated
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Несуществующий номер заказа")
    public void getPaymentToolsWithOrder404() {
        final Response response = PaymentToolsV2Request.GET("failedOrderNumber");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(2217)
    @Story("Способы оплаты")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Несуществующий номер заказа")
    public void getPaymentToolsWithTypes404() {
        final Response response = PaymentToolsWithTypesV2Request.GET(null,"failedOrderId");
        checkStatusCode404(response);
        checkError(response, "Заказ не существует");
    }

    @CaseId(2216)
    @Story("Способы оплаты")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Существующий номер заказа")
    public void getPaymentToolsWithTypes200() {
        apiV2.dropAndFillCart(SessionFactory.getSession(SessionType.API_V2).getUserData(), EnvironmentProperties.DEFAULT_SID);
        String orderNumber = apiV2.getCurrentOrderNumber();
        final Response response = PaymentToolsWithTypesV2Request.GET(null, orderNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, PaymentToolTypesV2Response.class);
    }
}
