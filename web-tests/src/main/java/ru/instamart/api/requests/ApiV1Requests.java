package instamart.api.requests;

import instamart.api.endpoints.ApiV1Endpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

/**
 * МЕТОДЫ ЗАПРОСОВ SPREE REST API (сайт)
 */
public class ApiV1Requests extends InstamartRequestsBase {

    public static class Retailers {
        /**
         * Получаем список всех доступных ритейлеров (spree api)
         */
        @Step
        public static Response GET() {
            return givenCatch().get(ApiV1Endpoints.RETAILERS);
        }

        public static class Stores {
            /**
             * Получаем список всех доступных магазинов у ритейлера (spree api)
             */
            @Step
            public static Response GET(int retailerId) {
                return givenCatch().get(ApiV1Endpoints.Retailers.STORES, retailerId);
            }
        }
    }

}
