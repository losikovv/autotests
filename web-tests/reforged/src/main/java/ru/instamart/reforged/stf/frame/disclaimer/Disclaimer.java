package ru.instamart.reforged.stf.frame.disclaimer;

import io.qameta.allure.Step;

public class Disclaimer implements DisclaimerCheck {

    @Step("Нажать на затемнение вокруг модального окна для закрытия дисклеймера")
    public void clickOffTheModalToCloseDisclaimer() {
        disclaimerModal.clickWithOffset(500, 0);
    }
}
