package ru.instamart.autotests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class TestAuthorisationFromLandingPage {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://instamart.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

    }

    @Test
    public void testUntitledTestCase() throws Exception {
        //логинимся на лендинге
        doLoginOnLanding("instatestuser@yandex.ru", "instamart");
        // проверяем что на странице есть кнопка Профиль
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).isDisplayed());
        //разлогиниваемся
        doLogout();
    }

    private void doLoginOnLanding(String login, String password) {
        // идем на лендинг
        driver.get("https://instamart.ru");
        // клик по кнопке Вход
        driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/ul/li[3]/a")).click();
        // вводим логин
        driver.findElement(By.id("login_form__email")).sendKeys(login);
        // вводим пароль
        driver.findElement(By.id("login_form__password")).sendKeys(password);
        // клик по кнопке Войти
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
    }

    private void doLogout() {
        // клик по кнопке Профиль
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).click();
        // проверяем что в менюшке Профиль есть имя юзера
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[1]")).isDisplayed());
        // проверяем что в менюшке Профиль есть пункт Профиль
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[3]/a")).isDisplayed());
        // клик по кнопке Выйти
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a/div[2]")).click();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
