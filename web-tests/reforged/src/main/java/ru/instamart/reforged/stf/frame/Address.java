package ru.instamart.reforged.stf.frame;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.stf.component.Button;
import ru.instamart.reforged.stf.component.DropDown;
import ru.instamart.reforged.stf.component.Input;
import ru.instamart.reforged.stf.component.Selector;

public final class Address implements Close {

    private final Button delivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-delivery']"));
    private final Button selfDelivery = new Button(By.xpath("//button[@data-qa='address-edit-selector-pickup']"));

    private final Input address = new Input(By.xpath("//input[@data-qa='address-modal-input']"));
    private final DropDown dropDownAddress = new DropDown(By.xpath("//div[contains(@class,'dropdown')]//*[text()]"));

    private final Selector selectCity = new Selector(By.xpath("//select[@data-qa='city-selector-control']"));

    @Step("Выбрать доставку")
    public void selectDelivery() {
        delivery.click();
    }

    @Step("Указать адрес доставки")
    public void setAddress(final String text) {
        address.fill(text);
    }

    @Step("Выбрать любой адрес из совпадений")
    public void select() {
        dropDownAddress.selectAny();
    }

    @Step("Выбрать самовывоз")
    public void selectSelfDelivery() {
        selfDelivery.click();
    }

    @Step("Выбрать город {0}")
    public void selectCity(final String text) {
        selectCity.selectByText(text);
    }
}
