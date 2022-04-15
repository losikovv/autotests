package ru.instamart.reforged.admin.page.retailers.retailer_page.settings_sidebar;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface SettingsSidebarElements {

    Element drawer = new Element(By.xpath("//div[@data-qa='retailer_edit_drawer']"), "Сайдбар 'Редактирование настроек ритейлера'");
    Element drawerTitle = new Element(By.xpath("//div[@data-qa='retailer_edit_drawer']//div[@class='ant-drawer-title']"), "Заголовок сайдбара");
    Input retailerName = new Input(By.xpath("//input[@data-qa='retailer_edit_name']"), "Поле ввода 'Наименование'");

    Button cancel = new Button(By.xpath("//button[@data-qa='retailer_edit_close_drawer_btn']"), "Кнопка 'Отменить'");
    Button submit = new Button(By.xpath("//button[@data-qa='retailer_edit_submit_edit_btn']"), "Кнопка 'Применить'");
}
