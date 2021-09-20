package ru.instamart.reforged.selgros.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.selgros.faq.*;
import ru.instamart.reforged.selgros.page.home.SelgrosHomePage;

public final class SelgrosRouter extends Router {

    public static SelgrosHomePage selgros() {
        return (SelgrosHomePage) getPage(SelgrosHomePage.class);
    }

    public static SelgrosAboutPage selgrosAbout() {
        return (SelgrosAboutPage) getPage(SelgrosAboutPage.class);
    }

    public static SelgrosDeliveryPage selgrosDelivery() {
        return (SelgrosDeliveryPage) getPage(SelgrosDeliveryPage.class);
    }

    public static SelgrosRulesPage selgrosRules() {
        return (SelgrosRulesPage) getPage(SelgrosRulesPage.class);
    }

    public static SelgrosReturnPolicyPage selgrosReturnPolicy() {
        return (SelgrosReturnPolicyPage) getPage(SelgrosReturnPolicyPage.class);
    }

    public static SelgrosFaqPage selgrosFaq() {
        return (SelgrosFaqPage) getPage(SelgrosFaqPage.class);
    }

    public static SelgrosTermsPage selgrosTerms() {
        return (SelgrosTermsPage) getPage(SelgrosTermsPage.class);
    }

    public static SelgrosContactsPage selgrosContacts() {
        return (SelgrosContactsPage) getPage(SelgrosContactsPage.class);
    }

    public SelgrosRouter() {}
}
