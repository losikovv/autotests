package instamart.api.validation;

import instamart.api.checkpoints.InstamartApiCheckpoints;
import instamart.api.condition.FavoritesCondition;
import instamart.api.responses.v2.FavoritesListItemsResponse;

import static org.testng.Assert.assertNotNull;

public final class FavoritesCheck {

    private final FavoritesCondition favoritesCondition;

    private FavoritesCheck(final FavoritesCondition favoritesCondition) {
        this.favoritesCondition = favoritesCondition;
    }

    public FavoritesCondition emptyFavoritesList() {
        InstamartApiCheckpoints.assertStatusCode200(favoritesCondition.getResponse());
        assertNotNull(favoritesCondition.getResponse().as(FavoritesListItemsResponse.class).getItems(), "Не вернулись любимые товары");
        return favoritesCondition;
    }

    public FavoritesCondition emptySkuFavoritesList() {
        return favoritesCondition;
    }

    public FavoritesCondition itemWasAdded() {
        return favoritesCondition;
    }

    public FavoritesCondition itemWasAddedBySku() {
        return favoritesCondition;
    }

    public FavoritesCondition itemWasRemoved() {
        return favoritesCondition;
    }

    public FavoritesCondition itemWasRemovedBySku() {
        return favoritesCondition;
    }

    public static FavoritesCheck newCheck(final FavoritesCondition favoritesCondition) {
        return new FavoritesCheck(favoritesCondition);
    }
}
