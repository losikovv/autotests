package ru.instamart.reforged.business.frame.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.Close;

public class StoreSelector implements StoreSelectorCheck, Close {

    @Step("Выбрать первый магазин в списке")
    public void clickToFirstStoreCard() {
        firstStoreCard.click();
    }

    @Step("Выбрать второй магазин в списке")
    public void clickToSecondStoreCard() {
        secondStoreCard.click();
    }

    @Step("Нажать Изменить адресс")
    public void clickToChangeAddress() {
        editAddress.click();
    }
}
