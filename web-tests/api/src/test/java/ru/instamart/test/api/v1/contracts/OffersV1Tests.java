package ru.instamart.test.api.v1.contracts;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.request.v1.OffersV1Request;
import ru.instamart.api.response.v1.OfferV1Response;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkResponseJsonSchema;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.checkStatusCode200;

@Epic("ApiV1")
@Feature("Эндпоинты, используемые шоппер бэкендом")
public class OffersV1Tests extends RestBase {

    @Story("Поиск товаров")
    @CaseId(111)
    @Test(  description = "Контрактный тест поиска товаров в магазине",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "offerOfEachRetailer-parallel")
    public void getOffer(OfferV1 offer) {
        final Response response = OffersV1Request.GET(offer.getUuid());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OfferV1Response.class);
    }
}
