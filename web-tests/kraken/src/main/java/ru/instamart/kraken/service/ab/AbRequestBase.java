package ru.instamart.kraken.service.ab;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.instamart.kraken.service.ab.request.ObtainJwtRequest;
import ru.instamart.kraken.service.ab.response.JwtResponse;

import static io.restassured.RestAssured.given;
import static java.util.Objects.isNull;

@Slf4j
public abstract class AbRequestBase {

    private static final RequestSpecification abService = new RequestSpecBuilder()
                .setBaseUri(CoreProperties.AB_SERVICE_URL)
                .setBasePath("/api/v1")
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();

    private static String token = null;

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization", "JWT " + (isNull(token) ? createAbSession(UserManager.getDefaultAbUser()) : token));
    }

    public static RequestSpecification givenWithSpec() {
        return given().spec(abService);
    }

    private static String createAbSession(final UserData userData) {
        log.debug("Авторизуемся в АБ сервисе пользователем {}", userData);
        final var response = ObtainJwtRequest.POST(userData);
        response.then().statusCode(200);
        final var jwt = response.as(JwtResponse.class);
        token = jwt.getAccess();
        log.debug("Получаем jwt '{}'", token);
        return token;
    }
}
