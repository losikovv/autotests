package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.action.WaitAction;

@ToString(callSuper = true)
public final class Autocomplete extends Component {

    public Autocomplete(final By by) {
        super(by);
    }

    public Autocomplete(final By by, final String description) {
        super(by, description);
    }

    public Autocomplete(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        if (component == null) {
            component = WaitAction.shouldBeClickable(this);
        }
        return component;
    }

    public void fill(final String data) {
        getComponent().sendKeys(data);
    }
}
