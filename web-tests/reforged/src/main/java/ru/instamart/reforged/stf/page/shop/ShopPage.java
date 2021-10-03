package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
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
        Kraken.jsAction().ymapReady();
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCart() {
        plusFirstItemToCartAddedAddress.click();
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

    @Step("Открыть дефолтный магазин с дефолтным sid")
    public void goToShopPageWithDefaultSid() {
        openSitePage(CoreProperties.DEFAULT_RETAILER + "?sid=" + EnvironmentProperties.DEFAULT_SID);
    }

    public void goToPage(final ShopUrl shop) {
        Kraken.open(EnvironmentProperties.Env.FULL_SITE_URL_WITH_BASIC_AUTH + shop.getUrl());
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
