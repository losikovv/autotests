package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ShoppingCartHelper extends HelperBase {
    public ShoppingCartHelper(WebDriver driver) {
        super(driver);
    }



    // Хелпер корзины



    /** Метод, определяющий открыта ли корзина */
    public boolean cartIsOpen(){
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Открыть корзину */
    public void openCart(){
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div"));
    }

    /** Закрыть корзину */
    public void closeCart(){
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[1]/div[2]"));
    }

    /** Метод, определяющий пуста ли корзина */
    // TODO переписать метод, определяя сумму корзины, а не по плейсхолдеру
    public boolean isCartEmpty(){
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Метод, определяющий пуста ли корзина */
    // TODO переписать метод, определяя сумму корзины, а не по плейсхолдеру
    public boolean isPlaceholderShown(){
        if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div"))) {
            return true;
        } else {
            return false;
        }
    }

    /** Удалить верхний товар в корзине */
    public void deleteTopItem(){
        click(By.xpath("*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div/div[2]/span/div[1]/div/div/div[1]/div[2]/div[2]"));
    }

    /** Удалить товар по позиции в корзине */
    public void deleteItem(int itemPosition){
        click(By.xpath("*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div/div[2]/span/div[" + itemPosition + "]/div/div/div[1]/div[2]/div[2]"));
    }

    // TODO isCheckoutButtonActive - активна ли кнопка перехода в чекаут

    // TODO clearCart - очистить корзину (удалить все товары)

    // TODO changeTopItemQuantity( int newQuantity) - изменить кол-во верхнего товара
    // TODO changeItemQuantity(int itemPosition, int newQuantity) - изменить кол-во товара по позиции

    // TODO addTopItemQuantity() - +1 кол-во верхнего товара
    // TODO addItemQuantity(int itemPosition) - +1 кол-во товара по позиции

    // TODO reduceTopItemQuantity() - -1 кол-во верхнего товара
    // TODO reduceItemQuantity(int itemPosition) - -1 кол-во товара по позиции


    // TODO getCartTotal - получить сумму корзины

    /** перейти в чекаут */
    public void goToCheckout(){
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[3]/form"));
    }

    // TODO grabCartUnder1k - набрать корзину на сумму <1000р
    // TODO grabCartFrom1kTo5k - набрать корзину на сумму от 1000р до 5000р
    // TODO grabCartFrom5kTo10k - набрать корзину на сумму от 5000р до 10000р
    // TODO grabCartOver10k - набрать корзину на сумму >10000р

    // TODO методы для мультизаказа
}
