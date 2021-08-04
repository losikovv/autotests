package ru.instamart.reforged.stf.frame.address;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface AddressElement {

    Element addressModal = new Element((By.xpath("//div[@data-qa='address-modal']")));

    Button delivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-delivery']"));
    Button selfDelivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-pickup']"));

    Input address = new Input(By.xpath("//input[@data-qa='address-modal-input']"));

    DropDown dropDownAddress = new DropDown(By.xpath("//div[contains(@class,'dropdown')]/div/*[text()]"));
    Button save = new Button(By.xpath("//button[@data-qa='address-modal-submit']"));
    Element outOfShippingZone = new Element(By.xpath("//div[text()='Адрес не в зоне доставки']"));

    Selector selectCity = new Selector(By.xpath("//select[@data-qa='city-selector-control']"));
    Element storeList = new Element(By.xpath("//div[@data-qa='expandable-store-list']"), "список магазинов для самовывоза");
    Element storeItem = new Element(By.xpath("//div[@data-qa='store-item']"));
    Element availableStoreCounter = new Element(By.xpath("//div[@data-qa='expandable-store-list-counter']"));
    Button selectStoreButton = new Button(By.xpath("//button[@data-qa='store-item-btn']"));
    Element otherRetailers = new Element(By.xpath("//div[@data-qa='address-edit']//span[contains(text(), 'Выбрать другой магазин')]"));
    Button changeStore = new Button(By.xpath("//button[@data-qa='selected-store-btn']"));
    Element prevAddresses = new Element(By.xpath("//div[@data-qa='address-modal-addresses']"));
    Element login = new Element(By.xpath("//span[@data-qa='address-modal-login']"));

    Element markerImageOnMapInAdvice = new Element(By.xpath("//div[contains(@class, 'notice')]/descendant::img[contains(@src, '/marker')]"));
}
