package ru.instamart.reforged.stf.frame.auth.auth_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface AuthModalElement {

    Element modal = new Element(By.xpath("//form[@data-qa='tel_login_form']"), "модальное окно логина/регистрации");

    Input phoneField = new Input(By.xpath("//input[@data-qa='tel_login_form_input']"));
    Button sendSms = new Button(By.xpath("//button[@data-qa='tel_login_form_button']"));
    Checkbox forBusiness = new Checkbox(By.xpath("//input[@data-qa='registration_form_b2b_checkbox']"));

    Link sberId = new Link(By.xpath("//span[@class='sbid-button__logo']"));
    Link vkontakte = new Link(By.xpath("//a[@data-qa='vkontakte']"));
    Link facebook = new Link(By.xpath("//a[@data-qa='facebook']"));
    Link mailRu = new Link(By.xpath("//a[@data-qa='mail_ru']"));

    Checkbox promo = new Checkbox(By.xpath("//input[@type='checkbox' and not(contains(@name, 'b2b'))]"));
    Button promoTerms = new Button(By.xpath("//button[@data-qa='promo_terms_button']"));

    Button terms = new Button(By.xpath("//button[@data-qa='tel_login_form_footer_button']"));

    Button back = new Button(By.xpath("//button[@title='Назад']"));
    Button changePhone = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"));
    Input smsInput = new Input(By.xpath("//input[@data-qa='sms_confirm_input']"));
    Button resendSms = new Button(By.xpath("//button[@data-qa='sms_resend_button']"));

    Element phoneError = new Element(By.xpath("//div[@data-qa='tel_login_form_error']"));

    Button sberBusinessIdButton = new Button(By.xpath("//button[@data-qa='sber_business_id']"), "Кнопка 'Войти по Сбер Бизнес ID'");

}
