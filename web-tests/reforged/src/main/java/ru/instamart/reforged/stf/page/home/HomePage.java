package ru.instamart.reforged.stf.page.home;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.page.Window;
import ru.instamart.reforged.stf.frame.Address;
import ru.instamart.reforged.stf.frame.auth.AuthModal;
import ru.instamart.reforged.stf.page.StfPage;

public final class HomePage implements StfPage, Window, HomeCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Address interactAddressModal() {
        return addressModal;
    }

    @Step("Открыть модалку авторизации")
    public void openLoginModal() {
        loginButton.click();
    }

    @Step("Нажать Для себя")
    public void clickForYourself() {
        forYourself.click();
    }

    @Step("Нажать Для бизнеса")
    public void clickForBusiness() {
        forBusiness.click();
    }

    @Step("Открыть модалку ввода адреса доставки")
    public void clickToSetAddress() {
        setAddress.click();
        Kraken.jsAction().ymapReady();
    }

    @Step("Нажать Показать всех ритейлеров")
    public void clickToShowAllRetailers() {
        showAllRetailers.click();
    }

    @Step("Нажать Показать все города")
    public void clickToShowAllCities() {
        showAllCities.click();
    }


    @Step("Нажать Скрыть все города")
    public void clickToHideCities() {
        hideCities.click();
    }

    @Step("Перейти в GooglePlay")
    public void clickToGooglePlay() {
        googlePlay.click();
    }

    @Step("Перейти в AppStore")
    public void clickToAppStore() {
        appStore.click();
    }

    @Step("Перейти в AppGallery")
    public void clickToAppGallery() {
        appGallery.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
