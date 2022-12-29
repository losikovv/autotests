package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
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
import io.qameta.allure.TmsLink;

import static org.testng.Assert.*;
import static ru.instamart.api.Group.API_INSTAMART_PROD;
import static ru.instamart.api.Group.API_INSTAMART_REGRESS;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.*;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic("ApiV2")
@Feature("Магазины")
public final class StoresV2Test extends RestBase {

    @Deprecated
    @Test(description = "Получаем магазин")
    public void getStore() {
        final Response response = StoresV2Request.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(StoreV2Response.class).getStore(), "Не вернулся магазин");
    }

    @Deprecated
    @Test(description = "Получаем промо-акции в магазине")
    public void getStorePromotionCards() {
        final Response response = StoresV2Request.PromotionCards.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(PromotionCardsV2Response.class).getPromotionCards(),
                "Не вернулись промо-акции магазина");
    }

    @Deprecated
    @Test(dataProviderClass = RestDataProvider.class,
            dataProvider = "getStores",
            description = "Получаем список всех магазинов по указанным координатам")
    public void testStoresWithData(final StoresV2Request.Store store,
                                   final int statusCode) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode(response, statusCode);
        if (statusCode == 200) assertNotNull(response.as(StoresV2Response.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @TmsLink("197")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получаем магазин")
    public void testGetStoresWithInvalidSid() {
        final Response response = StoresV2Request.GET(0);
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(description = "Статус быстрой доставки с валидным sid")
    public void testGetFastDeliveryStatusWithValidSid() {
        final Response response = StoresV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        Allure.step("Проверка поля express_delivery", () -> assertFalse(response.as(StoreV2Response.class).getStore().getExpressDelivery(),
                "У магазина не должно быть быстрой доставки"));
    }

    @Deprecated
    @Test(description = "Статус быстрой доставки с невалидным sid")
    public void testGetFastDeliveryStatusWithInvalidSid() {
        final Response response = StoresV2Request.GET(6666);
        checkStatusCode404(response);
    }

    @TmsLink("196")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получаем магазин")
    public void testGetStoresWithDefaultSid() {
        final Response response = StoresV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoreV2Response.class);
        Allure.step("Проверка id у store", () -> compareTwoObjects(response.as(StoreV2Response.class).getStore().getId(), EnvironmentProperties.DEFAULT_SID));
    }

    @TmsLink("188")
    @JsonDataProvider(path = "data/json_v2/api_v2_negative_store_data.json", type = RestDataProvider.StoreDataRoot.class)
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Получить список магазинов с отсутствующими lat или/и lon")
    public void getStoresData(StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode422(response);
        checkError(response, "lat and lon params are both required");
    }

    @TmsLink("189")
    @Test(groups = {API_INSTAMART_REGRESS, "api-v2", "api-bff"},
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
        Allure.step("Проверка store", ()->{
            final SoftAssert sa = new SoftAssert();
            sa.assertTrue(storesV2Response.getStoreLabels().isEmpty(), "Stores Labels not empty");
            sa.assertEquals(storesV2Response.getStores().get(0).getName(), "METRO, Нижний Новгород Нартова", "Наименование отличается");
            sa.assertEquals(storesV2Response.getStores().get(0).getRetailer().getId(), Integer.valueOf(1), "Ретейлер не соответствует");
            sa.assertAll();
        });
    }

    @TmsLink("190")
    @JsonDataProvider(path = "data/json_v2/api_v2_positive_store_data.json", type = RestDataProvider.StoreDataRoot.class)
    @Test(groups = {"api-instamart-smoke", API_INSTAMART_PROD, "api-v2", "api-bff"},
            dataProvider = "json",
            dataProviderClass = JsonProvider.class,
            description = "Получение списка магазинов с валидными значениями")
    public void getStores(StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode200(response);
        checkResponseJsonSchema(response, StoresV2Response.class);
    }

    @Deprecated
    @Test(groups = {},
            description = "Получение списка магазинов с невалидным shippingMethod")
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
            description = "Получение списка магазинов с невалидным shippingMethod")
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

    @TmsLink("2213")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            dataProvider = "forMapFailedTestParams",
            dataProviderClass = RestDataProvider.class,
            description = "Получение магазинов для вывода на карте без обязательных параметров")
    public void failedTestForMap(StoresV2Request.ForMapParams params, String errorMessage) {
        final Response response = StoresV2Request.ForMap.GET(params);
        checkStatusCode422(response);
        checkError(response, errorMessage);
    }

    @TmsLink("2212")
    @Test(groups = {API_INSTAMART_REGRESS, API_INSTAMART_PROD, "api-v2", "api-bff"},
            description = "Получение магазинов для вывода на карте")
    public void testForMap() {
        StoresV2Request.ForMapParams params = StoresV2Request.ForMapParams.builder()
                .bbox("56.291423,43.967728~56.332495,44.058287")
                .build();
        final Response response = StoresV2Request.ForMap.GET(params);
        checkStatusCode200(response);
    }
}
