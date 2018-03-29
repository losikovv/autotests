package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;

import static ru.instamart.autotests.testdata.TestDataGenerator.generateUserData;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver) {
        super(driver);
    }



    /*

    Session helper for handling user registration, login and logout

    */



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
        if (itsOnLandingPage()) {
            regNewUserOnLandingPage(generateUserData());
        } else {
            regNewUserOnRetailerPage(generateUserData());
        }
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
    public void doLoginWithAdminUser() {
        final String LOGIN = "autotestuser@instamart.ru";
        final String PASSWORD = "DyDrasLipMeibe7";
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(new UserData(LOGIN, PASSWORD, null));
        } else {
            doLoginOnRetailerPage(new UserData(LOGIN, PASSWORD, null));
        }
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
    }

    /** Do logout */
    public void doLogout() {
        if (!itsInAdmin()) {
            doLogoutFromSite();
        } else {
            doLogoutFromAdmin();
        }
    }

    /** Do logout from site */
    public void doLogoutFromSite() {
        // клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        // клик по кнопке Выйти
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a"));
    }

    /** Do logout from admin panel */
    public void doLogoutFromAdmin() {
        // клик по кнопке Выйти
        click(By.xpath("//*[@id=/login-nav/]/li[3]/a"));
    }

}
