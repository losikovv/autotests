package ru.instamart.tests.api.v1.contracts;

import instamart.api.common.RestBase;
import instamart.api.objects.v2.Retailer;
import instamart.api.requests.ApiV1Requests;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RetailersV1Tests extends RestBase {

    @CaseId(122)
    @Test(  description = "Контрактный тест списка ритейлеров",
            groups = "api-instamart-regress")
    public void getRetailers() {
        Response response = ApiV1Requests.Retailers.GET();
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Retailers.json"));
    }

    @CaseId(123)
    @Test(  description = "Контрактный тест ритейлера",
            groups = "api-instamart-regress",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailer(Retailer retailer) {
        Response response = ApiV1Requests.Retailers.GET(retailer.getId());
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Retailer.json"));
    }

    @CaseId(124)
    @Test(  description = "Контрактный тест списка штрихкодов у ритейлера",
            groups = "api-instamart-regress",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "retailersSpree-parallel")
    public void getRetailerEans(Retailer retailer) {
        Response response = ApiV1Requests.Retailers.Eans.GET(retailer.getId());
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Eans.json"));
    }
}
