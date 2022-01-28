package ru.instamart.api.request;

import io.restassured.specification.RequestSpecification;
import ru.instamart.api.common.Specification;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.kraken.config.EnvironmentProperties;

import static io.restassured.RestAssured.given;

public class ShopperAppRequestBase {

    /**
     * Добавляем спеки к запросу
     */
    public static RequestSpecification givenWithSpec() {
        return EnvironmentProperties.SERVER.equals("production") ?
                given().spec(Specification.INSTANCE.getShopperAdminRequestSpec()) : //TODO: Убрать условие после B2C-7772
                given().spec(Specification.INSTANCE.getShopperRequestSpec());

    }

    /**
     * Добавляем хедер авторизации к запросу
     */
    public static RequestSpecification givenWithAuth() {
        return givenWithSpec()
                        .basePath(EnvironmentProperties.SERVER.equals("production") ?"":"shp-back/") //TODO: Убрать условие после B2C-7772
                        .header("Authorization",
                                "Bearer " + SessionFactory.getSession(SessionType.SHOPPER_APP).getToken());

    }
}
