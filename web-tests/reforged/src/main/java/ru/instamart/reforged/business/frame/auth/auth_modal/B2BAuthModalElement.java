package ru.instamart.reforged.business.frame.auth.auth_modal;

import org.openqa.selenium.By;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Element;
import ru.instamart.reforged.core.component.Input;

public interface B2BAuthModalElement {

    Element modal = new Element(By.xpath("//form[@data-qa='tel_login_form']"), "Модальное окно логина/регистрации");

    Input phoneField = new Input(By.xpath("//input[@data-qa='tel_login_form_input']"), "Поле ввода телефона");
    Button sendSms = new Button(By.xpath("//button[@data-qa='tel_login_form_button']"), "Кнопка 'Получить код в СМС'");
    Button sberBusinessIdButton = new Button(By.xpath("//button[@data-qa='sber_business_id']"), "Кнопка 'Войти по СберБизнес ID'");
    Checkbox promo = new Checkbox(By.xpath("//input[@type='checkbox']"), "чекбокс промосообщений");
    Button promoTerms = new Button(By.xpath("//button[@data-qa='promo_terms_button']"), "ссылка на термсы");
    Button terms = new Button(By.xpath("//button[@data-qa='tel_login_form_footer_button']"), "термсы");

    // Модальное окно ввода SMS
    Button back = new Button(By.xpath("//button[@title='Назад']"), "Кнопка перехода назад");
    Button changePhone = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"), "Кнопка 'Изменить' (телефонный номер)");
    Input smsInput = new Input(By.xpath("//input[@data-qa='sms_confirm_input']"), "Поле ввода кода СМС");
    Button resendSms = new Button(By.xpath("//button[@data-qa='sms_resend_button']"), "Кнопка 'Выслать СМС ещё раз'");

    Element phoneError = new Element(By.xpath("//div[@data-qa='tel_login_form_error']"), "сообщение об ошибке");
}
