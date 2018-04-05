package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;

import java.util.concurrent.TimeUnit;


// Базовый класс хелперов
// Содержит базовые методы взаимодейсвия с тестируемой системой



public class HelperBase {

    protected WebDriver driver;
    static final String baseUrl = "https://instamart.ru/";
    private boolean acceptNextAlert = true;

    public boolean userIsOnSite;
    public boolean userIsInAdmin;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
    }

    /** Simply get a given URL */
    public void getUrl(String url) {
        driver.get(url);
    }

    /** Click an element on the page using a given locator */
    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    /** Fill the form field with a given text, which may be null
     * Method skips filling if the field is already have same text */
    protected void fillField(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText =   driver.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            }
        }
    }

    /** Get the URL of the current page */
    public String currentURL(){
        return driver.getCurrentUrl();
    }

    /** Get text of the element */
    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    /** Find out if the element is shown on the page */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /** Find out if the alert is shown on the page */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /** Switch to the alert, get and return its text and close it */
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

    /** Switch to active element on the page */
    protected void swithchToActiveElement() {
        driver.switchTo().activeElement();
    }

    /** Method returns true if the user is on landing page and false if he isn't */
    public boolean itsOnLandingPage() {
        return currentURL().equals(baseUrl);
    }

    /** Method returns true if the user is on retailer page and false if he isn't */
    public boolean itsOnRetailerPage() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[2]"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Method returns true if user is on site and false if he isn't */
    public boolean itsOnSite() {
        if (isElementPresent(By.xpath(" //*[@id='new-home-footer']"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Method returns true if user is in the admin panel and false if he isn't */
    public boolean itsInAdmin() {
        String XPATH = "//*[@id='login-nav']/li[2]/a";
        //driver.manage().timeouts().waitForIt(5, TimeUnit.SECONDS);
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("Учетная запись");
        } else {
            return false;
        }
    }

    /** Method returns true if it's 404 on the page and false if it's not */
    public boolean its404() {
        String XPATH = "/html/body/div[3]/div/div/div/div[1]/div/div[1]";
        //уменьшаем задержку для ускорения проверок
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("Страница не найдена");
        } else {
            return false;
        }
    }

    /** Method returns true if it's something wrong on the page and false if it's ok */
    public boolean itsSomethingWrong() {
        String XPATH = "/html/body/div/h1";
        //уменьшаем задержку для ускорения проверок
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("There is something wrong");
        } else {
            return false;
        }
    }

    /** Artificial waiting for nonexistent element which lasts for the time specified in 'waitForIt' driver timeout */
    public void waitForIt() {
        isElementPresent(By.xpath("//*[@id='spree_user_999666999666999']/td[3]/a[2]"));
    }

    /** Print given message to system out */
    public void printMessage(String message) {
        System.out.println(message);
    }
}
