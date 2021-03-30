package ru.instamart.tests.api.delivery_club.contracts;

import instamart.api.common.RestBase;
import instamart.api.requests.delivery_club.AuthenticationDCRequest;
import instamart.api.responses.deliveryclub.TokenDCResponse;
import instamart.core.testdata.UserManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Epic("Партнёры")
@Feature("Delivery Club")
public class AuthenticationDCTest extends RestBase {

    @CaseId(562)
    @Story("Авторизация")
    @Test(groups = {"api-instamart-regress"},
          description = "Получение токена")
    public void postAuthToken() {
        final Response response = AuthenticationDCRequest.Token.POST(UserManager.getDeliveryClubUser());
        checkStatusCode200(response);

        final TokenDCResponse tokenResponse = response.as(TokenDCResponse.class);
        assertNotNull(tokenResponse.getToken(), "Вернулся пустой токен");

        LocalDateTime localDateTimeFromResponse = LocalDateTime.parse(tokenResponse.getExpiresAt().substring(0, 19));
        assertTrue(localDateTimeFromResponse.isBefore(LocalDateTime.now().plus(16, ChronoUnit.MINUTES)));
        assertTrue(localDateTimeFromResponse.isAfter(LocalDateTime.now().plus(14, ChronoUnit.MINUTES)));
    }
}
