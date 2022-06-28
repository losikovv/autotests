package ru.instamart.reforged.business.drawer.cart;

import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import ru.instamart.reforged.next.drawer.cart.container.Item;

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

    @Step("Увеличиваем кол-во товара до тех пор, пока заказ не станет доступен")
    public void increaseFirstItemCountToMin() {
        Item item = getFirstItem();
        while (!checkOrderButtonIsEnabled()) {
            item.increaseCount();
            item.checkSpinnerIsNotVisible();
        }
    }

    @Step("Получаем первый товар в корзине")
    public Item getFirstItem() {
        return new Item(firstItem.getElement());
    }
}
