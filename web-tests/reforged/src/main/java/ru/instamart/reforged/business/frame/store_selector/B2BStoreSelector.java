package ru.instamart.reforged.business.frame.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.B2BClose;

public class B2BStoreSelector implements B2BStoreSelectorCheck, B2BClose {

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
