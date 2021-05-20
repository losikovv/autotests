package ru.instamart.test.api.v1.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.b2b.UserCompaniesV1Request;
import ru.instamart.api.response.v1.b2b.CompaniesV1Response;
import ru.instamart.kraken.testdata.UserManager;

import static org.junit.Assert.assertFalse;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class UserCompaniesV1Tests extends RestBase {

    @BeforeMethod
    public void preconditions() {
    }

    @Test(groups = {"api-instamart-regress"})
    public void getUserCompanies() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());

        Response response = UserCompaniesV1Request.GET();
        checkStatusCode200(response);
        assertFalse(response.as(CompaniesV1Response.class).getCompanies().isEmpty());
    }
}
