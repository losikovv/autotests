package ru.instamart.reforged.stf.page.checkout_new.delivery_slots_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface DeliverySlotsModalElement {
    ElementCollection deliverySlotsDays = new ElementCollection(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//li"), "Дни в карусели выбора дней");
    ElementCollection selectedDays = new ElementCollection(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//li[./button[contains(@class,'active')]]"), "Выбранный день");
    Element selectedDayByPosition = new Element(ByKraken.xpathExpression("//div[contains(@class,'DeliverySlotsModalCarousel')]//li[%s][./button[contains(@class,'active')]]"), "Выбранный день по позиции");
    Button scrollDaysPrev = new Button(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//button[contains(@class,'Carousel_prev')]"), "Кнопка прокрутки карусели дней вперёд");
    Button scrollDaysNext = new Button(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//button[contains(@class,'Carousel_next')]"), "Кнопка прокрутки карусели дней назад");

    ElementCollection slots = new ElementCollection(By.xpath("//label[contains(@class,'ShippingRatesModalList_label')]"), "Список слотов доставки");
    ElementCollection selectedSlots = new ElementCollection(By.xpath("//label[contains(@class,'ShippingRatesModalList_label')][./input[@checked]]"), "Выбранные слоты доставки");
    Element selectedSlotByPosition = new Element(ByKraken.xpathExpression("//label[contains(@class,'ShippingRatesModalList_label')][%s][./input[@checked]]"), "Выбранный слот по в позиции");

    Element firstSlotTime = new Element(By.xpath("//label[contains(@class,'ShippingRatesModalList_label')][1]//div[contains(@class,'ShippingRatesModalList_slotTime')]"), "Время доставки первого слота");
    Element firstSlotCost = new Element(By.xpath("//label[contains(@class,'ShippingRatesModalList_label')][1]//div[contains(@class,'ShippingRatesModalList_slotCostLabel')]"), "Стоимость доставки первого слота");

    Element selectedSlotTime = new Element(By.xpath("//label[contains(@class,'ShippingRatesModalList_label')][./input[@checked]]//div[contains(@class,'ShippingRatesModalList_slotTime')]"), "Время доставки");
    Element selectedSlotCost = new Element(By.xpath("//label[contains(@class,'ShippingRatesModalList_label')][./input[@checked]]//div[contains(@class,'ShippingRatesModalList_slotCostLabel')]"), "Стоимость доставки слота");

    Button apply = new Button(By.xpath("//div[contains(@class,'ShippingRatesModal')]//button[contains(.,'Выбрать')]"), "Кнопка 'Выбрать'");
    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton')]/div"), "Кнопка 'Закрыть'");
}
