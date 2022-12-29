package ru.instamart.test.api.authorization_service;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.helper.AuthorizationServiceHelper;
import ru.instamart.api.model.authorization_service.RealmModel;
import ru.instamart.api.request.authorization_service.RealmRequest;
import ru.instamart.api.response.authorization_service.RealmPostErrorResponse;
import ru.instamart.api.response.authorization_service.RealmPostResponse;
import ru.instamart.api.response.authorization_service.RealmPutErrorResponse;
import ru.instamart.api.response.authorization_service.RealmResponse;
import ru.instamart.kraken.data.user.UserManager;
import io.qameta.allure.TmsLink;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("Сервис авторизации")
@Feature("Рилм")
public class RealmTest {

    private static final String repositoryUrlBase = "gitlab.sbmt.io/paas/content/kraken-api-tests/";

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        SessionFactory.createSessionToken(SessionType.AUTHORIZATION_SERVICE, UserManager.getAuthorizationServiceKeycloakClient());
        Response response = RealmRequest.POST(AuthorizationServiceHelper.getInitialRealm());
    }

    @TmsLink("19")
    @Test(groups = {"api-authorization-service"},
            description = "Получение информации о рилме")
    public void getRealm200() {

        Response response = RealmRequest.GET("admin");

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmResponse.class);

    }

    @TmsLink("20")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма")
    public void createRealm200() {
        RealmModel body = RealmModel
                .builder()
                .name("kraken-api-tests")
                .repositoryUrl(repositoryUrlBase + "example-service")
                .service(RealmModel.Service
                        .builder()
                        .name("example-service")
                        .description("example-service description")
                        .build())
                .build();

        Response response = RealmRequest.POST(body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

    }

    @TmsLink("21")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма без сервисов")
    public void createRealmWithoutServices200() {
        RealmModel body = RealmModel
                .builder()
                .name("kraken-api-tests")
                .repositoryUrl(repositoryUrlBase + "example-service")
                .build();

        Response response = RealmRequest.POST(body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

    }

    @TmsLink("22")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма. DryRun = true")
    public void createRealmDryRunTrue200() {
        RealmModel body = RealmModel
                .builder()
                .name("test")
                .repositoryUrl(repositoryUrlBase + "policy-realm-test")
                .service(RealmModel.Service
                        .builder()
                        .name("test")
                        .description("test description")
                        .build())
                .build();

        Response response = RealmRequest.POST(true, body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

    }

    @TmsLink("28")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма с неверным сервисом")
    public void createRealmWithWrongServices400() {
        JSONObject body = new JSONObject();
        body.put("name", "test");
        body.put("repository_url", "gitlab.sbmt.io/paas/content/test/test");
        body.put("services", "invalid");

        Response response = RealmRequest.POST(body);

        checkStatusCode400(response);
        checkResponseJsonSchema(response, RealmPostErrorResponse.class);

        Allure.step("Проверка сообщения об ошибке", () -> {
            assertEquals(response.as(RealmPostErrorResponse.class).getError().getDetail(), "Body parsing error");
        });

    }

    @TmsLink("23")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма. DryRun = false")
    public void createRealmDryRunFalse200() {
        RealmModel body = RealmModel
                .builder()
                .name("test")
                .repositoryUrl(repositoryUrlBase + "policy-realm-test")
                .service(RealmModel.Service
                        .builder()
                        .name("test")
                        .description("test description")
                        .build())
                .build();

        Response response = RealmRequest.POST(false, body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

    }

    @TmsLink("24")
    @Test(groups = {"api-authorization-service"},
            description = "Обновление рилма")
    public void updateRealm200() {
        RealmModel body = RealmModel
                .builder()
                .name("test")
                .repositoryUrl(repositoryUrlBase + "policy-realm-test-updated")
                .build();

        Response response = RealmRequest.PUT("test", body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

        Response responseGet = RealmRequest.GET("test");

        Allure.step("Данные рилма действительно обновились", () -> {
            assertEquals(responseGet.as(RealmResponse.class).getData().get(0).getRepositoryUrl(),
                    repositoryUrlBase + "policy-realm-test-updated",
                    "Значения полей не были обновлены");
        });
    }

    @TmsLink("25")
    @Test(groups = {"api-authorization-service"},
            description = "Обновление неверного рилма")
    public void updateRealmWrongRealm422() {
        RealmModel body = RealmModel
                .builder()
                .name("test")
                .repositoryUrl(repositoryUrlBase + "policy-realm-test-updated")
                .build();

        Response response = RealmRequest.PUT("wrong", body);

        checkStatusCode422(response);
        checkResponseJsonSchema(response, RealmPutErrorResponse.class);

        Allure.step("Проверка сообщения об ошибке", () -> {
            assertEquals(response.as(RealmPutErrorResponse.class).getError().getDetail(), "unexpected realm in body data");
        });

    }

    @TmsLink("26")
    @Test(groups = {"api-authorization-service"},
            description = "Обновление рилма. DryRun = true")
    public void updateRealmDryRunTrue200() {
        RealmModel body = RealmModel
                .builder()
                .name("test")
                .repositoryUrl(repositoryUrlBase + "policy-realm-test-updated-dry")
                .build();

        Response response = RealmRequest.PUT("test", true, body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

        Response responseGet = RealmRequest.GET("test");

        Allure.step("Данные рилма не были загружены", () -> {
            assertNotEquals(responseGet.as(RealmResponse.class).getData().get(0).getRepositoryUrl(),
                    repositoryUrlBase + "policy-realm-test-updated-dry",
                    "Значения полей были обновлены при DryRun=true");
        });
    }

    @TmsLink("27")
    @Test(groups = {"api-authorization-service"},
            description = "Обновление рилма. DryRun = false")
    public void updateRealmDryRunFalse200() {
        RealmModel body = RealmModel
                .builder()
                .name("test")
                .repositoryUrl(repositoryUrlBase + "policy-realm-test-updated-not-dry")
                .build();

        Response response = RealmRequest.PUT("test", false, body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

        Response responseGet = RealmRequest.GET("test");

        Allure.step("Данные рилма действительно обновились", () -> {
            assertEquals(responseGet.as(RealmResponse.class).getData().get(0).getRepositoryUrl(),
                    repositoryUrlBase + "policy-realm-test-updated-not-dry",
                    "Значения полей не были обновлены");
        });
    }

    @TmsLink("41")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма с заданным user_type")
    public void createRealmWithUserType200() {
        RealmModel body = RealmModel
                .builder()
                .name("test-realm")
                .repositoryUrl(repositoryUrlBase + "test-realm")
                .service(RealmModel.Service
                        .builder()
                        .name("test-service")
                        .description("test-service description")
                        .build())
                .userType("testRealmUserType")
                .build();

        Response response = RealmRequest.POST(body);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RealmPostResponse.class);

        Allure.step("Проверка сообщения об успешном создании рилма", () -> {
            assertEquals(response.as(RealmPostResponse.class).getData(), "All data was added");
        });

        Response responseGet = RealmRequest.GET("test-realm");

        Allure.step("Созранен заданный UserType", () -> {
            assertEquals(responseGet.as(RealmResponse.class).getData().get(0).getUserType(),
                    "testRealmUserType",
                    "UserType не соответствует заданному");
        });

    }

    @TmsLink("42")
    @Test(groups = {"api-authorization-service"},
            description = "Создание рилма с именем в uppercase")
    public void createRealWithWrongName() {
        RealmModel body = RealmModel
                .builder()
                .name("kraken-api-tests")
                .repositoryUrl(repositoryUrlBase + "UpperCase")
                .service(RealmModel.Service
                        .builder()
                        .name("UpperCase")
                        .description("wrong name in uppercase")
                        .build())
                .build();

        Response response = RealmRequest.POST(body);

        checkStatusCode422(response);
        Allure.step("Проверка сообщения об ошибке", () -> {
            assertEquals(response.as(RealmPutErrorResponse.class).getError().getTitle(), "Request parameters didn't validate");
        });
    }
}
