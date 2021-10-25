package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.SkipException;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.JuridicalData;

@SuppressWarnings("unchecked")
public class UserCompaniesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.User.COMPANIES)
    public static Response GET() {
        return givenWithAuth()
                .get(ApiV1Endpoints.User.COMPANIES);
    }

    @Step("{method} /" + ApiV1Endpoints.User.Company.BY_ID)
    public static Response GET(String companyID) {
        return givenWithAuth()
                .get(ApiV1Endpoints.User.Company.BY_ID, companyID);
    }

    @Step("{method} /" + ApiV1Endpoints.User.COMPANIES)
    public static Response POST(JuridicalData companyData) {
        if (EnvironmentProperties.SERVER.equals("production")) {
            throw new SkipException("Не создаём компании на проде");
        } else {
            JSONObject requestParams = new JSONObject();
            requestParams.put("inn", companyData.getInn());
            requestParams.put("name", companyData.getJuridicalName());
            return givenWithAuth()
                    .body(requestParams)
                    .contentType(ContentType.JSON)
                    .post(ApiV1Endpoints.User.COMPANIES);
        }
    }

    public static class PaymentAccount {

        @Step("{method} /" + ApiV1Endpoints.User.Company.PAYMENT_ACCOUNT)
        public static Response GET(String companyID) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.User.Company.PAYMENT_ACCOUNT, companyID);
        }

        @Step("{method} /" + ApiV1Endpoints.User.Company.PaymentAccount.REFRESH)
        public static Response POST(String companyID) {
            return givenWithAuth()
                    .post(ApiV1Endpoints.User.Company.PaymentAccount.REFRESH, companyID);
        }
    }

    public static class Employees {

        @Step("{method} /" + ApiV1Endpoints.User.Company.EMPLOYEES)
        public static Response GET(String companyID) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.User.Company.EMPLOYEES, companyID);
        }
    }

    public static class Manager {

        @Step("{method} /" + ApiV1Endpoints.User.Company.MANAGER)
        public static Response GET(String companyID) {
            return givenWithAuth()
                    .get(ApiV1Endpoints.User.Company.MANAGER, companyID);
        }
    }
}
