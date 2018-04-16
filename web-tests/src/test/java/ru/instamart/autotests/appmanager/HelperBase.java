package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;



    // Helpers base class
    // Contains basic methods of interaction with the system under test



public class HelperBase {

    protected WebDriver driver;

    //TODO попробовать брать baseUrl из Application Manager
    static final String baseUrl = "https://instamart.ru/";
    private boolean acceptNextAlert = true;

    public HelperBase(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Simply get a given URL
     */
    public void getUrl(String url) {
        driver.get(url);
    }

    /**
     * Get base URL
     */
    public void getBaseUrl() {
        driver.get(baseUrl);
        printMessage("Get base URL " + baseUrl);
    }

    /**
     * Click an element on the page using a given locator
     * If the target element is obscured by something, then try to close marketing widgets and perform click again
     */
    protected void click(By locator) {
        try {
            driver.findElement(locator).click();
        } catch (ElementClickInterceptedException e) {
            closeWidgets();
            driver.findElement(locator).click();
        }
    }

    /**
     * Fill the form field with a given text, which may be null
     * Method skips filling if the field is already have the same text
     */
    protected void fillField(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = driver.findElement(locator).getAttribute("value");
            if (! text.equals(existingText)) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            }
        }
    }

    /**
     * Get the URL of the current page
     */
    public String currentURL(){
        return driver.getCurrentUrl();
    }

    /**
     * Get text of the element
     */
    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }

    /**
     * Find out if the element is shown on the page
     */
    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * Find out if the alert is shown on the page
     */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    /**
     * Switch to the alert, get and return its text and close it
     */
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

    /**
     * Switch to active element on the page
     */
    protected void swithchToActiveElement() {
        driver.switchTo().activeElement();
    }

    /**
     * Method returns true if the user is on landing page and false if he isn't
     */
    public boolean itsOnLandingPage() {
        return currentURL().equals(baseUrl);
    }

    /**
     * Method returns true if the user is on retailer page and false if he isn't
     */
    public boolean itsOnRetailerPage() {
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[2]"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method returns true if user is on site and false if he isn't
     */
    public boolean itsOnSite() {
        if (isElementPresent(By.xpath(" //*[@id='new-home-footer']"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Method returns true if user is in the admin panel and false if he isn't
     */
    public boolean itsInAdmin() {
        String XPATH = "//*[@id='login-nav']/li[2]/a";
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("Учетная запись");
        } else {
            return false;
        }
    }

    /**
     * Method returns true if it's 404 on the page and false if it's not
     */
    public boolean its404() {
        String XPATH = "/html/body/div[3]/div/div/div/div[1]/div/div[1]";
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("Страница не найдена");
        } else {
            return false;
        }
    }

    /**
     * Method returns true if it's something wrong on the page and false if it's ok
     */
    public boolean itsSomethingWrong() {
        String XPATH = "/html/body/div/h1";
        if (isElementPresent(By.xpath(XPATH))) {
            return getText(By.xpath(XPATH)).equals("We're sorry, but something went wrong.");
        } else {
            return false;
        }
    }

    /**
     * Waiting which lasts for the time specified in 'implicitlyWait' timeout
     */
    public void waitForIt() {
        isElementPresent(By.xpath("//*[@id='spree_user_999666999666999']/td[3]/a[2]"));
    }

    /**
     * Print a given message to system out
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Close marketing widgets which often obscure active elements while test execution
     */
    public void closeWidgets() {
        //TODO оптимизировать проверки чтобы сначала выполнялись проверки на самые частые виджеты
        if (isElementPresent(By.className("flocktory-widget-overlay"))){
            closeFlocktoryWidget();
        }
        // доделать методы закрытия остальных виджетов
        //else if (isElementPresent(By.className(""))){closePromoWidget();}
        //else if (isElementPresent(By.className(""))){closeEmailWidget();}
    }

    /**
     *Close Flocktory widget if it's present
     */
    public void closeFlocktoryWidget() {
            closeAlertAndGetItsText();
            //click(By.xpath("/html/body/div/div[1]")); //TODO заменить локатор, уйти от xpath
    }

    /**
     * Close promo widget if it's present after making first order
     */
    public void closePromoWidget() {
            //TODO
    }

    /**
     * Close email opt-on widget if it's present on landing
     */
    public void closeEmailWidget() {
            //TODO
    }

}
