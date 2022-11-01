package ru.instamart.reforged.hr_ops_partners.frame.confirn_SMS_form_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface ConfirmSMSModalElement {

    Button close = new Button(By.xpath("//button[contains(@class,'Modal_closeButton_')]"), "Кнопка 'X' (закрыть)");
    Element modalTitle = new Element(By.xpath("//div[contains(@class,'RegistrationModal_title')]"), "Заголовок окна ввода СМС");
    Element description = new Element(By.xpath("//div[contains(@class,'RegistrationModal_title')]/following-sibling::div"), "Описание в окне ввода СМС");
    Button change = new Button(By.xpath("//span[contains(@class,'RegistrationModal_changeButton')]"), "Кнопка 'Изменить'");
    Input codeInput = new Input(By.xpath("//input[contains(@class,'RegistrationModal_input')]"), "Поле ввода кода СМС");
    Button confirm = new Button(By.xpath("//button[contains(@class,'RegistrationModal_button_')]"), "Кнопка 'Отправить заявку'");
    Element timer = new Element(By.xpath("//div[contains(@class,'RegistrationModal_wait_')]"), "Таймер обратного отсчёта повторной отправки СМС");
    Element retry = new Element(By.xpath("//div[contains(@class,'RegistrationModal_retry_')]"), "Отправить ещё раз");
}
