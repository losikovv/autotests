package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.b2b.SalesContractV1;
import ru.instamart.api.request.ApiV1RequestBase;

@SuppressWarnings("unchecked")
public class CompanySalesContractV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.COMPANY_SALES_CONTRACT)
    public static Response POST(Integer companyID, Integer contractNumber, String signingDate) {
        JSONObject body = new JSONObject();
        JSONObject requestParams = new JSONObject();
        requestParams.put("number", contractNumber);
        requestParams.put("signing_date", signingDate);
        requestParams.put("company_id", companyID);
        body.put("sales_contract", requestParams);
        return givenWithAuth()
                .body(body)
                .contentType(ContentType.JSON)
                .post(ApiV1Endpoints.COMPANY_SALES_CONTRACT);

    }

    @Step("{method} /" + ApiV1Endpoints.COMPANY_SALES_CONTRACT)
    public static Response POST(Integer companyID, Integer contractNumber) {
        JSONObject body = new JSONObject();
        JSONObject requestParams = new JSONObject();
        requestParams.put("number", contractNumber);
        requestParams.put("company_id", companyID);
        body.put("sales_contract", requestParams);
        return givenWithAuth()
                .body(body)
                .contentType(ContentType.JSON)
                .post(ApiV1Endpoints.COMPANY_SALES_CONTRACT);

    }

    @Step("{method} /" + ApiV1Endpoints.CompanySalesContracts.ARCHIVE)
    public static Response POST(Integer contractID) {
        return givenWithAuth()
                .post(ApiV1Endpoints.CompanySalesContracts.ARCHIVE, contractID);
    }

    @Step("{method} /" + ApiV1Endpoints.CompanySalesContracts.BY_ID)
    public static Response PUT(Integer companyID, SalesContractV1 salesContractV1) {
        JSONObject body = new JSONObject();
        JSONObject requestParams = new JSONObject();
        requestParams.put("number", salesContractV1.getNumber());
        requestParams.put("signing_date", salesContractV1.getSigningDate());
        requestParams.put("company_id", companyID);
        body.put("sales_contract", requestParams);
        return givenWithAuth()
                .body(body)
                .contentType(ContentType.JSON)
                .put(ApiV1Endpoints.CompanySalesContracts.BY_ID, salesContractV1.getId());
    }

}
