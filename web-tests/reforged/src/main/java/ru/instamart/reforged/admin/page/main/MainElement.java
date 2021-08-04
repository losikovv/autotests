package ru.instamart.reforged.admin.page.main;

import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.side_menu.SideMenu;

public interface MainElement {

    SideMenu sideMenu = new SideMenu();
    AuthoredHeader authoredHeader = new AuthoredHeader();
}
