package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import ru.instamart.autotests.models.UserData;

public class AuthorisationHelper {
    private WebDriver driver;

    public AuthorisationHelper(WebDriver driver) {
        this.driver = driver;
    }

    public void doLoginOnLandingPage(UserData userData) {
        // клик по кнопке Вход
        driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/ul/li[3]/a")).click();
        // вводим логин
        driver.findElement(By.id("login_form__email")).sendKeys(userData.getLogin());
        // вводим пароль
        driver.findElement(By.id("login_form__password")).sendKeys(userData.getPassword());
        // клик по кнопке Войти
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
    }

    public void doLoginOnRetailerPage(UserData userData) {
        // клик по кнопке Вход
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button")).click();
        // вводим логин
        driver.findElement(By.xpath("//*[@id='login_form__email']")).sendKeys(userData.getLogin());
        // вводим пароль
        driver.findElement(By.xpath("//*[@id='login_form__password']")).sendKeys(userData.getPassword());
        // клик по кнопке Войти
        driver.findElement(By.xpath("//*[@id='login_form']/ul[1]/li[4]/input[2]")).click();
    }

    public void doLogout() {
        // клик по кнопке Профиль
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).click();
        // клик по кнопке Выйти
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a")).click();
    }


    public void assertAuthorised() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).isDisplayed());
    }

    public void assertUsernameShown() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[1]")).isDisplayed());
    }

}
