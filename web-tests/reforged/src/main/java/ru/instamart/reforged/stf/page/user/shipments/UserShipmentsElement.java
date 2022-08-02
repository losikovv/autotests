package ru.instamart.reforged.stf.page.user.shipments;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.shipment_cancel_modal.ShipmentCancelModal;

public interface UserShipmentsElement {

    RepeatModal repeatModal = new RepeatModal();
    ShipmentCancelModal shipmentModal = new ShipmentCancelModal();

    Button repeatOrder = new Button(By.xpath("//button[@data-qa='undefined-repeat']"), "кнопка 'Повторить'");
    Button repeatOrderFromOrderPage = new Button(By.xpath("//button[@data-qa='user-shipment-repeat']"), "кнопка 'Повторить заказ'");
    Button cancelOrderFromOrderPage = new Button(By.xpath("//button[@qakey='user-shipment-cancel']"), "кнопка 'Отменить заказ'");
    ElementCollection shipments = new ElementCollection(By.xpath("//a[starts-with(@href,'/user/shipments/')]"), "список заказов");

    Element orderDetailsTrigger = new Element(By.xpath("//div[@data-qa='user-shipment-summary-trigger']"), "Кнопка разворачивания деталей заказа");
    Element shipmentStatusCancel = new Element(By.xpath("//div[@data-qa='user-shipment-status']//div[text()='Ваш заказ отменен']"), "статус заказа 'Ваш заказ отменен'");
    Element shipmentStatusShipmentReady = new Element(By.xpath("//div[@data-qa='user-shipment-status' and @id='shipment-ready']"), "статус заказа 'Готово к отправке'");
    Element userShipmentPromocode = new Element(By.xpath("//div[@data-qa='user-shipment-promocode']"), "промокод на странице статуса заказа");
    Element shipmentNumber = new Element(By.xpath("//div[@data-qa='user-shipment-number']"), "Номер заказа");
    Element shippingAddress = new Element(By.xpath("//span[@data-qa='user-shipment-address']"), "Адрес доставки");
    Element productsCost = new Element(By.xpath("//span[@data-qa='user-shipment-products-cost']"), "Стоимость продуктов");
    Element shipmentCost = new Element(By.xpath("//div[@data-qa='user-shipment-cost']"), "Стоимость доставки");
    Element totalCost = new Element(By.xpath("//span[@data-qa='user-shipment-total']"), "Итого");
    Element paymentMethod = new Element(By.xpath("//span[@data-qa='user-shipment-payment-method']"), "Метод оплаты в заказе");

    Element discountSumm = new Element(By.xpath("//span[@data-qa='user-shipment-product-discount']"), "сумма скидки");
    Element replacementPolicy = new Element(ByKraken.xpathExpression("//span[@data-qa='user-shipment-replacement-policy' and text()='%s']"), "Политика замен в заказе");

    Element textNoOrders = new Element(By.xpath("//h3[text()='У вас нет завершенных заказов']"), "текст сообщающий, что у нового пользователя нет заказов");
    Button activeOrders = new Button(By.xpath("//button[@data-qa='user-shipment-list-selector-active']"), "кнопка активные заказы");
    Button finishedOrders = new Button(By.xpath("//button[@data-qa='user-shipment-list-selector-inactive']"), "кнопка завершенные заказы");
    Button allOrders = new Button(By.xpath("//button[@data-qa='user-shipment-list-selector-all']"), "кнопка все заказы");
    Button goToShopping = new Button(By.xpath("//a[@data-qa='user-shipment-list-buy-more']"), "кнопка перейти к покупкам");

    Link firstProductName = new Link(By.xpath("//div[@data-qa='line-item']//dt"), "Название первого продукта в корзине");

    ElementCollection productsInOrder = new ElementCollection(By.xpath("//div[@data-qa='user-shipment-assembly-items']"), "Коллекция элементов продуктов в заказе");
    ElementCollection productInOrderNames = new ElementCollection(By.xpath("//div[@data-qa='user-shipment-assembly-items']//div[./picture]/following-sibling::div/div[1]"), "Названия продуктов в заказе");
    ElementCollection orderCancelStatuses = new ElementCollection(By.xpath("//div[@data-qa='undefined-state' and text() = 'Отменен']"), "Коллекция элементов статусов 'Отменено' у заказов");

    Element userPhone = new Element(By.xpath("//span[@data-qa='user-shipment-phone']"), "Лейбл телефона пользователя");
    Element userEmail = new Element(By.xpath("//span[@data-qa='user-shipment-email']"), "Лейбл емейла пользователя");

    Element deliveryInterval = new Element(By.xpath("//div[@data-qa='user-shipment-interval']"), "Интервал доставки");
}
