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
        printMessage("================= УБОРКА =================\n");

        printMessage("Отмена тестовых заказов...");
        orders();

        printMessage("Удаление тестовых пользователей...");
        users();
    }

    /**
     * Удаление тестовых юзеров по дефолтному списку
     */
    public void users() throws Exception {
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
        if (kraken.detect().isElementPresent(Elements.Admin.Users.userlistFirstRow())) {
            printMessage("- удаляем пользователя " + kraken.grab().text(Elements.Admin.Users.firstUserLogin()));
            kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            kraken.perform().waitingFor(1); // Ожидание удаления предыдущего тестового пользователя
            users(usersListPath); // Keep deleting users, recursively
        } else {
            printMessage("✓ Все тестовые пользователи удалены\n");
        }
    }

    /**
     * Отмена тестовых заказов по дефолтному списку
     */
    public void orders() throws Exception {
        orders(Config.testOrdersList);
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
        if (!kraken.detect().isElementPresent(Elements.Admin.Shipments.emptyListPlaceholder())) {
            kraken.perform().click(Elements.Admin.Shipments.firstOrderInTable());
            kraken.perform().waitingFor(1); // Ожидание отмены предыдущего тестового заказа
            kraken.admin().cancelOrder(); // todo добавить проверку, отменять только если тестовый заказ
            orders(ordersListPath); // Keep cancelling orders recursively
        } else {
            printMessage("✓ Все тестовые заказы отменены\n");
        }
    }
}