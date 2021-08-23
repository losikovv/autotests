package ru.instamart.reforged.metro.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.metro.faq.*;
import ru.instamart.reforged.metro.page.home.MetroHomePage;

public final class MetroRouter extends Router {

    public static MetroHomePage metro() {
        return (MetroHomePage) getPage(MetroHomePage.class);
    }

    public static MetroAboutPage metroAbout() {
        return (MetroAboutPage) getPage(MetroAboutPage.class);
    }

    public static MetroDeliveryPage metroDelivery() {
        return (MetroDeliveryPage) getPage(MetroDeliveryPage.class);
    }

    public static MetroRulesPage metroRules() {
        return (MetroRulesPage) getPage(MetroRulesPage.class);
    }

    public static MetroReturnPolicyPage metroReturnPolicy() {
        return (MetroReturnPolicyPage) getPage(MetroReturnPolicyPage.class);
    }

    public static MetroFaqPage metroFaq() {
        return (MetroFaqPage) getPage(MetroFaqPage.class);
    }

    public static MetroTermsPage metroTerms() {
        return (MetroTermsPage) getPage(MetroTermsPage.class);
    }

    public static MetroContactsPage metroContacts() {
        return (MetroContactsPage) getPage(MetroContactsPage.class);
    }

    public MetroRouter() {}
}
