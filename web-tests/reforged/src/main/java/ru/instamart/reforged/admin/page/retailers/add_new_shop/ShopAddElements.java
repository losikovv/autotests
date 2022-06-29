package ru.instamart.reforged.admin.page.retailers.add_new_shop;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface ShopAddElements {

    Input regionsInput = new Input(By.xpath("//div[@data-qa='operationalZoneId']//input"), "Инпут поиска региона");
    Element regionsSearchElement = new Element(
            ByKraken.xpathExpression("//div[@id='operational_zone_id_list']/following-sibling::div[@class='rc-virtual-list']//div[text()='%s']"),
                        "Элемент региона в выпадающем списке");

    Element globalLoader = new Element(By.xpath("//div[@data-qa='global_app_loader']"), "Лоадер");
}
