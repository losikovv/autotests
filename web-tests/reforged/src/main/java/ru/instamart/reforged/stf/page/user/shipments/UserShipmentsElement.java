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

    Element shipmentStatusCancel = new Element(By.xpath("//div[@data-qa='user-shipment-status']//div[text()='Ваш заказ отменен']"), "статус заказа 'Ваш заказ отменен'");
}
