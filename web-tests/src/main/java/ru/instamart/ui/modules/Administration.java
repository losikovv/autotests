package instamart.ui.modules;

import instamart.core.common.AppManager;
import instamart.ui.common.pagesdata.UserData;
import instamart.ui.objectsmap.Elements;
import io.qameta.allure.Step;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Administration extends Base {

    private static final Logger log = LoggerFactory.getLogger(Administration.class);

    public Administration(final AppManager kraken) {
        super(kraken);
    }

    /** Раздел ЗАКАЗЫ */
    public static class Orders {

        /** Найти заказ по номеру заказа или шипмента */
        @Step("Ищем заказ по номеру заказа или шипмента: {0}")
        public static void searchOrder(String order) {
            kraken.reach().admin("shipments");
            log.info("Поиск заказа по номеру {}", order);
            kraken.perform().fillField(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderNumber(), order);
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.applyFilterButton());
            kraken.await().implicitly(2); // Ожидание поиска заказа в админке
        }

        /** Найти заказ по номеру заказа или шипмента */
        @Step("Ищем заказ по номеру заказа или шипмента: {0}")
        public static void searchOrder(String number, boolean b2b) {
            kraken.reach().admin("shipments");
            log.info("Поиск B2B заказа по номеру {}", number);
            kraken.perform().fillField(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.orderNumber(), number);
            kraken.perform().setCheckbox(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.b2bOnly(),b2b);
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrdersSearchPage.Filters.applyFilterButton());
        }

        /** Возобновить заказ */
        @Step("Возобновляем заказ")
        public static void resumeOrder() {
            log.info("> возобновляем заказ {}", kraken.grab().currentURL());
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.resumeOrderButton());
            handleAlert();
            kraken.await().implicitly(2); // Ожидание возобновления заказа в админке
        }

        /** Отменить заказ на текущей странице с тестовой причиной отмены */
        @Step("Отменяем заказ на текущей странице с тестовой причиной отмены")
        public static void cancelOrder() {
            cancelOrder(4, "Тестовый заказ");
        }

        /** Отменить заказ по номеру с тестовой причиной отмены */
        @Step("Отменяем заказ по номеру с тестовой причиной отмены: {0}")
        public static void cancelOrder(String orderNumber) {
            kraken.reach().admin(instamart.ui.common.lib.Pages.Admin.Order.details(orderNumber));
            cancelOrder();
        }

        /** Отменить заказ на текущей странице с указанными причинами отмены */
        @Step("Отменяем заказ на текущей странице с указанными причинами отмены :{1}")
        public static void cancelOrder(int reason, String details) {
            log.info("> отменяем заказ {}", kraken.grab().currentURL());
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.cancelOrderButton());
            handleAlert();
            chooseCancellationReason(reason, details);
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.confirmOrderCancellationButton());
            kraken.await().implicitly(2); // Ожидание отмены заказа в админке
        }

        /** Выбрать причину и текст отмены заказа */
        @Step("Выбираем причину и текст отмены заказа: {1}")
        private static void chooseCancellationReason(int reasonPosition, String details) {
            kraken.perform().click(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.cancellationReasonType(reasonPosition));
            kraken.perform().fillField(Elements.Administration.ShipmentsSection.OrderDetailsPage.Details.cancellationReasonField(),details);
        }
    }

    /** Раздел ПОЛЬЗОВАТЕЛИ */
    public static class Users {

        /** Поиск пользователей */
        public static void searchUser(UserData userData) {
            searchUser(userData.getLogin());
        }

        @Step("Поиск пользователя по почте: {0}")
        public static void searchUser(String email) {
            kraken.reach().admin("users");
            log.info("Поиск пользователя {}", email);
            kraken.perform().fillField(Elements.Administration.UsersSection.emailField(), email);
            kraken.perform().click(Elements.Administration.UsersSection.searchButton());
            kraken.await().simply(1); // Ожидание осуществления поиска юзера в админке
        }

        @Step("Поиск пользователя по телефону: {0}")
        public static void searchPhone(String phone) {
            kraken.reach().admin("users");
            log.info("Поиск пользователя {}", phone);
            kraken.perform().fillField(Elements.Administration.UsersSection.phoneField(), phone);
            kraken.perform().click(Elements.Administration.UsersSection.searchButton());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Administration.UsersSection.userlistFirstRow().getLocator()),
                    "пользователь по критериям поиска не найден",5);
        }

        /** Перейти в редактирование пользователя из указанного объекта userData */
        public static void editUser(UserData userData) {
            editUser(userData.getLogin());
        }

//        /** Перейти в редактирование пользователя по номеру телефона */
//        public static void editUser(String phone) {
//            editUser(phone);
//        }

        /** Перейти в редактирование пользователя по элементу поиска */
        @Step("Переходим в редактирование пользователя с указанием: {0}")
        public static void editUser(String value) {
            if(value.contains("@")){
                searchUser(value);
                if (kraken.grab().text(Elements.Administration.UsersSection.userEmail()).equals(value.toLowerCase())) {
                    kraken.perform().click(Elements.Administration.UsersSection.editUserButton());
                    kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                            Elements.Administration.UsersSection.UserPage.b2bCheckbox().getLocator()),
                            "страница редактирования пользователя не отобразилась",
                            5);
                    log.info("Редактирование пользователя {}", kraken.grab().currentURL());
                } else {
                    log.warn("! Найден не тот юзер !");
                    log.warn("Первый email в списке по локатору: {}", kraken.grab().text(Elements.Administration.UsersSection.userEmail()));
                    log.warn("А ищем: {}", value);
                }
            }else {
                searchPhone(value);
                if (kraken.getWebDriver().findElements(Elements.Administration.UsersSection.userlistFirstRow().getLocator()).size()==1) {
                    kraken.perform().click(Elements.Administration.UsersSection.editUserButton());
                    kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                            Elements.Administration.UsersSection.UserPage.b2bCheckbox().getLocator()),
                            "страница редактирования пользователя не отобразилась",
                            5);
                    log.info("Редактирование пользователя {}", kraken.grab().currentURL());
                } else {
                    log.warn("! Найден не тот юзер !");
                    log.warn("больше одного юзера по номеру телефона: {0}", value);
                    throw new ElementNotSelectableException("Невозможно выбрать конкретный элемент, " +
                            "тк представлены несколько вариантов");
                }
            }

        }

        /** Удалить первого найденного пользователя */
        @Step("Удаляем первого найденного пользователя: {0}")
        public static void deleteUser(String email) {
            searchUser(email);
            if (kraken.detect().isElementDisplayed(Elements.Administration.UsersSection.userEmail())) {
                if (kraken.grab().text(Elements.Administration.UsersSection.userEmail()).equalsIgnoreCase(email)) {
                    kraken.perform().click(Elements.Administration.UsersSection.deleteUserButton());
                    kraken.perform().handleAlert();
                } else {
                    log.warn("Найден не тот пользователь!");
                }
            } else {
                log.warn("Пользователь уже удалён!");
            }
        }

        /** Предоставить админские права пользователю из указанного объекта userData */
        @Step("Предоставляем админские права пользователю из указанного объекта userData")
        public static void grantAdminPrivileges(UserData userData) {
            editUser(userData.getLogin());
            grantAdminPrivileges();
        }

        /** Предоставить админские права в карточке пользователя */
        @Step("Предоставляем админские права в карточке пользователя")
        public static void grantAdminPrivileges() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox())) {
                log.warn("Административные права были предоставлены ранее");
            } else {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox());
                kraken.await().implicitly(1); // Ожидание проставления чекбокса админских прав
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("Предоставлены права администратора");
            }
        }

        /** Отозвать админские права пользователю из указанного объекта userData */
        @Step("Отзываем админские права пользователю из указанного объекта userData")
        public static void revokeAdminPrivileges(UserData userData) {
            editUser(userData.getLogin());
            revokeAdminPrivileges();
        }

        /** Отозвать админские права в карточке пользователя */
        @Step("Отзываем админские права в карточке пользователя")
        public static void revokeAdminPrivileges() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox())) {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox());
                kraken.await().implicitly(1); // Ожидание снятия чекбокса админских прав
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("Отозваны административные права");
            } else {
                log.warn("Пользователь не имеет административных прав");
            }
        }

        /** Сменить пароль в карточке пользователя */
        @Step("Меняем пароль в карточке пользователя")
        public static void changePassword(String password) {
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.passwordField(), password);
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.passwordConfirmationField(), password);
            kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
            log.info("Смена пароля пользователя");
        }

        /** Сменить email в карточке пользователя */
        @Step("Меняем email в карточке пользователя")
        public static void changeEmail(String email) {
            log.info("меняем email пользователю: " + email);
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.emailField(), email);
            kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
            log.info("email пользователя изменен на: "+email);
        }

        /** Проставить флаг B2B в карточке пользователя */
        @Step("Проставляем флаг B2B в карточке пользователя")
        public static void grantB2B() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox())) {
                log.warn("Пользователь уже B2B");
            } else {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
                kraken.await().implicitly(1); // Ожидание проставления чекбокса B2B
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("Проставлен флаг B2B");
            }
        }

        /** Снять флаг B2B в карточке пользователя */
        @Step("Снимаем флаг B2B в карточке пользователя")
        public static void revokeB2B() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox())) {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
                kraken.await().implicitly(1); // Ожидание снятия чекбокса B2B
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("Снят флаг B2B");
            } else {
                log.warn("Пользователь уже не B2B");
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

            //baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.PageEditPage.pageNameField());
            //baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.PageEditPage.pageURLField());
            Pages.switchSourceEditor();
            //baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.PageEditPage.pageDescriptionField());
            //baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.PageEditPage.pageNameField());
            //baseChecks.checkIsElementPresent(Elements.Administration.PagesSection.PageEditPage.pageURLField());
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
            // TODO: Проверяем, что зашли в редактрование той страницы
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
