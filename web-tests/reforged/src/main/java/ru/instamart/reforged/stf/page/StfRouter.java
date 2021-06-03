package ru.instamart.reforged.stf.page;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.stf.page.faq.*;
import ru.instamart.reforged.stf.page.user.UserCompanies;
import ru.instamart.reforged.stf.page.user.UserEdit;
import ru.instamart.reforged.stf.page.user.UserFavorites;
import ru.instamart.reforged.stf.page.user.UserShipments;

public final class StfRouter extends Router {

    public static Home home() {
        return (Home) getPage(Home.class);
    }

    public static UserCompanies userCompanies() {
        return (UserCompanies) getPage(UserCompanies.class);
    }

    public static UserEdit userEdit() {
        return (UserEdit) getPage(UserEdit.class);
    }

    public static UserFavorites userFavorites() {
        return (UserFavorites) getPage(UserFavorites.class);
    }

    public static UserShipments userShipments() {
        return (UserShipments) getPage(UserShipments.class);
    }

    public static Shop shop() {
        return (Shop) getPage(Shop.class);
    }

    public static Business business() {
        return (Business) getPage(Business.class);
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

    private StfRouter() {}
}
