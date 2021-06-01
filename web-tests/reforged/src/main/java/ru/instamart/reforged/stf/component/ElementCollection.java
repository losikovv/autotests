package ru.instamart.reforged.stf.component;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.action.WaitAction;

import java.util.List;

import static java.util.Objects.isNull;

@ToString(callSuper = true)
@Slf4j
public final class ElementCollection extends CollectionComponent {

    public ElementCollection(final By by) {
        super(by);
    }

    public ElementCollection(final By by, final String description) {
        super(by, description);
    }

    public ElementCollection(final By by, final String description, final String errorMsg) {
        super(by, description, errorMsg);
    }

    public void clickOnElementWithText(final String text) {
        for (final WebElement we : getComponents()) {
            if (we.getText().contains(text)) {
                we.click();
            }
        }
    }

    @Override
    protected List<WebElement> getComponents() {
        log.info("Get {}'s with locator {}", getClass().getSimpleName(), getBy());
        if (isNull(components)) {
            components = WaitAction.isOneOrMoreElementsExist(this);
        }
        return components;
    }
}
