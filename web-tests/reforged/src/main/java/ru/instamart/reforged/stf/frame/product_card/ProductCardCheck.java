package ru.instamart.reforged.stf.frame.product_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ProductCardCheck extends Check, ProductCardElement {

    @Step("Продуктовая карта открыта")
    default void checkProductCardVisible() {
        waitAction().shouldBeVisible(itemName);
    }
}
