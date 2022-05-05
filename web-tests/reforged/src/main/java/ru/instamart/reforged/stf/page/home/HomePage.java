package ru.instamart.reforged.stf.page.home;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.CookieFactory;
import ru.instamart.reforged.core.page.Window;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.drawer.cookie.CookieDrawer;
import ru.instamart.reforged.stf.frame.address.AddressLarge;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.delivery_zones.DeliveryZones;
import ru.instamart.reforged.stf.page.StfPage;

public final class HomePage implements StfPage, Window, HomeCheck {

    private static final String EXPRESS_GROUP_FILTER_URL = "retailer_selection/express";

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public AddressLarge interactAddressModal() {
        return addressModal;
    }

    public Footer interactFooter() {
        return footer;
    }

    public DeliveryZones interactDeliveryZonesModal() {
        return deliveryZones;
    }

    public CookieDrawer interactCookieAlert() {
        return cookieAlert;
    }

    @Step("Открыть модалку авторизации")
    public void openLoginModal() {
        loginButton.click();
    }

    @Step("Нажать на кнопку деавторизации")
    public void clickOnLogout() {
        headerAuthLogoutButton.click();
    }

    @Step("Нажать на кнопку выбора адреса")
    public void clickToSetAddress() {
        addressBlockAddressButton.click();
    }

    @Step("Очищаем поле ввода адреса в лендинге")
    public void clearAddressInLanding() {
        addressCleanButton.click();
    }

    @Step("Вводим адрес в поле ввода лендинга: '{address}'")
    public void fillAddressInLanding(final String address) {
        addressBlockAddressInput.fill(address);
    }

    @Step("Выбираем первый найденный адрес в выпадающем списке")
    public void selectFirstAddressInFounded() {
        //TODO: Ожидание смены списка
        ThreadUtil.simplyAwait(2);
        dropDownAddresses.selectFirst();
        //TODO: Ожидание смены геопозиции
        ThreadUtil.simplyAwait(2);
    }

    @Step("Получаем количество отображаемых ритейлеров")
    public int getRetailersCountInBlock() {
        return deliveryRetailers.elementCount();
    }

    @Step("Нажать на первого ретейлера")
    public void clickOnFirstRetailer() {
        deliveryRetailers.clickOnFirst();
    }

    @Step("Получаем количество отображаемых магазинов")
    public int getStoresCountInBlock() {
        return deliveryStores.elementCount();
    }

    @Step("Нажать на первый магазин")
    public void clickOnFirstStore() {
        deliveryStores.clickOnFirst();
    }

    @Step("Нажать на магазин Sid = {storeSid}")
    public void clickOnStoreWithSid(final int storeSid) {
        storeBySid.click(storeSid);
    }

    @Step("Получаем введенный адрес, указанный на странице")
    public String getEnteredAddress() {
        return addressBlockAddressInput.getValue();
    }

    @Step("Получаем заголовок блока с ритейлерами")
    public String getRetailersBlockTitle() {
        return deliveryRetailersBlockTitle.getText();
    }

    @Step("Получаем заголовок блока с магазинами")
    public String getStoresBlockTitle() {
        return deliveryStoresBlockTitle.getText();
    }

    @Step("Получаем текст лендинга с баннера")
    public String getAddressBlockText() {
        return addressBlockText.getText();
    }

    @Step("Нажимаем кнопку 'Показать все' (города)")
    public void clickShowAllCities() {
        showAllCities.click();
    }

    @Step("Выбираем в списке город '{city}'")
    public void selectCity(final String city) {
        cities.clickOnElementWithText(city);
    }

    @Step("Получаем время доставки, указанное в карточке магазина SID = '{storeSid}'")
    public String getNextDeliveryBySid(final int storeSid) {
        return nextDeliveryBySid.getText(storeSid);
    }

    @Step("Открываем страницу магазинов в группе 'express'")
    public void openExpressGroupPage() {
        goToPage(EXPRESS_GROUP_FILTER_URL);
    }

    @Step("Получаем текст всплывающего сообщения")
    public String getAlertText() {
        return alert.getText();
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

    public void goToPage(final boolean isFixedUUID) {
        goToPage(pageUrl());
        cookieChange(isFixedUUID ? CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID_REFERENCE : CookieFactory.EXTERNAL_ANALYTICS_ANONYMOUS_ID);
    }
}
