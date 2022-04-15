package ru.instamart.reforged.admin.page.retailers.retailer_page.appearance_sidebar;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface AppearanceSidebarElements {

    Element drawer = new Element(By.xpath("//div[@data-qa='retailer_edit_appearance_drawer']"), "Сайдбар 'Редактирование внешнего вида ритейлера'");
    Element drawerTitle = new Element(By.xpath("//div[@data-qa='retailer_edit_appearance_drawer']//div[@class='ant-drawer-title']"), "Заголовок сайдбара");
    Button uploadLogo = new Button(By.xpath("//button[@data-qa='retailer_edit_upload_logo']"), "Кнопка 'Загрузить файл'");

    Button cancel = new Button(By.xpath("//button[@data-qa='retailer_edit_close_drawer_btn']"), "Кнопка 'Отменить'");
    Button submit = new Button(By.xpath("//button[@data-qa='retailer_edit_submit_edit_btn']"), "Кнопка 'Применить'");
}
