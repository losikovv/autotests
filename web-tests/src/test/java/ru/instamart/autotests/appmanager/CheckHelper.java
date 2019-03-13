package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;

public class CheckHelper extends HelperBase {

    CheckHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Проверка скачивания документации на странице деталей заказа */
    public void orderDocuments(){
        for(int i = 1; i <= 3; i++) {
            if(kraken.detect().orderDocument(i) != null) {
                kraken.perform().click(Elements.locator());
            }
        }
    }

    /** Проверка скачивания документации заказа */
    public void orderDocuments(String orderNumber){
        kraken.get().page("user/orders/" + orderNumber);
        orderDocuments();
    }

}
