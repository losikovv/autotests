package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.config.EnvironmentProperties;

import static io.restassured.RestAssured.given;

@Slf4j
public class ApiV2RequestBase {
    /**
     * Добавляем хедер авторизации к запросу
     */
    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header(
                        "Authorization",
                        "Token token=" + SessionFactory.getSession(EnvironmentProperties.SERVER.equals("production") ? SessionType.PROD
                                : SessionType.API_V2).getToken());
    }

    /**
     * Авторизация с кастомным токеном, для случаев когда нужна проверка на невалидный токен
     *
     * @param token
     * @return
     */
    public static RequestSpecification givenCustomToken(final String token) {
        return givenWithSpec()
                .header(
                        "Authorization",
                        "Token token=" + token);
    }

    /**
     * Добавляем спеки к запросу
     */
    public static RequestSpecification givenWithSpec() {
        return EnvironmentProperties.SERVER.equals("production") ?
                given()
                        .spec(Specification.INSTANCE.getProdRequestSpec()):
                given()
                        .spec(Specification.INSTANCE.getApiV2RequestSpec());
    }
}
