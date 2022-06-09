package ru.instamart.reforged.next.frame.store_modal;

import io.qameta.allure.Step;
import ru.instamart.reforged.next.frame.Close;

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

    @Step("Выбираем магазин c sid = '{sid}")
    public void selectStoreWithSid(final int sid) {
        storeCardBySid.click(sid);
    }
}
