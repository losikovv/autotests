package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.StoreParametersEtaRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.eta.StoreParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.StoreParametersDao;
import ru.instamart.jdbc.dao.stf.StoresDao;
import ru.instamart.jdbc.entity.eta.StoreParametersEntity;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.helper.EtaHelper.updateStoreParameters;

@Epic("On Demand")
@Feature("ETA")
public class StoreParametersEtaTest extends RestBase {

    private String storeUuid;
    private StoreParametersEtaResponse storeParameters;
    private int avgPositionsPerPlace;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        storeUuid = StoresDao.INSTANCE.findById(EnvironmentProperties.DEFAULT_ON_DEMAND_SID).get().getUuid();
    }


    @CaseId(29)
    @Story("Параметры магазинов")
    @Test(description = "Получение параметров магазина",
            groups = "dispatch-eta-smoke")
    public void getStoreParameters() {
        final Response response = StoreParametersEtaRequest.GET(storeUuid);

        checkStatusCode(response, 200, "");
        checkResponseJsonSchema(response, StoreParametersEtaResponse.class);
        storeParameters = response.as(StoreParametersEtaResponse.class);
        compareTwoObjects(storeParameters.getId(), storeUuid);
    }

    @CaseId(30)
    @Story("Параметры магазинов")
    @Test(description = "Обновление параметров магазина",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "getStoreParameters")
    public void editStoreParameters() {
        avgPositionsPerPlace = storeParameters.getAvgPositionsPerPlace();
        storeParameters.setAvgPositionsPerPlace(RandomUtils.nextInt(1, 10));
        final Response response = StoreParametersEtaRequest.PUT(storeUuid, storeParameters);

        checkStatusCode(response, 200, "");
        StoreParametersEntity storeParametersFromDb = StoreParametersDao.INSTANCE.findById(storeUuid).get();
        compareTwoObjects(storeParametersFromDb.getAvgPositionsPerPlace(), storeParameters.getAvgPositionsPerPlace());
    }

    @CaseId(219)
    @Story("Параметры магазинов")
    @Test(description = "Получение ошибки не существующего магазина",
            groups = "dispatch-eta-regress")
    public void getStoreParametersUndefined() {
        String storeId = "11111111-1111-1111-1111-111111111111";
        String expectedResult = String.format("магазин id = %s не существует", storeId);
        final Response response = StoreParametersEtaRequest.GET(storeId);

        checkStatusCode(response, 404, "");
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), expectedResult);
    }

    @CaseId(227)
    @Story("Параметры магазинов")
    @Test(description = "Получение ошибки не валидного uuid магазина",
            groups = "dispatch-eta-regress")
    public void getStoreParametersInvalid() {
        final Response response = StoreParametersEtaRequest.GET("test");

        checkStatusCode(response, 400, "");
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "Invalid storeID, must be a valid UUID");
    }

    @CaseId(230)
    @Story("Параметры магазинов")
    @Test(description = "Получение ошибки не поддерживаемого медиа типа",
            groups = "dispatch-eta-regress",
            dependsOnMethods = "getStoreParameters")
    public void editStoreParametersWithoutContentType() {
        final Response response = StoreParametersEtaRequest.WithoutСontentType.PUT(storeUuid, storeParameters);

        checkStatusCode(response, 415, "");
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "Unsupported Media Type");
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        storeParameters.setAvgPositionsPerPlace(avgPositionsPerPlace);
        updateStoreParameters(storeUuid, storeParameters);
    }
}
