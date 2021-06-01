package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.testdata.JuridicalData;

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
        JSONObject requestParams = new JSONObject();
        requestParams.put("inn", companyData.getInn());
        requestParams.put("name", companyData.getJuridicalName());
        return givenWithAuth()
                .body(requestParams)
                .contentType(ContentType.JSON)
                .post(ApiV1Endpoints.User.COMPANIES);
    }
}
