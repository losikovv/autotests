package ru.instamart.api.request.v1.b2b;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.SkipException;
import ru.instamart.api.endpoint.ApiV1Endpoints;
import ru.instamart.api.model.v1.b2b.UserV1;
import ru.instamart.api.request.ApiV1RequestBase;
import ru.instamart.kraken.config.EnvironmentProperties;

@SuppressWarnings("unchecked")
public class CompanyEmployeesV1Request extends ApiV1RequestBase {

    @Step("{method} /" + ApiV1Endpoints.COMPANY_EMPLOYEES)
    public static Response POST(Integer companyID, UserV1 user) {
        if (!EnvironmentProperties.SERVER.equals("production")) {
            JSONObject body = new JSONObject();
            JSONObject requestParams = new JSONObject();
            requestParams.put("user_id", user.getId());
            requestParams.put("company_id", companyID);
            body.put("employee", requestParams);
            return givenWithAuth()
                    .body(body)
                    .contentType(ContentType.JSON)
                    .post(ApiV1Endpoints.COMPANY_EMPLOYEES);
        } else {
            throw new SkipException("Пользователь создан только на кракене");
        }
    }

    @Step("{method} /" + ApiV1Endpoints.CompanyEmployees.BY_ID)
    public static Response PUT(Integer employeeID, boolean isApproved) {
            JSONObject body = new JSONObject();
            JSONObject requestParams = new JSONObject();
            requestParams.put("approved", isApproved);
            body.put("employee", requestParams);
            return givenWithAuth()
                    .body(body)
                    .contentType(ContentType.JSON)
                    .put(ApiV1Endpoints.CompanyEmployees.BY_ID, employeeID);
    }

    @Step("{method} /" + ApiV1Endpoints.CompanyEmployees.BY_ID)
    public static Response DELETE(Integer employeeID) {
        return givenWithAuth()
                .delete(ApiV1Endpoints.CompanyEmployees.BY_ID, employeeID);
    }
}
