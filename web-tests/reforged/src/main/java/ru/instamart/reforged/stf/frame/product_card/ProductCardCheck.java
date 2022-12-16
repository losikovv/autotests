package ru.instamart.reforged.stf.frame.product_card;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface ProductCardCheck extends Check, ProductCardElement {

    @Step("Продуктовая карта открыта")
    default void checkProductCardVisible() {
        waitAction().shouldBeVisible(itemName);
    }

    @Step("Продуктовая карта закрыта")
    default void checkProductCardIsNotVisible() {
        itemName.should().invisible();
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

    @Step("Кнопка 'Купить' отображается")
    default void checkBuyButtonVisible() {
        buy.should().visible();
    }

    @Step("Кнопка 'Купить' отображается неактивной")
    default void checkBuyButtonInactive() {
        buy.should().unclickable();
    }

    @Step("Проверяем, что отображается иконка скидки (для товара со скидкой)")
    default void checkDiscountLabelDisplayed() {
        waitAction().shouldBeVisible(discountLabel);
    }

    @Step("Проверяем, что не отображается иконка скидки (для товара без скидки)")
    default void checkDiscountLabelNotDisplayed() {
        discountLabel.should().invisible();
    }

    @Step("Проверяем, что навигационная цепочка категорий (хлебные крошки) отображается")
    default void checkBreadscrumbsVisible() {
        Kraken.waitAction().shouldBeVisible(categoriesBreadscrumbs);
    }

    @Step("Проверяем отображение картинки товара")
    default void checkProductImageDisplayed() {
        Kraken.waitAction().shouldBeVisible(image);
    }

    @Step("Проверяем, что название товара отображается")
    default void checkNameDisplayed() {
        Kraken.waitAction().shouldBeVisible(itemName);
    }

    @Step("Проверяем, что название товара соответствует ожидаемому: {expectedProductTitle}")
    default void checkProductTitle(final String expectedProductTitle) {
        Assert.assertEquals(itemName.getText(), expectedProductTitle, "Название продукта не соответствует ожидаемому");
    }

    @Step("Проверяем, что размер упаковки товара отображается")
    default void checkPackageSizeDisplayed() {
        Kraken.waitAction().shouldBeVisible(packageSize);
    }

    @Step("Проверяем, что отображается 'Описание продукта'")
    default void checkDescriptionDisplayed() {
        Kraken.waitAction().shouldBeVisible(description);
    }

    @Step("Проверяем, что блок с общей информацией отображается")
    default void checkGeneralInfoDisplayed() {
        Kraken.waitAction().shouldBeVisible(generalInformationBlock);
    }

    @Step("Проверяем, что отображается кнопка 'Избранное'")
    default void checkFavoriteButtonDisplayed() {
        Kraken.waitAction().shouldBeVisible(favorite);
    }

    @Step("Проверяем, что для товара без скидки отображается только одна цена")
    default void checkItemWithoutDiscountPricesCount() {
        Assert.assertTrue(waitAction().isElementCollectionSizeEqual(prices, 1));
    }

    @Step("Проверяем, что для товара со скидкой отображаются две цены")
    default void checkItemWithDiscountPricesCount() {
        Assert.assertTrue(waitAction().isElementCollectionSizeEqual(prices, 2));
    }

    @Step("Проверяем соответствие цены товара в карточке: {0} и сниппете: {1}")
    default void checkFullPrice(final String cardPrice, final String snippetPrice) {
        Assert.assertEquals(cardPrice, snippetPrice, "отображаемая цена товара в карточке не соответствует цене товара в сниппете");
    }

    @Step("Проверяем соотвествие цены товара со скидкой в карточке: {0} и сниппете: {1}")
    default void checkDiscountPrice(final String cardPriceWithDiscount, final String snippetPriceWithDiscount) {
        Assert.assertEquals(cardPriceWithDiscount, snippetPriceWithDiscount, "отображаемая цена товара со скидкой в карточке не соответствует цене товара со скидкой в сниппете");
    }

    @Step("Проверяем что кнопка увеличения кол-ва кликабельна")
    default void checkIsIncreaseClickable() {
        increase.shouldBe().clickable();
    }

    @Step("Проверяем что поле ввода количества товара отображается")
    default void checkQuantityInputVisible() {
        quantityInputDiv.should().visible();
    }

    @Step("Проверяем что указанное в поле ввода количество товара соответствует ожидаемому: '{productQuantity}'")
    default void checkItemQuantity(final double productQuantity) {
        Assert.assertEquals(StringUtil.stringToDouble(quantityInputDiv.getText()), productQuantity, "Указанное в поле ввода количество товара не соответствует ожидаемому");
    }

    @Step("Проверяем что указанное в поле ввода количество товара соответствует ожидаемому: '{productQuantity}'")
    default void checkItemQuantity(final String productQuantity) {
        quantityInputDiv.should().textContains(productQuantity);
    }

    @Step("Проверяем что кнопка уменьшения кол-ва кликабельна")
    default void checkDecreaseClickable() {
        decrease.shouldBe().clickable();
    }
}
