package ru.instamart.reforged.stf.drawer;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.stf.frame.ClearCart;

public final class Cart {

    private final ClearCart clearCartModal = new ClearCart();

    private static final Button close = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "кнопка 'Закрыть корзину'");
    private final Button returnToCatalog = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"), "кнопка 'Вернуться в каталог'");
    private final Element placeholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "Плейсхолдер пустой корзины");

    private final Element mergeChecker = new Element(By.xpath("//div[@class='cart-retailer__merge-checker']"), "сообщение о возможности сделать дозаказ");//локатор из 2х классов
    private final Button mergeButton = new Button(By.xpath("//button[@data-qa='merge_products_button']"), "кнопка 'перенести' в сообщении дозаказа");
    private final Button lookMergedProductsButton = new Button(By.xpath("//a[@data-qa='merged_products_look_button']"), "кнопка 'посмотреть' в сообщении дозаказа");
    private final Element orderReminder = new Element(By.xpath("//div[@data-qa='cart']//div[contains(text(), 'Забыли купить нужные товары')]"), "сообщение о возможности дозаказа в пустой корзине");

    private final Button hideOutOfStock = new Button(By.xpath("//button[@data-qa='cart_price_diff_toggle_button'][contains(text(),'Показать')]"));
    private final Button showOutOfStock = new Button(By.xpath("//button[@data-qa='cart_price_diff_toggle_button'][contains(text(),'Скрыть')]"));
    private final Element outOfStock = new Element(By.xpath("//div[@data-qa='line-item']//div[contains(text(), 'Нет в наличии')]"), "товар не в наличии");

    private final Button clearCart = new Button(By.xpath("//button[@data-qa='cart_remove_shipments_button']"), "кнопка 'Очистить корзину'");
    private final Element itemCounter = new Element(By.xpath("//div[@data-qa='line-item-counter']"), "кол-во добавленных товаров");
    private final Element minSumAlert = new Element(By.xpath("//div[@class='cart-retailer__alert-message-box']"), "сообщение о минимальной сумме корзины");
    private final Button increaseCount = new Button(By.xpath("//button[@data-qa='increase-button']"), "кнопка Увеличить кол-во");
    private final Button decreaseCount = new Button(By.xpath("//button[@data-qa='decrease-button']"), "кнопка Уменьшить кол-во");
    private final Element openItemCard = new Element(By.xpath("//a[@data-qa='open-button']"), "переход к карточке товара");
    private final Button mobileRemoveItem = new Button(By.xpath("//button[@data-qa='remove-button-mobile']"));
    private final Button removeItem = new Button(By.xpath("//button[@data-qa='cart_delete_item_button']"), "кнопка Удалить позицию");
    private final Element costSpinner = new Element(By.xpath("//div[@data-qa='line-item']//div[contains(@class,'Spinner')]"), "спиннер пересчета цены позиции");
    private final Element retailRocketBlock = new Element(By.xpath("//div[@data-qa='cart']//div[contains(@class, 'retail-rocket-block')]"), "блок Не забудьте купить");
    private final Button nextRetailSlide = new Button(By.xpath("//button[@aria-label='Next slide']"), "следующий слайд блока Не забудьте купить");
    private final Button prevRetailSlide = new Button(By.xpath("//button[@aria-label='Previous slide']"), "предыдущий слайд блока Не забудьте купить");

    private final Button submitOrder = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"), "кнопка Сделать заказ");

    @Step("Закрыть корзину")
    public void closeCart() {
        close.click();
    }

    @Step("Вернуться в каталог")
    public void returnToCatalog() {
        returnToCatalog.click();
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
