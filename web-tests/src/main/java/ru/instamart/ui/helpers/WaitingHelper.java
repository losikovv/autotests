package ru.instamart.ui.helpers;

import ru.instamart.core.common.AppManager;
import ru.instamart.core.helpers.HelperBase;
import ru.instamart.core.settings.Config;
import ru.instamart.ui.common.pagesdata.ElementData;
import ru.instamart.ui.objectsmap.Elements;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public final class WaitingHelper extends HelperBase {

    private static final Logger log = LoggerFactory.getLogger(WaitingHelper.class);

    public WaitingHelper(final AppManager kraken) {
        super(kraken);
    }

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
     * TODO: Все элементы отображаются за разное время и ждать константные {@link Config#WAITING_TIMEOUT} очень не хорошо
     * TODO: предлагаю засунуть это в {@link ElementData} так получится, что для каждого элемента, свое ожидание будет.
     * @param data
     * @param timeout
     * @return
     */
    public WebElement shouldBeClickable(final ElementData data, final int timeout) {
        return new FluentWait<>(kraken.getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(data.getLocator()));
    }

    public boolean shouldBeVisible(final ElementData data, final int timeout) {
        final FluentWait<WebDriver> wait =  new FluentWait<>(kraken.getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .withMessage(data.getDescription())
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        return wait.until((ExpectedCondition<Boolean>) driver -> driver.findElement(data.getLocator()).isDisplayed());
    }

    public void urlToBe(final String url, final int timeout) {
        new FluentWait<>(kraken.getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.urlToBe(url));
    }

    public void fluentlyWithWindowsHandler(Function conditions){
        for (String winHandle : kraken.getWebDriver().getWindowHandles()) {
            try {
                kraken.getWebDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
                fluently(conditions,"");
            } catch (Exception ex){
                log.error("окно закрыто продолжаем поиск");
                continue;
            }

        }
    }

    public void fluently(Function conditions, String message){
        new FluentWait<>(kraken.getWebDriver())
                .withTimeout(Config.WAITING_TIMEOUT, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public void fluently(Function conditions, String message, int time){
        new FluentWait<>(kraken.getWebDriver())
                .withTimeout(time, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public void fluentlyMS(Function conditions, String message, int time){
        new FluentWait<>(kraken.getWebDriver())
                .withTimeout(time, TimeUnit.MILLISECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public boolean fluentlyPossibleAppearance(Function conditions, String message, int time){
        try{
            new FluentWait<>(kraken.getWebDriver())
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

    public void byElementIsPresentByText(List<WebElement> elements, String text, int timer){
        for (WebElement element:elements){
            if(element.getText().equals(text)){
                for (int i = 0; i<timer; i++){
                    if (element.isDisplayed())return;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        log.error("Can't find element");
                    }
                }
            }
        }
    }


}
