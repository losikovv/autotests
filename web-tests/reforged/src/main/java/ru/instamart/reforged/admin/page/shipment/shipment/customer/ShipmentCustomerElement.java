package ru.instamart.reforged.admin.page.shipment.shipment.customer;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;

public interface ShipmentCustomerElement {

    Link backToOrder = new Link(By.xpath("//a[.='Вернуться к заказу']"), "Кнопка 'Вернуться к заказу'");
    Input customerFirstName = new Input(By.xpath("//input[@id='order_ship_address_attributes_firstname']"), "Поле ввода 'Имя'");
}