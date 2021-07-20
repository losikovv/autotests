package ru.instamart.reforged.admin.main;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface MainElement {

    Element user = new Element(By.xpath("//span[@class='ant-avatar ant-avatar-circle ant-avatar-icon']"));
    Element logout = new Element(By.xpath("//span[@class='anticon anticon-logout ant-dropdown-menu-item-icon']"));
}
