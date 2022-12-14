package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.util.List;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ShopCheck extends Check, ShopElement {

    @Step("Проверяем присутствие элемента - строка №{line}, элемент по порядку №{element} Внутри Таблицы на странице")
    default void checkMinusButtonIsVisible(String line, String element) {
        waitAction().shouldBeVisible(minusItemFromCart, line, element);
    }

    @Step("Проверяем, что отображется сниппет каталога")
    default void checkSnippet() {
        firstProductCardProd.should().visible();
    }

    @Step("Проверяем, что отображается блок ввода количества товаров сниппета")
    default void checkItemQuantityVisible() {
        firstItemQuantity.should().visible();
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
        Assert.assertTrue(waitAction().isElementCollectionSizeEqual(pricesInItemWithoutDiscount, 1));
    }

    @Step("Проверяем, что для товара со скидкой отображаются две цены")
    default void checkItemWithDiscountPricesCount() {
        Assert.assertTrue(waitAction().isElementCollectionSizeEqual(pricesInItemWithDiscount, 3));
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
        youBoughtBeforeCategory.should().invisible();
    }

    @Step("Проверяем что указанное в поле ввода количество товара первого сниппета соответствует ожидаемому: '{productQuantity}'")
    default void checkItemQuantity(final double productQuantity) {
        Assert.assertEquals(StringUtil.stringToDouble(firstItemQuantity.getText()), productQuantity, "Указанное в поле ввода количество товара не соответствует ожидаемому");
    }
}
