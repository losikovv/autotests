package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.instamart.reforged.action.WaitAction;

@ToString(callSuper = true)
public final class Selector extends Component {

    private Select select;

    public Selector(By by) {
        super(by);
    }

    @Override
    protected WebElement getComponent() {
        if (component == null) {
            component = WaitAction.shouldBeClickable(this);
        }
        return component;
    }

    public void selectByText(final String text) {
        getSelect().selectByVisibleText(text);
    }

    public void selectByIndex(final int index) {
        getSelect().selectByIndex(index);
    }

    private Select getSelect() {
        if (select == null) {
            select = new Select(getComponent());
        }
        return select;
    }
}
