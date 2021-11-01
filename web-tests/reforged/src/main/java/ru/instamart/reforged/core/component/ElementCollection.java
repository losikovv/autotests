package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import java.util.List;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class ElementCollection extends CollectionComponent {

    public ElementCollection(final By by) {
        super(by);
    }

    public ElementCollection(final By by, final String description) {
        super(by, description);
    }

    public ElementCollection(final By by, final long timeout) {
        super(by, timeout);
    }

    public ElementCollection(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public ElementCollection(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public ElementCollection(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    public void clickOnFirst() {
        log.debug("Click first {} with locator {}", getDescription(), getBy());
        final WebElement webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
    }

    public void clickOnAll() {
        for (final WebElement we : getComponents()) {
            log.debug("Click on element {} with", we);
            we.click();
        }
    }

    public void clickOnElementWithText(final String text) {
        for (final WebElement we : getComponents()) {
            final String elementText = we.getText();
            if (elementText.contains(text)) {
                log.debug("Click on element {} with text {}", we, elementText);
                we.click();
                break;
            }
        }
    }

    public String getElementText(final int order) {
        var elements = getElements();
        if (elements.size() > order) {
            return getElements().get(order).getText();
        } else {
            log.error("Try to get text from {} element", order);
            return "element_not_found";
        }
    }

    public void moveOnElementWithText(final String text) {
        for (final WebElement we : getComponents()) {
            final String elementText = we.getText();
            if (elementText.contains(text)) {
                log.debug("Hover on element {} with text {}", we, elementText);
                Kraken.action().moveToElement(we).perform();
                break;
            }
        }
    }

    public List<WebElement> getElements() {
        return getComponents();
    }

    public int elementCount() {
        return getComponents().size();
    }

    @Override
    protected List<WebElement> getComponents() {
        log.debug("Get {}'s with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(components) || isCacheDisable) {
            components = Kraken.waitAction().isElementsExist(this);
        }
        return components;
    }
}
