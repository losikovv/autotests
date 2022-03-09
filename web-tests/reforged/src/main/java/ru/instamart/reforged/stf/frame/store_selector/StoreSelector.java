package ru.instamart.reforged.stf.frame.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.frame.Close;

public class StoreSelector implements StoreSelectorCheck, Close {

    @Step("Выбрать первый магазин в списке")
    public void clickToFirstStoreCard() {
        firstStoreCard.click();
    }

    @Step("Выбрать второй магазин в списке")
    public void clickToSecondStoreCard() {
        secondStoreCard.click();
    }

    @Step("Выбираем магазин с Sid = '{storeSid}'")
    public void clickToStoreWithSid(final int storeSid){
        storeBySid.click(storeSid);
    }

    @Step("Нажать Изменить адресс")
    public void clickToChangeAddress() {
        editAddress.click();
    }
}
