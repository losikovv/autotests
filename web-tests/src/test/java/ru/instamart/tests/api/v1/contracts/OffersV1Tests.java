package ru.instamart.tests.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.objects.v1.OfferV1;
import ru.instamart.api.requests.v1.OffersV1Request;
import ru.instamart.core.testdata.dataprovider.RestDataProvider;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static ru.instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class OffersV1Tests extends RestBase {

    @Feature("Поиск товаров")
    @CaseId(111)
    @Test(  description = "Контрактный тест поиска товаров в магазине",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "offerOfEachRetailer-parallel")
    public void getOffer(OfferV1 offer) {
        Response response = OffersV1Request.GET(offer.getUuid());
        checkStatusCode200(response);
        response.then().body(matchesJsonSchemaInClasspath("schemas/api_v1/Offer.json"));
    }
}
