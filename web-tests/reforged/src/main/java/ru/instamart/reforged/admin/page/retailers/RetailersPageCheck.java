package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.kraken.util.TimeUtil;
import ru.instamart.reforged.core.Check;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailersPageCheck extends Check, RetailersPageElements {

    @Step("Проверяем, что кнопка 'Добавить ретейлера' отображается")
    default void checkAddNewRetailerButtonVisible() {
        waitAction().shouldBeVisible(addNewRetailerButton);
    }

    @Step("Сравнение количества ретейлеров с количеством надписей о доступности/недоступности")
    default void retailerAccessibilityCompare(final int quantity) {
        Assert.assertEquals(retailersAccessibilityInTable.elementCount(), quantity,
                "Количество ретейлеров не совпадает с количеством надписей о доступности/недоступности");
    }

    @Step("Сравнение количества ретейлеров с количеством дат создания ретейлеров")
    default void retailerCreateDateCompare(final int quantity) {
        Assert.assertEquals(retailersCreateDateInTable.elementCount(), quantity,
                "Количество ретейлеров не совпадает с количеством дат создания");
    }

    @Step("Проверка недоступности ретейлера")
    default void checkRetailerInactive(final String retailer) {
        waitAction().shouldBeVisible(retailerInaccessibilityInTable, retailer);
    }

    @Step("Проверка доступности ретейлера")
    default void checkRetailerActive(final String retailer) {
        waitAction().shouldBeVisible(retailerAccessibilityInTable, retailer);
    }

    @Step("Проверяем, что сортировка городов магазинов по количеству отображается корректно")
    default void checkStoreSortViaNumbersCorrect() {
        List<Integer> numbersOfCities = storesInTable.getTextFromAllElements().stream()
                .map(StringUtil::parseNumberCitiesFromString).collect(Collectors.toList());
        Assert.assertEquals(numbersOfCities, numbersOfCities.stream()
                .sorted(Collections.reverseOrder()).collect(Collectors.toList()),
                "Города по количеству магазинов отсортированы некорректно");
    }

    @Step("Проверяем, что сортировка городов магазинов по дате создания отображается корректно")
    default void checkDateSortCorrect() {
        List<ZonedDateTime> datesList = addressDatesInTable.getTextFromAllElements().stream()
                .map(TimeUtil::convertStringToDate).collect(Collectors.toList());
        Assert.assertEquals(datesList, datesList.stream().sorted().collect(Collectors.toList()),
                "Города по дате создания отсортированы некорректно");
    }

    @Step("Проверяем, что список магазинов отсортирован по алфавиту")
    default void checkIfStoreAlphabeticallySorted() {
        List<String> listCities = storesInTable.getTextFromAllElements();
        List<Integer> listCitiesNumbers = listCities.stream().map(StringUtil::parseNumberCitiesFromString)
                .collect(Collectors.toList());

        Map<String, Integer> mapMerged = CollectionUtil.mergeIntoMap(listCities, listCitiesNumbers);
        Map<String, Integer> mapNotUnique = CollectionUtil.removeUniqueValues(mapMerged);
        Map<String, Integer> mapSorted =  CollectionUtil.reverseSortMapByValue(mapNotUnique);

        Assert.assertEquals(mapNotUnique, mapSorted, "Список городов отсортирован некорректно");
    }
}