package ru.instamart.reforged.stf.page.checkout.step;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.core.component.RadioButton;

public class DeliveryOptionsStep {

    private final Button delivery = new Button(By.xpath("//button[@data-qa='checkout_form_shipping_kind_by_courier']"), "кнопка Доставка");
    private final Button pickUp = new Button(By.xpath("//button[@data-qa='checkout_form_shipping_kind_pickup']"), "кнопка Самовывоз");
    private final RadioButton forSelf = new RadioButton(By.xpath("//input[@data-qa='checkout_form_kind_home_radio_button']"), "радиобаттон Для себя");
    private final RadioButton forBusiness = new RadioButton(By.xpath("//input[@data-qa='checkout_form_kind_office_radio_button']"), "радиобаттон Для бизнеса");
    private final Button addCompany = new Button(By.xpath("//button[@data-qa='checkout_add_company_button']"), "кнопка Добавить компанию");
    private final Input apartment = new Input(By.xpath("//input[@name='apartment']"), "поле Номер квартиры/офис");
    private final Input floor = new Input(By.xpath("//input[@name='floor']"), "поле Этаж");
    private final Checkbox elevator = new Checkbox(By.xpath("//input[@data-qa='ship_address_form_elevator_checkbox']"), "чекбокс Есть лифт");
    private final Input entrance = new Input(By.xpath("//input[@name='entrance']"), "поле Подъезд");
    private final Input doorPhone = new Input(By.xpath("//input[@name='doorPhone']"), "поле Код домофона");
    private final Checkbox contactlessDelivery = new Checkbox(By.xpath("//input[@data-qa='ship_address_form_contactless_delivery_checkbox']"), "чекбокс бесплатной доставки");
    private final Input comments = new Input(By.xpath("//textarea[@id='FormGroup_comments']"), "поле Комментарии по доставке");

    private final Button submitStepWithDelivery = new Button(By.xpath("//button[@data-qa='checkout_ship_address_form_submit_button']"), "кнопка Продолжить, если выбрана Доставка");
    private final Button submitStepWithPickUp = new Button(By.xpath("//button[@data-qa=\"checkout_shipping_method_button'\"]"), "кнопка Продолжить, если выбран Самовывоз");

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
    public void fillApartment(String data) {
        apartment.fill(data);
    }

    @Step("Заполнить поле Этаж")
    public void fillFloor(String data) {
        floor.fill(data);
    }

    @Step("Выбрать Есть лифт")
    public void checkElevator() {
        elevator.check();
    }

    @Step("Снять выбор Есть лифт")
    public void uncheckElevator() {
        elevator.uncheck();
    }

    @Step("Заполнить поле Подъезд")
    public void fillEntrance(String data) {
        entrance.fill(data);
    }

    @Step("Заполнить поле Код домофона")
    public void fillDoorPhone(String data) {
        doorPhone.fill(data);
    }

    @Step("Выбрать Бесконтактная доставка")
    public void checkContactlessDelivery() {
        contactlessDelivery.check();
    }

    @Step("Снять выбор Бесконтактная доставка")
    public void uncheckContactlessDelivery() {
        contactlessDelivery.uncheck();
    }

    @Step("Заполнить поле Код домофона")
    public void fillComments(String data) {
        comments.fill(data);
    }

    @Step("Нажать Продолжить(для доставки)")
    public void clickToSubmitForDelivery() {
        submitStepWithDelivery.click();
    }

    @Step("Нажать Продолжить(для самовывоза)")
    public void clickToSubmitForPickup() {
        submitStepWithPickUp.click();
    }

    @Step("Очистить поле Номер квартиры / офис")
    public void clearApartment() {
        apartment.clear();
    }

    @Step("Очистить поле Этаж")
    public void clearFloor() {
        floor.clear();
    }

    @Step("Очистить поле Подъезд")
    public void clearEntrance() {
        entrance.clear();
    }

    @Step("Очистить поле Код домофона")
    public void clearDoorPhone() {
        doorPhone.clear();
    }

    @Step("Очистить поле Комментарии по доставке")
    public void clearComments() {
        comments.clear();
    }

    @Step("Получить состояние радиобаттона Для себя")
    public Boolean getForSelfState() {
        return forSelf.radioButtonState();
    }

    @Step("Получить состояние радиобаттона Для бизнеса")
    public Boolean getForBusinessState() {
        return forBusiness.radioButtonState();
    }

    @Step("Получить значение поля Номер квартиры / офис")
    public String getApartmentValue() {
        return apartment.getValue();
    }

    @Step("Получить значение поля Этаж")
    public String getFloorValue() {
        return floor.getValue();
    }

    @Step("Получить состояние чекбокса Есть лифт")
    public Boolean getElevatorState() {
        return elevator.checkboxState();
    }

    @Step("Получить значение поля Подъезд")
    public String getEntranceValue() {
        return entrance.getValue();
    }

    @Step("Получить значение поля Код домофона")
    public String getDoorPhoneValue() {
        return doorPhone.getValue();
    }

    @Step("Получить значение поля Бесконтактная доставка")
    public Boolean getContactlessDeliveryState() {
        return contactlessDelivery.checkboxState();
    }

    @Step("Получить значение поля Комментарии по доставке")
    public String getCommentsValue() {
        return comments.getValue();
    }

}
