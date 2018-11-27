package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Pages;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class SessionHelper extends HelperBase {

    private ApplicationManager kraken;

    SessionHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }


    // ======= Handling test users =======

    /** Delete all users on a given page in admin panel */
    public void deleteUsers(Pages usersList) throws Exception {
        kraken.perform().reachAdmin(usersList);
        if(isElementPresent(Elements.Admin.Users.userlistFirstRow())) {
            printMessage("- delete user " + fetchText(Elements.Admin.Users.firstUserLogin()));
            kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            waitingFor(1);
            deleteUsers(usersList); // Keep deleting users, recursively
        } else {
            printMessage("✓ Complete: no test users left\n");
        }
    }


    // ======= Handling test orders =======

    public void cancelOrders(Pages ordersList) throws Exception {
        kraken.perform().reachAdmin(ordersList);
        if(!isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder()))  {
            kraken.perform().click(Elements.Admin.Shipments.firstOrderInTable());
            waitingFor(1);
            cancelOrder(); // todo обернуть в проверку, выполнять только если тестовый заказ
            cancelOrders(ordersList); // Keep cancelling orders, recursively
        } else {
            printMessage("✓ Complete: no test orders left active\n");
        }
    }


    /** Cancel order on the order page in admin panel */
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


    // TODO перенести в Administration helper
    public void cleanup() throws Exception {
        printMessage("================= CLEANING-UP =================\n");

        printMessage("Canceling test orders...");
        cancelOrders(Pages.Admin.Shipments.testOrdersList());

        printMessage("Deleting test users...");
        deleteUsers(Pages.Admin.Users.testUsersList());
    }

}