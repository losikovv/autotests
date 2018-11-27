package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class CleanupHelper extends HelperBase {

    private ApplicationManager kraken;

    CleanupHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    public void all() throws Exception {
        printMessage("================= CLEANING-UP =================\n");

        printMessage("Canceling test orders...");
        orders(Pages.Admin.Shipments.testOrdersList());

        printMessage("Deleting test users...");
        users(Pages.Admin.Users.testUsersList());
    }

    /** Delete all users on a given page in admin panel */
    public void users(Pages usersList) throws Exception {
        kraken.perform().reachAdmin(usersList);
        if(isElementPresent(Elements.Admin.Users.userlistFirstRow())) {
                printMessage("- delete user " + fetchText(Elements.Admin.Users.firstUserLogin()));
                kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
                handleAlert();
                waitingFor(1);
            // Keep deleting users, recursively
            users(usersList);
        } else {
            printMessage("✓ Complete: no test users left\n");
        }
    }

    /** Cancel all orders on a given page in admin panel */
    public void orders(Pages ordersList) throws Exception {
        kraken.perform().reachAdmin(ordersList);
        if(!isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder()))  {
                kraken.perform().click(Elements.Admin.Shipments.firstOrderInTable());
                waitingFor(1);
                cancelOrder(); // todo обернуть в проверку, выполнять только если тестовый заказ
            // Keep cancelling orders, recursively
            orders(ordersList);
        } else {
            printMessage("✓ Complete: no test orders left active\n");
        }
    }


    /** Cancel order on the page in admin panel */
    public void cancelOrder(){
        printMessage("> cancel order " + fetchCurrentURL());
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(4,"Тестовый заказ");
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitingFor(2);
    }


    public void cancelOrder(int reason, String details){
        printMessage("> cancel order " + fetchCurrentURL());
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(reason,details);
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        waitingFor(2);
    }


    private void chooseCancellationReason(int reason, String details) {
        kraken.perform().click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
        kraken.perform().fillField(By.id("cancellation_reason_details"),details);        // todo вынести в elements
    }

}