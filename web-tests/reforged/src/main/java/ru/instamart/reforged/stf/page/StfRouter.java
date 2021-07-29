package ru.instamart.reforged.stf.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.stf.frame.checkout.CheckoutPage;
import ru.instamart.reforged.stf.page.business.BusinessPage;
import ru.instamart.reforged.stf.page.faq.*;
import ru.instamart.reforged.stf.page.home.HomePage;
import ru.instamart.reforged.stf.page.shop.ShopPage;
import ru.instamart.reforged.stf.page.user.UserCompanies;
import ru.instamart.reforged.stf.page.user.UserEdit;
import ru.instamart.reforged.stf.page.user.favorites.UserFavoritesPage;
import ru.instamart.reforged.stf.page.user.UserShipments;

public final class StfRouter extends Router {

    public static HomePage home() {
        return (HomePage) getPage(HomePage.class);
    }

    public static UserCompanies userCompanies() {
        return (UserCompanies) getPage(UserCompanies.class);
    }

    public static UserEdit userEdit() {
        return (UserEdit) getPage(UserEdit.class);
    }

    public static UserFavoritesPage userFavorites() {
        return (UserFavoritesPage) getPage(UserFavoritesPage.class);
    }

    public static UserShipments userShipments() {
        return (UserShipments) getPage(UserShipments.class);
    }

    public static ShopPage shop() {
        return (ShopPage) getPage(ShopPage.class);
    }

    public static BusinessPage business() {
        return (BusinessPage) getPage(BusinessPage.class);
    }

    public static About about() {
        return (About) getPage(About.class);
    }

    public static Contacts contacts() {
        return (Contacts) getPage(Contacts.class);
    }

    public static Delivery delivery() {
        return (Delivery) getPage(Delivery.class);
    }

    public static Faq faq() {
        return (Faq) getPage(Faq.class);
    }

    public static Rules rules() {
        return (Rules) getPage(Rules.class);
    }

    public static Terms terms() {
        return (Terms) getPage(Terms.class);
    }

    public static CheckoutPage checkout() {
        return (CheckoutPage) getPage(CheckoutPage.class);
    }

    private StfRouter() {
    }
}
