package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.StoresV2Request;
import ru.instamart.api.response.v2.DeliveryWindowsV2Response;
import ru.instamart.api.response.v2.PromotionCardsV2Response;
import ru.instamart.api.response.v2.StoreV2Response;
import ru.instamart.api.response.v2.StoresV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkFieldIsNotEmpty;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.errorAssert;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic("ApiV2")
@Feature("Получение списка магазинов")
public final class StoresV2Test extends RestBase {

    @Deprecated
    @Test(groups = {}, description = "Получаем магазин")
    public void getStore() {
        final Response response = StoresV2Request.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(StoreV2Response.class).getStore(), "Не вернулся магазин");
    }

    @Deprecated
    @Test(groups = {}, description = "Получаем промо-акции в магазине")
    public void getStorePromotionCards() {
        final Response response = StoresV2Request.PromotionCards.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(PromotionCardsV2Response.class).getPromotionCards(),
                "Не вернулись промо-акции магазина");
    }

    @Deprecated
    @Test(groups = {},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "getStores",
            description = "Получаем список всех магазинов по указанным координатам")
    public void testStoresWithData(final StoresV2Request.Store store,
                                   final int statusCode) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode(response, statusCode);
        if (statusCode == 200) assertNotNull(response.as(StoresV2Response.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(197)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получаем магазин")
    public void testGetStoresWithInvalidSid() {
        final Response response = StoresV2Request.GET(6666);
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(groups = {}, description = "Статус быстрой доставки с валидным sid")
    public void testGetFastDeliveryStatusWithValidSid() {
        final Response response = StoresV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        assertFalse(response.as(StoreV2Response.class).getStore().getExpressDelivery(),
                "У магазина не должно быть быстрой доставки");
    }

    @Deprecated
    @Test(groups = {}, description = "Статус быстрой доставки с невалидным sid")
    public void testGetFastDeliveryStatusWithInvalidSid() {
        final Response response = StoresV2Request.GET(6666);
        checkStatusCode404(response);
    }

    @CaseId(196)
    @Test(groups = {"api-instamart-regress"}, description = "Получаем магазин")
    public void testGetStoresWithDefaultSid() {
        final Response response = StoresV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("schemas/api_v2/Store.json"));
        assertEquals(response.as(StoreV2Response.class).getStore().getId().intValue(), EnvironmentProperties.DEFAULT_SID, "Id магазина не совпадает");

    }

    @CaseId(188)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "storeData",
            dataProviderClass = RestDataProvider.class,
            description = "Получить список магазинов с указанием lon или lon")
    public void getStoresData(StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode422(response);
        errorAssert(response, "lat and lon params are both required");
    }

    @CaseId(189)
    @Test(groups = {"api-instamart-regress"},
            description = "Получение списка магазинов с указанием lat и lon")
    public void getStoresWithLatAndLon() {
        final Response response = StoresV2Request.GET(
                StoresV2Request.Store.builder()
                        .lat(RestAddresses.NizhnyNovgorod.defaultAddress().getLat())
                        .lon(RestAddresses.NizhnyNovgorod.defaultAddress().getLon())
                        .build()
        );
        checkStatusCode200(response);
        StoresV2Response storesV2Response = response.as(StoresV2Response.class);
        final SoftAssert sa = new SoftAssert();
        sa.assertFalse(storesV2Response.getStores().isEmpty(), "Stores is missed");
        sa.assertTrue(storesV2Response.getStoreLabels().isEmpty(), "Stores Labels not empty");
        sa.assertEquals(storesV2Response.getStores().get(0).getId().intValue(), EnvironmentProperties.DEFAULT_SID, "Id магазина отличается");
        sa.assertEquals(storesV2Response.getStores().get(0).getName(), "METRO, Нижний Новгород Нартова", "Наименование отличается");
        sa.assertEquals(storesV2Response.getStores().get(0).getRetailer().getId(), Integer.valueOf(1), "Ретейлер не соответствует");
        sa.assertAll();
    }

    @CaseId(190)
    @Test(groups = {"api-instamart-regress"},
            dataProvider = "storeDataWithLatAndLon",
            dataProviderClass = RestDataProvider.class,
            description = "Получение списка магазинов  с валидными значениями")
    public void getStores(StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode200(response);
        checkFieldIsNotEmpty(response.as(StoresV2Response.class).getStores(), "магазины");
    }

    @Deprecated
    @Test(groups = {},
            description = "Получение списка магазинов  с невалидным shippingMethod")
    public void getStoresNOtValidShippingMethod() {
        final Response response = StoresV2Request.GET(StoresV2Request.Store.builder()
                .lat(RestAddresses.NizhnyNovgorod.defaultAddress().getLat())
                .lon(RestAddresses.NizhnyNovgorod.defaultAddress().getLon())
                .shippingMethod("notValidShippingMethod")
                .build()
        );
        checkStatusCode200(response);
        assertFalse(response.as(StoresV2Response.class).getStores().isEmpty(), "Stores is missed");
    }

    @Deprecated
    @Test(groups = {},
            description = "Получение списка магазинов  с невалидным shippingMethod")
    public void getStoresNOtValidOperationalZoneId() {
        final Response response = StoresV2Request.GET(StoresV2Request.Store.builder()
                .lat(RestAddresses.Moscow.defaultAddress().getLat())
                .lon(RestAddresses.Moscow.defaultAddress().getLon())
                .operationalZoneId(-1)
                .build()
        );
        checkStatusCode200(response);
        assertTrue(response.as(StoresV2Response.class).getStores().isEmpty(), "Stores is missed");
    }
}
