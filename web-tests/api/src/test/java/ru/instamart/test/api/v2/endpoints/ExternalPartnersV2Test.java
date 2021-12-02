package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ExternalPartnersV2Request;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkExternalPartnersServices;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;
import static ru.instamart.api.k8s.K8sHelper.addSberPrime;

@Epic("ApiV2")
@Feature("Список банеров для SberPrime")
public class ExternalPartnersV2Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(270)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Отправка запроса без store_id")
    public void testWithoutShopId() {
        final Response response = ExternalPartnersV2Request.Banners.SberPrime.GET("");
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'store_id'");
    }

    @CaseId(269)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Отправка запроса c store_id")
    public void testWithShopId() {
        final Response response = ExternalPartnersV2Request.Banners.SberPrime.GET("1");
        checkStatusCode200(response);
    }

    @CaseId(810)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress"}, description = "Подписка SberPrime неактивна")
    public void getInactiveSubscription() {
        Response response = ExternalPartnersV2Request.Services.GET();
        checkStatusCode200(response);
        checkExternalPartnersServices(response, false);
    }

    @CaseId(1086)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Подписка SberPrime активна")
    public void getActiveSubscription() {
        addSberPrime(apiV2.getProfile().getUser().getEmail());
        Response response = ExternalPartnersV2Request.Services.GET();
        checkStatusCode200(response);
        checkExternalPartnersServices(response, true);
    }
}
