package ru.instamart.application.platform.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.instamart.application.lib.Pages;
import ru.instamart.application.models.ElementData;
import ru.instamart.application.AppManager;
import ru.instamart.application.lib.Addresses;
import ru.instamart.application.Elements;
import ru.instamart.application.platform.modules.Shop;
import ru.instamart.application.models.EnvironmentData;
import ru.instamart.application.platform.modules.User;

public class PerformHelper extends HelperBase {

    public PerformHelper(WebDriver driver, EnvironmentData environment, AppManager app) {
        super(driver, environment, app);
    }

    /** Кликнуть элемент */
    public void click(ElementData element) {
        debugMessage("Клик по: " + element.getDescription());
        try {
            driver.findElement(element.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            if(kraken.detect().is502()) {
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

    public void fillField(By locator, String text) {
        click(locator);
        if (text != null) {
            String existingText = kraken.grab().value(locator);
            if (!text.equals(existingText)) {
                driver.findElement(locator).clear();
                driver.findElement(locator).sendKeys(text);
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
            User.ShippingAddress.set(Addresses.Moscow.defaultAddress());
        }
        Shop.Cart.collect();
        Shop.Cart.proceedToCheckout();
        kraken.checkout().complete();
    }

    /** Повторить крайний заказ с экрана истории заказов */
    public void repeatLastOrder() {
        message("Повторяем крайний заказ\n");
        kraken.get().page(Pages.UserProfile.shipments());
        if(kraken.detect().isOrdersHistoryEmpty()) {
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
        message("Повторяем заказ\n");
        click(Elements.UserProfile.OrderDetailsPage.OrderSummary.repeatOrderButton());
        kraken.await().simply(1); // Ожидание анимации открытия модалки повтора заказа
        click(Elements.UserProfile.OrderDetailsPage.RepeatOrderModal.yesButton());
        kraken.await().fluently(
                ExpectedConditions.visibilityOfElementLocated(Elements.Cart.drawer().getLocator()),
                    "Не добавились товары в корзину при повторе заказа\n");
    }

    /** Отменить крайний активный заказ */
    public void cancelLastActiveOrder() {
        message("Отменяем крайний активный заказ...");
        kraken.get().page(Pages.UserProfile.shipments());
        click(Elements.UserProfile.OrdersHistoryPage.activeOrdersFilterButton());
        if(!kraken.detect().isElementPresent(Elements.UserProfile.OrdersHistoryPage.activeOrdersPlaceholder())) {
            click(Elements.UserProfile.OrdersHistoryPage.order.snippet());
            cancelOrder();
        } else message("> Заказ не активен\n");
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
            message("✓ Заказ отменен\n");
    }
}
