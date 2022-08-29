package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.TimeoutException;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;

@RequiredArgsConstructor
public class Is {

    private final AbstractComponent component;

    public boolean displayed() {
        try {
            return Kraken.waitAction().shouldBeVisible(component).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public synchronized boolean displayed(final Object... args) {
        try {
            component.setBy(ByKraken.xpathExpression(((ByKraken) component.getBy()).getDefaultXpathExpression(), args));
            return !Kraken.waitAction().shouldNotBeVisible(component);
        } catch (TimeoutException e) {
            return false;
        }
    }
}
