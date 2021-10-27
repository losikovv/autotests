package ru.instamart.api.checkpoint;

import io.qameta.allure.Allure;
import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;

public class AdminApiCheckpoints {

    public static void checkStatusCode(Response response, int statusCode) {
        Allure.step("Проверка statusCode = "+statusCode+" для response");
        assertEquals(
                response.statusCode(),
                statusCode,
                "\n" + response.statusLine() + "\n" + response.body().asString());
    }

    public static void checkStatusCode200(Response response) {
        checkStatusCode(response, 200);
    }

    public static void checkStatusCode302(Response response) {
        checkStatusCode(response, 302);
    }
}
