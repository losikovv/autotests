package ru.instamart.reforged.admin.page.main;

import org.openqa.selenium.By;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.side_menu.SideMenu;
import ru.instamart.reforged.core.component.Element;

public interface MainElement {

    SideMenu sideMenu = new SideMenu();
    AuthoredHeader authoredHeader = new AuthoredHeader();

    Element user = new Element(By.xpath("//span[@data-qa='header_user_name']"));
    Element logout = new Element(By.xpath("//span[@data-qa='header_logout_button']"));
}
