package ru.instamart.test.api.on_demand.eta;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.EtaBase;
import ru.instamart.api.request.eta.RetailerParametersEtaRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.RetailerParametersDao;
import ru.instamart.jdbc.entity.eta.RetailerParametersEntity;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.helper.EtaHelper.updateRetailerParameters;

@Epic("ETA")
@Feature("Retailer Parameters")
public class RetailerParametersEtaTest extends EtaBase {

    private RetailerParametersEtaResponse retailerParameters;
    private int courierSpeed;

    @TmsLink("27")
    @Story("Параметры ритейлеров")
    @Test(description = "Получение параметров ритейлера",
            groups = "ondemand-eta")
    public void getRetailerParameters() {
        final Response response = RetailerParametersEtaRequest.GET(RETAILER_ID);

        checkStatusCode(response, 200, "");
        checkResponseJsonSchema(response, RetailerParametersEtaResponse.class);
        retailerParameters = response.as(RetailerParametersEtaResponse.class);
        compareTwoObjects(retailerParameters.getId(), RETAILER_ID);
    }

    @TmsLink("28")
    @Story("Параметры ритейлеров")
    @Test(description = "Изменение параметров ритейлера",
            groups = "ondemand-eta",
            dependsOnMethods = "getRetailerParameters")
    public void editRetailerParameters() {
        courierSpeed = retailerParameters.getCourierSpeed();
        retailerParameters.setCourierSpeed(RandomUtils.nextInt(10, 100));
        updateRetailerParameters(RETAILER_ID, retailerParameters);

        RetailerParametersEntity retailerParametersFromDb = RetailerParametersDao.INSTANCE.findById(1L).get();
        compareTwoObjects(retailerParametersFromDb.getCourierSpeed(), retailerParameters.getCourierSpeed());
    }

    @TmsLink("229")
    @Story("Параметры ритейлеров")
    @Test(description = "Получение ошибки не поддерживаемого медиа типа",
            groups = "ondemand-eta",
            dependsOnMethods = "getRetailerParameters")
    public void editRetailerParametersWithoutContentType() {
        final Response response = RetailerParametersEtaRequest.WithoutСontentType.PUT(RETAILER_ID, retailerParameters);

        checkStatusCode(response, 415, "");
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "Unsupported Media Type");
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        retailerParameters.setCourierSpeed(courierSpeed);
        updateRetailerParameters(RETAILER_ID, retailerParameters);
    }
}
