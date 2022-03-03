package ru.instamart.reforged.business.frame.address;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.business.frame.B2BClose;

public final class B2BAddress implements B2BClose, B2BAddressCheck {

    @Step("Указать адрес доставки")
    public void fillAddress(final String text) {
        address.fillField(text);
    }

    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        dropDownAddress.selectFirst();
        //TODO: Ожидание смены геопозиции
        ThreadUtil.simplyAwait(2);
    }

    @Step("Нажать сохранить")
    public void clickOnSave() {
        save.click();
    }
}
