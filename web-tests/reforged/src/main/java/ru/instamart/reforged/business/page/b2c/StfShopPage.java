package ru.instamart.reforged.business.page.b2c;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.core.page.Page;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;

public final class StfShopPage implements Page, StfShopCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
    }

    public Address interactAddress() {
        return address;
    }

    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl());
    }

    public void goToPage(final String url) {
        openStfPage(url);
        cookiesChange(false);
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCart() {
        plusFirstItemToCartAddedAddress.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
