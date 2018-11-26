package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;

public class PerformHelper extends HelperBase {

    private ApplicationManager kraken;

    PerformHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    /** Открыть меню аккаунта */
    public void openAccountMenu() {
        if(!kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already opened");
    }

    /** Закрыть меню аккаунта */
    public void closeAccountMenu() {
        if(kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already closed");
    }
}
