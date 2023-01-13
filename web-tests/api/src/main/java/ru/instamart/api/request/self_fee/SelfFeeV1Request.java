package ru.instamart.api.request.self_fee;

import io.qameta.allure.Step;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.json.JSONObject;
import ru.instamart.api.endpoint.SelfFreeEndpoints;
import ru.instamart.api.request.SelfFeeRequestBase;

public class SelfFeeV1Request extends SelfFeeRequestBase {


    public static class Registry {

        @Step("{method} /" + SelfFreeEndpoints.V1.REGISTRY)
        public static Response GET() {
            return givenWithAuth()
                    .get(SelfFreeEndpoints.V1.REGISTRY);
        }

        @Step("{method} /" + SelfFreeEndpoints.V1.REGISTRY)
        public static Response GET(final int offset, final boolean history) {
            return givenWithAuth()
                    .queryParam("offset", offset)
                    .queryParam("history", history)
                    .get(SelfFreeEndpoints.V1.REGISTRY);
        }

        public static class Receipt {
            @Step("{method} /" + SelfFreeEndpoints.V1.Registry.RECEIPT)
            public static Response GET(final Integer id, final boolean fiscalized) {
                return givenWithAuth()
                        .queryParam("fiscalized", fiscalized)
                        .get(SelfFreeEndpoints.V1.Registry.RECEIPT, id);
            }

            @Step("{method} /" + SelfFreeEndpoints.V1.Registry.RECEIPT)
            public static Response HEAD(final Integer id, final boolean fiscalized) {
                return givenWithAuth()
                        .queryParam("fiscalized", fiscalized)
                        .head(SelfFreeEndpoints.V1.Registry.RECEIPT, id);
            }
        }

        public static class Cancellation {
            @Step("{method} /" + SelfFreeEndpoints.V1.Registry.CANCELLATION)
            public static Response POST(final Integer id, final String uuid) {
                final var json = new JSONObject();
                json.put("file_id", uuid);
                return givenWithAuth()
                        .body(json)
                        .post(SelfFreeEndpoints.V1.Registry.CANCELLATION, id);
            }
        }

        public static class Fiscalize {
            @Step("{method} /" + SelfFreeEndpoints.V1.Registry.FISCALIZE)
            public static Response POST(final int id) {
                return givenWithAuth()
                        .post(SelfFreeEndpoints.V1.Registry.FISCALIZE, id);
            }
        }

        public static class Payroll {
            @Step("{method} /" + SelfFreeEndpoints.V1.Registry.Payroll.SBER)
            public static Response GET(final int id) {
                return givenWithAuth()
                        .get(SelfFreeEndpoints.V1.Registry.Payroll.SBER, id);
            }
        }
    }

    public static class File {
        @Step("{method} /" + SelfFreeEndpoints.V1.File.BY_ID)
        public static Response GET(final String uuid) {
            return givenWithAuth()
                    .get(SelfFreeEndpoints.V1.File.BY_ID, uuid);
        }

        @Step("{method} /" + SelfFreeEndpoints.V1.File.BY_ID)
        public static Response POST(final String file) {
            return givenWithAuth()
                    .config(RestAssuredConfig.config()
                            .httpClient(HttpClientConfig.httpClientConfig()
                                    .httpMultipartMode(HttpMultipartMode.BROWSER_COMPATIBLE)))
                    .contentType("multipart/form-data; charset=UTF-8")
                    .multiPart("file", new java.io.File(file), "multipart/form-data")
                    .get(SelfFreeEndpoints.V1.File.BY_ID);
        }
    }
}
