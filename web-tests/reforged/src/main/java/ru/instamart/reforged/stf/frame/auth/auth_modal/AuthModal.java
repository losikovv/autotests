package ru.instamart.reforged.stf.frame.auth.auth_modal;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.setting.Config;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.reforged.core.component.Link;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Checkbox;
import ru.instamart.reforged.core.component.Input;
import ru.instamart.reforged.stf.frame.Close;
import ru.instamart.reforged.stf.frame.auth.AuthFacebook;
import ru.instamart.reforged.stf.frame.auth.AuthMail;
import ru.instamart.reforged.stf.frame.auth.AuthSberId;
import ru.instamart.reforged.stf.frame.auth.AuthVk;

public final class AuthModal implements Close, AuthModalCheck {

    private final AuthMail authMailWindow = new AuthMail();
    private final AuthFacebook authFacebookWindow = new AuthFacebook();
    private final AuthVk authVkWindow = new AuthVk();
    private final AuthSberId authSberIdPage = new AuthSberId();

    public void fillPhone(final UserData userData) {
        fillPhone(userData.getPhone());
    }

    public AuthMail interactAuthMailWindow() {
        return authMailWindow;
    }

    public AuthFacebook interactAuthFacebookWindow() {
        return authFacebookWindow;
    }

    public AuthVk interactAuthVkWindow() {
        return authVkWindow;
    }

    public AuthSberId interactAuthSberIdPage() {
        return authSberIdPage;
    }

    @Step("Зарегистрировать пользователя по телефону")
    public void phoneRegistration() {
        fillPhone(Generate.phoneNumber());
        sendSms();
        fillSMS(Config.DEFAULT_SMS);
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

    @Step("Поставить отметку получения рассылки")
    public void checkPromoMailing() {
        promo.check();
    }

    @Step("Убрать отметку получения рассылки")
    public void uncheckPromoMailing() {
        promo.uncheck();
    }

    @Step("Заполнить поле с sms {sms}")
    public void fillSMS(final String sms) {
        smsInput.fill(sms);
    }

    @Step("Отправить форму")
    public void sendSms() {
        sendSms.click();
    }

    @Step("Войти через mail.ru")
    public void authViaMail() {
        mailRu.click();
    }

    @Step("Войти через Вконтакте")
    public void authViaVk() {
        vkontakte.click();
    }

    @Step("Войти через facebook")
    public void authViaFacebook() {
        facebook.click();
    }

    @Step("Войти через sberId")
    public void authViaSberId() {
        sberId.click();
    }
}
