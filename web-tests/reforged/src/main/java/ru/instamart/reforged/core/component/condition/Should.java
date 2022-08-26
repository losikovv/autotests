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

    public void invisible() {
        Kraken.waitAction().shouldNotBeVisible(component);
    }
}
