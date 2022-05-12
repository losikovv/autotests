package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.RetailersEtaRequest;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;
import ru.instamart.jdbc.dao.eta.RetailerParametersDao;
import ru.instamart.jdbc.entity.eta.RetailerParametersEntity;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("On Demand")
@Feature("ETA")
public class RetailersEtaTest extends RestBase {

    private RetailerParametersEtaResponse parameters;
    private final String retailerId = "1";

    @CaseId(27)
    @Story("Ритейлеры")
    @Test(description = "Получение параметров ритейлера",
            groups = "dispatch-eta-smoke")
    public void getRetailerParameters() {
        final Response response = RetailersEtaRequest.GET(retailerId);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerParametersEtaResponse.class);
        parameters = response.as(RetailerParametersEtaResponse.class);
        assertEquals(parameters.getId(), retailerId, "Вернулся не верный id ритейлера");
    }

    @CaseId(28)
    @Story("Ритейлеры")
    @Test(description = "Изменение параметров ритейлера",
            groups = "dispatch-eta-smoke",
            dependsOnMethods = "getRetailerParameters")
    public void editRetailerParameters() {
        parameters.setCourierSpeed(RandomUtils.nextInt(10, 100));
        final Response response = RetailersEtaRequest.PUT(retailerId, parameters);

        checkStatusCode(response, 200, "");
        RetailerParametersEntity retailerParametersFromDb = RetailerParametersDao.INSTANCE.findById(1L).get();
        compareTwoObjects(retailerParametersFromDb.getCourierSpeed(), parameters.getCourierSpeed());
    }
}
