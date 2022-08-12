package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.ItemV2;
import ru.instamart.api.request.v2.FavoritesV2Request;
import ru.instamart.api.response.v2.FavoritesItemV2Response;
import ru.instamart.api.response.v2.FavoritesListItemsV2Response;
import ru.instamart.api.response.v2.FavoritesSkuListItemV2Response;
import ru.instamart.api.response.v2.ProductSkuV2Response;
import ru.sbermarket.qase.annotation.CaseId;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.BaseApiCheckpoints.checkError;
import static ru.instamart.api.checkpoint.StatusCodeCheckpoints.*;

@Epic(value = "ApiV2")
@Feature(value = "Избранные товары")
public class FavoritesListV2Test extends RestBase {

    private final long PRODUCT_ID = 239210;
    private final long PRODUCT_ID_2 = 239211;
    private final int PRODUCT_SKU = 38732;

    @BeforeClass(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2);
    }

    @Deprecated
    @Test(groups = {}, description = "Получаем пустой список любимых товаров")
    public void testEmptyFavoritesList() {
        final Response response = FavoritesV2Request.GET(1);
        checkStatusCode200(response);
        assertEquals(response.as(FavoritesListItemsV2Response.class).getItems().size(), 0, "Список избранного не пустой");
    }

    @Deprecated
    @Test(groups = {}, description = "Добавление товара в избранное")
    public void testAddItemToFavoritesList() {
        final Response response = FavoritesV2Request.POST(PRODUCT_ID);
        checkStatusCode200(response);
        final ItemV2 item = response.as(FavoritesItemV2Response.class).getItem();
        assertNotNull(item, "Избранное вернуло пустое значение");
    }

    @Deprecated
    @Test(groups = {}, description = "Добавление товара в избранное с несуществующим id")
    public void testNegativeAddItemToFavoritesList() {
        final Response response = FavoritesV2Request.POST(1);
        checkStatusCode404(response);
    }

    @Deprecated
    @Test(groups = {}, description = "Удаление товара из избранного")
    public void testDeleteItemToFavoritesList() {
        Response response = FavoritesV2Request.POST(PRODUCT_ID_2);
        checkStatusCode200(response);
        final ItemV2 item = response.as(FavoritesItemV2Response.class).getItem();
        assertNotNull(item, "Избранное вернуло пустое значение");
        response = FavoritesV2Request.DELETE(item.getId());
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {}, description = "Получаем пустой список sku любимых товаров")
    public void testEmptySkuFavoritesList() {
        final Response response = FavoritesV2Request.ProductSku.GET();
        checkStatusCode200(response);
        assertEquals(response
                .as(FavoritesSkuListItemV2Response.class).getProductsSkuList().size(), 0, "Список sku товаров не пустой");
    }

    @Deprecated
    @Test(groups = {}, description = "Добавление товара в избранное по его Sku")
    public void testAddItemToFavoritesListBySku() {
        final Response response = FavoritesV2Request.ProductSku.POST(PRODUCT_SKU);
        checkStatusCode200(response);
    }

    @Deprecated
    @Test(groups = {}, description = "Добавление товара в избранное с несуществующим Sku")
    public void testNegativeAddItemToFavoritesListBySku() {
        final Response response = FavoritesV2Request.ProductSku.POST(1);
        checkStatusCode422(response);
    }

    @Deprecated
    @Test(groups = {}, description = "Удаление товара из избранного по sku")
    public void testDeleteItemToFavoritesListBySku() {
        Response response = FavoritesV2Request.ProductSku.POST(PRODUCT_SKU);
        checkStatusCode200(response);
        response = FavoritesV2Request.ProductSku.DELETE(PRODUCT_SKU);
        checkStatusCode200(response);
    }

    @CaseId(788)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получаем пустой список любимых товаров без обязательного параметра sid")
    public void testEmptyFavoritesList400() {
        final Response response = FavoritesV2Request.GET();
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'sid'");
    }

    @CaseId(788)
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Получаем пустой список любимых товаров без обязательного параметра sid")
    public void testEmptyFavoritesListWithSidParams400() {
        final Response response = FavoritesV2Request.GET("");
        checkStatusCode400(response);
        checkError(response, "Отсутствует обязательный параметр 'sid'");
    }

    @CaseId(524)
    @Story("Список SKU товаров из избранного")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Список SKU товаров из избранного. В избранном нет товаров")
    public void getFavoritesSku() {
        final Response response = FavoritesV2Request.ProductSku.GET();
        checkStatusCode200(response);
        ProductSkuV2Response productSkuV2Response = response.as(ProductSkuV2Response.class);
        assertTrue(productSkuV2Response.getProductsSku().isEmpty(), "Избранное не пустое");
    }

    @CaseId(526)
    @Story("Добавить товар в избранное")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Добавить товар в избранное с несуществующим id")
    public void addFavoritesList404() {
        final Response response = FavoritesV2Request.POST("invalidNumber_0120102012");
        checkStatusCode404(response);
        checkError(response, "Продукт не существует");
    }

    @CaseId(528)
    @Story("Удаление товара из избранного")
    @Test(groups = {"api-instamart-regress", "api-instamart-prod", "api-v2"},
            description = "Удаление товара из избранного с несуществующим id")
    public void deleteFavoritesList404() {
        final Response response = FavoritesV2Request.DELETE("invalidNumber_0120102012");
        checkStatusCode404(response);
        checkError(response, "Элемент списка не существует");
    }
}
