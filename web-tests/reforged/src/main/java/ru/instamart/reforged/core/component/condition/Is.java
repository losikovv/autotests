package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;

@RequiredArgsConstructor
public class Is {

    private final AbstractComponent component;

    public boolean displayed() {
        return Kraken.waitAction().isVisible(component);
    }

    public boolean displayed(final Object... args) {
        return !Kraken.waitAction().shouldNotBeVisible(component, args);
    }

    public boolean containText(final String text) {
        return Kraken.waitAction().containText(component, text);
    }

    public boolean containTextFromAttribute(final String attribute, final String text) {
        return Kraken.waitAction().containTextFromAttribute(component, attribute, text);
    }

    public boolean containText(final String text, final Object... args) {
        return Kraken.waitAction().containText(component, text, args);
    }
}
