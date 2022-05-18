package ru.instamart.reforged.next.page.checkout.firstStep;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.AddressDetailsData;

public class DeliveryOptionStep implements DeliveryOptionCheck {

    @Step("Нажать 'Доставка'")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажать 'Самовывоз'")
    public void clickToPickUp() {
        pickUp.click();
    }

    @Step("Выбрать 'Для себя'")
    public void clickToForSelf() {
        forSelf.set();
    }

    @Step("Выбрать 'Для бизнеса'")
    public void clickToForBusiness() {
        forBusiness.set();
    }

    @Step("Нажать 'Добавить компанию'")
    public void clickToAddCompany() {
        addCompany.click();
    }

    @Step("Заполнить адрес")
    public void fillDeliveryAddress(final AddressDetailsData data) {
        fillApartment(data.getApartment());
        fillFloor(data.getFloor());
        checkElevator();
        fillEntrance(data.getEntrance());
        fillDoorPhone(data.getDomofon());
        fillComments(data.getComments());
    }

    @Step("Заполнить поле 'Номер квартиры/офиса'")
    public void fillApartment(String data) {
        apartment.fill(data);
    }

    @Step("Заполнить поле 'Этаж'")
    public void fillFloor(String data) {
        floor.fill(data);
    }

    @Step("Выбрать 'Есть лифт'")
    public void checkElevator() {
        elevator.check();
    }

    @Step("Снять выбор 'Есть лифт'")
    public void uncheckElevator() {
        elevator.uncheck();
    }

    @Step("Заполнить поле 'Подъезд'")
    public void fillEntrance(String data) {
        entrance.fill(data);
    }

    @Step("Заполнить поле 'Код домофона'")
    public void fillDoorPhone(String data) {
        doorPhone.fill(data);
    }

    @Step("Выбрать 'Бесконтактная доставка'")
    public void checkContactlessDelivery() {
        contactlessDelivery.check();
    }

    @Step("Снять выбор 'Бесконтактная доставка'")
    public void uncheckContactlessDelivery() {
        contactlessDelivery.uncheck();
    }

    @Step("Заполнить поле 'Комментарий'")
    public void fillComments(String data) {
        comments.fill(data);
    }

    @Step("Нажать 'Продолжить'(для доставки)")
    public void clickToSubmitForDelivery() {
        submitStepWithDelivery.scrollTo();
        submitStepWithDelivery.hoverAndClick();
    }

    @Step("Нажать 'Продолжить'(для самовывоза)")
    public void clickToSubmitForPickup() {
        submitStepWithPickUp.click();
    }

    @Step("Очистить поле 'Номер квартиры / офис'")
    public void clearApartment() {
        apartment.clear();
    }

    @Step("Очистить поле 'Этаж'")
    public void clearFloor() {
        floor.clear();
    }

    @Step("Очистить поле 'Подъезд'")
    public void clearEntrance() {
        entrance.clear();
    }

    @Step("Очистить поле 'Код домофона'")
    public void clearDoorPhone() {
        doorPhone.clear();
    }

    @Step("Очистить поле 'Комментарии по доставке'")
    public void clearComments() {
        comments.clear();
    }

    @Step("Получить состояние радиобаттона 'Для себя'")
    public boolean getForSelfState() {
        return forSelf.radioButtonState();
    }

    @Step("Получить состояние радиобаттона 'Для бизнеса'")
    public boolean getForBusinessState() {
        return forBusiness.radioButtonState();
    }

    @Step("Получить значение поля 'Номер квартиры / офис'")
    public String getApartmentValue() {
        return apartment.getValue();
    }

    @Step("Получить значение поля 'Этаж'")
    public String getFloorValue() {
        return floor.getValue();
    }

    @Step("Получить состояние чекбокса 'Есть лифт'")
    public boolean getElevatorState() {
        return elevator.checkboxState();
    }

    @Step("Получить значение поля 'Подъезд'")
    public String getEntranceValue() {
        return entrance.getValue();
    }

    @Step("Получить значение поля 'Код домофона'")
    public String getDoorPhoneValue() {
        return doorPhone.getValue();
    }

    @Step("Получить значение поля 'Бесконтактная доставка'")
    public boolean getContactlessDeliveryState() {
        return contactlessDelivery.checkboxState();
    }

    @Step("Получить значение поля 'Комментарии по доставке'")
    public String getCommentsValue() {
        return comments.getValue();
    }

}
