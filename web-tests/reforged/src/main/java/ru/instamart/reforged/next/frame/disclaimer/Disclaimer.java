package ru.instamart.reforged.next.frame.disclaimer;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.frame.Close;

public class Disclaimer implements DisclaimerCheck, Close {

    @Step("Нажать на затемнение вокруг модального окна для закрытия дисклеймера")
    public void clickOffTheModalToCloseDisclaimer() {
        disclaimerModal.getActions().clickWithOffset(500, 0);
    }
}
