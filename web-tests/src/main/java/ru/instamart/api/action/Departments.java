package instamart.api.action;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class Departments {

    /**
     * Получение продуктов в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.DEPARTMENTS)
    public static Response GET(final int sid, final int numberOfProductsFromEachDepartment) {
        return givenCatch().get(ApiV2EndPoints.DEPARTMENTS, sid, numberOfProductsFromEachDepartment);
    }
}
