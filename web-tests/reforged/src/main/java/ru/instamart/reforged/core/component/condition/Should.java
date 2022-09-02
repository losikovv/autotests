package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;

@RequiredArgsConstructor
public final class Should {

    private final AbstractComponent component;

    public void visible() {
        Kraken.waitAction().shouldBeVisible(component);
    }

    public void visible(final String args) {
        Kraken.waitAction().shouldBeVisible(component, args);
    }

    public void invisible() {
        Kraken.waitAction().shouldNotBeVisible(component);
    }

    public void invisible(final String args) {
        Kraken.waitAction().shouldNotBeVisible(component, args);
    }

    public void notClickable() {
        Kraken.waitAction().shouldNotBeClickable(component);
    }
}
