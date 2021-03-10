package ru.instamart.tests.api.v2.endpoints;

import instamart.api.SessionFactory;
import instamart.api.enums.SessionType;
import instamart.api.requests.v2.FavoritesRequest;
import instamart.api.common.RestBase;
import instamart.api.objects.v2.Item;
import instamart.api.responses.v2.FavoritesItemResponse;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.api.responses.v2.FavoritesSkuListItemResponse;
import io.qameta.allure.*;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static instamart.api.checkpoints.InstamartApiCheckpoints.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Epic(value = "ApiV2")
@Feature(value = "Избранное")
public class FavoritesListTest extends RestBase {

    private final long PRODUCT_ID = 239210;
    private final long PRODUCT_ID_2 = 239211;
    private final int PRODUCT_SKU = 38732;

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.APIV2);
    }

    @CaseId(13)
    @Test(groups = {"api-instamart-regress"})
    @Story("Получаем пустой список любимых товаров")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptyFavoritesList() {
        final Response response = FavoritesRequest.GET(1);
        assertStatusCode200(response);
        assertEquals(response.as(FavoritesListItemsResponse.class).getItems().size(), 0, "Список избранного не пустой");
    }

    @CaseId(128)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Добавление товара в избранное")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddItemToFavoritesList() {
        final Response response = FavoritesRequest.POST(PRODUCT_ID);
        assertStatusCode200(response);
        final Item item = response.as(FavoritesItemResponse.class).getItem();
        assertNotNull(item);
    }

    @CaseId(129)
    @Test(groups = {"api-instamart-regress"})
    @Story("Добавление товара в избранное с несуществующим id")
    @Severity(SeverityLevel.NORMAL)
    public void testNegativeAddItemToFavoritesList() {
        final Response response = FavoritesRequest.POST(1);
        assertStatusCode404(response);
    }

    @CaseId(130)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Удаление товара из избранного")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteItemToFavoritesList() {
        Response response = FavoritesRequest.POST(PRODUCT_ID_2);
        assertStatusCode200(response);
        final Item item = response.as(FavoritesItemResponse.class).getItem();
        assertNotNull(item);
        response = FavoritesRequest.DELETE(item.getId());
        assertStatusCode200(response);
    }

    @CaseId(131)
    @Test(groups = {"api-instamart-regress"})
    @Story("Получаем пустой список sku любимых товаров")
    @Severity(SeverityLevel.NORMAL)
    public void testEmptySkuFavoritesList() {
        final Response response = FavoritesRequest.ProductSku.GET();
        assertStatusCode200(response);
        assertEquals(response
                .as(FavoritesSkuListItemResponse.class).getProductsSkuList().size(), 0, "Список sku товаров не пустой");
    }

    @CaseId(132)
    @Test(groups = {"api-instamart-smoke"})
    @Story("Добавление товара в избранное по его Sku")
    @Severity(SeverityLevel.CRITICAL)
    public void testAddItemToFavoritesListBySku() {
        final Response response = FavoritesRequest.ProductSku.POST(PRODUCT_SKU);
        assertStatusCode200(response);
    }

    @CaseId(133)
    @Test(groups = {"api-instamart-regress"})
    @Story("Добавление товара в избранное с несуществующим Sku")
    @Severity(SeverityLevel.NORMAL)
    public void testNegativeAddItemToFavoritesListBySku() {
        final Response response = FavoritesRequest.ProductSku.POST(1);
        assertStatusCode422(response);
    }

    @CaseId(134)
    @Test(groups = {"api-instamart-regress"})
    @Story("Удаление товара из избранного по sku")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteItemToFavoritesListBySku() {
        Response response = FavoritesRequest.ProductSku.POST(PRODUCT_SKU);
        assertStatusCode200(response);
        response = FavoritesRequest.ProductSku.DELETE(PRODUCT_SKU);
        assertStatusCode200(response);
    }
}
