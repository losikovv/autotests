package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;

public class DeliveryModalHelper extends HelperBase {
    DeliveryModalHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    public boolean isOpen() {
        return isElementDisplayed(Elements.Site.DeliveryPopup.popup())
                && isElementDetected(Elements.Site.DeliveryPopup.title());
    }

    public void close() {
        if(isOpen()) {
            click(Elements.Site.DeliveryPopup.closeButton());
            waitFor(1);
        } else printMessage("Skip, modal is already closed");
    }
}
