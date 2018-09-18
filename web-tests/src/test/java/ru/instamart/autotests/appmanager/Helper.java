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
            click(Elements.Site.DeliveryPopup.closeButton());
            waitForIt(1);
        } else printMessage("Can't close popup - already closed");
    }

    //======= Попап партнеры ========

    /** Определить открыт ли попап "Партнеры" */
    public boolean isPartnersPopupOpen() {
        if (isElementDisplayed(Elements.Site.PartnersPopup.popup())
                && isElementDetected(Elements.Site.PartnersPopup.title())) {
            printMessage("✓ Partners popup open");
            return true;
        } else {
            return false;
        }
    }

    /** Открыть попап "Партнеры" */
    public void openPartnersPopup() {
        if(!isPartnersPopupOpen()) {
            click(Elements.Site.PartnersPopup.openPopupButton());
            waitForIt(1);
        } else printMessage("Can't open popup - already opened");
    }

    /** Закрыть попап "Партнеры" */
    public void closePartnersPopup() {
        if(isPartnersPopupOpen()) {
            click(Elements.Site.PartnersPopup.closeButton());
            waitForIt(1);
        } else printMessage("Can't close popup - already closed");
    }

    //======= Попап оплата ========

    /** Определить открыт ли попап "Оплата" */
    public boolean isPaymentPopupOpen() {
        if (isElementDisplayed(Elements.Site.PaymentPopup.popup())
                && isElementDetected(Elements.Site.PaymentPopup.title())) {
            printMessage("✓ Payment popup open");
            return true;
        } else {
            return false;
        }
    }

    /** Закрыть попап "Оплата" */
    public void closePaymentPopup() {
        if(isPaymentPopupOpen()) {
            click(Elements.Site.PaymentPopup.closeButton());
            waitForIt(1);
        } else printMessage("Can't close popup - already closed");
    }

}
