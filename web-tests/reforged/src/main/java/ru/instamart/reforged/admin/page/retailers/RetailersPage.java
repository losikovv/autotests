package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.apache.commons.lang3.ArrayUtils;
import org.testng.Assert;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.component.ElementCollection;

import java.util.Arrays;

public final class RetailersPage implements AdminPage, RetailersPageCheck {

    @Step("Вернуть количество ретейлеров на странице ретейлеров")
    public Integer retailerQuantityReturn() {
        return retailersInTable.elementCount();
    }

    @Step("Нажать на ретейлера {0}")
    public void clickOnRetailer(final String retailer) {
        retailersInTable.clickOnElementWithText(retailer);
    }

    @Step("Нажать на плюс у ретейлера {0}")
    public void clickOnPlusForRetailer(final String retailer) {
        retailerPlusIconInTable.click(retailer);
    }

    @Step("Получаем строковый массив текста элементов из коллекции элементов адресов")
    public String[] returnStringArrayFromAddressCollection(final ElementCollection elcol) {
        String[] strings = new String[elcol.elementCount()];
        for (int i = 0; i < strings.length; i++) strings[i] = elcol.getElementText(i);
        return strings;
    }

    @Step("Получаем отсортированный массив количества адресов для магазинов")
    public Integer[] sortStoreArray(final Integer[] array) {
        Integer[] clone = Arrays.copyOf(array, array.length);
        Arrays.sort(clone);
        ArrayUtils.reverse(clone);
        return clone;
    }

    @Override
    public String pageUrl() {
        return "retailers";
    }
}
