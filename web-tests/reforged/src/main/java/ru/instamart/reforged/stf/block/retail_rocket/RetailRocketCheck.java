package ru.instamart.reforged.stf.block.retail_rocket;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailRocketCheck extends Check, RetailRocketElement {

    @Step("Наличие блока 'Добавить к заказу?'")
    default void checkBlockAddToCart() {
        waitAction().shouldBeVisible(carousel);
    }
}
