package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.enums.v2.AuthProvider;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.SessionsResponse;
import instamart.core.testdata.Users;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.EnvironmentData;
import instamart.ui.common.pagesdata.UserData;
import io.qase.api.annotation.CaseId;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.ApiV2Checkpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

public class Sessions extends RestBase {
    private UserData user;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        user = Users.apiUser();
        apiV2.registration(user);
    }

    @CaseId(14)
    @Test(  dataProvider = "authProviders",
            dataProviderClass = RestDataProvider.class,
            description = "Авторизуемся через стороннего провайдера",
            groups = {"api-v2-smoke"})
    public void postAuthProvidersSessions(AuthProvider authProvider) {
        if (!EnvironmentData.INSTANCE.getServer().equalsIgnoreCase("production")) {
            throw new SkipException("Скипаем тесты не на проде");
        }
        response = ApiV2Requests.AuthProviders.Sessions.POST(authProvider);

        assertStatusCode200(response);
        assertNotNull(response.as(SessionsResponse.class).getSession());
    }

    @CaseId(110)
    @Test(  description = "Авторизуемся c Client-Id: InstamartApp (как мобильное приложение)",
            groups = {"api-v2-smoke"})
    public void postSessionsInstamartApp() {
        response = ApiV2Requests.Sessions.POST(user.getLogin(), user.getPassword(), "InstamartApp");
        assertStatusCode200(response, "Не работает авторизация с Client-Id: InstamartApp");
        assertNotNull(response.as(SessionsResponse.class).getSession());
    }

}
