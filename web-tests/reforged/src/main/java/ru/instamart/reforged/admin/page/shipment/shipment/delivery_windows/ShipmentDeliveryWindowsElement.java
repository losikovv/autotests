package ru.instamart.reforged.admin.page.shipment.shipment.delivery_windows;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface ShipmentDeliveryWindowsElement {
    Element noticePopup = new Element(By.xpath("//div[@class='ant-message-notice-content']"), "Всплывающее уведомение о выполнении операции");

    Element savedShipmentInterval = new Element(By.xpath("//span[@class='ant-descriptions-item-label'][contains(.,'Сохраненный слот доставки')]/following-sibling::span"), "Сохраненный интервал доставки в шапке");
    Element savedShop = new Element(By.xpath("//span[@class='ant-descriptions-item-label'][contains(.,'Магазин')]/following-sibling::span"), "Выбранный магазин");
    ElementCollection availableIntervals = new ElementCollection(By.xpath("//label[@class='ant-radio-wrapper']"), "Доступные интервалы доставки");
    Element checkedInterval = new Element(By.xpath("//label[contains(@class,'ant-radio-wrapper-checked')]"), "Выбранный интервал доставки");

    Element deliveryChangeReason = new Element(By.xpath("//span[contains(.,'Причина изменения')]"), "Инпут выбора причины изменения интервала доставки");
    Input deliveryChangeReasonInput = new Input(By.xpath("//span[@class='ant-select-selection-search'][./following-sibling::span[contains(.,'Причина изменения')]]"), "");
    DropDown deliveryChangeReasons = new DropDown(By.xpath("//div[contains(@class,'ant-select-item-option')][./span]"), "Дропдаун выбора выбора причин изменения интервала доставки");
    Button updateDeliveryInterval = new Button(By.xpath("//button[./span[contains(.,'Обновить')]]"), "Кнопка 'Обновить' (интервал доставки)");
}
