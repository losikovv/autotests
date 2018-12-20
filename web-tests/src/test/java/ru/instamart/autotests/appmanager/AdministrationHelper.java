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


    // ====== USERS =======

    /**
     * Найти пользователя по реквизитам из указанного объекта userData
     */
    public void searchUser(UserData userData) {
        searchUser(userData.getLogin());
    }

    /**
     * Найти пользователя по email
     */
    public void searchUser(String email) {
        kraken.get().page("admin/users");
        printMessage("Поиск пользователей по запросу " + email);
        kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
        kraken.perform().click(Elements.Admin.Users.searchButton());
    }

    /**
     * Перейти в редактиирование первого пользователя в списке
     */
    public void editFirstUserInList() {
        kraken.perform().click(Elements.Admin.Users.firstUserEditButton());
        kraken.perform().waitingFor(1); // Ожидание загрузки страницы пользователя в админке
        printMessage("Редактирование пользователя " + kraken.grab().currentURL());
    }

    /**
     * Предоставить админские права в карточке пользователя
     */
    public void grantAdminPrivileges() {
        if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.adminCheckbox())) {
            printMessage("Административные права были предоставлены ранее");
        } else {
            kraken.perform().click(Elements.Admin.Users.UserPage.adminCheckbox());
            kraken.perform().waitingFor(1); // Ожидание проставления чекбокса админских прав
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
            printMessage("Предоставлены административные права");
        }
    }

    /**
     * Отозвать админские права в карточке пользователя
     */
    public void revokeAdminPrivileges() {
        if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.adminCheckbox())) {
            kraken.perform().click(Elements.Admin.Users.UserPage.adminCheckbox());
            kraken.perform().waitingFor(1); // Ожидание снятия чекбокса админских прав
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
            printMessage("Отозваны административные права");
        } else {
            printMessage("Пользователь не имеет административных прав");
        }
    }
}
