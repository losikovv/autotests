package ru.instamart.autotests.configuration;

import org.openqa.selenium.By;



// Web-элементы сайта



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

        public static Header setShipAddressButton() {
            text = "Ввести адрес";
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/button");
            return new Header(text, locator);
        }

        public static Header changeShipAddressButton() {
            text = "Изменить";
            locator = By.className("ship-address-selector__edit-btn");
            return new Header(text, locator);
        }

        public static Header currentShipAddress() {
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/div/div/div/div/div/span");
            return new Header(null, locator);
        }

        public static Header loginButton(){
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]");
            return new Header(null, locator);
        }

        public static Header profileButton(){
            //locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div");
            text = "Профиль";
            locator = By.xpath("//*[@id='wrap']/div[1]/div/div/div/header/nav/div[3]/div/div[1]/div[1]");
            return new Header(text, locator);
        }

        public static Header openCartButton(){
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

        public static AccountMenu popup() {
            locator = By.className("account-menu");
            return new AccountMenu(null, locator);
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



    /** Модалка авторизации / регистрации */

    public static class AuthModal extends Elements {

        AuthModal(String text, By locator) {
            Elements.text = text;
            Elements.locator = locator;
        }

        public static AuthModal popup() {
            locator = By.className("auth-modal");
            return new AuthModal(null, locator);
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



        public static HeaderAdmin profileButton() {
            text = "Учетная запись";
            locator = By.xpath("//*[@id='login-nav']/li[2]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin backToListButton() {
            text = "Назад к списку";
            locator = By.xpath("//*[@id='login-nav']/li[4]/a");
            return new HeaderAdmin(text,locator);

        }

        //=========== Раздел  "Заказы" и его подразделы ===========

        public static HeaderAdmin ordersButton() {
            text = "Заказы";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[1]/a/span");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin multiOrderButton() {
            text = "Мульти заказ";
            locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
            return new HeaderAdmin( text, locator);

        }

        public static HeaderAdmin exportButton() {
            text = "Export";
            locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin veerouteButton() {
            text = "Veeroute";
            locator = By.xpath("//*[@id='sub_nav']/li[3]/a");
            return new HeaderAdmin(text, locator);
        }

        // =========== Раздел Магазины и его подразделы =========

        public static HeaderAdmin storeButton() {
            text = "Магазины";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[2]/a/span");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin retailersButton() {
            text = "Ритейлеры";
            locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin zonesButton() {
            text = "Зоны";
            locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
            return new HeaderAdmin(text,locator);
        }

        // =========== Раздел Продукты и его подразделы ==========

        public static HeaderAdmin productsButton() {
            text = "Продукты";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[3]/a/span");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin subProductsButton() {
            text = "Продукты";
            locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
            return  new HeaderAdmin(text,locator);
        }

        public static HeaderAdmin productsStatsButton() {
            text = "Статистика";
            locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin optionTypesButton() {
            text = "Option Types";
            locator = By.xpath("//*[@id='sub_nav']/li[3]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin propertiesButton() {
            text = "Properties";
            locator = By.xpath("//*[@id='sub_nav']/li[4]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin prototypesButton() {
            text = "Prototypes";
            locator = By.xpath("//*[@id='sub_nav']/li[5]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin brandsButtton() {
            text = "Бренды";
            locator = By.xpath("//*[@id='sub_nav']/li[6]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin producersButton() {
            text = "Производители";
            locator = By.xpath("//*[@id='sub_nav']/li[7]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin producersCountriesButton() {
            text = "Страны производства";
            locator = By.xpath("//*[@id='sub_nav']/li[8]/a");
            return new HeaderAdmin(text,locator);
        }

        //========== Раздел Импорт и его подразделы ========

        public static HeaderAdmin importButton() {
            text = "Импорт";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[4]/a/span");
            return new HeaderAdmin(text,locator);
        }

        public static HeaderAdmin queueOfTasksButton() {
            text = "Очередь задач";
            locator = By.xpath("//*[@id='sub_nav']/li[1]/a");
            return new HeaderAdmin(text,locator);
        }

        public static HeaderAdmin importStatsButton() {
            text = "Статистика";
            locator = By.xpath("//*[@id='sub_nav']/li[2]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin archiveButton() {
            text = "Архив";
            locator = By.xpath("//*[@id='sub_nav']/li[3]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin blackListButton() {
            text = "Черный список Gm";
            locator = By.xpath("//*[@id='sub_nav']/li[4]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin categoryButton() {
            text = "Категории";
            locator = By.xpath("//*[@id='sub_nav']/li[5]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin filtersButton() {
            text = "Фильтры";
            locator = By.xpath("//*[@id='sub_nav']/li[6]/a");
            return new HeaderAdmin(text, locator);
        }

        public static HeaderAdmin importProductsButton() {
            text = "Продукты";
            locator = By.xpath("//*[@id='sub_nav']/li[7]/a");
            return new HeaderAdmin(text,locator);
        }

        public static HeaderAdmin priceButton() {
            text = "Цены";
            locator = By.xpath("//*[@id='sub_nav']/li[8]/a");
            return new HeaderAdmin(text, locator);
        }

        //========== Раздел отчеты ==============

        public static HeaderAdmin reportsButton() {
            text = "Отчеты";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[5]/a/span");
            return new HeaderAdmin(text, locator);
        }

        //========== Раздел настройки ============

         public static HeaderAdmin settingsButton() {
            text = "Настройки";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[6]/a/span");
            return new HeaderAdmin(text, locator);
         }

         //========= Раздел Маркетинг и его подразделы ===========

         public static HeaderAdmin marketingButton() {
            text = "Маркетинг";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[7]/a/span");
            return new HeaderAdmin(text, locator);
         }

         public static HeaderAdmin promoCardsButton() {
            text = "Промо карточки";
            locator = By.xpath("//*[@id='sub_nav']/li[1]/a/span");
            return new HeaderAdmin(text, locator);
         }

         public static HeaderAdmin promoActionButton() {
            text = "Промо акции";
            locator = By.xpath("//*[@id='sub_nav']/li[2]/a/span");
            return new HeaderAdmin(text, locator);
         }

         public static HeaderAdmin welcomeBannersButton() {
            text = "Welcome баннеры";
            locator = By.xpath("//*[@id='sub_nav']/li[3]/a/span");
            return new HeaderAdmin(text,locator);
         }

         public static HeaderAdmin advertisementButton() {
            text = "Реклама";
            locator = By.xpath("//*[@id='sub_nav']/li[4]/a/span");
            return new HeaderAdmin(text,locator);
         }

         public static HeaderAdmin yandexMarketButton() {
            text = "Яндекс.Маркет";
            locator = By.xpath("//*[@id='sub_nav']/li[5]/a/span");
            return new HeaderAdmin(text, locator);
         }

         public static HeaderAdmin cartsButton() {
            text = "Корзины";
            locator = By.xpath("//*[@id='sub_nav']/li[6]/a/span");
            return new HeaderAdmin(text, locator);
         }

         public static HeaderAdmin bonusCardsButton() {
            text = "Бонусные карты";
            locator = By.xpath("//*[@id='sub_nav']/li[7]/a/span");
            return new HeaderAdmin(text, locator);
         }


         public static HeaderAdmin retailersProgramsButton() {
            text = "Программы ритейлеров";
            locator = By.xpath("//*[@id='sub_nav']/li[8]/a/span");
            return new HeaderAdmin(text, locator);
         }

         public static HeaderAdmin newCitiesButton() {
            text = "Новые Города";
            locator = By.xpath("//*[@id='sub_nav']/li[9]/a/span");
            return new HeaderAdmin(text,locator);
         }

         //========== Раздел пользователи ===========

         public static HeaderAdmin usersButton() {
            text = "Пользователи";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[8]/a/span");
            return new HeaderAdmin(text, locator);
         }

         //========== Раздел Страницы ===========

         public static HeaderAdmin pagesButton() {
            text = "Страницы";
            locator = By.xpath("//*[@id='admin-menu']/div/div/ul/li[9]/a/span");
            return new HeaderAdmin(text, locator);
         }


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
