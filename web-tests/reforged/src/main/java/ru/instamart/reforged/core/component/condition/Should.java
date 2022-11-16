package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.service.ashot.AshotService;

@RequiredArgsConstructor
public final class Should {

    private final AbstractComponent component;

    public void invisible(final Object... args) {
        Assert.assertTrue(Kraken.waitAction().isInvisible(component, args), "Элемент видим");
    }

    public void invisible(final WebElement webElement) {
        Assert.assertTrue(Kraken.waitAction().isInvisible(component, webElement), "Элемент видим");
    }

    public void unclickable(final Object... args) {
        Assert.assertTrue(Kraken.waitAction().isUnclickable(component, args), "Элемент кликабелен");
    }

    public void animationFinished(final Object... args) {
        Assert.assertTrue(Kraken.waitAction().isAnimationFinished(component, args), "Анимация не завершена");
    }

    public void visible() {
        Kraken.waitAction().shouldBeVisible(component);
    }

    public void visible(final String args) {
        Kraken.waitAction().shouldBeVisible(component, args);
    }

    public void clickable() {
        Kraken.waitAction().shouldBeClickable(component);
    }

    public void screenDiff(final Component... components) {
        AshotService.compareImage(component.screenWebElement(components), component.getComponent(), components);
    }
}
