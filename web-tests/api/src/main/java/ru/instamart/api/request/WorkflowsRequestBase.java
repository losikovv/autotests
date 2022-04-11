package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;

import static io.restassured.RestAssured.given;

public class WorkflowsRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getShopperRequestSpec());

    }

    /**
     * Добавляем хедер авторизации к запросу
     */
    public static RequestSpecification givenWithAuth() {
        return givenWithSpec().log().all()
                .basePath("workflows/")
                .header("Authorization",
                        "Bearer " + SessionFactory.getSession(SessionType.SHOPPER_APP).getToken());

    }
}
