package instamart.api.condition;

import instamart.api.SessionFactory;
import instamart.api.action.Favorites;
import instamart.api.validation.FavoritesCheck;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public final class FavoritesCondition implements ICondition<FavoritesCondition> {

    private final FavoritesCheck favoritesCheck;
    private Response response;
    private long addedItemId;

    private FavoritesCondition() {
        this.favoritesCheck = FavoritesCheck.newCheck(this);
    }

    @Step("Получить список избранного {sid}")
    public FavoritesCheck getFavoritesItems(final int sid) {
        response = Favorites.getAllFavoriteItems(SessionFactory.getSession().getToken(), sid);
        return favoritesCheck;
    }

    @Step("Получить список sku товаров из избранного")
    public FavoritesCheck getAllSkuItemsFromFavorites() {
        response = Favorites.getAllFavoriteItemsBySku(SessionFactory.getSession().getToken());
        return favoritesCheck;
    }

    @Step("Добавить товар {id} в избранное")
    public FavoritesCheck addToFavorites(final long id) {
        response = Favorites.addFavoriteItem(SessionFactory.getSession().getToken(), id);
        return favoritesCheck;
    }

    @Step("Добавить товар в избранное по его sku {sku}")
    public FavoritesCheck addToFavoritesBySku(final int sku) {
        response = Favorites.addFavoriteItemBySku(SessionFactory.getSession().getToken(), sku);
        return favoritesCheck;
    }

    @Step("Удалить товар из избранного")
    public FavoritesCheck removeFirstItemFromFavorites() {
        response = Favorites.deleteFavoriteItem(SessionFactory.getSession().getToken(), addedItemId);
        return favoritesCheck;
    }

    @Step("Удалить товар из избранного по его sku")
    public FavoritesCheck removeFromFavoritesBySku(final int sku) {
        response = Favorites.deleteFavoriteItemBySku(SessionFactory.getSession().getToken(), sku);
        return favoritesCheck;
    }

    public static FavoritesCondition newTest() {
        return new FavoritesCondition();
    }

    public Response getResponse() {
        return response;
    }

    public long getAddedItemId() {
        return addedItemId;
    }

    public void setAddedItemId(final long addedItemId) {
        this.addedItemId = addedItemId;
    }
}
