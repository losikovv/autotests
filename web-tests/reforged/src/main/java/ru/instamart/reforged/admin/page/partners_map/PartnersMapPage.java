package ru.instamart.reforged.admin.page.partners_map;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class PartnersMapPage implements AdminPage, PartnersMapCheck {

    @Step("Добавить фильтрацию по городу '{city}'")
    public void addFilterCity(final String city) {
        citySelector.fillAndSelect(city);
    }

    @Step("Добавить фильтрацию по типу курьера '{type}'")
    public void addFilterTypeOfCouriers(final String... type) {
        typeOfCouriersSelector.fillAndSelect(type);
    }

    @Step("Добавить фильтрацию по Территории доставки '{area}'")
    public void addFilterDeliveryArea(final String area) {
        deliveryAreaSelector.fillAndSelect(area);
    }

    @Step("Добавить фильтрацию по 'ФИО, телефон или номер заказа' '{filter}'")
    public void addFilter(final String filter) {
        filterInput.fillField(filter);
    }

    @Step("Нажать на кнопку 'Обновить карту'")
    public void clickOnRefreshMap() {
        refreshMapButton.click();
    }

    @Step("Нажать на кнопку 'Сбросить'")
    public void clickOnReset() {
        resetButton.click();
    }

    @Step("Нажать на кнопку 'Применить'")
    public void clickOnApply() {
        applyButton.click();
    }

    @Override
    public String pageUrl() {
        return "partners_map";
    }
}
