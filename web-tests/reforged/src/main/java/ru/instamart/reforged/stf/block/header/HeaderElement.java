package ru.instamart.reforged.stf.block.header;

import org.openqa.selenium.By;
import ru.instamart.kraken.data.TestVariables;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.drawer.account_menu.AccountMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.TransferCartModal;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.store_selector.StoreSelector;

public interface HeaderElement {

    Address addressDrawer = new Address();
    Cart cartFrame = new Cart();
    AccountMenu accountMenu = new AccountMenu();
    StoreSelector storeSelectorDrawer = new StoreSelector();
    TransferCartModal transferCartModal = new TransferCartModal();

    Element header = new Element(By.xpath("//header"), "контейнер для шапки");

    Link logo = new Link(By.xpath("//header//i"), "лого на сайте");

    Button delivery = new Button(By.xpath("//button[@data-qa='ship_selector_type_delivery']"), "кнопка доставки");
    Button pickup = new Button(By.xpath("//button[@data-qa='ship_selector_type_pickup']"), "empty");
    Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"), "empty");
    Button firstSelectAddress = new Button(By.xpath("//button[@data-qa='shipping_method_button']"), "empty");
    Element hotlineWorkHoursText = new Element(By.xpath("//header//span[text()='" + TestVariables.CompanyParams.companyHotlineWorkhoursShort + "']"), "empty");
    Element enteredAddress = new Element(By.xpath("//span[@data-qa='current-ship-address']"), "Лэйбл отображающий введенный адрес в шапке");
    Element nextDelivery = new Element(By.xpath("//b[contains(.,'Ближайшая доставка')]/span[3]"), "Ближайшая доставка");

    Link forB2B = new Link(By.xpath("//a[contains(@href, '/transfer/to_b2b')]"), "empty");
    Link buyForBusiness = new Link(By.xpath("//div[contains(@class,'header-navbar__b2b-showcase-button')]/*"), "Ссылка 'Покупайте для бизнеса'");
    Link forBrands = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Для производителей')]"), "empty");
    Link howWeWork = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Как мы работаем')]"), "empty");
    Link contacts = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Контакты')]"), "empty");
    Link help = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Помощь')]"), "empty");
    Link deliveryAndPayment = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Доставка')]"), "empty");

    Button categoryMenu = new Button(By.xpath("//button[@data-qa='catalog-button']"), "кнопка 'Каталог'");
    Button storeSelector = new Button(By.xpath("//a[@href='/']"), "Кнопка 'Все магазины'");

    Element searchContainer = new Element(By.xpath("//div[@data-qa='search']"), "Контейнер поиска");
    Input searchInput = new Input(By.xpath("//input[@data-qa='suggester_header_form_input']"), "Инпут поиска");
    Button searchInputResetButton = new Button(By.xpath("//button[@data-qa='suggester_header_form_reset_button']"), "Кнопка очистки инпута поиска");
    Button searchButton = new Button(By.xpath("//button[@data-qa='suggester_header_form_search_button']"), "Кнопка запуска поиска");
    Element searchSuggester = new Element(By.xpath("//div[@data-qa='suggester_header_dropdown_content']"), "Саджестор поиска");
    Button scrollTabHeadersLeft = new Button(By.xpath("//button[@data-qa='suggester_header_prev_slide']"), "Кнопка пролистать вкладки категорий влево");
    Button scrollTabHeadersRight = new Button(By.xpath("//button[@data-qa='suggester_header_next_slide']"), "Кнопка пролистать вкладки категорий вправо");
    ElementCollection suggesterTabHeaders = new ElementCollection(By.xpath("//button[contains(@data-qa,'suggester_header_tab_')]"), "Вкладки категорий в саджесторе");
    ElementCollection suggesterFirstTabItems = new ElementCollection(By.xpath("//div[@data-qa='suggester_header_tab_panel_0']/div[contains(@data-qa,'suggester_header_item_')]"), "Результаты поиска на первой вкладке саджестора");
    ElementCollection suggesterSecondTabItems = new ElementCollection(By.xpath("//div[@data-qa='suggester_header_tab_panel_1']/div[contains(@data-qa,'suggester_header_item_')]"), "Результаты поиска на второй вкладке саджестора");
    Link showAllResults = new Link(By.xpath("//a[@data-qa='suggester_header_show_all_link']"), "Кнопка 'Показать все ХХ результаты'");

    Element alert = new Element(By.xpath("//div[@class='alert alert--error']"), "Тултип-сообщение об ошибке");
    Element minAmountAlert = new Element(By.xpath("//div[@class='alerts']//div[contains(@class, 'alert--error')]"), "Алерт минимальной суммы заказа в шапке");
    Element authOrRegAlert = new Element(By.xpath("//div[@class='alerts']//span[contains(text(), 'войти или зарегистрироваться')]"), "Алерт регистрации или авторизации в шапке");

    ElementCollection taxonCategoriesCollection = new ElementCollection(By.xpath("//div[@data-qa='taxon']"), "Список категорий в подсказке поиска");
    ElementCollection taxonCategoriesCollectionImagesAlco = new ElementCollection(By.cssSelector("svg.header-search-list-category__icon-content"), "Картинки 18+ списка категорий в подсказке поиска");
    ElementCollection searchSuggestsCollectionImagesAlco = new ElementCollection(By.xpath("//div[@data-qa='suggester_header_tab_panel_0']//img[contains(@src,'adult-warning')]"), "Картинки 18+ в подсказках поиска");

    Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"), "кнопка профиль пользователя в хэдере");
    Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"), "кнопка корзины");
    Link favorite = new Link(By.xpath("//a[@data-qa='favorites-link']"), "empty");
    Link favoriteWithOutAuth = new Link(By.xpath("//button[@data-qa='favorites-link']"), "кнопка избранного для неавторизованных");
    Link orders = new Link(By.xpath("//a[@data-qa='shipments-link']"), "Кнопка 'Заказы'");
    Button login = new Button(By.xpath("//button[@data-qa='login-button_button']"), "empty"); // кнопка в хедере, но без авторизации

    Element cartNotification = new Element(By.xpath("//div[@class='notification']"), "Алерт добавления товара в корзину");

}
