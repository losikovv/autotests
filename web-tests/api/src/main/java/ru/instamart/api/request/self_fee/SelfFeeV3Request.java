package ru.instamart.api.request.self_fee;

import io.qameta.allure.Step;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.entity.mime.HttpMultipartMode;
import ru.instamart.api.endpoint.SelfFreeEndpoints;
import ru.instamart.api.request.SelfFeeRequestBase;

import java.io.File;
import java.util.Map;

public class SelfFeeV3Request extends SelfFeeRequestBase {

    @Step("{method} /" + SelfFreeEndpoints.V3.REGISTRY)
    public static Response GET(final int offset, final boolean history) {
        return givenWithAuth()
                .queryParam("offset", offset)
                .queryParam("history", history)
                .get(SelfFreeEndpoints.V3.REGISTRY);
    }

    public static class Upload {
        @Step("{method} /" + SelfFreeEndpoints.V3.UPLOAD)
        public static Response POST(Map<String, String> files) {
            RequestSpecification requestSpecification = givenWithAuth()
                    .config(RestAssuredConfig.config()
                            .httpClient(HttpClientConfig.httpClientConfig()
                                    .httpMultipartMode(HttpMultipartMode.BROWSER_COMPATIBLE)))
                    .contentType("multipart/form-data; charset=UTF-8");
            files.forEach((k, v) -> requestSpecification.multiPart(k, new File(v), "multipart/form-data"));

            return requestSpecification
                    .post(SelfFreeEndpoints.V3.UPLOAD);
        }

        @Step("{method} /" + SelfFreeEndpoints.V3.Upload.BY_ID)
        public static Response GET(final long id) {
            return givenWithAuth()
                    .get(SelfFreeEndpoints.V3.Upload.BY_ID, id);
        }
    }

    public static class Registry {
        @Step("{method} /" + SelfFreeEndpoints.V3.Registry.RECEIPT)
        public static Response GET(final long id, final boolean withErrors) {
            return givenWithAuth()
                    .queryParam("with_errors", withErrors)
                    .get(SelfFreeEndpoints.V3.Registry.RECEIPT, id);
        }
    }

}
