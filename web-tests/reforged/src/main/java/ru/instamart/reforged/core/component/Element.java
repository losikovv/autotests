package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.config.BrowserProperties;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Element extends Component {

    public Element(final By by) {
        super(by);
    }

    public Element(final By by, final String description) {
        super(by, description);
    }

    public Element(final By by, final long timeout) {
        super(by, timeout);
    }

    public Element(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public Element(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public Element(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.debug("Create {} with locator {}", getDescription(), getBy());
        if (isNull(component) || isCacheDisable) {
            component = Kraken.waitAction().shouldBeVisible(this);
        }
        return component;
    }

    public void click(final Object... args) {
        setBy(ByKraken.xpath(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        click();
    }

    public void click() {
        if (BrowserProperties.USE_JS_CLICK) {
            jsClick();
        } else {
            log.debug("Click {} with locator {}", getDescription(), getBy());
            getComponent().click();
        }
    }

    public void getText(final Object... args) {
        setBy(ByKraken.xpath(getLocator(), args));
        getText();
    }

    public String getText() {
        final String text = getComponent().getText();
        log.debug("Get text '{}' for {} with locator {}", text, getDescription(), getBy());
        return text;
    }

    public String getAttribute(final String attr) {
        final String attributeValue = getComponent().getAttribute(attr);
        log.debug("Get text '{}' for {} with locator {}", attributeValue, getDescription(), getBy());
        return attributeValue;
    }

    /**
     * ожидание загрузки элемента
     */
    public void waitImgLoad(final Object... args) {
        setBy(ByKraken.xpath(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        waitImgLoad();
    }

    /**
     * ожидание загрузки элемента
     */
    public void waitImgLoad() {
        log.debug("Get img '{}' with locator {}", getDescription(), getBy());
        Kraken.jsAction().waitImgLoad(getLocator());
    }
}
