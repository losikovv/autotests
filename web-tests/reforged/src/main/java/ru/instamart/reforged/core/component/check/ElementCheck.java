package ru.instamart.reforged.core.component.check;

import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;

@RequiredArgsConstructor
public class ElementCheck {

    private final AbstractComponent component;

    public boolean isDisplayed() {
        return Kraken.waitAction().shouldBeVisible(component).isDisplayed();
    }

    public synchronized boolean isDisplayed(final Object... args) {
        component.setBy(ByKraken.xpathExpression(((ByKraken) component.getBy()).getDefaultXpathExpression(), args));
        return !Kraken.waitAction().shouldNotBeVisible(component);
    }

    public void visible() {
        Kraken.waitAction().shouldBeVisible(component);
    }

    public void invisible() {
        Kraken.waitAction().shouldNotBeVisible(component);
    }
}
