package ru.instamart.reforged.admin.element;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

@Getter
@Accessors(fluent = true)
public final class HeaderElement {
    private final Element adminNavigationTitle = new Element(By.xpath("//*[@title='Домашняя страница системы администрирования']"));
    private final Element adminName = new Element(By.xpath("//span[contains(@class,'user_menu')]"));
    private final Element adminAvatar = new Element(By.xpath("//span[contains(@class,'ant-avatar-icon')]"));
    private final Element logoutDropdown = new Element(By.xpath("//span[@aria-label='down']"));
    private final Element logoutButton = new Element(By.xpath("//span[text()='Выйти']"));
}
