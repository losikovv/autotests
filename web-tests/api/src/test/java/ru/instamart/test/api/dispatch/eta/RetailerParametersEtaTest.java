package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.RetailerParametersEtaRequest;
import ru.instamart.api.response.ErrorResponse;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.RetailerParametersDao;
import ru.instamart.jdbc.entity.eta.RetailerParametersEntity;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.helper.EtaHelper.updateRetailerParameters;

@Epic("On Demand")
@Feature("ETA")
public class RetailerParametersEtaTest extends RestBase {

    private RetailerParametersEtaResponse retailerParameters;
    private final String retailerId = "1";
    private int courierSpeed;

    @CaseId(27)
    @Story("Параметры ритейлеров")
    @Test(description = "Получение параметров ритейлера",
            groups = "dispatch-eta-smoke")
    public void getRetailerParameters() {
        final Response response = RetailerParametersEtaRequest.GET(retailerId);

        checkStatusCode(response, 200, "");
        checkResponseJsonSchema(response, RetailerParametersEtaResponse.class);
        retailerParameters = response.as(RetailerParametersEtaResponse.class);
        compareTwoObjects(retailerParameters.getId(), retailerId);
    }

    @CaseId(28)
    @Story("Параметры ритейлеров")
    @Test(description = "Изменение параметров ритейлера",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "getRetailerParameters")
    public void editRetailerParameters() {
        courierSpeed = retailerParameters.getCourierSpeed();
        retailerParameters.setCourierSpeed(RandomUtils.nextInt(10, 100));
        updateRetailerParameters(retailerId, retailerParameters);

        RetailerParametersEntity retailerParametersFromDb = RetailerParametersDao.INSTANCE.findById(1L).get();
        compareTwoObjects(retailerParametersFromDb.getCourierSpeed(), retailerParameters.getCourierSpeed());
    }

    @CaseId(229)
    @Story("Параметры ритейлеров")
    @Test(description = "Получение ошибки не поддерживаемого медиа типа",
            groups = "dispatch-eta-regress",
            dependsOnMethods = "getRetailerParameters")
    public void editRetailerParametersWithoutContentType() {
        final Response response = RetailerParametersEtaRequest.WithoutСontentType.PUT(retailerId, retailerParameters);

        checkStatusCode(response, 415, "");
        ErrorResponse parameters = response.as(ErrorResponse.class);
        compareTwoObjects(parameters.getMessage(), "Unsupported Media Type");
    }

    @AfterClass(alwaysRun = true)
    public void postConditions() {
        retailerParameters.setCourierSpeed(courierSpeed);
        updateRetailerParameters(retailerId, retailerParameters);
    }
}
