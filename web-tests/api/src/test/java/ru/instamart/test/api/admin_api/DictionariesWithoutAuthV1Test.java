package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.request.v1.DictionariesV1Request;

import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode401;

@Epic("ApiV1")
@Feature("Словари")
public class DictionariesWithoutAuthV1Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        SessionFactory.clearSession(SessionType.API_V1);
    }

    @TmsLink("1818")
    @Story("Получение словарей")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение словаря api_clients без авторизации")
    public void getDictionariesApiClients401() {
        final Response response = DictionariesV1Request.ApiClients.GET();
        checkStatusCode401(response);
    }

    @TmsLink("1819")
    @Story("Получение словарей")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение словаря payment_methods без авторизации")
    public void getDictionariesPaymentMethods401() {
        final Response response = DictionariesV1Request.PaymentMethods.GET();
        checkStatusCode401(response);
    }

    @TmsLink("1820")
    @Story("Получение словарей")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение словаря payment_states без авторизации")
    public void getDictionariesPaymentStates401() {
        final Response response = DictionariesV1Request.PaymentStates.GET();
        checkStatusCode401(response);
    }

    @TmsLink("1821")
    @Story("Получение словарей")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение словаря shipment_combined_states без авторизации")
    public void getDictionariesShipmentCombinedStates401() {
        final Response response = DictionariesV1Request.ShipmentCombinedStates.GET();
        checkStatusCode401(response);
    }

    @TmsLink("1822")
    @Story("Получение словарей")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v1"},
            description = "Получение словаря tenants без авторизации")
    public void getDictionariesTenants401() {
        final Response response = DictionariesV1Request.Tenants.GET();
        checkStatusCode401(response);
    }
}
