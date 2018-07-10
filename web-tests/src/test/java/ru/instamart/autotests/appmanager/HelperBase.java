package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.models.EnvironmentData;



    // Helpers base class
    // Contains basic methods of interaction with the system under test



public class HelperBase {

    protected WebDriver driver;
    protected String environmentName;
    protected String host;
    protected String baseUrl;

    private boolean acceptNextAlert = true;

    public HelperBase(WebDriver driver, EnvironmentData environment) {
        this.driver = driver;
        this.environmentName = environment.getEnvironmentName();
        this.host = environment.getHost();
        this.baseUrl = environment.getBaseURL();
    }

    /**
     * Simply get a given URL
     */
    public void getUrl(String url) {
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Can't get " + url + " by timeout");
        }
    }

    /**
     * Get base URL
     */
    public void getBaseUrl() {
        getUrl(baseUrl);
        printMessage("Got base URL " + baseUrl + "\n");
    }

    /**
     * Click an element on the page using a given locator
     * If the target element is obscured by something, then try to close marketing widgets and perform click again
     */
    protected void click(By locator) {
        try {
            driver.findElement(locator).click();
        } catch (ElementClickInterceptedException e) {
            waitForIt(1);
            if(isElementPresent(By.className("flocktory-widget-overlay"))){
                printMessage("Flocktory widget detected");
                closeFlocktoryWidgets();
            }
            driver.findElement(locator).click();
        } catch (NoSuchElementException n){
            printMessage("Can't find element <" + locator + "> to click on " + currentURL() + "\n");
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
            if (!text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }

    /**
     * Get the URL of the current page
     */
    public String currentURL() {
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
     * Find out if the element by the given locator is displayed
     */
    public boolean isElementDisplayed(By locator){
        return driver.findElement(locator).isDisplayed();
    }

    /**
     * Find out if the element by the given locator is enabled
     */
    public boolean isElementEnabled(By locator){
        return driver.findElement(locator).isEnabled();
    }

    /**
     * Find out if the checkbox by the given locator is selected
     */
    boolean isCheckboxSelected(By locator) {
        return driver.findElement(locator).isSelected();
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
    protected String closeAlertAndGetItsText(){
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
     * Waiting which lasts for the 'implicitlyWait' timeout multiplied by the given number of iterations
     */
    void waitForIt(int duration){
        for (int i = 1; i <= duration; i++){
            isElementPresent(By.xpath("//*[@id='nowhere']"));
        }
    }

    /**
     * Print a given message to system out
     */
    public void printMessage(String message) {
        System.out.println(message);
    }

    /**
     * Close marketing widgets which often obscure active elements while test execution and switch back to main frame
     */

    private void closeFlocktoryWidgets() {

        String pushTipWidgetId = "fl-75517";
        String cartRibbonWidgetId = "fl-73939";

        // Push Tip widget
        if (isElementPresent(By.id(pushTipWidgetId))){
            printMessage("Closing Push Tip widget\n");
            driver.switchTo().frame(pushTipWidgetId);
            driver.findElement(By.xpath("/html/body/div/div[1]")).click();
        }

        /*
        // Shopping Cart Ribbon widget
        if(isElementPresent(By.id(cartRibbonWidgetId))){
            printMessage("Closing Ribbon widget\n");
            driver.switchTo().frame(cartRibbonWidgetId);
            driver.findElement(By.xpath("/html/body/div/div[2]")).click();
        }
        */

        /*
        //TODO
        if(isElementPresent(By.id("856bfda0-423f-11e8-89bb-c5fffb7fc056"))){
            printMessage("Closing Wrapper widget\n");
            driver.switchTo().frame(driver.findElement(By.id("856bfda0-423f-11e8-89bb-c5fffb7fc056")));
            click(By.xpath("/html/body/div/div[1]/div/div[1]"));
        }

        //TODO
        if (isElementPresent(By.className("flockapi-overlay"))){
            printMessage("Closing First Purchase widget\n");
            driver.switchTo().frame(driver.findElement(By.className("flockapi-overlay")));
            driver.findElement(By.id("close")).click();
        }
        */

        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();

    }

}
