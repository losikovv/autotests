package instamart.core.helpers;

import instamart.core.common.AppManager;
import instamart.ui.common.lib.Addresses;
import instamart.ui.common.lib.Pages;
import instamart.ui.common.pagesdata.ElementData;
import instamart.ui.modules.Shop;
import instamart.ui.modules.User;
import instamart.ui.objectsmap.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PerformHelper extends HelperBase {

    private static final Logger log = LoggerFactory.getLogger(PerformHelper.class);

    public PerformHelper(WebDriver driver, AppManager app) {
        super(driver, app);
    }

    /** Кликнуть элемент предпочтительно использование именно этого метода*/
    public void click(ElementData element) {
        log.info("Клик по: {}", element.getDescription());
        try {
            driver.findElement(element.getLocator()).click();
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
        try {
            new Actions(driver).moveToElement(driver.findElement(element.getLocator())).perform();
            kraken.await().simply(1); // Ожидание для стабильности
        }
        catch (ElementNotVisibleException v) {
            log.error("Невозможно навестись на элемент <{}> \nЭлемент не отображается на {}",
                    element.getLocator(),
                    kraken.grab().currentURL());
        }
    }

    /** Заполнить поле указанным текстом */
    public void fillField(ElementData element, String text) {
        click(element);
        if (text != null) {
            String existingText = kraken.grab().value(element);
            if (!text.equals(existingText)) {
                driver.findElement(element.getLocator()).clear();
                driver.findElement(element.getLocator()).sendKeys(text);
            }
        }
    }

    /** Заполнить поле через метод Action*/
    public void fillFieldAction(ElementData element, String text){
        click(element);
        var element1 = driver.findElement(element.getLocator());
        Actions actions = new Actions(driver);
        actions.click(element1).sendKeys(text).perform();
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
        List<WebElement> elements = driver.findElements(element.getLocator());
        click(elements.get(counter-1));
    }

    /** Переключиться на фреймами по имени или id */
    void switchToFrame(String nameOrId) {
        driver.switchTo().frame(nameOrId);
    }

    /** Переключиться на активный элемент */
    public void switchToActiveElement() {
        driver.switchTo().activeElement();
    }

    /** Переключиться на следующую вкладку */
    public void switchToNextWindow() {
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle); // switch focus of WebDriver to the next found window handle
        }
    }

    /** Переключиться на дефолтный контент */
    public void switchToDefaultContent() {
        //driver.switchTo().parentFrame();
        driver.switchTo().defaultContent();
    }

    /** Обновить страницу */
    public void refresh() {
        driver.navigate().refresh();
        kraken.await().implicitly(1); // Ожидание обновления страницы
    }


    // ======= Работа с заказами =======

    /** Оформить тестовый заказ */
    public void order() {
        if (!kraken.detect().isShippingAddressSet()) {
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress(),true);
        }
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();
    }

    /** Повторить крайний заказ с экрана истории заказов */
    public void repeatLastOrder() {
        log.info("Повторяем крайний заказ");
        kraken.get().page(Pages.UserProfile.shipments());
        if (kraken.detect().isOrdersHistoryEmpty()) {
            throw new AssertionError("Невозможно повторить заказ, у пользователя нет заказов в истории\n");
        } else {
            click(Elements.UserProfile.OrdersHistoryPage.order.repeatButton());
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.Cart.drawer().getLocator()),
                        "Не добавились товары в корзину при повторе заказа\n");
        }
    }

    /** Повторить заказ с экрана деталей заказа */
    public void repeatOrder() {
        log.info("Повторяем заказ");
        click(Elements.UserProfile.OrderDetailsPage.OrderSummary.repeatOrderButton());
        kraken.await().simply(1); // Ожидание анимации открытия модалки повтора заказа
        click(Elements.UserProfile.OrderDetailsPage.RepeatOrderModal.yesButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Cart.drawer().getLocator()),
                    "Не добавились товары в корзину при повторе заказа\n");
    }

    /** Отменить крайний активный заказ */
    public void cancelLastActiveOrder() {
        log.info("Отменяем крайний активный заказ...");
        kraken.get().page(Pages.UserProfile.shipments());
        click(Elements.UserProfile.OrdersHistoryPage.activeOrdersFilterButton());
        if(!kraken.detect().isElementPresent(Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder())) {
            click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
            cancelOrder();
        } else log.warn("> Заказ не активен");
    }

    /** Отменить заказ на странице деталей заказа */
    public void cancelOrder() {
            click(Elements.UserProfile.OrderDetailsPage.OrderSummary.cancelOrderButton());
            kraken.await().simply(1); // Ожидание анимации открытия модалки отмены заказа
            click(Elements.UserProfile.OrderDetailsPage.CancelOrderModal.yesButton());
            kraken.await().fluently(
                    ExpectedConditions.presenceOfElementLocated(Elements.UserProfile.OrderDetailsPage.CancelOrderModal.popup().getLocator()),
                    "Не отменился заказ за допустимое время ожидания\n"
            );
            log.info("✓ Заказ отменен");
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
        List<WebElement> elements = driver.findElement(parent.getLocator()).findElements(tag);
        try{
            return elements.get(index);
        } catch (Exception ex){
            throw new AssertionError("Невозможно найти элемент по тегу <" + tag
                    + "> и индексу: "+index+"\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
    }
}
