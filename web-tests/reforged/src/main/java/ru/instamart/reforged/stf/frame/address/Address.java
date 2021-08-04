package ru.instamart.reforged.stf.frame.address;

import io.qameta.allure.Step;
import lombok.SneakyThrows;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.frame.Close;

public final class Address implements Close, AddressCheck {

    @Step("Выбрать доставку")
    public void selectDelivery() {
        delivery.click();
    }

    @Step("Указать адрес доставки")
    public void setAddress(final String text) {
        Kraken.waitAction().fillField(address, text);
    }

    @Step("Очистить поле адреса")
    public void clear() {
        address.clear();
    }


    @Step("Очистить поле адреса")
    public void clearAddressField(String text) {
        final WebElement webElement = Kraken.waitAction().getValue(address);
        webElement.click();
        webElement.sendKeys(Keys.COMMAND + "a");
        webElement.sendKeys(Keys.CONTROL + "a");
        webElement.sendKeys(Keys.DELETE);

    }

    @Step("Выбрать любой адрес из совпадений")
    public void select() {
        dropDownAddress.selectAny();
    }

    @SneakyThrows
    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        dropDownAddress.selectFirst();
        //TODO: Ожидание смены геопозиции
        Thread.sleep(2000);
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
