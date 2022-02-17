package ru.instamart.reforged.business.block.header;

import org.openqa.selenium.By;
import ru.instamart.reforged.business.drawer.cart.account_menu.AccountMenu;
import ru.instamart.reforged.business.frame.TransferCartModal;
import ru.instamart.reforged.business.frame.store_selector.StoreSelector;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;

public interface HeaderElement {

    AccountMenu accountMenu = new AccountMenu();
    TransferCartModal transferCartModal = new TransferCartModal();
    StoreSelector storeSelectorDrawer = new StoreSelector();

    Element header = new Element(By.xpath("//header"), "Контейнер для хедера");

    Button selectAddress = new Button(By.xpath("//button[@data-qa='select-button']"), "Кнопка 'Указать адрес'");
    Element enteredAddress = new Element(By.xpath("//span[@data-qa='current-ship-address']"), "Лэйбл отображающий введенный адрес в шапке");
    Button login = new Button(By.xpath("//button[contains(.,'Войти')]"), "Кнопка 'Войти'");
    Button storeSelector = new Button(By.xpath("//button[@data-qa='open-store-selector-button']"), "Кнопка выбора магазина");
    Button profile = new Button(By.xpath("//button[@data-qa='profile-button_button']"), "Кнопка профиль пользователя в хэдере");
    Element cartNotification = new Element(By.xpath("//div[@class='notification']"), "Алерт добавления товара в корзину");
    Button cart = new Button(By.xpath("//button[@data-qa='open-cart-button']"), "Кнопка 'Корзина'");
    Element itemsCountSpoilerOnCartButton = new Element(By.xpath("//button[@data-qa='open-cart-button']/following-sibling::span"), "Значок о количестве товаров в корзине над кнопкой");
    Element alert = new Element(By.xpath("//div[@class='alert alert--error']"), "Тултип-сообщение об ошибке");
}
