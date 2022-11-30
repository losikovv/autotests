package ru.instamart.reforged.stf.frame.address;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

/**
 * Попап выбора адреса, открывается на главной странице, а также в других местах для пользователя, не исключенного из АБ
 */
public final class AddressLarge implements Close, AddressLargeCheck {

    @Step("Указать адрес доставки")
    public void fillAddress(final String text) {
        addressInput.fillField(text);
    }

    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        foundedAddresses.clickOnFirst();
    }

    @Step("Получаем {order}-й адрес из списка совпадений")
    public String getFoundedAddressByPositions(final int positionInList) {
        return foundedAddresses.getElementText(positionInList - 1);
    }

    @Step("Нажать 'Сохранить'")
    public void clickSave() {
        save.click();
    }

    @Step("Нажимаем 'x' (очистить адрес))")
    public void clearInput() {
        clearInput.click();
    }

    @Step("Нажать 'Найти магазины'")
    public void clickFindStores() {
        findStores.click();
    }

    @Step("Нажимаем 'Заберу отсюда'")
    public void clickTakeFromHere() {
        takeFromHere.click();
    }

    @Step("Нажимаем 'Показать списком'")
    public void clickShowAsList() {
        asList.click();
    }

    @Step("Выбираем ритейлера с именем: '{retailerName}'")
    public void selectRetailerByName(final String retailerName) {
        retailerByName.click(retailerName);
    }

    @Step("Выбираем магазин с адресом: '{address}'")
    public void selectStoreByAddress(final String address) {
        storeByAddress.click(address);
    }

    @Step("Выбираем первый магазин в списке")
    public void selectFirstRetailerStore() {
        retailerStores.clickOnFirst();
    }
}
