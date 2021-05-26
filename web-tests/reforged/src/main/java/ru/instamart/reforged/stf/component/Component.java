package ru.instamart.reforged.stf.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.kraken.setting.Config;
import ru.instamart.ui.manager.AppManager;

import java.util.concurrent.TimeUnit;

import static ru.instamart.ui.manager.AppManager.getWebDriver;

@RequiredArgsConstructor
@ToString
public abstract class Component {

    protected WebElement component;

    protected final By by;
    protected final long timeout;
    @Getter
    protected final String description;
    @Getter
    protected final String errorMsg;

    public Component(final By by) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = "Вызвали " + this.getClass().getSimpleName();
        this.errorMsg = "Элемент " + by.toString() + " не найден";
    }

    protected WebElement getComponent() {
        if (component == null) {
            if (this instanceof Element) {
                component = isVisible();
            } else {
                component = isClickable();
            }
        }
        return component;
    }

    private WebElement isClickable() {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    protected WebElement isVisible() {
        return new FluentWait<>(AppManager.getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
