package ru.instamart.reforged.stf.page.multiretailer_search.search;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.Set;

import static org.testng.Assert.assertNotEquals;

public interface MultiSearchCheck extends Check, MultiSearchElement {

    @Step("Проверяем, что отображаются карточки ритейлеров")
    default void checkRetailerCardsVisible() {
        Kraken.waitAction().shouldBeVisible(retailerCardsList);
    }

    @Step("Проверяем, что отображаются сниппеты найденных продуктов")
    default void checkProductsCardsVisible() {
        Kraken.waitAction().shouldBeVisible(productsSnippets);
    }

    @Step("Проверяем, что все найденные продукты соответствуют критериям поиска: '{searchText}'")
    default void checkSearchResultsContains(final String searchText) {
        productsTitles.getTextFromAllElements().forEach(
                title -> krakenAssert.assertTrue(title.toLowerCase().contains(searchText.toLowerCase()),
                        String.format("Название продукта '%s' не содержит текст '%s'", title, searchText)));
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что найденные продукты имеют ссылку на ритейлера: '{retailerUrlName}'")
    default void checkProductsRetailerLinksContains(final String retailerUrlName) {
        productsLinks.getAttributeValues("href").forEach(
                link -> krakenAssert.assertTrue(link.contains(retailerUrlName),
                        String.format("Ссылка на продукт '%s' не содержит текст '%s'", link, retailerUrlName)));
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что список продуктов изменился")
    default void checkIfProductsListChanged(final Set<String> productsList) {
        assertNotEquals(productsTitles.getTextFromAllElements(), productsList, "Список продуктов не изменился");
    }
}
