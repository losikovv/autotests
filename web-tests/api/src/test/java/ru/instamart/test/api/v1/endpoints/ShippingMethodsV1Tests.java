package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.ShippingMethodKindsV1Request;
import ru.instamart.api.response.v1.ShippingMethodKindsV1Response;
import ru.instamart.jdbc.dao.ShippingMethodKindsDao;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Способы доставки")
public class ShippingMethodsV1Tests extends RestBase {

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2137)
    @Test(description = "Получение списка способов доставки",
            groups = {"api-instamart-regress"})
    public void getShippingMethodKinds() {
        final Response response = ShippingMethodKindsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, ShippingMethodKindsV1Response.class);
        int countFromDb = ShippingMethodKindsDao.INSTANCE.getCount();
        compareTwoObjects(response.as(ShippingMethodKindsV1Response.class).getShippingMethodKinds().size(), countFromDb);
    }
}
