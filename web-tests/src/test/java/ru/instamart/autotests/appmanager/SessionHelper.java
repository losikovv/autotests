package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver driver) {
        super(driver);
    }



    /**  Session helper for handling user registration, login and logout
     *   User autotest@instamart.ru is reserved for autotests */



    public void generateUserData(){
        // TODO имена юзеров генерить со стандартным префиксом + рандомной строкой. Пример - autotestX14Y07LM@example.com
        // TODO пароль использовать везде одинаковый
    }

    public void regUser(UserData userData) {
        if (itsOnLandingPage()) {
            regUserOnLandingPage(userData);
        } else {
            regUserOnRetailerPage(userData);
        }
    }

    public void regUserOnLandingPage(UserData userData){
        // клик по кнопке Вход
        click(By.xpath("/html/body/div[4]/header/div[2]/ul/li[3]"));
        // TODO
    }

    public void regUserOnRetailerPage(UserData userData){
        // клик по кнопке Вход
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button"));
        // TODO
    }

    public void doLogin(UserData userData) {
        if (itsOnLandingPage()) {
            doLoginOnLandingPage(userData);
        } else {
            doLoginOnRetailerPage(userData);
        }
    }

    public void doLoginOnLandingPage(UserData userData) {
        // клик по кнопке Вход
        click(By.xpath("/html/body/div[4]/header/div[2]/ul/li[3]"));
        // вводим логин
        fillField(By.id("login_form__email"), userData.getLogin());
        // вводим пароль
        fillField(By.id("login_form__password"), userData.getPassword());
        // клик по кнопке Войти
        click(By.xpath("(//input[@name='commit'])[2]"));
    }

    public void doLoginOnRetailerPage(UserData userData) {
        // клик по кнопке Вход
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button"));
        // вводим логин
        fillField(By.xpath("//*[@id='login_form__email']"), userData.getLogin());
        // вводим пароль
        fillField(By.xpath("//*[@id='login_form__password']"), userData.getPassword());
        // клик по кнопке Войти
        click(By.xpath("//*[@id='login_form']/ul[1]/li[4]/input[2]"));
    }

    public void doLogout() {
        if (!itsInAdmin()) {
            doLogoutFromSite();
        } else {
            doLogoutFromAdmin();
        }
    }

    public void doLogoutFromSite() {
        // клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        // клик по кнопке Выйти
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a"));
    }

    public void doLogoutFromAdmin() {
        // клик по кнопке Выйти
        click(By.xpath("//*[@id=/login-nav/]/li[3]/a"));
    }

}
