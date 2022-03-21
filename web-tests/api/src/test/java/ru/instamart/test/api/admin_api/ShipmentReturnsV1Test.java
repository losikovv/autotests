package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.admin.ShipmentReturnsAdminV1Request;
import ru.instamart.kraken.enums.Server;
import ru.instamart.kraken.listener.Skip;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.newAdminRoles;

@Epic("ApiV1")
@Feature("История возвратов ДС по заказу")
public class ShipmentReturnsV1Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        newAdminRoles(true);
        admin.authApi();
        //ThreadUtil.simplyAwait(6); до проверки схемы
    }

    @Skip(onServer = Server.STAGING)
    @CaseId(2312)
    @Test(groups = {"api-instamart-regress"},
            description = "Отображение истории возвратов на стр. возвратов")
    public void getShipmentReturns() {
        Response response = ShipmentReturnsAdminV1Request.GET("8fbb749f-5353-45a5-90e2-3823fc05df60");//TODO: расхардкодить, добавить проверку схемы.

        checkStatusCode200(response);
    }

    @AfterClass(alwaysRun = true)
    public void postconditions() {
        newAdminRoles(false);
    }
}
