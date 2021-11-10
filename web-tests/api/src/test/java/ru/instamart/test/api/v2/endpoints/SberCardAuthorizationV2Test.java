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

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Авторизация банковской карты через Сбербанк")
public class SberCardAuthorizationV2Test extends RestBase {

    private String transactionNumber;

    @BeforeMethod(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseIDs(value = {@CaseId(509), @CaseId(510)})
    @Story("Начало авторизации")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение номера транзакции для заказа",
            dataProvider = "orderNumbers",
            dataProviderClass = RestDataProvider.class)
    public void getTransactionNumber(String orderNumber) {
        Response response = PaymentsV2Request.POST(orderNumber);
        checkStatusCode200(response);
        CreditCardAuthorizationV2Response creditCardAuthorizationV2Response = response.as(CreditCardAuthorizationV2Response.class);
        checkFieldIsNotEmpty(creditCardAuthorizationV2Response.getCreditCardAuthorization(), "авторизация карты");
        transactionNumber = creditCardAuthorizationV2Response.getCreditCardAuthorization().getTransactionNumber();
        checkFieldIsNotEmpty(transactionNumber, "номер транзакции");
    }

    @CaseIDs(value = {@CaseId(512), @CaseId(1033)})
    @Story("Продолжение авторизации")
    @Test(groups = {"api-instamart-regress"},
            description = "Продолжение авторизации с невалидными данными",
            dataProvider = "transactionNumbers",
            dataProviderClass = RestDataProvider.class)
    public void authorizeCardWithInvalidData(String transactionNumber, int statusCode, String errorMessage) {
        Response response = PaymentsV2Request.PUT(transactionNumber);
        checkStatusCode(response, statusCode);
        checkError(response, errorMessage);
    }

    @CaseId(513)
    @Story("Продолжение авторизации")
    @Test(groups = {"api-instamart-regress"},
            description = "Продолжение авторизации с невалидным токеном авторизации",
            dependsOnMethods = "getTransactionNumber")
    public void authorizeCardWithInvalidToken() {
        SessionFactory.clearSession(SessionType.API_V2_FB);
        Response response = PaymentsV2Request.PUT(transactionNumber);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseIDs(value = {@CaseId(515), @CaseId(1041)})
    @Story("Финальный шаг авторизации карты")
    @Test(groups = {"api-instamart-regress"},
            description = "Завершение авторизации карты с невалидными данными",
            dataProvider = "invalidTransactionData",
            dataProviderClass = RestDataProvider.class)
    public void finishCardAuthorizationWithoutCard(String transactionNumber, String orderNumber, String userUuid, String errorMessage) {
        Response response = PaymentsV2Request.GET(transactionNumber, orderNumber, userUuid);
        checkStatusCode404(response);
        checkError(response, errorMessage);
    }
}
