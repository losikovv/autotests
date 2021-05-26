package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;
import ru.instamart.ui.manager.AppManager;

@ToString(callSuper = true)
public final class Button extends Component {

    public Button(By by) {
        super(by);
    }

    public void click() {
        getComponent().click();
    }

    public void getText() {
        getComponent().getText();
    }
}
