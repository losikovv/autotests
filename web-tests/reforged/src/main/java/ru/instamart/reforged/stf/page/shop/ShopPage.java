package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public final class ShopPage implements StfPage, ShopCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
    }

    public RetailRocket interactRetailRocket() {
        return retailRocket;
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

    public Footer interactFooter() {
        return footer;
    }

    public HelpDesk interactHelpDesk() {
        return helpDesk;
    }

    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCart() {
        plusFirstItemToCartAddedAddress.click();
    }

    @Step("Нажать на плюс у второго товара")
    public void plusSecondItemToCart() {
        plusSecondItemToCartAddedAddress.click();
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCartNonLogin() {
        plusFirstItemToCartNonLogin.click();
    }

    @Step("Нажать на плюс у второго товара")
    public void plusSecondItemToCartNonLogin() {
        plusSecondItemToCartNonLogin.click();
    }

    @Step("Вернуть значение имени первого товара")
    public String returnFirstProductTitle() {
        return firstProductTitle.getText();
    }

    @Step("Вернуть значение имени второго товара")
    public String returnSecondProductTitle() {
        return secondProductTitle.getText();
    }

    @Step("Вернуть значение имени первого товара")
    public String returnFirstProductTitleNonLogin() {
        return firstProductTitleNonLogin.getAttribute("title");
    }

    @Step("Вернуть значение имени второго товара")
    public String returnSecondProductTitleNonLogin() {
        return secondProductTitleNonLogin.getText();
    }

    @Step("Нажать на минус у первого товара")
    public void minusFirstItemFromCart() {
        minusFirstItemFromCartAddedAddress.hoverAndClick();
    }

    @Step("Добавить первый товар в избранное")
    public void addFirstItemToFavorite() {
        addFirstItemToFavorite.hoverAndClick();
    }

    @Step("Добавить первый товар в избранное")
    public void deleteFirstItemFromFavorite() {
        deleteFirstItemFromFavorite.hoverAndClick();
    }

    @Step("Открыть карточку первого товара на проде")
    public void openFirstProductCardProd() {
        firstProductCardProd.click();
    }

    @Step("Открыть карточку первого товара на стейдже")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl());
        cookiesChange();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
