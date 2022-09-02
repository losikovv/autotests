package ru.instamart.reforged.admin.page.orders;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.List;
import java.util.Set;

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
        Assert.assertTrue(shipmentStatusSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Статус заказа' не пуст");
    }

    @Step("Проверяем, что фильтры 'Платформа' не выбраны")
    default void checkPlatformFiltersNotSelected() {
        Assert.assertTrue(platformSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Платформа' не пуст");
    }

    @Step("Проверяем, что фильтры 'Ритейлер' не выбраны")
    default void checkRetailerFiltersNotSelected() {
        Assert.assertTrue(retailerSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Ритейлер' не пуст");
    }

    @Step("Проверяем, что фильтры 'Базовый магазин' не выбраны")
    default void checkBasicStoreFiltersNotSelected() {
        Assert.assertTrue(basicStoreSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Базовый магазин' не пуст");
    }

    @Step("Проверяем, что фильтры 'Магазин' не выбраны")
    default void checkStoreFiltersNotSelected() {
        Assert.assertTrue(storeSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Магазин' не пуст");
    }

    @Step("Проверяем, что фильтры 'Способ оплаты' не выбраны")
    default void checkPaymentMethodFiltersNotSelected() {
        Assert.assertTrue(paymentMethodSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Способ оплаты' не пуст");
    }

    @Step("Проверяем, что фильтры 'Статус оплаты' не выбраны")
    default void checkPaymentStatusFiltersNotSelected() {
        Assert.assertTrue(paymentStatusSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Статус оплаты' не пуст");
    }

    @Step("Проверяем, что фильтры 'Регион' не выбраны")
    default void checkRegionFiltersNotSelected() {
        Assert.assertTrue(regionSelector.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Регион' не пуст");
    }

    @Step("Проверяем, что 'Быстрые фильтры' не выбраны")
    default void checkQuickFiltersNotSelected() {
        Assert.assertTrue(quickFilters.getAllSelectedName().isEmpty(), "Список выбранных фильтров 'Быстрые фильтры' не пуст");
    }

    @Step("Проверяем, что отображается кнопка 'Сбросить'")
    default void checkResetFiltersButtonVisible() {
        Kraken.waitAction().shouldBeVisible(resetFilters);
    }

    @Step("Проверяем, что отображается кнопка 'Применить фильтры'")
    default void checkApplyFiltersButtonVisible() {
        Kraken.waitAction().shouldBeVisible(applyFilters);
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Статус заказа' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkShipmentStatusSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(shipmentStatusSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Статус заказа' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Платформа' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkPlatformSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(platformSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Платформа' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Ритейлер' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkRetailerSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(retailerSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Ритейлер' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Базовый магазин' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkBasicStoreSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(basicStoreSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Базовый магазин' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Магазин' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkStoreSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(storeSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Магазин' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Способ оплаты' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkPaymentMethodSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(paymentMethodSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Способ оплаты' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Статус оплаты' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkPaymentStatusSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(paymentStatusSelector.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Статус оплаты' не соответствует ожидаемому");
    }

    @Step("Проверяем, что список выбранных фильтров в селекторе 'Быстрые фильтры' соответствует ожидаемому: '{expectedFiltersList}'")
    default void checkQuickFiltersSelectedFilterList(final List<String> expectedFiltersList) {
        Assert.assertEquals(quickFilters.getAllSelectedName(), expectedFiltersList, "Список выбранных фильтров в селекторе 'Быстрые фильтры' не соответствует ожидаемому");
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

    //До диспетчеризации заказ имеет один статус
    @Step("Проверяем, что все отфильтрованные заказы имеют статус: {expectedValues}")
    default void checkAllShipmentInTableHasSingleStatusIn(final Set<String> expectedValues) {
        tableComponent.getAllShipmentStatusesList().forEach(status -> {
            krakenAssert.assertTrue(
                    expectedValues.contains(status),
                    String.format("Статус заказа '%s' не найден среди ожидаемых: '%s'", status, expectedValues)
            );
        });
        krakenAssert.assertAll();
    }

    //После диспетчеризации заказ имеет 2 статуса: Сборки и Доставки
    @Step("Проверяем, что все отфильтрованные заказы имеют Статус сборки: {expectedValues}")
    default void checkAllShipmentInTableHasCollectingStatusIn(final Set<String> expectedValues) {
        tableComponent.getAllCollectingShipmentStatusesList().forEach(status -> {
            krakenAssert.assertTrue(
                    expectedValues.contains(status),
                    String.format("Статус сборки заказа '%s' не найден среди ожидаемых: '%s'", status, expectedValues)
            );
        });
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что все отфильтрованные заказы имеют Статус доставки: {expectedValues}")
    default void checkAllShipmentInTableHasShippingStatusIn(final Set<String> expectedValues) {
        tableComponent.getAllDeliveryShipmentStatusesList().forEach(status -> {
            krakenAssert.assertTrue(
                    expectedValues.contains(status),
                    String.format("Статус доставки заказа '%s' не найден среди ожидаемых: '%s'", status, expectedValues)
            );
        });
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что все отфильтрованные заказы имеют платфому: {expectedValues}")
    default void checkAllShipmentInTableHasPlatformIn(final Set<String> expectedValues) {
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
    default void checkAllShipmentInTableHasRetailerIn(final Set<String> expectedValues) {
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
    default void checkAllShipmentInTableHasBasicStoreIn(final Set<String> expectedValues) {
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
    default void checkAllShipmentInTableHasPaymentMethodIn(final Set<String> expectedValues) {
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
    default void checkAllShipmentInTableHasPaymentStatusIn(final Set<String> expectedValues) {
        var allShipmentPaymentStatusesInTable = tableComponent.getAllPaymentStatusesList();
        for (int i = 0; i < allShipmentPaymentStatusesInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allShipmentPaymentStatusesInTable.get(i)),
                    String.format("Статус оплаты в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allShipmentPaymentStatusesInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что для всех отфильтрованных заказов Заказчик один из: {expectedValues}")
    default void checkAllShipmentInTableHasCustomerIn(final Set<String> expectedValues) {
        var allCustomerNamesInTable = tableComponent.getAllCustomerNames();
        for (int i = 0; i < allCustomerNamesInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allCustomerNamesInTable.get(i)),
                    String.format("Заказчик в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allCustomerNamesInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что для всех отфильтрованных заказов Сборщик один из: {expectedValues}")
    default void checkAllShipmentInTableHasCollectorIn(final Set<String> expectedValues) {
        var allCollectorsInTable = tableComponent.getAllCollectorsList();
        for (int i = 0; i < allCollectorsInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allCollectorsInTable.get(i)),
                    String.format("Назначение - Сборщик в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allCollectorsInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что для всех отфильтрованных заказов Курьер один из: {expectedValues}")
    default void checkAllShipmentInTableHasCourierIn(final Set<String> expectedValues) {
        var allCouriersInTable = tableComponent.getAllCouriersList();
        for (int i = 0; i < allCouriersInTable.size(); i++) {
            krakenAssert.assertTrue(
                    expectedValues.contains(allCouriersInTable.get(i)),
                    String.format("Назначение - Курьер в заказе номер '%s' : '%s' не найден среди ожидаемых: '%s'", tableComponent.getShipmentNumber(i), allCouriersInTable.get(i), expectedValues)
            );
        }
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что отображается выпадающее меню доставки")
    default void checkShipmentDropdownMenuVisible() {
        Kraken.waitAction().shouldBeVisible(shipmentDropdownMenu);
    }

    @Step("Проверяем, что отображается модальное окно ручного назначения заказа")
    default void checkManualAssignmentModalVisible() {
        Kraken.waitAction().shouldBeVisible(manualAssignmentModal);
    }
}
