package ru.instamart.reforged.stf.drawer.cart;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;

public final class Cart implements CartCheck {

    public RetailRocket interactRetailRocket() {
        return retailRocket;
    }

    @Step("Закрыть корзину")
    public void closeCart() {
        close.click();
    }

    @Step("Вернуться в каталог")
    public void returnToCatalog() {
        returnToCatalog.click();
    }

    @Step("Вернуть значение стоимости товаров в корзине")
    public double returnOrderAmount() {
        return StringUtil.orderAmountParse(orderAmount.getText());
    }

    @Step("Вернуть значение мин суммы заказа в корзине")
    public double returnMinOrderAmount() {
        return StringUtil.orderAmountParse(minAmountAlert.getText());
    }

    @Step("Очистить корзину")
    public void clearCart() {
        clearCart.click();
    }

    @Step("Подтвердить очистку корзины")
    public void confirmClearCart() {
        clearCartModal.confirm();
    }

    @Step("Отменить очистку корзины")
    public void cancelClearCart() {
        clearCartModal.cancel();
    }

    @Step("Сделать заказ")
    public void submitOrder() {
        submitOrder.click();
    }

    @Step("Увеличить кол-во товара")
    public void increaseCount() {
        increaseCount.hoverAndClick();
    }

    @Step("Удалить первый товар из корзины")
    public void deleteFirstItem() {
        deleteFirstItemButton.hoverAndClick();
    }

    @Step("Увеличить кол-во товара до минимального, доступного к заказу")
    public void increaseCountToMin() {
        while (!checkOrderButtonIsEnabled()) {
            increaseCount.hoverAndClick();
            checkSpinnerIsNotVisible();
        }
    }

    @Step("Уменьшить кол-во товара")
    public void decreaseCount() {
        decreaseCount.hoverAndClick();
    }

    @Step("Открыть карточку товара")
    public void openItemCard() {
        openItemCard.click();
    }

    @Step("Удалить товар")
    public void removeItem() {
        removeItem.hoverAndClick();
    }

    @Step("Удалить товар")
    public void mobileRemoveItem() {
        mobileRemoveItem.hoverAndClick();
    }

    @Step("Следующий слайд в блоке 'Не забудьте купить'")
    public void nextRetailSlide() {
        nextRetailSlide.click();
    }

    @Step("Предыдущий слайд в блоке 'Не забудьте купить'")
    public void prevRetailSlide() {
        prevRetailSlide.click();
    }

    @Step("Добавление позиций в существующий заказ")
    public void mergeProducts() {
        mergeButton.click();
    }

    @Step("Переход на страницу заказа для проверки позиций дозаказа")
    public void checkMergeProducts() {
        lookMergedProductsButton.click();
    }

    @Step("Скрыть товары не в наличии")
    public void clickToHideOutOfStock() {
        hideOutOfStock.click();
    }

    @Step("Показать товары не в наличии")
    public void clickToShowOutOfStock() {
        showOutOfStock.click();
    }

}
