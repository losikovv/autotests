package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;

@RequiredArgsConstructor
public class Is {

    private final AbstractComponent component;

    public boolean displayed() {
        return Kraken.waitAction().shouldBeVisible(component).isDisplayed();
    }

    public synchronized boolean displayed(final Object... args) {
        component.setBy(ByKraken.xpathExpression(((ByKraken) component.getBy()).getDefaultXpathExpression(), args));
        return !Kraken.waitAction().shouldNotBeVisible(component);
    }
}
