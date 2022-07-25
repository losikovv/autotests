package ru.instamart.reforged.stf.page.checkout_new.delivery_slots_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface DeliverySlotsModalCheck extends Check, DeliverySlotsModalElement {

    @Step("Проверяем, что модальное окно отображается")
    default void checkModalVisible() {
        Kraken.waitAction().shouldBeVisible(firstSlotTime);
    }

    @Step("Проверяем, что модальное окно не отображается")
    default void checkModalNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(apply);
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
        Kraken.waitAction().shouldNotBeVisible(selectedSlots);
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
}
