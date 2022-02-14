package ru.instamart.reforged.business.page.home;

import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.business.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.business.page.BusinessPage;

import static ru.instamart.reforged.CookieFactory.RETAILERS_REMINDER_MODAL;

public final class BusinessHomePage implements BusinessPage, BusinessHomeCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
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