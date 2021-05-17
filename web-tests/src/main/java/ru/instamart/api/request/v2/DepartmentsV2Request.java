package ru.instamart.api.request.v2;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV2EndPoints;
import ru.instamart.api.request.ApiV2RequestBase;

public final class DepartmentsV2Request extends ApiV2RequestBase {

    /**
     * Получение продуктов в выбранном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.DEPARTMENTS)
    public static Response GET(final int sid, final int numberOfProductsFromEachDepartment) {
        return givenWithSpec().get(ApiV2EndPoints.DEPARTMENTS, sid, numberOfProductsFromEachDepartment);
    }

    public static final class Id {
        @Step("{method} /" + ApiV2EndPoints.Departments.BY_ID)
        public static Response GET(final int id, final int sid) {
            return givenWithSpec()
                    .get(ApiV2EndPoints.Departments.BY_ID, id, sid);
        }
    }
}
