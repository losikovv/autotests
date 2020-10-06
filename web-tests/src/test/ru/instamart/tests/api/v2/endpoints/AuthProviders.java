package ru.instamart.tests.api.v2.endpoints;

import instamart.api.v2.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.v2.responses.SessionsResponse;
import instamart.core.common.AppManager;
import instamart.core.testdata.dataprovider.RestDataProvider;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class AuthProviders extends RestBase {

    @Test(  dataProvider = "authProviders",
            dataProviderClass = RestDataProvider.class,
            description = "Авторизуемся через стороннего провайдера",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 14)
    public void postAuthProvidersSessions(String authProviderId) {
        if (AppManager.environment.getServer().equalsIgnoreCase("staging")) {
            throw new SkipException("staging");
        }
        response = ApiV2Requests.postAuthProvidersSessions(authProviderId);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(SessionsResponse.class).getSession());
    }

}
