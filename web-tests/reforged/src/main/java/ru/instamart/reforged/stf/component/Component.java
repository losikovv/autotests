package ru.instamart.reforged.stf.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import ru.instamart.kraken.setting.Config;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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

    private final Actions actions = new Actions(getWebDriver());
    private final Consumer<WebElement> hover = (wb) -> actions.moveToElement(wb).perform();

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

    public void mouseOver() {
        hover.accept(getComponent());
    }

    private WebElement isClickable() {
        return new FluentWait<>(getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.elementToBeClickable(by));
    }

    private WebElement isVisible() {
        return new FluentWait<>(getWebDriver())
                .withTimeout(timeout, TimeUnit.SECONDS)
                .pollingEvery(250, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class)
                .until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
