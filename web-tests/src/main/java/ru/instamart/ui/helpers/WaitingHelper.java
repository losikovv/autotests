package ru.instamart.ui.helpers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.instamart.core.common.AppManager;
import ru.instamart.core.helpers.HelperBase;
import ru.instamart.core.settings.Config;
import ru.instamart.ui.common.pagesdata.ElementData;
import ru.instamart.ui.objectsmap.Elements;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@RequiredArgsConstructor
@Slf4j
public final class WaitingHelper extends HelperBase {

    private final AppManager kraken;

    /** Просто задержка на указанное время */
    public static void simply(double seconds) {
        log.info("Задержка на {} сек.", seconds);
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException i) {
            log.error("Прервано");
        }
    }

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

    public void getText(final ElementData data) {
        new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(data.getTimeout(), TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until((ExpectedCondition<Boolean>) input -> input.findElement(data.getLocator()).getText().length() != 0);
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
