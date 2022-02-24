package ru.instamart.test.api.admin_api;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v1.DictionariesV1Request;
import ru.instamart.api.response.v1.dictionaries.*;
import ru.sbermarket.qase.annotation.CaseId;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Словари")
public class DictionariesWithAuthV1Test extends RestBase {

    @BeforeMethod(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @CaseId(1813)
    @Story("Получение словарей")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение словаря api_clients")
    public void getDictionariesApiClients200() {
        final Response response = DictionariesV1Request.ApiClients.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DictionariesApiClientsV1Response.class);
    }

    @CaseId(1814)
    @Story("Получение словарей")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение словаря payment_methods")
    public void getDictionariesPaymentMethods200() {
        final Response response = DictionariesV1Request.PaymentMethods.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DictionariesPaymentMethodsV1Response.class);
    }

    @CaseId(1815)
    @Story("Получение словарей")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение словаря payment_states")
    public void getDictionariesPaymentStates200() {
        final Response response = DictionariesV1Request.PaymentStates.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DictionariesPaymentStatesV1Response.class);
    }

    @CaseId(1816)
    @Story("Получение словарей")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение словаря shipment_combined_states")
    public void getDictionariesShipmentCombinedStates200() {
        final Response response = DictionariesV1Request.ShipmentCombinedStates.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DictionariesShipmentCombinedStatesV1Response.class);
    }

    @CaseId(1817)
    @Story("Получение словарей")
    @Test(groups = {"api-instamart-regress"},
            description = "Получение словаря tenants")
    public void getDictionariesTenants200() {
        final Response response = DictionariesV1Request.Tenants.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, DictionariesTenantsV1Response.class);
    }
}
