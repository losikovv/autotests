package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestAddresses;
import instamart.api.responses.v2.PromotionCardsResponse;
import org.testng.annotations.Test;
import instamart.api.requests.ApiV2Requests;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.StoreResponse;
import instamart.api.responses.v2.StoresResponse;

import static org.testng.Assert.*;

public class Stores extends RestBase {

    @Test(  description = "Получаем магазин",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 1)
    public void getStore() {
        response = ApiV2Requests.getStores(1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(StoreResponse.class).getStore(), "Не вернулся магазин");
    }

    @Test(  description = "Получаем список всех магазинов по указанным координатам",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 7)
    public void getStoresByCoordinates() {
        response = ApiV2Requests.getStores(
                RestAddresses.Moscow.defaultAddress().getLat(),
                RestAddresses.Moscow.defaultAddress().getLon());

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @Test(  description = "Получаем промоакции в магазине",
            groups = {"rest-smoke","rest-v2-smoke"},
            priority = 12)
    public void getStorePromotionCards() {
        response = ApiV2Requests.getStorePromotionCards(1);

        assertEquals(response.getStatusCode(), 200);
        assertNotNull(response.as(PromotionCardsResponse.class).getPromotion_cards(),
                "Не вернулись промоакции магазина");
    }

}
