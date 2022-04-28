package ru.instamart.test.api.v1.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.enums.v2.ProductPriceTypeV2;
import ru.instamart.api.model.common.OfferForRequest;
import ru.instamart.api.model.v1.OfferV1;
import ru.instamart.api.request.v1.OffersV1Request;
import ru.instamart.api.request.v1.ProductsV1Request;
import ru.instamart.api.request.v1.StoresV1Request;
import ru.instamart.api.response.v1.OfferV1Response;
import ru.instamart.api.response.v1.OffersV1Response;
import ru.instamart.jdbc.dao.stf.OffersDao;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data.Generate;
import ru.sbermarket.qase.annotation.CaseIDs;
import ru.sbermarket.qase.annotation.CaseId;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkOffer;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV1")
@Feature("Офферы")
public class OffersV1Tests extends RestBase {

    private OfferV1 offerForRequest;
    private OfferV1 offerFromResponse;

    @BeforeClass(alwaysRun = true)
    public void preconditions() {
        admin.authApi();
    }

    @Story("Поиск товаров")
    @CaseId(111)
    @Test(  description = "Контрактный тест поиска товаров в магазине",
            groups = {"api-instamart-smoke", "api-instamart-prod"},
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
            groups = {"api-instamart-smoke", "api-instamart-prod"},
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
        List<OfferV1> offersFromStore = apiV1.getActiveOffers(offerForRequest.getStore().getUuid()).stream().sorted(Comparator.comparing(OfferV1::getId)).collect(Collectors.toList());
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

    @Story("Офферы")
    @CaseIDs(value = {@CaseId(2221), @CaseId(2222)})
    @Test(description = "Создание оффера",
            groups = {"api-instamart-regress"},
            dataProvider = "priceTypes",
            dataProviderClass = RestDataProvider.class,
            dependsOnMethods = {"getOfferBySku", "getOfferBySkus"})
    public void createOffer(ProductPriceTypeV2 priceType) {
        OfferForRequest offer = OfferForRequest.builder()
                .offer(OfferForRequest.OfferParams.builder()
                        .name("Autotest-" + Generate.literalString(6))
                        .vatRate(0)
                        .itemsPerPack(1)
                        .stock(RandomUtils.nextInt(5, 100))
                        .maxStock(RandomUtils.nextInt(5, 100))
                        .pickupOrder(1)
                        .priceType(priceType.getValue())
                        .published(false)
                        .build())
                .build();
        final Response response = ProductsV1Request.POST(offerForRequest.getPermalink(), offer);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OfferV1Response.class);
        offerFromResponse = response.as(OfferV1Response.class).getOffer();
        checkOffer(offer, offerFromResponse, false);
    }

    @CaseId(2223)
    @Story("Офферы")
    @Test(description = "Редактирование оффера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "createOffer")
    public void editOffer() {
        OfferForRequest offer = OfferForRequest.builder()
                .offer(OfferForRequest.OfferParams.builder()
                        .name("Autotest-" + Generate.literalString(6))
                        .vatRate(0)
                        .itemsPerPack(1)
                        .stock(RandomUtils.nextInt(5, 100))
                        .maxStock(RandomUtils.nextInt(5, 100))
                        .pickupOrder(1)
                        .priceType(ProductPriceTypeV2.PER_ITEM.getValue())
                        .published(true)
                        .build())
                .build();
        final Response response = OffersV1Request.PUT(offerFromResponse.getUuid(), offer);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OfferV1Response.class);
        OfferV1 offerFromResponse = response.as(OfferV1Response.class).getOffer();
        checkOffer(offer, offerFromResponse, true);
    }

    @CaseId(2224)
    @Story("Офферы")
    @Test(description = "Редактирование несуществующего оффера",
            groups = {"api-instamart-regress"})
    public void editNonexistentOffer() {
        final Response response = OffersV1Request.PUT("offerUuid", OfferForRequest.builder().build());
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2225)
    @Story("Офферы")
    @Test(description = "Удаление оффера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "editOffer")
    public void deleteOffer() {
        final Response response = OffersV1Request.DELETE(offerFromResponse.getUuid());
        checkStatusCode200(response);
        checkFieldIsNotEmpty(OffersDao.INSTANCE.findById(offerFromResponse.getId()).get().getDeletedAt(), "дата удаления");
    }

    @CaseId(2226)
    @Story("Офферы")
    @Test(description = "Удаление несуществующего оффера",
            groups = {"api-instamart-regress"})
    public void deleteNonexistentOffer() {
        final Response response = OffersV1Request.DELETE("failedUuuid");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }

    @CaseId(2227)
    @Story("Офферы")
    @Test(description = "Получение оффера",
            groups = {"api-instamart-regress"},
            dependsOnMethods = "getOffer")
    public void getOffersByPermalink() {
        final Response response = ProductsV1Request.GET(offerForRequest.getPermalink());
        checkStatusCode200(response);
        checkResponseJsonSchema(response, OffersV1Response.class);
    }

    @CaseId(2228)
    @Story("Офферы")
    @Test(description = "Получение оффера по несуществующему permalink",
            groups = {"api-instamart-regress"})
    public void getOffersByNonExistentPermalink() {
        final Response response = ProductsV1Request.GET("failed-permalink");
        checkStatusCode404(response);
        checkErrorText(response, "Объект не найден");
    }
}
