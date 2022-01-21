package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_zone_edit;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class StoreZone implements AdminPage, StoreZoneCheck  {

    @Step("Вернуть количество зон на странице зон")
    public Integer zoneQuantityReturn() {
        return zoneInTable.elementCount();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
