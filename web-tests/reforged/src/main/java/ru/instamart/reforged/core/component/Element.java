package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class Element extends AbstractComponent {

    public Element(final By by, final String description) {
        super(by, description);
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

    public WebElement getElement() {
        return getComponent();
    }

    public void click(final Object... args) {
        setBy(ByKraken.xpathExpression(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        click();
    }

    public void click() {
        log.debug("Click {} with locator {}", getDescription(), getBy());
        getComponent().click();
    }

    public void getText(final Object... args) {
        setBy(ByKraken.xpathExpression(getLocator(), args));
        getText();
    }

    public int getNumericValue() {
        log.debug("Получить численное значение");
        return StringUtil.extractNumberFromString(getText());
    }

    public String getText() {
        final var text = getComponent().getText();
        log.debug("Get text '{}' for {} with locator {}", text, getDescription(), getBy());
        return text;
    }

    public String getAttribute(final String attr) {
        final var attributeValue = getComponent().getAttribute(attr);
        log.debug("Get text '{}' for {} with locator {}", attributeValue, getDescription(), getBy());
        return attributeValue;
    }

    public String getTitleOrText(final Object... args) {
        setBy(ByKraken.xpathExpression(((ByKraken)getBy()).getDefaultXpathExpression(), args));
        final var component = getComponent();
        final var title = component.getAttribute("title");
        if (isNull(title) || title.isEmpty()) {
            log.debug("Title is empty or null for '{}' get text", getDescription());
            return component.getText();
        }
        log.debug("Get title '{}' for {} with locator {}", title, getDescription(), getBy());
        return title;
    }
}
