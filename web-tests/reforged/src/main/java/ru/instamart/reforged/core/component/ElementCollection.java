package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class ElementCollection extends CollectionComponent {

    public ElementCollection(final By by, final String description) {
        super(by, description);
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

    @Override
    protected List<WebElement> getComponents() {
        log.debug("Get {}'s with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(components) || isCacheDisable) {
            components = shouldBe().elementsExists();
        }
        return components;
    }

    public void clickOnFirst() {
        log.debug("Click first {} with locator {}", getDescription(), getBy());
        final var webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
    }

    public void clickOnFirst(final String args) {
        setBy(ByKraken.xpathExpression(((ByKraken)getBy()).getDefaultXpathExpression(), args));
        log.debug("Click first {} with locator {}", getDescription(), getBy());
        final var webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
    }

    public void scrollToLast() {
        log.debug("Scroll to last {} with locator {}", getDescription(), getBy());
        final WebElement webElement = getComponents().stream().reduce((first, second) -> second).orElseThrow();
        Kraken.action().moveToElement(webElement).perform();
    }

    public void clickOnLast() {
        log.debug("Click last {} with locator {}", getDescription(), getBy());
        final var webElement = getComponents().stream().reduce((first, second) -> second).orElseThrow();
        webElement.click();
    }

    public void clickOnAll() {
        for (final var we : getComponents()) {
            log.debug("Click on element {} with", we);
            we.click();
        }
    }

    public void clickOnElementWithText(final String text) {
        for (final var we : getComponents()) {
            final var elementText = we.getText();
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
        for (final var we : getComponents()) {
            final var elementText = we.getText();
            if (elementText.contains(text)) {
                log.debug("Hover on element {} with text {}", we, elementText);
                Kraken.action().moveToElement(we).perform();
                break;
            }
        }
    }

    public Boolean isElementWithTextPresent(final String text) {
        for (final var we : getComponents()) {
            final var elementText = we.getText();
            if (elementText.contains(text)) {
                log.debug("Element {} contains text {}", we, elementText);
                return true;
            }
        }
        return false;
    }

    public List<WebElement> getElements() {
        return getComponents();
    }

    public Set<String> getTextFromAllElements() {
        log.debug("Get text from all elements of element collection {}'s with locator {}", getClass().getSimpleName(), getBy());
        return getElements().stream().map(WebElement::getText).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public synchronized Set<String> getTextFromAllElements(final Object... args) {
        setBy(ByKraken.xpathExpression(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        log.debug("Get text from all elements of element collection {}'s with locator {}", getClass().getSimpleName(), getBy());
        return getElements().stream().map(WebElement::getText).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<String> getAttributeValues(final String attr) {
        final var attributeValue = getComponent().getAttribute(attr);
        log.debug("Get text '{}' for {} with locator {}", attributeValue, getDescription(), getBy());
        return getElements().stream().map(we -> we.getAttribute(attr)).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public int elementCount() {
        return getComponents().size();
    }
}
