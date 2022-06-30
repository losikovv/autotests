package ru.instamart.reforged.next.page.shop;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.config.UiProperties;
import ru.instamart.reforged.core.enums.ShopUrl;

import java.util.List;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShopCheck extends Check, ShopElement {

    @Step("Проверяем присутствие элемента - строка №{line}, элемент по порядку №{element} Внутри Таблицы на странице")
    default void checkMinusButtonIsVisible(String line, String element) {
        waitAction().shouldBeVisible(minusItemFromCart, line, element);
    }

    @Step("Проверяем, что отображается карточка товара")
    default void checkFirstProductCardIsVisible() {
        waitAction().shouldBeVisible(firstProductCard);
    }

    @Step("Проверяем, что отображется сниппет каталога")
    default void checkSnippet() {
        waitAction().shouldBeVisible(firstProductCardProd);
    }

    @Step("Проверяем, что находимся на странице дефолтного магазина")
    default void checkDefaultShopOpened() {
        waitAction().urlEquals(UiProperties.STF_URL);
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

    @Step("Проверяем что заголовок первой категории товаров в магазине '{0}' соответствует '{1}'")
    default void checkFirstCategoryIs(final String actualCategoryName, final String expectedCategoryName) {
        Assert.assertEquals(actualCategoryName, expectedCategoryName,
                String.format("Название первой категории товаров в магазине: '%s' отличается от ожидаемого: '%s' ", actualCategoryName, expectedCategoryName));
    }

    @Step("Проверяем что заголовок первой категории товаров в магазине '{0}' отличается от '{1}'")
    default void checkFirstCategoryIsNot(final String actualCategoryName, final String expectedCategoryName) {
        Assert.assertNotEquals(actualCategoryName, expectedCategoryName,
                String.format("Название первой категории товаров в магазине: '%s' не отличается от: '%s' ", actualCategoryName, expectedCategoryName));
    }

    @Step("Проверяем что список продуктов соответствует ожидаемому")
    default void checkProductListsEquals(final List<String> actualProductNames, final List<String> expectedProductNames) {
        Assert.assertEquals(actualProductNames, expectedProductNames, "Список продуктов не соответствует ожидаемому");
    }

    @Step("Проверяем что категория 'Вы покупали ранее' не отображается")
    default void checkYouBoughtBeforeCategoryNotDisplayed() {
        waitAction().shouldNotBeVisible(youBoughtBeforeCategory);
    }
}
