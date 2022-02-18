package ru.instamart.reforged.business.page.home;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.business.frame.GetCallbackModal;
import ru.instamart.reforged.business.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.business.page.BusinessPage;
import ru.instamart.reforged.core.page.Window;

import static ru.instamart.reforged.CookieFactory.RETAILERS_REMINDER_MODAL;

public final class BusinessHomePage implements BusinessPage, BusinessHomeCheck, Window {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public GetCallbackModal interactGetCallbackModal() {
        return getCallBackModal;
    }

    public Header interactHeader() {
        return header;
    }

    @Step("Нажимаем кнопку 'Создать профиль компании'")
    public void clickAddCompany() {
        addCompany.click();
    }

    @Step("Нажимаем кнопку 'Заказать обратный звонок'")
    public void clickGetCallback() {
        getCallback.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }

    @Override
    public void goToPage() {
        goToPage(pageUrl());
        addCookie(RETAILERS_REMINDER_MODAL);
    }

}