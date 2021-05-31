package ru.instamart.reforged.stf.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.action.WaitAction;

import java.util.List;

@ToString(callSuper = true)
@Slf4j
public final class DropDown extends Component {

    public DropDown(final By by) {
        super(by);
    }

    public DropDown(final By by, final String description) {
        super(by, description);
    }

    public DropDown(final By by, final String description, final String errorMsg) {
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

    public void selectFirst() {
        final WebElement webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
        log.info("Select first {} with locator {}", getClass().getSimpleName(), getBy());
    }

    public void selectAny() {
        final WebElement webElement = getComponents().stream().findAny().orElseThrow();
        webElement.click();
        log.info("Select any {} with locator {}", getClass().getSimpleName(), getBy());
    }

    public void withText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().equals(text)) {
                wb.click();
                log.info("Select {} with locator {} and text {}", getClass().getSimpleName(), getBy(), text);
                return;
            }
        }
    }

    public void containsText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().contains(text)) {
                wb.click();
                log.info("Select {} with locator {} and contain text {}", getClass().getSimpleName(), getBy(), text);
            }
        }
    }

    private List<WebElement> getComponents() {
        log.info("Get {}'s with locator {}", getClass().getSimpleName(), getBy());
        return WaitAction.isElementsExist(this);
    }
}
