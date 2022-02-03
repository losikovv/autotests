package ru.instamart.reforged.business.frame.product_card;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.frame.Close;

public final class ProductCard implements ProductCardCheck, Close {

    @Step("Нажимаем кнопку 'Купить'")
    public void clickOnBuy() {
        buy.click();
    }

    @Step("Нажимаем кнопку '+' (добавить товар в корзину)")
    public void increaseItemCount() {
        increase.click();
    }

    @Step("Нажимаем кнопку '-' (убрать товар из корзины)")
    public void decreaseItemCount() {
        decrease.click();
    }

}
