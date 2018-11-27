package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;



    // Helpers base class
    // Contains basic methods of interaction with the system under test



public class HelperBase {
    WebDriver driver;
    String environmentName;
    String host;
    public String baseUrl;
    public String fullBaseUrl;
    private boolean acceptNextAlert = true;

    HelperBase(WebDriver driver, Environments environment) {
        this.driver = driver;
        this.environmentName = environment.getEnvironmentName();
        this.host = environment.getHost();
        this.baseUrl = environment.getBaseURL(false);
        this.fullBaseUrl = environment.getBaseURL(true);
    }


    // TODO перенести в GrabHelper

    /** Вернуть текущий URL */

    public String fetchCurrentURL() {
        return driver.getCurrentUrl();
    }


    /** Взять текст элемента */

    String fetchText(Elements element) {
        try {
            return driver.findElement(Elements.getLocator()).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    String fetchText(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }


    /** Точно определить отображается ли конкретно указанный элемент */
    public boolean isElementDetected(Elements element) {
        return isElementPresent(Elements.getLocator()) && fetchText(Elements.getLocator()).equals(Elements.getText());
    }

    public boolean isElementDetected(String xpath, String text) {
        return isElementPresent(By.xpath(xpath)) && fetchText(By.xpath(xpath)).equals(text);
    }

    public boolean isElementDetected(By locator, String text) {
        return isElementPresent(locator) && fetchText(locator).equals(text);
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
        try {
            return driver.findElement(Elements.getLocator()).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isElementDisplayed(By locator){
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
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
            //printMessage("> handling alert [" + alertText + "]");
        } finally {
            acceptNextAlert = true;
        }
    }

    /** Waiting which lasts for the 'implicitlyWait' timeout multiplied by the given number of iterations */
    public void waitingFor(int duration){
        for (int i = 1; i <= duration; i++){
            isElementPresent(By.xpath("//*[@id='nowhere']"));
        }
    }
    
    /** Отправить сообщение в консоль*/
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


    /** Cookies */

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }


    /** Скачивание документов к заказу */

    public String detectOrderDocument(int position) {
        Elements.Site.OrderDetailsPage.documentation(position);
        if (fetchText(Elements.getLocator()) != null) {
            printMessage("Скачиваем: " + fetchText(Elements.getLocator()));
            return fetchText(Elements.getLocator());
            } else {
            printMessage("Документ отсутствует\n");
            return null;
        }
    }


    /** Округлить цену до целого числа, отбросив копейки, пробелы и знак рубля */
    protected int round(String price) {
        return Integer.parseInt(((price).substring(0,(price.length() - 5))).replaceAll("\\s",""));
    }
}
