package ru.instamart.reforged.business.page;

import ru.instamart.reforged.business.page.home.BusinessHomePage;
import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.metro.page.home.MetroHomePage;

public class BusinessRouter extends Router {

    public static BusinessHomePage business() {
        return (BusinessHomePage) getPage(BusinessHomePage.class);
    }

    public BusinessRouter() {}
}
