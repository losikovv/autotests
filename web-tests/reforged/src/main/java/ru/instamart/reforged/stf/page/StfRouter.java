package ru.instamart.reforged.stf.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.stf.page.business.BusinessPage;
import ru.instamart.reforged.stf.page.checkout.CheckoutPage;
import ru.instamart.reforged.stf.page.faq.*;
import ru.instamart.reforged.stf.page.home.HomePage;
import ru.instamart.reforged.stf.page.landings.AeroflotPage;
import ru.instamart.reforged.stf.page.landings.drivers_hiring.DriversHiringPage;
import ru.instamart.reforged.stf.page.landings.gift.CertificatePage;
import ru.instamart.reforged.stf.page.landings.job.JobPage;
import ru.instamart.reforged.stf.page.notfound.Page404;
import ru.instamart.reforged.stf.page.search.SearchPage;
import ru.instamart.reforged.stf.page.seo.SeoCatalogPage;
import ru.instamart.reforged.stf.page.seo_incognito.SeoIncognitoPage;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.reforged.stf.page.user.favorites.UserFavoritesPage;
import ru.instamart.reforged.stf.page.user.profile.UserProfilePage;
import ru.instamart.reforged.stf.page.user.shipments.UserShipmentsPage;

public final class StfRouter extends Router {

    public static HomePage home() {
        return (HomePage) getPage(HomePage.class);
    }


    public static UserProfilePage userEdit() {
        return (UserProfilePage) getPage(UserProfilePage.class);
    }

    public static UserFavoritesPage userFavorites() {
        return (UserFavoritesPage) getPage(UserFavoritesPage.class);
    }

    public static UserShipmentsPage userShipments() {
        return (UserShipmentsPage) getPage(UserShipmentsPage.class);
    }

    public static ShopPage shop() {
        return (ShopPage) getPage(ShopPage.class);
    }

    public static SearchPage search() {
        return (SearchPage) getPage(SearchPage.class);
    }

    public static BusinessPage business() {
        return (BusinessPage) getPage(BusinessPage.class);
    }

    public static AboutPage about() {
        return (AboutPage) getPage(AboutPage.class);
    }

    public static ContactsPage contacts() {
        return (ContactsPage) getPage(ContactsPage.class);
    }

    public static DeliveryPage delivery() {
        return (DeliveryPage) getPage(DeliveryPage.class);
    }

    public static FaqPage faq() {
        return (FaqPage) getPage(FaqPage.class);
    }

    public static RulesPage rules() {
        return (RulesPage) getPage(RulesPage.class);
    }

    public static TermsPage terms() {
        return (TermsPage) getPage(TermsPage.class);
    }

    public static HowWeWorkPage howWeWork() {
        return (HowWeWorkPage) getPage(HowWeWorkPage.class);
    }

    public static CheckoutPage checkout() {
        return (CheckoutPage) getPage(CheckoutPage.class);
    }

    public static CertificatePage certificate() {
        return (CertificatePage) getPage(CertificatePage.class);
    }

    public static JobPage job() {
        return (JobPage) getPage(JobPage.class);
    }

    public static PrivacyPolicyPage privacyPolicy() {
        return (PrivacyPolicyPage) getPage(PrivacyPolicyPage.class);
    }

    public static DriversHiringPage driversHiring() {
        return (DriversHiringPage) getPage(DriversHiringPage.class);
    }

    public static AeroflotPage aeroflot() {
        return (AeroflotPage) getPage(AeroflotPage.class);
    }

    public static Page404 notfound() {
        return (Page404) getPage(Page404.class);
    }

    public static SeoCatalogPage seo() {
        return (SeoCatalogPage) getPage(SeoCatalogPage.class);
    }

    public static SeoIncognitoPage seoIncognito() {
        return (SeoIncognitoPage) getPage(SeoIncognitoPage.class);
    }

    private StfRouter() {
    }
}
