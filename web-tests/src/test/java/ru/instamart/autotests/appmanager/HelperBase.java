package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;



// Базовый класс хелперов
// Содержит базовые методы взаимодейсвия с тестируемой системой



public class HelperBase {

    protected WebDriver driver;
    public static final String baseUrl = "https://instamart.ru/";
    private boolean acceptNextAlert = true;

    public boolean userIsAuthorised;
    public boolean userIsOnSite;
    public boolean userIsInAdmin;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
    }

    /** Click an element on the page */
    protected void click(By locator) {
        driver.findElement(locator).click();
    }

    /** Fill the form field with a given text */
    protected void fillField(By locator, String text) {
        click(locator);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    /** Get the URL of the current page */
    public String currentURL(){
        return driver.getCurrentUrl();
    }

    /** Get the text of an element */
    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    /** Find out if the element is shown on the page */
    protected boolean isElementPresent(By locator) {
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

    /** Find out if the user is athorised or not */
    // TODO перенести в authorisation helper
    public boolean userIsAuthorised() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[5]/div/div[1]"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Find out if the user is on site or not */
    public boolean itsOnSite() {
        if (isElementPresent(By.xpath(" //*[@id='new-home-footer']"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Find out if the user is on landing page or not */
    // TODO перенести в navigation helper
    public boolean itsOnLandingPage() {
       return currentURL().equals(baseUrl);
       //"https://instamart.ru/"
       // String XPATH = "/html/body/a[1]/div";
       // if (isElementPresent(By.xpath(XPATH))) {
       //     return getText(By.xpath(XPATH)).equals("Попробуйте сегодня и получите бесплатную доставку первого заказа!") ;
       // } else {
        //    return false;
       // }
    }

    /** Find out if the user is on retailer page or not */
    // TODO перенести в navigation helper
    public boolean itsOnRetailerPage() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[2]"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Find out if the user is in administration panel or not */
    public boolean itsInAdmin() {
        if (isElementPresent(By.xpath("//*[@id='login-nav']/li[3]/a"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Find out if its 404 on the page */
    public boolean its404() {
        String XPATH = "/html/body/div[3]/div/div/div/div[1]/div/div[1]";
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("Страница не найдена");
        } else {
            return false;
        }
    }

    /** Find out if its something wrong on the page */
    public boolean itsSomethingWrong() {
        String XPATH = "/html/body/div/h1";
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("There is something wrong");
        } else {
            return false;
        }
    }

}
