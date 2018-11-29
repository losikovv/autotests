package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
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
     * Cancel order on the page in admin panel with default test reason
     */
    public void cancelOrder() {
        cancelOrder(4, "Тестовый заказ");
    }

    /**
     * Cancel order on the page in admin panel
     */
    public void cancelOrder(int reason, String details) {
        printMessage("> cancel order " + kraken.grab().currentURL());
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(reason, details);
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitingFor(2);
    }

    /**
     * Выбрать причину и текст отмены заказа
     */
    private void chooseCancellationReason(int reason, String details) {
        kraken.perform().click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
        kraken.perform().fillField(By.id("cancellation_reason_details"),details);        // todo вынести в elements
    }

}
