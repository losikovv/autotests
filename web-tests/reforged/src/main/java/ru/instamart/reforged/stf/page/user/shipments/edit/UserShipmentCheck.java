package ru.instamart.reforged.stf.page.user.shipments.edit;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.PatternUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.Set;
import java.util.regex.Pattern;

public interface UserShipmentCheck extends Check, UserShipmentElement {

    @Step("Проверяем, что заголовок отображается и в нём текст: '{text}'")
    default void checkShipmentTitle(final String text) {
        Assert.assertEquals(shipmentTitle.getText(), text, "Текст в заголовке отличается от ожидаемого");
    }

    @Step("Проверяем, что текущий статус заказа: '{stateName}'")
    default void checkActiveShipmentState(final String stateName) {
        Assert.assertEquals(shipmentState.getText(), stateName, "Текущий статус заказа отличается от ожидаемого");
    }

    @Step("Проверяем, что статус заказа 'Заказ отменён'")
    default void checkShipmentStateCancelled() {
        shipmentStateCancelled.should().visible();
    }

    @Step("Проверяем, что отображается лейбл ритейлера в доставленном заказе")
    default void checkRetailerLabelVisible() {
        storeLabelShipped.should().visible();
    }

    @Step("Проверяем, что отображается даты и времени доставки")
    default void checkShippedDateTitleVisible() {
        titleShippedDate.should().visible();
    }

    @Step("Проверяем, что отображается дата и время доставки")
    default void checkShippedDateVisible() {
        dateShippedDate.should().visible();
    }

    @Step("Проверяем, что отображается блок оценки заказа")
    default void checkRateBlockVisible() {
        rateShipped.should().visible();
    }

    @Step("Проверяем, что в блоке оценки заказа отображаются звёзды")
    default void checkRateBlockStarsVisible() {
        rateStars.should().visible();
    }

    @Step("Проверяем, что в блоке оценки заказа не отображаются звёзды")
    default void checkRateBlockStarsNotVisible() {
        rateStars.should().invisible();
    }

    @Step("Проверяем, что в блоке оценки заказа: '{text}'")
    default void checkRateText(final String text) {
        Assert.assertEquals(rateStatus.getText(), text, "Текст в блоке оценки заказа отличается от ожидаемого");
    }

    @Step("Проверяем, что статус заказа 'Заказ доставлен'")
    default void checkShipmentStateShipped() {
        shipmentStateShipped.should().visible();
    }

    @Step("Проверяем, что блок информации о дозаказе не отображается")
    default void checkAdditionalOrderInfoNotVisible() {
        additionalOrderInfoTitle.should().invisible();
    }

    @Step("Проверяем, что блок информации о дозаказе отображается и его заголовок: '{text}'")
    default void checkAdditionalOrderTitle(final String text) {
        Assert.assertEquals(additionalOrderInfoTitle.getText(), text, "Текст в заголовке блока информации о дозаказе отличается от ожидаемого");
    }

    @Step("Проверяем, текст в блоке информации о дозаказе: '{text}'")
    default void checkAdditionalOrderText(final String text) {
        Assert.assertEquals(additionalOrderInfoText.getText(), text, "Текст в блоке информации о дозаказе отличается от ожидаемого");
    }

    @Step("Проверяем, выбранный фильтр списка продуктов: '{text}'")
    default void checkActiveProductListFilter(final String text) {
        Assert.assertEquals(filterActive.getText(), text, "Текущий выбранный фильтр списка продуктов отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка фильтров списка продуктов 'Все'")
    default void checkFilterAllButtonVisible() {
        filterAll.should().visible();
    }

    @Step("Проверяем, что отображается кнопка фильтров списка продуктов 'Собрано'")
    default void checkFilterCollectedButtonVisible() {
        filterCollected.should().visible();
    }

    @Step("Проверяем, что отображается кнопка фильтров списка продуктов 'Замены'")
    default void checkFilterReplacedButtonVisible() {
        filterReplaced.should().visible();
    }

    @Step("Проверяем, что отображается кнопка фильтров списка продуктов 'Отмены'")
    default void checkFilterCancelledButtonVisible() {
        filterCancelled.should().visible();
    }

    @Step("Проверяем, отображается информация о заказе с алкоголем")
    default void checkAlcoholInfoVisible() {
        alcoContentMessage.should().visible();
    }

    @Step("Проверяем текст информации о заказе с алкоголем: {expectedText}")
    default void checkAlcoholInfoText(final String expectedText) {
        Assert.assertEquals(alcoContentMessage.getText(), expectedText, "Текст сообщения о заказе с алкоголем");
    }

    @Step("Проверяем, отображается текст-плейсхолдер пустого списка отфильтрованных продуктов")
    default void checkEmptyFilteredListPlaceholderVisible() {
        noContentMessageFirst.should().visible();
    }

    @Step("Проверяем, что первое сообщение пустого списка отфильтрованных продуктов: {expectedText}")
    default void checkFirstEmptyFilteredMessage(final String expectedText) {
        Assert.assertEquals(noContentMessageFirst.getText(), expectedText, "Сообщение отличается от ожидаемого");
    }

    @Step("Проверяем, что второе сообщение пустого списка отфильтрованных продуктов: {expectedText}")
    default void checkSecondEmptyFilteredMessage(final String expectedText) {
        Assert.assertEquals(noContentMessageSecond.getText(), expectedText, "Сообщение отличается от ожидаемого");
    }

    @Step("Проверяем, что третье сообщение пустого списка отфильтрованных продуктов: {expectedText}")
    default void checkThirdEmptyFilteredMessage(final String expectedText) {
        Assert.assertEquals(noContentMessageThird.getText(), expectedText, "Сообщение отличается от ожидаемого");
    }

    @Step("Проверяем, что кнопка 'Посмотреть ещё' не отображается")
    default void checkSeeMoreButtonVisible() {
        seeMore.should().visible();
    }

    @Step("Проверяем, что кнопка 'Посмотреть ещё' не отображается")
    default void checkSeeMoreButtonNotVisible() {
        seeMore.should().invisible();
    }

    @Step("Проверяем отображения списка продуктов")
    default void checkProductListView(final int itemsCount, final String status) {
        for (int i = 1; i <= itemsCount; i++) {
            checkItemPictureVisible(i);
            checkItemName(i);
            checkItemWeight(i);
            checkItemQuantity(i);
            checkItemCost(i);
            checkItemStatus(i, status);
        }
    }

    @Step("Проверяем отображения списка продуктов")
    default void checkProductListView(final int itemsCount) {
        for (int i = 1; i <= itemsCount; i++) {
            checkItemPictureVisible(i);
            checkItemName(i);
            checkItemWeight(i);
            checkItemQuantity(i);
            checkItemCost(i);
            checkItemStatusVisible(i);
        }
    }

    @Step("Проверяем, что отображается картинка {position}-го товара")
    default void checkItemPictureVisible(final int position) {
        itemPicture.should().visible(String.valueOf(position));
    }

    @Step("Проверяем, название {position}-го товара")
    default void checkItemName(final int position) {
        itemName.should().visible(String.valueOf(position));
        itemName.should().textMatches(PatternUtil.rusString(), String.valueOf(position));
    }

    @Step("Проверяем, вес {position}-го товара")
    default void checkItemWeight(final int position) {
        itemWeight.should().visible(String.valueOf(position));
        itemWeight.should().textMatches(PatternUtil.productWeight(), String.valueOf(position));
    }

    @Step("Проверяем, количество {position}-го товара")
    default void checkItemQuantity(final int position) {
        itemQuantity.should().visible(String.valueOf(position));
        itemQuantity.should().textMatches(PatternUtil.productQuantity(), String.valueOf(position));
    }

    @Step("Проверяем, стоимость {position}-го товара")
    default void checkItemCost(final int position) {
        itemCost.should().visible(String.valueOf(position));
        itemCost.should().textMatches(PatternUtil.costWithThousandSeparator(), String.valueOf(position));
    }

    @Step("Проверяем, что статус сборки {position}-го товара отображается")
    default void checkItemStatusVisible(final int position) {
        itemStatus.should().visible(String.valueOf(position));
    }

    @Step("Проверяем, что статус сборки {position}-го товара: '{expectedText}'")
    default void checkItemStatus(final int position, final String expectedText) {
        itemStatus.should().visible(String.valueOf(position));
        itemStatus.should().textContains(expectedText, String.valueOf(position));
    }

    @Step("Проверяем, что отображается номер заказа")
    default void checkShipmentNumberVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentNumber);
    }

    @Step("Проверяем, что номер заказа: {expectedNumber}")
    default void checkShipmentNumber(final String expectedNumber) {
        Assert.assertEquals(shipmentNumber.getText(), expectedNumber, "Номер заказа отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается адрес доставки")
    default void checkShippingAddressVisible() {
        Kraken.waitAction().shouldBeVisible(shippingAddress);
    }

    @Step("Проверяем, что адрес доставки: {expectedAddress}")
    default void checkShippingAddress(final String expectedAddress) {
        Assert.assertEquals(shippingAddress.getText(), expectedAddress, "Адрес доставки отличается от ожидаемого");
    }

    @Step("Проверяем, что указан телефон: {expectedPhone}")
    default void checkUserPhone(final String expectedPhone) {
        Assert.assertEquals(userPhone.getText(), expectedPhone, "Телефон отличается от ожидаемого");
    }

    @Step("Проверяем, что метод оплаты: '{expectedPaymentMethod}'")
    default void checkPaymentMethodEquals(final String expectedPaymentMethod) {
        Assert.assertEquals(paymentMethod.getText(), expectedPaymentMethod, "Метод оплаты в заказе отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается стоимость товаров")
    default void checkProductsCostVisible() {
        Kraken.waitAction().shouldBeVisible(productsCost);
    }

    @Step("Проверяем, что отображается стоимость доставки")
    default void checkShipmentCostVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentCost);
    }

    @Step("Проверяем, что отображается стоимость сборки")
    default void checkAssemblyCostVisible() {
        Kraken.waitAction().shouldBeVisible(assemblyCost);
    }

    @Step("Проверяем, что отображается промокод")
    default void checkPromoCodeVisible() {
        Kraken.waitAction().shouldBeVisible(promoCode);
    }

    @Step("Проверяем, что стоимость доставки заказа: '{expectedValue}'")
    default void checkShipmentCost(final String expectedValue) {
        Assert.assertEquals(shipmentCost.getText(), expectedValue, "Стоимость сборки и доставки отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость сборки заказа: '{expectedValue}'")
    default void checkAssemblyCost(final double expectedValue) {
        Assert.assertEquals(StringUtil.stringToDouble(assemblyCost.getText()), expectedValue, "Стоимость сборки отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость доставки заказа: '{expectedValue}'")
    default void checkShipmentCost(final double expectedValue) {
        Assert.assertEquals(StringUtil.stringToDouble(shipmentCost.getText()), expectedValue, "Стоимость доставки отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость 'Сборка и доставка' заказа: '{expectedValue}'")
    default void checkShipmentSummaryCost(final double expectedValue) {
        Assert.assertEquals(StringUtil.stringToDouble(shipmentSummaryCost.getText()), expectedValue, "Стоимость сборки и доставки отличается от ожидаемой");
    }

    @Step("Проверяем, что отображается сумма 'Итого'")
    default void checkTotalCostVisible() {
        Kraken.waitAction().shouldBeVisible(totalCost);
    }

    @Step("Проверяем, что список товаров в заказе соответствует ожидаемому: {expectedProductsList}")
    default void checkProductsList(final Set<String> expectedProductsList) {
        Assert.assertEquals(productInOrderNames.getTextFromAllElements(), expectedProductsList, "Список продуктов в заказе отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка 'Изменить' (слот)")
    default void checkChangeDeliverySlotButtonVisible() {
        Kraken.waitAction().shouldBeVisible(changeDeliverySlot);
    }

    @Step("Проверяем, что появилось всплывающее сообщение")
    default void checkAlertVisible() {
        Kraken.waitAction().shouldBeVisible(alert);
    }

    @Step("Проверяем, что всплывающее сообщение не отображается")
    default void checkAlertNotVisible() {
        alert.should().invisible();
    }

    @Step("Проверяем что текст бабл-сообщения соответствует ожидаемому")
    default void checkAlertText(final String expectedText) {
        Assert.assertEquals(alert.getText(), expectedText, "Текст всплывающего сообщения отличается от ожидаемого");
    }

    @Step("Проверяем, что текущий интервал доставки: '{expectedValue}'")
    default void checkCurrentDeliveryInterval(final String expectedValue) {
        Assert.assertEquals(deliveryInterval.getText(), expectedValue, "Текущий интервал доставки отличается от ожидаемого");
    }

    @Step("Проверяем, что стоимость заказа 'Итого': '{expectedValue}'")
    default void checkOrderTotalCost(final String expectedValue) {
        Assert.assertEquals(totalCost.getText(), expectedValue, "Стоимость заказа отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость заказа 'Итого': '{expectedValue}'")
    default void checkOrderTotalCost(final double expectedValue) {
        Assert.assertEquals(StringUtil.stringToDouble(totalCost.getText()), expectedValue, "Стоимость заказа отличается от ожидаемой");
    }

    @Step("Проверяем, что стоимость заказа 'Итого' увеличилась'")
    default void checkOrderTotalCostIncreased(final double oldTotalCostValue) {
        Assert.assertTrue(StringUtil.stringToDouble(totalCost.getText()) > oldTotalCostValue,
                String.format("Текущая стоимость заказа: '%s' не больше первоначальной: '%s'", totalCost.getText(), oldTotalCostValue));
    }

    @Step("Проверяем, что стоимость заказа 'Итого' уменьшилась'")
    default void checkOrderTotalCostDecreased(final double oldTotalCostValue) {
        Assert.assertTrue(StringUtil.stringToDouble(totalCost.getText()) < oldTotalCostValue,
                String.format("Текущая стоимость заказа: '%s' не меньше первоначальной: '%s'", totalCost.getText(), oldTotalCostValue));
    }

    @Step("Проверяем, что отображается кнопка 'Повторить заказ'")
    default void checkRepeatOrderButtonVisible() {
        repeatOrder.should().visible();
    }

    @Step("Проверяем, что отображается кнопка 'Отмена'")
    default void checkCancelOrderButtonVisible() {
        cancelOrder.should().visible();
    }

    @Step("Проверяем, что не отображается кнопка 'Отмена'")
    default void checkCancelOrderButtonInvisible() {
        cancelOrder.should().invisible();
    }
}
