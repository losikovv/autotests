package ru.instamart.reforged.admin.block.authored_header;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface AuthoredHeaderElement {

    Link adminNavigationTitle = new Link(By.xpath("//a[@data-qa='header_main_logo_link']"));
    Element adminName = new Element(By.xpath("//span[@data-qa='header_user_name']"));
    Element adminAvatar = new Element(By.xpath("//span/span[@aria-label='user']"));
    Element logoutDropdown = new Element(By.xpath("//span[@aria-label='down']"));
    Element logoutButton = new Element(By.xpath("//span[@aria-label='logout']"));
}
