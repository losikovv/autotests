package ru.instamart.test.api.v1.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.b2b.CompanyPresenceV1Request;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.kraken.testdata.UserManager;

import static org.junit.Assert.assertEquals;

import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

public class CompanyPresenceV1Tests extends RestBase {
    @BeforeMethod
    public void preconditions() {
    }

    @Test(groups = {"api-instamart-regress"})
    public void getCompanyNotPresence() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());

        Response response = CompanyPresenceV1Request.GET("123456789");
        checkStatusCode404(response);
        assertEquals("Объект не найден", response.as(ErrorResponse.class).getError());
    }
}

