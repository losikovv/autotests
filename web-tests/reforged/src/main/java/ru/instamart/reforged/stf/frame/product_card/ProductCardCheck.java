package ru.instamart.reforged.stf.frame.product_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ProductCardCheck extends Check, ProductCardElement {

    @Step("Продуктовая карта открыта")
    default void checkProductCardVisible() {
        waitAction().shouldBeVisible(itemName);
    }

    @Step("Продуктовая карта закрыта")
    default void checkProductCardIsNotVisible() {
        waitAction().shouldNotBeVisible(itemName);
    }

    @Step("На продуктовой карте есть алерт продажи алкоголя 18+")
    default void checkAlcoAlertVisible() {
        waitAction().shouldBeVisible(alcoAlert);
    }

    @Step("На продуктовой карте есть картинка-заглушка продажи алкоголя 18+")
    default void checkAlcoStubVisible() {
        waitAction().shouldBeVisible(alcoStub);
    }

    @Step("На продуктовой карте алкоголя есть кнопка 'зарезервировать'")
    default void checkReserveButtonVisible() {
        waitAction().shouldBeVisible(reserveButton);
    }
}
