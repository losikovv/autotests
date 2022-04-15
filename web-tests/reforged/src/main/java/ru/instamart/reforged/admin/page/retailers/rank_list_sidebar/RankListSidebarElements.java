package ru.instamart.reforged.admin.page.retailers.rank_list_sidebar;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface RankListSidebarElements {

    Element drawer = new Element(By.xpath("//div[@data-qa='retailers_range_feature']"), "Сайдбар 'Ранжировать список'");
    Element drawerTitle = new Element(By.xpath("//div[@data-qa='retailers_range_feature']//div[@class='ant-drawer-title']"), "Заголовок сайдбара");
    ElementCollection retailers = new ElementCollection(By.xpath("//tr[@data-qa='retailers_range_table_row']"), "Список ритейлеров");

    Button cancel = new Button(By.xpath("//button[@data-qa='retailers_range_close_drawer_btn']"), "Кнопка 'Отменить'");
    Button submit = new Button(By.xpath("//button[@data-qa='retailers_range_submit_edit_btn']"), "Кнопка 'Применить'");
}
