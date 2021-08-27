package ru.instamart.reforged.stf.page.checkout.fifthStep;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public class SlotStep implements SlotStepElement {

    @Step("Выбрать первый активный слот")
    public void setFirstActiveSlot() {
        firstActiveSlot.click();
    }

    @Step("Нажать продолжить")
    public void clickToSubmit() {
        submit.click();
    }
}
