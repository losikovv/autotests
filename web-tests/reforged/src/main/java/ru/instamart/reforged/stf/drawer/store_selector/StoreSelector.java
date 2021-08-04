package ru.instamart.reforged.stf.drawer.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

public class StoreSelector implements StoreSelectorCheck, Close {

    @Step("Выбрать первый магазин в списке")
    public void clickToStoreCard() {
        storeCard.click();
    }

    @Step("Нажать Изменить адресс")
    public void clickToChangeAddress() {
        editAddress.click();
    }
}
