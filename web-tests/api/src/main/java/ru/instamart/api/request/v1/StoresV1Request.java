package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;

import static ru.instamart.kraken.util.TimeUtil.*;

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

    public static class DeliveryWindows {
        @Step("{method} /" + ApiV1Endpoints.Stores.DeliveryWindows.BY_DATE)
        public static Response GET(Integer storeId) {
            String date = getFutureDateWithoutTime(1L);
            return givenWithAuth()
                    .get(ApiV1Endpoints.Stores.DeliveryWindows.BY_DATE, storeId, date);
        }


        @Step("{method} /" + ApiV1Endpoints.Stores.DeliveryWindows.GENERATE)
        public static Response POST(Integer storeId) {
            JSONObject body = new JSONObject();
            body.put("starting_date", getZonedDate());
            body.put("ending_date", getZonedFutureDate(6L));
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .post(ApiV1Endpoints.Stores.DeliveryWindows.GENERATE, storeId);
        }
    }
}
