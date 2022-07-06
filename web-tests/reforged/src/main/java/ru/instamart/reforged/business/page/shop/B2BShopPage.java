package ru.instamart.reforged.business.page.shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.block.header.B2BHeader;
import ru.instamart.reforged.business.drawer.cart.B2BCart;
import ru.instamart.reforged.business.frame.address.B2BAddress;
import ru.instamart.reforged.business.frame.address.B2BAddressLarge;
import ru.instamart.reforged.business.frame.auth.auth_modal.B2BAuthModal;
import ru.instamart.reforged.business.frame.product_card.B2BProductCard;
import ru.instamart.reforged.business.page.BusinessPage;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.core.page.Window;

public final class B2BShopPage implements BusinessPage, B2BShopCheck, Window {

    public B2BAuthModal interactAuthModal() {
        return authModal;
    }

    public B2BHeader interactHeader() {
        return header;
    }

    public B2BProductCard interactProductCard() {
        return productCard;
    }

    public B2BCart interactCart() {
        return cart;
    }

    public B2BAddress interactAddress() {
        return address;
    }

    public B2BAddressLarge interactAddressLarge() {
        return addressLarge;
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
        cookiesChange(false);
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
