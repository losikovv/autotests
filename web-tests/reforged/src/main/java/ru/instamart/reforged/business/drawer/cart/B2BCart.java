package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

/**
 * Корзина. Основные элементы
 */
public final class B2BCart implements B2BCartCheck {

    @Step("Нажимаем кнопку 'Закрыть'")
    public void closeCart() {
        closeButton.click();
    }

    @Step("Вводим количество единиц товара: {itemCount}")
    public void setItemCount(final String itemCount) {
        itemCounter.click();
        itemCounterInput.fillField(itemCount);
        itemCounterInput.getActions().sendKeys(Keys.ENTER);
    }
}
