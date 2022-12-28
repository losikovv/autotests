package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.StoreV3;
import ru.instamart.api.request.v3.StoresV3Request;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Магазины")
public class StoresV3Test extends RestBase {

    @CaseId(669)
    @Test(groups = {"api-instamart-smoke", API_INSTAMART_PROD, "api-v3"},
            description = "Все доступные магазины ")
    public void getStores() {
        Response response = StoresV3Request.Stores.GET();
        checkStatusCode200(response);
    }

    @CaseId(864)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v3"},
            description = "Доступные магазины для доставки")
    public void getStoresDelivery() {
        Response response = StoresV3Request.Delivery.GET();
        checkStatusCode200(response);

        List<StoreV3> stores = Arrays.asList(response.as(StoreV3[].class));
        String shippingMethodTitle = stores.get(0).getShipping_methods().get(0).getTitle();
        checkFieldIsNotEmpty(shippingMethodTitle, "title");
    }

    @CaseId(865)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v3"},
            description = "Доступные магазины для самовывоза")
    public void getStoresPickupFromStore() {
        Response response = StoresV3Request.PickupFromStore.GET();
        checkStatusCode200(response);
    }

    @CaseId(866)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v3"},
            description = "Доступные магазины с ближайшими опциями")
    public void getStoresClosestShippingOptions() {
        Response response = StoresV3Request.ClosestShippingOptions.GET();
        checkStatusCode200(response);
    }

    @CaseId(867)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v3"},
            description = "Доступные магазины по ритейлеру Metro")
    public void getStoresRetailerId() {
        Response response = StoresV3Request.RetailerId.GET();
        checkStatusCode200(response);
    }
}
