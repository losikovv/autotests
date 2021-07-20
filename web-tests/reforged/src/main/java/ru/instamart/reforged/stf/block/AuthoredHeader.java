package ru.instamart.reforged.stf.block;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.stf.drawer.AccountMenu;
import ru.instamart.reforged.stf.drawer.Cart;
import ru.instamart.reforged.stf.drawer.StoreSelector;
import ru.instamart.reforged.stf.frame.Address;

public final class AuthoredHeader {

    private final Address addressDrawer = new Address();
    private final Cart cartFrame = new Cart();
    private final AccountMenu accountMenu = new AccountMenu();
    private final StoreSelector storeSelectorDrawer = new StoreSelector();

    private final Button delivery = new Button(By.xpath("//button[@data-qa='ship_selector_type_delivery']"));
    private final Button pickup = new Button(By.xpath("//button[@data-qa='ship_selector_type_pickup']"));
    private final Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    private final Button firstSelectAddress = new Button(By.xpath("//button[@data-qa='shipping_method_button']"));

    private final Link forB2B = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Для бизнеса')]"));
    private final Link forBrands = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Для производителей')]"));
    private final Link howWeWork = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Как мы работаем')]"));
    private final Link contacts = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Контакты')]"));
    private final Link help = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Помощь')]"));
    private final Link deliveryAndPayment = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Доставка')]"));

    private final Button categoryMenu = new Button(By.xpath("//button[@data-qa='catalog-button']"));
    private final Button storeSelector = new Button(By.xpath("//button[@data-qa='open-store-selector-button']"));
    private final Input searchInput = new Input(By.xpath("//div[@data-qa='search']/form/input"));
    private final Button searchButton = new Button(By.xpath("//div[@data-qa='search']/form/button"));
    private final DropDown searchDropDown = new DropDown(By.xpath("//div[@data-qa='offer']"));

    private final Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"));
    private final Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"));
    private final Link favorite = new Link(By.xpath("//a[@data-qa='favorites-link']"));
    private final Link orders = new Link(By.xpath("//a[@data-qa='shipments-link']"));
    private final Button login = new Button(By.xpath("//button[@data-qa='login-button_button']")); // кнопка в хедере, но без авторизации

    public Cart interactCart() {
        return cartFrame;
    }

    public Address interactAddress() {
        return addressDrawer;
    }

    public AccountMenu interactAccountMenu() {
        return accountMenu;
    }

    public StoreSelector interactStoreSelector() {
        return storeSelectorDrawer;
    }

    @Step("Выбрать магазин")
    public void clickToStoreSelector() {
        storeSelector.click();
    }

    @Step("Ввести текст для поиска {0}")
    public void fillSearch(final String text) {
        searchInput.fill(text);
    }

    @Step("Выбрать элемент поиска")
    public void selectSearch() {
        searchDropDown.selectAny();
    }

    @Step("Открыть мень профиля")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Открыть страницу Избранное")
    public void clickToFavorite() {
        favorite.click();
    }

    @Step("Открыть корзину")
    public void clickToCart() {
        cart.click();
    }

    @Step("Открыть страницу заказов")
    public void clickToOrders() {
        orders.click();
    }

    @Step("Нажать войти")
    public void clickToLogin() {
        login.click();
    }

    @Step("Нажать на Доставка")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажать на самовывоз")
    public void clickToPickup() {
        pickup.click();
    }

    @Step("Нажать на Выберите адрес доставки(первый выбор адреса)")
    public void clickToSelectAddressFirstTime() {
        firstSelectAddress.click();
    }

    @Step("Нажать на выбор адреса")
    public void clickToSelectAddress() {
        selectAddress.click();
    }

    @Step("Открыть страницу Ваши компании")
    public void clickToForB2B() {
        forB2B.click();
    }

    @Step("Открыть лендинг для брендов")
    public void clickToForBrands() {
        forBrands.click();
    }

    @Step("Открыть страницу Как мы работаем")
    public void clickToHowWeWork() {
        howWeWork.click();
    }

    @Step("Открыть страницу Контакты")
    public void clickToContacts() {
        contacts.click();
    }

    @Step("Открыть страницу FAQ")
    public void clickToHelp() {
        help.click();
    }

    @Step("Открыть страницу Доставка и оплата")
    public void clickToDeliveryAndPayment() {
        deliveryAndPayment.click();
    }

    @Step("Открыть каталог")
    public void clickToCategoryMenu() {
        categoryMenu.click();
    }
}
