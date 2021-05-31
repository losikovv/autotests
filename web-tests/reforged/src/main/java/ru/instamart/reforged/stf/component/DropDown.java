package ru.instamart.reforged.stf.component;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.action.WaitAction;

import java.util.List;

public final class DropDown extends Component {

    public DropDown(By by) {
        super(by);
    }

    @Override
    protected WebElement getComponent() {
        if (component == null) {
            component = WaitAction.shouldBeClickable(this);
        }
        return component;
    }

    public void selectFirst() {
        final WebElement webElement = getComponents().stream().findFirst().orElseThrow();
        webElement.click();
    }

    public void selectAny() {
        final WebElement webElement = getComponents().stream().findAny().orElseThrow();
        webElement.click();
    }

    public void withText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().equals(text)) {
                wb.click();
                return;
            }
        }
    }

    public void containsText(final String text) {
        for (final WebElement wb : getComponents()) {
            if (wb.getText().contains(text)) {
                wb.click();
            }
        }
    }

    private List<WebElement> getComponents() {
        return WaitAction.isElementsExist(this);
    }
}
