package ru.instamart.test.api.dispatch.eta;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.eta.RetailersEtaRequest;
import ru.instamart.api.response.eta.RetailerParametersEtaResponse;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.assertEquals;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("On Demand")
@Feature("ETA")
public class RetailersEtaTest extends RestBase {

    @CaseId(27)
    @Story("Ритейлеры")
    @Test(description = "Получение параметров ритейлера",
            groups = "dispatch-eta-smoke")
    public void getRetailerParameters() {
        String retailerId = "1";
        final Response response = RetailersEtaRequest.Parameters.GET(retailerId);

        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerParametersEtaResponse.class);
        RetailerParametersEtaResponse parameters = response.as(RetailerParametersEtaResponse.class);
        assertEquals(parameters.getId(), retailerId, "Вернулся не верный id ритейлера");
    }
}
