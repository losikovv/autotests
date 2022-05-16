package ru.instamart.reforged.admin.page.shipment.shipment.payments;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface ShipmentPaymentsElement {

    Link newPayment = new Link(By.xpath("//a[contains(@href,'/payments/new')]"), "Кнопка 'Новый платеж'");
    Element successAddPaymentAlert = new Element(By.xpath("//div[contains(@class,'ant-message-success')]"), "Алерт успешного добавления платежа");

    Element invalidPaymentByCard = new Element(By.xpath("//a[contains(text(),'Картой при получении')]/ancestor::tr//span[text()='недействительный']"), "Недействительный платеж");
    Element pendingPaymentByCard = new Element(By.xpath("//a[contains(text(),'Картой при получении')]/ancestor::tr//span[text()='оформляется']"), "Недействительный платеж");

    Element waitingPaymentBySberPay = new Element(By.xpath("//a[contains(text(),'SberPay')]/ancestor::tr//span[text()='ожидает авторизации']"), "Платеж по сберпей в статусе ожидания авторизации");
}
