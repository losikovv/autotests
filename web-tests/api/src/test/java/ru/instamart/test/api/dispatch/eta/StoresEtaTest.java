package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
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

@Epic("On Demand")
@Feature("ETA")
public class StoresEtaTest extends RestBase {

    @CaseId(29)
    @Story("Магазины")
    @Test(description = "Получение параметров магазина",
            groups = "dispatch-eta-smoke")
    public void getStoreParameters() {
        String storeId = "c158f834-b944-4c84-9165-65f311e6aed4";
        final Response response = StoresEtaRequest.Parameters.GET(storeId);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreParametersEtaResponse.class);
        StoreParametersEtaResponse parameters = response.as(StoreParametersEtaResponse.class);
        assertEquals(parameters.getId(), storeId, "Вернулся неверный uuid магазина");
    }

    @CaseId(219)
    @Story("Магазины")
    @Test(description = "Получение ошибки не существующего магазина",
            groups = "dispatch-eta-smoke")
    public void getStoreParametersUndefined() {
        String storeId = "11111111-1111-1111-1111-111111111111";
        String expectedResult = String.format("магазин id = %s не существует", storeId);
        final Response response = StoresEtaRequest.Parameters.GET(storeId);

        checkStatusCode404(response);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        Allure.step("Проверка получения верной ошибки", () -> {
            assertEquals(parameters.getMessage(), expectedResult, "Неверная ошибка");
        });
    }

    @CaseId(227)
    @Test(description = "Получение ошибки не валидного uuid магазина",
            groups = "dispatch-eta-smoke")
    public void getStoreParametersInvalid() {
        final Response response = StoresEtaRequest.Parameters.GET("test");

        checkStatusCode400(response);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        Allure.step("Проверка получения верной ошибки", () -> {
            assertEquals(parameters.getMessage(), "Invalid storeID, must be a valid UUID", "Неверная ошибка");
        });
    }
}
