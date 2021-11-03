package ru.instamart.test.api.v3.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.model.v3.StoreV3;
import ru.instamart.api.request.v3.StoresV3Request;

import java.util.Arrays;
import java.util.List;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV3")
@Feature("Доступные магазины клиенту")
public class StoresV3Test extends RestBase {

    @CaseId(669)
    @Test(groups = {"api-instamart-smoke"}, description = "Все доступные магазины ")
    public void getStores() {
        Response response = StoresV3Request.Stores.GET();
        checkStatusCode200(response);
    }

    @CaseId(864)
    @Test(groups = {"api-instamart-regress"}, description = "Доступные магазины для доставки")
    public void getStoresDelivery() {
        Response response = StoresV3Request.Delivery.GET();
        checkStatusCode200(response);

        List<StoreV3> stores = Arrays.asList(response.as(StoreV3[].class));
        String shippingMethodTitle = stores.get(0).getShipping_methods().get(0).getTitle();
        checkFieldIsNotEmpty(shippingMethodTitle, "title");
    }

    @CaseId(865)
    @Test(groups = {"api-instamart-regress"}, description = "Доступные магазины для самовывоза")
    public void getStoresPickupFromStore() {
        Response response = StoresV3Request.PickupFromStore.GET();
        checkStatusCode200(response);
    }

    @CaseId(866)
    @Test(groups = {"api-instamart-regress"}, description = "Доступные магазины с ближайшими опциями")
    public void getStoresClosestShippingOptions() {
        Response response = StoresV3Request.ClosestShippingOptions.GET();
        checkStatusCode200(response);
    }

    @CaseId(867)
    @Test(groups = {"api-instamart-regress"}, description = "Доступные магазины по ритейлеру Metro")
    public void getStoresRetailerId() {
        Response response = StoresV3Request.RetailerId.GET();
        checkStatusCode200(response);
    }
}
