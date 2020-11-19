package instamart.core.helpers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import instamart.ui.modules.Administration;
import instamart.core.common.AppManager;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.EnvironmentData;

public class CleanupHelper extends HelperBase {

    public CleanupHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    public void all() {
        verboseMessage("\n================== CLEANUP AFTER TESTRUN ( " + kraken.session.id + " ) ==================\n");
            try {
                orders();
            } catch (WebDriverException w) {
                verboseMessage("❌ НЕ УДАЛОСЬ ПРОВЕСТИ ОТМЕНУ ВСЕХ ТЕСТОВЫХ ЗАКАЗОВ ❌\n");
            }
            try {
                users();
            } catch (WebDriverException w) {
                verboseMessage("❌ НЕ УДАЛОСЬ ПРОВЕСТИ УДАЛЕНИЕ ВСЕХ ТЕСТОВЫХ ЮЗЕРОВ ❌\n");
            }
    }

    /**
     * Удаление тестовых юзеров по дефолтному списку
     */
    public void users() {
        verboseMessage("Удаление тестовых пользователей");
        //TODO в соло-режиме удалять всех тестовых пользователей
        users(kraken.session.userList);
    }

    /**
     * Delete all users on a given page in admin panel
     */
    public void users(String usersListPath) {
        kraken.reach().admin(usersListPath);
        if (kraken.detect().isElementPresent(Elements.Administration.UsersSection.userlistFirstRow())) {
            verboseMessage("> удаляем пользователя " + kraken.grab().text(Elements.Administration.UsersSection.userEmail()));
            kraken.perform().click(Elements.Administration.UsersSection.deleteUserButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            kraken.await().implicitly(1); // Ожидание удаления предыдущего тестового пользователя
            users(usersListPath); // Keep deleting users, recursively
        } else {
            verboseMessage("✓ Все тестовые пользователи удалены\n");
        }
    }

    /**
     * Отмена тестовых заказов по дефолтному списку
     */
    public void orders() {
        verboseMessage("Отмена тестовых заказов");
        //TODO в соло-режиме отменять все тестовые заказы
        orders(kraken.session.orderList);
    }

    /**
     * Cancel all orders on a given page in admin panel
     */
    public void orders(String ordersListPath) {
        kraken.reach().admin(ordersListPath);
        if (!kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.placeholder())) {
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.container());
            kraken.await().implicitly(1); // Ожидание отмены предыдущего тестового заказа
            Administration.Orders.cancelOrder(); // todo добавить проверку, отменять только если тестовый заказ
            orders(ordersListPath); // Keep cancelling orders recursively
        } else {
            verboseMessage("✓ Все тестовые заказы отменены\n");
        }
    }
}