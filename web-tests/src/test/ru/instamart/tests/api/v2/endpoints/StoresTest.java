package ru.instamart.tests.api.v2.endpoints;

import instamart.api.requests.v2.StoresRequest;
import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.common.RestAddresses;
import instamart.api.common.RestBase;
import instamart.api.responses.v2.PromotionCardsResponse;
import instamart.api.responses.v2.StoreResponse;
import instamart.api.responses.v2.StoresResponse;
import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

public class StoresTest extends RestBase {

    @CaseId(1)
    @Test(  description = "Получаем магазин",
            groups = {"api-instamart-smoke"})
    public void getStore() {
        response = StoresRequest.GET(1);
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(StoreResponse.class).getStore(), "Не вернулся магазин");
    }

    @CaseId(7)
    @Test(  description = "Получаем список всех магазинов по указанным координатам",
            groups = {"api-instamart-smoke"})
    public void getStoresByCoordinates() {
        response = StoresRequest.GET(
                RestAddresses.Moscow.defaultAddress().getLat(),
                RestAddresses.Moscow.defaultAddress().getLon());
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(12)
    @Test(  description = "Получаем промоакции в магазине",
            groups = {"api-instamart-smoke"})
    public void getStorePromotionCards() {
        response = StoresRequest.PromotionCards.GET(1);
        InstamartApiCheckpoints.assertStatusCode200(response);
        assertNotNull(response.as(PromotionCardsResponse.class).getPromotion_cards(),
                "Не вернулись промоакции магазина");
    }

}
