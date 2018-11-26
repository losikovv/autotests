package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



    // Common helper for calling methods inherited from HelperBase



public class PerformHelper extends HelperBase {

    public PerformHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }


    // ======= Попап "Профиль" =======

    public void openAccountMenu() {
        if(!isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already opened");
    }

    public void closeAccountMenu() {
        if(isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already closed");
    }

    public boolean isAccountMenuOpen() {
        return isElementDisplayed(Elements.Site.AccountMenu.popup());
    }



    //======= Попап "Партнеры" ========

    /** Определить открыт ли попап "Партнеры" */
    public boolean isPartnersPopupOpen() {
        if (isElementDisplayed(Elements.Site.PartnersPopup.popup())
                && isElementDetected(Elements.Site.PartnersPopup.title())) {
            //printMessage("✓ Partners popup open");
            return true;
        } else {
            return false;
        }
    }

    /** Открыть попап "Партнеры" */
    public void openPartnersPopup() {
        if(!isPartnersPopupOpen()) {
            click(Elements.Site.Header.partnersButton());
            waitFor(1);
        } else printMessage("Can't open popup - already opened");
    }

    /** Закрыть попап "Партнеры" */
    public void closePartnersPopup() {
        if(isPartnersPopupOpen()) {
            click(Elements.Site.PartnersPopup.closeButton());
            waitFor(1);
        } else printMessage("Can't close popup - already closed");
    }


}
