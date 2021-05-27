package ru.instamart.test.api.v1.endpoints;

import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.b2b.LegalEntityV1Request;
import ru.instamart.api.response.v1.b2b.LegalEntityV1Response;
import ru.instamart.kraken.testdata.UserManager;

import static org.junit.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

public class LegalEntityV1Tests extends RestBase {
    @BeforeMethod
    public void preconditions() {
    }

    @Test(groups = {"api-instamart-regress"})
    public void getWithoutLegalEntity() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());

        Response response = LegalEntityV1Request.GET("123456789");
        checkStatusCode200(response);
        assertNull(response.as(LegalEntityV1Response.class).getLegalEntity());
    }
}
