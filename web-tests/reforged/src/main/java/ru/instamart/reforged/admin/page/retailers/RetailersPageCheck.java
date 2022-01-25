package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Step("Проверяем, что сортировка городов магазинов по количеству отображается корректно")
    default void checkStoreSortViaNumbersCorrect() {
        Integer[] array = StringUtil.returnNumbersOfCitiesInTableArray(storesInTable.getTextFromAllElements());
        Integer[] arrayClone = Arrays.copyOf(array, array.length);

        Assert.assertTrue(Arrays.equals(array, arrayClone));
    }

    @Step("Проверяем корректность сортировки адресов магазинов по дате создания")
    default void checkDateSortCorrect(List storeList) {
        Date[] original = new Date[storeList.size()];
        original = (Date[]) storeList.toArray(original);
        Date[] clone = Arrays.copyOf(original, original.length);
        Arrays.sort(clone);
        Assert.assertTrue(Arrays.equals(original, clone));
    }

    @Step("Проверяем, что список магазинов отсортирован по алфавиту")
    default void checkIfStoreAlphabeticallySorted() {
        char str1;
        char str2;
        List<String> list = storesInTable.getTextFromAllElements();
        var array = StringUtil.returnNumbersOfCitiesInTableArray(list);

        if (array.length > 2) {
            for (int i = 1; i < array.length; i++) {
                if (array[i] == array[i - 1]) {
                    str1 = list.get(i).charAt(0);
                    str2 = list.get(i - 1).charAt(0);
                    Assert.assertTrue(str1 >= str2);
                }
            }
        }
    }
}