package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;

public class ProbeHelper extends HelperBase {
    ProbeHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    /** Определить находимся на сайте или нет */
    public boolean isOnSite() {
        return isElementPresent(Elements.Site.footer());
    }

    /** Определить находимся на лендинге или нет */
    public boolean isOnLanding() {
        return isElementPresent(Elements.Site.landing());
    }

    /** Определить находимся в админке или нет */
    public boolean isInAdmin() {
        return isElementPresent(Elements.Admin.container());
    }

    /** Определить 404 ошибку */
    public boolean is404() {
        return isElementDetected(Elements.Page404.title());
    }

    /** Определить 500 ошибку */
    public boolean is500() {
        return isElementDetected(Elements.Page500.placeholder());
    }
}
