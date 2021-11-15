package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;

import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.*;

public interface SearchCheck extends Check, SearchElement {

    @Step("Проверяем, что отображается кнопка добавления в корзину у первого товара в поиске")
    default void checkAddToCartButtonVisible() {
        waitAction().shouldBeVisible(firstAddToCartButton);
    }

    @Step("Проверяем, что отображается заглушка товаров в поиске")
    default void checkSearchProductsSkeletonVisible() {
        waitAction().shouldBeVisible(searchProductsSkeleton);
    }

    @Step("Проверяем, что селектор виден")
    default void checkSortSelectVisible() {
        waitAction().shouldBeVisible(selectSort);
    }

    @Step("Проверяем, что заглушка загрузки видна")
    default void checkProductsStubVisible() {
        waitAction().shouldBeVisible(productsStub);
    }

    @Step("Проверяем, что заглушка загрузки не видна")
    default void checkProductsStubNotVisible() {
        waitAction().shouldNotBeVisible(productsStub);
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

    @Step("Проверка подскролла страницы поиска к новой выдаче")
    default void checkPageScrolled() {
        waitAction().elementCollectionSizeShouldBeEqual(searchProductPrices, 40);
    }

    @Step("Проверяем, что сортировка 'Сначала дешевые' работает корректно")
    default void checkPriceAscSortCorrect() {
        jsAction().waitForDocumentReady();
        var tmp = new ArrayList<>();
        searchProductPrices.getElements().forEach(element -> {
            tmp.add(StringUtil.stringToDoubleParse(element.getText()));
        });
        assertEquals(tmp, tmp.stream().sorted().collect(Collectors.toList()));
    }

    @Step("Проверяем, что сортировка 'Сначала дорогие' работает корректно")
    default void checkPriceDescSortCorrect() {
        jsAction().waitForDocumentReady();
        var tmp = new ArrayList<>();
        searchProductPrices.getElements().forEach(element -> {
            tmp.add(StringUtil.stringToDoubleParse(element.getText()));
        });
        assertEquals(tmp, tmp.stream().sorted(Collections.reverseOrder()).collect(Collectors.toList()));
    }

    @Step("Проверить, что фильтр '{0}' задизейблен")
    default void checkFilterDisabled(String filterText) {
        waitAction().shouldNotBeClickable(filterCheckbox, filterText);
    }

    @Step("Проверить, что сортировка '{0}' применена")
    default void checkSortEnabled(String sortText) {
        waitAction().shouldBeVisible(selectSortApplied, sortText);
    }

    @Step("Проверить, что все картинки загружены")
    default void checkSearchImgLoaded() {
        searchProductsImagesCollection.getElements().forEach(element -> {
            productImg.waitImgLoad(element.getAttribute("src"));
        });
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
