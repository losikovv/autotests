package ru.instamart.api.requests.v3;

import ru.instamart.api.endpoints.ApiV3Endpoints;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;

import static ru.instamart.api.requests.InstamartRequestsBase.givenCatch;

public class SetupInfoV3Request {

    /**
     * Cправочная информация об интеграции
     */
    @Step("{method} /" + ApiV3Endpoints.SETUP_INFO)
    public static Response GET() {

        JSONObject requestParams = new JSONObject();
        return givenCatch()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .header("Api-Version","3.0")
                .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                .get(ApiV3Endpoints.SETUP_INFO);
    }

    public static  class  Stores{
        /**
         * Доступные для ритейлера магазины
         */
        @Step("{method} /" + ApiV3Endpoints.SetupInfo.STORES)
        public static Response GET() {

            JSONObject requestParams = new JSONObject();
            return givenCatch()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .header("Api-Version","3.0")
                    .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                    .get(ApiV3Endpoints.SetupInfo.STORES);
        }
    }

}
