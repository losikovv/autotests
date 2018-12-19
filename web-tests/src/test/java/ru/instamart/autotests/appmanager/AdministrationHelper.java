package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.models.UserData;

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
        kraken.perform().waitingFor(2); // Ожидание возобновления заказа в админке
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
        printMessage("- отмена заказа " + kraken.grab().currentURL());
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(reason, details);
        kraken.perform().click(Elements.Admin.Shipments.OrderDetailsPage.confirmOrderCancellationButton());
        kraken.perform().waitingFor(2); // Ожидание отмены заказа в админке
    }

    /**
     * Выбрать причину и текст отмены заказа
     */
    private void chooseCancellationReason(int reason, String details) {
        kraken.perform().click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
        kraken.perform().fillField(By.id("cancellation_reason_details"),details);        // todo вынести в elements
    }

    /**
     * Найти пользователя по email
     */
    public void searchUser(String email) {
        kraken.get().page("admin/users");
        kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
        kraken.perform().click(Elements.Admin.Users.searchButton());
    }

    /**
     * Найти пользователя по userData
     */
    public void searchUser(UserData userData) {
        kraken.get().page("admin/users");
        kraken.perform().fillField(Elements.Admin.Users.searchField(), userData.getLogin());
        kraken.perform().click(Elements.Admin.Users.searchButton());
    }

    /** Добавить админские права в карточке пользователя */
    public void checkAdminCheckbox() {
        if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.adminCheckbox())) {
            printMessage("Пропускаем проставление чекбокса админских прав, он уже проставлен");
        } else {
            kraken.perform().click(Elements.Admin.Users.UserPage.adminCheckbox());
            kraken.perform().waitingFor(1); // Ожидание проставления чекбокса админских прав
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
        }
    }

    /** Убрать админские права в карточке пользователя */
    public void uncheckAdminCheckbox() {
        if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.adminCheckbox())) {
            kraken.perform().click(Elements.Admin.Users.UserPage.adminCheckbox());
            kraken.perform().waitingFor(1); // Ожидание снятия чекбокса админских прав
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
        } else {
            printMessage("Пропускаем снятие чекбокса админских прав, он уже снят");
        }
    }

}
