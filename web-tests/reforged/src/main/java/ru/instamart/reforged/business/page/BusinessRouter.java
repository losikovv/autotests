package ru.instamart.reforged.business.page;

import ru.instamart.reforged.business.page.checkout.CheckoutPage;
import ru.instamart.reforged.business.page.home.BusinessHomePage;
import ru.instamart.reforged.business.page.shop.ShopPage;
import ru.instamart.reforged.core.page.Router;

public class BusinessRouter extends Router {

    public static BusinessHomePage business() {
        return (BusinessHomePage) getPage(BusinessHomePage.class);
    }

    public static CheckoutPage checkout() {
        return (CheckoutPage) getPage(CheckoutPage.class);
    }

    public static ShopPage shop() {
        return (ShopPage) getPage(ShopPage.class);
    }

    private BusinessRouter() {
    }
}
