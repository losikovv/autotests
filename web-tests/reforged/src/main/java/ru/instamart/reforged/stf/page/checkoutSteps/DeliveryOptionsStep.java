package ru.instamart.reforged.stf.page.checkoutSteps;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.RadioButton;
import ru.instamart.reforged.stf.frame.AddCompanyModal;

public class DeliveryOptionsStep {

    private final Button delivery = new Button(By.xpath("//button[@data-qa='checkout_form_shipping_kind_by_courier']"));
    private final Button pickUp = new Button(By.xpath("//button[@data-qa='checkout_form_shipping_kind_pickup']"));
    private final RadioButton forSelf = new RadioButton(By.xpath("//input[@data-qa='checkout_form_kind_home_radio_button']"));
    private final RadioButton forBusiness = new RadioButton(By.xpath("//input[@data-qa='checkout_form_kind_office_radio_button']"));
    private final Button addCompany = new Button(By.xpath("//button[@data-qa='checkout_add_company_button']"));
    private final Input apartment = new Input(By.xpath("//input[@name='apartment']"));
    private final Input floor = new Input(By.xpath("//input[@name='floor']"));
    private final Checkbox elevator = new Checkbox(By.xpath("//input[@data-qa='ship_address_form_elevator_checkbox']"));
    private final Input entrance = new Input(By.xpath("//input[@name='entrance']"));
    private final Input doorPhone = new Input(By.xpath("//input[@name='doorPhone']"));
    private final Checkbox contactlessDelivery = new Checkbox(By.xpath("//input[@data-qa='ship_address_form_contactless_delivery_checkbox']"));
    private final Input comment = new Input(By.xpath("//textarea[@name='order[special_instructions]']"));//??

    private final Button submitStepWithDelivery = new Button(By.xpath("//button[@data-qa='checkout_ship_address_form_submit_button']"));
    private final Button submitStepWithPickUp = new Button(By.xpath("//button[@data-qa=\"checkout_shipping_method_button'\"]"));


    private final AddCompanyModal addCompanyFrame = new AddCompanyModal();

    public AddCompanyModal interactAddCompanyFrame() {
        return addCompanyFrame;
    }

    @Step("Нажать Доставка")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажать Самовывоз")
    public void clickToPickUp() {
        pickUp.click();
    }

    @Step("Выбрать Для себя")
    public void clickToForSelf() {
        forSelf.set();
    }

    @Step("Выбрать Для бизнеса")
    public void clickToForBusiness() {
        forBusiness.set();
    }

    @Step("Нажать Добавить компанию")
    public void clickToAddCompany() {
        addCompany.click();
    }

    @Step("Заполнить поле Номер квартиры/офиса")
    public void fillAppartment(String data){
        apartment.fill(data);
    }

    @Step("Заполнить поле Этаж")
    public void fillFloor(String data){
        floor.fill(data);
    }

    @Step("Выбрать Есть лифт")
    public void setElevator(){
        elevator.check();
    }

    @Step("Заполнить поле Подъезд")
    public void fillEntrance(String data){
        entrance.fill(data);
    }

    @Step("Заполнить поле Код домофона")
    public void fillDoorPhone(String data){
        doorPhone.fill(data);
    }

    @Step("Выбрать Бесконтактная доставка")
    public void setContactlessDelivery(){
        contactlessDelivery.check();
    }

    @Step("Заполнить поле Код домофона")
    public void fillComment(String data){
        comment.fill(data);
    }

    @Step("Нажать Продолжить(для доставки)")
    public void clickSubmitForDelivery(){
        submitStepWithDelivery.click();
    }

    @Step("Нажать Продолжить(для самовывоза)")
    public void clickSubmitForPickup(){
        submitStepWithPickUp.click();
    }
}
