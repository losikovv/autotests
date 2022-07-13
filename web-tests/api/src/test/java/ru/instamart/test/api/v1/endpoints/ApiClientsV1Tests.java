package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.ApiClientV1;
import ru.instamart.api.request.v1.admin.ApiClientsV1Request;
import ru.instamart.api.response.v1.ApiClientV1Response;
import ru.instamart.api.response.v1.ApiClientsV1Response;
import ru.instamart.jdbc.dao.stf.ApiClientsDao;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static org.testng.Assert.assertNull;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkApiClient;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("API клиентов")
public class ApiClientsV1Tests extends RestBase {

    private long apiClientId;
    private ApiClientsV1Request.ApiClientRequest apiClient;

    @BeforeClass(alwaysRun = true, description = "Авторизация")
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2697)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение API клиентов")
    public void getApiClients() {
        final Response response = ApiClientsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ApiClientsV1Response.class);
    }

    @CaseId(2698)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение API клиентов со второй страницы")
    public void getApiClientsFromSecondPage() {
        final Response response = ApiClientsV1Request.GET(null, 2);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ApiClientsV1Response.class);
    }

    @CaseId(2699)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание API клиента")
    public void createApiClient() {
        ApiClientsV1Request.ApiClientRequest apiClient = ApiClientsV1Request.ApiClientRequest.builder()
                .apiClient(ApiClientsV1Request.ApiClient.builder()
                        .clientId("Autotest_" + Generate.literalString(6))
                        .secret("autotest_" + Generate.literalString(6))
                        .skuKind("sku_kind_internal")
                        .retailerIds(List.of(1L))
                        .tenantId("instamart")
                        .verifiable(false)
                        .customPrices(true)
                        .customPromo(true)
                        .build())
                .build();
        final Response response = ApiClientsV1Request.POST(apiClient);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ApiClientV1Response.class);
        ApiClientV1 apiClientFromResponse = response.as(ApiClientV1Response.class).getApiClient();
        apiClientId = apiClientFromResponse.getId();
        checkApiClient(apiClient, apiClientFromResponse);
    }

    @CaseId(2700)
    @Test(groups = {"api-instamart-regress"},
            description = "Создание API клиента без client_id")
    public void createApiClientWithoutClientId() {
        ApiClientsV1Request.ApiClientRequest apiClient = ApiClientsV1Request.ApiClientRequest.builder()
                .apiClient(ApiClientsV1Request.ApiClient.builder()
                        .secret("autotest_" + Generate.literalString(6))
                        .skuKind("sku_kind_internal")
                        .retailerIds(List.of(1L))
                        .tenantId("instamart")
                        .verifiable(false)
                        .customPrices(true)
                        .customPromo(true)
                        .build())
                .build();
        final Response response = ApiClientsV1Request.POST(apiClient);
        checkStatusCode422(response);
        Assert.assertTrue(response.asString().contains("\"client_id\":[\"не может быть пустым\"]"), "Пришел неверный текст ошибки");
    }

    @CaseId(2701)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование API клиента",
            dependsOnMethods = "createApiClient")
    public void editApiClient() {
        apiClient = ApiClientsV1Request.ApiClientRequest.builder()
                .apiClient(ApiClientsV1Request.ApiClient.builder()
                        .clientId("Autotest_" + Generate.literalString(6))
                        .secret("autotest_" + Generate.literalString(6))
                        .skuKind("sku_kind_internal")
                        .retailerIds(List.of(1L))
                        .tenantId("instamart")
                        .verifiable(false)
                        .customPrices(true)
                        .customPromo(true)
                        .build())
                .build();
        final Response response = ApiClientsV1Request.PUT(apiClient, apiClientId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ApiClientV1Response.class);
        ApiClientV1 apiClientFromResponse = response.as(ApiClientV1Response.class).getApiClient();
        checkApiClient(apiClient, apiClientFromResponse);
    }

    @CaseId(2702)
    @Test(groups = {"api-instamart-regress"},
            description = "Редактирование несуществующего API клиента")
    public void editNonExistentApiClient() {
        ApiClientsV1Request.ApiClientRequest apiClient = ApiClientsV1Request.ApiClientRequest.builder()
                .apiClient(ApiClientsV1Request.ApiClient.builder()
                        .clientId("Autotest_" + Generate.literalString(6))
                        .build())
                .build();
        final Response response = ApiClientsV1Request.PUT(apiClient, 0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2705)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение API клиента",
            dependsOnMethods = "editApiClient")
    public void getApiClient() {
        final Response response = ApiClientsV1Request.GET(apiClientId);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ApiClientV1Response.class);
        ApiClientV1 apiClientFromResponse = response.as(ApiClientV1Response.class).getApiClient();
        checkApiClient(apiClient, apiClientFromResponse);
    }

    @CaseId(2703)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение несуществующего API клиента")
    public void getNonExistentApiClient() {
        final Response response = ApiClientsV1Request.GET(99999L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2704)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение API клиентов по запросу",
            dependsOnMethods = "editApiClient")
    public void getSpecificApiClients() {
        final Response response = ApiClientsV1Request.GET(apiClient.getApiClient().getClientId(), null);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ApiClientsV1Response.class);
        List<ApiClientV1> apiClientsFromResponse = response.as(ApiClientsV1Response.class).getApiClients();
        apiClientsFromResponse.forEach(a -> compareTwoObjects(a.getClientId(), apiClient.getApiClient().getClientId()));
    }

    @CaseId(2706)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление API клиента",
            dependsOnMethods = "getSpecificApiClients")
    public void deleteApiClient() {
        final Response response = ApiClientsV1Request.DELETE(apiClientId);
        checkStatusCode200(response);
        Allure.step("Проверяем, что клиент удалился", () -> {
            assertNull(ApiClientsDao.INSTANCE.getClientIdByName(apiClient.getApiClient().getClientId()), "API клиент не удалился");
        });
    }

    @CaseId(2707)
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление несуществующего API клиента",
            dependsOnMethods = "getSpecificApiClients")
    public void deleteNonExistentApiClient() {
        final Response response = ApiClientsV1Request.DELETE(0L);
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
