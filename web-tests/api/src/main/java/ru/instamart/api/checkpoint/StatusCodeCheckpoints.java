package ru.instamart.api.checkpoint;

import io.qameta.allure.Allure;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static org.hamcrest.Matchers.*;

@Slf4j
public class StatusCodeCheckpoints {

    public static void checkStatusCode(final Response response, final int statusCode, final ContentType contentType) {
        checkStatusCode(response, statusCode, contentType.toString());
    }

    public static void checkStatusCode(final Response response, final int statusCode, final String contentType) {
        response.then().statusCode(statusCode);
        Allure.step("Проверка на " + statusCode + " статус код");

        if (statusCode == 302) {
            response.then().header("location", notNullValue());
            Allure.step("Проверка хедера location");
        }

        if (Objects.nonNull(contentType)) {
            response.then().contentType(contentType);
            Allure.step("Проверка на " + contentType + " тип контента");
        }
    }

    public static void checkStatusCode(final Response response, final int statusCode) {
        checkStatusCode(response, statusCode, (String) null);
    }

    /**
     * check 200 status code
     * check JSON content type (default for 200 responses)
     */
    public static void checkStatusCode200(final Response response) {
        checkStatusCode(response, 200, ContentType.JSON);
    }

    /**
     * check 302 status code
     * check HTML content type (default for 302 responses)
     * check Location header
     */
    public static void checkStatusCode302(final Response response) {
        checkStatusCode(response, 302, ContentType.HTML);
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

    public static void checkStatusGroup400(final Response response) {
        response.then().statusCode(both(greaterThan(399)).and(lessThan(500)));
        Allure.step("Проверка на статус код клиентской ошибки (400-499)");
    }

    public static void checkStatusCode200or404(final Response response) {
        response.then().statusCode(anyOf(is(200), is(404)));
        Allure.step("Проверка на 200 или 404 статус код");
        if (response.statusCode() == 200) {
            response.then().contentType(ContentType.JSON);
            Allure.step("Проверка на JSON тип контента");
        }
    }

    public static void checkStatusCode204or404(final Response response) {
        response.then().statusCode(anyOf(is(204), is(404)));
    }

    public static void checkStatusCode200or422(final Response response) {
        response.then().statusCode(anyOf(is(200), is(422)));
        Allure.step("Проверка на 200 или 422 статус код");
        if (response.statusCode() == 200) {
            response.then().contentType(ContentType.JSON);
            Allure.step("Проверка на JSON тип контента");
        }
    }

    public static void checkStatusCode422(final Response response) {
        checkStatusCode(response, 422);
    }

    public static void checkContentTypeImage(final Response response) {
        checkStatusCode(response, 200, "image/");
    }
}
