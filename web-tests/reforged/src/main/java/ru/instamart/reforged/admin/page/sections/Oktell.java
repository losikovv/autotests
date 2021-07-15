package ru.instamart.reforged.admin.page.sections;

import ru.instamart.reforged.admin.page.AdminPage;
import ru.instamart.reforged.core.page.Page;
import ru.instamart.reforged.stf.page.StfPage;

public final class Oktell implements StfPage {
    @Override
    public String pageUrl() {
        return "widgets/oktell_order_view";
    }
}
