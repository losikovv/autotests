package ru.instamart.test.api.eta;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.StoresEtaRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.eta.StoreParametersEtaResponse;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ETA сервис")
@Feature("Магазины")
public class StoresEtaTest extends RestBase {

    @CaseId(29)
    @Test(description = "Получение параметров магазина",
            groups = "api-eta")
    public void getStoreParameters() {
        String storeId = "c158f834-b944-4c84-9165-65f311e6aed4";
        final Response response = StoresEtaRequest.Parameters.GET(storeId);
        StoreParametersEtaResponse parameters = response.as(StoreParametersEtaResponse.class);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreParametersEtaResponse.class);
        assertEquals(parameters.getId(), storeId, "Вернулся не верный uuid магазина");
    }

    @CaseId(219)
    @Test(description = "Получение ошибки не существующего магазина",
            groups = "api-eta")
    public void getStoreParametersUndefined() {
        String storeId = "11111111-1111-1111-1111-111111111111";
        String expectedResult = String.format("магазин id = %s не существует", storeId);
        final Response response = StoresEtaRequest.Parameters.GET(storeId);
        ErrorResponse parameters = response.as(ErrorResponse.class);

        checkStatusCode404(response);
        Allure.step("Проверка получения верной ошибки", () -> {
            assertEquals(parameters.getMessage(), expectedResult, "Не верная ошибка");
        });
    }

    @CaseId(227)
    @Test(description = "Получение ошибки не валидного uuid магазина",
            groups = "api-eta")
    public void getStoreParametersInvalid() {
        final Response response = StoresEtaRequest.Parameters.GET("test");
        ErrorResponse parameters = response.as(ErrorResponse.class);

        checkStatusCode400(response);
        Allure.step("Проверка получения верной ошибки", () -> {
            assertEquals(parameters.getMessage(), "Invalid storeID, must be a valid UUID", "Не верная ошибка");
        });
    }
}
