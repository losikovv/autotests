package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import static ru.instamart.reforged.admin.AdminRout.retailers;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RetailersPageCheck extends Check, RetailersPageElements {

    @Step("Проверяем, что кнопка 'Добавить ретейлера' отображается")
    default void checkAddNewRetailerButtonVisible() {
        waitAction().shouldBeVisible(addNewRetailerButton);
    }

    @Step("Сравнение количества ретейлеров с количеством надписей о доступности/недоступности")
    default void retailerAccessibilityCompare(final int quantity) {
        Assert.assertEquals(retailersAccessibilityInTable.elementCount(), quantity);
    }

    @Step("Сравнение количества ретейлеров с количеством дат создания ретейлеров")
    default void retailerCreateDateCompare(final int quantity) {
        Assert.assertEquals(retailersCreateDateInTable.elementCount(), quantity);
    }

    @Step("Проверка недоступности ретейлера")
    default void checkRetailerInactive(final String retailer) {
        waitAction().shouldBeVisible(retailerInaccessibilityInTable, retailer);
    }

    @Step("Проверка доступности ретейлера")
    default void checkRetailerActive(final String retailer) {
        waitAction().shouldBeVisible(retailerAccessibilityInTable, retailer);
    }

    @Step("Проверяем, что сортировка городов магазинов отображается корректно")
    default void checkStoreNumbersSortCorrect() {
        Assert.assertTrue(checkStoreSort());
    }

    @Step("Проверяем, что сортировка городов магазинов по количеству отображается корректно")
    default Boolean checkStoreSort() {
        Integer[] array = StringUtil.returnCitiesInTableNumbersArray(
                retailers().returnStringArrayFromAddressCollection(storesInTable));
        return Arrays.equals(array, retailers().sortStoreArray(array));
    }

    @Step("Проверяем корректность сортировки адресов магазинов по дате создания")
    default Boolean checkDateSort() throws ParseException {
        Date[] original = retailers().convertStringArrayDatesToDate();
        Date[] clone = Arrays.copyOf(original, original.length);
        Arrays.sort(clone);
        return Arrays.equals(original, clone);
    }

    @Step("Проверяем корректность сортировки адресов магазинов по дате создания")
    default void checkDateSortCorrect() throws ParseException {
        Assert.assertTrue(checkDateSort());
    }

    @Step("Проверяем, что сортировка городов магазинов отображается корректно")
    default void checkStoreNameSortCorrect() {
        Assert.assertTrue(checkStoreNameSort());
    }

    @Step("Проверяем, что сортировка городов магазинов по имени отображается корректно")
    default Boolean checkStoreNameSort() {
        Integer[] array = StringUtil.returnCitiesInTableNumbersArray(
                retailers().returnStringArrayFromAddressCollection(storesInTable));
        String[] strings = retailers().returnStringArrayFromAddressCollection(storesInTable);

        return retailers().checkIfStoreAlphabeticallySorted(strings, array);
    }

    @Step("Получаем отсортированный массив количества адресов для магазинов")
    default Boolean checkIfStoreAlphabeticallySorted(final String[] strings, final Integer[] array) {
        char str1;
        char str2;
        if (array.length > 2) {
            for (int i = 1; i < array.length; i++) {
                if (array[i] == array[i - 1]) {
                    str1 = strings[i].charAt(0);
                    str2 = strings[i - 1].charAt(0);
                    Assert.assertTrue(str1 >= str2);
                }
            }
        }
        return true;
    }
}