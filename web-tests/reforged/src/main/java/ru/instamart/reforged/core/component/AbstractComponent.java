package ru.instamart.reforged.core.component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.WaitProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@ToString
@Slf4j
public abstract class AbstractComponent implements Component {

    public static final Pattern LOCATOR = Pattern.compile("/[^\\r\\n]*");

    protected WebElement component;
    protected boolean isCacheDisable = true;

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private By by;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;
    @Getter
    private final Actions actions;

    public AbstractComponent(final By by, final String description) {
        this(by, WaitProperties.BASIC_TIMEOUT, description, null);
    }

    public AbstractComponent(final By by, final long timeout, final String description) {
        this(by, timeout, description, null);
    }

    public AbstractComponent(final By by, final String description, final String errorMsg) {
        this(by, WaitProperties.BASIC_TIMEOUT, description, errorMsg);
    }

    public AbstractComponent(final By by, final long timeout, final String description, final String errorMsg) {
        this.by = by;
        this.timeout = timeout;
        this.description = isNull(description) ? this.getClass().getSimpleName() : description;
        this.errorMsg = isNull(errorMsg) ? "Элемент " + by + " не найден" : errorMsg;
        this.actions = new Actions(this);
    }

    protected abstract WebElement getComponent();

    /**
     * В обход дома делает наведение и клик по элементу через js
     */
    public void hoverAndClick() {
        log.debug("Hover and click to element {} '{}'", description, by);
        Kraken.jsAction().hoverAndClick(getLocator());
    }

    public void jsClick() {
        log.debug("JS Click on {} with locator {} '{}'", getClass().getSimpleName(), description, by);
        Kraken.jsAction().click(component);
    }

    /**
     * В обход дома через js скролит до элемента
     */
    public void scrollTo() {
        log.debug("Scroll to element {} '{}'", description, by);
        Kraken.jsAction().scrollToElement(getLocator());
    }

    protected String getLocator() {
        return getLocator(by);
    }

    protected String getLocator(final By by) {
        final Matcher matcher = LOCATOR.matcher(by.toString());
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "locator empty";
        }
    }
}
