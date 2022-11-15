package ru.instamart.reforged.stf.page.user.shipments.edit.delivery_slots_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface DeliverySlotsModalCheck extends Check, DeliverySlotsModalElement {

    @Step("Проверяем, что модальное окно отображается")
    default void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(currentSlotDateTimeTitle);
    }

    @Step("Проверяем, что модальное окно не отображается")
    default void checkModalNotVisible() {
        Assert.assertTrue(close.is().invisible());
    }

    @Step("Проверяем, что заголовок окна: '{expectedTitle}'")
    default void checkModalTitle(final String expectedTitle){
        Assert.assertEquals(modalTitle.getText(), expectedTitle, "Заголовок окна отличается от ожидаемого");
    }

    @Step("Проверяем что в заголовке текущего выбранного времени указано: '{expectedCurrentSlotTitle}'")
    default void checkCurrentSlotTitle(final String expectedCurrentSlotTitle){
        Assert.assertEquals(currentSlotDateTimeTitle.getText(), expectedCurrentSlotTitle, "Заголовок текущего выбранного времени отличается от ожидаемого");
    }

    @Step("Проверяем что в заголовке блока выбора слотов указано: '{expectedSlotsTitle}'")
    default void checkSlotBlockTitle(final String expectedSlotsTitle){
        Assert.assertEquals(slotsTitle.getText(), expectedSlotsTitle, "Заголовок блока слотов отличается от ожидаемого");
    }

    @Step("Проверяем что карусель выбора дня доставки отображается")
    default void checkAvailableDeliveryDaysVisible() {
        Kraken.waitAction().shouldBeVisible(deliverySlotsDays);
    }

    @Step("Проверяем что количество дней для выбора равно: '{expectedCount}'")
    default void checkAvailableDeliveryDaysCount(final int expectedCount) {
        Assert.assertEquals(deliverySlotsDays.elementCount(), expectedCount, "Количество дней с доступными слотами отличается от ожидаемого");
    }

    @Step("Проверяем что количество выбранных дней равно: '{expectedCount}'")
    default void checkSelectedDeliveryDaysCount(final int expectedCount) {
        Assert.assertEquals(selectedDays.elementCount(), expectedCount, "Количество дней с доступными слотами отличается от ожидаемого");
    }

    @Step("Проверяем, что выбранный день в позиции '{expectedPosition}' списка")
    default void checkSelectedDayPosition(final int expectedPosition) {
        Kraken.waitAction().shouldBeVisible(selectedDayByPosition, expectedPosition);
    }

    @Step("Проверяем, что название выбранного дня содержит: {expectedText}")
    default void checkSelectedDayNameContainsText(final String expectedText) {
        Assert.assertTrue(selectedDays.getElementText(0).toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Текст выбранного дня: '%s' не содержит '%s'", selectedDays.getElementText(0), expectedText));
    }

    @Step("Проверяем, что выбранные слоты отсутствуют")
    default void checkSelectedSlotNotVisible() {
        Assert.assertTrue(selectedSlots.is().invisible());
    }

    @Step("Проверяем, что в списке слотов отображаются слоты с большей стоимостью доставки")
    default void checkDeliverySlotWithHigherPriceVisible() {
        Kraken.waitAction().shouldBeVisible(slotsWithHigherDeliveryPrice);
    }

    @Step("Проверяем, что в списке слотов отображаются слоты с меньшей стоимостью доставки")
    default void checkDeliverySlotWithLowerPriceVisible() {
        Kraken.waitAction().shouldBeVisible(slotsWithLowerDeliveryPrice);
    }

    @Step("Проверяем, что в списке слотов не отображаются слоты с информацией об изменении цены доставки")
    default void checkDeliverySlotWithAnotherPriceNotVisible() {
        Assert.assertTrue(slotsWithAnotherDeliveryPrice.is().invisible());
    }

    @Step("Проверяем, что количество выбранных слотов: '{expectedCount}'")
    default void checkSelectedSlotsCount(final int expectedCount) {
        Assert.assertEquals(selectedSlots.elementCount(), expectedCount, "Количество выбранных слотов отличается от ожидаемого");
    }

    @Step("Проверяем, что выбранный слот в позиции '{expectedPosition}' списка")
    default void checkSelectedSlotPosition(final int expectedPosition) {
        Kraken.waitAction().shouldBeVisible(selectedSlotByPosition, expectedPosition);
    }

    @Step("Проверяем, что временной интервал выбранного слота содержит: {expectedText}")
    default void checkSelectedSlotTimeContainsText(final String expectedText) {
        Assert.assertTrue(selectedSlotTime.getText().toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Интервал времени выбранного слота: '%s' не содержит '%s'", selectedSlotTime.getText(), expectedText));
    }

    @Step("Проверяем, что стоимость выбранного слота содержит: {expectedText}")
    default void checkSelectedSlotCostContainsText(final String expectedText) {
        Assert.assertTrue(selectedSlotCost.getText().toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Стоимость выбранного слота: '%s' не содержит '%s'", selectedSlotCost.getText(), expectedText));
    }

    @Step("Проверяем, что отображается заголовок блока 'Текущее время доставки'")
    default void checkCurrentDeliveryDateTimeTitleVisible(){
        Kraken.waitAction().shouldBeVisible(currentSlotDateTimeTitle);
    }

    @Step("Проверяем, что в блоке 'Текущее время доставки' содержится текст: {expectedText}")
    default void checkCurrentDateTimeContainsText(final String expectedText) {
        Assert.assertTrue(currentDeliveryDateTime.getText().toLowerCase().contains(expectedText.toLowerCase()),
                String.format("Значение в блоке 'Текущее время доставки': '%s' не содержит '%s'", currentDeliveryDateTime.getText(), expectedText));
    }
}
