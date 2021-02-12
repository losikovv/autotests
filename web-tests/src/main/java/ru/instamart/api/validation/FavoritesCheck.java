package instamart.api.validation;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.condition.FavoritesCondition;
import instamart.api.responses.v2.FavoritesListItemsResponse;
import instamart.api.responses.v2.FavoritesSkuListItemResponse;
import io.qameta.allure.Step;

import static org.testng.Assert.*;

public final class FavoritesCheck {

    private final FavoritesCondition favoritesCondition;

    private FavoritesCheck(final FavoritesCondition favoritesCondition) {
        this.favoritesCondition = favoritesCondition;
    }

    @Step("Пустой список избранных товаров")
    public FavoritesCondition emptyFavoritesList() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        assertEquals(favoritesCondition
                .getResponse()
                .as(FavoritesListItemsResponse.class).getItems().size(), 0, "Список избранного не пустой");
        return favoritesCondition;
    }

    @Step("Пустой список Sku товаров в избранном")
    public FavoritesCondition emptySkuFavoritesList() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        assertEquals(favoritesCondition
                .getResponse()
                .as(FavoritesSkuListItemResponse.class).getProductsSkuList().size(), 0, "Список sku товаров не пустой");
        return favoritesCondition;
    }

    @Step("Список Sku товаров в избранном содержит добавленный")
    public FavoritesCondition skuFavoritesList(final int sku) {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        assertTrue(favoritesCondition
                .getResponse()
                .as(FavoritesSkuListItemResponse.class).getProductsSkuList().stream().findFirst().isPresent(), "Список избранного пуст");
        assertEquals(favoritesCondition
                .getResponse()
                .as(FavoritesSkuListItemResponse.class).getProductsSkuList().stream().findFirst().get().intValue(), sku, "В списке добавлен не тот товар");
        return favoritesCondition;
    }

    @Step("Товар был добавлен в избранное")
    public FavoritesCondition itemWasAdded() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        return favoritesCondition;
    }

    @Step("Товар по id не был добавлен в избранное")
    public FavoritesCondition itemWasNotAdded() {
        InstamartApiCheckpoints.assertStatusCode404(favoritesCondition.getResponse());
        return favoritesCondition;
    }

    @Step("Товар по sku был добавлен в избранное")
    public FavoritesCondition itemWasAddedBySku() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        return favoritesCondition;
    }

    @Step("Товар по sku не был добавлен в избранное")
    public FavoritesCondition itemWasNotAddedBySku() {
        InstamartApiCheckpoints.assertStatusCode422(favoritesCondition.getResponse());
        return favoritesCondition;
    }

    @Step("Товар был удален из избранного")
    public FavoritesCondition itemWasRemoved() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        return favoritesCondition;
    }

    @Step("Товар был удален из избранного по его sku")
    public FavoritesCondition itemWasRemovedBySku() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        return favoritesCondition;
    }

    public static FavoritesCheck newCheck(final FavoritesCondition favoritesCondition) {
        return new FavoritesCheck(favoritesCondition);
    }
}
