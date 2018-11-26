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


}
