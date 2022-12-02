package ru.instamart.reforged.stf.page.user.shipments.edit;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.RepeatModal;
import ru.instamart.reforged.stf.frame.order_evaluation_modal.OrderEvaluationModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.frame.shipment_cancel_modal.ShipmentCancelModal;
import ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal.DeliverySlotsModal;

public interface UserShipmentElement {

    Header header = new Header();
    RepeatModal repeatModal = new RepeatModal();
    ShipmentCancelModal shipmentCancelModal = new ShipmentCancelModal();
    DeliverySlotsModal deliverySlotsModal = new DeliverySlotsModal();
    OrderEvaluationModal orderEvaluationModal = new OrderEvaluationModal();
    ProductCard productCard = new ProductCard();

    Element shipmentTitle = new Element(By.xpath("//div[contains(@class,'styles_header')]//span[contains(@class,'styles_title')]"), "Заголовок 'Ваш заказ'");
    Element deliveryInterval = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateDate')]"), "Интервал доставки");
    Button changeDeliverySlot = new Button(By.xpath("//div[contains(@class,'ChangeDeliverySlotButton')]/button"), "Кнопка 'Изменить' (слот доставки)");
    Element shipmentState = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateListItemActive')]/div"), "Статус заказа");
    Element shipmentStateDescription = new Element(By.xpath("//div[contains(@class, 'NewShipmentState_description')]"), "Пояснение к статусу заказа");
    Element shipmentStateCancelled = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateCancel_')]"), "Статус 'Заказ отменен'");
    Element shipmentStateShipped = new Element(By.xpath("//p[contains(@class,'NewShipmentState_stateCompleteName')][.='Заказ доставлен']"), "Статус 'Заказ доставлен'");
    Element storeLabelShipped = new Element(By.xpath("//picture[contains(@class,'NewShipmentState_stateStoreImage')]"), "Лейбл ритейлера в доставленном заказе");
    Element titleShippedDate = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateTextMuted')][.='Доставлен']"), "Заголовок даты доставки 'Доставлен'");
    Element dateShippedDate = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateDate_')]"), "Дата и время доставки");
    Element rateShipped = new Element(By.xpath("//div[contains(@class,'NewShipmentState_stateCompleteWrapperRate')]"), "Блок оценки заказа");
    ElementCollection rateStars = new ElementCollection(By.xpath("//button[contains(@class,'NewShipmentState_rateStar_')]"), "Звездочки блока оценки");
    Element thirdRateStar = new Element(By.xpath("(//button[contains(@class,'NewShipmentState_rateStar_')])[3]"), "3-я звездочка блока оценки");
    Element rateStatus = new Element(By.xpath("//p[contains(@class,'NewShipmentState_stateCompleteRateText_')]"), "Оценка заказа пояснение");
    Element additionalOrderInfoTitle = new Element(By.xpath("//div[contains(@class, 'NewShipmentState_messageTitle')]"), "Заголовок блока информации о дозаказе");
    Element additionalOrderInfoText = new Element(By.xpath("//div[contains(@class, 'NewShipmentState_messageContent_')]"), "Текст в блоке информации о дозаказе");
    Button filterActive = new Button(By.xpath("//button[contains(@class,'styles_active_')]"), "Выбранный фильтр списка товаров");
    Button filterAll = new Button(By.xpath("//button[@data-qa='user-shipment-assembly-items-all'][.='Все']"), "Фильтр товаров, кнопка 'Все'");
    Button filterCollected = new Button(By.xpath("//button[@data-qa='user-shipment-assembly-items-collected'][.='Собрано']"), "Фильтр товаров, кнопка 'Собрано'");
    Button filterReplaced = new Button(By.xpath("//button[@data-qa='user-shipment-assembly-items-replaced'][.='Замены']"), "Фильтр товаров, кнопка 'Замены'");
    Button filterCancelled = new Button(By.xpath("//button[@data-qa='user-shipment-assembly-items-canceled'][.='Отмены']"), "Фильтр товаров, кнопка 'Отмены'");
    Element alcoContentMessage = new Element(By.xpath("//div[contains(@class,'styles_alcoMessage_')]"), "Информация для заказа с алкоголем");
    Element noContentMessageFirst = new Element(By.xpath("(//div[contains(@class,'no_content_message_text_')])[1]"), "Текст 1 пустого окна отфильтрованных продуктов");
    Element noContentMessageSecond = new Element(By.xpath("(//div[contains(@class,'no_content_message_text_')])[2]"), "Текст 2 пустого окна отфильтрованных продуктов");
    Element noContentMessageThird = new Element(By.xpath("(//div[contains(@class,'no_content_message_text_')])[3]"), "Текст 3 пустого окна отфильтрованных продуктов");
    Element itemPicture = new Element(ByKraken.xpathExpression("(//div[@data-qa='user-shipment-assembly-items']//picture)[%s]"), "Картинка продукта по индексу в списке");
    Element itemName = new Element(ByKraken.xpathExpression("(//div[@data-qa='user-shipment-assembly-items']//div[contains(@class,'styles_name_')])[%s]"), "Название продукта по индексу в списке");
    Element itemWeight = new Element(ByKraken.xpathExpression("(//div[@data-qa='user-shipment-assembly-items']//div[contains(@class,'styles_weight_')])[%s]"), "Вес продукта по индексу в списке");
    Element itemQuantity = new Element(ByKraken.xpathExpression("(//div[@data-qa='user-shipment-assembly-items']//div[contains(@class,'styles_quantity_')])[%s]"), "Количество продукта по индексу в списке");
    Element itemCost = new Element(ByKraken.xpathExpression("(//div[@data-qa='user-shipment-assembly-items']//div[contains(@class,'styles_currentPrice_')])[%s]"), "Стоимость продукта по индексу в списке");
    Element itemStatus = new Element(ByKraken.xpathExpression("(//div[@data-qa='user-shipment-assembly-items']//div[contains(@class,'StatusBadge_root_')])[%s]"), "Статус сборки продукта по индексу в списке");
    Button seeMore = new Button(By.xpath("//button[@data-qa='user-shipment-assembly-items-more']"), "Кнопка 'Посмотреть ещё'");
    Element shipmentNumber = new Element(By.xpath("//div[@data-qa='user-shipment-summary']//span[contains(@class,'_shipmentNumber')]"), "Номер заказа");
    Element shippingAddress = new Element(By.xpath("//span[contains(.,'Адрес ')]/../following-sibling::div"), "Адрес доставки/самовывоза");
    Element orderDetailsTrigger = new Element(By.xpath("//div[@data-qa='user-shipment-summary-trigger']"), "Кнопка разворачивания деталей заказа");
    Element userPhone = new Element(By.xpath("//span[.='Телефон']/../following-sibling::div/span"), "Телефона пользователя");
    Element userEmail = new Element(By.xpath("//span[@data-qa='user-shipment-email']"), "Лейбл емейла пользователя");
    Element replacementPolicy = new Element(ByKraken.xpathExpression("//span[@data-qa='user-shipment-replacement-policy' and text()='%s']"), "Политика замен в заказе");
    Element paymentMethod = new Element(By.xpath("//span[@data-qa='user-shipment-payment-method']"), "Метод оплаты в заказе");
    Element productsCost = new Element(By.xpath("//span[@data-qa='user-shipment-products-cost']"), "Стоимость продуктов");
    Element assemblyCost = new Element(By.xpath("//*[.='Сборка']/following-sibling::*[@data-qa='user-shipment-cost']"), "Стоимость сборки");
    Element shipmentCost = new Element(By.xpath("//*[.='Доставка']/following-sibling::*[@data-qa='user-shipment-cost']"), "Стоимость доставки");
    Element shipmentSummaryCost = new Element(By.xpath("//div[.='Сборка и доставка']/following-sibling::span[@data-qa='user-shipment-cost']"), "Суммарная стоимость 'Сборка и доставка'");

    Element promoCode = new Element(By.xpath("//div[@data-qa='user-shipment-promocode']"), "Промокод");
    Element totalCost = new Element(By.xpath("//span[@data-qa='user-shipment-total']"), "Итого");
    Button repeatOrder = new Button(By.xpath("//button[@data-qa='user-shipment-repeat']"), "кнопка 'Повторить заказ'");
    Button cancelOrder = new Button(By.xpath("//button[@qakey='user-shipment-cancel']"), "кнопка 'Отменить заказ'");

    ElementCollection productsInOrder = new ElementCollection(By.xpath("//div[@data-qa='user-shipment-assembly-items']"), "Коллекция элементов продуктов в заказе");
    ElementCollection productInOrderNames = new ElementCollection(By.xpath("//div[@data-qa='user-shipment-assembly-items']//div[contains(@class,'styles_name_')]"), "Названия продуктов в заказе");

    Element alert = new Element(By.xpath("//span[@class='alert__msg']"), "Всплывающее бабл-сообщение");
}
