package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;

import static io.restassured.RestAssured.given;

public class RisExporterRequestBase {

    public static RequestSpecification givenWithSpec() {
        return given()
                .spec(Specification.INSTANCE.getRisExporterRequestSpec());
    }

    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                .header("Authorization","Bearer " + SessionFactory.getSession(SessionType.RIS_EXPORTER).getToken());
    }
}
