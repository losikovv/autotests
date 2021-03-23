package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class DepartmentsRequest {

    /**
     * Получение продуктов в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.DEPARTMENTS)
    public static Response GET(final int sid, final int numberOfProductsFromEachDepartment) {
        return givenCatch().get(ApiV2EndPoints.DEPARTMENTS, sid, numberOfProductsFromEachDepartment);
    }

    public static final class Id {
        @Step("{method} /" + ApiV2EndPoints.Departments.ID)
        public static Response GET(final int id, final int sid) {
            return givenCatch()
                    .get(ApiV2EndPoints.Departments.ID, id, sid);
        }
    }
}
