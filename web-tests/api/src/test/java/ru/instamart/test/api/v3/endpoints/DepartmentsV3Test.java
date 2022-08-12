package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.DepartmentV3;
import ru.instamart.api.request.v3.DepartmentsV3Request;
import ru.instamart.api.response.v3.DepartmentV3Response;
import ru.instamart.api.response.v3.DepartmentsV3Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Категории (departments)")
public class DepartmentsV3Test extends RestBase {
    private DepartmentV3 department;
    private final Integer sid = EnvironmentProperties.DEFAULT_SID;

    @Skip //todo разобраться почему в метро обучение на проде возвращается пустой массов
    @CaseId(2364)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение списка департаментов")
    public void getDepartments()  {
        Response response = DepartmentsV3Request.GET(sid);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DepartmentsV3Response.class);
        department = response.as(DepartmentsV3Response.class).getDepartments().get(0);
    }

    @Skip //todo разобраться почему в метро обучение на проде возвращается пустой массов
    @CaseId(2365)
    @Test(  groups = {"api-instamart-smoke", "api-instamart-prod"},
            description = "Получение информации о категории",
            dependsOnMethods = "getDepartments")
    public void getDepartment()  {
        Response response = DepartmentsV3Request.GET(sid, department.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DepartmentV3Response.class);
    }
}
