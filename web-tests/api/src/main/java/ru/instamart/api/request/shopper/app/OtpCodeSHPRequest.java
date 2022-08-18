package ru.instamart.api.request.shopper.app;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ShopperAppEndpoints;

import static io.restassured.RestAssured.given;
import static ru.instamart.kraken.config.EnvironmentProperties.SSO_AUTH_URL;

public class OtpCodeSHPRequest {

    @Step("{method} /" + ShopperAppEndpoints.Auth.Testing.OTP)
    public static Response GET(String phoneNumber){
        return given()
                .baseUri(SSO_AUTH_URL)
                .basePath(ShopperAppEndpoints.Auth.Testing.OTP)
                .queryParam("phone", phoneNumber)
                .get();
    }
}
