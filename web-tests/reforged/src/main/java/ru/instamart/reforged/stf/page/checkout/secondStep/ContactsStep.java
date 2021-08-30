package ru.instamart.reforged.stf.page.checkout.secondStep;

import io.qameta.allure.Step;

public class ContactsStep implements ContactsStepElement {

    @Step("Заполнить имя")
    public void fillFirstName(String data) {
        firstName.fill(data);
    }

    @Step("Заполнить Фамилия")
    public void fillLastName(String data) {
        lastName.fill(data);
    }

    @Step("Заполнить email")
    public void fillEmail(String data) {
        email.fill(data);
    }

    @Step("Выбрать чекбокс Отправлять информацию о заказе на email")
    public void checkEmailInfo() {
        emailInfo.check();
    }

    @Step("Снять выбор чекбокса Отправлять информацию о заказе на email")
    public void uncheckEmailInfo() {
        emailInfo.uncheck();
    }

    @Step("Нажать Добавить новый телефон")
    public void clickToAddNewNumber() {
        addNewNumber.click();
    }

    @Step("Нажать Изменить")
    public void clickToChangePhone() {
        changePhone.click();
    }

    @Step("Нажать Продолжить")
    public void clickToSubmit() {
        submit.click();
    }

}
