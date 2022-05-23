package ru.instamart.reforged.next.frame.delivery_zones;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface DeliveryZonesElement {

    Element deliveryZonesModal = new Element(By.xpath("//h3[text()='Зоны доставки']/ancestor::div[@aria-modal='true']"), "Модальное окно просмотра зон доставки");
}
