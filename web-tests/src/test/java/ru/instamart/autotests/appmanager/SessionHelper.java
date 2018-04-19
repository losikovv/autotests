package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;



    // Session helper is for handling testing sessions
    // Contains various methods for user and orders



public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver) {
        super(driver);
    }

    // TODO перенести в HelperBase + все методы по заказам перенести в OrdersHelper
    public void getUrlAsAdmin(String targetUrl) {
        // trying to get target URL in admin panel
        getUrl(targetUrl);
        // if we don't have admin privileges then log-in as admin and try again
        if (!itsInAdmin()) {
            getBaseUrl();
            if (isUserAuthorised()) {
                doLogout();
            }
            doLoginAsAdmin();
            getUrl(targetUrl);
        }
    }

    // ======= Registration =======

    /**
     * Method finds out if a given username is already registered in the system or not
     */
    public boolean isUserRegistered(String username) {
        // TODO идем в админку и чекаем наличие пользователя в поиске
        return true;
    }

    /**
     * Do new user registration with given user data
     */
    public void regNewUser(UserData userData) {
        if (itsOnLandingPage()) {
            regNewUserOnLandingPage(userData);
        } else {
            regNewUserOnRetailerPage(userData);
        }
    }

    /**
     * Do new user registration on landing page with a given user data
     */
    public void regNewUserOnLandingPage(UserData userData){
        printMessage("Performing user registration on landing page");
        // открываем форму авторизации/регистрации
        click(By.xpath("/html/body/div[4]/header/div[2]/ul/li[3]"));
        // нажимаем кнопку Регистрация
        click(By.id("pop__registration"));
        // заполняем поле "Имя и фамилия"
        fillField(By.id("spree_user_fullname"), userData.getName());
        // заполняем поле "Электронная почта"
        fillField(By.id("spree_user_email"), userData.getLogin());
        // заполняем поле "Пароль"
        fillField(By.id("spree_user_password"), userData.getPassword());
        // заполняем поле "Подтвердите ароль"
        fillField(By.id("spree_user_password_confirmation"), userData.getPassword());
        // нажимаем "Зарегистрироваться"
        click(By.xpath("//*[@id='signup_form']/ul[2]/li[1]/input[2]"));
        // задержка чтобы пользователь нормально зарегался
        waitForIt();
    }

    /**
     * Do new user registration on retailer page with given user data
     */
    public void regNewUserOnRetailerPage(UserData userData){
        printMessage("Performing user registration on retailer page");
        // открываем форму авторизации/регистрации
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button"));
        // нажимаем кнопку Регистрация
        click(By.id("pop__registration"));
        // заполняем поле "Имя и фамилия"
        fillField(By.id("spree_user_fullname"), userData.getName());
        // заполняем поле "Электронная почта"
        fillField(By.id("spree_user_email"), userData.getLogin());
        // заполняем поле "Пароль"
        fillField(By.id("spree_user_password"), userData.getPassword());
        // заполняем поле "Подтвердите ароль"
        fillField(By.id("spree_user_password_confirmation"), userData.getPassword());
        // нажимаем "Зарегистрироваться"
        click(By.xpath("//*[@id='signup_form']/ul[2]/li[1]/input[2]"));
        // задержка чтобы пользователь нормально зарегался
        waitForIt();
    }



    // ======= Password recovery =======
    // TODO добавить метод восстановления пароля + использующий его тест для autotestuser



    // ======= Authorisation =======

    /**
     * Method returns true if user is authorised and false if he isn't
     */
    public boolean isUserAuthorised() {
        // Проверяем авторизованность по наличию на странице кнопки "Профиль"
        String XPATH = "//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]/div[1]";
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("Профиль");
        } else {
            return false;
        }
    }

    /**
     * Do log-in with a given user data
     */
    public void doLogin(UserData userData) {
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(userData);
        } else {
            doLoginOnRetailerPage(userData);
        }
    }

    /**
     * Do log-in on landing page with a given user data
     */
    public void doLoginOnLandingPage(UserData userData) {
        // открываем форму авторизации/регистрации
        click(By.xpath("/html/body/div[4]/header/div[2]/ul/li[3]"));
        // вводим логин
        fillField(By.id("login_form__email"), userData.getLogin());
        // вводим пароль
        fillField(By.id("login_form__password"), userData.getPassword());
        // клик по кнопке Вход
        click(By.xpath("(//input[@name='commit'])[2]"));
        // задержка чтобы пользователь нормально авторизовался
        waitForIt();
        printMessage("Performing user log-in on landing page");
    }

    /**
     * Do log-in on retailer page page with a given user data
     */
    public void doLoginOnRetailerPage(UserData userData) {
        // открываем форму авторизации/регистрации
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button"));
        // вводим логин
        fillField(By.xpath("//*[@id='login_form__email']"), userData.getLogin());
        // вводим пароль
        fillField(By.xpath("//*[@id='login_form__password']"), userData.getPassword());
        // клик по кнопке Вход
        click(By.xpath("//*[@id='login_form']/ul[1]/li[4]/input[2]"));
        // задержка чтобы пользователь нормально авторизовался
        waitForIt();
        printMessage("Performing user log-in on retailer page");
    }



    // ======= De-Authorisation =======

    /**
     * Do log-out
     */
    public void doLogout() {
        if (!itsInAdmin()) {
            doLogoutFromSite();
        } else {
            doLogoutFromAdmin();
        }
    }

    /**
     * Do log-out from site
     */
    public void doLogoutFromSite() {
        //клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        //клик по кнопке Выйти
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a"));
        //задержка чтобы пользователь нормально разлогинился
        waitForIt();
        printMessage("Logged-out from site\n");
    }

    /**
     * Do log-out from admin panel
     */
    public void doLogoutFromAdmin() {
        //клик по кнопке Выйти
        click(By.xpath("//*[@id='login-nav']/li[3]/a"));
        //задержка чтобы пользователь нормально разлогинился
        waitForIt();
        printMessage("Logged-out from admin\n");
    }



    // ======= Handling test users =======

    /**
     * Do new user registration with generated user data
     */
    public void regNewAutotestUser() {
        UserData userData = Generate.autotestUserData();
        if (itsOnLandingPage()) {
            regNewUserOnLandingPage(userData);
        } else {
            regNewUserOnRetailerPage(userData);
        }
        printMessage("New autotest user has been registered\n");
    }

    /**
     * Do log-in with user credentials of autotest@instamart.ru which is reserved for autotests and have admin privileges
     */
    public void doLoginAsAdmin() {
        final String LOGIN = "autotestuser@instamart.ru";
        final String PASSWORD = "DyDrasLipMeibe7";
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(new UserData(LOGIN, PASSWORD, null));
        } else {
            doLoginOnRetailerPage(new UserData(LOGIN, PASSWORD, null));
        }
        printMessage("Logged-in with admin privileges as " + LOGIN + "\n");
    }

    /**
     * Do log-in with user credentials of instatestuser@yandex.ru which is reserved for testing needs
     */
    public void doLoginAsReturningCustomer() {
        final String LOGIN = "instatestuser@yandex.ru";
        final String PASSWORD = "instamart";
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(new UserData(LOGIN, PASSWORD, null));
        } else {
            doLoginOnRetailerPage(new UserData(LOGIN, PASSWORD, null));
        }
        printMessage("Logged-in as " + LOGIN + "\n");
    }

    /**
     * Do log-in with Facebook as reserved for testing needs user
     */
    public void doLoginWithFacebook() {
        //TODO
    }

    /**
     * Do log-in with VKontakte as reserved for testing needs user
     */
    public void doLoginWithVKontakte() {
        //TODO
    }

    /**
     * Delete all autotest users from admin panel
     */
    public void deleteAllAutotestUsers() {
        // Getting target URL in admin panel which contains table with autotest users only
        getUrlAsAdmin(baseUrl + "admin/users?q%5Bemail_cont%5D=%40example.com");
        // Delete first user if it's present in the list
        if(isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr"))) {
            deleteFirstUserInTable();
            printMessage("Autotest user has been deleted");
            // Keep deleting users recursively
            deleteAllAutotestUsers();
        } else {
            printMessage("Autotest users deletion is complete\n");
        }
    }

    /**
     * Delete first user in the Users table in admin panel
     */
    private void deleteFirstUserInTable() {
        click(By.xpath("//*[@id='content']/div/table/tbody/tr/td[3]/a[2]"));
        closeAlertAndGetItsText();
        waitForIt();
    }



    // ======= Handling test orders =======

    /**
     * Cancel all test orders from admin panel
     */
    public void cancelAllTestOrders() {
        // Getting target URL in admin panel which contains table with test orders only
        // фильтр по частичному совпадению email пока не работает,
        // поэтому тестовые заказы пока делаем с autotestuser@instamart.ru
        // позже нужно переделать под юзеров @example.com
        getUrlAsAdmin(baseUrl + "admin/shipments?search%5Bemail%5D=autotestuser%40instamart.ru&search%5Bonly_completed%5D=1&search%5Bstate%5D=ready");
        // Cancel first order if it's present in the list
        if(!isElementPresent(By.className("no-objects-found"))) {
            cancelFirstOrderInTable();
            printMessage("Test order has been canceled");
            // Keep cancelling orders recursively
            cancelAllTestOrders();
        } else {
            printMessage("Test orders cancellation is complete\n");
        }
    }

    /**
     * Cancel first order in the Shipments table in admin panel
     */
    private void cancelFirstOrderInTable(){
        // Go to the first order in table
        click(By.xpath("//*[@id='listing_orders']/tbody/tr/td[14]/a"));
        waitForIt();
        // Perform cancellation
        cancelOrder();
        printMessage("Order has been canceled");
    }

    /**
     * Cancel order with the given number
     */
    public void cancelOrder(String orderNumber){
        // get order page in admin panel
        getUrlAsAdmin(baseUrl + "admin/orders/" + orderNumber + "/edit");
        cancelOrder();
    }

    /**
     * Cancel order on the order page in admin panel
     */
    public void cancelOrder(){
        // click cancel button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order cancellation alert
        closeAlertAndGetItsText();
        // fill order cancellation reason form
        fillField(By.name("cancellation[reason]"),"Тестовый заказ");
        // click confirmation button
        click(By.xpath("//*[@id='new_cancellation']/fieldset/div[2]/button"));
        waitForIt();
    }

    /**
     * Resume order with the given number
     */
    public void resumeOrder(String orderNumber){
        // get order page in admin panel
        getUrlAsAdmin(baseUrl + "admin/orders/" + orderNumber + "/edit");
        resumeOrder();
    }

    /**
     * Resume order on the order page in admin panel
     */
    public void resumeOrder(){
        // click resume button
        click(By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button"));
        // accept order resume alert
        closeAlertAndGetItsText();
        waitForIt();
    }

    /**
     * Find out if the order with the given order number is canceled
     */
    public boolean isOrderCanceled(String orderNumber) {
        // get order page in admin panel
        getUrlAsAdmin(baseUrl + "admin/orders/" + orderNumber + "/edit");
        return isOrderCanceled();
    }

    /**
     * Find out if the order is canceled by checking the order page in admin panel
     */
    public boolean isOrderCanceled() {
        String XPATH = "//*[@id='content']/div/table/tbody/tr[3]/td/b";
        if (isElementPresent(By.xpath(XPATH))){
            return getText(By.xpath(XPATH)).equals("ЗАКАЗ ОТМЕНЕН");
        } else {
            return false;
        }
    }

}
