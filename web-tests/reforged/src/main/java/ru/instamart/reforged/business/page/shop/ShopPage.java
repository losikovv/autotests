package ru.instamart.reforged.business.page.shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.business.drawer.cart.Cart;
import ru.instamart.reforged.business.frame.address.Address;
import ru.instamart.reforged.business.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.business.frame.product_card.ProductCard;
import ru.instamart.reforged.business.page.BusinessPage;
import ru.instamart.reforged.core.enums.ShopUrl;

public final class ShopPage implements BusinessPage, ShopCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    public Cart interactCart() {
        return cart;
    }

    public Address interactAddress() {
        return address;
    }

    @Step("Нажимаем на кнопку 'Добавить в корзину' у первого товара")
    public void plusFirstItemToCart() {
        plusFirstItemToCart.click();
    }

    @Step("Нажимаем на кнопку 'Убрать из корзины' у первого товара")
    public void minusFirstItemFromCart() {
        minusFirstItemFromCart.hoverAndClick();
    }

    @Step("Открываем карточку первого товара")
    public void openFirstProductCard() {
        firstProductSnippet.click();
    }

    @Step("Нажимаем на кнопку 'Добавить в избранное' у первого товара")
    public void addFirstItemToFavourites() {
        addFirstItemToFavorite.click();
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
        cookiesChange(false);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
