package ru.instamart.reforged.admin.sections;

import ru.instamart.reforged.admin.AdminPage;

public final class Products implements AdminPage {
    @Override
    public String pageUrl() {
        return "products";
    }
}
