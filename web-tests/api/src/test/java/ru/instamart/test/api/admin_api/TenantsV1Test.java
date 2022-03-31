package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v1.AdminTenantV1;
import ru.instamart.api.request.v1.TenantsV1Request;
import ru.instamart.api.response.v1.TenantsV1Response;
import ru.instamart.jdbc.dao.TenantsDao;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Тенанты")
public class TenantsV1Test extends RestBase {

    @CaseId(2515)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка тенантов")
    public void getTenants() {
        admin.authApi();
        final Response response = TenantsV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, TenantsV1Response.class);
        List<AdminTenantV1> tenants = response.as(TenantsV1Response.class).getTenants();
        compareTwoObjects(tenants.size(), TenantsDao.INSTANCE.getCount());
    }
}
