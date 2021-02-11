package instamart.api.validation;

import instamart.api.condition.FavoritesCondition;

public final class FavoritesCheck {

    private final FavoritesCondition favoritesCondition;

    private FavoritesCheck(final FavoritesCondition favoritesCondition) {
        this.favoritesCondition = favoritesCondition;
    }

    public FavoritesCondition emptyFavoritesList() {
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
