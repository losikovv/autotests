package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Selector;

public interface ShopAddElements {

    Selector regionsDropdown = new Selector(By.id("store_operational_zone_id"), "Выпадающий список доступных регионов");
}
