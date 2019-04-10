package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.ElementData;
import ru.instamart.autotests.models.EnvironmentData;

public class CheckHelper extends HelperBase {

    CheckHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Проверка скачивания документации на странице деталей заказа */
    public void orderDocument(String docname){
        ElementData docLink = Elements.Site.UserProfile.OrderDetailsPage.document(docname);
        printMessage("Проверяем : " + docname);
        if(kraken.detect().isElementPresent(docLink)) {
            kraken.perform().click(docLink);
        } else throw new AssertionError();
    }
}
