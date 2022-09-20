package ru.instamart.reforged.stf.page.user.favorites;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public final class UserFavoritesPage implements StfPage, UserFavoritesCheck {

    public Header interactHeader() {
        return header;
    }

    public ProductCard interactProductCart() {
        return productCart;
    }

    @Step("Открыть карточку первого избранного товара")
    public void openCartForFirstFavoriteItem() {
        allFavorites.clickOnFirst();
    }

    @Step("Удалить первый избранный товар")
    public void removeFirstFavoriteItem() {
        favoriteButton.clickOnFirst();
    }

    @Step("Добавить в корзину первый избранный товар")
    public void addToCartFirstFavoriteItem() {
        ThreadUtil.simplyAwait(1);
        addToCart.clickOnFirst();
    }

    @Step("Получить количество избранных товаров")
    public int getFavoritesCount() {
        return allFavorites.elementCount();
    }

    @Step("Опускаемся к последнему видимому товару")
    public void scrollToLastFavoriteItem() {
        allFavorites.scrollToLast();
    }

    @Override
    public String pageUrl() {
        return "user/favorites";
    }
}
