package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;
import ru.instamart.autotests.testdata.Generate;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver) {
        super(driver);
    }



    // Session helper for handling user registration, login and logout
    // TODO добавить методы авторизации через соцсети
    // TODO добавить метод восстановления пароля + использующий его тест для autotestuser



    /** Do new user registration with given user data */
    public void regNewUser(UserData userData) {
        if (itsOnLandingPage()) {
            regNewUserOnLandingPage(userData);
        } else {
            regNewUserOnRetailerPage(userData);
        }
    }

    /** Do new user registration with generated user data */
    public void regNewAutotestUser() {
        UserData userData = Generate.autotestUserData();
        if (itsOnLandingPage()) {
            regNewUserOnLandingPage(userData);
        } else {
            regNewUserOnRetailerPage(userData);
        }
        getBaseUrl();
        printMessage("New autotest user has been registered");
    }

    /** Do new user registration on landing page with given user data */
    public void regNewUserOnLandingPage(UserData userData){
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
        printMessage("User registration performed on landing page");
    }

    /** Do new user registration on retailer page with given user data */
    public void regNewUserOnRetailerPage(UserData userData){
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
        printMessage("User registration performed on retailer page");
    }

    /** Method returns true if user is authorised and false if he isn't */
    // TODO ускорить выполнение метода, переделав локатор с xpath на другой
    public boolean userIsAuthorised() {
        // проверяем наличие на странице кнопки "Профиль" по xpath
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"))) {
            // проверяем наличие на странице кнопки "Профиль" по тексту ссылки
            //if (isElementPresent(By.linkText("Профиль"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Do login with given user data */
    public void doLogin(UserData userData) {
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(userData);
        } else {
            doLoginOnRetailerPage(userData);
        }
    }

    /** Do login with user credentials of autotest@instamart.ru which is reserved for autotests and have admin privileges */
    public void doLoginAsAdmin() {
        final String LOGIN = "autotestuser@instamart.ru";
        final String PASSWORD = "DyDrasLipMeibe7";
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(new UserData(LOGIN, PASSWORD, null));
        } else {
            doLoginOnRetailerPage(new UserData(LOGIN, PASSWORD, null));
        }
        printMessage("Logged-in with admin privileges");
    }

    /** Do login on landing page with given user data */
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
        printMessage("Log-in on landing page");
    }

    /** Do login on retailer page page with given user data */
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
        printMessage("Log-in on retailer page");
    }

    /** Do logout */
    public void doLogout() {
        closeFlocktoryWidget();
        if (!itsInAdmin()) {
            doLogoutFromSite();
        } else {
            doLogoutFromAdmin();
        }
    }

    /** Do logout from site */
    public void doLogoutFromSite() {
        //клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        //клик по кнопке Выйти
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a"));
        //задержка чтобы пользователь нормально разлогинился
        waitForIt();
        printMessage("Logged-out from site");
    }

    /** Do logout from admin panel */
    public void doLogoutFromAdmin() {
        //клик по кнопке Выйти
        click(By.xpath("//*[@id='login-nav']/li[3]/a"));
        //задержка чтобы пользователь нормально разлогинился
        waitForIt();
        printMessage("Logged-out from admin");
    }

    /** Delete all autotest users from admin panel */
    public void deleteAllAutotestUsers() {
        //URL целевой страницы со списком автотестовых пользователей
        final String targetUrlForCleanup = baseUrl + "admin/users?q%5Bemail_cont%5D=%40example.com";
        //идем на целевую страницу
        driver.get(targetUrlForCleanup);
        //если нет админских прав, то авторизуемся под админом
        if (!itsInAdmin()) {
            driver.get(baseUrl);
            if (userIsAuthorised()) {
                doLogout();
            }
            doLoginAsAdmin();
            driver.get(targetUrlForCleanup);
        }
        //если есть пользователи на экране результатов, то удаляем верхнего пользователя в таблице
        if(isElementPresent(By.xpath("//*[@id='content']/div/table/tbody/tr"))) {
            deleteFirstUserInTable();
            printMessage("Autotest user has been deleted");
            //рекурсивно удаляем всех автотестовых юзеров пока никого не останется
            deleteAllAutotestUsers();
        } else {
            printMessage("Autotest user deletion is complete");
        }
    }

    /** Delete first user which exists in Users table in admin panel */
    private void deleteFirstUserInTable() {
        click(By.xpath("//*[@id='content']/div/table/tbody/tr/td[3]/a[2]"));
        closeAlertAndGetItsText();
        waitForIt();
    }

}
