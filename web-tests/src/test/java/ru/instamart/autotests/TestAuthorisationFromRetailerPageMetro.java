package ru.instamart.autotests;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestAuthorisationFromRetailerPageMetro {
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

        driver.get("https://instamart.ru/metro");

        // клик по кнопке Вход
        driver.findElement(By.cssSelector("button.rc-login-btn")).click();

        // вводим логин
        driver.findElement(By.id("login_form__email")).sendKeys("instatestuser@yandex.ru");

        // вводим пароль
        driver.findElement(By.id("login_form__password")).sendKeys("instamart");

        // клик по кнопке Войти
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();

        // проверяем что на странице есть кнопка Профиль
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).isDisplayed());

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
