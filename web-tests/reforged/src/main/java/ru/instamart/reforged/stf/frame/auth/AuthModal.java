package ru.instamart.reforged.stf.frame.auth;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.reforged.stf.checkpoint.AuthModalCheck;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.stf.frame.Close;

public final class AuthModal implements Close, AuthModalCheck {

    private final Input phoneField = new Input(By.xpath("//input[@data-qa='tel_login_form_input']"));
    private final Button sendSms = new Button(By.xpath("//button[@data-qa='tel_login_form_button']"));
    private final Checkbox forBusiness = new Checkbox(By.xpath("//input[@data-qa='registration_form_b2b_checkbox']"));

    private final Button sberId = new Button(By.xpath("//button[@data-qa='sber_id']"));
    private final Button vkontakte = new Button(By.xpath("//button[@data-qa='vkontakte']"));
    private final Button facebook = new Button(By.xpath("//button[@data-qa='facebook']"));
    private final Button mailRu = new Button(By.xpath("//button[@data-qa='mail_ru']"));

    private final Checkbox promo = new Checkbox(By.xpath("//input[@type='checkbox' and not(contains(@name, 'b2b'))]"));
    private final Button promoTerms = new Button(By.xpath("//button[@data-qa='promo_terms_button']"));

    private final Button terms = new Button(By.xpath("//button[@data-qa='tel_login_form_footer_button']"));

    private final Button back = new Button(By.xpath("//button[@title='Назад']"));
    private final Button changePhone = new Button(By.xpath("//button[@data-qa='editable_info_change_button']"));
    private final Input smsInput = new Input(By.xpath("//input[@data-qa='sms_confirm_input']"));
    private final Button resendSms = new Button(By.xpath("//button[@data-qa='sms_resend_button']"));

    public void fillPhone(final UserData userData) {
        fillPhone(userData.getPhone());
    }

    @Step("Заполнить поле с телефоном {phone}")
    public void fillPhone(final String phone) {
        phoneField.fill(phone);
    }

    @Step("Поставить отметку для бизнеса")
    public void checkForBusiness() {
        forBusiness.check();
    }

    @Step("Убрать отметку для бизнеса")
    public void uncheckForBusiness() {
        forBusiness.uncheck();
    }

    @Step("Заполнить поле с sms {sms}")
    public void fillSMS(final String sms) {
        smsInput.fill(sms);
    }

    @Step("Отправить форму")
    public void sendSms() {
        sendSms.click();
    }
}
