package ru.instamart.reforged.stf.page.home;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.page.Window;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.drawer.cookie.CookieDrawer;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.page.StfPage;

public final class HomePage implements StfPage, Window, HomeCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Address interactAddressModal() {
        return addressModal;
    }

    public Footer interactFooter() {
        return footer;
    }

    public CookieDrawer interactCookieAlert() {
        return cookieAlert;
    }

    @Step("Открыть модалку авторизации")
    public void openLoginModal() {
        loginButton.click();
    }

    @Step("Нажать на кнопку выбора адреса")
    public void clickToSetAddress() {
        addressBlockAddressButton.click();
    }

    @Step("Нажать на первый магазин")
    public void clickOnFirstStore() {
        deliveryStores.clickOnFirst();
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @Override
    public void goToPage() {
        goToPage(pageUrl());
        excludeGuestFromAllAb();
    }
}
