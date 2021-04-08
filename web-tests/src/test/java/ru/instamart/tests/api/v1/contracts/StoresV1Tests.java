package ru.instamart.tests.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.objects.v2.StoreV2;
import ru.instamart.api.requests.ApiV1Requests;
import ru.instamart.core.testdata.dataprovider.RestDataProvider;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class StoresV1Tests extends RestBase {

    @Story("Магазины")
    @CaseId(125)
    @Test(  description = "Контрактный тест списка магазинов",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getStores() {
        Response response = ApiV1Requests.Stores.GET();
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Stores.json"));
    }

    @Story("Магазины")
    @CaseId(126)
    @Test(  description = "Контрактный тест магазина",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "storeOfEachRetailer-parallel")
    public void getStore(StoreV2 store) {
        Response response = ApiV1Requests.Stores.GET(store.getUuid());
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Store.json"));
    }

    @Story("Магазины")
    @CaseId(127)
    @Test(  description = "Контрактный тест поиска товаров в магазине",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "storeOfEachRetailer-parallel")
    public void getStoreOffers(StoreV2 store) {
        Response response = ApiV1Requests.Stores.Offers.GET(
                store.getUuid(),
                "вода",
                "");
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Offers.json"));
    }
}
