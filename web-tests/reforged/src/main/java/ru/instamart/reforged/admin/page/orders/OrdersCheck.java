package ru.instamart.reforged.admin.page.orders;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.List;

public interface OrdersCheck extends Check, OrdersElement {

    @Step("Проверяем, что в таблице пусто")
    default void checkShipmentListEmpty() {
        Kraken.waitAction().shouldBeVisible(emptyShipmentsListLabel);
    }

    @Step("Проверяем, что в таблице отображаются заказы")
    default void checkShipmentListNotEmpty() {
        Kraken.waitAction().shouldNotBeVisible(emptyShipmentsListLabel);
    }

    @Step("Проверяем, что подгрузка данных завершена")
    default void checkLoadingLabelNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(shipmentsLoadingLabel);
    }

    @Step("Проверяем отображаемое количество найденных заказов в заголовке таблицы равно: '{expectedShipmentsCount}'")
    default void checkShipmentsCountInTableHeader(final int expectedShipmentsCount) {
        Assert.assertEquals(StringUtil.extractNumberFromString(searchResultCount.getText()), expectedShipmentsCount, "Количество найденных заказов, отображаемое в заголовке таблицы не соответствует ожидаемому");
    }

    @Step("Проверяем, что количество записей в таблице равно: '{expectedShipmentsCount}'")
    default void checkItemsCountInTable(final int expectedShipmentsCount) {
        Assert.assertEquals(tableComponent.getRowsCount(), expectedShipmentsCount, "Количество записей в таблице отличается от ожидаемого");
    }

    @Step("Проверяем, что все фильтры очищены")
    default void checkAllFilterInputsIsEmpty() {
        krakenAssert.assertTrue(shipmentNumber.getValue().isEmpty(), "Фильтр 'Номер заказа' не пуст");
        krakenAssert.assertTrue(shipmentCreateDateStart.getValue().isEmpty(), "Фильтр 'Создание заказа - Начальная дата' не пуст");
        krakenAssert.assertTrue(shipmentCreateDateEnd.getValue().isEmpty(), "Фильтр 'Создание заказа - Конечная дата' не пуст");
        krakenAssert.assertTrue(shipmentDeliveryDateStart.getValue().isEmpty(), "Фильтр 'Доставка заказа - Начальная дата' не пуст");
        krakenAssert.assertTrue(shipmentDeliveryDateEnd.getValue().isEmpty(), "Фильтр 'Доставка заказа - Конечная дата' не пуст");
        krakenAssert.assertTrue(totalWeightStart.getValue().isEmpty(), "Фильтр 'Вес заказа - От' не пуст");
        krakenAssert.assertTrue(totalWeightEnd.getValue().isEmpty(), "Фильтр 'Вес заказа - До' не пуст");
        krakenAssert.assertTrue(itemsCountStart.getValue().isEmpty(), "Фильтр 'Кол-во позиций - От' не пуст");
        krakenAssert.assertTrue(itemsCountEnd.getValue().isEmpty(), "Фильтр 'Кол-во позиций - До' не пуст");
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что загружена таблица с заказами")
    default void checkOrdersLoaded() {
        Kraken.waitAction().shouldBeVisible(shipment);
    }

    @Step("Проверяем, что фильтры 'Статус заказа' не выбраны")
    default void checkShipmentStatusFiltersNotSelected() {
        shipmentStatusSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Платформа' не выбраны")
    default void checkPlatformFiltersNotSelected() {
        platformSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Ритейлер' не выбраны")
    default void checkRetailerFiltersNotSelected() {
        retailerSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Базовый магазин' не выбраны")
    default void checkBasicStoreFiltersNotSelected() {
        basicStoreSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Магазин' не выбраны")
    default void checkStoreFiltersNotSelected() {
        storeSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Способ оплаты' не выбраны")
    default void checkPaymentMethodFiltersNotSelected() {
        paymentMethodSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Статус оплаты' не выбраны")
    default void checkPaymentStatusFiltersNotSelected() {
        paymentStatusSelector.checkSelectedItemsInInputNotVisible();
    }

    @Step("Проверяем, что фильтры 'Регион' не выбраны")
    default void checkRegionFiltersNotSelected() {
        Kraken.waitAction().shouldNotBeVisible(selectedRegionFilters);
    }

    @Step("Проверяем, что 'Быстрые фильтры' не выбраны")
    default void checkQuickFiltersNotSelected() {
        Kraken.waitAction().shouldNotBeVisible(selectedQuickFilters);
    }

    @Step("Проверяем, что отображается кнопка 'Сбросить'")
    default void checkResetFiltersButtonVisible() {
        Kraken.waitAction().shouldBeVisible(resetFilters);
    }

    @Step("Проверяем, что отображается кнопка 'Применить фильтры'")
    default void checkApplyFiltersButtonVisible() {
        Kraken.waitAction().shouldBeVisible(applyFilters);
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Статус заказа'")
    default void checkShipmentStatusDropdownItemsVisible() {
        shipmentStatusSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Статус заказа'")
    default void checkShipmentStatusDropdownItemsNotVisible() {
        shipmentStatusSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Статус заказа' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkShipmentStatusSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(shipmentStatusSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Статус заказа' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Платформа'")
    default void checkPlatformDropdownItemsVisible() {
        platformSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Платформа'")
    default void checkPlatformDropdownItemsNotVisible() {
        platformSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Платформа' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkPlatformSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(platformSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Платформа' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Ритейлер'")
    default void checkRetailerDropdownItemsVisible() {
        retailerSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Ритейлер'")
    default void checkRetailerDropdownItemsNotVisible() {
        retailerSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Ритейлер' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkRetailerSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(retailerSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Ритейлер' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Базовый магазин'")
    default void checkBasicStoreDropdownItemsVisible() {
        basicStoreSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Базовый магазин'")
    default void checkBasicStoreDropdownItemsNotVisible() {
        basicStoreSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Базовый магазин' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkBasicStoreSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(basicStoreSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Базовый магазин' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Магазин'")
    default void checkStoreDropdownItemsVisible() {
        storeSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Магазин'")
    default void checkStoreDropdownItemsNotVisible() {
        storeSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Магазин' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkStoreSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(storeSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Магазин' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Способ оплаты'")
    default void checkPaymentMethodDropdownItemsVisible() {
        paymentMethodSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Способ оплаты'")
    default void checkPaymentMethodDropdownItemsNotVisible() {
        paymentMethodSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Способ оплаты' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkPaymentMethodSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(paymentMethodSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Способ оплаты' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Статус оплаты'")
    default void checkPaymentStatusDropdownItemsVisible() {
        paymentStatusSelector.checkDropdownVisible();
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Статус оплаты'")
    default void checkPaymentStatusDropdownItemsNotVisible() {
        paymentStatusSelector.checkDropdownNotVisible();
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Статус оплаты' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkPaymentStatusSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(paymentStatusSelector.getAllSelectedItemsInInput(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Статус оплаты' не соответствует ожидаемому");
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Сборщик'")
    default void checkCollectorDropdownItemsVisible() {
        Kraken.waitAction().shouldBeVisible(collectorDropdownList);
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Сборщик'")
    default void checkCollectorDropdownItemsNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(collectorDropdownList);
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Курьер'")
    default void checkCourierDropdownItemsVisible() {
        Kraken.waitAction().shouldBeVisible(courierDropdownList);
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Курьер'")
    default void checkCourierDropdownItemsNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(courierDropdownList);
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Регион'")
    default void checkRegionDropdownItemsVisible() {
        Kraken.waitAction().shouldBeVisible(regionDropdownList);
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Регион'")
    default void checkRegionDropdownItemsNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(regionDropdownList);
    }

    @Step("Проверяем, что отображается список выпадающих элементов селектора 'Быстрые фильтры'")
    default void checkQuickFiltersDropdownItemsVisible() {
        Kraken.waitAction().shouldBeVisible(quickFiltersDropdownList);
    }

    @Step("Проверяем, что не отображается список выпадающих элементов селектора 'Быстрые фильтры'")
    default void checkQuickFiltersDropdownItemsNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(quickFiltersDropdownList);
    }

    @Step("Проверяем, что отображается список выпадающий элемент '{itemName}' селектора")
    default void checkSelectorItemVisible(final String itemName) {
        Kraken.waitAction().shouldBeVisible(selectOptionByName, itemName);
    }

    @Step("Проверяем, что отображается виджет выбора даты и времени создания заказа")
    default void checkCreateDateTimePickerVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentCreateDateTimePicker);
    }

    @Step("Проверяем, что не отображается виджет выбора даты и времени создания заказа")
    default void checkCreateDateTimePickerNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(shipmentCreateDateTimePicker);
    }

    @Step("Проверяем, что отображается виджет выбора даты и времени доставки заказа")
    default void checkDeliveryDateTimePickerVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentDeliveryDateTimePicker);
    }

    @Step("Проверяем, что не отображается виджет выбора даты и времени доставки заказа")
    default void checkDeliveryDateTimePickerNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(shipmentDeliveryDateTimePicker);
    }

    @Step("Проверяем, что все отфильтрованные заказы содержат в номере доставки: {expectedValue}")
    default void checkAllShipmentNumbersContains(final String expectedValue) {
        tableComponent.getAllShipmentNumbersList()
                .forEach(shipmentNumber -> {
                    krakenAssert.assertTrue(
                            shipmentNumber.contains(expectedValue),
                            String.format("Номер доставки %s не содержит %s", shipmentNumber, expectedValue)
                    );
                });
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что список значений в выпадающем списке селектора 'Статус заказа' соответствует ожидаемому: {expectedSelectorValuesList}")
    default void checkShipmentStatusSelectorListEquals(final List<String> expectedSelectorValuesList) {
        Assert.assertEquals(shipmentStatusSelector.getAllValuesFromDropdown(), expectedSelectorValuesList, "Список значений селектора 'Статус заказа' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список значений в выпадающем списке селектора 'Статус оплаты' соответствует ожидаемому: {expectedSelectorValuesList}")
    default void checkPaymentStatusSelectorListEquals(final List<String> expectedSelectorValuesList) {
        Assert.assertEquals(paymentStatusSelector.getAllValuesFromDropdown(), expectedSelectorValuesList, "Список значений селектора 'Статус оплаты' не соответствует ожидаемому");
    }

    @Step("Проверяем, что все отфильтрованные заказы имеют статус: {expectedValues}")
    default void checkAllShipmentInTableHasStatusIn(final List<String> expectedValues) {
        var allShipmentStatusesInTable = tableComponent.getAllShipmentStatusesList();
        for (int i = 0; i < allShipmentStatusesInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentStatusesInTable.get(i)),
                    String.format("Статус заказа номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentStatusesInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что все отфильтрованные заказы имеют платфому: {expectedValues}")
    default void checkAllShipmentInTableHasPlatformIn(final List<String> expectedValues) {
        var allShipmentPlatformsInTable = tableComponent.getAllPlatformsList();
        for (int i = 0; i < allShipmentPlatformsInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentPlatformsInTable.get(i)),
                    String.format("Платформа заказа номер '%s' : '%s' не найдена среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentPlatformsInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что все отфильтрованные заказы сделаны у ритейлеров: {expectedValues}")
    default void checkAllShipmentInTableHasRetailerIn(final List<String> expectedValues) {
        var allShipmentRetailersInTable = tableComponent.getAllRetailersList();
        for (int i = 0; i < allShipmentRetailersInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentRetailersInTable.get(i)),
                    String.format("Ритейлер в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentRetailersInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что для всех отфильтрованных заказов базовый магазин один из: {expectedValues}")
    default void checkAllShipmentInTableHasBasicStoreIn(final List<String> expectedValues) {
        var allShipmentBasicStoresInTable = tableComponent.getAllBasicStoresList();
        for (int i = 0; i < allShipmentBasicStoresInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentBasicStoresInTable.get(i)),
                    String.format("Базовый магазин в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentBasicStoresInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что для всех отфильтрованных заказов Способ оплаты один из: {expectedValues}")
    default void checkAllShipmentInTableHasPaymentMethodIn(final List<String> expectedValues) {
        var allShipmentPaymentMethodsInTable = tableComponent.getAllPaymentMethodsList();
        for (int i = 0; i < allShipmentPaymentMethodsInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentPaymentMethodsInTable.get(i)),
                    String.format("Способ оплаты в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentPaymentMethodsInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что для всех отфильтрованных заказов Статус оплаты один из: {expectedValues}")
    default void checkAllShipmentInTableHasPaymentStatusIn(final List<String> expectedValues) {
        var allShipmentPaymentStatusesInTable = tableComponent.getAllPaymentStatusesList();
        for (int i = 0; i < allShipmentPaymentStatusesInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentPaymentStatusesInTable.get(i)),
                    String.format("Статус оплаты в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentPaymentStatusesInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }
}
