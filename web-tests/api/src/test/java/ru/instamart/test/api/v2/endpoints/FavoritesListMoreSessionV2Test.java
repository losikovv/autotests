package ru.instamart.test.api.v2.endpoints;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qase.api.annotation.CaseId;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import ru.instamart.api.common.RestBase;
import ru.instamart.api.enums.SessionType;
import ru.instamart.api.factory.SessionFactory;
import ru.instamart.api.model.v2.ProductV2;
import ru.instamart.api.request.v2.FavoritesV2Request;
import ru.instamart.api.response.v2.FavoritesItemV2Response;
import ru.instamart.api.response.v2.FavoritesListItemsV2Response;
import ru.instamart.api.response.v2.ProductSkuV2Response;
import ru.instamart.kraken.config.EnvironmentProperties;

import static org.testng.Assert.*;
import static ru.instamart.api.checkpoint.InstamartApiCheckpoints.checkStatusCode200;

@Epic(value = "ApiV2")
@Feature(value = "Избранные товары")
public class FavoritesListMoreSessionV2Test extends RestBase {


    @BeforeMethod(alwaysRun = true)
    @Story("Создание сессии")
    public void preconditions() {
        SessionFactory.makeSession(SessionType.API_V2_FB);
    }

    @CaseId(517)
    @Story("Получить список избранных товаров")
    @Test(groups = {"api-instamart-regress"},
            description = "Получить список избранных товаров. В избранном есть товары")
    public void getFavoritesItem() {
        FavoritesItemV2Response product = apiV2.addFavoritesProductBySid(EnvironmentProperties.DEFAULT_SID);
        final Response response = FavoritesV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        FavoritesListItemsV2Response favorites = response.as(FavoritesListItemsV2Response.class);
        assertEquals(favorites.getItems().get(0), product.getItem(), "data mismatch");
    }

    @Deprecated
    @Story("Получить список избранных товаров")
    @Test(  groups = {},
            description = "Получить список избранных товаров. Отображение более 30 товаров на странице.")
    public void getFavoritesMore30Items() {
        var products = apiV2.addFavoritesQtyListProductBySid(EnvironmentProperties.DEFAULT_SID, 32);
        final Response response = FavoritesV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        FavoritesListItemsV2Response favoritesListItemsV2Response = response.as(FavoritesListItemsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(favoritesListItemsV2Response.getItems().size(), 30, "");
        favoritesListItemsV2Response.getItems()
                .forEach(itemV2 -> {
                    softAssert.assertTrue(products.stream().anyMatch(prd -> itemV2.getProduct().equals(prd)), itemV2.getProduct().getId() + " product mismatch");
                    softAssert.assertTrue(products.stream().anyMatch(prd -> itemV2.getName().equals(prd.getName())), itemV2.getProduct().getName() + " product name mismatch");
                    softAssert.assertTrue(products.stream().anyMatch(prd -> itemV2.getSku().equals(prd.getSku())), itemV2.getProduct().getSku() + " product sku mismatch");
                });
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getCurrentPage(), Integer.valueOf(1), "current_page don't equals 1");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getNextPage(), Integer.valueOf(2), "next_page don't equals 2");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getTotalPages(), Integer.valueOf(2), "total_pages don't equals 2");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getPerPage(), Integer.valueOf(30), "per_page don't equals 30");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getTotalCount(), Integer.valueOf(32), "total_count don't equals 32");
        softAssert.assertAll();
    }

    @CaseId(520)
    @Story("Получить список избранных товаров")
    @Test(groups = {"api-instamart-regress"},
            description = "Получаем пустой список любимых товаров у дефолтного магазина")
    public void emptyFavoritesForDefaultSid() {
        final Response response = FavoritesV2Request.GET(EnvironmentProperties.DEFAULT_SID);
        checkStatusCode200(response);
        FavoritesListItemsV2Response favoritesListItemsV2Response = response.as(FavoritesListItemsV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(favoritesListItemsV2Response.getItems().size(), 0, "Список избранного не пустой");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getCurrentPage(), Integer.valueOf(1), "total_pages не равен 1");
        softAssert.assertNull(favoritesListItemsV2Response.getMeta().getNextPage(), "next_page не null");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getTotalPages(), Integer.valueOf(0), "total_pages не равен 0");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getPerPage(), Integer.valueOf(30), "per_page не равен 30");
        softAssert.assertEquals(favoritesListItemsV2Response.getMeta().getTotalCount(), Integer.valueOf(0), "total_count не равен 30");
        softAssert.assertAll();
    }

    @CaseId(522)
    @Story("Список SKU товаров из избранного")
    @Test(groups = {"api-instamart-regress"},
            description = "Список SKU товаров из избранного. Один товар в избранном")
    public void getFavoritesSku() {
        var favorites = apiV2.addFavoritesProductBySid(EnvironmentProperties.DEFAULT_SID);
        Response response = FavoritesV2Request.ProductSku.GET();
        checkStatusCode200(response);
        ProductSkuV2Response productSkuV2Response = response.as(ProductSkuV2Response.class);
        assertEquals(productSkuV2Response.getProductsSku().get(0), favorites.getItem().getSku(), "product sku mismatch");
    }

    @CaseId(523)
    @Story("Список SKU товаров из избранного")
    @Test(groups = {"api-instamart-regress"},
            description = "Список SKU товаров из избранного. 3 товар в избранном")
    public void getFavoritesSku3Items() {
        apiV2.addFavoritesQtyListProductBySid(EnvironmentProperties.DEFAULT_SID, 3);
        Response response = FavoritesV2Request.ProductSku.GET();
        checkStatusCode200(response);
        ProductSkuV2Response productSkuV2Response = response.as(ProductSkuV2Response.class);
        assertFalse(productSkuV2Response.getProductsSku().isEmpty(), "Избранное пустое");
    }

    @CaseId(525)
    @Story("Добавить товар в избранное")
    @Test(groups = {"api-instamart-regress"},
            description = "Добавить товар в избранное с существующим id")
    public void addFavoritesList200() {
        ProductV2 product = apiV2.getProductFromEachDepartmentInStore(EnvironmentProperties.DEFAULT_SID).get(0);
        final Response response = FavoritesV2Request.POST(product.getId());
        checkStatusCode200(response);
        FavoritesItemV2Response favorites = response.as(FavoritesItemV2Response.class);
        final SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(favorites.getItem().getId(), "Id товара не совпадает");
        softAssert.assertEquals(favorites.getItem().getProduct().getId(), product.getId(), "Id товара не совпадает");
        softAssert.assertEquals(favorites.getItem().getSku(), product.getSku(), "sku товара не совпадает");
        softAssert.assertEquals(favorites.getItem().getName(), product.getName(), "name товара не совпадает");
        softAssert.assertEquals(favorites.getItem().getProduct(), product, " product товара не совпадает");
        softAssert.assertAll();
    }


    @CaseId(527)
    @Story("Удаление товара из избранного")
    @Test(groups = {"api-instamart-regress"},
            description = "Удаление товара из избранного с существующим id")
    public void deleteFavoritesList200() {
        var favorites = apiV2.addFavoritesProductBySid(EnvironmentProperties.DEFAULT_SID);
        final Response response = FavoritesV2Request.DELETE(favorites.getItem().getId());
        checkStatusCode200(response);
        assertTrue(response.getBody().asString().isEmpty(), "Ответ не пустой");
    }
}
