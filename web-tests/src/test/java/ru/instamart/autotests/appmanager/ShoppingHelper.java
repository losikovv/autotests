package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Constants;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;
import ru.instamart.autotests.testdata.Addresses;


// Shopping helper
// Contains methods for all shopping operations before checkout


public class ShoppingHelper extends HelperBase {

    ShoppingHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }



    // ======= Адрес доставки =======

    /** Определяем пуст ли адрес доставки */
    public boolean isShippingAddressEmpty() {
        return isElementDetected(Elements.Site.Header.setShipAddressButton());
    }

    /** Определяем выбран ли адрес доставки */
    public boolean isShippingAddressSet() {
        if (isElementDetected((Elements.Site.Header.changeShipAddressButton()))) {
            currentShippingAddress();
            return true;
        } else {
            printMessage("Shipping address is not set\n");
            return false;
        }
    }

    /** Установить адрес доставки */
    public void setShippingAddress(String address) {
        printMessage("Setting shipping address...");

        if(currentURL().equals(baseUrl)){
            fillField(Elements.Site.LandingPage.addressField(), address);
            waitForIt(1);
            click(Elements.Site.LandingPage.addressSuggest());
            click(Elements.Site.LandingPage.selectStoreButton());
        } else {
            click(Elements.Site.Header.setShipAddressButton());
            checkAddressModal();
            fillField(Elements.Site.AddressModal.addressField(), address);
            selectAddressSuggest();
            click(Elements.Site.AddressModal.saveButton());
        }
        waitForIt(1);
    }

    /** Изменить адрес доставки */
    public void changeShippingAddress(String newAddress) {
        printMessage("Changing shipping address");
        click(Elements.Site.Header.changeShipAddressButton());
        checkAddressModal();
        fillField(Elements.Site.AddressModal.addressField(), newAddress);
        selectAddressSuggest();
        click(Elements.Site.AddressModal.saveButton());
        waitForIt(2);
    }

    /** Определить и вернуть текущий адрес доставки */
    public String currentShippingAddress() {
        printMessage("Shipping address: " + getText(Elements.Site.Header.currentShipAddress()));
        return getText(Elements.getLocator());
    }

    /** Определить показаны ли адресные саджесты */
    private boolean isAnyAddressSuggestsAvailable() {
        return isElementPresent(Elements.Site.AddressModal.addressSuggest());
    }

    /** Выбрать первый адресный саджест */
    private void selectAddressSuggest() {
        if (isAnyAddressSuggestsAvailable()) {
            click(Elements.Site.AddressModal.addressSuggest());
            waitForIt(1); // Пауза, чтобы дать время обновиться кнопке "сохранить адрес"
        } else {
            printMessage("Can't click address suggest - there are no such");
        }
    }

    /** Проверка на наличие адресной модалки адресной модалки */
    private void checkAddressModal() {
        printMessage("Modal opened: [" + getText(Elements.Site.AddressModal.header()) + "]");
    }



    // ======= Шторка выбора магазинов =======

    /** Открыть шторку выбора магазина */
    public void openShopSelector() {
        click(Elements.Site.Header.changeStoreButton());
        waitForIt(1);
    }

    /** Закрыть шторку выбора магазина */
    public void closeShopSelector() {
        click(Elements.Site.ShopSelector.closeButton());
        waitForIt(1);
    }

    /** Определить открыта ли шторка выбора магазина */
    public boolean isShopSelectorOpen() {
        return isElementDisplayed(Elements.Site.ShopSelector.drawer());
    }

    /** Определить пуст ли селектор */
    public boolean isShopSelectorEmpty() {
        return isElementDisplayed(Elements.Site.ShopSelector.placeholder());
    }



    //========= Шторка каталога ==========

    /** Открыть шторку каталога */
    public void openCatalog() {
        if(!isCatalogDrawerOpen()) {
            click(Elements.Site.CatalogDrawer.openCatalogButton());
            waitForIt(1);
        }  else printMessage("Can't open catalog drawer - already opened");
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(isCatalogDrawerOpen()) {
            click(Elements.Site.CatalogDrawer.closeCatalogButton());
            waitForIt(1);
        } else printMessage("Can't close catalog drawer - already closed");
    }

    /** Определить открыта ли шторка каталога */
    public boolean isCatalogDrawerOpen() {
        return isElementDisplayed(Elements.Site.CatalogDrawer.drawer());

    }



    // ======= Каталог =======

    public boolean isProductAvailable() {
        if(isElementPresent(Elements.Site.Catalog.product())){
            printMessage("✓ Products available");
            return true;
        } else {
            printMessage("No products available!");
            return false;
        }
    }

    /**
     * Add the first line item on the page to the shopping cart
     * Shipping address must be chosen before that
     */
    public void addFirstItemOnPageToCart() {
        openFirstItemCard();
        hitPlusButton();
        waitForIt(1);
        closeItemCard();
        waitForIt(1);
    }

    /** Открываем карточку первого товара */
    private void openFirstItemCard() {
        click(Elements.Site.Catalog.firstItem());
        waitForIt(1);
        switchToActiveElement();
    }



    // ======= Карточка товара  =======

    /** Определить открыта ли карточка товара */
    public boolean isItemCardOpen() {
        if(isElementPresent(Elements.Site.ItemCard.popup())){
            printMessage("✓ Item card open");
            return true;
        } else return false;
    }

    /** Нажать кнопку [+] в карточке товара */
    private void hitPlusButton() {
        if (isElementEnabled(Elements.Site.ItemCard.plusButton())) {
            click(Elements.Site.ItemCard.plusButton());
        } else {
            printMessage("'Plus' button is disabled");
        }
    }

    /** Нажать кнопку [-] в карточке товара */
    private void hitMinusButton() {
        if (isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            click(Elements.Site.ItemCard.minusButton());
        } else {
            printMessage("'Minus' button is not displayed");
        }
    }

    /** Закрыть карточку товара */
    private void closeItemCard() {
        click(Elements.Site.ItemCard.closeButton());
    }


    // ======= Поиск товаров =======

    /** Заполнить поле поиска */
    public void searchItem(String queryText) {
        printMessage("Searching products on query \"" + queryText + "\"...");
        fillSearchField(queryText);
        hitSearchButton();
        waitForIt(1);
    }

    /** Заполнить поле поиска */
    public void fillSearchField(String queryText) {
        fillField(Elements.Site.Header.Search.searchField(), queryText);
        waitForIt(1);
    }

    /** Нажать кнопку поиска */
    public void hitSearchButton() {
        click((Elements.Site.Header.Search.searchButton()));
    }
    /** Проверка пустого результата поиска */
    public boolean isSearchResultsEmpty() {
        if(isElementPresent(Elements.Site.Catalog.emptySearchPlaceholder())){
            printMessage("Empty search results");
            return true;
        } else return false;
    }

    /** Нажать на саджест(продуктовый/товарный) */
    public void hitSuggest(String type) {
        switch (type) {
            case "category":
                hitCategorySuggest();
                break;
            case "product":
                hitProductSuggest();
                break;
        }
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isCategorySuggestPresent() {
        return isElementPresent(Elements.Site.Header.Search.categorySuggest());
    }

    /** Нажать на категорийный саджест */
    private void hitCategorySuggest() {
        click(Elements.Site.Header.Search.categorySuggest());
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isProductSuggestPresent() {
        return isElementPresent(Elements.Site.Header.Search.productSuggest());

    }

    /** Нажать на продуктовый саджест */
    private void hitProductSuggest() {
        click(Elements.Site.Header.Search.productSuggest());
    }


    // ======= Корзина =======

    /** Определить открыта ли корзина */
    public boolean isCartOpen() {
        waitForIt(1); // Пауза, на случай если штокра медленно отображается
        return isElementDisplayed(Elements.Site.Cart.drawer());
    }

    /** Открыть корзину */
    public void openCart() {
        if (!isCartOpen()) {
            click(Elements.Site.Cart.openCartButton());
            waitForIt(1);
        } else printMessage("Skip open cart - already opened");
    }

    /** Закрыть корзину */
    public void closeCart() {
        if (isCartOpen()) {
            click(Elements.Site.Cart.closeButton());
            waitForIt(1);
        } else printMessage("Skip close cart - already closed");
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        openCart();
        waitForIt(1); // Пауза на случай, тормозов с корзиной
        return isElementPresent(Elements.Site.Cart.placeholder());
    }

    /**
     * Очистить корзину изменениями адреса доставки ( временный метод, пока не запилят очистку корзины )
     */
    public void dropCart() {
        String currentAddress = currentShippingAddress();
        String addressOne = Addresses.Moscow.defaultAddress();
        String addressTwo = Addresses.Moscow.testAddress();

        if (!isCartEmpty()) {
            closeCart();
            if (currentAddress.equals(addressOne)) {
                changeShippingAddress(addressTwo);
            } else {
                changeShippingAddress(addressTwo);
                changeShippingAddress(addressOne);
            }
        }
        closeCart();
    }

    /** Определить активна ли кнопка "Сделать заказ" в корзине */
    public boolean isCheckoutButtonActive() {
        openCart();
        waitForIt(1); // Пауза на случай, если стостояние кнопки долго обновляется
        return isElementEnabled(Elements.Site.Cart.checkoutButton());
    }

    /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
    public void proceedToCheckout() {
        openCart();
        click(Elements.Site.Cart.checkoutButton());
    }

    /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
    public void grabCart() {
        openFirstItemCard();

        int quantity = (Constants.getMinOrderSum() / round(getText(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for minimal order : " + quantity );

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            waitForIt(1);
        }
        closeItemCard();
    }

    /** Набрать корзину на указанную сумму */
    public void grabCart(int sum) {
        openFirstItemCard();

        int quantity = (sum / round(getText(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for " + sum + "р : " + quantity);

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            waitForIt(1);
        }
        closeItemCard();
    }


    //TODO
    /**
     * Удалить верхний товар в корзине
     */
    //public void deleteItem(){
    //    click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/span/div[1]/div/div/div[1]/div[1]/span"));
    //    click(By.xpath("//*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div[1]/div[2]/span/div[1]/div/div/div[1]/div[1]/button[2]"));
    //}

    //TODO

    /**
     * Удалить товар по позиции в корзине
     */
    public void deleteItem(int itemPosition) {
        click(By.xpath("*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div/div[2]/span/div[" + itemPosition + "]/div/div/div[1]/div[2]/div[2]"));
    }

    //TODO
    /**
     * Удалить все товары в корзине удаляя верхние товары
     */
    //public void deleteAllItemsInCart(){
    //   if(!isCartEmpty()){
    //      deleteItem();
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

    // TODO changeQuantity( int newQuantity) - изменить кол-во верхнего товара
    // TODO changeQuantity(int itemPosition, int newQuantity) - изменить кол-во товара по позиции

    // TODO addQuantity() - +1 кол-во верхнего товара
    // TODO addQuantity(int itemPosition) - +1 кол-во товара по позиции

    // TODO reduceQuantity() - -1 кол-во верхнего товара
    // TODO reduceQuantity(int itemPosition) - -1 кол-во товара по позиции

}