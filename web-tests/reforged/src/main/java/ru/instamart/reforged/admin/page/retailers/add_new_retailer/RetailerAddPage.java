package ru.instamart.reforged.admin.page.retailers.add_new_retailer;

import ru.instamart.reforged.admin.AdminPage;

public class RetailerAddPage implements AdminPage, RetailerAddCheck {

    @Override
    public String pageUrl() {
        return "retailers/new";
    }
}
