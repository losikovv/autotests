package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;

@ToString(callSuper = true)
public final class Autocomplete extends Component {

    public Autocomplete(By by) {
        super(by);
    }
}
