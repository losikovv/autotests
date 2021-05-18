package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.request.v2.StoresV2Request;
import ru.instamart.api.response.v2.PromotionCardsV2Response;
import ru.instamart.api.response.v2.StoreV2Response;
import ru.instamart.api.response.v2.StoresV2Response;
import ru.instamart.api.dataprovider.RestDataProvider;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode404;

@Epic("ApiV2")
@Feature("Получение списка магазинов")
public final class StoresV2Test extends RestBase {

    @CaseId(1)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Получаем магазин")
    public void getStore() {
        final Response response = StoresV2Request.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(StoreV2Response.class).getStore(), "Не вернулся магазин");
    }

    @CaseId(12)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Получаем промо-акции в магазине")
    public void getStorePromotionCards() {
        final Response response = StoresV2Request.PromotionCards.GET(1);
        checkStatusCode200(response);
        assertNotNull(response.as(PromotionCardsV2Response.class).getPromotionCards(),
                "Не вернулись промо-акции магазина");
    }

    @CaseId(7)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"},
            dataProviderClass = RestDataProvider.class,
            dataProvider = "getStores",
            description = "Получаем список всех магазинов по указанным координатам")
    public void testStoresWithData(final StoresV2Request.Store store) {
        final Response response = StoresV2Request.GET(store);
        checkStatusCode200(response);
        assertNotNull(response.as(StoresV2Response.class).getStores(),
                "Не вернулись магазины по указанным координатам");
    }

    @CaseId(197)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получаем магазин")
    public void testGetStoresWithInvalidSid() {
        final Response response = StoresV2Request.GET(6666);
        checkStatusCode404(response);
    }

    @CaseId(198)
    @Test(groups = {"api-instamart-smoke"}, description = "Статус быстрой доставки с валидным sid")
    public void testGetFastDeliveryStatusWithValidSid() {
        final Response response = StoresV2Request.GET(EnvironmentData.INSTANCE.getDefaultSid());
        checkStatusCode200(response);
        assertFalse(response.as(StoreV2Response.class).getStore().getExpressDelivery(),
                "У магазина не должно быть быстрой доставки");
    }

    @CaseId(199)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Статус быстрой доставки с невалидным sid")
    public void testGetFastDeliveryStatusWithInvalidSid() {
        final Response response = StoresV2Request.GET(6666);
        checkStatusCode404(response);
    }
}
