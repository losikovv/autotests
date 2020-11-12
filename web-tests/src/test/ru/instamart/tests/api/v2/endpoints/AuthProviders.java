package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.SessionsResponse;
import instamart.core.common.AppManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class AuthProviders extends RestBase {

    @CaseId(14)
    @Test(  dataProvider = "authProviders",
            dataProviderClass = RestDataProvider.class,
            description = "Авторизуемся через стороннего провайдера",
            groups = {"api-v2-smoke"})
    public void postAuthProvidersSessions(String authProviderId) {
        if (AppManager.environment.getServer().equalsIgnoreCase("staging")) {
            throw new SkipException("Скипаем тесты на стэйдже");
        }
        response = ApiV2Requests.postAuthProvidersSessions(authProviderId);

        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(SessionsResponse.class).getSession());
    }

}
