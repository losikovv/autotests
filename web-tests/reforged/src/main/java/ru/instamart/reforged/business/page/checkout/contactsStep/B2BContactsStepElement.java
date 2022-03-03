package ru.instamart.reforged.business.page.checkout.contactsStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public interface B2BContactsStepElement {

    Input firstName = new Input(By.xpath("//input[@id='FormGroup_user.firstName']"), "Поле 'Имя'");
    Input lastName = new Input(By.xpath("//input[@id='FormGroup_user.lastName']"), "Поле 'Фамилия'");
    Input email = new Input(By.xpath("//input[@id='FormGroup_user.email']"), "Поле 'Email'");
    Checkbox emailInfo = new Checkbox(By.xpath("//input[@data-qa='contacts_form_send_info_by_email_checkbox']"), "Чекбокс 'Отправлять информацию о заказе на email'");
    Button addNewNumber = new Button(By.xpath("//button[contains(.,'Добавить новый телефон')]"), "Кнопка 'Добавить новый телефон'");
    Button changePhone = new Button(By.xpath("//button[contains(.,'Изменить')]"), "Кнопка 'Изменить' (номер телефона)");
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_contact_form_submit_button']"), "Кпопка 'Продолжить' шага 'Добавьте свои контакты'");
}