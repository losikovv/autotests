package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.request.v1.b2b.UserCompanyEmployeesV1Request;
import ru.instamart.api.response.v1.b2b.CompanyV1Response;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.kraken.data.JuridicalData;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class UserCompanyEmployeesV1Tests extends RestBase {

    private final Juridical companyData = JuridicalData.juridical();
    private CompanyV1 company;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
        final Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode200(response);
        company = response.as(CompanyV1Response.class).getCompany();
    }


    @Story("Web")
    @TmsLink("1263")
    @Test(description = "Выход пользователя из компании",
            groups = {API_INSTAMART_REGRESS, "api-v1"})
    public void deleteUserFromCompany() {
        final Response response = UserCompanyEmployeesV1Request.DELETE(company.getId().toString());
        checkStatusCode200(response);
    }
}
