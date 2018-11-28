package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Pages;

public class GrabHelper extends HelperBase{

    private ApplicationManager kraken;

    GrabHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Взять округленное значение цены из указанного элемента */
    public int roundedPrice(Elements element) throws Exception {
        return round(fetchText(element));
    }

}
