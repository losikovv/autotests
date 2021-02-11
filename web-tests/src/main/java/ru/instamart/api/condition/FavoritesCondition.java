package instamart.api.condition;

import instamart.api.SessionFactory;
import instamart.api.validation.FavoritesCheck;

public final class FavoritesCondition implements ICondition<FavoritesCondition> {

    private final FavoritesCheck favoritesCheck;
    private String token;

    private FavoritesCondition() {
        this.favoritesCheck = FavoritesCheck.newCheck(this);
    }

    public FavoritesCheck getFavoritesItems(final String sid) {
        return favoritesCheck;
    }

    public FavoritesCheck getAllSkuItemsFromFavorites() {
        return favoritesCheck;
    }

    public FavoritesCheck addToFavorites(final String id) {
        return favoritesCheck;
    }

    public FavoritesCheck addToFavoritesBySku(final String sku) {
        return favoritesCheck;
    }

    public FavoritesCheck removeFromFavorites(final String id) {
        return favoritesCheck;
    }

    public FavoritesCheck removeFromFavoritesBySku(final String sku) {
        return favoritesCheck;
    }

    public static FavoritesCondition newTest() {
        return new FavoritesCondition();
    }

    @Override
    public FavoritesCondition authFromFactory() {
        this.token = SessionFactory.getSessionToken();
        return this;
    }
}
