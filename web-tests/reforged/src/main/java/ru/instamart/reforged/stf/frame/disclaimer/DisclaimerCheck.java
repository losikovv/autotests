package ru.instamart.reforged.stf.frame.disclaimer;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface DisclaimerCheck extends DisclaimerElement, Check {

    @Step("Нажать кнопку 'Мне есть 18 лет' на модалке дисклеймера")
    default void checkApproveButtonVisible() {
        waitAction().shouldBeVisible(approve);
    }
}
