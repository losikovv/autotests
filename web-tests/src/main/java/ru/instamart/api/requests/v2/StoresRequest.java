package instamart.api.requests.v2;

import instamart.api.endpoints.ApiV2EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static instamart.api.requests.InstamartRequestsBase.givenCatch;

public final class StoresRequest {

    /**
     * Получаем список доступных магазинов по координатам
     */
    @Step("{method} /" + ApiV2EndPoints.Stores.COORDINATES)
    public static Response GET(double lat, double lon) {
        return givenCatch().get(ApiV2EndPoints.Stores.COORDINATES, lat, lon);
    }

    /**
     * Получаем данные о конкретном магазине
     */
    @Step("{method} /" + ApiV2EndPoints.Stores.SID)
    public static Response GET(int sid) {
        return givenCatch().get(ApiV2EndPoints.Stores.SID, sid);
    }

    public static class PromotionCards {
        /**
         * Получаем промоакции в магазине
         */
        @Step
        public static Response GET(int sid) {
            return givenCatch().get(ApiV2EndPoints.Stores.PROMOTION_CARDS, sid);
        }
    }
}
