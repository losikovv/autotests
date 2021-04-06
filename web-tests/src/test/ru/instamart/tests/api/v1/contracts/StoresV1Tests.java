package ru.instamart.tests.api.v1.contracts;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Store;
import instamart.api.requests.ApiV1Requests;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StoresV1Tests extends RestBase {

    @CaseId(125)
    @Test(  description = "Контрактный тест списка магазинов",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getStores() {
        Response response = ApiV1Requests.Stores.GET();
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Stores.json"));
    }

    @CaseId(126)
    @Test(  description = "Контрактный тест магазина",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "storeOfEachRetailer-parallel")
    public void getStore(Store store) {
        Response response = ApiV1Requests.Stores.GET(store.getUuid());
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Store.json"));
    }

    @CaseId(127)
    @Test(  description = "Контрактный тест поиска товаров в магазине",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "storeOfEachRetailer-parallel")
    public void getStoreOffers(Store store) {
        Response response = ApiV1Requests.Stores.Offers.GET(
                store.getUuid(),
                "вода",
                "");
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Offers.json"));
    }
}
