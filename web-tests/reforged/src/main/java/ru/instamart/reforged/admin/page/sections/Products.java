package ru.instamart.reforged.admin.page.sections;

import ru.instamart.reforged.admin.page.AdminPage;

public class Products implements AdminPage {
    @Override
    public String pageUrl() {
        return "products";
    }
}
