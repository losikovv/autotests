package ru.instamart.api.request.ris_exporter;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.instamart.api.endpoint.RisExporterEndpoints;
import ru.instamart.api.request.RisExporterRequestBase;

public class AuthenticationRisRequest extends RisExporterRequestBase {

    @Step("{method} /" + RisExporterEndpoints.AUTHENTICATION_TOKEN)
    public static Response POST(final String base64) {
        return givenWithSpec()
                .header("Authorization", base64)
                .post(RisExporterEndpoints.AUTHENTICATION_TOKEN);
    }
}
