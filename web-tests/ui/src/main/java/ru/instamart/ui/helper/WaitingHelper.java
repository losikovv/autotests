package ru.instamart.ui.helper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.instamart.kraken.testdata.lib.Addresses;
import ru.instamart.ui.manager.AppManager;
import ru.instamart.kraken.setting.Config;
import ru.instamart.ui.data.ElementData;
import ru.instamart.ui.Elements;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public final class WaitingHelper extends HelperBase {

    private final AppManager kraken;

    /** Ожидание, равное переданному значению умноженному на переменную 'implicitlyWait' в конфиге */
    public void implicitly(int duration){
        for (int i = 1; i <= duration; i++){
            kraken.detect().isElementPresent(Elements.none());
        }
    }

    /** Умное ожидание указанных условий
     * Ожидание прекратится как только условия будут выполнены, иначе ошибка по таймауту
     * */
    public void fluently(Function conditions) {
        fluently(conditions,"");
    }

    /**
     * Метод заточен под ожидание элементов в которые можно кликнуть
     * @param data
     * @return
     */
    public WebElement shouldBeClickable(final ElementData data) {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(data.getTimeout(), TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(data.getLocator()));
    }

    public boolean shouldBeVisible(final ElementData data) {
        final FluentWait<WebDriver> wait =  new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(data.getTimeout(), TimeUnit.SECONDS)
                .withMessage(data.getDescription())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        return wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(data.getLocator()).isDisplayed());
    }

    public boolean shouldNotBeVisible(final ElementData data) {
        final FluentWait<WebDriver> wait =  new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(data.getTimeout(), TimeUnit.SECONDS)
                .withMessage(data.getDescription())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(data.getLocator()));
    }

    public void urlToBe(final String url, final int timeout) {
        new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.urlToBe(url));
    }

    public WebElement getValue(final ElementData data) {
        return createWait(data)
                .until((ExpectedCondition<WebElement>) driver -> {
                    final WebElement webElement = driver.findElement(data.getLocator());
                    if (webElement.getAttribute("value").length() != 0) {
                        return webElement;
                    }
                    throw new NoSuchElementException("Element without text");
                });
    }

    public WebElement shouldBeText(final ElementData data) {
        return createWait(data)
                .until((ExpectedCondition<WebElement>) driver -> {
                    final WebElement webElement = driver.findElement(data.getLocator());
                    if (webElement.getText().length() != 0) {
                        return webElement;
                    }
                    throw new NoSuchElementException("Element without text");
                });
    }

    public List<WebElement> isElementsExist(final ElementData data) {
        return createWait(data)
                .until((ExpectedCondition<List<WebElement>>) driver -> {
                    final List<WebElement> webElements = driver.findElements(data.getLocator());
                    if (webElements.size() > 0) {
                        return webElements;
                    }
                    throw new NoSuchElementException("Elements not found or size < 1");
                });
    }

    public void fillField(final WebElement element, final String text) {
        createWait().until(keysSendCondition(element, text));
    }

    public static ExpectedCondition<Boolean> keysSendCondition(final WebElement element, final String text) {
        return driver -> {
            if (element.isDisplayed()) {
                if (element.getAttribute("value").length() != 0) {
                    element.click();
                    element.sendKeys(Keys.COMMAND + "a");
                    element.sendKeys(Keys.CONTROL + "a");
                    element.sendKeys(Keys.DELETE);
                }
                element.sendKeys(text);
                return element.getAttribute("value").equals(text);
            }
            return false;
        };
    }

    private FluentWait<WebDriver> createWait(final ElementData data) {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(data.getTimeout(), TimeUnit.SECONDS)
                .withMessage(data.getDescription())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
    }

    private FluentWait<WebDriver> createWait() {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(10, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .ignoring(NotFoundException.class);
    }

    public void fluentlyWithWindowsHandler(Function conditions){
        for (String winHandle : AppManager.getWebDriver().getWindowHandles()) {
            try {
                AppManager.getWebDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
                fluently(conditions,"");
            } catch (Exception ex){
                log.error("окно закрыто продолжаем поиск");
                continue;
            }

        }
    }

    public void fluently(Function conditions, String message){
        new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(Config.WAITING_TIMEOUT, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public void fluently(Function conditions, String message, int time){
        new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(time, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public void fluentlyMS(Function conditions, String message, int time){
        new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(time, TimeUnit.MILLISECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public boolean fluentlyPossibleAppearance(Function conditions, String message, int time){
        try{
            new FluentWait<>(AppManager.getWebDriver())
                    .withTimeout(time, TimeUnit.SECONDS)
                    .withMessage(message)
                    .pollingEvery(1, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class)
                    .until(conditions);
            return true;
        }catch (Exception ex){
            log.error("> {} в течении: {}", message, time);
            return false;
        }
    }

    public boolean checkIfPopupWindowClosed() {
        try {
            return (new WebDriverWait(AppManager.getWebDriver(), 15))
                    .until((ExpectedCondition<Boolean>) d -> d.getWindowHandles().size() == 1);
        } catch (TimeoutException e) {
            return false;
        }
    }
}
