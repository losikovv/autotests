package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.action.WaitAction;

@ToString(callSuper = true)
public final class Element extends Component {

    public Element(final By by) {
        super(by);
    }

    @Override
    protected WebElement getComponent() {
        if (component == null) {
            component = WaitAction.shouldBeVisible(this);
        }
        return component;
    }

    public void click() {
        getComponent().click();
    }

    public void getText() {
        getComponent().getText();
    }
}
