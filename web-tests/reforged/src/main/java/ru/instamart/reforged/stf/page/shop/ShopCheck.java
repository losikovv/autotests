package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.enums.ShopUrl;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShopCheck extends Check, ShopElement {

    @Step("Проверяем присутствие элемента Внутри Таблицы на странице")
    default void checkMinusButtonAddedAddressIsVisible() {
        waitAction().shouldBeVisible(minusFirstItemFromCartAddedAddress);
    }

    @Step("Проверяем, что отображается карточка товара")
    default void checkFirstProductCardIsVisible() {
        waitAction().shouldBeVisible(firstProductCard);
    }

    @Step("Проверяем, не отображается спиннер")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем, что отображется сниппет каталога")
    default void checkSnippet() {
        waitAction().shouldBeVisible(firstProductCardProd);
    }

    @Step("Проверяем, что находимся на странице дефолтного магазина")
    default void checkDefaultShopOpened() {
        waitAction().urlEquals(EnvironmentProperties.Env.FULL_SITE_URL + ShopUrl.DEFAULT.getUrl());
    }
}
