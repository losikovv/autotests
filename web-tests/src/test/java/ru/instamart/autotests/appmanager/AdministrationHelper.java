package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.models.UserData;

public class AdministrationHelper extends HelperBase {

    AdministrationHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    // ====== ORDERS =======

    /**
     * Найти заказ по номеру заказа или шипмента
     */
    public void searchOrder(String number) throws Exception {
        kraken.perform().reachAdmin("shipments");
        printMessage("Поиск заказа по номеру заказа/шипмента " + number);
        kraken.perform().fillField(Elements.Admin.Shipments.searchNumberField(), number);
        kraken.perform().click(Elements.Admin.Shipments.searchButton());
    }

    /**
     * Найти заказ по номеру заказа или шипмента
     */
    public void searchB2BOrder(String number) throws Exception {
        kraken.perform().reachAdmin("shipments");
        printMessage("Поиск B2B заказа по номеру заказа/шипмента " + number);
        kraken.perform().fillField(Elements.Admin.Shipments.searchNumberField(), number);
        kraken.perform().click(Elements.Admin.Shipments.b2bCheckbox());
        kraken.perform().click(Elements.Admin.Shipments.searchButton());
    }

    /**
     * Возобновить заказ
     */
    public void resumeOrder() {
        kraken.perform().click(Elements.Admin.Shipments.Order.Details.resumeOrderButton());
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
        kraken.perform().click(Elements.Admin.Shipments.Order.Details.cancelOrderButton());
        handleAlert();
        chooseCancellationReason(reason, details);
        kraken.perform().click(Elements.Admin.Shipments.Order.Details.confirmOrderCancellationButton());
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
    public void searchUser(UserData userData) throws Exception {
        searchUser(userData.getEmail());
    }

    /**
     * Найти пользователя по email
     */
    public void searchUser(String email) throws Exception {
        kraken.perform().reachAdmin("users");
        printMessage("Поиск пользователей по запросу " + email);
        kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
        kraken.perform().click(Elements.Admin.Users.searchButton());
        kraken.perform().waitingFor(1); // Ожидание осуществления поиска юзера в админке
    }

    /**
     * Найти пользователя по реквизитам из указанного объекта userData
     */
    public void searchB2BUser(UserData userData) throws Exception {
        searchB2BUser(userData.getEmail());
    }

    /**
     * Найти B2B пользователя по email
     */
    public void searchB2BUser(String email) throws Exception {
        kraken.perform().reachAdmin("users");
        printMessage("Поиск пользователей по запросу " + email);
        kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
        kraken.perform().click(Elements.Admin.Users.b2bCheckbox());
        kraken.perform().click(Elements.Admin.Users.searchButton());
    }

    /**
     * Перейти в редактирование пользователя из указанного объекта userData
     */
    public void editUser(UserData userData) throws Exception {
        editUser(userData.getEmail());
    }

    /**
     * Перейти в редактирование пользователя с указанием почты
     */
    public void editUser(String email) throws Exception {
        searchUser(email);
        if(kraken.grab().text(Elements.Admin.Users.firstUserLogin()).equals(email.toLowerCase())) {
            editFirstUserInList();
        } else {
            printMessage("! Найден не тот юзер !");
            printMessage("Первый email в списке по локатору: " + kraken.grab().text(Elements.Admin.Users.firstUserLogin()));
            printMessage("А ищем: " + email);
        }
    }

    /**
     * Перейти в редактирование первого пользователя в списке
     */
    public void editFirstUserInList() {
        kraken.perform().click(Elements.Admin.Users.firstUserEditButton());
        kraken.perform().waitingFor(1); // Ожидание загрузки страницы пользователя в админке
        printMessage("Редактирование пользователя " + kraken.grab().currentURL());
    }

    /** Удалить первого найденного пользователя */
    public void deleteFirstFoundUser(String email) throws Exception {
        searchUser(email);
        if (kraken.detect().isElementDisplayed(Elements.Admin.Users.firstUserLogin())) {
            if (kraken.grab().text(Elements.Admin.Users.firstUserLogin()).equalsIgnoreCase(email)) {
                kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton());
                kraken.perform().handleAlert();
            } else {
                kraken.perform().printMessage("Найден не тот пользователь!");
            }
        } else { kraken.perform().printMessage("Пользователь уже удалён!"); }

        }

    /**
     * Предоставить админские права пользователю из указанного объекта userData
     */
    public void grantAdminPrivileges(UserData userData) throws Exception {
        editUser(userData.getEmail());
        grantAdminPrivileges();
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
            printMessage("Предоставлены права администратора");
        }
    }

    /**
     * Отозвать админские права пользователю из указанного объекта userData
     */
    public void revokeAdminPrivileges(UserData userData) throws Exception {
        editUser(userData.getEmail());
        revokeAdminPrivileges();
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

    /**
     * Снять флаг B2B в карточке пользователя
     */
    public void revokeB2B() {
        if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox())) {
            kraken.perform().click(Elements.Admin.Users.UserPage.b2bCheckbox());
            kraken.perform().waitingFor(1); // Ожидание снятия чекбокса B2B
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
            printMessage("Снят флаг B2B");
        } else {
            printMessage("Пользователь уже не B2B");
        }
    }

}
