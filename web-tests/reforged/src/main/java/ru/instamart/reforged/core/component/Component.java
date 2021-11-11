package ru.instamart.reforged.core.component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.WaitProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@Slf4j
public abstract class Component {

    public static final Pattern LOCATOR = Pattern.compile("/[^\\r\\n]*");

    protected WebElement component;
    protected boolean isCacheDisable = true;

    @Setter(AccessLevel.PROTECTED)
    private By by;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;

    private final Actions actions;

    public Component(final By by, final long timeout, final String description, final String errorMsg) {
        this.by = by;
        this.timeout = timeout;
        this.description = description == null ? this.getClass().getSimpleName() : description;
        this.errorMsg = errorMsg == null ? "Элемент " + by + " не найден" : errorMsg;
        this.actions = new Actions(this);
    }

    public Component(final By by) {
        this(by, WaitProperties.BASIC_TIMEOUT, null, null);
    }

    public Component(final By by, final long timeout) {
        this(by, timeout, null, null);
    }

    public Component(final By by, final boolean isCacheDisable) {
        this(by, WaitProperties.BASIC_TIMEOUT, null, null);
        this.isCacheDisable = isCacheDisable;
    }

    public Component(final By by, final String description) {
        this(by, WaitProperties.BASIC_TIMEOUT, description, null);
    }

    public Component(final By by, final long timeout, final String description) {
        this(by, timeout, description, null);
    }

    public Component(final By by, final String description, final String errorMsg) {
        this(by, WaitProperties.BASIC_TIMEOUT, description, errorMsg);
    }

    protected abstract WebElement getComponent();

    public By getBy() {
        return by;
    }

    public By getBy(final Object... args) {
        if (by instanceof ByKraken) {
            final ByKraken byKraken = (ByKraken) by;
            return ByKraken.xpath(byKraken.getDefaultXpathExpression(), args);
        }
        return by;
    }

    public void mouseOver() {
        log.debug("Element {} '{}' hover", description, by);
        actions.mouseOver();
    }

    public void sendKey(final Keys keys) {
        actions.sendKeys(keys);
    }

    /**
     * В обход дома делает наведение и клик по элементу через js
     */
    public void hoverAndClick() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        while (matcher.find()) {
            log.debug("Hover and click to element {} '{}'", description, by);
            Kraken.jsAction().hoverAndClick(matcher.group());
        }
    }

    public void jsClick() {
        log.debug("JS Click on {} with locator {} '{}'", getClass().getSimpleName(), description, by);
        Kraken.jsAction().click(component);
    }

    /**
     * В обход дома через js скролит до элемента
     */
    public void scrollTo() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        while (matcher.find()) {
            log.debug("Scroll to element {} '{}'", description, by);
            Kraken.jsAction().scrollToElement(matcher.group());
        }
    }

    protected String getLocator() {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "locator empty";
        }
    }
}
