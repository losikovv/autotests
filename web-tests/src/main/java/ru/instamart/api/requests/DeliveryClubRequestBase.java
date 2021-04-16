package ru.instamart.api.requests;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.SessionFactory;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;

import static io.restassured.RestAssured.given;

public class DeliveryClubRequestBase {
    /**
     * Добавляем хедер авторизации к запросу деливери клаб
     */
    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization",
                        "Bearer " + SessionFactory.getSession(SessionType.DELIVERY_CLUB).getToken());
    }

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getApiV2RequestSpec());
    }
}
