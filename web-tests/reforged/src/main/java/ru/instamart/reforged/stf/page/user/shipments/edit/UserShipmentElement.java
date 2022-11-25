package ru.instamart.reforged.stf.page.user.shipments.edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.shipment_cancel_modal.ShipmentCancelModal;
import ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal.DeliverySlotsModal;

public interface UserShipmentElement {

    RepeatModal repeatModal = new RepeatModal();
    ShipmentCancelModal shipmentCancelModal = new ShipmentCancelModal();
    DeliverySlotsModal deliverySlotsModal = new DeliverySlotsModal();

    Element deliveryInterval = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateDate')]"), "Интервал доставки");
    Button changeDeliverySlot = new Button(By.xpath("//div[contains(@class,'ChangeDeliverySlotButton')]/button"), "Кнопка 'Изменить' (слот доставки)");
    Element shipmentState = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateListItemActive')]/div"), "Статус заказа");

    Element shipmentNumber = new Element(By.xpath("//div[@data-qa='user-shipment-summary']//span[contains(@class,'_shipmentNumber')]"), "Номер заказа");
    Element shippingAddress = new Element(By.xpath("//span[contains(.,'Адрес ')]/../following-sibling::div"), "Адрес доставки/самовывоза");
    Element orderDetailsTrigger = new Element(By.xpath("//div[@data-qa='user-shipment-summary-trigger']"), "Кнопка разворачивания деталей заказа");
    Element userPhone = new Element(By.xpath("//span[@data-qa='user-shipment-phone']"), "Лейбл телефона пользователя");
    Element userEmail = new Element(By.xpath("//span[@data-qa='user-shipment-email']"), "Лейбл емейла пользователя");
    Element replacementPolicy = new Element(ByKraken.xpathExpression("//span[@data-qa='user-shipment-replacement-policy' and text()='%s']"), "Политика замен в заказе");

    Element paymentMethod = new Element(By.xpath("//span[@data-qa='user-shipment-payment-method']"), "Метод оплаты в заказе");
    Element productsCost = new Element(By.xpath("//span[@data-qa='user-shipment-products-cost']"), "Стоимость продуктов");
    Element assemblyCost = new Element(By.xpath("//span[text()='Сборка']/following-sibling::span[@data-qa='user-shipment-cost']"), "Стоимость сборки");
    Element shipmentCost = new Element(By.xpath("//span[text()='Доставка']/following-sibling::span[@data-qa='user-shipment-cost']"), "Стоимость доставки");
    Element promoCode = new Element(By.xpath("//div[@data-qa='user-shipment-promocode']"), "Промокод");
    Element totalCost = new Element(By.xpath("//span[@data-qa='user-shipment-total']"), "Итого");
    Button repeatOrder = new Button(By.xpath("//button[@data-qa='user-shipment-repeat']"), "кнопка 'Повторить заказ'");
    Button cancelOrder = new Button(By.xpath("//button[@qakey='user-shipment-cancel']"), "кнопка 'Отменить заказ'");

    ElementCollection productsInOrder = new ElementCollection(By.xpath("//div[@data-qa='user-shipment-assembly-items']"), "Коллекция элементов продуктов в заказе");
    ElementCollection productInOrderNames = new ElementCollection(By.xpath("//div[@data-qa='user-shipment-assembly-items']//div[./picture]/following-sibling::div/div[1]"), "Названия продуктов в заказе");

    Element alert = new Element(By.xpath("//span[@class='alert__msg']"), "Всплывающее бабл-сообщение");
}
