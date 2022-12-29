package ru.instamart.reforged.chatwoot.frame.account_menu;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface AccountMenuElement {
    Element accountDropdownMenu = new Element(By.xpath("//ul[contains(@class,'ant-dropdown-menu-vertical')]"), "Пользовательское меню");
    Button stateByName = new Button(ByKraken.xpathExpression("//ul[contains(@class,'ant-dropdown-menu-item-group-list')]/li[.='%s']"), "Статус по имени");
    Element selectedStateByName = new Element(ByKraken.xpathExpression("//li[contains(@class,'ant-dropdown-menu-item-selected')][.='%s']"), "Выбранный статус по имени");
    Element disabledStateByName = new Element(ByKraken.xpathExpression("//li[contains(@class,'ant-dropdown-menu-item-disabled')][.='%s']"), "Заблокированный статус по имени");
    Button quickKeysInfoLink = new Button(By.xpath("//li[@class='ant-dropdown-menu-item'][.='Клавиши быстрого доступа']"), "Ссылка 'Клавиши быстрого доступа'");
    Button logout = new Button(By.xpath("//li[@class='ant-dropdown-menu-item'][.='Выйти']"), "Кнопка 'Выйти'");
}
