package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;

// хелпер авторизации-деавторизации + проверки на логин и админ-доступ
public class AuthorisationHelper extends HelperBase {

    public AuthorisationHelper(WebDriver driver) {
        super(driver);
    }

    public void doLoginOnLandingPage(UserData userData) {
        // клик по кнопке Вход
        click(By.xpath("/html/body/div[2]/header/div[2]/ul/li[3]/a"));
        // вводим логин
        fillField(By.id("login_form__email"), userData.getLogin());
        // вводим пароль
        fillField(By.id("login_form__password"),userData.getPassword());
        // клик по кнопке Войти
        click(By.xpath("(//input[@name='commit'])[2]"));
    }

    public void doLoginOnRetailerPage(UserData userData) {
        // клик по кнопке Вход
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button"));
        // вводим логин
        fillField(By.xpath("//*[@id='login_form__email']"),userData.getLogin());
        // вводим пароль
        fillField(By.xpath("//*[@id='login_form__password']"),userData.getPassword());
        // клик по кнопке Войти
        click(By.xpath("//*[@id='login_form']/ul[1]/li[4]/input[2]"));
    }

    public void doLogout() {
        // клик по кнопке Профиль
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        // клик по кнопке Выйти
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a"));
    }

    public void doLogoutFromAdmin() {
        // клик по кнопке Выйти
        click(By.xpath("//*[@id=/login-nav/]/li[3]/a"));
    }

    public boolean isAuthorised() {
        isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        return true;
    }

    public boolean isAdmin() {
        isElementPresent(By.xpath("//*[@id='login-nav']/li[3]/a"));
        return true;
    }

}
