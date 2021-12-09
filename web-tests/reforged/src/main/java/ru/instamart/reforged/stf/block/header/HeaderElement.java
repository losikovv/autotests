package ru.instamart.reforged.stf.block.header;

import org.openqa.selenium.By;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.drawer.account_menu.AccountMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.drawer.store_selector.StoreSelector;
import ru.instamart.reforged.stf.frame.address.Address;

public interface HeaderElement {

    Address addressDrawer = new Address();
    Cart cartFrame = new Cart();
    AccountMenu accountMenu = new AccountMenu();
    StoreSelector storeSelectorDrawer = new StoreSelector();

    Element header = new Element(By.xpath("//header"), "контейнер для шапки");

    Link logo = new Link(By.xpath("//header//i"), "лого на сайте");

    Button delivery = new Button(By.xpath("//button[@data-qa='ship_selector_type_delivery']"));
    Button pickup = new Button(By.xpath("//button[@data-qa='ship_selector_type_pickup']"));
    Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    Button firstSelectAddress = new Button(By.xpath("//button[@data-qa='shipping_method_button']"));
    Element hotlineWorkHoursText = new Element(By.xpath("//header//span[text()='" + TestVariables.CompanyParams.companyHotlineWorkhoursShort + "']"));
    Element enteredAddress = new Element(By.xpath("//span[@data-qa='current-ship-address']"), "Лэйбл отображающий введенный адрес в шапке");

    Link forB2B = new Link(By.xpath("//a[contains(@href, '/transfer/to_b2b')]"));
    Link forBrands = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Для производителей')]"));
    Link howWeWork = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Как мы работаем')]"));
    Link contacts = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Контакты')]"));
    Link help = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Помощь')]"));
    Link deliveryAndPayment = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Доставка')]"));

    Button categoryMenu = new Button(By.xpath("//button[@data-qa='catalog-button']"));
    Button storeSelector = new Button(By.xpath("//button[@data-qa='open-store-selector-button']"));
    Element searchContainer = new Element(By.xpath("//div[@data-qa='search']"), "Контейнер поиска");
    Input searchInput = new Input(By.xpath("//div[@data-qa='search']//input"), "Инпут поиска");
    Button searchButton = new Button(By.xpath("//div[@data-qa='search']//button[@type='submit']"), "Кнопка поиска");
    DropDown searchDropDown = new DropDown(By.xpath("//div[@data-qa='offer']"));
    Element taxonCategories = new Element(By.xpath("//div[@class='header-search-list__categories']"), "Список категорий в подсказке поиска");
    Element taxonFirstCategory = new Element(By.xpath("//div[@class='header-search-list-category']"), "Первая категория в подсказке поиска");
    Element minAmountAlert = new Element(By.xpath("//div[@class='alerts']//div[contains(@class, 'alert--error')]"), "Алерт минимальной суммы заказа в шапке");
    Element authOrRegAlert = new Element(By.xpath("//div[@class='alerts']//span[contains(text(), 'войти или зарегистрироваться')]"), "Алерт регистрации или авторизации в шапке");

    ElementCollection taxonCategoriesCollection = new ElementCollection(By.xpath("//div[@data-qa='taxon']"), "Список категорий в подсказке поиска");
    ElementCollection taxonCategoriesCollectionImagesAlco = new ElementCollection(By.cssSelector("svg.header-search-list-category__icon-content"), "Картинки 18+ списка категорий в подсказке поиска");

    ElementCollection searchSuggestsCollection = new ElementCollection(By.xpath("//div[@data-qa='offer']"), "Товарные подсказки поиска");
    ElementCollection searchSuggestsCollectionImagesAlco = new ElementCollection(By.cssSelector("svg.header-search-list-product__image-content"), "Картинки 18+ в подсказках поиска");

    Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"), "кнопка профиль пользователя в хэдере");
    Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"), "кнопка корзины");
    Link favorite = new Link(By.xpath("//a[@data-qa='favorites-link']"));
    Link favoriteWithOutAuth = new Link(By.xpath("//button[@data-qa='favorites-link']"), "кнопка избранного для неавторизованных");
    Link orders = new Link(By.xpath("//a[@data-qa='shipments-link']"));
    Button login = new Button(By.xpath("//button[@data-qa='login-button_button']")); // кнопка в хедере, но без авторизации

    Element cartNotification = new Element(By.xpath("//div[@class='notification']"), "Алерт добавления товара в корзину");

}
