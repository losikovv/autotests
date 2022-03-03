package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeClass;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.PaymentsV2Request;
import ru.instamart.api.response.v2.CreditCardAuthorizationV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Авторизация банковской карты через Сбербанк")
public class SberCardAuthorizationV2Test extends RestBase {

    private String transactionNumber;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseIDs(value = {@CaseId(509), @CaseId(510)})
    @Story("Начало авторизации")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, enabled = false, // проблема на стороне сбера https://sbermarket.slack.com/archives/C01M76U7WT1/p1646301134269719?thread_ts=1646113076.981539&cid=C01M76U7WT1
            description = "Получение номера транзакции для заказа",
            dataProvider = "orderNumbers",
            dataProviderClass = RestDataProvider.class)
    public void getTransactionNumber(String orderNumber) {
        final Response response = PaymentsV2Request.POST(orderNumber);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, CreditCardAuthorizationV2Response.class);
        CreditCardAuthorizationV2Response creditCardAuthorizationV2Response = response.as(CreditCardAuthorizationV2Response.class);
        transactionNumber = creditCardAuthorizationV2Response.getCreditCardAuthorization().getTransactionNumber();
    }

    @CaseIDs(value = {@CaseId(512), @CaseId(1033)})
    @Story("Продолжение авторизации")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, enabled = false, // проблема на стороне сбера
            description = "Продолжение авторизации с невалидными данными",
            dataProvider = "transactionNumbers",
            dataProviderClass = RestDataProvider.class)
    public void authorizeCardWithInvalidData(String transactionNumber, int statusCode, String errorMessage) {
        final Response response = PaymentsV2Request.PUT(transactionNumber);
        checkStatusCode(response, statusCode);
        checkError(response, errorMessage);
    }

    @CaseId(513)
    @Story("Продолжение авторизации")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, enabled = false, // проблема на стороне сбера
            description = "Продолжение авторизации с невалидным токеном авторизации",
            dependsOnMethods = "getTransactionNumber")
    public void authorizeCardWithInvalidToken() {
        SessionFactory.clearSession(SessionType.API_V2);
        final Response response = PaymentsV2Request.PUT(transactionNumber);
        checkStatusCode401(response);
        checkError(response, "Ключ доступа невалиден или отсутствует");
    }

    @CaseIDs(value = {@CaseId(515), @CaseId(1041)})
    @Story("Финальный шаг авторизации карты")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, enabled = false, // проблема на стороне сбера
            description = "Завершение авторизации карты с невалидными данными",
            dataProvider = "invalidTransactionData",
            dataProviderClass = RestDataProvider.class)
    public void finishCardAuthorizationWithoutCard(String transactionNumber, String orderNumber, String userUuid, String errorMessage) {
        final Response response = PaymentsV2Request.GET(transactionNumber, orderNumber, userUuid);
        checkStatusCode404(response);
        checkError(response, errorMessage);
    }
}
