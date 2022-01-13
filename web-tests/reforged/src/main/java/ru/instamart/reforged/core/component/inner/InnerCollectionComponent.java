package ru.instamart.reforged.core.component.inner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;

import java.util.List;

public class InnerCollectionComponent extends InnerComponent {

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
        return getComponents().size();
    }
}
