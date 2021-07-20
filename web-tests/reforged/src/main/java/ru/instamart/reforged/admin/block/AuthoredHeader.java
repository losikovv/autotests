package ru.instamart.reforged.admin.block;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

@Getter
@Accessors(fluent = true)
public final class AuthoredHeader {
    private final Link adminNavigationTitle = new Link(By.xpath("//a[contains(@class, 'logo')]"));
    private final Element adminName = new Element(By.xpath("//span[contains(@class,'user_menu')]"));
    private final Element adminAvatar = new Element(By.xpath("//span[contains(@class,'ant-avatar-icon')]"));
    private final Element logoutDropdown = new Element(By.xpath("//span[@aria-label='down']"));
    private final Element logoutButton = new Element(By.xpath("//span[text()='Выйти']"));
}
