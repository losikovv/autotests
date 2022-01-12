package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.request.v1.OffersV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.OfferV1Response;
import ru.instamart.api.response.v1.OffersV1Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Офферы")
public class OffersV1Tests extends RestBase {

    private OfferV1 offerForRequest;

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
        offerForRequest = response.as(OfferV1Response.class).getOffer();
    }

    @Story("Поиск товаров")
    @CaseId(1382)
    @Test(  description = "Поиск товаров в магазине по SKU",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "getOffer")
    public void getOfferBySku() {
        final Response response = StoresV1Request.Offers.GET(offerForRequest.getStoreId(), offerForRequest.getProductSku());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersV1Response.class);
        OfferV1 offerFromResponse = response.as(OffersV1Response.class).getOffers().get(0);
        compareTwoObjects(offerFromResponse, offerForRequest);
    }

    @Story("Поиск товаров")
    @CaseId(1383)
    @Test(  description = "Поиск товаров в магазине по нескольким SKU",
            groups = {"api-instamart-regress", "api-instamart-prod"},
            dependsOnMethods = "getOffer")
    public void getOfferBySkus() {
        List<OfferV1> offersFromStore = apiV2.getActiveOffers(offerForRequest.getStore().getUuid()).stream().sorted(Comparator.comparing(OfferV1::getId)).collect(Collectors.toList());
        OfferV1 firstOffer = offersFromStore.get(0);
        OfferV1 secondOffer = offersFromStore.get(1);
        final Response response = StoresV1Request.Offers.GET(firstOffer.getStoreId(), firstOffer.getProductSku() + ";" + secondOffer.getProductSku());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersV1Response.class);
        List<OfferV1> offersFromResponse = response.as(OffersV1Response.class).getOffers().stream().sorted(Comparator.comparing(OfferV1::getId)).collect(Collectors.toList());
        compareTwoObjects(offersFromResponse.size(), 2);
        final SoftAssert softAssert = new SoftAssert();
        compareTwoObjects(offersFromResponse.get(0), firstOffer, softAssert);
        compareTwoObjects(offersFromResponse.get(1), secondOffer, softAssert);
        softAssert.assertAll();
    }

    @Story("Поиск товаров")
    @CaseId(1384)
    @Test(  description = "Поиск товаров в магазине без SKU",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getOfferWithoutSkus() {
        final Response response = StoresV1Request.Offers.GET(EnvironmentProperties.DEFAULT_SID, null);
        checkStatusCode400(response);
        checkErrorText(response, "Не указан обязательный параметр skus");
    }

    @Story("Поиск товаров")
    @CaseId(1385)
    @Test(  description = "Поиск товаров в несуществующем магазине по SKU",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getOfferForNonExistentStore() {
        final Response response = StoresV1Request.Offers.GET(0, "123");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @Story("Поиск товаров")
    @CaseId(1386)
    @Test(  description = "Поиск товаров в магазине по несуществующему SKU",
            groups = {"api-instamart-regress", "api-instamart-prod"})
    public void getOfferByNonExistentSkus() {
        final Response response = StoresV1Request.Offers.GET(EnvironmentProperties.DEFAULT_SID, "000");
        checkStatusCode200(response);
        compareTwoObjects(0, response.as(OffersV1Response.class).getOffers().size());
    }
}
