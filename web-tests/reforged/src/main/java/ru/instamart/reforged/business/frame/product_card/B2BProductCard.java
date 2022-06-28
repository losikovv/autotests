package ru.instamart.reforged.business.frame.product_card;

import io.qameta.allure.Step;

public final class B2BProductCard implements B2BProductCardCheck {

    @Step("Нажимаем кнопку 'Закрыть'")
    public void clickOnClose() {
        close.click();
    }

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
