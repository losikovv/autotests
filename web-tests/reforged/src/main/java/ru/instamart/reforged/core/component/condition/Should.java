package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.service.ashot.AshotService;

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

    public void clickable() {
        Kraken.waitAction().shouldBeClickable(component);
    }

    public void notAnimated() {
        Kraken.waitAction().shouldNotBeAnimated(component);
    }

    public void screenDiff(final Component... components) {
        AshotService.compareImage(component.screenWebElement(components), component.getComponent(), components);
    }
}
