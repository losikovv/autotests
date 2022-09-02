package ru.instamart.reforged.stf.frame.disclaimer;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

public class Disclaimer implements DisclaimerCheck, Close {

    @Step("Нажать на затемнение вокруг модального окна для закрытия дисклеймера")
    public void clickOffTheModalToCloseDisclaimer() {
        disclaimerModal.getActions().clickWithOffset(500, 0);
    }

    @Step("Принимаем условия продажи алкоголя")
    public void agreeAndConfirm(){
        confirm.check();
        approve.click();
    }

}
