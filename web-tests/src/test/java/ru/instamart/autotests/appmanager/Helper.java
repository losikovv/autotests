package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



    // Common helper for calling methods inherited from HelperBase



public class Helper extends HelperBase {

    public Helper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }


    //======== Попап доставка ==========

    /** Определить открыт ли попап доставки */
    public boolean isDeliveryPopupOpen() {
        if (isElementDisplayed(Elements.Site.DeliveryPopup.popup())
                && isElementDetected(Elements.Site.DeliveryPopup.title())) {
            printMessage("✓ Delivery popup open");
            return true;
        } else {
            return false;
        }
    }

    /** Открыть попап доставки  */
    public void openDeliveryPopup() {
        if(!isDeliveryPopupOpen()) {
            click(Elements.Site.DeliveryPopup.openPopupButton());
            waitForIt(1);
        } else printMessage("Can't open popup - already opened");
    }

    /** Закрыть попап доставки */
    public void closeDeliveryPopup() {
        if(isDeliveryPopupOpen()) {
            click(Elements.Site.DeliveryPopup.closePopupButton());
            waitForIt(1);
        } else printMessage("Can't close popup - already closed");
    }

    //======= Попап партнеры ========

    /** Определить открыт ли попап "Партнеры" */
    public boolean isPartnersPopupOpened() {
        return isElementDisplayed(Elements.Site.PartnersPopup.popup());
    }

    /** Открыть попап "Партнеры" */
    public void openPartnersPopup() {
        if(!isPartnersPopupOpened()) {
            click(Elements.Site.PartnersPopup.openPopupButton());
            waitForIt(1);
        } else printMessage("Can't open popup - already opened");
    }

    /** Закрыть попап "Партнеры" */
    public void closePartnersPopup() {
        if(isPartnersPopupOpened()) {
            click(Elements.Site.PartnersPopup.closePopupButton());
            waitForIt(1);
        } else printMessage("Can't close popup - already closed");
    }

}
