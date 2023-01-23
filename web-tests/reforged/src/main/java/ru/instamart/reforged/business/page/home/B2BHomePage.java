package ru.instamart.reforged.business.page.home;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.block.header.B2BHeader;
import ru.instamart.reforged.business.block.header_multisearch.B2BMultisearchHeader;
import ru.instamart.reforged.business.frame.B2BGetCallbackModal;
import ru.instamart.reforged.business.frame.address.B2BAddressLarge;
import ru.instamart.reforged.business.frame.auth.auth_modal.B2BAuthModal;
import ru.instamart.reforged.business.page.BusinessPage;
import ru.instamart.reforged.core.page.Window;

public final class B2BHomePage implements BusinessPage, B2BHomeCheck, Window {

    public B2BAuthModal interactAuthModal() {
        return authModal;
    }

    public B2BGetCallbackModal interactGetCallbackModal() {
        return getCallBackModal;
    }

    public B2BHeader interactHeader() {
        return header;
    }

    public B2BAddressLarge interactAddressModal() {
        return addressModal;
    }

    public B2BMultisearchHeader interactHeaderMultisearch() {
        return multisearchHeader;
    }

    @Step("Нажать на кнопку выбора адреса")
    public void clickToSetAddress() {
        addressBlockAddressButton.click();
    }

    @Step("Нажимаем кнопку 'Создать профиль компании'")
    public void clickAddCompany() {
        addCompany.click();
    }

    @Step("Нажимаем кнопку 'Заказать обратный звонок'")
    public void clickGetCallback() {
        getCallback.hoverAndClick();
    }

    @Step("Нажимаем на карточку магазина Sid = {storeSid}")
    public void clickOnStoreWithSid(final int storeSid) {
        storeBySid.click(storeSid);
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @Override
    public void goToPage() {
        goToPage(pageUrl());
    }
}