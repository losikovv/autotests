package ru.instamart.reforged.stf.drawer.store_selector;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.frame.Close;

public class StoreSelector implements StoreSelectorCheck, Close {

    @Step("Закрыть выбор магазинов")
    public void clickToCloseButton() {
        closeModal.click();
    }

    @Step("Выбрать первый магазин в списке")
    public void clickToStoreCard() {
        storeCard.click();
    }
}
