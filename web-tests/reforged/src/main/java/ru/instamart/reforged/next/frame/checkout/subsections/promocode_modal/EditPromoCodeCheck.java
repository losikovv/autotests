package ru.instamart.reforged.next.frame.checkout.subsections.promocode_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface EditPromoCodeCheck extends EditPromoCodeElement, Check {

    @Step("Проверяем, что алерт первого заказа отображается")
    default void checkFirstOrderAlertVisible() {
        waitAction().shouldBeVisible(firstPromoAlert);
    }

    @Step("Проверяем, что алерт несуществующего промо отображается")
    default void checkNonExistAlertVisible() {
        waitAction().shouldBeVisible(nonExistPromoAlert);
    }
}
