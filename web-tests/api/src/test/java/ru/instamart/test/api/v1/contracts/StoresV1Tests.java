package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.StoreV2;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.OffersV1Response;
import ru.instamart.api.response.v1.StoreV1Response;
import ru.instamart.api.response.v1.StoresV1Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class StoresV1Tests extends RestBase {

    @Story("Магазины")
    @CaseId(125)
    @Test(description = "Контрактный тест списка магазинов",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getStores() {
        Response response = StoresV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoresV1Response.class);
    }

    @Story("Магазины")
    @CaseId(126)
    @Test(description = "Контрактный тест магазина",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "storeOfEachRetailer-parallel")
    public void getStore(StoreV2 store) {
        Response response = StoresV1Request.GET(store.getUuid());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreV1Response.class);
    }

    @Story("Магазины")
    @CaseId(127)
    @Test(description = "Контрактный тест поиска товаров в магазине",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "storeOfEachRetailer-parallel")
    public void getStoreOffers(StoreV2 store) {
        Response response = StoresV1Request.Offers.GET(
                store.getUuid(),
                "вода",
                "");
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersV1Response.class);
    }
}
