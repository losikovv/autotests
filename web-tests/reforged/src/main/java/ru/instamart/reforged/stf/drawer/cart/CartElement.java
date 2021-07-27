package ru.instamart.reforged.stf.drawer.cart;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.frame.ClearCart;

public interface CartElement {

    ClearCart clearCartModal = new ClearCart();

    Button close = new Button(By.xpath("//button[@data-qa='cart_close-button']"), "кнопка 'Закрыть корзину'");
    Button returnToCatalog = new Button(By.xpath("//button[@data-qa='cart_return_to_catalog_button']"), "кнопка 'Вернуться в каталог'");
    Element placeholder = new Element(By.xpath("//div[@class='new-cart-empty']"), "Плейсхолдер пустой корзины");

    Element mergeChecker = new Element(By.xpath("//div[@class='cart-retailer__merge-checker']"), "сообщение о возможности сделать дозаказ");//локатор из 2х классов
    Button mergeButton = new Button(By.xpath("//button[@data-qa='merge_products_button']"), "кнопка 'перенести' в сообщении дозаказа");
    Link lookMergedProductsButton = new Link(By.xpath("//a[@data-qa='merged_products_look_button']"), "кнопка 'посмотреть' в сообщении дозаказа");
    Element orderReminder = new Element(By.xpath("//div[@data-qa='cart']//div[contains(text(), 'Забыли купить нужные товары')]"), "сообщение о возможности дозаказа в пустой корзине");

    Button hideOutOfStock = new Button(By.xpath("//button[@data-qa='cart_price_diff_toggle_button'][contains(text(),'Показать')]"));
    Button showOutOfStock = new Button(By.xpath("//button[@data-qa='cart_price_diff_toggle_button'][contains(text(),'Скрыть')]"));
    Element outOfStock = new Element(By.xpath("//div[@data-qa='line-item']//div[contains(text(), 'Нет в наличии')]"), "товар не в наличии");

    Button showRisePrice = new Button(By.xpath("//button[@data-qa='cart_price_diff_toggle_button'][contains(text(),'Показать')]"), "кнопка 'посмотреть' цену с учетом повышения");
    Button hideRisePrice = new Button(By.xpath("//button[@data-qa='cart_price_diff_toggle_button'][contains(text(),'Показать')]"), "кнопка 'скрыть' цену с учетом повышения");

    Button clearCart = new Button(By.xpath("//button[@data-qa='cart_remove_shipments_button']"), "кнопка 'Очистить корзину'");
    Element itemCounter = new Element(By.xpath("//div[@data-qa='line-item-counter']"), "кол-во добавленных товаров");
    Element minSumAlert = new Element(By.xpath("//div[@class='cart-retailer__alert-message-box']"), "сообщение о минимальной сумме корзины");
    Button increaseCount = new Button(By.xpath("//button[@data-qa='increase-button']"), "кнопка Увеличить кол-во");
    Button decreaseCount = new Button(By.xpath("//button[@data-qa='decrease-button']"), "кнопка Уменьшить кол-во");
    Link openItemCard = new Link(By.xpath("//a[@data-qa='open-button']"), "переход к карточке товара");
    Button mobileRemoveItem = new Button(By.xpath("//button[@data-qa='remove-button-mobile']"));
    Button removeItem = new Button(By.xpath("//button[@data-qa='cart_delete_item_button']"), "кнопка Удалить позицию");
    Element costSpinner = new Element(By.xpath("//div[@data-qa='line-item']//div[contains(@class,'Spinner')]"), "спиннер пересчета цены позиции");
    Element retailRocketBlock = new Element(By.xpath("//div[@data-qa='cart']//div[contains(@class, 'retail-rocket-block')]"), "блок Не забудьте купить");
    Button nextRetailSlide = new Button(By.xpath("//button[@aria-label='Next slide']"), "следующий слайд блока Не забудьте купить");
    Button prevRetailSlide = new Button(By.xpath("//button[@aria-label='Previous slide']"), "предыдущий слайд блока Не забудьте купить");

    Button submitOrder = new Button(By.xpath("//button[@data-qa='cart_checkout_button']"), "кнопка Сделать заказ");
    Element cartSpinner = new Element(By.xpath("//div[@data-qa='line-item']/descendant::div[contains(@class, 'Spinner')] "), "спиннер пересчета цены позиции");
}
