package ru.instamart.reforged.stf.frame.address;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Selector;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.frame.Close;

public final class Address implements Close, AddressCheck {

    @Step("Выбрать доставку")
    public void selectDelivery() {
        delivery.click();
    }

    @Step("Указать адрес доставки")
    public void setAddress(final String text) {
        address.fill(text);
    }

    @Step("Очистить поле адреса")
    public void clear() {
        address.clear();
    }

    @Step("Выбрать любой адрес из совпадений")
    public void select() {
        dropDownAddress.selectAny();
    }

    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        dropDownAddress.selectFirst();
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

    @Step("Изменить выбранный магазин самовывоза")
    public void clickToLogin() {
        login.click();
    }
}
