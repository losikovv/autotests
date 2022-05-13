package ru.instamart.reforged.admin.page.shipment.shipment;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;

public interface ShipmentElement {

    Element orderInfo = new Element(By.xpath("//div[@id='order-form-wrapper']"), "Информация о заказе");
    Link deliveryWindowLink = new Link(By.xpath("//a[contains(@class,'icon-calendar')][./span[contains(.,'Магазин и время доставки')]]"), "Ссылка 'Магазин и время доставки'");
    Link paymentsLink = new Link(By.xpath("//a[contains(@class,'icon-credit-card')][./span[contains(.,'Платежи')]]"), "Ссылка 'Платежи'");
}
