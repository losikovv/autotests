package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.autotests.models.EnvironmentData;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static ru.instamart.autotests.application.Config.CoreSettings.waitingTimeout;

public class WaitingHelper extends HelperBase {

    WaitingHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Просто задержка на указанное время */
    public void simply(int seconds) {
        debugMessage("Задержка на " + seconds + " сек.");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException i) {
            debugMessage("Прервано");
        }
    }

    /** Ожидание, равное переданному значению умноженному на переменную 'implicitlyWait' в конфиге */
    public void implicitly(int duration){
        for (int i = 1; i <= duration; i++){
            kraken.detect().isElementPresent(By.xpath("//*[@id='nowhere']"));
        }
    }

    /** Умное ожидание указанных условий
     * Ожидание прекратится как только условия будут выполнены, иначе ошибка по таймауту
     * */
    public void fluently(Function conditions) {
        fluently(conditions,"");
    }

    public void fluently(Function conditions, String message){
        new FluentWait<>(driver)
                .withTimeout(waitingTimeout, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }
}
