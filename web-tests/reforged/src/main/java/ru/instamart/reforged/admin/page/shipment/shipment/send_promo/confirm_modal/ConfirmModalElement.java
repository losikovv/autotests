package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.confirm_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface ConfirmModalElement {

    Element modal = new Element(By.xpath("//div[@role='dialog' and contains(@class,'modal-confirm')]"), "Модальное окно");
    Element sendButton = new Element(By.xpath("//div[@role='dialog' and contains(@class,'modal-confirm')]//button[contains(@class,'primary')]"), "Кнопка отправки промо на модальном окне");
}
