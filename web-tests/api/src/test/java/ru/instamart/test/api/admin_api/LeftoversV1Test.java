package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.admin.LeftoversAdminV1Request;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Остатки")
public class LeftoversV1Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(2310)
    @Test(groups = {"api-instamart-regress"},
        description = "Отображение таблицы товаров на стр. возвратов (sberpayments)")
    public void getLeftovers() {
        Response response = LeftoversAdminV1Request.GET("bd57224a-7bb5-4974-9225-ec3ff3a68f90"); //пока хардкод

        checkStatusCode200(response);
    }
}
