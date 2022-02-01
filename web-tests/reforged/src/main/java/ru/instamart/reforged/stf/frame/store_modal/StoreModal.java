package ru.instamart.reforged.stf.frame.store_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

public final class StoreModal implements StoreModalCheck, Close {

    @Step("Нажать на кнопку 'Войти'")
    public void clickOnLogin() {
        login.click();
    }

    @Step("Выбрать способ доставки 'Доставка'")
    public void selectDelivery() {
        delivery.click();
    }
    @Step("Выбрать способ доставки 'Самовывоз'")
    public void selectPickup() {
        pickup.click();
    }

    @Step("Нажать на кнопку 'Изменить'")
    public void clickOnEditAddress() {
        editAddress.click();
    }
}
