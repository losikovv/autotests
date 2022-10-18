package ru.instamart.reforged.admin.page.orders;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.core.page.Window;

public final class OrdersPage implements AdminPage, OrdersCheck, Window {

    @Step("Получаем произвольный номер заказа")
    public String getAnyShipmentNumber() {
        return tableComponent.getShipmentNumber(0);
    }

    @Step("Кликаем в поле 'Номер заказа'")
    public void clickShipmentNumberInput() {
        shipmentNumber.click();
    }

    @Step("Вводим в поле 'Номер заказа': {number}")
    public void fillShipmentNumber(final String number) {
        shipmentNumber.fill(number);
    }

    @Step("Добавляем фильтр 'Статус заказа': '{statusText}'")
    public void addStatusFilterItem(final String... statusText) {
        shipmentStatusSelector.fillAndSelect(statusText);
    }

    @Step("Удаляем выбранный фильтр 'Статус заказа': {statusText}")
    public void removeShipmentStatusFilterItem(final String statusText) {
        shipmentStatusSelector.removeItemByName(statusText);
    }

    @Step("Очищаем фильтры 'Статус заказа'")
    public void clearShipmentStatusFilters() {
        shipmentStatusSelector.removeAll();
    }

    @Step("Кликаем в поле 'Создание заказа - Начальная дата'")
    public void clickShipmentCreateDateStart() {
        shipmentCreateDateStart.click();
    }

    @Step("Заполнить поле 'Создание заказа - Начальная дата' датой '{date}'")
    //2022-09-12 06:35
    public void fillShipmentCreateDateStart(final String date) {
        shipmentCreateDateStart.fillField(date);
        shipmentCreateDateStart.getActions().sendKeys(Keys.ENTER);
    }

    @Step("Кликаем в поле 'Создание заказа - Конечная дата'")
    public void clickShipmentCreateDateEnd() {
        shipmentCreateDateEnd.click();
    }

    @Step("Заполнить поле 'Создание заказа - Конечная дата' датой '{date}'")
    //2022-09-12 06:35
    public void fillShipmentCreateDateEnd(final String date) {
        shipmentCreateDateEnd.fillField(date);
        shipmentCreateDateEnd.getActions().sendKeys(Keys.ENTER);
    }

    @Step("Кликаем в поле 'Доставка - Начальная дата'")
    public void clickShipmentDeliveryDateStart() {
        shipmentDeliveryDateStart.click();
    }

    @Step("Заполнить поле 'Доставка - Начальная дата' датой '{date}'")
    //2022-09-12 06:35
    public void fillShipmentDeliveryDateStart(final String date) {
        shipmentDeliveryDateStart.fillField(date);
        shipmentDeliveryDateStart.getActions().sendKeys(Keys.ENTER);
    }

    @Step("Кликаем в поле 'Доставка - Конечная дата'")
    public void clickShipmentDeliveryDateEnd() {
        shipmentDeliveryDateEnd.click();
    }

    @Step("Заполнить поле 'Доставка - Конечная дата' датой '{date}'")
    //2022-09-12 06:35
    public void fillShipmentDeliveryDateEnd(final String date) {
        shipmentDeliveryDateEnd.fillField(date);
        shipmentDeliveryDateEnd.getActions().sendKeys(Keys.ENTER);
    }

    @Step("Указываем 'Вес заказа' от {}")
    public void addOrderWeightFrom(final String from) {
        totalWeightStart.fillField(from);
    }

    @Step("Указываем 'Вес заказа' до {}")
    public void addOrderWeightTo(final String to) {
        totalWeightEnd.fillField(to);
    }

    @Step("Указываем 'Кол-во позиций' от {}")
    public void addOrderItemsFrom(final String from) {
        itemsCountStart.fillField(from);
    }

    @Step("Указываем 'Кол-во позиций' до {}")
    public void addOrderItemsTo(final String to) {
        itemsCountEnd.fillField(to);
    }

    @Step("Добавляем фильтр 'Платформа': '{platformName}'")
    public void addPlatformFilterItem(final String... platformName) {
        platformSelector.fillAndSelect(platformName);
    }

    @Step("Удаляем выбранный фильтр 'Платформа': {platformName}")
    public void removePlatformFilterItem(final String platformName) {
        platformSelector.removeItemByName(platformName);
    }

    @Step("Очищаем фильтры 'Платформа'")
    public void clearPlatformFilters() {
        platformSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Ритейлер': '{retailerName}'")
    public void addRetailerFilterItem(final String... retailerName) {
        retailerSelector.fillContains(retailerName);
    }

    @Step("Удаляем выбранный фильтр 'Ритейлер': {retailerName}")
    public void removeRetailerFilterItem(final String retailerName) {
        retailerSelector.removeItemByName(retailerName);
    }

    @Step("Очищаем фильтры 'Ритейлер'")
    public void clearRetailerFilters() {
        retailerSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Базовый магазин': '{basicStoreName}'")
    public void addBasicStoreFilterItem(final String... basicStoreName) {
        basicStoreSelector.fillContains(basicStoreName);
    }

    @Step("Удаляем выбранный фильтр 'Базовый магазин': {basicStoreName}")
    public void removeBasicStoreFilterItem(final String basicStoreName) {
        basicStoreSelector.removeItemByName(basicStoreName);
    }

    @Step("Очищаем фильтры 'Базовый магазин'")
    public void clearBasicStoreFilters() {
        basicStoreSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Магазин': '{storeName}'")
    public void addStoreFilterItem(final String... storeName) {
        storeSelector.fillContains(storeName);
    }

    @Step("Удаляем выбранный фильтр 'Магазин': {storeName}")
    public void removeStoreFilterItem(final String storeName) {
        storeSelector.removeItemByName(storeName);
    }

    @Step("Очищаем фильтры 'Магазин'")
    public void clearStoreFilters() {
        storeSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Способ оплаты': '{paymentMethod}'")
    public void addPaymentMethodFilterItem(final String... paymentMethod) {
        paymentMethodSelector.fillAndSelect(paymentMethod);
    }

    @Step("Удаляем выбранный фильтр 'Способ оплаты': {paymentMethod}")
    public void removePaymentMethodFilterItem(final String paymentMethod) {
        paymentMethodSelector.removeItemByName(paymentMethod);
    }

    @Step("Очищаем фильтры 'Способ оплаты'")
    public void clearPaymentMethodFilters() {
        paymentMethodSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Статус оплаты': '{paymentStatus}'")
    public void addPaymentStatusFilterItem(final String... paymentStatus) {
        paymentStatusSelector.fillAndSelect(paymentStatus);
    }

    @Step("Удаляем выбранный фильтр 'Статус оплаты': {paymentStatus}")
    public void removePaymentStatusFilterItem(final String paymentStatus) {
        paymentStatusSelector.removeItemByName(paymentStatus);
    }

    @Step("Очищаем фильтры 'Статус оплаты'")
    public void clearPaymentStatusFilters() {
        paymentStatusSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Промокод': '{code}'")
    public void addPromoCodeFilter(final String code) {
        promoCode.fillField(code);
    }

    @Step("Добавляем фильтр 'Быстрые фильтры: '{quickFilterName}'")
    public void addQuickFilterItem(final String quickFilterName) {
        quickFilters.selectWithoutSearch(quickFilterName);
    }

    @Step("Удаляем выбранный фильтр 'Быстрые фильтры': {quickFilterName}")
    public void removeQuickFilterItem(final String quickFilterName) {
        quickFilters.removeItemByName(quickFilterName);
    }

    @Step("Очищаем фильтры 'Быстрые фильтры'")
    public void clearQuickFilters() {
        quickFilters.removeAll();
    }

    @Step("Кликаем в поле 'Сборщик'")
    public void clickCollectorFilterSelector() {
        collector.click();
    }

    @Step("Кликаем в поле 'Курьер'")
    public void clickCourierFilterSelector() {
        courier.click();
    }

    @Step("Добавляем фильтр 'Статус сборки': '{collectingStatus}'")
    public void addCollectingStatusFilter(final String... collectingStatus) {
        collectingStatusSelector.fillAndSelect(collectingStatus);
    }

    @Step("Удаляем выбранный фильтр 'Статус сборки': {paymentMethod}")
    public void removeCollectingStatusFilterItem(final String collectingStatus) {
        collectingStatusSelector.removeItemByName(collectingStatus);
    }

    @Step("Очищаем фильтры 'Статус сборки'")
    public void clearCollectingStatusFilters() {
        collectingStatusSelector.removeAll();
    }

    @Step("Добавляем фильтр 'Статус доставки': '{deliveryStatus}'")
    public void addDeliveryStatusFilter(final String... deliveryStatus) {
        deliveryStatusSelector.fillAndSelect(deliveryStatus);
    }

    @Step("Удаляем выбранный фильтр 'Статус доставки': {deliveryStatus}")
    public void removeDeliveryStatusFilterItem(final String deliveryStatus) {
        deliveryStatusSelector.removeItemByName(deliveryStatus);
    }

    @Step("Очищаем фильтры 'Статус доставки'")
    public void clearDeliveryStatusFilters() {
        deliveryStatusSelector.removeAll();
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
        quickFiltersTitle.getComponent().click();
        applyFilters.click();
    }

    @Step("Получаем название ритейлера '{shipmentPosition}'-го заказа в таблице")
    public String getRetailerName(final int shipmentPosition) {
        return tableComponent.getRetailerName(shipmentPosition - 1);
    }

    @Step("Получаем название магазина '{shipmentPosition}'-го заказа в таблице")
    public String getStoreName(final int shipmentPosition) {
        return tableComponent.getStoreName(shipmentPosition - 1);
    }

    @Step("Кликаем на название магазина '{shipmentPosition}'-го заказа в таблице")
    public void clickStoreLinkInShipment(final int shipmentPosition) {
        tableComponent.clickRetailerStoreAddress(shipmentPosition - 1);
    }

    @Step("Получаем номер заказа '{shipmentPosition}'-го заказа в таблице")
    public String getOrderNumber(final int shipmentPosition) {
        return tableComponent.getOrderNumber(shipmentPosition - 1);
    }

    @Step("Кликаем на номер заказа '{shipmentPosition}'-го заказа в таблице")
    public void clickOrderNumberInShipment(final int shipmentPosition) {
        tableComponent.clickOrderNumber(shipmentPosition - 1);
    }

    @Step("Кликаем на номер доставки '{shipmentPosition}'-го заказа в таблице")
    public void clickShipmentNumberInShipment(final int shipmentPosition) {
        tableComponent.clickShipmentNumber(shipmentPosition - 1);
    }

    @Step("Выбираем '{shipmentDropdownMenuItem}' в выпадающем меню доставки")
    public void clickShipmentDropdownMenu(final String shipmentDropdownMenuItem) {
        dropdownMenuItemByName.click(shipmentDropdownMenuItem);
    }

    @Step("Кликаем на статус оплаты '{shipmentPosition}'-го заказа в таблице")
    public void clickPaymentStatusInShipment(final int shipmentPosition) {
        tableComponent.clickPaymentStatus(shipmentPosition - 1);
    }

    @Step("Кликаем на время доставки '{shipmentPosition}'-го заказа в таблице")
    public void clickDeliveryTimeInShipment(final int shipmentPosition) {
        tableComponent.clickDeliveryTime(shipmentPosition - 1);
    }

    @Step("Кликаем на имя заказчика '{shipmentPosition}'-го заказа в таблице")
    public void clickCustomerName(final int shipmentPosition) {
        tableComponent.clickClientName(shipmentPosition - 1);
    }

    @Override
    public String pageUrl() {
        return "orders";
    }
}
