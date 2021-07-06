package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.SkipException;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.b2b.ManagerV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

@SuppressWarnings("unchecked")
public class CompanyManagersV1Request extends ApiV1RequestBase {


    @Step("{method} /" + ApiV1Endpoints.COMPANY_MANAGERS)
    public static Response POST(Integer companyID, ManagerV1 manager) {
        if (EnvironmentData.INSTANCE.getBasicUrl().equals("https://stf-kraken.k-stage.sbermarket.tech/")) {
            JSONObject body = new JSONObject();
            JSONObject requestParams = new JSONObject();
            requestParams.put("user_id", manager.getUser().getId());
            requestParams.put("company_id", companyID);
            body.put("manager", requestParams);
            return givenWithAuth()
                    .body(body)
                    .contentType(ContentType.JSON)
                    .post(ApiV1Endpoints.COMPANY_MANAGERS);
        } else {
            throw new SkipException("Менеджер создан только на кракене");
        }
    }


    @Step("{method} /" + ApiV1Endpoints.CompanyManagers.BY_ID)
    public static Response DELETE(Integer managerID) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.CompanyManagers.BY_ID, managerID);

    }

}
