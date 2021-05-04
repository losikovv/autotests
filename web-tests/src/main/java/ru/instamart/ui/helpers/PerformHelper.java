package ru.instamart.ui.helpers;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.core.common.AppManager;
import ru.instamart.core.helpers.HelperBase;
import ru.instamart.core.settings.Config;
import ru.instamart.ui.common.pagesdata.ElementData;

import java.util.List;

import static ru.instamart.ui.modules.Base.kraken;

@Slf4j
public final class PerformHelper extends HelperBase {

    private static String windowHandler;

    public void setWindow(){
        windowHandler = AppManager.getWebDriver().getWindowHandle();
    }

    public String getWindow(){
        return windowHandler;
    }

    /** Кликнуть элемент предпочтительно использование именно этого метода*/
    public void click(ElementData element) {
        log.info("Клик по: {}", element.getDescription());
        try {
            kraken.await().fluently(ExpectedConditions.elementToBeClickable(element.getLocator()),
                    "элемент не кликабельный: "+element.getLocator().toString(), Config.BASIC_TIMEOUT);
            AppManager.getWebDriver().findElement(element.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            if (kraken.detect().is502()) {
                throw new AssertionError(
                        "\n\nОшибка 502 CloudFlare\n> на " + kraken.grab().currentURL()
                );
            } else {
                throw new AssertionError(
                        "\n\nОтсутствует " + element.getDescription()
                                + "\n> элемент не найден на " + kraken.grab().currentURL()
                                + "\n> по " + element.getLocator().toString().substring(3)
                );
            }
        }
        catch (ElementNotVisibleException v) {
            throw new AssertionError(
                    "\n\nОтсутствует " + element.getDescription()
                            + "\n> элемент невидим на " + kraken.grab().currentURL()
                            + "\n> по " + element.getLocator().toString().substring(3)
            );
        }
        catch (ElementNotInteractableException i) {
            throw new AssertionError(
                    "\n\nОтсутствует " + element.getDescription()
                            + "\n> элемент недоступен на " + kraken.grab().currentURL()
                            + "\n> по " + element.getLocator().toString().substring(3)
            );
        }
    }

    /** Клик по элементу*/
    public void click(WebElement element){
        try {
            log.info("Клик по: {}", element.toString().replaceAll("^[^->]*",""));
            kraken.await().fluently(ExpectedConditions.elementToBeClickable(element),
                    "элемент не кликабельный", Config.BASIC_TIMEOUT);
            element.click();
        }
        catch (NoSuchElementException n) {
            throw new AssertionError("Невозможно нажать на элемент <" +
                     ">\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            throw new AssertionError("Невозможно нажать на элемент <"
                    + ">\nЭлемент невидим на " + kraken.grab().currentURL() + "\n");
        }
    }


    /** Навестисть на элемент */
    public void hoverOn(ElementData element) {
        log.info("Наводим курсор на элемент");
        try {
            new Actions(AppManager.getWebDriver()).moveToElement(AppManager.getWebDriver().findElement(element.getLocator())).perform();
//            WaitingHelper.simply(1); // Ожидание для стабильности
        }
        catch (ElementNotVisibleException v) {
            log.error("Невозможно навести на элемент <{}> \nЭлемент не отображается на {}",
                    element.getLocator(),
                    kraken.grab().currentURL());
        }
    }

    /** Заполнить поле указанным текстом */
    public void fillField(final ElementData element, final String text) {
        log.info("Заполняем поле: {}", element.getDescription());
        if (text == null) {
            log.error("> пустое значение для элемента: {}", element);
            Assert.assertNotNull("Пустое значение для заполнения поля", text);
        }
        final WebElement webElement = kraken.await().shouldBeClickable(element, Config.BASIC_TIMEOUT);
        webElement.click();
        webElement.clear();
        if(!text.equals("")){
            for (int i = 0; i < text.length(); i++) {
                webElement.sendKeys(text.charAt(i) + "");
            }
        }else {
            WaitingHelper.simply(1.5);
            if(webElement.getAttribute("value").length()>0){
                cleanField(webElement);
                //Эти костыли здесь, тк в селениум есть бага использования sendKeys и очистки поля
                if(webElement.getAttribute("value").length()>0)cleanField(webElement);
            }else webElement.sendKeys(text);
        }
    }

    public void cleanField(final WebElement webElement){
        int j=webElement.getAttribute("value").length();
        for (int i =0; i<j;i++){
            WaitingHelper.simply(0.2);
            webElement.sendKeys(Keys.BACK_SPACE);
        }
    }

    /** Заполнить поле через метод Action*/
    public void fillFieldAction(ElementData element, String text){
        Actions actions = new Actions(AppManager.getWebDriver());
        actions.moveToElement(AppManager.getWebDriver().findElement(element.getLocator())).click().perform();
        var element1 = AppManager.getWebDriver().findElement(element.getLocator());
        if(!text.equals("")){
            for (int i = 0; i < text.length();i++) {
                element1.sendKeys(text.charAt(i) + "");
            }
        }else actions.click(element1).sendKeys(text).perform();
    }

    public void fillFieldActionPhone(ElementData element, String text){
        Actions actions = new Actions(AppManager.getWebDriver());
        actions.moveToElement(AppManager.getWebDriver().findElement(element.getLocator())).click().perform();
        var element1 = AppManager.getWebDriver().findElement(element.getLocator());
        int attempts=0;
        if(!text.equals("")){
            for (int i = 0; i < text.length();) {
                WaitingHelper.simply(0.3);
                element1.sendKeys(text.charAt(i) + "");
                if(i==text.length()-1){
                    if(!element1.getAttribute("value").replaceAll("[() -]","").contains(text)){
                        cleanField(element1);
                        i=0;
                        if(attempts>=3)throw new ElementNotInteractableException("!!!!не удалось ввести значение 3 раза!!!!");
                        attempts++;
                    }else break;
                }else i++;
            }
        }else actions.click(element1).sendKeys(text).perform();
    }

    /** Установить чекбокс */
    public void setCheckbox(ElementData element, boolean value) {
        if (value) {
            if (!kraken.detect().isCheckboxSet(element))
                click(element);
        } else {
            if (kraken.detect().isCheckboxSet(element))
                click(element);
        }
    }
    /** Установить чекбокс по номеру элемента в списке*/
    public void setCheckbox(ElementData element, int counter){
        //В данном случае это скорее костыль, так как конкретный чекбокс найти очень сложно, клик по
        //тексту, чекбокс не переводит в статус true/false
        //также нельзя найти атрибут по которому будет понятно в каком статусе объект
        List<WebElement> elements = AppManager.getWebDriver().findElements(element.getLocator());
        click(elements.get(counter-1));
    }

    /** Переключиться на фреймами по имени или id */
    void switchToFrame(String nameOrId) {
        AppManager.getWebDriver().switchTo().frame(nameOrId);
    }

    /** Переключиться на активный элемент */
    public void switchToActiveElement() {
        AppManager.getWebDriver().switchTo().activeElement();
    }

    /** Переключиться на следующую вкладку */
    public void switchToNextWindow() {
        for (String winHandle : AppManager.getWebDriver().getWindowHandles()) {
            AppManager.getWebDriver().switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
        }
    }

    public void switchToWindowIndex(final int index) {
        try {
            final List<String> windowHandles = List.copyOf(AppManager.getWebDriver().getWindowHandles());
            AppManager.getWebDriver().switchTo().window(windowHandles.get(index));
        } catch (IndexOutOfBoundsException windowWithIndexNotFound) {
            log.error("FATAL: Windows with index {} doesn't exist", index);
        }
    }

    public void switchToMainWindow() {
        if (kraken.await().checkIfPopupWindowClosed()) {
            switchToWindowIndex(0);
        } else {
            log.error("Popup still alive");
        }
    }

    /** Переключиться на дефолтный контент */
    public void switchToDefaultContent() {
        //driver.switchTo().parentFrame();
        AppManager.getWebDriver().switchTo().defaultContent();
    }

    /** Обновить страницу */
    public void refresh() {
        AppManager.getWebDriver().navigate().refresh();
    }

    /** Поиск чаилда с помощью тега и текста*/
    public WebElement findChildElementByTagAndText(WebElement parent, By tag, String text){
        List<WebElement> elements = parent.findElements(tag);
        for(WebElement element:elements){
            if(element.getText().equals(text)){
                log.info("> Элемент найден по тегу <{}> и тексту: {}", tag, text);
                return element;
            }
        }
        throw new NoSuchElementException("Невозможно найти элемент по тегу <" + tag
                + "> и тексту: "+text+"\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
    }

    /**Это функция работает как костыль, пока у нас нет дата атрибутов, она берет парента и возвращает чаилда по индексу
     * нужно от нее отказаться, когла сделаем дата атрибут*/
    public WebElement findChildElementByTagAndIndex(ElementData parent,By tag, Integer index){
        List<WebElement> elements = AppManager.getWebDriver().findElement(parent.getLocator()).findElements(tag);
        try{
            return elements.get(index);
        } catch (Exception ex){
            throw new AssertionError("Невозможно найти элемент по тегу <" + tag
                    + "> и индексу: "+index+"\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
    }

    public void scrollToTheBottom(){
        log.info("> прокручиваем страницу в самый низ");
        JavascriptExecutor jse = (JavascriptExecutor) AppManager.getWebDriver();
        jse.executeScript("scroll(0, 500)");
    }

    public void scrollToTheBottom(ElementData element){
        log.info("> фокусируем страницу на элементе");
        kraken.await().fluently(ExpectedConditions.elementToBeClickable(element.getLocator()),
                "элемент не доступен: "+element.getDescription(), Config.BASIC_TIMEOUT);
        JavascriptExecutor jse = (JavascriptExecutor) AppManager.getWebDriver();
        jse.executeScript("arguments[0].scrollIntoView();", AppManager.getWebDriver().findElement(element.getLocator()));
    }
}
