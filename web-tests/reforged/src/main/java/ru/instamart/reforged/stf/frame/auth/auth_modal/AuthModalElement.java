package ru.instamart.reforged.stf.frame.auth.auth_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.*;

public interface AuthModalElement {

    Element modal = new Element(By.xpath("//form[@data-qa='tel_login_form']"), "модальное окно логина/регистрации");

    Input phoneField = new Input(By.xpath("//input[@data-qa='tel_login_form_input']"), "поле ввода телефона");
    Button sendSms = new Button(By.xpath("//button[@data-qa='tel_login_form_button']"), "поле ввода sms");
    Checkbox forBusiness = new Checkbox(By.xpath("//input[@data-qa='registration_form_b2b_checkbox']"), "чекбокс b2b");

    Link sberId = new Link(By.xpath("//span[@class='sbid-button__logo']"), "кнопка авторизации через SberID");
    Link vkontakte = new Link(By.xpath("//a[@data-qa='vkontakte']"), "кнопка авторизации через vkontakte");
    Link facebook = new Link(By.xpath("//a[@data-qa='facebook']"), "кнопка авторизации через facebook");
    Link mailRu = new Link(By.xpath("//a[@data-qa='mail_ru']"), "кнопка авторизации через mail_ru");

    Checkbox promo = new Checkbox(By.xpath("//input[@type='checkbox' and not(contains(@name, 'b2b'))]"), "чекбокс промосообщений");
    Button promoTerms = new Button(By.xpath("//button[@data-qa='promo_terms_button']"), "ссылка на термсы");

    Button terms = new Button(By.xpath("//button[@data-qa='tel_login_form_footer_button']"), "термсы");

    Button back = new Button(By.xpath("//button[@title='Назад']"), "кнопка перехода назад");
    Button changePhone = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"), "кнопка смены телефонного номера");
    Input smsInput = new Input(By.xpath("//input[@data-qa='sms_confirm_input']"), "кнопка отправки SMS кода");
    Button resendSms = new Button(By.xpath("//button[@data-qa='sms_resend_button']"), "кнопка переотправки SMS кода");

    Element phoneError = new Element(By.xpath("//div[@data-qa='tel_login_form_error']"), "сообщение об ошибке");

    Button sberBusinessIdButton = new Button(By.xpath("//button[@data-qa='sber_business_id']"), "Кнопка 'Войти по Сбер Бизнес ID'");

}
