package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.DropDown;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.Selector;
import ru.instamart.reforged.core.component.Element;

public final class Address implements Close {

    private final Button delivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-delivery']"));
    private final Button selfDelivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-pickup']"));

    private final Input address = new Input(By.xpath("//input[@data-qa='address-modal-input']"));
    private final DropDown dropDownAddress = new DropDown(By.xpath("//div[contains(@class,'dropdown')]/div/*[text()]"));
    private final Button save = new Button(By.xpath("//button[@data-qa='address-modal-submit']"));

    private final Selector selectCity = new Selector(By.xpath("//select[@data-qa='city-selector-control']"));
    private final Element storeList = new Element(By.xpath("//div[@data-qa='expandable-store-list']"), "список магазинов для самовывоза");
    private final Element storeItem = new Element(By.xpath("//div[@data-qa='store-item']"));
    private final Element availableStoreCounter = new Element(By.xpath("//div[@data-qa='expandable-store-list-counter']"));
    private final Button selectStoreButton = new Button(By.xpath("//button[@data-qa='store-item-btn']"));
    private final Element otherRetailers = new Element(By.xpath("//div[@data-qa='address-edit']//span[contains(text(), 'Выбрать другой магазин')]"));
    private final Button changeStore = new Button(By.xpath("//button[@data-qa='selected-store-btn']"));
    private final Element prevAddresses = new Element(By.xpath("//div[@data-qa='address-modal-addresses']"));


    @Step("Выбрать доставку")
    public void selectDelivery() {
        delivery.click();
    }

    @Step("Указать адрес доставки")
    public void setAddress(final String text) {
        address.fill(text);
    }

    @Step("Очистить поле адреса")
    public void clear() {
        address.clear();
    }

    @Step("Выбрать любой адрес из совпадений")
    public void select() {
        dropDownAddress.selectAny();
    }

    @Step("Выбрать первый адрес из совпадений")
    public void selectFirstAddress() {
        dropDownAddress.selectFirst();
    }

    @Step("Выбрать самовывоз")
    public void selectSelfDelivery() {
        selfDelivery.click();
    }

    @Step("Выбрать город {0}")
    public void selectCity(final String text) {
        selectCity.selectByText(text);
    }

    @Step("Нажать сохранить")
    public void clickOnSave() {
        save.click();
    }

    @Step("Нажать Выбрать другой магазин")
    public void clickViewOtherRetailers() {
        otherRetailers.click();
    }

    @Step("Выбрать первый доступный магазин")
    public void selectFirstStore() {
        selectStoreButton.click();
    }

    @Step("Изменить выбранный магазин самовывоза")
    public void changeStore() {
        changeStore.click();
    }
}
