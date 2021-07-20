package ru.instamart.reforged.admin.block.authored_header;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;


public interface AuthoredHeaderElement {
    Link adminNavigationTitle = new Link(By.xpath("//a[contains(@class, 'logo')]"));
    Element adminName = new Element(By.xpath("//span[contains(@class,'user_menu')]"));
    Element adminAvatar = new Element(By.xpath("//span[contains(@class,'ant-avatar-icon')]"));
    Element logoutDropdown = new Element(By.xpath("//span[@aria-label='down']"));
    Element logoutButton = new Element(By.xpath("//span[text()='Выйти']"));
}
