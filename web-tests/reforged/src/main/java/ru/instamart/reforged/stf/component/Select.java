package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;

@ToString(callSuper = true)
public final class Select extends Component {

    public Select(By by) {
        super(by);
    }
}
