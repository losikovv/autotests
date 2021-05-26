package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;

@ToString(callSuper = true)
public final class Input extends Component {

    public Input(By by) {
        super(by);
    }

    public void fill(final String data) {
        getComponent().sendKeys(data);
    }
}
