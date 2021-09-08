package ru.instamart.ui.module;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.ElementNotSelectableException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.testdata.pagesdata.StaticPageData;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.ui.Elements;
import ru.instamart.ui.config.WaitProperties;
import ru.instamart.ui.manager.AppManager;

import static ru.instamart.ui.helper.HelperBase.handleAlert;

@Slf4j
public final class Administration extends Base {

    public Administration(final AppManager kraken) {
        super(kraken);
    }
    /**Наигация по админке */
    public static class AdminNavigation{

        @Step("Переходим в раздел: {0}")
        public static void switchTotab(String menuElement){
            log.info("> переходим по разделам меню: {}",menuElement);
            kraken.perform().click(Elements.Administration.submenuElement(menuElement));
        }

        @Step("Открываем dropdown элемент: {0}")
        public static void openMenuDropdown(String menuElement){
            log.info("> переходим по разделам меню: {}",menuElement);
            kraken.await().fluently(ExpectedConditions.elementToBeClickable(Elements.Administration.menuTopElement(menuElement).getLocator()),
                    "элемент не доступен: "+menuElement, WaitProperties.BASIC_TIMEOUT);
            kraken.perform().scrollToTheBottom(Elements.Administration.menuTopElement(menuElement));
            ThreadUtil.simplyAwait(0.5);
            kraken.perform().click(Elements.Administration.menuTopElement(menuElement));
        }
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
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Administration.ShipmentsSection.OrdersSearchPage.OrdersTable.orderRow.orderNumber().getLocator()),
                    "не подгрузились результаты поиска", WaitProperties.BASIC_TIMEOUT);
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
        }

        /** Отменить заказ на текущей странице с тестовой причиной отмены */
        @Step("Отменяем заказ на текущей странице с тестовой причиной отмены")
        public static void cancelOrder() {
            cancelOrder(4, "Тестовый заказ");
        }

        /** Отменить заказ по номеру с тестовой причиной отмены */
        @Step("Отменяем заказ по номеру с тестовой причиной отмены: {0}")
        public static void cancelOrder(String orderNumber) {
            kraken.reach().admin(ru.instamart.kraken.testdata.lib.Pages.Admin.Order.details(orderNumber));
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
            searchUser(userData.getEmail());
        }

        @Step("Поиск пользователя по почте: {0}")
        public static void searchUser(String email) {
            kraken.reach().admin("users");
            log.info("Поиск пользователя {}", email);
            kraken.perform().fillField(Elements.Administration.UsersSection.emailField(), email);
            kraken.perform().click(Elements.Administration.UsersSection.searchButton());
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                    Elements.Administration.UsersSection.userlistFirstRow().getLocator()),
                    "результаты поиска не отобразились",2);
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
            editUser(userData.getEmail());
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
                if (AppManager.getWebDriver().findElements(Elements.Administration.UsersSection.userlistFirstRow().getLocator()).size()==1) {
                    kraken.perform().click(Elements.Administration.UsersSection.editUserButton());
                    kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(
                            Elements.Administration.UsersSection.UserPage.b2bCheckbox().getLocator()),
                            "страница редактирования пользователя не отобразилась",
                            5);
                    log.info("Редактирование пользователя {}", kraken.grab().currentURL());
                } else {
                    log.warn("! Найден не тот юзер !");
                    log.warn("больше одного юзера по номеру телефона: {}", value);
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
                    handleAlert();
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
            editUser(userData.getEmail());
            grantAdminPrivileges();
        }

        /** Предоставить админские права в карточке пользователя */
        @Step("Предоставляем админские права в карточке пользователя")
        public static void grantAdminPrivileges() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox())) {
                log.warn("> права администратора были предоставлены ранее");
            } else {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox());
                kraken.await().fluently(
                        ExpectedConditions.elementSelectionStateToBe(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox().getLocator(),
                                true),"не проставляется чекбокс с админскими правами",2);
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("> предоставлены права администратора");
            }
        }

        /** Отозвать админские права пользователю из указанного объекта userData */
        @Step("Отзываем админские права пользователю из указанного объекта userData")
        public static void revokeAdminPrivileges(UserData userData) {
            editUser(userData.getEmail());
            revokeAdminPrivileges();
        }

        /** Отозвать админские права пользователю из указанного объекта userData */
        @Step("Отзываем админские права пользователю из указанного объекта userData")
        public static void revokeAdminPrivileges(String phone) {
            editUser(phone);
            revokeAdminPrivileges();
        }

        /** Отозвать админские права в карточке пользователя */
        @Step("Отзываем админские права в карточке пользователя")
        public static void revokeAdminPrivileges() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox())) {
                log.info("> отзываем права администратора");
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.adminRoleCheckbox());
                kraken.await().fluently(
                        ExpectedConditions.elementSelectionStateToBe(
                                Elements.Administration.UsersSection.UserPage.adminRoleCheckbox().getLocator(),false),
                        "не снимается чекбокс с админскими правами",2);
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("> отозваны права администратора");
            } else {
                log.warn("> пользователь не имеет административных прав");
            }
        }

        /** Сменить пароль в карточке пользователя */
        @Step("Меняем пароль в карточке пользователя")
        public static void changePassword(String password) {
            log.info("> меняем пароль пользователю: {}",password);
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.passwordField(), password);
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.passwordConfirmationField(), password);
            kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
            log.info("> пароль сменен на: {}",password);
        }

        /** Сменить email в карточке пользователя */
        @Step("Меняем email в карточке пользователя")
        public static void changeEmail(String email) {
            log.info("> меняем email пользователю: {}",email);
            kraken.perform().fillField(Elements.Administration.UsersSection.UserPage.emailField(), email);
            kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
            kraken.await().fluently(ExpectedConditions.invisibilityOfElementLocated(
                    Elements.Administration.UsersSection.UserPage.successChangeUserMessage().getLocator()),
                    "сообщение не исчезло",10);
            log.info("> email пользователя изменен на: {}",email);
        }

        /** Проставить флаг B2B в карточке пользователя */
        @Step("Проставляем флаг B2B в карточке пользователя")
        public static void grantB2B() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox())) {
                log.warn("> Пользователь уже B2B");
            } else {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
                kraken.await().fluently(ExpectedConditions.elementSelectionStateToBe(
                        Elements.Administration.UsersSection.UserPage.b2bCheckbox().getLocator(),true),
                        "Не проставляется чекбокс с признаком b2b",2);
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("Проставлен флаг B2B");
            }
        }

        /** Снять флаг B2B в карточке пользователя */
        @Step("Снимаем флаг B2B в карточке пользователя")
        public static void revokeB2B() {
            if (kraken.detect().isCheckboxSet(Elements.Administration.UsersSection.UserPage.b2bCheckbox())) {
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.b2bCheckbox());
                kraken.perform().click(Elements.Administration.UsersSection.UserPage.saveButton());
                log.info("Снят флаг B2B");
            } else {
                log.warn("Пользователь уже не B2B");
            }
        }
    }

    /** Раздел СТРАНИЦЫ */
    public static class Pages {

        @Step("Переходим в поле редактирования")
        public static void switchSourceEditor(){
            log.info("> переключаем источник ввода текста");
            int counter =0;
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.Administration.StaticPagesSection.PageEditPage.pageDescriptionSourceButton().getLocator()),
                    "Недоступна кнопка переключения режима ввода",2);
            kraken.perform().hoverOn(Elements.Administration.StaticPagesSection.PageEditPage.pageDescriptionSourceButton());
            kraken.perform().click(Elements.Administration.StaticPagesSection.PageEditPage.pageDescriptionSourceButton());// минутка магии в html-редакторе
        }

        @Step("Создаем статическую страницу")
        public static void create(StaticPageData staticPages){
            log.info("> создаем статическую страницу с именем: {}, адресом: {} и описанием: {}",
                    staticPages.getPageName(),staticPages.getPageURL(),staticPages.getDescription());
            kraken.perform().click(Elements.Administration.StaticPagesSection.newPageButton());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageNameField(), staticPages.getPageName());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageURLField(), staticPages.getPageURL());
            Pages.switchSourceEditor();
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageMetaTitleField(), staticPages.getText());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageKeyWordsField(), staticPages.getText());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageDescriptionField(), staticPages.getText());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageExternalURLField(), staticPages.getText());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pagePositionField(), staticPages.getPosition());
            kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageMainTextField(), staticPages.getDescription());
            kraken.perform().click(Elements.Administration.StaticPagesSection.PageEditPage.savePageButton());
            log.info("> статическая страница создана");
        }

        @Step("Редактируем ранее созданную статическую страницу")
        public static void editStaticPage(StaticPageData staticPageOld,StaticPageData staticPageDataNew){
            log.info("> находим ранее созданную статическую страницу с именем: {} и нажимаем кнопку редактировать",
                    staticPageOld.getPageName());
            kraken.perform().click(Elements.Administration.StaticPagesSection.editPageButton(staticPageOld.getPageName()));
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.Administration.StaticPagesSection.PageEditPage.pageNameField().getLocator()),
                    "не открылась форма редактирования статической страницы",2);
            if (kraken.grab().value(Elements.Administration.StaticPagesSection.PageEditPage.pageNameField()).equals(staticPageOld.getPageName())) {
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageNameField(), staticPageDataNew.getPageName());
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageURLField(), staticPageDataNew.getPageURL());
                Pages.switchSourceEditor();
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageMetaTitleField(), staticPageDataNew.getText());
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageKeyWordsField(), staticPageDataNew.getText());
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageDescriptionField(), staticPageDataNew.getText());
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageExternalURLField(), staticPageDataNew.getText());
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pagePositionField(), staticPageDataNew.getPosition());
                kraken.perform().fillField(Elements.Administration.StaticPagesSection.PageEditPage.pageMainTextField(), staticPageDataNew.getDescription());
                kraken.perform().click(Elements.Administration.StaticPagesSection.PageEditPage.savePageButton());
                log.info("> статическая страница отредактирована");
            }
            else {
                throw new ElementNotInteractableException("!!!тест выбрал неправильную страницу для редактирования!!!");
            }
        }

        @Step("Удаляем статическую страницу")
        public static void delete(String name){
            log.info("> Удаляем статическую страницу: {}",name);
            kraken.await().fluently(ExpectedConditions.visibilityOfElementLocated(Elements.Administration.StaticPagesSection.deletePageButton(name).getLocator()),
                    "Не появилась кнопка удаления страницы",1);
            kraken.perform().hoverOn(Elements.Administration.StaticPagesSection.deletePageButton(name));
            kraken.perform().click(Elements.Administration.StaticPagesSection.deletePageButton(name));
            handleAlertAcceptByDefault();
//            kraken.await().fluently(
//                    ExpectedConditions.visibilityOfElementLocated(
//                            Elements.Administration.StaticPagesSection.pageDeletionConfirmationMessage().getLocator()),
//                    "не появилось сообщение с подтверждением удаления статической страницы",2);
            kraken.await().fluently(
                    ExpectedConditions.invisibilityOfElementLocated(
                            Elements.Administration.StaticPagesSection.editPageButton(name).getLocator()),
                    "статическая страница не исчезла из списка созданных страниц",3);
            log.info("> статическая страница удалена: {}",name);
        }

        public static void checkAndDeleteIfExists(String name){
            Allure.step("Предусловие, проверяем существует ли страница, если да, то удаляем",()->{
                log.info("> Предусловие, проверяем существует ли страница, если да, то удаляем: {}",name);
                try{
                    delete(name);
                }catch (Exception ex){
                    log.info("> статическая страница не существует: {}, предусловие не нужно",name);
                }
            });
        }

        @Step("Проверяем существование статической страницы")
        public static void validateStaticPage(String name, String URL){
            log.info("> проверяем существование статической страницы: {}",name);
            kraken.get().page(URL);
            kraken.detect().isElementPresent(Elements.Administration.StaticPagesSection.pageTitle());
            kraken.get().adminPage("pages");
            log.info("✓ Успешно");
        }

        @Step("Проверяем,что статическая страница не существует")
        public static void validateStaticNotExistsPage(String name, String URL){
            log.info("> проверяем, что статическая страница не существует: {}",name);
            kraken.get().page(URL);
            kraken.detect().is404();
            log.info("✓ Успешно");
        }
    }
}
