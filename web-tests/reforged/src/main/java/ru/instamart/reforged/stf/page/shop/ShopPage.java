package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.instamart.kraken.testdata.pagesdata.EnvironmentData;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.block.header.AuthoredHeader;
import ru.instamart.reforged.stf.drawer.CategoryMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.ProductCard;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.page.StfPage;

import static ru.instamart.reforged.stf.page.home.HomeElement.authModal;

public final class ShopPage implements StfPage, ShopCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public AuthoredHeader interactHeader() {
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

    public CategoryMenu interactCategoryMenu() {
        return categoryMenu;
    }


    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
        Kraken.jsAction().ymapReady();
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCart() {
        firstProductCard.mouseOver();
        plusFirstItemToCart.click();
    }

    @Step("Нажать на минус у первого товара")
    public void minusFirstItemFromCart() {
        firstProductCard.mouseOver();
        minusFirstItemFromCart.click();
    }

    @Step("Добавить первый товар в избранное")
    public void addFirstItemToFavorite() {
        firstProductCard.mouseOver();
        addFirstItemToFavorite.click();
    }

    @Step("Добавить первый товар в избранное")
    public void deleteFirstItemFromFavorite() {
        firstProductCard.mouseOver();
        deleteFirstItemFromFavorite.click();
    }

    @Step("Открыть карточку первого товара")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        Kraken.open(EnvironmentData.INSTANCE.getBasicUrlWithHttpAuth() + shop.getUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @RequiredArgsConstructor
    @Getter
    public enum ShopUrl {
        DEFAULT(""),
        METRO("metro"),
        LENTA("lenta"),
        AUCHAN("auchan");

        private final String url;
    }
}
