package ru.instamart.reforged.core.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@ToString(callSuper = true)
public abstract class CollectionComponent extends Component {

    protected List<WebElement> components;

    public CollectionComponent(final By by) {
        super(by);
    }

    public CollectionComponent(final By by, final boolean isCashDisable) {
        super(by, isCashDisable);
    }

    public CollectionComponent(final By by, final String description) {
        super(by, description);
    }

    public CollectionComponent(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        return null;
    }

    protected abstract List<WebElement> getComponents();
}
