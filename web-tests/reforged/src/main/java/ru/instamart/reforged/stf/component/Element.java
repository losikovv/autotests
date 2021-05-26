package ru.instamart.reforged.stf.component;

import lombok.ToString;
import org.openqa.selenium.By;

@ToString(callSuper = true)
public final class Element extends Component {

    public Element(final By by) {
        super(by);
    }
}
