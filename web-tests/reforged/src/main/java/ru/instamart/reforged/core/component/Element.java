package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.action.WaitAction;

@ToString(callSuper = true)
@Slf4j
public final class Element extends Component {

    public Element(final By by) {
        super(by);
    }

    public Element(final By by, final String description) {
        super(by, description);
    }

    public Element(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.info("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (component == null) {
            component = WaitAction.shouldBeVisible(this);
        }
        return component;
    }

    public void click() {
        getComponent().click();
        log.info("Click {} with locator {}", getClass().getSimpleName(), getBy());
    }

    public void getText() {
        getComponent().getText();
        log.info("Get text {} with locator {}", getClass().getSimpleName(), getBy());
    }
}
