package ru.instamart.reforged.admin.main;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.side_menu.SideMenu;
import ru.instamart.reforged.core.component.Element;

public interface MainElement {

    SideMenu sideMenu = new SideMenu();
    AuthoredHeader authoredHeader = new AuthoredHeader();

    Element user = new Element(By.xpath("//span[@class='ant-avatar ant-avatar-circle ant-avatar-icon']"));
    Element logout = new Element(By.xpath("//span[@class='anticon anticon-logout ant-dropdown-menu-item-icon']"));
}
