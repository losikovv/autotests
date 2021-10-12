package ru.instamart.reforged.stf.page.user.shipments;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.ShipmentCancelModal;

public interface UserShipmentsElement {

    RepeatModal repeatModal = new RepeatModal();
    ShipmentCancelModal shipmentModal = new ShipmentCancelModal();

    Button repeatOrder = new Button(By.xpath("//button[@data-qa='undefined-repeat']"), "кнопка 'Повторить'");
    Button repeatOrderFromOrderPage = new Button(By.xpath("//button[@data-qa='user-shipment-repeat']"), "кнопка 'Повторить заказ'");
    Button cancelOrderFromOrderPage = new Button(By.xpath("//button[@qakey='user-shipment-cancel']"), "кнопка 'Отменить заказ'");
    ElementCollection shipments = new ElementCollection(By.xpath("//a[@data-qa='user-shipment-list-shipment']"), "список заказов");

    Element orderDetailsTrigger = new Element(By.xpath("//div[@data-qa='user-shipment-summary-trigger']"), "Кнопка разворачивания деталей заказа");
    Element shipmentStatusCancel = new Element(By.xpath("//div[@data-qa='user-shipment-status']//div[text()='Ваш заказ отменен']"), "статус заказа 'Ваш заказ отменен'");
    Element shipmentStatusShipmentReady = new Element(By.xpath("//div[@data-qa='user-shipment-status' and @id='shipment-ready']"), "статус заказа 'Готово к отправке'");
    Element userShipmentPromocode = new Element(By.xpath("//div[@data-qa='user-shipment-promocode']"), "промокод на странице статуса заказа");
    Element paymentMethodCardOnline = new Element(By.xpath("//span[@data-qa='user-shipment-payment-method' and text()='Картой онлайн']"), "метод оплаты в заказе 'Картой онлайн'");
    Element paymentMethodCardToCourier = new Element(By.xpath("//span[@data-qa='user-shipment-payment-method' and text()='Картой при получении']"), "метод оплаты в заказе 'Картой курьеру'");
    Element paymentMethodForBusiness = new Element(By.xpath("//span[@data-qa='user-shipment-payment-method' and text()='По счёту для бизнеса']"), "метод оплаты в заказе 'По счёту для бизнеса'");

    Element replacementMethodCallAndReplace = new Element(By.xpath("//span[@data-qa='user-shipment-replacement-policy' and text()='Позвонить мне. Подобрать замену, если не смогу ответить']"), "Политика замен в заказе 'Звонить / заменить'");
    Element replacementMethodCallAndRemove = new Element(By.xpath("//span[@data-qa='user-shipment-replacement-policy' and text()='Позвонить мне. Убрать из заказа, если не смогу ответить']"), "Политика замен в заказе 'Звонить / убрать'");
    Element replacementMethodNoCallAndReplace = new Element(By.xpath("//span[@data-qa='user-shipment-replacement-policy' and text()='Не звонить мне. Подобрать замену']"), "Политика замен в заказе 'Не звонить /заменить'");
    Element replacementMethodNoCallAndRemove = new Element(By.xpath("//span[@data-qa='user-shipment-replacement-policy' and text()='Не звонить мне. Убрать из заказа']"), "Политика замен в заказе 'Не звонить / убрать'");
}
