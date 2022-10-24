package ru.instamart.reforged.business.frame.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.B2BClose;

public class B2BStoreSelector implements B2BStoreSelectorCheck, B2BClose {

    @Step("Нажимаем на карточку магазина с sid = {storeSid}")
    public void clickOnStoreWithSid(final int storeSid) {
        storeCardBySid.click(storeSid);
    }

    @Step("Нажать Изменить адресс")
    public void clickToChangeAddress() {
        editAddress.click();
    }
}
