package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v2.ExternalPartnersV2Request;
import io.qameta.allure.TmsLink;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode400;
import static ru.instamart.api.helper.K8sHelper.addSberPrime;

@Epic("ApiV2")
@Feature("Список банеров для SberPrime")
public class ExternalPartnersV2Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void testUp() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @TmsLink("270")
    @Test(//Прайм -10% частично выпилен с прода DMND-1912
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Отправка запроса без store_id")
    public void testWithoutShopId() {
        final Response response = ExternalPartnersV2Request.Banners.SberPrime.GET("");
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'store_id'");
    }

    @TmsLink("269")
    @Test(//Прайм -10% частично выпилен с прода DMND-1912
            groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Отправка запроса c store_id")
    public void testWithShopId() {
        final Response response = ExternalPartnersV2Request.Banners.SberPrime.GET("1");
        checkStatusCode200(response);
    }

    @TmsLink("810")
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2", "api-bff"},
            description = "Подписка SberPrime неактивна")
    public void getInactiveSubscription() {
        final Response response = ExternalPartnersV2Request.Services.GET();
        checkStatusCode200(response);
    }

    @TmsLink("1086")
    @Story("Получение списка подписок для пользователя")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2", "api-bff"},
            description = "Подписка SberPrime активна")
    public void getActiveSubscription() {
        addSberPrime(apiV2.getProfile().getUser().getEmail());
        final Response response = ExternalPartnersV2Request.Services.GET();
        checkStatusCode200(response);
    }
}
