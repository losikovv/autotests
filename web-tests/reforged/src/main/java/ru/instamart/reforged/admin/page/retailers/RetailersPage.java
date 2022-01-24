package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import org.apache.commons.lang3.ArrayUtils;
import ru.instamart.kraken.util.TimeUtil;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.component.ElementCollection;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

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

    @Step("Нажать на магазин {0}")
    public void clickOnStore(final String store) {
        storesInTable.clickOnElementWithText(store);
    }

    @Step("Нажать на адрес {0}")
    public void clickOnAddress(final String store) {
        addressesInTable.clickOnElementWithText(store);
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

    @Step("Получаем строковый массив дат создания магазинов")
    public String[] returnStringArrayDates() {
        String[] str = new String[addressDatesInTable.elementCount()];
        for (int i = 0; i < str.length; i++) {
            str[i] = addressDatesInTable.getElementText(i);
        }
        return str;
    }

    @Step("Конвертируем строковый массив дат создания магазинов в даты")
    public Date[] convertStringArrayDatesToDate() throws ParseException {
        Date[] dates = new Date[addressDatesInTable.elementCount()];
        String[] str =  returnStringArrayDates();
        for (int i = 0; i < dates.length; i++) {
            dates[i] = TimeUtil.returnDateFromString(str[i]);
        }
        return dates;
    }

    @Override
    public String pageUrl() {
        return "retailers";
    }
}
