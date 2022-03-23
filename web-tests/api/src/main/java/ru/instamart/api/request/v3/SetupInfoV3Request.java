package ru.instamart.api.request.v3;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV3Endpoints;
import ru.instamart.api.enums.v3.ClientV3;
import ru.instamart.api.request.ApiV3RequestBase;

public class SetupInfoV3Request extends ApiV3RequestBase {

    /**
     * Справочная информация об интеграции
     */
    @Step("{method} /" + ApiV3Endpoints.SETUP_INFO)
    public static Response GET() {

        JSONObject requestParams = new JSONObject();
        return givenWithAuth(ClientV3.SBER_DEVICES)
                .contentType(ContentType.JSON)
                .body(requestParams)
                .get(ApiV3Endpoints.SETUP_INFO);
    }

    public static  class  Stores {
        /**
         * Доступные для ритейлера магазины
         */
        @Step("{method} /" + ApiV3Endpoints.SetupInfo.STORES)
        public static Response GET() {

            JSONObject requestParams = new JSONObject();
            return givenWithAuth(ClientV3.SBER_DEVICES)
                    .contentType(ContentType.JSON)
                    .body(requestParams)
                    .get(ApiV3Endpoints.SetupInfo.STORES);
        }
    }
}
