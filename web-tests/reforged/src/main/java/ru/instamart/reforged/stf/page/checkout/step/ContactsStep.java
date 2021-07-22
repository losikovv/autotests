package ru.instamart.reforged.stf.page.checkout.step;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public class ContactsStep {

    private final Input firstName = new Input(By.xpath("//input[@name='user.firstName']"));
    private final Input lastName = new Input(By.xpath("//input[@name='user.lastName']"));
    private final Input email = new Input(By.xpath("//input[@name='user.email']"));
    private final Input phone = new Input(By.xpath("//input[@id='phone-input']"));
    private final Checkbox emailInfo = new Checkbox(By.xpath("//input[@data-qa='contacts_form_send_info_by_email_checkbox']"));
    private final Button addNewNumber = new Button(By.xpath("//span[contains(text(),'Добавить новый телефон')]"));
    private final Button changePhone = new Button(By.xpath("//button[contains(text(),'Изменить')]"));
    private final Button submit = new Button(By.xpath("//button[@data-qa='checkout_contact_form_submit_button']"));

    @Step("Заполнить имя")
    public void fillFirstName(String data) {
        firstName.fill(data);
    }

    @Step("Заполнить Фамилия")
    public void fillLastName(String data) {
        lastName.fill(data);
    }

    @Step("Заполнить Телефон")
    public void fillPhone(String data) {
        phone.fill(data);
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
