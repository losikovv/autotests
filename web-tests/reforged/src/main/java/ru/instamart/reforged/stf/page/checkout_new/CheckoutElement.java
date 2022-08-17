package ru.instamart.reforged.stf.page.checkout_new;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.page.checkout_new.add_payment_card_modal.AddPaymentCardModal;
import ru.instamart.reforged.stf.page.checkout_new.delivery_slots_modal.DeliverySlotsModal;
import ru.instamart.reforged.stf.page.checkout_new.payment_methods_modal.PaymentMethodsModal;

public interface CheckoutElement {

    PaymentMethodsModal paymentMethodsModal = new PaymentMethodsModal();
    AddPaymentCardModal addPaymentCardModal = new AddPaymentCardModal();
    DeliverySlotsModal deliverySlotsModal = new DeliverySlotsModal();

    Element loadingSpinner = new Element(By.xpath("//div[contains(@class,'Spinner')]"), "Спиннер загрузки страницы");

    Button deliveryTab = new Button(By.xpath("//button[@data-qa='checkout_page_ship_address_by_courier_tab']"), "Кнопка (вкладка) 'Доставка'");
    Button pickupTab = new Button(By.xpath("//button[@data-qa='checkout_page_ship_address_tab_pickup_tab']"), "Кнопка (вкладка) 'Самовывоз'");
    Element pickupTabInfo = new Element(By.xpath("//div[@data-qa='checkout_page_ship_address_pickup']"), "Информация о выборе магазина на вкладке 'Самовывоз'");

    Element addressTitle = new Element(By.xpath("//h3[@data-qa='checkout_page_ship_address_by_courier_title']"), "Адрес");
    Input apartment = new Input(By.xpath("//input[@data-qa='checkout_page_ship_address_by_courier_apartment_input']"), "Поле ввода 'Кв, офис'");
    Input entrance = new Input(By.xpath("//input[@data-qa='checkout_page_ship_address_by_courier_entrance_input']"), "Поле ввода 'Подъезд'");
    Input floor = new Input(By.xpath("//input[@data-qa='checkout_page_ship_address_by_courier_floor_input']"), "Поле ввода 'Этаж'");
    Input doorPhone = new Input(By.xpath("//input[@data-qa='checkout_page_ship_address_by_courier_door_phone_input']"), "Поле ввода 'Домофон'");
    Input comment = new Input(By.xpath("//textarea[@data-qa='checkout_page_ship_address_by_courier_comments_input']"), "Поле ввода 'Комментарий'");
    Checkbox deliveryToDoor = new Checkbox(By.xpath("//input[@data-qa='checkout_page_ship_address_by_courier_delivery_to_door_input']"), "Чекбокс 'Бесконтактная доставка'");
    Button b2bLink = new Button(By.xpath("//a[@data-qa='checkout_page_ship_address_b2b_link']"), "Кнопка 'Заказываю для бизнеса'");

    Button openDeliverySlotsModalInTitle = new Button(By.xpath("//div[contains(@class,'DeliverySlots_header')]//button"), "Кнопка 'Доставка на ...' (открывает модалку выбора слота)");
    Element deliverySlot = new Element(By.xpath("//li[.//div[contains(@class,'ShippingRateCard')]]"), "Слот доставки");

    ElementCollection deliverySlots = new ElementCollection(By.xpath("//li[.//div[contains(@class,'ShippingRateCard')]]"), "Доступные слоты доставки");
    ElementCollection activeDeliverySlots = new ElementCollection(By.xpath("//li[.//div[contains(@class,'ShippingRateCard')]][./div[contains(@class,'active')]]"), "Выбранные слоты доставки");

    Element activeSlotByPosition = new Element(ByKraken.xpathExpression("//li[.//div[contains(@class,'ShippingRateCard')]][%s]/div[contains(@class,'active')]"), "Активный слот с учётом позиции в карусели");
    ElementCollection deliverySlotDate = new ElementCollection(By.xpath("//div[contains(@class,'ShippingRateCard_date')]"), "Дата в слоте доставки");
    Element activeDeliverySlotDate = new Element(By.xpath("//li[.//div[contains(@class,'ShippingRateCard')]]/div[contains(@class,'active')]//div[contains(@class,'ShippingRateCard_date')]"), "Дата в выбранном слоте доставки");
    ElementCollection deliverySlotTime = new ElementCollection(By.xpath("//div[contains(@class,'ShippingRateCard_deliveryTime')]"), "Время в слоте доставки");
    Element activeDeliverySlotTime = new Element(By.xpath("//li[.//div[contains(@class,'ShippingRateCard')]]/div[contains(@class,'active')]//div[contains(@class,'ShippingRateCard_deliveryTime')]"), "Время в выбранном слоте доставки");

    ElementCollection deliverySlotPrice = new ElementCollection(By.xpath("//div[contains(@class,'ShippingRateCard_price')]"), "Дата в слоте доставки");
    Element activeDeliverySlotCost = new Element(By.xpath("//li[.//div[contains(@class,'ShippingRateCard')]]/div[contains(@class,'active')]//div[contains(@class,'ShippingRateCard_price')]"), "Дата в выбранном слоте доставки");

    Button deliverySlotsCarouselPrev = new Button(By.xpath("//button[contains(@class,'DeliverySlotsCarousel_prevButton')]"), "Кнопка прокрутки списка слотов '<'");

    Button deliverySlotsCarouselNext = new Button(By.xpath("//button[contains(@class,'DeliverySlotsCarousel_nextButton')]"), "Кнопка прокрутки списка слотов '>'");
    Button openDeliverySlotsModalInCarousel = new Button(By.xpath("//li[.//div[contains(@class,'ShippingRatesCarouselItem')][contains(.,'Другие дни')]]"),
            "Кнопка 'Другие дни' в карусели слотов доставки (открывает модалку выбора слота)");

    Button selectPaymentMethod = new Button(By.xpath("//div[contains(@class,'PayMethodCard_row')]//button"), "Кнопка 'Выбрать/Изменить' (способ оплаты)");
    Element currentPaymentMethod = new Element(By.xpath("//div[contains(@class,'PaymentMethod_label')]"), "Текущий метод оплаты");

    Element contactsSummary = new Element(By.xpath(
            "//div[contains(@class,'CheckoutEditableCard_header')][.//h3[.='Контакты']]//div[contains(@class,'CheckoutEditableCard_details')]"),
            "");
    Button contactsEdit = new Button(By.xpath("//div[contains(@class,'CheckoutEditableCard_header')][.//h3[.='Контакты']]//button"), "Кнопка 'Изменить' (Контакты)");
    Input contactsPhone = new Input(By.xpath("//input[@name='contacts.phone']"), "Поле ввода 'Телефон'");
    Input contactsEmail = new Input(By.xpath("//input[@name='contacts.email']"), "Поле ввода 'Email'");
    Element replacementPolicy = new Element(By.xpath("//div[@data-qa='dropdownSelect']"), "Выпадающий селектор политики замен");
    ElementCollection replacementPolicyList = new ElementCollection(By.xpath("//button[@data-qa='dropdownSelectOption']"), "Список вариантов политик замен в дропдауне");
    Element replacementPolicyByName = new Element(ByKraken.xpathExpression("//button[@data-qa='dropdownSelectOption'][contains(.,'%s')]"), "Пункт политики замен в выпадающем списке по названию");

    Input promoCode = new Input(By.xpath("//input[@data-qa='checkout_page_sidebar_promocode_input']"), "Поле ввода промокода");
    Button promoCodeApply = new Button(By.xpath("//button[@data-qa='checkout_page_sidebar_promocode_button'][.='Применить']"), "Кнопка 'Применить' промокод");
    Button promoCodeCancel = new Button(By.xpath("//button[@data-qa='checkout_page_sidebar_promocode_button'][.='Отменить']"), "Кнопка 'Отменить' промокод");
    Element promoCodeError = new Element(By.xpath("//div[@data-qa='checkout_page_sidebar_promocode_wrapper']//label[contains(@class,'FormGroup_description')]"), "Ошибка в поле ввода промокода");

    Element promoAppliedLabel = new Element(By.xpath("//span[@data-qa='checkout_page_sidebar_order_summary_promo_label']"), "Промокод");
    Element promoAppliedDiscount = new Element(By.xpath("//span[@data-qa='checkout_page_sidebar_order_summary_promo_price']"), "Скидка по промокоду");
    Button confirmPay = new Button(By.xpath("//button[@data-qa='checkout_page_sidebar_order_button'][.='Оплатить']"), "Кнопка 'Оплатить'");
    Button confirmPayDisabled = new Button(By.xpath("//button[@data-qa='checkout_page_sidebar_order_button'][.='Оплатить'][@disabled]"), "Кнопка 'Оплатить'");
    Button confirmOrder = new Button(By.xpath("//button[@data-qa='checkout_page_sidebar_order_button'][.='Заказать']"), "Кнопка 'Заказать'");
    Button confirmOrderDisabled = new Button(By.xpath("//button[@data-qa='checkout_page_sidebar_order_button'][.='Заказать'][@disabled]"), "Кнопка 'Заказать'");

    Element notificationBanner = new Element(By.xpath("//div[contains(@class,'NotificationBubble_banner')]"), "Всплывающее сообщение об ошибке");
    Element notificationBannerTitle = new Element(By.xpath("//div[contains(@class,'NotificationBubble_banner')]//h3"), "Заголовок всплывающего сообщения об ошибке");
    Element notificationBannerText = new Element(By.xpath("//div[contains(@class,'NotificationBubble_banner')]//p"), "Текст всплывающего сообщения об ошибке");
}
