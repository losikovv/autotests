package ru.instamart.reforged.core.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.instamart.reforged.core.action.WaitAction;

@ToString(callSuper = true)
@Slf4j
public final class Selector extends Component {

    private Select select;

    public Selector(final By by) {
        super(by);
    }

    public Selector(final By by, final String description) {
        super(by, description);
    }

    public Selector(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    @Override
    protected WebElement getComponent() {
        log.info("Create {} with locator {}", getClass().getSimpleName(), getBy());
        if (component == null) {
            component = WaitAction.shouldBeClickable(this);
        }
        return component;
    }

    public void selectByText(final String text) {
        getSelect().selectByVisibleText(text);
        log.info("Select {} with locator {} and text {}", getClass().getSimpleName(), getBy(), text);
    }

    public void selectByIndex(final int index) {
        getSelect().selectByIndex(index);
        log.info("Select {} with locator {} and index {}", getClass().getSimpleName(), getBy(), index);
    }

    private Select getSelect() {
        log.info("Create Select with locator {}", getBy());
        if (select == null) {
            select = new Select(getComponent());
        }
        return select;
    }
}
