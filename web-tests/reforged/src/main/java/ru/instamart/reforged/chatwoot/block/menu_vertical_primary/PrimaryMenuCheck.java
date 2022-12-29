package ru.instamart.reforged.chatwoot.block.menu_vertical_primary;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.core.Check;

public interface PrimaryMenuCheck extends Check, PrimaryMenuElement {

    @Step("Проверяем что кнопка вызова пользовательского меню оператора видна")
    default void checkAccountMenuButtonDisplayed() {
        accountMenuButton.should().visible();
    }

    @Step()
    default void checkAccountMenuIndicatorColor(final String colorCode) {
        ThreadUtil.simplyAwait(2);
        var background = accountMenuButtonIndicator.getComponent().getCssValue("background");
        Assert.assertTrue(background.contains(colorCode),
                String.format("Цвет индикатора на кнопке: '%s' не содержит код: '%s'", background, colorCode));
    }

}
