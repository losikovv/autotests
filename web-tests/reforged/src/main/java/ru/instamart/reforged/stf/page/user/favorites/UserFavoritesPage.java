package ru.instamart.reforged.stf.page.user.favorites;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.block.header.AuthoredHeader;
import ru.instamart.reforged.stf.frame.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public final class UserFavoritesPage implements StfPage, UserFavoritesCheck {

    public AuthoredHeader interactHeader() {
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
        addToCart.clickOnFirst();
    }

    @Step("Удалить все избранные товары")
    public void removeAll() {
        favoriteButton.clickOnAll();
    }

    @Step("Выбрать фильтр Все товары")
    public void filterAllGoods() {
        allGoods.click();
    }

    @Step("Выбрать фильтр В наличии")
    public void filterInStock() {
        inStock.click();
    }

    @Step("Выбрать фильтр Нет в наличии")
    public void filterOutOfStock() {
        outOfStock.click();
    }

    @Step("Получить количество избранных товаров")
    public int getFavoritesCount() {
        return allFavorites.elementCount();
    }

    @Step("Нажать на Показать больше")
    public void showMore() {
        showMore.scrollTo();
        showMore.click();
    }

    @Override
    public String pageUrl() {
        return "user/favorites";
    }
}
