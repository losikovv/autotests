package ru.instamart.reforged.core.component.inner;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import java.util.List;

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
}
