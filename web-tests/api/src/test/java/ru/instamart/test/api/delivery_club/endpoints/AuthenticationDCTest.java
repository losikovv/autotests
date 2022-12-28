package ru.instamart.test.api.delivery_club.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.delivery_club.AuthenticationDCRequest;
import ru.instamart.api.response.delivery_club.TokenDCResponse;
import ru.instamart.kraken.data.user.UserManager;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.testng.Assert.assertTrue;
import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

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
        checkFieldIsNotEmpty(tokenResponse.getToken(), "токен");

        final LocalDateTime localDateTimeFromResponse = LocalDateTime.parse(tokenResponse.getExpiresAt().substring(0, 19));
        final LocalDateTime dateNow = LocalDateTime.now(ZoneOffset.of(tokenResponse.getExpiresAt().substring(19, 25)));
        assertTrue(localDateTimeFromResponse.isBefore(dateNow.plusMinutes(16).withNano(0)), "Токен истекает раньше");
        assertTrue(localDateTimeFromResponse.isAfter(dateNow.plusMinutes(14).withNano(0)), "Токен истекает позже");
    }

    @CaseId(573)
    @Story("Авторизация")
    @Test(  groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD},
                description = "Клиент не авторизован")
    public void postAuthToken401() {
        final Response response = AuthenticationDCRequest.Token.POST("","");
        checkStatusCode401(response);
    }
}
