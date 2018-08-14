package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



    // Helpers base class
    // Contains basic methods of interaction with the system under test



public class HelperBase {

    protected WebDriver driver;
    protected String environmentName;
    protected String host;
    protected String baseUrl;

    private boolean acceptNextAlert = true;

    public HelperBase(WebDriver driver, Environments environment) {
        this.driver = driver;
        this.environmentName = environment.getEnvironmentName();
        this.host = environment.getHost();
        this.baseUrl = environment.getBaseURL();
    }


    /** Перейти на указанный URL */
    public void getUrl(String url) {
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Can't get " + url + " by timeout");
        }
    }


    /** Перейти на базовый URL */
    public void getBaseUrl() {
        getUrl(baseUrl);
        printMessage("Got base URL " + baseUrl + "\n");
    }


    /** Кликнуть указанный элемент */

    protected void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n){
            printMessage("Can't find element <" + locator + "> to click on " + currentURL() + "\n");
        }
    }

    protected void click(Elements element) {
        try {
            driver.findElement(Elements.getLocator()).click();
        }
        catch (NoSuchElementException n){
            printMessage("Can't find element " + Elements.getLocator() + " on " + currentURL() + "\n");
        }
    }


    /**
     * Заполнить поле указанным текстом
     * Заполнение пропускается, если поле уже заполнено таким же текстом
     */

    protected void fillField(Elements element, String text) {
        click(element);
        if (text != null) {
            String existingText = driver.findElement(Elements.getLocator()).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(Elements.getLocator()).clear();
                driver.findElement(Elements.getLocator()).sendKeys(text);
            }
        }
    }

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


    /** Взять URL текущей страницы */
    public String currentURL() {
        return driver.getCurrentUrl();
    }


    /** Взять текст элемента */

    protected String getText(Elements element) {
        return driver.findElement(Elements.getLocator()).getText();
    }

    protected String getText(By locator) {
        return driver.findElement(locator).getText();
    }


    /** Точно определить отображается ли конкретно указанный элемент */

    public boolean isElementDetected(Elements element) {
        return isElementPresent(Elements.getLocator()) && getText(Elements.getLocator()).equals(Elements.getText());
    }

    public boolean isElementDetected(String xpath, String text) {
        return isElementPresent(By.xpath(xpath)) && getText(By.xpath(xpath)).equals(text);
    }

    public boolean isElementDetected(By locator, String text) {
        return isElementPresent(locator) && getText(locator).equals(text);
    }


    /** Определить отображается ли элемент */

    public boolean isElementPresent(Elements element) {
        try {
            driver.findElement(Elements.getLocator());
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }


    /** Определить показан ли элемент */

    public boolean isElementDisplayed(Elements element){
        return driver.findElement(Elements.getLocator()).isDisplayed();
    }

    public boolean isElementDisplayed(By locator){
        return driver.findElement(locator).isDisplayed();
    }


    /** Определить доступен ли элемент */

    public boolean isElementEnabled(Elements element){
        return driver.findElement(Elements.getLocator()).isEnabled();
    }

    public boolean isElementEnabled(By locator){
        return driver.findElement(locator).isEnabled();
    }


    /** Определить проставлен ли чекбокс */

    boolean isCheckboxSelected(Elements element) {
        return driver.findElement(Elements.getLocator()).isSelected();
    }

    boolean isCheckboxSelected(By locator) {
        return driver.findElement(locator).isSelected();
    }


    /** Определить показан ли алерт на странице */
    protected boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }


    /** Обработать алерт в зависимости от настройки acceptNextAlert */
    void handleAlert(){
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            printMessage(alertText);
        } finally {
            acceptNextAlert = true;
        }
    }


    /** Определить находимся на сайте или нет */
    public boolean isOnSite() {
        return isElementPresent(By.className("footer"));
    }


    /** Определить находимся в админке или нет */
    public boolean isInAdmin() {
        return isElementPresent(By.className("admin"));
    }


    /** Определить 404 на странице или нет */
    public boolean is404() {
        return isElementDetected(Elements.Page404.title());
    }


    /** Определить 500 на странице или нет */
    public boolean is500() {
        return isElementDetected(Elements.Page500.placeholder());
    }


    /** Waiting which lasts for the 'implicitlyWait' timeout multiplied by the given number of iterations */

    void waitForIt(int duration){
        for (int i = 1; i <= duration; i++){
            isElementPresent(By.xpath("//*[@id='nowhere']"));
        }
    }


    /** Отправить консольное сообщение */
    public void printMessage(String message) {
        System.out.println(message);
    }


    /** Переклюение между фреймами */

    void switchToActiveElement() {
        driver.switchTo().activeElement();
    }

    void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    void switchToDefaultContent() {
        driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

}
