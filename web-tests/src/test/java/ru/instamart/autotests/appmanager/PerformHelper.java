package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;

public class PerformHelper extends HelperBase {

    private ApplicationManager kraken;

    PerformHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    /** Кликнуть элемент по локатору */
    public void click(By locator) {
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException n){
            printMessage("Can't click element <" + locator + "> on " + fetchCurrentURL() + "\n");
        }
    }

    /** Кликнуть элемент */
    public void click(Elements element) {
        try {
            driver.findElement(Elements.getLocator()).click();
        }
        catch (NoSuchElementException n) {
            if(Elements.getText() == null) {
                printMessage("Can't click element <" + Elements.getLocator()
                        + ">\nNo such element on " + fetchCurrentURL() + "\n");
            } else {
                printMessage("Can't click element " + Elements.getText() + " <" + Elements.getLocator()
                        + ">\nNo such element on " + fetchCurrentURL() + "\n");
            }
        }
        catch (ElementNotVisibleException v) {
            if(Elements.getText() == null) {
                printMessage("Can't click element <" + Elements.getLocator()
                        + ">\nElement is not visible on " + fetchCurrentURL() + "\n");
            } else {
                printMessage("Can't click element " + Elements.getText() + " <" + Elements.getLocator()
                        + ">\nElement is not visible on " + fetchCurrentURL() + "\n");
            }
        }
    }

    /** Заполнить поле указанным текстом */
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

    /** Заполнить поле по локатору указанным текстом */
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

    /** Открыть меню аккаунта */
    public void openAccountMenu() {
        if(!kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already opened");
    }

    /** Закрыть меню аккаунта */
    public void closeAccountMenu() {
        if(kraken.detect().isAccountMenuOpen()) {
            click(Elements.Site.Header.profileButton());
        } else printMessage("Account menu is already closed");
    }
}
