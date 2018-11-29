package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class AdministrationHelper extends HelperBase {

    private ApplicationManager kraken;

    AdministrationHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /**
     * Возобновить заказ
     */
    public void resumeOrder() {
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.resumeOrderButton());
        handleAlert();
        waitingFor(2);
    }

    /**
     * Определить отменен ли заказ
     */
    public boolean isOrderCanceled() {
        waitingFor(1);
        return isElementDetected(Elements.Admin.Shipments.OrderDetailsPage.canceledOrderAttribute());
    }

}
