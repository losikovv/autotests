package ru.instamart.reforged.chatwoot.block.menu_vertical_secondary;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface SecondaryMenuCheck extends Check, SecondaryMenuElement {

    @Step("Проверяем, что в меню активен пункт: '{itemName}'")
    default void checkMenuItemActive(final String itemName){
        Assert.assertEquals(secondaryMenuActiveLink.getText(), itemName, "Выбранный пункт меню отличается от ожидаемого");
    }
}
