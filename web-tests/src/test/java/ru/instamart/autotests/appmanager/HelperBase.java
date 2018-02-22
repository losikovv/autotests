package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;

// базовый класс хелперов
// тут описываем базовые методы взаимодейсвия с тестируемой системой
public class HelperBase {
    public static final String baseUrl = "https://instamart.ru/";
    private boolean acceptNextAlert = true;
    protected WebDriver driver;

    public boolean userIsAuthorised;
    public boolean userIsOnSite;
    public boolean userIsInAdmin;

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
    
    public String currentURL(){
        return driver.getCurrentUrl();
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    protected boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

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

    public boolean userIsAuthorised() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean itsOnSite() {
        if (isElementPresent(By.xpath(" //*[@id='new-home-footer']"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean itsOnLandingPage() {
        if (getText(By.xpath("/html/body/a[1]/div")).equals("Попробуйте сегодня и получите бесплатную доставку первого заказа!")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean itsOnRetailerPage() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[2]/div[3]/div[1]"))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean itsInAdmin() {
        if (isElementPresent(By.xpath("//*[@id='login-nav']/li[3]/a"))) {
            return true;
        } else return false;
    }

    public boolean its404() {
        String XPATH = "/html/body/div[3]/div/div/div/div[1]/div/div[1]";
        if (isElementPresent(By.xpath(XPATH)))
            return getText(By.xpath(XPATH)).equals("Страница не найдена");
        else return false;
    }

    public boolean itsSomethingWrong() {
        String XPATH = "/html/body/div/h1";
        if (isElementPresent(By.xpath(XPATH)))
            return getText(By.xpath(XPATH)).equals("There is something wrong");
        else return false;
    }

}
