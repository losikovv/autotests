package ru.instamart.reforged.next.page.checkout.fourthStep;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SlotStepCheck extends Check, SlotStepElement {

    @Step("Проверяем виден спиннер в разделе слотов")
    default void checkSlotsSpinnerIsVisible() {
        waitAction().shouldBeVisible(slotsSpinner);
    }

    @Step("Проверяем не виден спиннер в разделе слотов")
    default void checkSlotsSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(slotsSpinner);
    }

    @Step("Проверяем, что первый слот кликабелен")
    default void checkFirstSlotClickable() {
        waitAction().shouldBeClickable(firstActiveSlotTime);
    }

    @Step("Проверяем видимость ошибки невыбранного слота")
    default void checkSlotSetFailVisible() {
        waitAction().shouldBeVisible(slotSetFail);
    }
}