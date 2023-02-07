package ru.instamart.reforged.stf.block.header_multisearch;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.address.AddressLarge;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

public interface MultisearchHeaderElement {

    Address addressModal = new Address();
    AddressLarge addressLargeModal = new AddressLarge();
    ProductCard productCard = new ProductCard();
    Cart cart = new Cart();

    Input multisearch = new Input(By.xpath("//input[@data-qa='multisearch_form_input']"), "Поле ввода межритейлерного поиска");
    Button startSearch = new Button(By.xpath("//button[@data-qa='multisearch_form_search_button']"), "Кнопка 'лупа' межритейлерного поиска");
    Button resetSearch = new Button(By.xpath("//button[@data-qa='multisearch_form_reset_button']"), "Кнопка 'X' поиск");
    Element multiSearchDropdown = new Element(By.xpath("//div[@data-qa='multisearch_dropdown_content']"), "Саджестор межритейлерного поиска");
    ElementCollection retailersInDropdown = new ElementCollection(By.xpath("//div[@data-qa='multisearch_dropdown_content']//li[./div[contains(@class,'StoresItem')]]"), "Вкладки магазинов в саджесторе межритейлерного поиска");
    ElementCollection productsInActiveTab = new ElementCollection(By.xpath("//div[contains(@class,'Tabs_active')]//a[contains(@class,'ProductCard_link')]"), 20, "Найденные продукты в саджесторе");
    ElementCollection productsTitleInActiveTab = new ElementCollection(By.xpath("//div[contains(@class,'Tabs_active')]//div[contains(@data-qa,'multisearch_item')]//h3[contains(@class,'ProductCard_title')]"), "Названия найденных продуктов в саджесторе");
    ElementCollection productsLinkInActiveTab = new ElementCollection(By.xpath("//div[contains(@class,'Tabs_active')]//a"), "Ссылка на найденный продукт в саджесторе");
    ElementCollection productsAddToCart = new ElementCollection(By.xpath("//div[contains(@class,'Tabs_active')]//button[@title = 'Добавить в корзину']"), "Кнопки 'Добавить в корзину' найденных продуктов в саджесторе");
    ElementCollection productsRemoveFromCart = new ElementCollection(By.xpath("//div[contains(@class,'Tabs_active')]//button[@title = 'Убрать из корзины']"), "Кнопки 'Убрать из корзины' найденных продуктов в саджесторе");
    Button showAllResults = new Button(By.xpath("//a[@data-qa='multisearch_show_all_link']"), "Кнопка 'Показать все ХХХ результатов'");

    Button delivery = new Button(By.xpath("//button[@aria-controls='by_courier-tab']"), "Кнопка 'Доставка'");
    Element deliveryButtonActive = new Element(By.xpath("//button[@aria-controls='by_courier-tab'][@aria-selected='true']"), "Активная кнопка 'Доставка'");
    Button pickup = new Button(By.xpath("//button[@aria-controls='pickup-tab']"), "Кнопка 'Самовывоз'");
    Element pickupButtonActive = new Element(By.xpath("//button[@aria-controls='pickup-tab'][@aria-selected='true']"), "Активная кнопка 'Самовывоз'");

    Button addressChange = new Button(By.xpath("//button[contains(@class,'AddressInput_root')]"), "Кнопка перехода к выбору адреса");

    Button login = new Button(By.xpath("//button[@data-qa='b2c_home_landing_common_header_middle_bar_user_actions_login_button']"), "Кнопка 'Войти'");
    Button userActionsToggle = new Button(By.xpath("//div[contains(@class,'UserActionsMenuToggle')]//button[contains(@class,'UserActionsButton_root')]"), "Кнопка меню профиля пользователя");
    Button userFavourites = new Button(By.xpath("//a[contains(@class,'UserActions_favoriteButton')]"), "Кнопка перехода в 'Избранное'");
    Button userOrders = new Button(By.xpath("//a[contains(@class,'UserActions_ordersButton')]"), "Кнопка перехода в 'Историю заказов'");
    Button openCart = new Button(By.xpath("//span[contains(@class,'CounterBadge_badgeContainer')]/button[contains(@class,'UserActionsButton_root')]"), "Кнопка 'Корзина'");
}
