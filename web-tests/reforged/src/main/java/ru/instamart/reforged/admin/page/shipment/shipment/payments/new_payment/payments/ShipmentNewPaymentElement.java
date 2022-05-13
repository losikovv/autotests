package ru.instamart.reforged.admin.page.shipment.shipment.payments.new_payment.payments;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface ShipmentNewPaymentElement {

    Element setPhoneLabel = new Element(By.xpath("//span[text()='Указать привязанный номер']"), "Лейбл 'указать привязанный номер'");
    Input phoneInput = new Input(By.xpath("//input[@type='tel']"), "номер телефона для платежа через SberPay");
    Button sendNotification = new Button(By.xpath("//span[text()='Отправить уведомление']/parent::button"), "Кнопка 'Отправить уведомление'");

    Element sendNotificationModal = new Element(By.xpath("//div[contains(@class,'ant-modal-confirm-success')]"), "Модальное окно отправленного сообщения");
    Button sendNotificationModalButton = new Button(By.xpath("//div[contains(@class,'ant-modal-confirm-success')]//button"), "Кнопка вернуться на модальном окне отправленного сообщения");
    Button save = new Button(By.xpath("//button[@type='submit']"), "Кнопка 'сохранить'");
}