package ru.instamart.reforged.admin.page.orders;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.admin.AdminPage;

public final class OrdersPage implements AdminPage, OrdersCheck {

    @Step("Кликаем в поле 'Номер заказа'")
    public void clickShipmentNumberInput() {
        shipmentNumber.click();
    }

    @Step("Вводим в поле 'Номер заказа': {number}")
    public void fillShipmentNumber(final String number) {
        shipmentNumber.fill(number);
    }

    @Step("Кликаем в поле 'Статус заказа'")
    public void clickShipmentStatusFilterSelector() {
        shipmentStatusSelector.click();
    }

    @Step("Добавляем фильтр 'Статус заказа': '{statusText}'")
    public void addStatusFilterItem(final String statusText) {
        shipmentStatusSelector.clickItemInDropdownByNameExactly(statusText);
    }

    @Step("Удаляем выбранный фильтр 'Статус заказа': {statusText}")
    public void removeShipmentStatusFilterItem(final String statusText) {
        shipmentStatusSelector.removeSelectedItemFromInputByName(statusText);
    }

    @Step("Очищаем фильтры 'Статус заказа'")
    public void clearShipmentStatusFilters() {
        shipmentStatusSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Создание заказа - Начальная дата'")
    public void clickShipmentCreateDateStart() {
        shipmentCreateDateStart.click();
    }

    @Step("Кликаем в поле 'Создание заказа - Конечная дата'")
    public void clickShipmentCreateDateEnd() {
        shipmentCreateDateEnd.click();
    }

    @Step("Кликаем в поле 'Доставка - Начальная дата'")
    public void clickShipmentDeliveryDateStart() {
        shipmentDeliveryDateStart.click();
    }

    @Step("Кликаем в поле 'Доставка - Конечная дата'")
    public void clickShipmentDeliveryDateEnd() {
        shipmentDeliveryDateEnd.click();
    }

    @Step("Кликаем в поле 'Платформа'")
    public void clickPlatformFilterSelector() {
        platformSelector.click();
    }

    @Step("Добавляем фильтр 'Платформа': '{platformName}'")
    public void addPlatformFilterItem(final String platformName) {
        platformSelector.clickItemInDropdownByNameExactly(platformName);
    }

    @Step("Удаляем выбранный фильтр 'Платформа': {platformName}")
    public void removePlatformFilterItem(final String platformName) {
        platformSelector.removeSelectedItemFromInputByName(platformName);
    }

    @Step("Очищаем фильтры 'Платформа'")
    public void clearPlatformFilters() {
        platformSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Ритейлер'")
    public void clickRetailerFilterSelector() {
        retailerSelector.click();
    }

    @Step("Добавляем фильтр 'Ритейлер': '{retailerName}'")
    public void addRetailerFilterItem(final String retailerName) {
        retailerSelector.clickItemInDropdownByNameExactly(retailerName);
    }

    @Step("Удаляем выбранный фильтр 'Ритейлер': {retailerName}")
    public void removeRetailerFilterItem(final String retailerName) {
        retailerSelector.removeSelectedItemFromInputByName(retailerName);
    }

    @Step("Очищаем фильтры 'Ритейлер'")
    public void clearRetailerFilters() {
        retailerSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Базовый магазин'")
    public void clickBasicStoreFilterSelector() {
        basicStoreSelector.click();
    }

    @Step("Добавляем фильтр 'Базовый магазин': '{basicStoreName}'")
    public void addBasicStoreFilterItem(final String basicStoreName) {
        basicStoreSelector.clickItemInDropdownByNameExactly(basicStoreName);
    }

    @Step("Удаляем выбранный фильтр 'Базовый магазин': {basicStoreName}")
    public void removeBasicStoreFilterItem(final String basicStoreName) {
        basicStoreSelector.removeSelectedItemFromInputByName(basicStoreName);
    }

    @Step("Очищаем фильтры 'Базовый магазин'")
    public void clearBasicStoreFilters() {
        basicStoreSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Магазин'")
    public void clickStoreFilterSelector() {
        storeSelector.click();
    }

    @Step("Добавляем фильтр 'Магазин': '{storeName}'")
    public void addStoreFilterItem(final String storeName) {
        storeSelector.clickItemInDropdownByName(storeName);
    }

    @Step("Удаляем выбранный фильтр 'Магазин': {storeName}")
    public void removeStoreFilterItem(final String storeName) {
        storeSelector.removeSelectedItemFromInputByName(storeName);
    }

    @Step("Очищаем фильтры 'Магазин'")
    public void clearStoreFilters() {
        storeSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Способ оплаты'")
    public void clickPaymentMethodFilterSelector() {
        paymentMethodSelector.click();
    }

    @Step("Добавляем фильтр 'Способ оплаты': '{paymentMethod}'")
    public void addPaymentMethodFilterItem(final String paymentMethod) {
        paymentMethodSelector.clickItemInDropdownByNameExactly(paymentMethod);
    }

    @Step("Удаляем выбранный фильтр 'Способ оплаты': {paymentMethod}")
    public void removePaymentMethodFilterItem(final String paymentMethod) {
        paymentMethodSelector.removeSelectedItemFromInputByName(paymentMethod);
    }

    @Step("Очищаем фильтры 'Способ оплаты'")
    public void clearPaymentMethodFilters() {
        paymentMethodSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Статус оплаты'")
    public void clickPaymentStatusFilterSelector() {
        paymentStatusSelector.click();
    }

    @Step("Добавляем фильтр 'Статус оплаты': '{paymentStatus}'")
    public void addPaymentStatusFilterItem(final String paymentStatus) {
        paymentStatusSelector.clickItemInDropdownByNameExactly(paymentStatus);
    }

    @Step("Удаляем выбранный фильтр 'Статус оплаты': {paymentStatus}")
    public void removePaymentStatusFilterItem(final String paymentStatus) {
        paymentStatusSelector.removeSelectedItemFromInputByName(paymentStatus);
    }

    @Step("Очищаем фильтры 'Статус оплаты'")
    public void clearPaymentStatusFilters() {
        paymentStatusSelector.clearSelectedInInput();
    }

    @Step("Кликаем в поле 'Сборщик'")
    public void clickCollectorFilterSelector() {
        collector.click();
    }

    @Step("Кликаем в поле 'Курьер'")
    public void clickCourierFilterSelector() {
        courier.click();
    }

    @Step("Кликаем в поле 'Регион'")
    public void clickRegionFilterSelector() {
        region.click();
    }

    @Step("Кликаем в поле 'Быстрые фильтры'")
    public void clickQuickFiltersSelector() {
        quickFilters.click();
    }

    @Step("Получаем количество найденных заказов")
    public int getShipmentsCountFromTableHeader() {
        return StringUtil.extractNumberFromString(searchResultCount.getText());
    }

    @Step("Нажимаем кнопку 'Очистить'")
    public void resetFilters() {
        resetFilters.click();
    }

    @Step("Нажимаем кнопку 'Применить фильтры'")
    public void applyFilters() {
        applyFilters.click();
    }

    @Override
    public String pageUrl() {
        return "orders";
    }
}
