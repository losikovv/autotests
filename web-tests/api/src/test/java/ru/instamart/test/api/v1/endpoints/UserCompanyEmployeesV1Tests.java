package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionProvider;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v1.b2b.CompanyV1;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.request.v1.b2b.UserCompanyEmployeesV1Request;
import ru.instamart.api.response.v1.b2b.CompanyV1Response;
import ru.instamart.kraken.data.Juridical;
import ru.instamart.kraken.data.JuridicalData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("B2B endpoints")
public class UserCompanyEmployeesV1Tests extends RestBase {

    private final Juridical companyData = JuridicalData.juridical();
    private CompanyV1 company;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authAdminApi();
        final Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode200(response);
        company = response.as(CompanyV1Response.class).getCompany();
    }


    @Story("Web")
    @CaseId(1263)
    @Test(description = "Выход пользователя из компании",
            groups = {"api-instamart-regress"})
    public void deleteUserFromCompany() {
        final Response response = UserCompanyEmployeesV1Request.DELETE(company.getId().toString());
        checkStatusCode200(response);
    }
}
