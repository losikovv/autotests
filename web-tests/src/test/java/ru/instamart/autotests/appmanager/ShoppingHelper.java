package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;


    // Shopping helper
    // Contains methods for all shopping operations before checkout



public class ShoppingHelper extends HelperBase {

    public ShoppingHelper(WebDriver driver) {
        super(driver);
    }



    // ======= Shipping address =======

    /**
     * Set shipping address with a given string
     */
    public void setShippingAddress(String address){
        if(itsOnLandingPage()){
            setShippingAddressOnLandingPage(address);
        } else {
            setShippingAddressOnRetailerPage(address);
        }
        printMessage("Shipping address is set to " + address + "\n");
    }

    private void setShippingAddressOnLandingPage(String address){
        fillAddressField(address); // вводим адрес
        selectAddressSuggest(1); // выбираем первую подсказку
        clickChooseShopButton(); // жмем Выбрать магазин
        waitForIt(); // ждем
    }

    private void setShippingAddressOnRetailerPage(String address){
        fillAddressField(address); // вводим адрес
        selectAddressSuggest(1); // выбираем первую подсказку
        clickChooseShopButton(); // жмем Выбрать магазин
        waitForIt(); // ждем
    }

    public void changeShippingAddress(String address){
        printMessage("Changing shipping address");
        clickChangeAddressButton();
        waitForIt(); // ждем
        setShippingAddressOnRetailerPage(address);
    }

    public void clickChangeAddressButton(){
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div[1]/div/div/button"));
    }

    public void fillAddressField(String address){
        fillField(By.id("ship_address"),address);
    }

    public boolean isAnySuggestsAvailable(){
        return isElementPresent(By.id("downshift-1-item-0"));
    }

    public void selectAddressSuggest(int position){
        click(By.id("downshift-1-item-" + (position-1)));
    }

    public void clickChooseShopButton(){
        if(currentURL().equals(baseUrl)){
            click(By.xpath("/html/body/div[5]/div[1]/div[3]/div/div[2]/div/form/button"));
        } else {
            click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div[1]/div/div/form/button"));
        }
    }

    public void selectShop(int position){
        if(currentURL().equals(baseUrl + "stores")) {
            try {
                click(By.xpath("/html/body/div/div/div[2]/a[" + position + "]"));
            } catch (NoSuchElementException e) {
                printMessage("Can't select shop by the given position" + position);
                closeShopsList();
            }
            waitForIt();
        } else {
            openShopsList();
            try {
                click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[2]/div/div[2]/span/div/div/div[2]/a[" + position + "]"));
            } catch (NoSuchElementException e) {
                printMessage("Can't select shop by the given position" + position);
                closeShopsList();
            }
            waitForIt();
        }
    }

    public void changeShopSelection(int position){
        openShopsList();
        if(isAnyShopsAvailable()) {
            selectShop(position);
        } else {
            printMessage("Can't change shop because there is no shops available!");
            closeShopsList();
        }
    }


    /**
     * Open list of available shops by clicking "Shops" button on address bar
     * Method is not suitable for landing page because there is no such button
     */
    public void openShopsList(){
        if(!currentURL().equals(baseUrl)) {
            click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[2]/div"));
        } else {
            printMessage("Can't open shops list on the landing page!\n");
        }
    }

    /**
     * Close list of available shops by clicking "X" button
     */
    public void closeShopsList(){
        if(currentURL().equals(baseUrl + "stores")) {
            click(By.xpath("/html/body/div/div/div[1]/button"));
        } else {
            click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[2]/div/div[2]/span/div/div/div[1]/button"));
        }
    }

    /**
     * Find out if there shipping address is set by checking address bar
     * Address bar is not present on landing page, so method is only suitable for inner pages
     */
    public boolean isShippingAddressSet() {
        String XPATH = "//*[@id='wrap']/div[1]/div/div/div/div[1]/div/div/text()";
        return isElementPresent(By.xpath(XPATH)) && getText(By.xpath(XPATH)).equals("Вы выбрали адрес");
    }

    /**
     * Find out if shipping address is empty by checking address bar
     * Address bar is not present on landing page, so method is only suitable for inner pages
     */
    public boolean isShippingAddressEmpty() {
        String XPATH = "//*[@id='wrap']/div[1]/div/div/div/div[1]/div/div/div[2]";
        return isElementPresent(By.xpath(XPATH)) && getText(By.xpath(XPATH)).equals("Укажите ваш адрес для отображения доступных магазинов");
    }

    public boolean isAnyShopsAvailable(){
        final String placeholderText = "Нет доступных магазинов по выбранному адресу";
        final By placeholderLocator = By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[2]/div/div[2]/span/div/div/div[2]/div");
        final By placeholderLocatorLanding = By.xpath("/html/body/div/div/div[2]/div");

        //TODO переписать условия чтобы правильно определялось и наличие и отсутствие магазинов
        if(currentURL().equals(baseUrl + "stores")){
            if (isElementPresent(placeholderLocatorLanding))
                if (!getText(placeholderLocatorLanding).equals(placeholderText)) return true;
            return false;
        } else {
            return isElementPresent(placeholderLocator) && !getText(placeholderLocator).equals(placeholderText);
        }

    }

    public String getCurrentShippingAddress(){
        if(isShippingAddressSet()){
            return getText(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div[1]/div/div/span[2]"));
        }
        else return null;
    }

    // TODO isShopSelected - метод возвращающий логическое значение выбран ли магазин



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
        //TODO возвращать int или float
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