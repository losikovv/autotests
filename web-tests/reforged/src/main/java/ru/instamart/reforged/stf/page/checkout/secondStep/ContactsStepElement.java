package ru.instamart.reforged.stf.page.checkout.secondStep;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.ByKraken;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;

public interface ContactsStepElement {

    Input firstName = new Input(By.xpath("//input[@name='user.firstName']"), "поле 'Имя'");
    Input lastName = new Input(By.xpath("//input[@name='user.lastName']"), "поле 'Фамилия'");
    Input email = new Input(By.xpath("//input[@name='user.email']"), "поле 'Email'");
    Checkbox emailInfo = new Checkbox(By.xpath("//input[@data-qa='contacts_form_send_info_by_email_checkbox']"), "empty");
    Button addNewNumber = new Button(By.xpath("//span[contains(text(),'Добавить новый телефон')]"), "empty");
    Button changePhone = new Button(By.xpath("//button[contains(text(),'Изменить')]"), "empty");
    Button changePhoneWithText = new Button(ByKraken.xpathExpression("//span[text()='%s']/ancestor::button/following-sibling::button[text()='Изменить']"), "Кнопка 'изменить' у конкретного телефона");
    Button submit = new Button(By.xpath("//button[@data-qa='checkout_contact_form_submit_button']"), "empty");
}