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

    // ====== ORDERS =======

    /**
     * Найти заказ по номеру заказа или шипмента
     */
    public void searchOrder(String number) {
        kraken.get().adminPage("shipments");
        printMessage("Поиск заказа по номеру заказа/шипмента " + number);
        kraken.perform().fillField(Elements.Admin.Shipments.searchNumberField(), number);
        kraken.perform().click(Elements.Admin.Shipments.searchButton());
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
        kraken.get().adminPage("users");
        printMessage("Поиск пользователей по запросу " + email);
        kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
        kraken.perform().click(Elements.Admin.Users.searchButton());
    }

    /**
     * Найти пользователя по реквизитам из указанного объекта userData
     */
    public void searchB2BUser(UserData userData) {
        searchB2BUser(userData.getLogin());
    }

    /**
     * Найти B2B пользователя по email
     */
    public void searchB2BUser(String email) {
        kraken.get().adminPage("users");
        printMessage("Поиск пользователей по запросу " + email);
        kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
        kraken.perform().click(Elements.Admin.Users.b2bCheckbox());
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

    /**
     * Сменить пароль в карточке пользователя
     */
    public void changePassword(String password) {
        kraken.perform().fillField(Elements.Admin.Users.UserPage.passwordField(), password);
        kraken.perform().fillField(Elements.Admin.Users.UserPage.passwordConfirmationField(), password);
        kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
        printMessage("Смена пароля пользователя");
    }

    /**
     * Проставить флаг B2B в карточке пользователя
     */
    public void grantB2B() {
        if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox())) {
            printMessage("Пользователь уже B2B");
        } else {
            kraken.perform().click(Elements.Admin.Users.UserPage.b2bCheckbox());
            kraken.perform().waitingFor(1); // Ожидание проставления чекбокса B2B
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
            printMessage("Проставлен флаг B2B");
        }
    }

}
