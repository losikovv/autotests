package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

public class StoresV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.STORES)
    public static Response GET() {
        return givenWithSpec().get(ApiV1Endpoints.STORES);
    }

    @Step("{method} /" + ApiV1Endpoints.Stores.UUID)
    public static Response GET(String storeUuid) {
        return givenWithSpec().get(ApiV1Endpoints.Stores.UUID, storeUuid);
    }

    public static class Offers {
        @Step("{method} /" + ApiV1Endpoints.Stores.OFFERS)
        public static Response GET(String storeUuid, String offerName, String offerRetailerSku) {
            return givenWithSpec().get(ApiV1Endpoints.Stores.OFFERS, storeUuid, offerName, offerRetailerSku);
        }
    }
}
