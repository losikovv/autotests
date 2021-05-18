package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class RetailersV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.RETAILERS)
    public static Response GET() {
        return givenWithSpec().get(ApiV1Endpoints.RETAILERS);
    }

    @Step("{method} /" + ApiV1Endpoints.Retailers.ID)
    public static Response GET(int retailerId) {
        return givenWithSpec().get(ApiV1Endpoints.Retailers.ID, retailerId);
    }

    public static class Stores {
        @Step("{method} /" + ApiV1Endpoints.Retailers.STORES)
        public static Response GET(int retailerId) {
            return givenWithSpec().get(ApiV1Endpoints.Retailers.STORES, retailerId);
        }
    }

    public static class Eans {
        @Step("{method} /" + ApiV1Endpoints.Retailers.EANS)
        public static Response GET(int retailerId) {
            return givenWithSpec().get(ApiV1Endpoints.Retailers.EANS, retailerId);
        }
    }
}
