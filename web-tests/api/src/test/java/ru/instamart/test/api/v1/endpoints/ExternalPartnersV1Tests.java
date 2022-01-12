package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.ServicesV2;
import ru.instamart.api.request.v1.ExternalPartnersV1Request;
import ru.instamart.api.response.v2.ExternalPartnersServicesV2Response;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.data.user.UserManager;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.compareTwoObjects;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkExternalPartnersServices;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.helper.K8sHelper.addSberPrime;

@Epic("ApiV1")
@Feature("Внешние партнеры")
public class ExternalPartnersV1Tests extends RestBase {

    @CaseId(1433)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Подписка SberPrime неактивна")
    public void getInactiveSubscription() {
        SessionFactory.createSessionToken(SessionType.API_V1, UserManager.getDefaultAdmin());
        final Response response = ExternalPartnersV1Request.Services.GET();
        checkStatusCode200(response);
        List<ServicesV2> services = response.as(ExternalPartnersServicesV2Response.class).getServices();
        compareTwoObjects(services.size(), 0);
    }

    @CaseId(1434)
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {"api-instamart-regress"},
            description = "Подписка SberPrime активна")
    public void getActiveSubscription() {
        UserData user = UserManager.getDefaultAdminAllRoles();
        SessionFactory.createSessionToken(SessionType.API_V1, user);
        addSberPrime(user.getEmail());
        final Response response = ExternalPartnersV1Request.Services.GET();
        checkStatusCode200(response);
        checkExternalPartnersServices(response, true);
    }
}
