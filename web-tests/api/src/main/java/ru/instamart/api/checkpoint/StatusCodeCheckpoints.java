package ru.instamart.api.checkpoint;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.testng.Assert.assertEquals;

@Slf4j
public class StatusCodeCheckpoints {

    @Step("Проверка {statusCode} статус кода ответа")
    public static void checkStatusCode(Response response, int statusCode) {
        response.then().statusCode(statusCode);
        if (statusCode == 200) response.then().contentType(ContentType.JSON);
    }

    public static void checkStatusCode200(Response response) {
        checkStatusCode(response, 200);
    }

    public static void checkStatusCode400(final Response response) {
        checkStatusCode(response, 400);
    }

    public static void checkStatusCode401(final Response response) {
        checkStatusCode(response, 401);
    }

    public static void checkStatusCode403(final Response response) {
        checkStatusCode(response, 403);
    }

    public static void checkStatusCode404(final Response response) {
        checkStatusCode(response, 404);
    }

    public static void checkStatusCode500(final Response response) {
        checkStatusCode(response, 500);
    }

    public static void checkStatusGroup400(final Response response){
        Allure.step("Проверка statusCode в группе клиентских ошибок (400-499) для response");
        assertEquals(
                Math.abs(response.statusCode()/100),
                4,
                "\n" + response.statusLine() + "\n" + response.body().asString());
    }

    public static void checkStatusCode200or404(final Response response) {
        Allure.step("Проверка statusCode 200 или 404 для response");
        response.then().statusCode(anyOf(is(200), is(404)));
    }

    public static void checkStatusCode422(final Response response) {
        checkStatusCode(response, 422);
    }
}
