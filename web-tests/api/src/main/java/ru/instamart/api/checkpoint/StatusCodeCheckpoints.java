package ru.instamart.api.checkpoint;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import static org.hamcrest.Matchers.*;

@Slf4j
public class StatusCodeCheckpoints {

    @Step("Проверка на {statusCode} статус код и {contentType} тип контента ответа")
    public static void checkStatusCode(final Response response, final int statusCode, final ContentType contentType) {
        response.then().statusCode(statusCode);
        if (statusCode == 200) response.then().contentType(contentType);
    }

    @Step("Проверка на {statusCode} статус код и {contentType} тип контента ответа")
    public static void checkStatusCode(final Response response, final int statusCode, final String contentType) {
        response.then().statusCode(statusCode);
        if (statusCode == 200) response.then().contentType(contentType);
    }

    public static void checkStatusCode(final Response response, final int statusCode) {
        checkStatusCode(response, statusCode, ContentType.JSON);
    }

    public static void checkStatusCode200(final Response response) {
        checkStatusCode(response, 200);
    }

    public static void checkStatusCode302(final Response response) {
        checkStatusCode(response, 302);
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

    @Step("Проверка на статус код клиентской ошибки (400-499)")
    public static void checkStatusGroup400(final Response response){
        response.then().statusCode(both(greaterThan(399)).and(lessThan(500)));
    }

    @Step("Проверка на 200 или 404 статус код ответа")
    public static void checkStatusCode200or404(final Response response) {
        response.then().statusCode(anyOf(is(200), is(404)));
        if (response.statusCode() == 200) response.then().contentType(ContentType.JSON);
    }

    public static void checkStatusCode422(final Response response) {
        checkStatusCode(response, 422);
    }

    public static void checkContentTypeImage(final Response response) {
        checkStatusCode(response, 200, "image/");
    }
}
