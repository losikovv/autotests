package ru.instamart.reforged.admin.page.shipment.shipment.send_promo.approve_page.cancel_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Element;

public interface CancelPromoModalElement {

    Element modal = new Element(By.xpath("//div[@aria-label='Отмена промокода']"), "Модальное окно отмены промо");
}