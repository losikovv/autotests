package ru.instamart.tests.api.v1.contracts;

import instamart.api.common.RestBase;
import instamart.api.objects.v1.Offer;
import instamart.api.requests.ApiV1Requests;
import instamart.core.testdata.dataprovider.RestDataProvider;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class Offers extends RestBase {

    @CaseId(111)
    @Test(  description = "Контрактный тест поиска товаров в магазине",
            groups = "api-v2-regress",
            dataProviderClass = RestDataProvider.class,
            dataProvider = "offerOfEachRetailer-parallel")
    public void getOffer(Offer offer) {
        Response response = ApiV1Requests.Offers.GET(offer.getUuid());
        assertStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/Offer.json"));
    }
}
