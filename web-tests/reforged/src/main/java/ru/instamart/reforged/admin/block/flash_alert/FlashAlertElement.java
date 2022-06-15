package ru.instamart.reforged.admin.block.flash_alert;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface FlashAlertElement {

    Element successFlash = new Element(By.xpath("//div[@class='ant-message-notice']//div[contains(@class,'message-success')]"), "алерт после успешного редактирования");
    Element errorFlash = new Element(By.xpath("//div[@class='ant-message-notice']//div[contains(@class,'message-error')]"), "алерт с ошибкой редактирования");
}
