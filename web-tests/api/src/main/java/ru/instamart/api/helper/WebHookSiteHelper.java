package ru.instamart.api.helper;

import io.restassured.response.Response;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.instamart.api.request.webhook_site.TokenRequest;
import ru.instamart.api.request.webhook_site.WebhookSiteRequests;
import ru.instamart.api.response.webhook_site.RequestsResponse;
import ru.instamart.api.response.webhook_site.TokenResponse;
import ru.instamart.kraken.config.CoreProperties;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;

@Slf4j
public class WebHookSiteHelper {
    private static volatile WebHookSiteHelper INSTANCE;
    @Getter
    private String token;

    private WebHookSiteHelper() {
        this.token = createWebhookSiteSession();
    }

    public static WebHookSiteHelper getInstance() {
        WebHookSiteHelper RESULT = INSTANCE;
        if (RESULT != null) {
            return INSTANCE;
        }
        synchronized (WebHookSiteHelper.class) {
            if (INSTANCE == null) {
                INSTANCE = new WebHookSiteHelper();
            }
            return INSTANCE;
        }
    }

    private static String createWebhookSiteSession() {
        final Response response = TokenRequest.POST();
        checkStatusCode(response, 201);
        TokenResponse tokenResponse = response.as(TokenResponse.class);
        log.debug("Получен токен: {}", tokenResponse);
        return tokenResponse.getUuid();
    }

    public TokenResponse updateServiceConfig(TokenRequest.TokenRequestData params) {
        final Response response = TokenRequest.PUT(token, params);
        checkStatusCode(response, 200);
        return response.as(TokenResponse.class);
    }


    public RequestsResponse getAllRequest(WebhookSiteRequests.RequestParams params) {
        final Response response = WebhookSiteRequests.GET(token, params);
        return response.as(RequestsResponse.class);
    }

    public String getSite() {
        return CoreProperties.DEFAULT_WEBHOOK_SITE_URL + "/" + token;
    }


}
