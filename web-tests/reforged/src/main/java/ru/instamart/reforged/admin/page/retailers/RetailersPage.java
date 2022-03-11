package ru.instamart.reforged.admin.page.retailers;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.page.retailers.activate_store_modal.ActivateStoreModal;

public final class RetailersPage implements AdminPage, RetailersPageCheck {

    public ActivateStoreModal interactActivateStoreModal() {
        return activateStoreModal;
    }

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

    @Step("Нажать на плюс у первого ретейлера")
    public void clickOnPlusForFirstRetailer() {
        firstRetailerPlusIconInTable.click();
    }

    @Step("Нажать на плюс у города {0}")
    public void clickOnPlusForCity(final String city) {
        cityPlusIconInTable.click(city);
    }

    @Step("Нажать на плюс у первого города")
    public void clickOnPlusForFirstCity() {
        firstCityPlusIconInTable.click();
    }

    @Step("Нажать 'активировать' у магазина по адресу {0}")
    public void clickOnActivateStoreViaAddress(final String address) {
        storeActivateInTable.click(address);
    }

    @Step("Нажать 'активировать' у первого магазина")
    public void clickOnActivateFirstStore() {
        firstStoreActivateInTable.click();
    }

    @Step("Нажать 'Деактивировать' у магазина по адресу {0}")
    public void clickOnDeactivateStoreViaAddress(final String address) {
        storeDeactivateInTable.click(address);
    }

    @Step("Ввести название ретейлера '{0}' в поиск")
    public void fillRetailerSearch(final String retailer) {
        retailerSearchInput.fillField(retailer);
    }

    @Step("Ввести название региона '{0}' в поиск")
    public void fillRegionSearch(final String region) {
        regionsSearchInput.fill(region);
    }

    @Step("Кликнуть на поиск ретейлера")
    public void clickOnRetailerSearchElement() {
        retailerSearchElement.click();
    }

    @Step("Кликнуть на поиск региона")
    public void clickOnRegionsSearchElement() {
        regionsSearchElement.click();
    }

    @Step("Кликнуть на сортировку ретейлеров по имени")
    public void clickOnSortViaName() {
        sortRetailersViaNameInTable.click();
    }

    @Step("Кликнуть на сортировку ретейлеров по дате создания")
    public void clickOnSortViaCreationDate() {
        sortRetailersViaCreationDateInTable.click();
    }

    @Step("Выбрать первый ретейлер из подсказок результатов поиска")
    public void clickOnFirstRetailerInSearchSuggest() {
        retailerSearchOptions.clickOnFirst();
    }

    @Step("Выбрать первый регион из подсказок результатов поиска")
    public void clickOnFirstRegionInSearchSuggest() {
        regionSearchOptions.clickOnFirst();
    }

    @Step("Нажать на магазин {0}")
    public void clickOnStore(final String store) {
        storesInTable.clickOnElementWithText(store);
    }

    @Step("Нажать на первый магазин")
    public void clickOnFirstStore() {
        storesInTable.clickOnFirst();
    }

    @Step("Нажать на адрес {0}")
    public void clickOnAddress(final String address) {
        addressInTableWithText.click(address);
    }

    @Step("Нажать на первый адрес")
    public void clickOnFirstAddress() {
        addressInTable.click();
    }

    @Step("Вернуть текст первого адреса")
    public String getFirstAddressFromTable() {
        return addressesInTable.getElementText(0);
    }

    @Step("Кликнуть на кнопку 'Добавить ритейлера'")
    public void clickOnAddRetailerButton() {
        addNewRetailerButton.click();
    }

    @Step("Нажать на кнопку 'Добавить магазин' для ретейлера {0}")
    public void clickOnAddStore(final String retailer) {
        addNewStoreForRetailer.click(retailer);
    }

    @Step("Нажать 'Ok' в попапе деактивации магазина")
    public void clickOkOnDeactivateStorePopup() {
        okButtonOnDeactivateStorePopup.click();
    }

    @Step("Нажать на фильтр доступности для выбора опций")
    public void clickOnAccessibilityFilter() {
        accessibilityFilterButton.getActions().moveToElementAndClick();
    }

    @Step("Выбрать доступные ретейлеры в выпадающем списке фильтра доступности")
    public void selectAccessibleRetailers() {
        accessibleRetailersOption.click();
    }

    @Step("Выбрать недоступные ретейлеры в выпадающем списке фильтра доступности")
    public void selectInaccessibleRetailers() {
        inaccessibleRetailersOption.click();
    }

    @Step("Нажать кнопку 'Ok' в выпадающем списке фильтра доступности")
    public void clickOnOkRetailersFilterButton() {
        accessibilityFilterDropdownOkButton.click();
    }

    @Override
    public String pageUrl() {
        return "retailers";
    }
}
