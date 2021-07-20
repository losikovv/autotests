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

@ToString
@Slf4j
public abstract class Component {

    private static final Pattern LOCATOR = Pattern.compile("/[^\\r\\n]*");

    protected WebElement component;
    protected boolean isCacheDisable = true;

    @Getter
    private final By by;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;

    private final Action action;

    public Component(final By by, final long timeout, final String description, final String errorMsg) {
        this.by = by;
        this.timeout = timeout;
        this.description = description == null ? this.getClass().getSimpleName() : description;
        this.errorMsg = errorMsg == null ? "Элемент " + by + " не найден" : errorMsg;
        this.action = new Action(this);
    }

    public Component(final By by) {
        this(by, Config.BASIC_TIMEOUT, null, null);
    }

    public Component(final By by, final long timeout) {
        this(by, timeout, null, null);
    }

    public Component(final By by, final boolean isCacheDisable) {
        this(by, Config.BASIC_TIMEOUT, null, null);
        this.isCacheDisable = isCacheDisable;
    }

    public Component(final By by, final String description) {
        this(by, Config.BASIC_TIMEOUT, description, null);
    }

    public Component(final By by, final long timeout, final String description) {
        this(by, timeout, description, null);
    }

    public Component(final By by, final String description, final String errorMsg) {
        this(by, Config.BASIC_TIMEOUT, description, errorMsg);
    }

    protected abstract WebElement getComponent();

    public void mouseOver() {
        log.info("Element {} '{}' hover", description, by);
        action.mouseOver();
    }

    /**
     * В обход дома делает наведение и клик по элементу через js
     */
    public void hoverAndClick() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        while (matcher.find()) {
            log.info("Hover and click to element {} '{}'", description, by);
            Kraken.jsAction().hoverAndClick(matcher.group());
        }
    }

    public void jsClick() {
        log.info("JS Click on {} with locator {} '{}'", getClass().getSimpleName(), description, by);
        Kraken.jsAction().click(component);
    }

    /**
     * В обход дома через js скролит до элемента
     */
    public void scrollTo() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        while (matcher.find()) {
            log.info("Scroll to element {} '{}'", description, by);
            Kraken.jsAction().scrollToElement(matcher.group());
        }
    }
}
