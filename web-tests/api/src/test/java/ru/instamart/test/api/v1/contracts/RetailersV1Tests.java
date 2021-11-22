package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v2.RetailerV2;
import ru.instamart.api.request.v1.RetailersV1Request;
import ru.instamart.api.response.v1.EansV1Response;
import ru.instamart.api.response.v2.RetailerV2Response;
import ru.instamart.api.response.v2.RetailersV2Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;
import static ru.instamart.api.dataprovider.RestDataProvider.getAvailableRetailersSpree;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class RetailersV1Tests extends RestBase {

    @Story("Ретейлеры")
    @CaseId(122)
    @Test(description = "Контрактный тест списка ретейлеров",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getRetailers() {
        final Response response = RetailersV1Request.GET();
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailersV2Response.class);
    }

    @Test(groups = {"api-instamart-regress", "api-instamart-prod"})
    public void selfTestRetailersV1() {
        getAvailableRetailersSpree();
    }

    @Story("Ретейлеры")
    @CaseId(123)
    @Test(description = "Контрактный тест ретейлера",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailer(RetailerV2 retailer) {
        final Response response = RetailersV1Request.GET(retailer.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, RetailerV2Response.class);
    }

    @Story("Ретейлеры")
    @CaseId(124)
    @Test(description = "Контрактный тест списка штрихкодов у ретейлера",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailerEans(RetailerV2 retailer) {
        final Response response = RetailersV1Request.Eans.GET(retailer.getId());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, EansV1Response.class);
    }
}
