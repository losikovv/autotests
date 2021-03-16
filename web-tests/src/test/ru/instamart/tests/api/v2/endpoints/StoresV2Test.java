package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestAddresses;
import instamart.api.common.RestBase;
import instamart.api.requests.v2.StoresRequest;
import instamart.api.responses.v2.PromotionCardsResponse;
import instamart.api.responses.v2.StoreResponse;
import instamart.api.responses.v2.StoresResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.assertStatusCode200;
import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Получение списка магазинов")
public final class StoresV2Test extends RestBase {

    @CaseId(1)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Получаем магазин")
    public void getStore() {
        final Response response = StoresRequest.GET(1);
        assertStatusCode200(response);
        assertNotNull(response.as(StoreResponse.class).getStore(), "Не вернулся магазин");
    }

    @CaseId(7)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Получаем список всех магазинов по указанным координатам")
    public void getStoresByCoordinates() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        store.setLon(RestAddresses.Moscow.defaultAddress().getLon());
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(12)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Получаем промо-акции в магазине")
    public void getStorePromotionCards() {
        final Response response = StoresRequest.PromotionCards.GET(1);
        assertStatusCode200(response);
        assertNotNull(response.as(PromotionCardsResponse.class).getPromotion_cards(),
                "Не вернулись промо-акции магазина");
    }

    @CaseId(190)
    @Test
    public void testWithoutLan() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(191)
    @Test
    public void testShippingMethodPickup() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        store.setLon(RestAddresses.Moscow.defaultAddress().getLon());
        store.setShippingMethod("pickup");
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(192)
    @Test
    public void testShippingMethodByCourier() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        store.setLon(RestAddresses.Moscow.defaultAddress().getLon());
        store.setShippingMethod("by_courier");
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(193)
    @Test
    public void testShippingMethodWithInvalidValue() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        store.setLon(RestAddresses.Moscow.defaultAddress().getLon());
        store.setShippingMethod("fake");
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(194)
    @Test
    public void testOperationZoneWithValidId() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        store.setLon(RestAddresses.Moscow.defaultAddress().getLon());
        store.setOperationalZoneId(1);
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(195)
    @Test
    public void testOperationZoneWithInvalidId() {
        final StoresRequest.Store store = new StoresRequest.Store();
        store.setLat(RestAddresses.Moscow.defaultAddress().getLat());
        store.setLon(RestAddresses.Moscow.defaultAddress().getLon());
        store.setOperationalZoneId(999999);
        final Response response = StoresRequest.GET(store);
        assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }
}
