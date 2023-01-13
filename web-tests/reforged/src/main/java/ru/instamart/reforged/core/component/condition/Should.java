package ru.instamart.reforged.core.component.condition;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.component.AbstractComponent;
import ru.instamart.reforged.core.component.Component;
import ru.instamart.reforged.core.service.ashot.AshotService;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public final class Should {

    private final AbstractComponent component;

    public void invisible(final Object... args) {
        Assert.assertTrue(Kraken.waitAction().isInvisible(component, args), "Элемент должен быть невидимым");
    }

    public void invisible(final WebElement webElement) {
        Assert.assertTrue(Kraken.waitAction().isInvisible(component, webElement), "Элемент должен быть невидимым");
    }

    public void unclickable(final Object... args) {
        Assert.assertTrue(Kraken.waitAction().isUnclickable(component, args), "Элемент должен быть не кликабелен");
    }

    public void animationFinished(final Object... args) {
        Assert.assertTrue(Kraken.waitAction().isAnimationFinished(component, args), "Анимация должна быть завершена завершена");
    }

    public void visible() {
        Kraken.waitAction().shouldBeVisible(component);
    }

    public void visible(final Object args) {
        Kraken.waitAction().shouldBeVisible(component, args);
    }

    public void clickable() {
        Kraken.waitAction().shouldBeClickable(component);
    }

    public void screenDiff(final Component... components) {
        AshotService.compareImage(component.screenWebElement(components), component.getComponent(), components);
    }

    public void textContains(final String text) {
        Assert.assertTrue(Kraken.waitAction().containText(component, text),
                String.format("Текст элемента '%s' не содержит ожидаемого: '%s'", component.getBy(), text));
    }

    public void textContains(final String text, final String args) {
        Assert.assertTrue(Kraken.waitAction().containText(component, text, args),
                String.format("Текст элемента '%s' не содержит ожидаемого: '%s'", component.getBy(args), text));
    }

    public void textMatches(final Pattern pattern, final String args) {
        Assert.assertTrue(Kraken.waitAction().textMatches(component, pattern, args),
                String.format("Текст элемента '%s' не соответсвует паттерну: '%s'", component.getBy(args), pattern));
    }


}
