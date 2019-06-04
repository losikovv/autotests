package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;

public class NavigationHelper extends HelperBase {

    NavigationHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    // TODO сделать метод to принимающий массив элементов и кликающий их по очереди
    // TODO public void to(Elements[] elements){ }

}
