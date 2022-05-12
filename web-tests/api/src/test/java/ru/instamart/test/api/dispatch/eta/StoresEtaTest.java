package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.StoresEtaRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.eta.StoreParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.eta.StoreParametersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("On Demand")
@Feature("ETA")
public class StoresEtaTest extends RestBase {

    private String storeUuid;
    private StoreParametersEtaResponse parameters;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
    }


    @CaseId(29)
    @Story("Магазины")
    @Test(description = "Получение параметров магазина",
            groups = "dispatch-eta-smoke")
    public void getStoreParameters() {
        final Response response = StoresEtaRequest.GET(storeUuid);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreParametersEtaResponse.class);
        parameters = response.as(StoreParametersEtaResponse.class);
        assertEquals(parameters.getId(), storeUuid, "Вернулся неверный uuid магазина");
    }

    @CaseId(30)
    @Story("Магазины")
    @Test(description = "Обновление параметров магазина",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "getStoreParameters")
    public void editStoreParameters() {
        parameters.setAvgPositionsPerPlace(RandomUtils.nextInt(1, 10));
        final Response response = StoresEtaRequest.PUT(storeUuid, parameters);
        checkStatusCode(response, 200, "");
        StoreParametersEntity storeParametersFromDb = StoreParametersDao.INSTANCE.findById(storeUuid).get();
        compareTwoObjects(storeParametersFromDb.getAvgPositionsPerPlace(), parameters.getAvgPositionsPerPlace());
    }

    @CaseId(219)
    @Story("Магазины")
    @Test(description = "Получение ошибки не существующего магазина",
            groups = "dispatch-eta-smoke")
    public void getStoreParametersUndefined() {
        String storeId = "11111111-1111-1111-1111-111111111111";
        String expectedResult = String.format("магазин id = %s не существует", storeId);
        final Response response = StoresEtaRequest.GET(storeId);

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
        final Response response = StoresEtaRequest.GET("test");

        checkStatusCode400(response);
        ErrorResponse parameters = response.as(ErrorResponse.class);
        Allure.step("Проверка получения верной ошибки", () -> {
            assertEquals(parameters.getMessage(), "Invalid storeID, must be a valid UUID", "Неверная ошибка");
        });
    }
}
