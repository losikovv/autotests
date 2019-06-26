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

    /** Раздел ЗАКАЗЫ */
    public static class Orders {

        /** Найти заказ по номеру заказа или шипмента */
        public static void searchOrder(String order) {
            kraken.reach().admin("shipments");
            message("Поиск заказа по номеру " + order);
            kraken.perform().fillField(Elements.Admin.Shipments.searchNumberField(), order);
            kraken.perform().click(Elements.Admin.Shipments.searchButton());
            kraken.await().implicitly(2); // Ожидание поиска заказа в админке
        }

        /** Найти заказ по номеру заказа или шипмента */
        public static void searchOrder(String number, boolean b2b) {
            kraken.reach().admin("shipments");
            message("Поиск B2B заказа по номеру " + number);
            kraken.perform().fillField(Elements.Admin.Shipments.searchNumberField(), number);
            kraken.perform().setCheckbox(Elements.Admin.Shipments.b2bOnlyCheckbox(),b2b);
            kraken.perform().click(Elements.Admin.Shipments.searchButton());
        }

        /** Возобновить заказ */
        public static void resumeOrder() {
            message("> возобновляем заказ " + kraken.grab().currentURL());
            kraken.perform().click(Elements.Admin.Shipments.Order.Details.resumeOrderButton());
            handleAlert();
            kraken.await().implicitly(2); // Ожидание возобновления заказа в админке
        }

        /** Отменить заказ на текущей странице с тестовой причиной отмены */
        public static void cancelOrder() {
            cancelOrder(4, "Тестовый заказ");
        }

        /** Отменить заказ по номеру с тестовой причиной отмены */
        public static void cancelOrder(String orderNumber) {
            kraken.reach().admin(ru.instamart.autotests.application.Pages.Admin.Order.details(orderNumber));
            cancelOrder();
        }

        /** Отменить заказ на текущей странице с указанными причинами отмены */
        public static void cancelOrder(int reason, String details) {
            message("> отменяем заказ " + kraken.grab().currentURL());
            kraken.perform().click(Elements.Admin.Shipments.Order.Details.cancelOrderButton());
            handleAlert();
            chooseCancellationReason(reason, details);
            kraken.perform().click(Elements.Admin.Shipments.Order.Details.confirmOrderCancellationButton());
            kraken.await().implicitly(2); // Ожидание отмены заказа в админке
        }

        /** Выбрать причину и текст отмены заказа */
        private static void chooseCancellationReason(int reason, String details) {
            kraken.perform().click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
            kraken.perform().fillField(By.id("cancellation_reason_details"),details);        // todo вынести в elements
        }
    }

    /** Раздел ПОЛЬЗОВАТЕЛИ */
    public static class Users {

        /** Поиск пользователей */
        public static void searchUser(UserData userData) {
            searchUser(userData.getEmail());
        }

        public static void searchUser(UserData userData, boolean b2b, boolean tenant) {
            searchUser(userData.getEmail(), b2b, tenant);
        }

        private static void searchUser(String email) {
            searchUser(email, false, false);
        }

        private static void searchUser(String email, boolean b2b, boolean tenant) {
            kraken.reach().admin("users");
            message("Поиск пользователей по email " + email);
            kraken.perform().fillField(Elements.Admin.Users.searchField(), email);
            kraken.perform().setCheckbox(Elements.Admin.Users.b2bCheckbox(), b2b);
            kraken.perform().setCheckbox(Elements.Admin.Users.tenantCheckbox(), tenant);
            kraken.perform().click(Elements.Admin.Users.searchButton());
            kraken.await().implicitly(1); // Ожидание осуществления поиска юзера в админке
        }

        /** Перейти в редактирование пользователя из указанного объекта userData */
        public static void editUser(UserData userData) {
            editUser(userData.getEmail());
        }

        /** Перейти в редактирование пользователя с указанием почты */
        public static void editUser(String email) {
            searchUser(email);
            if(kraken.grab().text(Elements.Admin.Users.firstUserLogin()).equals(email.toLowerCase())) {
                editFirstUserInList();
            } else {
                message("! Найден не тот юзер !");
                message("Первый email в списке по локатору: " + kraken.grab().text(Elements.Admin.Users.firstUserLogin()));
                message("А ищем: " + email);
            }
        }

        /** Перейти в редактирование первого пользователя в списке */
        public static void editFirstUserInList() {
            kraken.perform().click(Elements.Admin.Users.firstUserEditButton());
            kraken.await().implicitly(1); // Ожидание загрузки страницы пользователя в админке
            message("Редактирование пользователя " + kraken.grab().currentURL());
        }

        /** Удалить первого найденного пользователя */
        public static void deleteFirstFoundUser(String email) {
            searchUser(email);
            if (kraken.detect().isElementDisplayed(Elements.Admin.Users.firstUserLogin())) {
                if (kraken.grab().text(Elements.Admin.Users.firstUserLogin()).equalsIgnoreCase(email)) {
                    kraken.perform().click(Elements.Admin.Users.firstUserDeleteButton());
                    kraken.perform().handleAlert();
                } else {
                    message("Найден не тот пользователь!");
                }
            } else { message("Пользователь уже удалён!"); }

        }

        /** Предоставить админские права пользователю из указанного объекта userData */
        public static void grantAdminPrivileges(UserData userData) {
            editUser(userData.getEmail());
            grantAdminPrivileges();
        }

        /** Предоставить админские права в карточке пользователя */
        public static void grantAdminPrivileges() {
            if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.adminCheckbox())) {
                message("Административные права были предоставлены ранее");
            } else {
                kraken.perform().click(Elements.Admin.Users.UserPage.adminCheckbox());
                kraken.await().implicitly(1); // Ожидание проставления чекбокса админских прав
                kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
                message("Предоставлены права администратора");
            }
        }

        /** Отозвать админские права пользователю из указанного объекта userData */
        public static void revokeAdminPrivileges(UserData userData) {
            editUser(userData.getEmail());
            revokeAdminPrivileges();
        }

        /** Отозвать админские права в карточке пользователя */
        public static void revokeAdminPrivileges() {
            if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.adminCheckbox())) {
                kraken.perform().click(Elements.Admin.Users.UserPage.adminCheckbox());
                kraken.await().implicitly(1); // Ожидание снятия чекбокса админских прав
                kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
                message("Отозваны административные права");
            } else {
                message("Пользователь не имеет административных прав");
            }
        }

        /** Сменить пароль в карточке пользователя */
        public static void changePassword(String password) {
            kraken.perform().fillField(Elements.Admin.Users.UserPage.passwordField(), password);
            kraken.perform().fillField(Elements.Admin.Users.UserPage.passwordConfirmationField(), password);
            kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
            message("Смена пароля пользователя");
        }

        /** Проставить флаг B2B в карточке пользователя */
        public static void grantB2B() {
            if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox())) {
                message("Пользователь уже B2B");
            } else {
                kraken.perform().click(Elements.Admin.Users.UserPage.b2bCheckbox());
                kraken.await().implicitly(1); // Ожидание проставления чекбокса B2B
                kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
                message("Проставлен флаг B2B");
            }
        }

        /** Снять флаг B2B в карточке пользователя */
        public static void revokeB2B() {
            if (kraken.detect().isCheckboxSelected(Elements.Admin.Users.UserPage.b2bCheckbox())) {
                kraken.perform().click(Elements.Admin.Users.UserPage.b2bCheckbox());
                kraken.await().implicitly(1); // Ожидание снятия чекбокса B2B
                kraken.perform().click(Elements.Admin.Users.UserPage.saveButton());
                message("Снят флаг B2B");
            } else {
                message("Пользователь уже не B2B");
            }
        }
    }

    /** Раздел СТРАНИЦЫ */
    public static class Pages {
        // TODO
    }
}
