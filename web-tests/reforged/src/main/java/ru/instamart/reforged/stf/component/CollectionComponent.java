package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

@ToString(callSuper = true)
public abstract class CollectionComponent extends Component {

    protected List<WebElement> components;

    public CollectionComponent(By by, long timeout, String description, String errorMsg, Action action) {
        super(by, timeout, description, errorMsg, action);
    }

    public CollectionComponent(By by) {
        super(by);
    }

    public CollectionComponent(By by, String description) {
        super(by, description);
    }

    public CollectionComponent(By by, String description, String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        return null;
    }

    protected abstract List<WebElement> getComponents();
}
