package ru.instamart.reforged.stf.block.header;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;
import ru.instamart.reforged.stf.drawer.account_menu.AccountMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.drawer.StoreSelector;
import ru.instamart.reforged.stf.frame.address.Address;

public interface AuthoredHeaderElement {

    Address addressDrawer = new Address();
    Cart cartFrame = new Cart();
    AccountMenu accountMenu = new AccountMenu();
    StoreSelector storeSelectorDrawer = new StoreSelector();

    Button delivery = new Button(By.xpath("//button[@data-qa='ship_selector_type_delivery']"));
    Button pickup = new Button(By.xpath("//button[@data-qa='ship_selector_type_pickup']"));
    Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"));
    Button firstSelectAddress = new Button(By.xpath("//button[@data-qa='shipping_method_button']"));
    Element enteredAddress = new Element(By.xpath("//span[@data-qa='current-ship-address']"));

    Link forB2B = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Для бизнеса')]"));
    Link forBrands = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Для производителей')]"));
    Link howWeWork = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Как мы работаем')]"));
    Link contacts = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Контакты')]"));
    Link help = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Помощь')]"));
    Link deliveryAndPayment = new Link(By.xpath("//a[@data-qa='header-navbar-button']//span[contains(text(), 'Доставка')]"));

    Button categoryMenu = new Button(By.xpath("//button[@data-qa='catalog-button']"));
    Button storeSelector = new Button(By.xpath("//button[@data-qa='open-store-selector-button']"));
    Input searchInput = new Input(By.xpath("//div[@data-qa='search']/form/input"));
    Button searchButton = new Button(By.xpath("//div[@data-qa='search']/form/button"));
    DropDown searchDropDown = new DropDown(By.xpath("//div[@data-qa='offer']"));

    Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"));
    Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"));
    Link favorite = new Link(By.xpath("//a[@data-qa='favorites-link']"));
    Link orders = new Link(By.xpath("//a[@data-qa='shipments-link']"));
    Button login = new Button(By.xpath("//button[@data-qa='login-button_button']")); // кнопка в хедере, но без авторизации
}
