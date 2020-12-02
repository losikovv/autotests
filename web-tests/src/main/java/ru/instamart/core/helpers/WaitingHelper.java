package instamart.core.helpers;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import instamart.core.common.AppManager;
import instamart.core.settings.Config;
import instamart.ui.objectsmap.Elements;
import instamart.ui.common.pagesdata.EnvironmentData;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitingHelper extends HelperBase {

    public WaitingHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Просто задержка на указанное время */
    public static void simply(double seconds) {
        verboseMessage("Задержка на " + seconds + " сек.");
        try {
            Thread.sleep((long) (seconds * 1000));
        } catch (InterruptedException i) {
            verboseMessage("Прервано");
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

    public void fluentlyWithWindowsHandler(Function conditions){
        for (String winHandle : driver.getWindowHandles()) {
            try{
                driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
                fluently(conditions,"");
            }catch (Exception ex){
                verboseMessage("окно закрыто продолжаем поиск");
                continue;
            }

        }
    }

    public void fluently(Function conditions, String message){
        new FluentWait<>(driver)
                .withTimeout(Config.CoreSettings.waitingTimeout, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public void fluently(Function conditions, String message, int time){
        new FluentWait<>(driver)
                .withTimeout(time, TimeUnit.SECONDS)
                .withMessage(message)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class)
                .until(conditions);
    }

    public boolean fluentlyPossibleAppearance(Function conditions, String message, int time){
        try{
            new FluentWait<>(driver)
                    .withTimeout(time, TimeUnit.SECONDS)
                    .withMessage(message)
                    .pollingEvery(1, TimeUnit.SECONDS)
                    .ignoring(NoSuchElementException.class)
                    .until(conditions);
            return true;
        }catch (Exception ex){
            verboseMessage("> "+message+" в течении: "+ time);
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
                        e.printStackTrace();
                    }
                }
            }
        }
    }


}
