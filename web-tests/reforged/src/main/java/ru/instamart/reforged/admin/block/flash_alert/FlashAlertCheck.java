package ru.instamart.reforged.admin.block.flash_alert;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;


public interface FlashAlertCheck extends Check, FlashAlertElement {

    @Step("Проверяем что появилась нотификация об успешном сохранении")
    default void checkSuccessFlashVisible() {
        waitAction().shouldBeVisible(successFlash);

    }

    @Step("Проверяем что пропала нотификация об успешном сохранении")
    default void checkSuccessFlashNotVisible() {
        waitAction().shouldNotBeVisible(successFlash);

    }

    @Step("Проверяем что появилась нотификация об неуспешном сохранении")
    default void checkErrorFlash() {
        waitAction().shouldBeVisible(errorFlash);
    }
}