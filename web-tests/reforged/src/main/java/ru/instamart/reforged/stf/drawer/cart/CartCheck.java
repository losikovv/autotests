package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CartCheck extends Check, CartElement {

    @Step("Проверяем, алерт мин суммы заказа не виден")
    default boolean checkMinSumAlertIsVisible() {
        waitAction().shouldBeVisible(minSumAlert).isDisplayed();
        return true;
    }
}
