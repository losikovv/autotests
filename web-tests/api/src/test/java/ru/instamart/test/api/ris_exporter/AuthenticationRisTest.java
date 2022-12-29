package ru.instamart.test.api.ris_exporter;

import io.qameta.allure.Epic;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.ris_exporter.AuthenticationRisRequest;
import ru.instamart.api.response.ris_exporter.TokenRisResponse;
import ru.instamart.kraken.data.user.UserManager;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("Аутентификация")
public class AuthenticationRisTest extends RestBase {

    @TmsLink("422")
    @Test(  groups = {"api-ris-exporter"},
            description = "Аутентификация")
    public void authentication200() {
        Response response = AuthenticationRisRequest.POST(UserManager.getRisUser().getToken());

        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(TokenRisResponse.class).getToken(),"token");
    }

    @TmsLink("423")
    @Test(  groups = {"api-ris-exporter"},
            description = "Аутентификация с неверными кредами")
    public void authentication401() {
        Response response = AuthenticationRisRequest.POST("ZGVsaXZlcnktY2x1Yi1pbnZhbGlkOmRlbGl2ZXJ5LWNsdWItcGFzc3dk");

        checkStatusCode401(response);
    }
}
