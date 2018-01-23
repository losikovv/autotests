package ru.instamart.autotests;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.fail;

public class TestBase {
    protected WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
        driver = new FirefoxDriver();
        baseUrl = "https://instamart.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        // идем на лендинг
        driver.get("https://instamart.ru");
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

    protected void doLoginOnLanding(UserData userData) {
        // клик по кнопке Вход
        driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/ul/li[3]/a")).click();
        // вводим логин
        driver.findElement(By.id("login_form__email")).sendKeys(userData.getLogin());
        // вводим пароль
        driver.findElement(By.id("login_form__password")).sendKeys(userData.getPassword());
        // клик по кнопке Войти
        driver.findElement(By.xpath("(//input[@name='commit'])[2]")).click();
    }

    protected void doLoginOnRetailerPage(UserData userData) {
        // клик по кнопке Вход
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/button")).click();
        // вводим логин
        driver.findElement(By.xpath("//*[@id='login_form__email']")).sendKeys(userData.getLogin());
        // вводим пароль
        driver.findElement(By.xpath("//*[@id='login_form__password']")).sendKeys(userData.getPassword());
        // клик по кнопке Войти
        driver.findElement(By.xpath("//*[@id='login_form']/ul[1]/li[4]/input[2]")).click();
    }

    protected void doLogout() {
        // клик по кнопке Профиль
        clickOnProfileButton();
        // проверяем что в менюшке Профиль есть имя юзера
        assertUsernameShown();
        // клик по кнопке Выйти
        clickOnLogoutButton();
    }



    protected void clickOnLogoutButton() {
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[8]/a")).click();
    }

    protected void clickOnProfileButton() {
        driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).click();
    }

    protected void goToRetailerPage() {
            driver.get("https://instamart.ru/metro");
    }

    protected void assertAuthorised() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]")).isDisplayed());
    }

    protected void assertUsernameShown() {
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[2]/div/div[1]")).isDisplayed());
    }
}
