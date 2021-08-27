package ru.instamart.reforged.okey.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.okey.faq.*;
import ru.instamart.reforged.okey.page.home.OkeyHomePage;

public final class OkeyRouter extends Router {

    public static OkeyHomePage okey() {
        return (OkeyHomePage) getPage(OkeyHomePage.class);
    }

    public static OkeyAboutPage okeyAbout() {
        return (OkeyAboutPage) getPage(OkeyAboutPage.class);
    }

    public static OkeyDeliveryPage okeyDelivery() {
        return (OkeyDeliveryPage) getPage(OkeyDeliveryPage.class);
    }

    public static OkeyRulesPage okeyRules() {
        return (OkeyRulesPage) getPage(OkeyRulesPage.class);
    }

    public static OkeyReturnPolicyPage okeyReturnPolicy() {
        return (OkeyReturnPolicyPage) getPage(OkeyReturnPolicyPage.class);
    }

    public static OkeyFaqPage okeyFaq() {
        return (OkeyFaqPage) getPage(OkeyFaqPage.class);
    }

    public static OkeyTermsPage okeyTerms() {
        return (OkeyTermsPage) getPage(OkeyTermsPage.class);
    }

    public static OkeyContactsPage okeyContacts() {
        return (OkeyContactsPage) getPage(OkeyContactsPage.class);
    }

    public OkeyRouter() {}
}
