package ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;

public interface DeliverySlotsModalElement {

    Element modalTitle = new Element(By.xpath("//div[contains(@class,'DeliverySlotsModal')]//h3"), "Заголовок модального окна");
    ElementCollection deliverySlotsDays = new ElementCollection(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//li"), "Дни в карусели выбора дней");
    ElementCollection selectedDays = new ElementCollection(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//li[./button[contains(@class,'active')]]"), "Выбранный день");
    Element selectedDayByPosition = new Element(ByKraken.xpathExpression("//div[contains(@class,'DeliverySlotsModalCarousel')]//li[%s][./button[contains(@class,'active')]]"), "Выбранный день по позиции");
    Button scrollDaysPrev = new Button(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//button[contains(@class,'Carousel_prev')]"), "Кнопка прокрутки карусели дней вперёд");
    Button scrollDaysNext = new Button(By.xpath("//div[contains(@class,'DeliverySlotsModalCarousel')]//button[contains(@class,'Carousel_next')]"), "Кнопка прокрутки карусели дней назад");

    Element currentSlotDateTimeTitle = new Element(By.xpath("//div[contains(@class,'CurrentDeliveryTimeBanner_title')]"), "Заголовок блока 'Текущее время доставки|самовывоза'");
    Element currentDeliveryDateTime = new Element(By.xpath("//div[contains(@class,'CurrentDeliveryTimeBanner_description')][1]"), "Текущее время доставки/самовывоза");
    Element currentDeliveryAmount = new Element(By.xpath("//div[contains(@class,'CurrentDeliveryTimeBanner_description')][2]"), "Сейчас доставка стоит...");
    Element slotsTitle = new Element(By.xpath("//div[contains(@class,'DeliverySlotsModalList_slotsContainer')]"), "Заголовок блока выбора слотов");
    ElementCollection slots = new ElementCollection(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')]"), "Список слотов");
    ElementCollection selectedSlots = new ElementCollection(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][./input[@checked]]"), "Выбранные слоты");
    Element selectedSlotByPosition = new Element(ByKraken.xpathExpression("//label[contains(@class,'DeliverySlotsModalList_label')][%s][./input[@checked]]"), "Выбранный слот по");

    Element deliverySlotTimeByPosition = new Element(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][%s]//div[contains(@class,'DeliverySlotsModalList_slotTime')]"), "Время слота в указанной позиции");
    ElementCollection slotsWithAnotherDeliveryPrice = new ElementCollection(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][.//span[contains(@class,'DeliverySlotsModalList_costDiffText')]]"), "Слот с отличающейся стоимостью доставки");
    ElementCollection slotsWithHigherDeliveryPrice = new ElementCollection(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][.//span[contains(@class,'DeliverySlotsModalList_costDiffText')][contains(.,'дороже')]]"), "Слоты с большей стоимстью доставки");
    ElementCollection slotsWithLowerDeliveryPrice = new ElementCollection(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][.//span[contains(@class,'DeliverySlotsModalList_costDiffText')][contains(.,'дешевле')]]"), "Слоты с меньшей стоимстью доставки");
    Element deliverySlotAmountChangeByPosition = new Element(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][%s]//div[contains(@class,'DeliverySlotsModalList_slotCostLabel')]"), "Разница стоимости слота в указанной позиции");
    Element selectedSlotTime = new Element(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][./input[@checked]]//div[contains(@class,'DeliverySlotsModalList_slotTime')]"), "Время");
    Element selectedSlotCost = new Element(By.xpath("//label[contains(@class,'DeliverySlotsModalList_label')][./input[@checked]]//div[contains(@class,'DeliverySlotsModalList_slotCostLabel')]"), "Разница в стоимости");

    Button applyChanges = new Button(By.xpath("//div[contains(@class,'DeliverySlotsModal')]//button[contains(.,'Сохранить изменения')]"), "Кнопка 'Выбрать'");
    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton')]/div"), "Кнопка 'Закрыть'");
}
