package ru.instamart.tests.api.v2.endpoints;

import instamart.api.common.RestBase;
import instamart.api.requests.v2.StoresRequest;
import instamart.api.responses.v2.PromotionCardsResponse;
import instamart.api.responses.v2.StoreResponse;
import instamart.api.responses.v2.StoresResponse;
import instamart.core.testdata.dataprovider.RestDataProvider;
import instamart.ui.common.pagesdata.EnvironmentData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode200;
import static instamart.api.checkpoints.InstamartApiCheckpoints.checkStatusCode404;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

@Epic("ApiV2")
@Feature("Получение списка магазинов")
public final class StoresV2Test extends RestBase {

    @CaseId(1)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Получаем магазин")
    public void getStore() {
        final Response response = StoresRequest.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(StoreResponse.class).getStore(), "Не вернулся магазин");
    }

    @CaseId(12)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Получаем промо-акции в магазине")
    public void getStorePromotionCards() {
        final Response response = StoresRequest.PromotionCards.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(PromotionCardsResponse.class).getPromotion_cards(),
                "Не вернулись промо-акции магазина");
    }

    @CaseId(7)
    @Test(groups = {"api-instamart-smoke"}, dataProviderClass = RestDataProvider.class, dataProvider = "getStores")
    @Story("Получаем список всех магазинов по указанным координатам")
    public void testStoresWithData(final StoresRequest.Store store) {
        final Response response = StoresRequest.GET(store);
        checkStatusCode200(response);
        assertNotNull(response.as(StoresResponse.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(197)
    @Test(groups = {"api-instamart-regress"})
    @Story("Получаем магазин")
    public void testGetStoresWithInvalidSid() {
        final Response response = StoresRequest.GET(6666);
        checkStatusCode404(response);
    }

    @CaseId(198)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Статус быстрой доставки с валидным sid")
    public void testGetFastDeliveryStatusWithValidSid() {
        final Response response = StoresRequest.GET(EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        assertFalse(response.as(StoreResponse.class).getStore().getExpress_delivery(),
                "У магазина не должно быть быстрой доставки");
    }

    @CaseId(199)
    @Test(groups = {"api-instamart-regress"})
    @Story("Статус быстрой доставки с невалидным sid")
    public void testGetFastDeliveryStatusWithInvalidSid() {
        final Response response = StoresRequest.GET(6666);
        checkStatusCode404(response);
    }
}
