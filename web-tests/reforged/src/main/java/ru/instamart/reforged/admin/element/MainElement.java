package ru.instamart.reforged.admin.element;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

@Getter
@Accessors(fluent = true)
public final class MainElement {
    private final Element user = new Element(By.xpath("//span[@class='ant-avatar ant-avatar-circle ant-avatar-icon']"));
    private final Element logout = new Element(By.xpath("//span[@class='anticon anticon-logout ant-dropdown-menu-item-icon']"));
}
