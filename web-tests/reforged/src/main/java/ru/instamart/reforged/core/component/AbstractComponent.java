package ru.instamart.reforged.core.component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.condition.Is;
import ru.instamart.reforged.core.component.condition.Should;
import ru.instamart.reforged.core.component.condition.ShouldBe;
import ru.instamart.reforged.core.config.WaitProperties;
import ru.instamart.reforged.core.service.ashot.AshotService;
import ru.yandex.qatools.ashot.Screenshot;

import java.util.regex.Pattern;

import static java.util.Objects.isNull;

@ToString
@Slf4j
public abstract class AbstractComponent implements Component {

    public static final Pattern LOCATOR = Pattern.compile("[(|/][^\\r\\n]*");

    protected WebElement component;

    @Getter
    @Setter
    private By by;
    @Getter
    private final long timeout;
    @Getter
    private final String description;
    @Getter
    private final String errorMsg;
    @Getter
    private final Actions actions;

    @Accessors(fluent = true)
    @Getter
    private final Is is;
    @Accessors(fluent = true)
    @Getter
    private final Should should;
    @Accessors(fluent = true)
    @Getter
    private final ShouldBe shouldBe;

    public AbstractComponent(final By by, final String description) {
        this(by, WaitProperties.WAITING_TIMEOUT, description);
    }

    public AbstractComponent(final By by, final long timeout, final String description) {
        this.by = by;
        this.timeout = timeout;
        this.description = isNull(description) ? this.getClass().getSimpleName() : description;
        this.errorMsg = String.format("Для элемента '%s', '%s' не выполнено условие: ", description, by);
        this.actions = new Actions(this);
        this.is = new Is(this);
        this.should = new Should(this);
        this.shouldBe = new ShouldBe(this);
    }

    public abstract WebElement getComponent();

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

    /**
     * В обход дома через js скролит до элемента
     */
    public void scrollTo(final Object... args) {
        log.debug("Scroll to element {} '{}'", description, by);
        setBy(ByKraken.xpathExpression(((ByKraken)getBy()).getDefaultXpathExpression(), args));
        Kraken.jsAction().scrollToElement(getLocator());
    }

    public Screenshot screenWebElement(final Component... components) {
        return AshotService.screenWebElement(getComponent(), components);
    }

    protected String getLocator() {
        return getLocator(by);
    }

    protected String getLocator(final By by) {
        final var matcher = LOCATOR.matcher(by.toString());
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "locator empty";
        }
    }
}
