package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class GrabHelper extends HelperBase{

    private ApplicationManager kraken;

    GrabHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Взять текущий URL */
    public String currentURL() {
        return driver.getCurrentUrl();
    }

    /** Взять текущий адрес доставки */
    public String currentShipAddress() {
        Elements.Site.Header.currentShipAddress();
        printMessage("Shipping address: " + fetchText(Elements.locator()));
        return fetchText(Elements.locator());
    }

    /** Взять округленное значение цены из указанного элемента */
    public int roundedPrice(Elements element) throws Exception {
        return round(fetchText(element));
    }
}
