package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v2.AuthProviderV2;
import ru.instamart.api.request.v2.AuthProvidersV2Request;
import ru.instamart.api.response.v2.SessionsV2Response;

import java.util.UUID;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic(value = "ApiV2")
@Feature(value = "Авторизация")
public final class AuthProviderV2Test extends RestBase {
    String uid1 = UUID.randomUUID().toString();
    String uid2 = UUID.randomUUID().toString();
    String uid3 = UUID.randomUUID().toString();
    String uid4 = UUID.randomUUID().toString();

    @CaseId(168)
    @Test(dataProvider = "authProviders",
            dataProviderClass = RestDataProvider.class,
            groups = {"api-instamart-prod"},
            description = "Авторизуемся через стороннего провайдера")
    public void postAuthProvidersSessions200(final AuthProviderV2 authProvider) {
        final Response response = AuthProvidersV2Request.Sessions.POST(authProvider);
        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(SessionsV2Response.class).getSession(), "сессия");
    }

    @CaseId(168)
    @Test(groups = {"api-instamart-regress"},
            description = "Авторизуемся через стороннего провайдера")
    public void postAuthProviderSessions200() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, SessionsV2Response.class);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через несуществующего стороннего провайдера")
    public void postAuthProviderSessions404WrongProviderId() {
        AuthProviderV2 authProvider = AuthProviderV2.WRONG_ID;
        final Response response = AuthProvidersV2Request.Sessions.POST(authProvider);
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с пустым session uid")
    public void postAuthProviderSessions404EmptySessionId() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK, "");
        checkStatusCode422(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с пустым именем, при первом входе")
    public void postAuthProviderSessions200EmptyFirstName1() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                "", null, null, null, uid1);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с пустым именем, при втором входе",
            dependsOnMethods = "postAuthProviderSessions200EmptyFirstName1")
    public void postAuthProviderSessions200EmptyFirstName2() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                "", null, null, null, uid1);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с пустой фамилией, при первом входе")
    public void postAuthProviderSessions200EmptyLastName1() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                null, "", null, null, uid2);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с пустой фамилией, при втором входе",
            dependsOnMethods = "postAuthProviderSessions200EmptyLastName1")
    public void postAuthProviderSessions200EmptyLastName2() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                null, "", null, null, uid2);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с некорректной почтой, при первом входе")
    public void postAuthProviderSessions422WrongEmail1() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                null, null, "wrong", null, uid3);
        checkStatusCode422(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с некорректной почтой, при втором входе",
            dependsOnMethods = "postAuthProviderSessions422WrongEmail1")
    public void postAuthProviderSessions422WrongEmail2() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                null, null, "wrong", null, uid3);
        checkStatusCode422(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с некорректным телефоном, при первом входе")
    public void postAuthProviderSessions422WrongPhone1() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                null, null, null, "123", uid4);
        checkStatusCode422(response);
    }

    @Deprecated
    @Test(groups = {},
            description = "Авторизуемся через стороннего провайдера с некорректным телефоном, при втором входе",
            dependsOnMethods = "postAuthProviderSessions422WrongPhone1")
    public void postAuthProviderSessions422WrongPhone2() {
        final Response response = AuthProvidersV2Request.Sessions.POST(AuthProviderV2.FACEBOOK,
                null, null, null, "123", uid4);
        checkStatusCode422(response);
    }
}
