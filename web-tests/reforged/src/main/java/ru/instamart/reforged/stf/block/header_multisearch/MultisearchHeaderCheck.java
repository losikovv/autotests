package ru.instamart.reforged.stf.block.header_multisearch;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

public interface MultisearchHeaderCheck extends Check, MultisearchHeaderElement {

    @Step("Проверяем, что выбрана 'Доставка'")
    default void checkDeliveryActive() {
        Kraken.waitAction().shouldBeVisible(deliveryButtonActive);
    }

    @Step("Проверяем, что выбран 'Самовывоз'")
    default void checkPickupActive() {
        Kraken.waitAction().shouldBeVisible(pickupButtonActive);
    }

    @Step("Проверяем, что отображается поле ввода мультиритейлерного поиска")
    default void checkMultisearchInputVisible() {
        Kraken.waitAction().shouldBeVisible(multisearch);
    }

    @Step("Проверяем, что не отображается поле ввода мультиритейлерного поиска")
    default void checkMultisearchInputNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(multisearch);
    }

    @Step("Проверяем, что отображается кнопка пользовательского меню")
    default void checkUserActionsButtonVisible() {
        Kraken.waitAction().shouldNotBeVisible(userActionsToggle);
    }

    @Step("Проверяем, что указан адрес: '{expectedAddress}'")
    default void checkEnteredAddress(final String expectedAddress) {
        assertEquals(expectedAddress, addressChange.getText(), "Указанный адрес отличается от ожидаемого");
    }

    @Step("Проверяем, что отображаются вкладки ритейлеров в саджесторе межритейлерного поиска")
    default void checkRetailersVisibleInSuggester() {
        Kraken.waitAction().shouldBeVisible(retailersInDropdown);
    }

    @Step("Проверяем, что отображаются товары в саджесторе межритейлерного поиска")
    default void checkProductsVisibleInSuggester() {
        Kraken.waitAction().shouldBeVisible(productsInActiveTab);
    }

    @Step("Проверяем, саджестор не отображается")
    default void checkProductsNotVisibleInSuggester() {
        Kraken.waitAction().shouldNotBeVisible(productsInActiveTab);
    }

    @Step("Проверяем, что все найденные продукты саджестора соответствуют критериям поиска: '{searchText}'")
    default void checkSearchResultsContains(final String searchText) {
        productsTitleInActiveTab.getTextFromAllElements().forEach(
                title -> krakenAssert.assertTrue(title.toLowerCase().contains(searchText.toLowerCase()),
                        String.format("Название продукта '%s' не содержит текст '%s'", title, searchText)));
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что найденные продукты саджестора имеют ссылку на ритейлера: '{retailerUrlName}'")
    default void checkProductsRetailerLinksContains(final String retailerUrlName) {
        productsLinkInActiveTab.getAttributeValues("href").forEach(
                link -> krakenAssert.assertTrue(link.contains(retailerUrlName),
                        String.format("Ссылка на продукт '%s' не содержит текст '%s'", link, retailerUrlName)));
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что список продуктов изменился")
    default void checkIfProductsListChanged(final Set<String> productsList) {
        assertNotEquals(productsTitleInActiveTab.getTextFromAllElements(), productsList, "Список продуктов не изменился");
    }
}