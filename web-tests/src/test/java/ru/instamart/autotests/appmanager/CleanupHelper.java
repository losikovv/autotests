package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Pages;

public class CleanupHelper extends HelperBase {

    private ApplicationManager kraken;

    CleanupHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    public void all() throws Exception {
        printMessage("================= CLEANING-UP =================\n");

        printMessage("Canceling test orders...");
        orders(Config.testOrdersList);

        printMessage("Deleting test users...");
        users(Config.testUsersList);
    }

    /**
     * Delete all users on a given page in admin panel
     */
    public void users(Pages usersList) throws Exception {
        users(Pages.getPagePath());
    }

    /**
     * Delete all users on a given page in admin panel
     */
    public void users(String usersListPath) throws Exception {
        kraken.perform().reachAdmin(usersListPath);
        if (isElementPresent(Elements.Admin.Users.userlistFirstRow())) {
            printMessage("- delete user " + fetchText(Elements.Admin.Users.firstUserLogin()));
            kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            waitingFor(1);
            // Keep deleting users, recursively
            users(usersListPath);
        } else {
            printMessage("✓ Complete: no test users left\n");
        }
    }

    /**
     * Cancel all orders on a given page in admin panel
     */
    public void orders(Pages ordersList) throws Exception {
        orders(Pages.getPagePath());
    }

    /**
     * Cancel all orders on a given page in admin panel
     */
    public void orders(String ordersListPath) throws Exception {
        kraken.perform().reachAdmin(ordersListPath);
        if (!isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder())) {
            kraken.perform().click(Elements.Admin.Shipments.firstOrderInTable());
            waitingFor(1);
            kraken.admin().cancelOrder(); // todo обернуть в проверку, выполнять только если тестовый заказ
            orders(ordersListPath); // Keep cancelling orders, recursively
        } else {
            printMessage("✓ Complete: no test orders left active\n");
        }
    }
}