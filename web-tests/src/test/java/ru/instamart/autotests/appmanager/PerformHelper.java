package ru.instamart.autotests.appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.autotests.application.libs.Addresses;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.appmanager.platform.Shop;
import ru.instamart.autotests.appmanager.models.ElementData;
import ru.instamart.autotests.appmanager.models.EnvironmentData;


public class PerformHelper extends HelperBase {

    PerformHelper(WebDriver driver, EnvironmentData environment, ApplicationManager app) {
        super(driver, environment, app);
    }

    /** Кликнуть элемент */
    public void click(ElementData element) {
        debugMessage("Клик по: " + element.getDescription());
        try {
            driver.findElement(element.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            message("Отсутствует " + element.getDescription()
                    + "\nЭлемент по " + element.getLocator().toString().substring(3)
                    + " \n не найден на " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            message("Отсутствует " + element.getDescription()
                    + "\nЭлемент по " + element.getLocator().toString().substring(3)
                    + " \nневидим на " + kraken.grab().currentURL() + "\n");
        }
    }

    // TODO убрать в тестах и хелперах все методы использующие прямой локатор
    /** Кликнуть элемент по локатору */
    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n) {
            message("Невозможно нажать на элемент <" + locator
                    + ">\nЭлемент не найден на " + kraken.grab().currentURL() + "\n");
        }
        catch (ElementNotVisibleException v) {
            message("Невозможно нажать на элемент <" + locator
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
            message("Невозможно навестись на элемент <" + element.getLocator()
                    + ">\nЭлемент не отображается на " + kraken.grab().currentURL() + "\n");
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

    /** Установить чекбокс */
    public void setCheckbox(ElementData element, boolean value) {
        if(value) {
            if(!kraken.detect().isCheckboxSet(element))
                click(element);
        } else {
            if(kraken.detect().isCheckboxSet(element))
                click(element);
        }
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
    void switchToDefaultContent() {
        driver.switchTo().parentFrame();
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
            Shop.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();
    }

    /** Повторить крайний заказ */
    public void repeatLastOrder() {
        message("Повторяем крайний заказ\n");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().isOrdersHistoryEmpty()) {
            throw new AssertionError("Невозможно повторить заказ, у пользователя нет заказов в истории\n");
        } else {
            click(Elements.UserProfile.OrdersHistoryPage.order.repeatButton());
            kraken.await().implicitly(1); // Ожидание добавления в корзину товаров из предыдущего заказа
            // TODO протестить ожидание
            kraken.await().fluently(
                    ExpectedConditions.visibilityOfElementLocated(Elements.Header.cartCounter().getLocator()),
                        "Не добавились товары в корзину при повторе заказа\n"
            );
        }
    }

    /** Отменить крайний заказ */
    public void cancelLastOrder() {
        message("Отменяем крайний заказ...");
        kraken.get().url(baseUrl + "user/orders");
        if(kraken.detect().isLastOrderActive()) {
            click(Elements.UserProfile.OrdersHistoryPage.order.cancelButton());
            message("✓ Заказ отменен\n");
        } else message("> Заказ не активен\n");
        kraken.await().implicitly(2); // Ожидание отмены заказа
    }
}
