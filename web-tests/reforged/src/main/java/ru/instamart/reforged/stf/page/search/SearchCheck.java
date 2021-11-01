package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SearchCheck extends Check, SearchElement {

    @Step("Проверяем, что отображается кнопка добавления в корзину у первого товара в поиске")
    default void checkAddToCartButtonVisible() {
        waitAction().shouldBeVisible(firstAddToCartButton);
    }

    @Step("Проверяем, что отображается заглушка товаров в поиске")
    default void checkSearchProductsSkeletonVisible() {
        waitAction().shouldBeVisible(searchProductsSkeleton);
    }

    @Step("Проверяем, что отображается спиннер в поиске")
    default void checkSearchProductsSpinnerVisible() {
        waitAction().shouldBeVisible(searchSpinner);
    }

    @Step("Проверяем, что спиннер в поиске не отображается")
    default void checkSearchProductsSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(searchSpinner);
    }

    @Step("Проверяем, что отображается спиннер в бесконечном поиске")
    default void checkInfiniteSearchProductsSpinnerVisible() {
        waitAction().shouldBeVisible(infiniteSearchSpinner);
    }

    @Step("Проверяем, что спиннер в бесконечном поиске не отображается")
    default void checkInfiniteSearchProductsSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(infiniteSearchSpinner);
    }

    @Step("Проверяем, что кол-во товаров: {0} не равно кол-ву после применения фильтра: {1}")
    default void checkQuantitiesNotEquals(double firstQuantity, double secondQuantity) {
        krakenAssert.assertNotEquals(firstQuantity, secondQuantity, "Фильтр не применился, кол-во товаров не изменилось");
    }

    @Step("Проверяем, что не отображается заглушка товаров в поиске")
    default void checkSearchProductsSkeletonNotVisible() {
        waitAction().shouldNotBeVisible(searchProductsSkeleton);
    }

    @Step("Проверяем, что сортировка 'Сначала дешевые' работает корректно, цена первого {0} != цене второго {1}")
    default void checkPriceAscSortCorrect(double firstPrice, double secondPrice) {
        krakenAssert.assertTrue(firstPrice < secondPrice, "Цена первого товара равна второму, ошибка сортировки 'Сначала дешевые'");
    }

    @Step("Проверяем, что сортировка 'Сначала дорогие' работает корректно, цена первого {0} != цене второго {1}")
    default void checkPriceDescSortCorrect(double firstPrice, double secondPrice) {
        krakenAssert.assertTrue(firstPrice > secondPrice, "Цена первого товара равна второму, ошибка сортировки 'Сначала дорогие'");
    }

    @Step("Проверить, что фильтр '{0}' задизейблен")
    default void checkFilterDisabled(String filterText) {
        waitAction().elementSelectCheckboxState(filterCheckbox, false, filterText);
    }

    @Step("Проверяем, что сетка найденных товаров не отображается")
    default void checkSearchProductGridNotVisible() {
        waitAction().shouldNotBeVisible(searchProductGrid);
    }

    @Step("Проверяем, что произошел переход в категорию {0}")
    default void checkTaxonTitle(final String title) {
        waitAction().shouldBeVisible(categoryTitle, title);
    }

    @Step("Проверяем, что товарные подсказки при поиске алко имеют картинки 18+")
    default void checkAlcoStubInProductsSearch() {
        assertEquals(searchProductsCollectionImagesAlco.getElements().size(), searchProductsCollection.getElements().size(), "Не все картинки товаров имеют картинки-заглушки 18+");
    }

    @Step("Проверяем, что сетка найденных товаров отображается")
    default void checkSearchProductGridVisible() {
        waitAction().shouldBeVisible(searchProductGrid);
    }

    @Step("Проверяем, что отображается заголовок пустых результатов поиска")
    default void checkEmptySearchPlaceholderVisible() {
        waitAction().shouldBeVisible(emptySearchPlaceHolder);
    }
}
