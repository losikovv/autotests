package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.request.ApiV1RequestBase;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CompaniesV1Request extends ApiV1RequestBase {
    @Step("{method} /" + ApiV1Endpoints.Company.BY_INN)
    public static Response GET(String inn) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Company.BY_INN, inn);
    }

    @Step("{method} /" + ApiV1Endpoints.Company.BY_ID)
    public static Response GET(Integer companyID) {
        return givenWithAuth()
                .get(ApiV1Endpoints.Company.BY_ID, companyID);
    }

    @Step("{method} /" + ApiV1Endpoints.COMPANIES)
    public static Response POST(final String inn, final String companyName, final String ownerEmail) {
        final Map<String, String> params = new HashMap<>();
        params.put("inn", inn);
        params.put("name", companyName);
        params.put("owner_email", ownerEmail);
        return givenWithAuth()
                .body(params)
                .contentType(ContentType.JSON)
                .post(ApiV1Endpoints.COMPANIES);
    }

    @Step("{method} /" + ApiV1Endpoints.Company.BY_ID)
    public static Response PUT(Integer companyID, CompanyV1 companyV1) {
        JSONObject company = new JSONObject();
        JSONObject body = formJsonBodyForPUT(companyID,companyV1);
        company.put("company", body);
        return givenWithAuth()
                .body(company)
                .contentType(ContentType.JSON)
                .put(ApiV1Endpoints.Company.BY_ID, companyID);

    }

    private static JSONObject formJsonBodyForPUT(Integer companyID, CompanyV1 companyV1){
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", companyID);
         if (companyV1.getName() != null){ requestParams.put("name", companyV1.getName());}
         if (companyV1.getLinkToCrm() != null){ requestParams.put("link_to_crm", companyV1.getLinkToCrm());}
         if (companyV1.getPostpay() != null){ requestParams.put("postpay", companyV1.getPostpay());}
         if (companyV1.getPrepay() != null){ requestParams.put("prepay", companyV1.getPrepay());}
         if (companyV1.getDeposit() != null){ requestParams.put("deposit", companyV1.getDeposit());}
         if (companyV1.getManagerComment() != null){ requestParams.put("manager_comment", companyV1.getManagerComment());}
        return requestParams;
    }
}
