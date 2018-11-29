package ru.instamart.autotests.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Config;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;
import ru.instamart.autotests.application.Addresses;

public class ShoppingHelper extends HelperBase {

    private ApplicationManager kraken;

    ShoppingHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }



    // ======= Шторка выбора магазинов =======

    /** Открыть шторку выбора магазина */
    public void openShopSelector() {
        kraken.perform().click(Elements.Site.Header.changeStoreButton());
        waitingFor(1);
    }

    /** Закрыть шторку выбора магазина */
    public void closeShopSelector() {
        kraken.perform().click(Elements.Site.ShopSelector.closeButton());
        waitingFor(1);
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
            kraken.perform().click(Elements.Site.CatalogDrawer.openCatalogButton());
            waitingFor(1);
        } else printMessage("Can't open catalog drawer - already opened");
    }

    /** Закрыть шторку каталога */
    public void closeCatalog() {
        if(isCatalogDrawerOpen()) {
            kraken.perform().click(Elements.Site.CatalogDrawer.closeCatalogButton());
            waitingFor(1);
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
        waitingFor(1);
        closeItemCard();
        waitingFor(1);
    }

    /** Открываем карточку первого товара */
    private void openFirstItemCard() {
        kraken.perform().click(Elements.Site.Catalog.firstItem());
        waitingFor(1);
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
        // TODO добавить проверку на обновление цен
        if (isElementEnabled(Elements.Site.ItemCard.plusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.plusButton());
        } else {
            printMessage("'Plus' button is disabled");
        }
    }

    /** Нажать кнопку [-] в карточке товара */
    private void hitMinusButton() {
        if (isElementDisplayed(Elements.Site.ItemCard.minusButton())) {
            kraken.perform().click(Elements.Site.ItemCard.minusButton());
        } else {
            printMessage("'Minus' button is not displayed");
        }
    }

    /** Закрыть карточку товара */
    private void closeItemCard() {
        kraken.perform().click(Elements.Site.ItemCard.closeButton());
    }



    // ======= Корзина =======

    /** Определить открыта ли корзина */
    public boolean isCartOpen() {
        waitingFor(1); // Пауза, на случай если штокра медленно отображается
        return isElementDisplayed(Elements.Site.Cart.drawer());
    }

    /** Открыть корзину */
    public void openCart() {
        if (!isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.openCartButton());
            waitingFor(1);
        }
        // DEBUG
        // else printMessage("Skip open cart, already opened");
    }

    /** Закрыть корзину */
    public void closeCart() {
        if (isCartOpen()) {
            kraken.perform().click(Elements.Site.Cart.closeButton());
            waitingFor(1);
        }
        // DEBUG
        // else printMessage("Skip close cart, already closed");
    }

    /** Определить пуста ли корзина */
    public boolean isCartEmpty() {
        openCart();
        waitingFor(1); // Пауза на случай, тормозов с корзиной
        return isElementPresent(Elements.Site.Cart.placeholder());
    }

    /**
     * Очистить корзину изменениями адреса доставки ( временный метод, пока не запилят очистку корзины )
     */
    public void dropCart() {
        String currentAddress = kraken.grab().currentShipAddress();
        String addressOne = Addresses.Moscow.defaultAddress();
        String addressTwo = Addresses.Moscow.testAddress();

        if (!isCartEmpty()) {
            closeCart();
            if (currentAddress.equals(addressOne)) {
                kraken.shipAddress().change(addressTwo);
            } else {
                kraken.shipAddress().change(addressTwo);
                kraken.shipAddress().change(addressOne);
            }
        }
        closeCart();
    }

    /** Определить активна ли кнопка "Сделать заказ" в корзине */
    public boolean isCheckoutButtonActive() {
        openCart();
        waitingFor(1); // Пауза на случай, если стостояние кнопки долго обновляется
        return isElementEnabled(Elements.Site.Cart.checkoutButton());
    }

    /** Перейти в чекаут нажатием кнопки "Сделать заказ" в корзине */
    public void proceedToCheckout() {
        openCart();
        kraken.perform().click(Elements.Site.Cart.checkoutButton());
    }

    /** Набрать корзину на минимальную сумму, достаточную для оформления заказа */
    public void grabCart() {
        openFirstItemCard();

        int quantity = (Config.minOrderSum / round(fetchText(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for minimal order : " + quantity );

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            waitingFor(1);
        }
        closeItemCard();
    }

    /** Набрать корзину на указанную сумму */
    public void grabCart(int sum) {
        openFirstItemCard();

        int quantity = (sum / round(fetchText(Elements.Site.ItemCard.price()))) + 1;
        printMessage("Quantity for " + sum + "р : " + quantity);

        for (int i = 1; i <= quantity; i++) {
            hitPlusButton();
            waitingFor(1);
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
        kraken.perform().click(By.xpath("*[@id='wrap']/div[1]/div/div/header/div[1]/div[6]/div/div[2]/div[2]/div/div[2]/div/div[2]/span/div[" + itemPosition + "]/div/div/div[1]/div[2]/div[2]"));
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
            return fetchText(By.xpath(XPATHAUTH));
        } catch (NoSuchElementException e) {
            return fetchText(By.xpath(XPATH));
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