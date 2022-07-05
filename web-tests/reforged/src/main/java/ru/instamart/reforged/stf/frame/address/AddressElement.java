package ru.instamart.reforged.stf.frame.address;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface AddressElement {

    Element addressModal = new Element(By.xpath("//div[@data-qa='address-modal']"), "empty");
    Button close = new Button(By.xpath("//button[@data-qa='address-modal-close']"), "Кнопка 'Закрыть'");

    Button delivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-delivery']"), "empty");
    Button selfDelivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-pickup']"), "empty");

    Input address = new Input(By.xpath("//input[@data-qa='address-modal-input']"), "поле ввода адреса");
    Element firstPrevAddresses = new Element(By.xpath("//div[@data-qa='address-modal-addresses']//div[@class='address-modal__address']"), "первый адрес из блока Предыдущие адреса");

    Input addressNew = new Input(By.xpath("//div[contains(@class,'AddressModal')]//input"), "поле ввода адреса");

    DropDown dropDownAddress = new DropDown(By.xpath("//input[@data-qa='address-modal-input']/following::div[2]/div/div/div"), "empty");
    Button save = new Button(By.xpath("//button[@data-qa='address-modal-submit']"), "кнопка Сохранить");
    Button findStores = new Button(By.xpath("//button[@data-qa='b2c_home_landing_address_block_address_modal_submit_button']"), "кнопка 'Найти магазины'");
    Element outOfShippingZone = new Element(By.xpath("//div[text()='Адрес не в зоне доставки']"), "лейбл Адрес не в зоне доставки");
    Button chooseOtherAddress = new Button(By.xpath("//button[@data-qa='address-modal-other-address']"), "кнопка Выбрать другой адрес");
    Button choosePickUp = new Button(By.xpath("//button[@data-qa='address-modal-pickup']"), "кнопка Выбрать самовывоз");

    Selector selectCity = new Selector(By.xpath("//select[@data-qa='city-selector-control']"), "empty");
    Element storeList = new Element(By.xpath("//div[@data-qa='expandable-store-list']"), "список магазинов для самовывоза");
    Element storeItem = new Element(By.xpath("//div[@data-qa='store-item']"), "empty");
    Element availableStoreCounter = new Element(By.xpath("//div[@data-qa='expandable-store-list-counter']"), "empty");
    Button selectStoreButton = new Button(By.xpath("//a[@data-qa='store-card']"), "Первый магазин в списке");
    Element otherRetailers = new Element(By.xpath("//div[@data-qa='address-edit']//span[contains(text(), 'Выбрать другой магазин')]"), "empty");
    Button changeStore = new Button(By.xpath("//button[@data-qa='selected-store-btn']"), "empty");
    Element login = new Element(By.xpath("//span[@data-qa='address-modal-login']"), "empty");

    Element markerImageOnMapInAdvice = new Element(By.xpath("//div[contains(@class, 'notice')]/descendant::img[contains(@src, '/marker')]"), "empty");
    Element ymapReady = new Element(By.xpath("//div[@data-qa='address-modal']//ymaps"), "Элемент яндекс карт, готовый к работе");
    ElementCollection storedAddresses = new ElementCollection(By.xpath("//div[@class='address-modal__address']"), "список сохраненных адресов");
}
