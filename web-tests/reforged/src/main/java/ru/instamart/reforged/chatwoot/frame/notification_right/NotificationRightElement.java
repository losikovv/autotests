package ru.instamart.reforged.chatwoot.frame.notification_right;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface NotificationRightElement {
    // их 2: "Отключен от чата"/"Массовые операции с чатами. Просьба перезагрузить страницу.", присутствуют всегда, проверять на видимость

    Element notificationMassCloseRefresh = new Element(By.xpath("//div[@class='ui-notification'][contains(.,'Массовые операции с чатами. Просьба перезагрузить страницу.')]"), "Уведомление о массовых операциях в правом верхнем углу экрана");
    Element notification = new Element(By.xpath("//div[@class='ui-notification'][contains(.,'Отключен от чата')]"), "Уведомление в правом верхнем углу экрана");
    Button buttonWithText = new Button(ByKraken.xpathExpression("//div[@class='ui-notification-container']//button[contains(.,'%s')]"), "Кнопка действий с текстом");
    Button close = new Button(By.xpath("//div[@class='ui-notification-container']//button[contains(@class,'button--only-icon')]"), "Кнопка 'X'");
}