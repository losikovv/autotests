package ru.instamart.reforged.stf.frame.store_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.ElementCollection;
import ru.instamart.reforged.core.component.Link;

public interface StoreModalElement {

    Element storeModalWindow = new Element(By.xpath("//div[@data-qa='stores-modal']"), "модальное окно выбора магазина");
    Link login = new Link(By.xpath("//span[@data-qa='stores-modal-login']"), "ссылка на авторизацию");
    Button delivery = new Button(By.xpath("//button[@data-qa='stores-selector-delivery']"), "кнопка выбора доставки 'Доставка'");
    Button pickup = new Button(By.xpath("//button[@data-qa='stores-selector-pickup']"), "кнопка выбора доставки 'Самовывоз'");
    Button editAddress = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"), "кнопка Изменить адрес");
    ElementCollection storeCard = new ElementCollection(By.xpath("//a[@data-qa='store-card']"), "карточки магазинов");
}
