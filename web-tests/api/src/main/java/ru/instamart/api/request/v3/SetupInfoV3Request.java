package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.request.ApiV3RequestBase;

public class SetupInfoV3Request extends ApiV3RequestBase {

    /**
     * Справочная информация об интеграции
     */
    @Step("{method} /" + ApiV3Endpoints.SETUP_INFO)
    public static Response GET() {

        JSONObject requestParams = new JSONObject();
        return givenWithSpec()
                .contentType(ContentType.JSON)
                .body(requestParams)
                .header("Api-Version","3.0")
                .header("Client-Token","14cd5d341d768bd4926fc9f5ce262094")
                .get(ApiV3Endpoints.SETUP_INFO);
    }

    public static  class  Stores {
        /**
         * Доступные для ритейлера магазины
         */
        @Step("{method} /" + ApiV3Endpoints.SetupInfo.STORES)
        public static Response GET() {

            JSONObject requestParams = new JSONObject();
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .get(ApiV3Endpoints.SetupInfo.STORES);
        }
    }
}
