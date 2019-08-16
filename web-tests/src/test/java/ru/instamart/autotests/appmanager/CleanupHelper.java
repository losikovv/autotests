package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;

public class CleanupHelper extends HelperBase {

    CleanupHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    public void all() {
        message("\n================== CLEANUP AFTER TESTRUN ( " + kraken.session.id + " ) ==================\n");
            try {
                orders();
            } catch (WebDriverException w) {
                message("❌ НЕ УДАЛОСЬ ПРОВЕСТИ ОТМЕНУ ВСЕХ ТЕСТОВЫХ ЗАКАЗОВ ❌\n");
            }
            try {
                users();
            } catch (WebDriverException w) {
                message("❌ НЕ УДАЛОСЬ ПРОВЕСТИ УДАЛЕНИЕ ВСЕХ ТЕСТОВЫХ ЮЗЕРОВ ❌\n");
            }
    }

    /**
     * Удаление тестовых юзеров по дефолтному списку
     */
    public void users() {
        message("Удаление тестовых пользователей");
        //TODO в соло-режиме удалять всех тестовых пользователей
        users(kraken.session.userList);
    }

    /**
     * Delete all users on a given page in admin panel
     */
    public void users(String usersListPath) {
        kraken.reach().admin(usersListPath);
        if (kraken.detect().isElementPresent(Elements.Administration.UsersSection.userlistFirstRow())) {
            message("> удаляем пользователя " + kraken.grab().text(Elements.Administration.UsersSection.firstUserLogin()));
            kraken.perform().click(Elements.Administration.UsersSection.firstUserDeleteButton()); // todo обернуть в проверку, выполнять только если тестовый юзер
            handleAlert();
            kraken.await().implicitly(1); // Ожидание удаления предыдущего тестового пользователя
            users(usersListPath); // Keep deleting users, recursively
        } else {
            message("✓ Все тестовые пользователи удалены\n");
        }
    }

    /**
     * Отмена тестовых заказов по дефолтному списку
     */
    public void orders() {
        message("Отмена тестовых заказов");
        //TODO в соло-режиме отменять все тестовые заказы
        orders(kraken.session.orderList);
    }

    /**
     * Cancel all orders on a given page in admin panel
     */
    public void orders(String ordersListPath) {
        kraken.reach().admin(ordersListPath);
        if (!kraken.detect().isElementPresent(Elements.Administration.ShipmentsSection.placeholder())) {
            kraken.perform().click(Elements.Administration.ShipmentsSection.firstOrderInTable());
            kraken.await().implicitly(1); // Ожидание отмены предыдущего тестового заказа
            AdministrationHelper.Orders.cancelOrder(); // todo добавить проверку, отменять только если тестовый заказ
            orders(ordersListPath); // Keep cancelling orders recursively
        } else {
            message("✓ Все тестовые заказы отменены\n");
        }
    }
}