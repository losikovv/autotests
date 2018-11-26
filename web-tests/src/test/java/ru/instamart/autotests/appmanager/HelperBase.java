package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



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

    /** Перейти на базовый URL */

    public void getBaseUrl() {
        getUrl(fullBaseUrl);
    }


    /** Перейти на указанный URL */

    public void getUrl(String url) {
        if (url.equals(fullBaseUrl)) printMessage("Getting baseURL " + url + "\n");
        try {
            driver.get(url);
        } catch (TimeoutException t) {
            printMessage("Can't get " + url + " by timeout");
        }
    }


    /** Кликнуть указанный элемент */

    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n){
            printMessage("Can't click element <" + locator + "> on " + currentURL() + "\n");
        }
    }

    public void click(Elements element) {
        try {
            driver.findElement(Elements.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            if(Elements.getText() == null) {
                printMessage("Can't click element <" + Elements.getLocator()
                        + ">\nNo such element on " + currentURL() + "\n");
            } else {
                printMessage("Can't click element " + Elements.getText() + " <" + Elements.getLocator()
                        + ">\nNo such element on " + currentURL() + "\n");
            }
        }
        catch (ElementNotVisibleException v) {
            if(Elements.getText() == null) {
                printMessage("Can't click element <" + Elements.getLocator()
                        + ">\nElement is not visible on " + currentURL() + "\n");
            } else {
                printMessage("Can't click element " + Elements.getText() + " <" + Elements.getLocator()
                        + ">\nElement is not visible on " + currentURL() + "\n");
            }
        }
    }


    /**
     * Заполнить поле указанным текстом
     * Заполнение пропускается, если поле уже заполнено таким же текстом
     */

    public void fillField(Elements element, String text) {
        click(element);
        if (text != null) {
            String existingText = driver.findElement(Elements.getLocator()).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(Elements.getLocator()).clear();
                driver.findElement(Elements.getLocator()).sendKeys(text);
            }
        }
    }

    public void fillField(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = driver.findElement(locator).getAttribute("value");
            if (!text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
            }
        }
    }


    /** Вернуть текущий URL */

    public String currentURL() {
        return driver.getCurrentUrl();
    }


    /** Взять текст элемента */

    String getText(Elements element) {
        try {
            return driver.findElement(Elements.getLocator()).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    String getText(By locator) {
        try {
            return driver.findElement(locator).getText();
        } catch (NoSuchElementException e) {
            return null;
        }
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

    public void waitFor(int duration){
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


    /** Cookies */

    public void deleteAllCookies() {
        driver.manage().deleteAllCookies();
    }


    /** Скачивание документов к заказу */

    public String detectOrderDocument(int position) {
        Elements.Site.OrderDetailsPage.documentation(position);
        if (getText(Elements.getLocator()) != null) {
            printMessage("Скачиваем: " + getText(Elements.getLocator()));
            return getText(Elements.getLocator());
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
