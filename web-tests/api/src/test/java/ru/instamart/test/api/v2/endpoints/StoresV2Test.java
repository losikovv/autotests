package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import ru.sbermarket.qase.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestAddresses;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.api.request.v2.StoresV2Request;
import ru.instamart.api.response.v2.PromotionCardsV2Response;
import ru.instamart.api.response.v2.StoreV2Response;
import ru.instamart.api.response.v2.StoresV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.kraken.data_provider.JsonDataProvider;
import ru.instamart.kraken.data_provider.JsonProvider;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Магазины")
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
        final Response response = StoresV2Request.GET(0);
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
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получаем магазин")
    public void testGetStoresWithDefaultSid() {
        final Response response = StoresV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreV2Response.class);
        compareTwoObjects(response.as(StoreV2Response.class).getStore().getId(), EnvironmentProperties.DEFAULT_SID);
    }

    @CaseId(188)
    @JsonDataProvider(path = "data/json_v2/api_v2_negative_store_data.json", type = RestDataProvider.StoreDataRoot.class)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Получить список магазинов с указанием lon или lon")
    public void getStoresData(StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode422(response);
        checkError(response, "lat and lon params are both required");
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
        checkResponseJsonSchema(response, StoresV2Response.class);
        StoresV2Response storesV2Response = response.as(StoresV2Response.class);
        final SoftAssert sa = new SoftAssert();
        sa.assertTrue(storesV2Response.getStoreLabels().isEmpty(), "Stores Labels not empty");
        sa.assertEquals(storesV2Response.getStores().get(0).getId().intValue(), EnvironmentProperties.DEFAULT_SID, "Id магазина отличается");
        sa.assertEquals(storesV2Response.getStores().get(0).getName(), "METRO, Нижний Новгород Нартова", "Наименование отличается");
        sa.assertEquals(storesV2Response.getStores().get(0).getRetailer().getId(), Integer.valueOf(1), "Ретейлер не соответствует");
        sa.assertAll();
    }

    @CaseId(190)
    @JsonDataProvider(path = "data/json_v2/api_v2_positive_store_data.json", type = RestDataProvider.StoreDataRoot.class)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Получение списка магазинов  с валидными значениями")
    public void getStores(StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoresV2Response.class);
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

    @CaseId(2213)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"},
            dataProvider = "forMapFailedTestParams",
            dataProviderClass = RestDataProvider.class,
            description = "Получение магазинов для вывода на карте без обязательных параметров")
    public void failedTestForMap(StoresV2Request.ForMapParams params, String errorMessage) {
        final Response response = StoresV2Request.ForMap.GET(params);
        checkStatusCode422(response);
        checkError(response, errorMessage);
    }

    @CaseId(2212)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получение магазинов для вывода на карте")
    public void testForMap() {
        StoresV2Request.ForMapParams params = StoresV2Request.ForMapParams.builder()
                .bbox("56.291423,43.967728~56.332495,44.058287")
                .build();
        final Response response = StoresV2Request.ForMap.GET(params);

        checkStatusCode200(response);
    }
}
