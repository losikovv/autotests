package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.model.v2.ItemV2;
import ru.instamart.api.request.v2.FavoritesV2Request;
import ru.instamart.api.response.v2.FavoritesItemV2Response;
import ru.instamart.api.response.v2.FavoritesListItemsV2Response;
import ru.instamart.api.response.v2.FavoritesSkuListItemV2Response;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.*;

@Epic(value = "ApiV2")
@Feature(value = "Избранное")
public class FavoritesListV2Test extends RestBase {

    private final long PRODUCT_ID = 239210;
    private final long PRODUCT_ID_2 = 239211;
    private final int PRODUCT_SKU = 38732;

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @CaseId(13)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получаем пустой список любимых товаров")
    public void testEmptyFavoritesList() {
        final Response response = FavoritesV2Request.GET(1);
        checkStatusCode200(response);
        assertEquals(response.as(FavoritesListItemsV2Response.class).getItems().size(), 0, "Список избранного не пустой");
    }

    @CaseId(128)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Добавление товара в избранное")
    public void testAddItemToFavoritesList() {
        final Response response = FavoritesV2Request.POST(PRODUCT_ID);
        checkStatusCode200(response);
        final ItemV2 item = response.as(FavoritesItemV2Response.class).getItem();
        assertNotNull(item);
    }

    @CaseId(129)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Добавление товара в избранное с несуществующим id")
    public void testNegativeAddItemToFavoritesList() {
        final Response response = FavoritesV2Request.POST(1);
        checkStatusCode404(response);
    }

    @CaseId(130)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Удаление товара из избранного")
    public void testDeleteItemToFavoritesList() {
        Response response = FavoritesV2Request.POST(PRODUCT_ID_2);
        checkStatusCode200(response);
        final ItemV2 item = response.as(FavoritesItemV2Response.class).getItem();
        assertNotNull(item);
        response = FavoritesV2Request.DELETE(item.getId());
        checkStatusCode200(response);
    }

    @CaseId(131)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Получаем пустой список sku любимых товаров")
    public void testEmptySkuFavoritesList() {
        final Response response = FavoritesV2Request.ProductSku.GET();
        checkStatusCode200(response);
        assertEquals(response
                .as(FavoritesSkuListItemV2Response.class).getProductsSkuList().size(), 0, "Список sku товаров не пустой");
    }

    @CaseId(132)
    @Test(groups = {"api-instamart-smoke", "api-instamart-prod"}, description = "Добавление товара в избранное по его Sku")
    public void testAddItemToFavoritesListBySku() {
        final Response response = FavoritesV2Request.ProductSku.POST(PRODUCT_SKU);
        checkStatusCode200(response);
    }

    @CaseId(133)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Добавление товара в избранное с несуществующим Sku")
    public void testNegativeAddItemToFavoritesListBySku() {
        final Response response = FavoritesV2Request.ProductSku.POST(1);
        checkStatusCode422(response);
    }

    @CaseId(134)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod"}, description = "Удаление товара из избранного по sku")
    public void testDeleteItemToFavoritesListBySku() {
        Response response = FavoritesV2Request.ProductSku.POST(PRODUCT_SKU);
        checkStatusCode200(response);
        response = FavoritesV2Request.ProductSku.DELETE(PRODUCT_SKU);
        checkStatusCode200(response);
    }
}
