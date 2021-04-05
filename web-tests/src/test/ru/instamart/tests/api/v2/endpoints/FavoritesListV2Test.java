package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.common.RestBase;
import instamart.api.enums.SessionType;
import instamart.api.objects.v2.Item;
import instamart.api.requests.v2.FavoritesRequest;
import instamart.api.responses.v2.FavoritesItemResponse;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.api.responses.v2.FavoritesSkuListItemResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Epic(value = "ApiV2")
@Feature(value = "Избранное")
public class FavoritesListV2Test extends RestBase {

    private final long PRODUCT_ID = 239210;
    private final long PRODUCT_ID_2 = 239211;
    private final int PRODUCT_SKU = 38732;

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(13)
    @Test(groups = {"api-instamart-regress"}, description = "Получаем пустой список любимых товаров")
    public void testEmptyFavoritesList() {
        final Response response = FavoritesRequest.GET(1);
        checkStatusCode200(response);
        assertEquals(response.as(FavoritesListItemsResponse.class).getItems().size(), 0, "Список избранного не пустой");
    }

    @CaseId(128)
    @Test(groups = {"api-instamart-smoke"}, description = "Добавление товара в избранное")
    public void testAddItemToFavoritesList() {
        final Response response = FavoritesRequest.POST(PRODUCT_ID);
        checkStatusCode200(response);
        final Item item = response.as(FavoritesItemResponse.class).getItem();
        assertNotNull(item);
    }

    @CaseId(129)
    @Test(groups = {"api-instamart-regress"}, description = "Добавление товара в избранное с несуществующим id")
    public void testNegativeAddItemToFavoritesList() {
        final Response response = FavoritesRequest.POST(1);
        checkStatusCode404(response);
    }

    @CaseId(130)
    @Test(groups = {"api-instamart-smoke"}, description = "Удаление товара из избранного")
    public void testDeleteItemToFavoritesList() {
        Response response = FavoritesRequest.POST(PRODUCT_ID_2);
        checkStatusCode200(response);
        final Item item = response.as(FavoritesItemResponse.class).getItem();
        assertNotNull(item);
        response = FavoritesRequest.DELETE(item.getId());
        checkStatusCode200(response);
    }

    @CaseId(131)
    @Test(groups = {"api-instamart-regress"}, description = "Получаем пустой список sku любимых товаров")
    public void testEmptySkuFavoritesList() {
        final Response response = FavoritesRequest.ProductSku.GET();
        checkStatusCode200(response);
        assertEquals(response
                .as(FavoritesSkuListItemResponse.class).getProductsSkuList().size(), 0, "Список sku товаров не пустой");
    }

    @CaseId(132)
    @Test(groups = {"api-instamart-smoke"}, description = "Добавление товара в избранное по его Sku")
    public void testAddItemToFavoritesListBySku() {
        final Response response = FavoritesRequest.ProductSku.POST(PRODUCT_SKU);
        checkStatusCode200(response);
    }

    @CaseId(133)
    @Test(groups = {"api-instamart-regress"}, description = "Добавление товара в избранное с несуществующим Sku")
    public void testNegativeAddItemToFavoritesListBySku() {
        final Response response = FavoritesRequest.ProductSku.POST(1);
        checkStatusCode422(response);
    }

    @CaseId(134)
    @Test(groups = {"api-instamart-regress"}, description = "Удаление товара из избранного по sku")
    public void testDeleteItemToFavoritesListBySku() {
        Response response = FavoritesRequest.ProductSku.POST(PRODUCT_SKU);
        checkStatusCode200(response);
        response = FavoritesRequest.ProductSku.DELETE(PRODUCT_SKU);
        checkStatusCode200(response);
    }
}
