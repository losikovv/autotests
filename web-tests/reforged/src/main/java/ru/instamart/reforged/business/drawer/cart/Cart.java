package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

/**
 * Корзина. Основные элементы
 */
public final class Cart implements CartCheck {

    @Step("Нажимаем кнопку 'Закрыть'")
    public void closeCart() {
        closeButton.click();
    }

    //TODO инпут скрыт, пока не кликнуть - не виден + кракеновский clear() не работает. Удалось работать с ним так
    @Step("Вводим количество единиц товара: {itemCount}")
    public void setItemCount(final String itemCount) {
        itemCounter.click();
        itemCounterInput.getActions().sendKeys(Keys.BACK_SPACE);
        itemCounterInput.fill(itemCount);
        itemCounterInput.getActions().sendKeys(Keys.ENTER);
    }
}
