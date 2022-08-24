package ru.instamart.reforged.core.component.inner;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public final class InnerCollectionComponent extends InnerComponent {

    public InnerCollectionComponent(final WebElement webElement, final By by, final String description) {
        super(webElement, by, description);
    }

    @Override
    protected WebElement getComponent() {
        return null;
    }

    public List<WebElement> getComponents() {
        return Kraken.waitAction().isElementsExist(this, getWebElement());
    }

    public int elementCount() {
        log.debug("Получить количество элементов");
        return getComponents().size();
    }

    public Set<String> getTextFromAllElements() {
        log.debug("Get text from all elements of element collection {}'s with locator {}", getClass().getSimpleName(), getBy());
        return getComponents().stream().map(WebElement::getText).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public synchronized Set<String> getTextFromAllElements(final Object... args) {
        setBy(ByKraken.xpathExpression(((ByKraken) getBy()).getDefaultXpathExpression(), args));
        log.debug("Get text from all elements of element collection {}'s with locator {}", getClass().getSimpleName(), getBy());
        return getComponents().stream().map(WebElement::getText).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
