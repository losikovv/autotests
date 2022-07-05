package ru.instamart.reforged.business.frame.address;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

/**
 * Попап выбора адреса, открывается на главной странице, а также в других местах для пользователя, не исключенного из АБ
 */
public final class B2BAddressLarge implements Close, B2BAddressLargeCheck {

    @Step("Указать адрес доставки")
    public void fillAddress(final String text) {
        addressInput.fillField(text);
    }

    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        foundedAddresses.selectFirst();
    }

    @Step("Нажать 'Сохранить'")
    public void clickSave() {
        save.click();
    }

    @Step("Нажать 'Найти магазины'")
    public void clickFindStores() {
        findStores.click();
    }
}
