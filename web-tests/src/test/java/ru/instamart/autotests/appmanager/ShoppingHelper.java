package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.UserData;

import javax.xml.xpath.XPath;



    // Shopping helper
    // Contains shopping operations before checkout



public class ShoppingHelper extends HelperBase {

    public ShoppingHelper(WebDriver driver) { super(driver); }



    // ======= Shipping address =======

    /**
     * Set shipping address with a given string
     */
    public void setShippingAddress(String address){
        // TODO
    }

    // TODO
    /**
     * Find out if there shipping address is set or not
     */
    // "ship-address-selector" - имя класса для контейнера когда адрес не выбран
    // "ship-address-selector--selected" - имя класса для контейнера когда адрес выбран
    //*[@id="wrap"]/div[1]/div/div/div/div[1]/div/div/text() - "Вы выбрали адрес
    public boolean shippingAddressIsSet(){
        if(isElementPresent(By.className("ship-address-selector--selected"))){
            return true;
        } else {
            return false;
        }
    }

    // TODO isShippingAddressSelected - метод возвращающий логическое значение выбран ли адрес доставки

    // TODO fillShippingAddress(String address) - заполнить поле адреса доставки

    // TODO anySuggestsAvailable - метод возврящающий логическое значение наличия адресных подсказок

    // TODO selectAddressSuggest(int suggestPosition) - выбрать адресную подсказку по позиции

    // TODO selectShippingAddress(String address) - заполнить и выбрать адрес доставки

    // TODO selectShippingAddressWithNoShops() - выбрать адрес без магазинов

    // TODO changeShippingAddress(String newAddress) - изменить адрес



    // TODO isShopSelected - метод возвращающий логическое значение выбран ли магазин

    // TODO isAnyShopsAvailable - метод возврящающий логическое значение наличия магазинов по выбранному адресу

    // TODO selectShop(int shopPosition) - выбрать магазин

    // TODO changeShop(int shopPosition) - изменить магазин



    // ======= Catalog =======

    public void catalog(){
        // TODO
    }

    /**
     * Add first line item on the page to the shopping cart
     * Shipping address must be chosen before that
     */
    public void addFirstLineItemOnPageToCart(){
        // жмем на сниппет первого товара на странице
        click(By.xpath("//*[@id='home']/div[2]/ul/li[1]/ul/li[1]/a"));
        // переключаемся на модалку карточки товара
        swithchToActiveElement();
        // жмем на кнопку добавления товара
        click(By.xpath("//*[@id='product']/div[2]/div/div[1]/div"));
        // жмем на крестик закрытия карточки товара
        click(By.className("close"));
    }



    // ======= Shopping Cart =======

    /**
     * Метод, определяющий открыта ли корзина
     */
    public boolean isCartOpen(){
        if (isElementPresent(By.className("new-cart"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Открыть корзину
     */
    public void openCart(){
        click(By.className("open-new-cart"));
    }

    /**
     * Закрыть корзину
     */
    public void closeCart(){
        click(By.className("btn-close-cart"));
    }

    /**
     * Метод, определяющий пуста ли корзина
     */
    public boolean isCartEmpty(){
        //if (isElementPresent(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div"))) {
        if (isElementPresent(By.className("new-cart-empty"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Метод, определяющий показан ли плейсхолдер в пустой корзине
     */
    public boolean isEmptyCartPlaceholderPresent(){
        if (isElementPresent(By.className("new-cart-empty__img"))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Удалить верхний товар в корзине
     */
    //public void deleteTopItem(){
    //    click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/span/div[1]/div/div/div[1]/div[1]/span"));
    //    click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/span/div[1]/div/div/div[1]/div[1]/button[2]"));
    //}

    /**
     * Удалить товар по позиции в корзине
     */
    public void deleteItem(int itemPosition){
        click(By.xpath("*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div/div[2]/span/div[" + itemPosition + "]/div/div/div[1]/div[2]/div[2]"));
    }

    /**
     * Удалить все товары в корзине удаляя верхние товары
     */
    //public void deleteAllItemsInCart(){
     //   if(!isCartEmpty()){
      //      deleteTopItem();
      //      deleteAllItemsInCart();
     //   }
    //}

    // TODO clearCart - очистить корзину (удалить все товары сразу кнопкой)

    // TODO changeTopItemQuantity( int newQuantity) - изменить кол-во верхнего товара
    // TODO changeItemQuantity(int itemPosition, int newQuantity) - изменить кол-во товара по позиции

    // TODO addTopItemQuantity() - +1 кол-во верхнего товара
    // TODO addItemQuantity(int itemPosition) - +1 кол-во товара по позиции

    // TODO reduceTopItemQuantity() - -1 кол-во верхнего товара
    // TODO reduceItemQuantity(int itemPosition) - -1 кол-во товара по позиции


    /**
     * Get the cart sum from the 'Make order' button
     */
    public void getCartTotal(){
        getText(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[3]/form/button/div/div[2]/div/text()"));
    }

    // TODO isCheckoutButtonActive - активна ли кнопка перехода в чекаут

    /**
     * Click the 'Make order' button in the shopping cart and proceed to checkout
     */
    public void proceedToCheckout(){
        click(By.className("cart-checkout"));
    }

    // TODO grabCart(int sum) - ннабрать корзину на переданную сумму

    // TODO методы для мультизаказа

}