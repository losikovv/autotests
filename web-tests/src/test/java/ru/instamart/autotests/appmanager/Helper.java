package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Environments;



    // Common helper for calling methods inherited from HelperBase



public class Helper extends HelperBase {

    public Helper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

}
