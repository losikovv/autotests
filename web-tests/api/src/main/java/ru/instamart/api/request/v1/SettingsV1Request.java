package ru.instamart.api.request.v1;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.CompanySettingsV1;
import ru.instamart.api.model.v1.GeneralSettingsV1;
import ru.instamart.api.model.v1.SmsSettingsV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.api.model.v1.FeatureSettingsV1;

public class SettingsV1Request extends ApiV1RequestBase {

    public static class CompanySettings {
        @Step("{method} /" + ApiV1Endpoints.Admin.COMPANY_SETTINGS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.COMPANY_SETTINGS);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.COMPANY_SETTINGS)
        public static Response PATCH(CompanySettingsV1 companySettings) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(companySettings)
                    .patch(ApiV1Endpoints.Admin.COMPANY_SETTINGS);
        }
    }

    public static class FeatureSettings {
        @Step("{method} /" + ApiV1Endpoints.Admin.FEATURE_SETTINGS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.FEATURE_SETTINGS);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.FEATURE_SETTINGS)
        public static Response PATCH(FeatureSettingsV1 featureSettings) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(featureSettings)
                    .patch(ApiV1Endpoints.Admin.FEATURE_SETTINGS);
        }
    }

    public static class GeneralSettings {
        @Step("{method} /" + ApiV1Endpoints.Admin.GENERAL_SETTINGS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.GENERAL_SETTINGS);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.GENERAL_SETTINGS)
        public static Response PATCH(GeneralSettingsV1 generalSettings) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(generalSettings)
                    .patch(ApiV1Endpoints.Admin.GENERAL_SETTINGS);
        }
    }

    public static class LogisticDensities {
        @Step("{method} /" + ApiV1Endpoints.Admin.LOGISTIC_DENSITIES)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.LOGISTIC_DENSITIES);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.LOGISTIC_DENSITIES)
        public static Response PATCH(int density) {
            JSONObject body = new JSONObject();
            body.put("default_logistic_density", density);
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .patch(ApiV1Endpoints.Admin.LOGISTIC_DENSITIES);
        }
    }

    public static class SmsSettings {
        @Step("{method} /" + ApiV1Endpoints.Admin.SMS_SETTINGS)
        public static Response GET() {
            return givenWithAuth()
                    .get(ApiV1Endpoints.Admin.SMS_SETTINGS);
        }

        @Step("{method} /" + ApiV1Endpoints.Admin.SMS_SETTINGS)
        public static Response PATCH(SmsSettingsV1 smsSettings) {
            return givenWithAuth()
                    .contentType(ContentType.JSON)
                    .body(smsSettings)
                    .patch(ApiV1Endpoints.Admin.SMS_SETTINGS);
        }
    }
}
