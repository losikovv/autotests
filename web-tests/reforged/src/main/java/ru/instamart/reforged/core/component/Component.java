package ru.instamart.reforged.core.component;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.setting.Config;
import ru.instamart.reforged.core.Kraken;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@ToString
@Slf4j
public abstract class Component {

    private static final Pattern LOCATOR = Pattern.compile("/[^\\r\\n]*");

    protected WebElement component;
    protected boolean isCashDisable = false;

    @Getter
    private final By by;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;

    private final Action action;

    public Component(final By by) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = "Вызвали " + this.getClass().getSimpleName() + " " + by.toString();
        this.errorMsg = "Элемент " + by + " не найден";
        this.action = new Action(this);
    }

    public Component(final By by, final boolean isCashDisable) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = "Вызвали " + this.getClass().getSimpleName() + " " + by.toString();
        this.errorMsg = "Элемент " + by + " не найден";
        this.action = new Action(this);
        this.isCashDisable = isCashDisable;
    }

    public Component(final By by, final String description) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = description;
        this.errorMsg = "Элемент " + by.toString() + " не найден";
        this.action = new Action(this);
    }

    public Component(final By by, final String description, final String errorMsg) {
        this.by = by;
        this.timeout = Config.BASIC_TIMEOUT;
        this.description = description;
        this.errorMsg = errorMsg;
        this.action = new Action(this);
    }

    protected abstract WebElement getComponent();

    public void mouseOver() {
        log.info("Element {} hover", by);
        action.mouseOver();
    }

    /**
     * В обход дома делает наведение и клик по элементу через js
     */
    public void hoverAndClick() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        while (matcher.find()) {
            log.info("Hover and click to element {}", by);
            Kraken.jsAction().hoverAndClick(matcher.group());
        }
    }

    public void jsClick() {
        log.info("JS Click on {} with locator {}", getClass().getSimpleName(), getBy());
        Kraken.jsAction().click(component);
    }

    /**
     * В обход дома через js скролит до элемента
     */
    public void scrollTo() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        while (matcher.find()) {
            log.info("Scroll to element {}", by);
            Kraken.jsAction().scrollToElement(matcher.group());
        }
    }
}
