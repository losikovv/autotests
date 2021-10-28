package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

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

    @Step("Проверяем, что не отображается заглушка товаров в поиске")
    default void checkSearchProductsSkeletonNotVisible() {
        waitAction().shouldNotBeVisible(searchProductsSkeleton);
    }

    @Step("Проверяем, что сортировка 'Сначала дешевые' работает корректно")
    default void checkPriceAscSortCorrect(double firstPrice, double secondPrice) {
        Assert.assertTrue(firstPrice < secondPrice);
    }

    @Step("Проверяем, что сетка найденных товаров не отображается")
    default void checkSearchProductGridNotVisible() {
        waitAction().shouldNotBeVisible(searchProductGrid);
    }

    @Step("Проверяем, что произошел переход в категорию {0}")
    default void checkTaxonTitle(final String title) {
        waitAction().shouldBeVisible(categoryTitle, title);
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
