package ru.instamart.reforged.core.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@ToString(callSuper = true)
public abstract class CollectionComponent extends AbstractComponent {

    protected List<WebElement> components;

    public CollectionComponent(final By by, final String description) {
        super(by, description);
    }

    public CollectionComponent(final By by, final long timeout, final String description) {
        super(by, timeout, description);
    }

    public CollectionComponent(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public CollectionComponent(final By by, final long timeout, final String description, final String errorMsg) {
        super(by, timeout, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        return null;
    }

    protected abstract List<WebElement> getComponents();
}
