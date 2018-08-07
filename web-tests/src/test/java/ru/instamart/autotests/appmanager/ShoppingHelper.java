package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.models.EnvironmentData;
import ru.instamart.autotests.testdata.Addresses;



    // Shopping helper
    // Contains methods for all shopping operations before checkout



public class ShoppingHelper extends HelperBase {

    public ShoppingHelper(WebDriver driver, EnvironmentData environment) {
        super(driver, environment);
    }



    // ======= Адрес доставки =======

    /** Определяем пуст ли адрес доставки */
    public boolean isShippingAddressEmpty() {
        final String XPATH = "//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/text()";
        final String TEXT = "Укажите ваш адрес для отображения доступных магазинов";
        if(currentURL().equals(baseUrl)){
            printMessage("User is on the landing, so shipping address is not set");
            return true;
        }
        return isElementDetected(XPATH,TEXT);
    }

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        String TEXT = "Изменить";
        String XPATH = "//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/button";
        if(isElementDetected(XPATH,TEXT)){
            printMessage("Shipping address is set\n");
            return true;
        } else if (isShippingAddressEmpty()) {
            printMessage("Shipping address is not set\n");
            return false;
        } else {return false;}
    }



//TODO e,hfnm vtnjl - yt b
    /**
     * Set default shipping address
     */
    public void setDefaultShippingAddress(){
        final String address = "Москва, ул Люблинская, д 123"; //TODO вынести переменную в конфиг
        printMessage("Setting default shipping address: " + address);
        fillAddressField(address);
        selectAddressSuggest(1);
        //waitForIt();  // доп. задержка - повышает стабильность при тормозах на сайте
        clickChooseShopButton();
        waitForIt(2);
    }

    /**
     * Set shipping address with one in the given string
     */
    public void setShippingAddress(String address){
            printMessage("Setting new shipping address: " + address);
            fillAddressField(address);
            selectAddressSuggest(1);
            //waitForIt();  // доп. задержка - повышает стабильность при тормозах на сайте
            clickChooseShopButton();
            waitForIt(2);
    }

    /**
     * Change current shipping address with one in the given string
     */
    public void changeShippingAddress(String newAddress){
        printMessage("Changing shipping address");
        clickChangeAddressButton();
        clearAddressField();
        setShippingAddress(newAddress);
    }

    /**
     * Return current shipping address string
     */
    public String currentShippingAddress(){
        String shippingAddress = getText(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/span"));
        printMessage("Current shipping address: " + shippingAddress);
        return shippingAddress;
    }

    /**
     * Fill address field by sending a backspace key
     */
    private void clearAddressField(){ ;
        driver.findElement(By.id("header_ship_address")).sendKeys(Keys.BACK_SPACE);
    }

    /**
     * Fill address field with the given address string
     */
    private void fillAddressField(String address){
        clearAddressField();
        fillField(By.id("header_ship_address"),address);
        if(!currentURL().equals(baseUrl)) {
            waitForIt(1);
        }
    }

    /**
     * Returns true if there is at least one of the address suggests, when address field is filled
     */
    private boolean isAnyAddressSuggestsAvailable(){
        return isElementPresent(By.id("downshift-1-item-0"));
    }

    /**
     * Click on the address suggest by the given position
     */
    private void selectAddressSuggest(int position){
        if(isAnyAddressSuggestsAvailable()) {
            click(By.id("downshift-1-item-" + (position - 1)));
        } else {
            printMessage("Can't click address suggest - there are no such");
        }
    }

    /**
     * Click on the "Choose shop" button while setting the shipping address on the landing or on the retailer page
     */
    private void clickChooseShopButton(){
        if(currentURL().equals(baseUrl)){
            click(By.xpath("/html/body/div[5]/div[1]/div[3]/div/div[2]/div/form/button"));
        } else {
            click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div[1]/div/div/form/button"));
        }
    }

    /**
     * Click on the "Change" button on the shipping address bar
     */
    private void clickChangeAddressButton(){
        click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div[1]/div/div/button"));
    }



    // ======= Список магазинов =======

    public void selectShop(int position){

        // for shoplist page from landing
        if(currentURL().equals(baseUrl + "stores")) {
            try {
                click(By.xpath("/html/body/div/div/div[3]/a[" + position + "]"));
            } catch (NoSuchElementException e) {
                printMessage("Can't select shop by the given position" + position);
                closeShopsList();
            }
            waitForIt(2);
        }

        // for shoplist on the retailer page
        else {
            openShopsList();
            try {
                click(By.xpath("/html/body/div[8]/div/div/div[3]/a[" + position + "]"));
            } catch (NoSuchElementException e) {
                printMessage("Can't select shop by the given position" + position);
                closeShopsList();
            }
            waitForIt(2);
        }
    }

    // TODO протестить метод
    public void changeShopSelection(int position){
        openShopsList();
        if(!isShopsListEmpty()) { //TODO добавить проверку наличия магазов?
            selectShop(position);
        } else {
            printMessage("Can't change shop because there is no shops available!");
            closeShopsList();
        }
    }

    /**
     * Open list of available shops by clicking shop selector button on the search bar
     * Method is not suitable for the landing page because there is no such button
     */
    public void openShopsList(){
        if(!currentURL().equals(baseUrl)) {
            click(By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/div[1]/div[3]/div/div[1]"));
        } else {
            printMessage("Can't open shops list on the landing page!\n");
        }
    }

    /**
     * Close list of available shops by clicking the "X" button
     */
    public void closeShopsList(){
        if(currentURL().equals(baseUrl + "stores")) {
            click(By.xpath("/html/body/div/div/div[2]/button"));
        } else {
            click(By.xpath("/html/body/div[8]/div/div/div[2]/button"));
        }
    }

    public boolean isShopsListOpen(){
        if(currentURL().equals(baseUrl + "stores")){
            return isElementPresent(By.className("store-selector-page"));
        }else {
            return isElementPresent(By.className("store-selector"));
        }
    }

    public boolean isShopsListEmpty(){
        final String placeholderText = "Нет доступных магазинов по выбранному адресу";
        final By placeholderLocator = By.xpath("/html/body/div[8]/div/div/div[3]/div");
        final By placeholderLocatorLanding = By.xpath("/html/body/div/div/div[3]/div");

        if(currentURL().equals(baseUrl + "stores")){
            return isElementPresent(placeholderLocatorLanding) && getText(placeholderLocatorLanding).equals(placeholderText);
        } else {
            return isElementPresent(placeholderLocator) && getText(placeholderLocator).equals(placeholderText);
        }
    }

    public boolean isAnyShopsAvailable(){
        return isElementDisplayed(By.className("store-card"));
    }



    // ======= Каталог =======

    public boolean isProductPresent(){
        return isElementPresent(By.className("product"));
    }

    /**
     * Add the first line item on the page to the shopping cart
     * Shipping address must be chosen before that
     */
    public void addFirstItemOnPageToCart(){
        openFirstItemCard();
        hitPlus();
        closeItemCard();
        waitForIt(1);
    }

    /**
     * Add the given quantity of the first line item on the page to the shopping cart
     * Shipping address must be chosen before that
     */
    public void addFirstItemOnPageToCart(int quantity){
        openFirstItemCard();
        for (int i = 1; i <= quantity; i++) {
            hitPlus();
            waitForIt(1);
        }
        closeItemCard();
        waitForIt(1);
    }

    /**
     * Открываем карточку первого товара на витрине ритейлера
     */
    private void openFirstItemCard() {
        click(By.xpath("//*[@id='home']/div[2]/ul/li[1]/ul/li[1]/a"));
        waitForIt(1);
        swithchToActiveElement();
    }



    // ======= Карточка товара  =======

    private void hitPlus() {
        final String XPATH = "//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/span/div/div[2]/div[2]/div[2]/div/div[3]/button[2]";
        if(isElementEnabled(By.xpath(XPATH))) {
            click(By.xpath(XPATH));
        } else {
            printMessage("'Plus' button is disabled");
        }
    }

    private void hitMinus() {
        final String XPATH = "//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/span/div/div[2]/div[2]/div[2]/div/div[3]/button[1]";
        if(isElementDisplayed(By.xpath(XPATH))) {
            click(By.xpath(XPATH));
        } else {
            printMessage("'Minus' button is not displayed");
        }
    }

    private void closeItemCard() {
        click(By.className("close"));
    }

    public boolean isItemCardOpen(){
        return isElementPresent(By.className("product-popup"));
    }



    // ======= Поиск товаров =======

    public void searchItem(String queryText) {
        printMessage("Searching for \"" + queryText + "\"...");
        fillSearchField(queryText);
        hitSearchButton();
        waitForIt(1);
    }

    public void fillSearchField(String queryText) {
        //click(By.className("header-search-wrapper"));
        fillField(By.name("search"), queryText);
        waitForIt(1);
    }

    public void hitSuggest(String type) {
        switch (type) {
            case "category":
                hitCategorySuggest();
            case "product":
                hitProductSuggest();
        }
    }

    public boolean isCategorySuggestPresent(){
        return isElementPresent(By.className("results__categories"));
    }

    //TODO переделать локатор
    public void hitCategorySuggest() {
        driver.findElement(By.xpath("(//input[@name='keywords'])[2]")).sendKeys(Keys.TAB); // костыль
        click((By.className("categories__item")));
    }

    public boolean isProductSuggestPresent(){
        return isElementPresent(By.className("products_item"));
    }

    //TODO переделать локатор
    public void hitProductSuggest() {
        click((By.className("products_item")));
    }

    public void hitSearchButton() {
        click((By.xpath("(//button[@type='sumbit'])[2]")));
    }

    public boolean isSearchResultsEmpty(){
        return isElementPresent(By.className("search__noresults"));
    }



    // ======= Корзина =======

        /**
         * Метод, определяющий открыта ли корзина
         */
        public boolean isCartOpen(){
            return isElementPresent(By.className("new-cart"));
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
            if(!isCartOpen()){ openCart(); }
            return isElementPresent(By.className("new-cart-empty"));
        }

        /** Временный метод, очищающий корзину изменениями адреса доставки, пока не запилят очистку корзины */
        public void dropCart(){
            if(!isCartEmpty()) {
                if(isCartOpen()){ closeCart(); }

                String currentAddress = currentShippingAddress();
                String addressOne = Addresses.get("default");
                String addressTwo = Addresses.get("non-default");

                if (currentAddress.equals(addressOne)) {
                    changeShippingAddress(addressTwo);
                } else {
                    changeShippingAddress(addressTwo);
                    changeShippingAddress(addressOne);
                }
            }

            if(isCartOpen()){ closeCart(); }
        }

        /** Возвращаем активна ли кнопка "Сделать заказ" в корзине */
        public boolean isCheckoutButtonActive(){
            if(!isCartOpen()){
                openCart();
                waitForIt(1);
            }
            return isElementEnabled(By.className("cart-checkout-link"));
        }

        /**
        * Нажимаем кнопку "Сделать заказ" в корзине и переходим в чекаут
        */
        public void proceedToCheckout(){
            click(By.className("cart-checkout"));
        }

        /**
        * Набрать корзину на минимальную сумму, достаточную для оформления заказа
        */
        public void grabCartWithMinimalOrderSum(){
            addFirstItemOnPageToCart(10);
            //waitForIt(1);
            openCart();
            if(!isCheckoutButtonActive()){
                closeCart();
                grabCartWithMinimalOrderSum();
            }
        }

        // TODO grabCart(int sum) - ннабрать корзину на переданную сумму


        //TODO
        /**
         * Удалить верхний товар в корзине
         */
        //public void deleteTopItem(){
        //    click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/span/div[1]/div/div/div[1]/div[1]/span"));
        //    click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/span/div[1]/div/div/div[1]/div[1]/button[2]"));
        //}

        //TODO
        /**
         * Удалить товар по позиции в корзине
         */
        public void deleteItem(int itemPosition){
            click(By.xpath("*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div/div[2]/span/div[" + itemPosition + "]/div/div/div[1]/div[2]/div[2]"));
        }

        //TODO
        /**
         * Удалить все товары в корзине удаляя верхние товары
         */
        //public void deleteAllItemsInCart(){
        //   if(!isCartEmpty()){
        //      deleteTopItem();
        //      deleteAllItemsInCart();
        //   }
        //}

        /**
         * Get the cart sum from the 'Make order' button
         */
        //TODO возвращать int или float
        public String getCartTotal() throws NoSuchElementException {
            final String XPATHAUTH = "/html/body/div[9]/div/div[2]/div/div[3]/form/button/div/div[2]/div"; // авторизован
            final String XPATH = "/html/body/div[8]/div/div[2]/div/div[3]/form/button/div/div[2]/div"; // неавторизован
            try {
                return getText(By.xpath(XPATHAUTH));
            } catch (NoSuchElementException e) {
                return getText(By.xpath(XPATH));
            }
        }

        // TODO clearCart - очистить корзину (удалить все товары сразу кнопкой)

        // TODO changeTopItemQuantity( int newQuantity) - изменить кол-во верхнего товара
        // TODO changeItemQuantity(int itemPosition, int newQuantity) - изменить кол-во товара по позиции

        // TODO addTopItemQuantity() - +1 кол-во верхнего товара
        // TODO addItemQuantity(int itemPosition) - +1 кол-во товара по позиции

        // TODO reduceTopItemQuantity() - -1 кол-во верхнего товара
        // TODO reduceItemQuantity(int itemPosition) - -1 кол-во товара по позиции

        // TODO методы для мультизаказа

}