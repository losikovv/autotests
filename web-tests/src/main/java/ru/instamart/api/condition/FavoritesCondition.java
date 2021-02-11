package instamart.api.condition;

import instamart.api.SessionFactory;
import instamart.api.action.Favorites;
import instamart.api.validation.FavoritesCheck;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public final class FavoritesCondition implements ICondition<FavoritesCondition> {

    private final FavoritesCheck favoritesCheck;
    private Response response;

    private FavoritesCondition() {
        this.favoritesCheck = FavoritesCheck.newCheck(this);
    }

    @Step("Получить список избранного")
    public FavoritesCheck getFavoritesItems(final int sid) {
        response = Favorites.GET(SessionFactory.getSession().getToken(), sid);
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

    public static void makeSession() {
        new FavoritesCondition().createSession();
    }

    public Response getResponse() {
        return response;
    }
}
