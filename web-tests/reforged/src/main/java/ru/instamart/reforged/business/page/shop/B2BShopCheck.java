package ru.instamart.reforged.business.page.shop;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface B2BShopCheck extends Check, B2BShopElement {

    @Step("Проверяем, спиннер отображается")
    default void checkSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }

    @Step("Проверяем, спиннер не отображается")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем, что отображется сниппет товара каталога")
    default void checkSnippetIsVisible() {
        waitAction().shouldBeVisible(firstProductSnippet);
    }

    @Step("Проверяем, что у товара отображается кнопка 'Убрать товар из корзины'")
    default void checkMinusButtonIsNotVisible() {
        waitAction().shouldNotBeVisible(minusFirstItemFromCart);
    }
}
