package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

import java.util.HashMap;
import java.util.Map;

public class PaymentsV2Request extends ApiV2RequestBase {

    @Step("{method} /" + ApiV2EndPoints.PaymentsSber.CREDIT_CARD_AUTHORIZATIONS)
    public static Response POST(String orderNumber) {
        JSONObject requestParams = new JSONObject();
        JSONObject creditCardAuthorization = new JSONObject();
        creditCardAuthorization.put("order_number", orderNumber);
        requestParams.put("credit_card_authorization", creditCardAuthorization);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .post(ApiV2EndPoints.PaymentsSber.CREDIT_CARD_AUTHORIZATIONS);
    }

    @Step("{method} /" + ApiV2EndPoints.PaymentsSber.CREDIT_CARD_AUTHORIZATIONS)
    public static Response PUT(String transactionNumber) {
        JSONObject requestParams = new JSONObject();
        JSONObject creditCardAuthorization = new JSONObject();
        JSONObject creditCard = new JSONObject();
        creditCard.put("name", "CARDHOLDER NAME");
        creditCard.put("cryptogram_packet", "QEbc");
        creditCardAuthorization.put("credit_card", creditCard);
        creditCardAuthorization.put("transaction_number", transactionNumber);
        requestParams.put("credit_card_authorization", creditCardAuthorization);
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .put(ApiV2EndPoints.PaymentsSber.CREDIT_CARD_AUTHORIZATIONS);
    }

    @Step("{method} /" + ApiV2EndPoints.PaymentsSber.CreditCardAuthorizations.FINISH)
    public static Response GET(String transactionNumber, String orderNumber, String userUuid) {
        Map<String, String> params = new HashMap<>();
        params.put("order_number", orderNumber);
        params.put("user_uuid", userUuid);
        params.put("orderId", transactionNumber);
        return givenWithAuth()
                .queryParams(params)
                .get(ApiV2EndPoints.PaymentsSber.CreditCardAuthorizations.FINISH);
    }
}
