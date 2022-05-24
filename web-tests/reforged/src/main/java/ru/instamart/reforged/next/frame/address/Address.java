package ru.instamart.reforged.next.frame.address;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.next.frame.store_selector.StoreSelector;

import static ru.instamart.reforged.next.block.header.HeaderElement.searchButton;
import static ru.instamart.reforged.next.block.header.HeaderElement.storeSelectorDrawer;

public final class Address implements AddressCheck {

    public StoreSelector interactStoreSelector() {
        return storeSelectorDrawer;
    }

    @Step("Нажимаем 'Закрыть'")
    public void clickOnClose() {
        close.click();
    }

    @Step("Выбрать доставку")
    public void selectDelivery() {
        delivery.click();
    }

    @Step("Указать адрес доставки")
    public void fillAddress(final String text) {
        address.fillField(text);
    }

    @Step("Выбрать любой адрес из совпадений")
    public void select() {
        dropDownAddress.selectAny();
    }

    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        dropDownAddress.selectFirst();
        //TODO: Ожидание смены геопозиции
        ThreadUtil.simplyAwait(2);
    }

    @Step("Нажать кнопку найти адрес")
    public void clickToSearchAddress() {
        searchButton.click();
    }

    @Step("Выбрать самовывоз")
    public void selectSelfDelivery() {
        selfDelivery.click();
    }

    @Step("Выбрать город {0}")
    public void selectCity(final String text) {
        selectCity.selectByText(text);
    }

    @Step("Нажать сохранить")
    public void clickOnSave() {
        save.click();
    }

    @Step("Нажать 'Найти магазины'")
    public void clickFindStores() {
        findStores.click();
    }

    @Step("Нажать Выбрать другой магазин")
    public void clickViewOtherRetailers() {
        otherRetailers.click();
    }

    @Step("Выбрать первый доступный магазин")
    public void selectFirstStore() {
        selectStoreButton.click();
    }

    @Step("Изменить выбранный магазин самовывоза")
    public void changeStore() {
        changeStore.click();
    }

    @Step("Нажать на кнопку 'Войти'")
    public void clickToLogin() {
        login.click();
    }

    @Step("Нажать Выбрать другой адрес")
    public void clickToChangeAddress() {
        chooseOtherAddress.click();
    }

    @Step("Клик на первую запись блока Предыдущие адреса")
    public void clickOnFirstPrevAddress() {
        firstPrevAddresses.click();
        ThreadUtil.simplyAwait(2);
    }

    @Step("Получить текст первого адреса из блока Предыдущие адреса")
    public String getFirstPrevAddress() {
        return firstPrevAddresses.getText();
    }

    @Step("Получить '{0}' адрес из списка сохраненных")
    public String getStoredAddress(final int order) {
        return storedAddresses.getElementText(order);
    }
}
