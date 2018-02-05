package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;

public class CartHelper extends HelperBase {
    public CartHelper(WebDriver driver) {
        super(driver);
    }

    // хелпер корзины

    //TODO методы:

    // TODO isCartOpen - метод определяющий открыта ли корзина

    // TODO openCart - открыть корзину

    // TODO closeCart - закрыть корзину

    // TODO isCardEmpty - метод определяющий пустая ли корзина по наличию заглушки

    // TODO grabCartUnder1k - набрать корзину на сумму <1000р

    // TODO grabCartFrom1kTo5k - набрать корзину на сумму от 1000р до 5000р

    // TODO grabCartFrom5kTo10k - набрать корзину на сумму от 5000р до 10000р

    // TODO grabCartOver10k - набрать корзину на сумму >10000р

    // TODO deleteItem - удалить товар из корзины

    // TODO clearCart - очистить корзину (удалить все товары)

    // TODO changeQuantity - изменить кол-во товара

    // TODO getCartTotal - получить сумму корзины

    // TODO goToCheckout - перейти в чекаут

}
