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

    @Step("Проверяем, отображается спиннер")
    default void checkSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }

    @Step("Проверяем, что отображется сниппет каталога")
    default void checkSnippet() {
        waitAction().shouldBeVisible(firstProductCardProd);
    }

    @Step("Проверяем, что находимся на странице дефолтного магазина")
    default void checkDefaultShopOpened() {
        waitAction().urlEquals(EnvironmentProperties.Env.FULL_SITE_URL + ShopUrl.DEFAULT.getUrl());
    }

    @Step("Проверяем, что изображение товара отображается")
    default void checkItemImageDisplayed() {
        waitAction().shouldBeVisible(imageInFirstItem);
    }

    @Step("Проверяем, что название товара отображается")
    default void checkItemNameDisplayed() {
        waitAction().shouldBeVisible(nameInFirstItem);
    }

    @Step("Проверяем, что размер упаковки товара отображается")
    default void checkItemPackageSizeDisplayed() {
        waitAction().shouldBeVisible(packageSizeInFirstItem);
    }

    @Step("Проверяем, что для товара без скидки отображается только одна цена")
    default void checkItemWithoutDiscountPricesCount() {
        waitAction().elementCollectionSizeShouldBeEqual(pricesInItemWithoutDiscount, 1);
    }

    @Step("Проверяем, что для товара со скидкой отображаются две цены")
    default void checkItemWithDiscountPricesCount() {
        waitAction().elementCollectionSizeShouldBeEqual(pricesInItemWithDiscount, 2);
    }
}
