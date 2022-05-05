package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.TimeUtil;
import ru.instamart.reforged.core.Check;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailersPageCheck extends Check, RetailersPageElements {

    @Step("Проверяем, что кнопка 'Добавить ретейлера' отображается")
    default void checkAddNewRetailerButtonVisible() {
        waitAction().shouldBeVisible(addNewRetailerButton);
    }

    @Step("Проверяем, что попап удаления магазина отображается")
    default void checkDeactivateStorePopupVisible() {
        waitAction().shouldBeVisible(deactivateStorePopup);
    }

    @Step("Проверяем, что попап удаления магазина не отображается")
    default void checkDeactivateStorePopupNotVisible() {
        waitAction().shouldNotBeVisible(deactivateStorePopup);
    }

    @Step("Проверяем, что попап фильтра ретейлеров по доступности отображается")
    default void checkAccessibilityFilterDropdownVisible() {
        waitAction().shouldBeVisible(accessibilityFilterDropdown);
    }

    @Step("Проверяем, что попап фильтра ретейлеров по доступности не отображается")
    default void checkAccessibilityFilterDropdownNotVisible() {
        waitAction().shouldNotBeVisible(accessibilityFilterDropdown);
    }

    @Step("Сравнение количества ретейлеров с количеством надписей о доступности/недоступности {0}")
    default void retailerAccessibilityCompare(final int quantity) {
        Assert.assertEquals(retailersAccessibilityInTable.elementCount(), quantity,
                "Количество ретейлеров не совпадает с количеством надписей о доступности/недоступности");
    }

    @Step("Сравнение количества ретейлеров с количеством дат создания ретейлеров {0}")
    default void retailerCreateDateCompare(final int quantity) {
        Assert.assertEquals(retailersCreationDateInTable.elementCount(), quantity,
                "Количество ретейлеров не совпадает с количеством дат создания");
    }

    @Step("Проверка недоступности ретейлера {0}")
    default void checkRetailerInactive(final String retailer) {
        waitAction().shouldBeVisible(retailerInaccessibilityInTable, retailer);
    }

    @Step("Проверка доступности ретейлера {0}")
    default void checkRetailerActive(final String retailer) {
        waitAction().shouldBeVisible(retailerAccessibilityInTable, retailer);
    }

    @Step("Проверка доступности магазина по адресу {0}")
    default void checkStoreActiveViaAddress(final String address) {
        waitAction().shouldBeVisible(storeAccessibilityInTable, address);
    }

    @Step("Проверка недоступности магазина по адресу {0}")
    default void checkStoreInactiveViaAddress(final String address) {
        waitAction().shouldBeVisible(storeInaccesubilityInTable, address);
    }


    @Step("Проверка доступности магазина по адресу {0}")
    default void checkRetailerSearchCorrect(final String retailer) {
        waitAction().shouldBeVisible(retailerInSearchResultTable, retailer);
    }

    @Step("Проверка определенного региона {0} у ретейлера")
    default void checkRetailerRegionCorrect(final String region) {
        waitAction().shouldBeVisible(regionNameInTable, region);
    }

    @Step("Проверяем, что сортировка городов магазинов по количеству отображается корректно")
    default void checkStoreSortViaNumbersCorrect() {
        List<Integer> numbersOfCities = storesInTable.getTextFromAllElements().stream()
                .map(StringUtil::parseNumberCitiesFromString).collect(Collectors.toList());
        Assert.assertEquals(numbersOfCities, CollectionUtil.reverseListOrder(numbersOfCities),
                "Города по количеству магазинов отсортированы некорректно");
    }

    @Step("Проверяем, что сортировка городов магазинов по дате создания отображается корректно")
    default void checkStoreSortViaCreationDateCorrect() {
        List<ZonedDateTime> datesList = addressDatesInTable.getTextFromAllElements().stream()
                .map(TimeUtil::convertStringToDate).collect(Collectors.toList());
        Assert.assertEquals(datesList, CollectionUtil.sortList(datesList),
                "Города по дате создания отсортированы некорректно");
    }

    @Step("Проверяем, что список магазинов отсортирован по алфавиту")
    default void checkIfStoreAlphabeticallySorted() {
        final var listCities = storesInTable.getTextFromAllElements();
        List<Integer> listCitiesNumbers = listCities.stream().map(StringUtil::parseNumberCitiesFromString)
                .collect(Collectors.toList());

        Map<String, Integer> mapMerged = CollectionUtil.mergeIntoMap(listCities, listCitiesNumbers);
        Map<String, Integer> mapNotUnique = CollectionUtil.removeUniqueValues(mapMerged);
        Map<String, Integer> mapSorted = CollectionUtil.reverseSortMapByValue(mapNotUnique);

        Assert.assertEquals(mapNotUnique, mapSorted, "Список городов отсортирован некорректно");
    }

    @Step("Проверяем, что результаты поиска ретейлера показаны")
    default void checkOptionsInRetailerSearchVisible() {
        waitAction().shouldBeVisible(retailerSearchOptions);
    }

    @Step("Проверяем, что результаты поиска региона показаны")
    default void checkOptionsInRegionSearchVisible() {
        waitAction().shouldBeVisible(regionSearchOptions);
    }

    @Step("Проверяем, что спиннер показан")
    default void checkSpinnerVisible() {
        waitAction().shouldBeVisible(spinner);
    }

    @Step("Проверяем, что спиннер скрыт")
    default void checkSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем, что ретейлеры загружены")
    default void checkRetailersLoaded() {
        waitAction().shouldBeVisible(retailersLoadCondition);
    }

    @Step("Проверяем, что ретейлеры не загружены")
    default void checkRetailersNotLoaded() {
        waitAction().shouldNotBeVisible(retailersLoadCondition);
    }

    @Step("Проверяем, что кнопка фильтра доступности кликабельна")
    default void checkAccessibilityFilterButtonClickable() {
        waitAction().shouldBeClickable(accessibilityFilterButton);
    }

    @Step("Проверяем, что спиннер показан")
    default void checkRegionSearchSpinnerVisible() {
        waitAction().shouldBeVisible(spinnerRegionSearch);
    }

    @Step("Проверяем, что спиннер скрыт")
    default void checkRegionSearchSpinnerNotVisible() {
        waitAction().shouldNotBeVisible(spinnerRegionSearch);
    }

    @Step("Проверяем, что лейбл с именем ретейлера {0} появился в строке поиска")
    default void checkRetailerLabelInSearchFieldVisible(final String retailer) {
        waitAction().shouldBeVisible(retailerNameOnSearchLabel, retailer);
    }

    @Step("Проверяем, что сортировка по имени ретейлера ASC включена")
    default void checkSortViaNameAscEnabled() {
        waitAction().shouldBeVisible(sortRetailersViaNameAscInTable);
    }

    @Step("Проверяем, что сортировка по имени ретейлера DESC включена")
    default void checkSortViaNameDescEnabled() {
        waitAction().shouldBeVisible(sortRetailersViaNameDescInTable);
    }

    @Step("Проверяем, что сортировка по дате создания ретейлера ASC включена")
    default void checkSortViaCreationDateAscEnabled() {
        waitAction().shouldBeVisible(sortRetailersViaCreationDateAsc);
    }

    @Step("Проверяем, что сортировка по дате создания ретейлера DESC включена")
    default void checkSortViaCreationDateDescEnabled() {
        waitAction().shouldBeVisible(sortRetailersViaCreationDateDesc);
    }

    @Step("Проверяем, что сортировка по имени ретейлера ASC корректна")
    default void checkSortViaNameAsc() {
        final var retailersNamesInTable = retailersInTable.getTextFromAllElements();
        final var sorted = CollectionUtil.sortListCaseInsensitive(retailersNamesInTable);
        krakenAssert.assertEquals(retailersNamesInTable, sorted,
                "Города по имени ASC отсортированы некорректно");
    }

    @Step("Проверяем, что сортировка по имени ретейлера DESC корректна")
    default void checkSortViaNameDesc() {
        final var retailersNamesInTable = retailersInTable.getTextFromAllElements();
        final var sorted = CollectionUtil.reverseListOrderCaseInsensitive(retailersNamesInTable);
        krakenAssert.assertEquals(retailersNamesInTable, sorted,
                "Города по имени DESC отсортированы некорректно");
    }

    @Step("Проверяем, что сортировка по дате создания ASC корректна")
    default void checkSortViaCreationDateAsc() {
        final var retailersCreationDate = retailersCreationDateInTable.getTextFromAllElements();
        final List<ZonedDateTime> retailersCreationDateConverted = retailersCreationDate.stream()
                .map(TimeUtil::convertStringToDate).collect(Collectors.toList());
        final List<ZonedDateTime> retailersCreationDateSorted = CollectionUtil.sortList(retailersCreationDateConverted);
        krakenAssert.assertEquals(retailersCreationDateConverted, retailersCreationDateSorted,
                "Ретейлеры по дате создания ASC отсортированы некорректно");
    }

    @Step("Проверяем, что сортировка по дате создания DESC корректна")
    default void checkSortViaCreationDateDesc() {
        final var retailersCreationDate = retailersCreationDateInTable.getTextFromAllElements();
        final List<ZonedDateTime> retailersCreationDateConverted = retailersCreationDate.stream()
                .map(TimeUtil::convertStringToDate).collect(Collectors.toList());
        final List<ZonedDateTime> retailersCreationDateSorted = CollectionUtil.reverseListOrder(retailersCreationDateConverted);
        krakenAssert.assertEquals(retailersCreationDateConverted, retailersCreationDateSorted,
                "Ретейлеры по дате создания DESC отсортированы некорректно");
    }

    @Step("Проверяем, что все отображаемые ретейлеры доступны")
    default void checkOnlyAccessibleRetailersVisible() {
        krakenAssert.assertEquals(retailersInTable.elementCount(), accessibleRetailerInTable.elementCount(),
                            "Среди отображаемых ретейлеров есть недоступные");
    }

    @Step("Проверяем, что все отображаемые ретейлеры недоступны")
    default void checkOnlyInaccessibleRetailersVisible() {
        krakenAssert.assertEquals(retailersInTable.elementCount(), inaccessibleRetailerInTable.elementCount(),
                            "Среди отображаемых ретейлеров есть доступные");
    }

    @Step("Проверяем, что кнопка фильтра доступности ретейлеров отображается и не анимирована")
    default void checkAccessibilityFilterButtonNotAnimated() {
        waitAction().shouldNotBeAnimated(accessibilityFilterButton);
    }
}