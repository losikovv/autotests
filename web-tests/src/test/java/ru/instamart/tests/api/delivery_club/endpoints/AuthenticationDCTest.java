package ru.instamart.tests.api.delivery_club.endpoints;

import ru.instamart.api.common.RestBase;
import ru.instamart.api.requests.delivery_club.AuthenticationDCRequest;
import ru.instamart.api.responses.deliveryclub.TokenDCResponse;
import ru.instamart.core.testdata.UserManager;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode401;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

@Epic("Партнёры")
@Feature("Delivery Club")
public class AuthenticationDCTest extends RestBase {

    @CaseId(562)
    @Story("Авторизация")
    @Test(  groups = {"api-instamart-smoke"},
            description = "Получение токена")
    public void postAuthToken200() {
        final Response response = AuthenticationDCRequest.Token.POST(UserManager.getDeliveryClubUser());
        checkStatusCode200(response);

        final TokenDCResponse tokenResponse = response.as(TokenDCResponse.class);
        assertNotNull(tokenResponse.getToken(), "Вернулся пустой токен");

        final LocalDateTime localDateTimeFromResponse = LocalDateTime.parse(tokenResponse.getExpiresAt().substring(0, 19));
        final LocalDateTime dateNow = LocalDateTime.now(ZoneOffset.of(tokenResponse.getExpiresAt().substring(19, 25)));
        assertTrue(localDateTimeFromResponse.isBefore(dateNow.plusMinutes(16).withNano(0)), "Токен истекает раньше");
        assertTrue(localDateTimeFromResponse.isAfter(dateNow.plusMinutes(14).withNano(0)), "Токен истекает позже");
    }

    @CaseId(573)
    @Story("Авторизация")
    @Test(  groups = {"api-instamart-regress", "api-instamart-prod"},
                description = "Клиент не авторизован")
    public void postAuthToken401() {
        final Response response = AuthenticationDCRequest.Token.POST("","");
        checkStatusCode401(response);
    }
}
