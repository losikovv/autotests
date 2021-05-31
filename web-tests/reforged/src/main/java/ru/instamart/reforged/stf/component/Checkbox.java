package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.action.WaitAction;

@ToString(callSuper = true)
public final class Checkbox extends Component {

    public Checkbox(final By by) {
        super(by);
    }

    public Checkbox(final By by, final String description) {
        super(by, description);
    }

    public Checkbox(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        if (component == null) {
            component = WaitAction.shouldBeClickable(this);
        }
        return component;
    }

    public void check() {
        getComponent().click();
    }

    public void uncheck() {
        getComponent().click();
    }
}
