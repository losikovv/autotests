package ru.instamart.tests.api.v2.endpoints;

import instamart.api.checkpoints.ApiV2Checkpoints;
import instamart.api.common.RestAddresses;
import instamart.api.common.RestBase;
import instamart.api.requests.ApiV2Requests;
import instamart.api.responses.v2.PromotionCardsResponse;
import instamart.api.responses.v2.StoreResponse;
import instamart.api.responses.v2.StoresResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class Stores extends RestBase {

    @CaseId(1)
    @Test(  description = "Получаем магазин",
            groups = {"api-v2-smoke"})
    public void getStore() {
        response = ApiV2Requests.getStores(1);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(StoreResponse.class).getStore(), "Не вернулся магазин");
    }

    @CaseId(7)
    @Test(  description = "Получаем список всех магазинов по указанным координатам",
            groups = {"api-v2-smoke"})
    public void getStoresByCoordinates() {
        response = ApiV2Requests.getStores(
                RestAddresses.Moscow.defaultAddress().getLat(),
                RestAddresses.Moscow.defaultAddress().getLon());
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(12)
    @Test(  description = "Получаем промоакции в магазине",
            groups = {"api-v2-smoke"})
    public void getStorePromotionCards() {
        response = ApiV2Requests.getStorePromotionCards(1);
        ApiV2Checkpoints.assertStatusCode200(response);
        assertNotNull(response.as(PromotionCardsResponse.class).getPromotion_cards(),
                "Не вернулись промоакции магазина");
    }

}
