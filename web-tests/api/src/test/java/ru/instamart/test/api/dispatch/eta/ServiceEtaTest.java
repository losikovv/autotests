package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.ServiceEtaRequest;
import ru.instamart.api.response.eta.ServiceParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.ServiceParametersDao;
import ru.instamart.jdbc.entity.eta.ServiceParametersEntity;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.EtaHelper.updateServiceParameters;

@Epic("On Demand")
@Feature("ETA")
public class ServiceEtaTest extends RestBase {

    private ServiceParametersEtaResponse serviceParameters;
    private int courierSpeed;

    @CaseId(25)
    @Story("Параметры сервиса")
    @Test(description = "Получение параметров сервисов",
            groups = "dispatch-eta-smoke")
    public void getServiceParameters() {
        final Response response = ServiceEtaRequest.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ServiceParametersEtaResponse.class);
        serviceParameters = response.as(ServiceParametersEtaResponse.class);
    }

    @CaseId(26)
    @Story("Параметры сервиса")
    @Test(description = "Изменение параметров сервисов",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "getServiceParameters")
    public void editServiceParameters() {
        courierSpeed = serviceParameters.getCourierSpeed();
        serviceParameters.setCourierSpeed(1000);
        serviceParameters.setIsMlEnabled(!serviceParameters.getIsMlEnabled());
        updateServiceParameters(serviceParameters);

        ServiceParametersEntity serviceParametersFromDb = ServiceParametersDao.INSTANCE.getServiceParameters();
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(serviceParametersFromDb.getCourierSpeed(), serviceParameters.getCourierSpeed(), softAssert);
        compareTwoObjects(serviceParametersFromDb.getIsMlEnabled(), serviceParameters.getIsMlEnabled(), softAssert);
        softAssert.assertAll();
    }


    @AfterClass(alwaysRun = true)
    public void postConditions() {
        serviceParameters.setCourierSpeed(courierSpeed);
        serviceParameters.setIsMlEnabled(!serviceParameters.getIsMlEnabled());
        updateServiceParameters(serviceParameters);
    }
}
