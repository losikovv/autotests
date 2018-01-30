package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;

// базовый класс хелперов
// тут описываем базовые методы взаимодейсвия с тестируемой системой
public class HelperBase {
    public static final String baseUrl = "https://instamart.ru/";
    private boolean acceptNextAlert = true;
    protected WebDriver driver;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
    }

    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    protected void fillField(By locator, String text) {
        // клик по полю ввода
        click(locator);
        // очистка поля ввода
        driver.findElement(locator).clear();
        // ввод текста в поле
        driver.findElement(locator).sendKeys(text);
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /*
        protected boolean isElementPresent(By locator) {
            if (driver.findElement(locator).isDisplayed()) {
                return true;
            } else {
                return false;
            }
        }
    */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    protected String closeAlertAndGetItsText() {
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

    public boolean itIsOnLandingPage() {
        if (isElementPresent(By.xpath("/html/body/div[2]/header/div[2]/ul/li[3]/a"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean itIsOnRetailerPage() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[1]/div/a/div[2]/div"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userIsAuthorised() {
        isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"));
        return true;
    }

    public boolean userIsInAdmin() {
        if (isElementPresent(By.xpath("//*[@id='login-nav']/li[3]/a"))) {
            return true;
        } else {
            return false;
        }
    }
}
