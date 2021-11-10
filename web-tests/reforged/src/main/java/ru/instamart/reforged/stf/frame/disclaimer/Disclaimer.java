package ru.instamart.reforged.stf.frame.disclaimer;

import io.qameta.allure.Step;
import org.openqa.selenium.interactions.Actions;
import ru.instamart.reforged.core.Kraken;

public class Disclaimer implements DisclaimerCheck {

    @Step("Нажать на затемнение вокруг модального окна для закрытия дисклеймера")
    public void clickOffTheModalToCloseDisclaimer() {
        Actions actions = new Actions(Kraken.getWebDriver());
        actions.moveToElement(disclaimerModal.getComponent(),500,0).click().build().perform();
    }
}
