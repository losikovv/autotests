package ru.instamart.test.api.v1.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.b2b.CompanyManagerV1Request;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.api.response.v1.b2b.CompanyByIDV1Response;
import ru.instamart.api.response.v1.b2b.CompanyManagerV1Response;
import ru.instamart.kraken.testdata.JuridicalData;
import ru.instamart.kraken.testdata.UserManager;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class UserCompaniesV1Tests extends RestBase {

    JuridicalData companyData = UserManager.juridical();

    @BeforeMethod
    public void preconditions() {
    }

    @Test(groups = {"api-instamart-regress"})
    public void postCreateCompany(){
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        Response response = UserCompaniesV1Request.POST(companyData);
        checkStatusCode200(response);
        assertEquals(response.as(CompanyByIDV1Response.class).getCompany().getInn(), companyData.getInn());

    }

    @Test(groups = {"api-instamart-regress"})
    public void getUserCompanies() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());

        Response response = UserCompaniesV1Request.GET();
        checkStatusCode200(response);
        assertFalse(response.as(CompaniesV1Response.class).getCompanies().isEmpty());
    }

    @Test(groups = {"api-instamart-regress"})
    public void getCompanyByID(){
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        Response response = UserCompaniesV1Request.GET("1303");
        checkStatusCode200(response);
        assertEquals(Integer.valueOf(1303), response.as(CompanyByIDV1Response.class).getCompany().getId(), "id компании не совпадает с запрошенным");
    }
    @Test(groups = {"api-instamart-regress"})
    public void getCompanyWithoutManager(){
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        Response response = CompanyManagerV1Request.GET("1303");
        checkStatusCode200(response);
        assertNull(response.as(CompanyManagerV1Response.class).getManager());
    }
}
