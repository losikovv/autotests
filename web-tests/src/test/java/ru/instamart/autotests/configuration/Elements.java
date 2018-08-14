package ru.instamart.autotests.configuration;

import org.openqa.selenium.By;



// Используемые web-элементы



public abstract class Elements {
    static String text;
    static By locator;

    public static String getText() {
        return text;
    }

    public static By getLocator() {
        return locator;
    }


    /** Шапка сайта */

    public static class Header extends Elements {

        Header(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static Header setAddressButton() {
            text = "Ввести адрес";
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/button");
            return new Header(text, locator);
        }

        public static Header changeAddressButton() {
            text = "Изменить";
            locator = By.className("ship-address-selector__edit-btn");
            return new Header(text, locator);
        }

        public static Header currentAddress() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/span");
            return new Header(null, locator);
        }

        public static Header profileButton(){
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div");
            return new Header(null, locator);
        }

        public static Header cartButton(){
            locator = By.className("open-new-cart");
            return new Header(null, locator);
        }

        public static Header changeStoreButton(){
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/div[1]/div[3]/div/div[1]");
            return new Header(null, locator);
        }

        public static Header searchField(){
            locator = By.className("header-search__inp");
            return new Header(null, locator);
        }

        public static Header searchButton(){
            locator = By.className("header-search__btn");
            return new Header(null, locator);
        }

    }


    /** Всплывающее меню "Профиль" */

    public static class AccountMenu extends Elements {

        AccountMenu(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static AccountMenu header() {
            locator = By.className("account-menu__header");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu profileButton() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[3]/a");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu ordersButton() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[4]/a");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu termsButton() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[5]/a");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu blogButton() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[6]/a");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu logoutButton() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[8]/a");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu deliveryLink() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[1]");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu paymentLink() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[2]");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu faqLink() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[3]");
            return new AccountMenu(null, locator);
        }

        public static AccountMenu contactsLink() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[2]/div/div[10]/button[4]");
            return new AccountMenu(null, locator);
        }

    }


    /** Модалка авторизации/регистрации */

    public static class AuthModal extends Elements {

        AuthModal(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static AuthModal submitButton() {
            locator = By.className("auth-modal__button");
            return new AuthModal(null, locator);
        }

    }


    /** Адресные модалки Феникса */

    public static class AddressModal extends Elements {

        AddressModal(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static AddressModal header() {
            locator = By.className("address-modal__header");
            return new AddressModal(null, locator);
        }

        public static AddressModal closeButton() {
            locator = By.className("modal-container__close");
            return new AddressModal(null, locator);
        }

        public static AddressModal addressField() {
            locator = By.id("ship_address");
            return new AddressModal(null, locator);
        }

        public static AddressModal addressSuggest() {
            locator = By.className("modal-address-autocomplete__dropdown-item");
            return new AddressModal(null, locator);
        }

        public static AddressModal saveButton() {
            text = "Сохранить";
            locator = By.xpath("//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/div/div[2]/form/button");
            return new AddressModal(text, locator);
        }

        public static AddressModal recentAddressesList() {
            locator = By.className("address-modal__addresses");
            return new AddressModal(null, locator);
        }

        public static AddressModal recentAddress() {
            locator = By.className("address-modal__address");
            return new AddressModal(null, locator);
        }

    }


    /** Селектор магазинов */

    public static class ShopSelector extends Elements {

        ShopSelector(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static ShopSelector closeButton() {
            locator = By.className("store-selector__close");
            return new ShopSelector(null, locator);
        }

        public static ShopSelector storeButton() {
            locator = By.className("store-card");
            return new ShopSelector(null, locator);
        }

        public static ShopSelector noStoresPlaceholder() {
            text = "Нет доступных магазинов по выбранному адресу";
            locator = By.className("store-selector__empty");
            return new ShopSelector(null, locator);
        }

    }


    /** Каталог товаров */
    public static class Catalog extends Elements {

        Catalog(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static Catalog emptySearchPlaceholder() {
            locator = By.className("search__noresults");
            return new Catalog(null, locator);
        }

        public static Catalog product() {
            locator = By.className("product");
            return new Catalog(null, locator);
        }

        public static Catalog firstItem() {
            locator = By.xpath("//*[@id='home']/div[2]/ul/li[1]/ul/li[1]/a");
            return new Catalog(null, locator);
        }

    }


    /** Карточка товара */

    public static class ItemCard extends Elements {

        ItemCard(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static ItemCard popup() {
            locator = By.className("product-popup");
            return new ItemCard(null, locator);
        }

        public static ItemCard closeButton() {
            locator = By.className("close");
            return new ItemCard(null, locator);
        }

        public static ItemCard plusButton() {
            locator = By.xpath("//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/span/div/div[2]/div[2]/div[2]/div/div[3]/button[2]");
            return new ItemCard(null, locator);
        }

        public static ItemCard minusButton() {
            locator = By.xpath("//*[@id='react-modal']/div/div/div/span/div[1]/div/div/div/div/span/div/div[2]/div[2]/div[2]/div/div[3]/button[1]");
            return new ItemCard(null, locator);
        }

    }


    /** Корзина */

    public static class Cart extends Elements {

        Cart(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static Cart drawer() {
            locator = By.className("new-cart");
            return new Cart(null, locator);
        }

        public static Cart closeButton() {
            locator = By.className("btn-close-cart");
            return new Cart(null, locator);
        }

        public static Cart placeholder() {
            locator = By.className("new-cart-empty");
            return new Cart(null, locator);
        }

        public static Cart checkoutButton() {
            locator = By.className("cart-checkout-link");
            return new Cart(null, locator);
        }

    }

    // TODO public static class Profile extends Elements

    // TODO public static class OrderPage extends Elements


    /** Страница 500 ошибки */

    public static class Page500 extends Elements {

        Page500(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static Page500 placeholder() {
            text = "We're sorry, but something went wrong.";
            locator = By.xpath("/html/body/div/h1");
            return new Page500(text, locator);
        }

    }


    /** Страница 404 ошибки */

    public static class Page404 extends Elements {

        Page404(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static Page404 title() {
            text = "Страница не найдена";
            locator = By.xpath("/html/body/div[3]/div/div/div/div[1]/div/div[1]");
            return new Page404(text, locator);
        }

    }



    /** Шапка админки */

    public static class HeaderAdmin extends Elements {

        HeaderAdmin(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static HeaderAdmin logoutButton() {
            locator = By.xpath("//*[@id='login-nav']/li[3]/a");
            return new HeaderAdmin(null, locator);
        }

        // TODO все кнопки корневых разделов и подразделов

    }

    /** Страница деталей заказа в админке */

    public static class OrderPageAdmin extends Elements {

        OrderPageAdmin(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static OrderPageAdmin resumeOrderButton() {
            locator = By.xpath("//*[@id='content-header']/div/div/div/div[2]/ul/li[1]/form/button");
            return new OrderPageAdmin(null, locator);
        }

        public static OrderPageAdmin canceledOrderAttribute() {
            text = "ЗАКАЗ ОТМЕНЕН";
            locator = By.xpath("//*[@id='content']/div/table/tbody/tr[3]/td/b");
            return new OrderPageAdmin(null, locator);
        }

    }

}
