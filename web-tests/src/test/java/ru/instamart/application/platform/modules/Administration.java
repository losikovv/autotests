package ru.instamart.application.platform.modules;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.application.Elements;
import ru.instamart.application.AppManager;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.models.UserData;

public class Administration extends Base {

    public Administration(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Раздел ЗАКАЗЫ */
    public static class Orders {

        /** Найти заказ по номеру заказа или шипмента */
        public static void searchOrder(String order) {
            kraken.reach().admin("shipments");
            message("Поиск заказа по номеру " + order);
            kraken.perform().fillField(Elements.Administration.ShipmentsSection.Filters.orderNumber(), order);
            kraken.perform().click(Elements.Administration.ShipmentsSection.applyFilterButton());
            kraken.await().implicitly(2); // Ожидание поиска заказа в админке
        }

        /** Найти заказ по номеру заказа или шипмента */
        public static void searchOrder(String number, boolean b2b) {
            kraken.reach().admin("shipments");
            message("Поиск B2B заказа по номеру " + number);
            kraken.perform().fillField(Elements.Administration.ShipmentsSection.Filters.orderNumber(), number);
            kraken.perform().setCheckbox(Elements.Administration.ShipmentsSection.Checkboxes.b2bOnly(),b2b);
            kraken.perform().click(Elements.Administration.ShipmentsSection.applyFilterButton());
        }

        /** Возобновить заказ */
        public static void resumeOrder() {
            message("> возобновляем заказ " + kraken.grab().currentURL());
            kraken.perform().click(Elements.Administration.ShipmentsSection.Order.Details.resumeOrderButton());
            handleAlert();
            kraken.await().implicitly(2); // Ожидание возобновления заказа в админке
        }

        /** Отменить заказ на текущей странице с тестовой причиной отмены */
        public static void cancelOrder() {
            cancelOrder(4, "Тестовый заказ");
        }

        /** Отменить заказ по номеру с тестовой причиной отмены */
        public static void cancelOrder(String orderNumber) {
            kraken.reach().admin(ru.instamart.application.lib.Pages.Admin.Order.details(orderNumber));
            cancelOrder();
        }

        /** Отменить заказ на текущей странице с указанными причинами отмены */
        public static void cancelOrder(int reason, String details) {
            message("> отменяем заказ " + kraken.grab().currentURL());
            kraken.perform().click(Elements.Administration.ShipmentsSection.Order.Details.cancelOrderButton());
            handleAlert();
            chooseCancellationReason(reason, details);
            kraken.perform().click(Elements.Administration.ShipmentsSection.Order.Details.confirmOrderCancellationButton());
            kraken.await().implicitly(2); // Ожидание отмены заказа в админке
        }

        /** Выбрать причину и текст отмены заказа */
        private static void chooseCancellationReason(int reason, String details) {
            kraken.perform().click(By.id("cancellation_reason_id_" + reason));               // todo вынести в elements
            kraken.perform().fillField(Elements.Administration.ShipmentsSection.Order.Details.cancellationReasonField(),details);
        }
    }

    /** Раздел ПОЛЬЗОВАТЕЛИ */
    public static class Users {

        /** Поиск пользователей */
        public static void searchUser(UserData userData) {
            searchUser(userData.getLogin());
        }

        public static void searchUser(String email) {
            kraken.reach().admin("users");
            message("Поиск пользователя " + email);
            kraken.perform().fillField(Elements.Administration.UsersSection.emailField(), email);
            kraken.perform().click(Elements.Administration.UsersSection.searchButton());
            kraken.await().simply(1); // Ожидание осуществления поиска юзера в админке
        }

        /** Перейти в редактирование пользователя из указанного объекта userData */
        public static void editUser(UserData userData) {
            editUser(userData.getLogin());
        }

        /** Перейти в редактирование пользователя с указанием почты */
        public static void editUser(String email) {
            searchUser(email);
            if(kraken.grab().text(Elements.Administration.UsersSection.userEmail()).equals(email.toLowerCase())) {
                kraken.perform().click(Elements.Administration.UsersSection.editUserButton());
                kraken.await().implicitly(1); // Ожидание загрузки страницы пользователя в админке
                message("Редактирование пользователя " + kraken.grab().currentURL());
            } else {
                message("! Найден не тот юзер !");
                message("Первый email в списке по локатору: " + kraken.grab().text(Elements.Administration.UsersSection.userEmail()));
                message("А ищем: " + email);
            }
        }

        /** Удалить первого найденного пользователя */
        public static void deleteUser(String email) {
            searchUser(email);
            if (kraken.detect().isElementDisplayed(Elements.Administration.UsersSection.userEmail())) {
                if (kraken.grab().text(Elements.Administration.UsersSection.userEmail()).equalsIgnoreCase(email)) {
                    kraken.perform().click(Elements.Administration.UsersSection.deleteUserButton());
                    kraken.perform().handleAlert();
                } else {
                    message("Найден не тот пользователь!");
                }
            } else { message("Пользователь уже удалён!"); }

        }

        /** Предоставить админские права пользователю из указанного объекта userData */
        public static void grantAdminPrivileges(UserData userData) {
            editUser(userData.getLogin());
            grantAdminPrivileges();
        }

        /** Предоставить админские права в карточке пользователя */
        public static void grantAdminPrivileges() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox())) {
                message("Административные права были предоставлены ранее");
            } else {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox());
                kraken.await().implicitly(1); // Ожидание проставления чекбокса админских прав
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                message("Предоставлены права администратора");
            }
        }

        /** Отозвать админские права пользователю из указанного объекта userData */
        public static void revokeAdminPrivileges(UserData userData) {
            editUser(userData.getLogin());
            revokeAdminPrivileges();
        }

        /** Отозвать админские права в карточке пользователя */
        public static void revokeAdminPrivileges() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox())) {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox());
                kraken.await().implicitly(1); // Ожидание снятия чекбокса админских прав
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                message("Отозваны административные права");
            } else {
                message("Пользователь не имеет административных прав");
            }
        }

        /** Сменить пароль в карточке пользователя */
        public static void changePassword(String password) {
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.passwordField(), password);
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.passwordConfirmationField(), password);
            kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
            message("Смена пароля пользователя");
        }

        /** Проставить флаг B2B в карточке пользователя */
        public static void grantB2B() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox())) {
                message("Пользователь уже B2B");
            } else {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
                kraken.await().implicitly(1); // Ожидание проставления чекбокса B2B
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                message("Проставлен флаг B2B");
            }
        }

        /** Снять флаг B2B в карточке пользователя */
        public static void revokeB2B() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox())) {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
                kraken.await().implicitly(1); // Ожидание снятия чекбокса B2B
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                message("Снят флаг B2B");
            } else {
                message("Пользователь уже не B2B");
            }
        }
    }

    /** Раздел СТРАНИЦЫ */
    public static class Pages {

        public static void switchSourceEditor(){
            kraken.await().implicitly(1); // ждём, пока прогрузится html-ка с описанием
            kraken.perform().click(Elements.Administration.PagesSection.PageEditPage.pageDescriptionSourceButton()); // минутка магии в html-редакторе
        }

        public static void validateStaticPage(String name){
            kraken.get().adminPage("pages/new");

            //assertPresence(Elements.Administration.PagesSection.PageEditPage.pageNameField());
            //assertPresence(Elements.Administration.PagesSection.PageEditPage.pageURLField());
            Pages.switchSourceEditor();
            //assertPresence(Elements.Administration.PagesSection.PageEditPage.pageDescriptionField());
            //assertPresence(Elements.Administration.PagesSection.PageEditPage.pageNameField());
            //assertPresence(Elements.Administration.PagesSection.PageEditPage.pageURLField());
        }

        public static void create(String name, String URL, String desc){
            kraken.perform().click(Elements.Administration.PagesSection.newPageButton());
            kraken.perform().fillField(Elements.Administration.PagesSection.PageEditPage.pageNameField(), name);
            kraken.perform().fillField(Elements.Administration.PagesSection.PageEditPage.pageURLField(), URL);
            Pages.switchSourceEditor();
            kraken.perform().fillField(Elements.Administration.PagesSection.PageEditPage.pageDescriptionField(), desc);
            kraken.perform().click(Elements.Administration.PagesSection.PageEditPage.savePageButton());
        }

        public static void editStaticPage(String name){
            // TODO: поправить метод так, чтобы он хоть что-нибудь толковое проверял
            String pageDesc = "Abra Kadabra Ahalai Mahalai";
            kraken.perform().click(Elements.Administration.PagesSection.editPageButton(name));
            // TODO: проверить, что зашли в редактрование той страницы
//            if (kraken.grab().value(Elements.Administration.PagesSection.PageEditPage.pageNameField()) == name) {
                Pages.switchSourceEditor();
                kraken.perform().fillField(Elements.Administration.PagesSection.PageEditPage.pageDescriptionField(), pageDesc);
                kraken.await().implicitly(1);
                kraken.perform().click(Elements.Administration.PagesSection.PageEditPage.savePageButton());
//            }
/*            else {
                message("Редактируем не ту страницу");
            }
*/
        }

        public static void delete(String name){
            kraken.perform().click(Elements.Administration.PagesSection.deletePageButton(name));
            handleAlert();
        }

        // проверяем существование статической страницы
        public static void validateStaticPage(String name, String URL){
            kraken.get().page(URL);
            kraken.detect().isElementPresent(Elements.StaticPages.pageTitle());
            kraken.get().adminPage("pages");
        }
    }
}
