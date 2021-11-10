package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseIDs;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.PaymentsV2Request;
import ru.instamart.api.response.v2.CreditCardAuthorizationV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV2")
@Feature("Оплата заказа")
public class PaymentsV2Test extends RestBase {

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseIDs(value = {@CaseId(509), @CaseId(510)})
    @Story("Авторизация банковской карты через Сбербанк")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение номера транзакции для заказа",
            dataProvider = "orderNumbers",
            dataProviderClass = RestDataProvider.class)
    public void getTransactionNumber(String orderNumber) {
        Response response = PaymentsV2Request.POST(orderNumber);
        checkStatusCode200(response);
        CreditCardAuthorizationV2Response creditCardAuthorizationV2Response = response.as(CreditCardAuthorizationV2Response.class);
        checkFieldIsNotEmpty(creditCardAuthorizationV2Response.getCreditCardAuthorization(), "авторизация карты");
        checkFieldIsNotEmpty(creditCardAuthorizationV2Response.getCreditCardAuthorization().getTransactionNumber(), "номер транзакции");
    }
}
