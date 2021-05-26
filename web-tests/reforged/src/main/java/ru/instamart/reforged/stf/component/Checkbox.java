package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;

@ToString(callSuper = true)
public final class Checkbox extends Component {

    public Checkbox(By by) {
        super(by);
    }

    public void check() {
        getComponent().click();
    }

    public void uncheck() {
        getComponent().click();
    }
}
