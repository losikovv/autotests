package ru.instamart.reforged.admin.page.settings.shipping_method;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;

public final class ShippingMethodPage implements AdminPage, ShippingMethodCheck {

    public FlashAlert interactFlashAlert() {
        return alert;
    }

    @Step("Нажать на кнопку 'Новый способ доставки'")
    public void addNewDelivery() {
        addNewDelivery.click();
    }

    @Step("Открыть на редактирование первый элемент таблица")
    public void clickToEditShipmentMethod(final String name) {
        editButton.scrollTo(name);
        editButton.click(name);
    }

    @Step("Открыть форму добавления маркетингова правила")
    public void clickToAddNewMarketingRule() {
        addNewMarketingRule.click();
    }

    @Step("Нажать на селектор 'Правило' для маркетинга")
    public void clickToMarketingSelectRule() {
        selectMarketingRule.click();
    }

    @Step("Нажать на селектор 'Калькулятор цен' для маркетинга")
    public void clickToMarketingSelectCalculator() {
        selectMarketingCalculator.click();
    }

    @Step("Удалить маркетинговое правило")
    public void deleteMarketingRule() {
        deleteMarketingRule.click();
    }

    @Step("Установить первый интервал {0} маркетингова 'Интервального правила'")
    public void fillFirstMarketingIntervalRule(final String data) {
        firstInterval.fillField(data);
    }

    @Step("Установить второй интервал {0} маркетингова 'Интервального правила'")
    public void fillSecondMarketingIntervalRule(final String data) {
        secondInterval.fillField(data);
    }

    @Step("Установить дней с последнего заказа {0} маркетингова 'Периодического правила'")
    public void fillDayMarketingPeriodicRule(final String data) {
        dayFromLastShipment.fillField(data);
    }

    @Step("Установить минимальной суммы последнего заказа {0} маркетингова 'Периодического правила'")
    public void fillMinSumMarketingPeriodicRule(final String data) {
        minShipmentSum.fillField(data);
    }

    @Step("Открыть форму добавления номинального правила")
    public void clickToAddNewNominalRule() {
        addNewNominalRule.click();
    }

    @Step("Нажать на селектор 'Правило' для номинальной доставки")
    public void clickToNominalSelectRule() {
        selectNominalRule.click();
    }

    @Step("Нажать на селектор 'Калькулятор цен' для номинальной доставки")
    public void clickToNominalSelectCalculator() {
        selectNominalCalculator.click();
    }

    @Step("Удалить номинальное правило")
    public void deleteNominalRule() {
        deleteNominalRule.click();
    }

    @Step("Выбрать значение в селекторе '{0}'")
    public void selectValue(final String value) {
        selectorValue.click(value);
    }

    @Step("Установить 'Цена доставки'={0}")
    public void fillDeliveryPrice(final String value) {
        deliveryPrice.fillField(value);
    }

    @Step("Установить 'Надбавка за сборку'={0}")
    public void fillAssemblySurcharge(final String value) {
        assemblySurcharge.fillField(value);
    }

    @Step("Установить 'Надбавка за пакеты'={0}")
    public void fillBagSurcharge(final String value) {
        bagSurcharge.fillField(value);
    }

    @Step("Установить 'Базовая цена'={0}")
    public void fillBasePrice(final String value) {
        basePrice.fillField(value);
    }

    @Step("Установить 'Базовое кол-во позиций'={0}")
    public void fillBaseCount(final String value) {
        baseCount.fillField(value);
    }

    @Step("Установить 'Базовая масса'={0}")
    public void fillBaseWeight(final String value) {
        baseWeight.fillField(value);
    }

    @Step("Установить 'Дополнительная масса'={0}")
    public void fillAddedWeight(final String value) {
        weight.fillField(value);
    }

    @Step("Установить 'Надбавка за доп. массу'={0}")
    public void fillSurchargeWeight(final String value) {
        surchargeWeight.fillField(value);
    }

    @Step("Установить 'Дополнительное количество'={0}")
    public void fillAddedCount(final String value) {
        addedCount.fillField(value);
    }

    @Step("Установить 'Надбавка за доп. количество'={0}")
    public void fillSurchargeCount(final String value) {
        surchargeCount.fillField(value);
    }

    @Step("Нажать на кнопку 'Применить изменения'")
    public void clickToSubmitChanges() {
        submitChanges.hoverAndClick();
    }

    @Override
    public String pageUrl() {
        return "shipping_methods";
    }
}
