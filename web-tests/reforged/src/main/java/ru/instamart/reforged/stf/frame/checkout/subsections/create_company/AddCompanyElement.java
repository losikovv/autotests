package ru.instamart.reforged.stf.frame.checkout.subsections.create_company;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public interface AddCompanyElement {

    Input inn = new Input(By.xpath("//input[@name='inn']"), "поле ввода для ИНН");
    Button submit = new Button(By.xpath("//button[@data-qa='create_company_form_submit_button']"), "кнопка сабмита формы");
    Input name = new Input(By.xpath("//input[@name='name']"), 20, "поле для ввода имени компании");
    Button okButton = new Button(By.xpath("//button[@data-qa='create_company_ok_button']"), "подтверждение имени компании");
}
