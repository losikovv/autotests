package ru.instamart.reforged.stf.frame.auth.auth_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.CoreProperties;
import ru.instamart.kraken.testdata.Generate;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.kraken.util.ThreadUtil;
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
    public void createAccount() {
        fillPhone(Generate.phoneNumber());
        sendSms();
        fillDefaultSMS();
    }

    @Step("Зарегистрировать пользователя {0} по телефону")
    public void createAccount(final UserData userData) {
        fillPhone(userData.getPhone());
        sendSms();
        fillDefaultSMS();
    }

    @Step("Заполнить поле с телефоном {phone}")
    public void fillPhone(final String phone) {
        phoneField.fillField(phone, true);
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

    @Step("Заполнить поле с sms значением из конфига")
    public void fillDefaultSMS() {
        smsInput.fill(CoreProperties.DEFAULT_SMS);
    }

    @Step("Заполнить поле с sms значением из конфига(с ожиданием)")
    public void fillDefaultSMSWithSleep() {
        ThreadUtil.simplyAwait(1);
        smsInput.fill(CoreProperties.DEFAULT_SMS);
    }

    @Step("Отправить форму")
    public void sendSms() {
        sendSms.click();
    }

    @Step("Войти через mail.ru")
    public void authViaMail() {
        mailRu.hoverAndClick();
    }

    @Step("Войти через Вконтакте")
    public void authViaVk() {
        vkontakte.hoverAndClick();
    }

    @Step("Войти через facebook")
    public void authViaFacebook() {
        facebook.hoverAndClick();
    }

    @Step("Войти через sberId")
    public void authViaSberId() {
        sberId.hoverAndClick();
    }

    //TODO ThreadUtil.simplyAwait убрать после отключения проверки таймаута для повторной смс
    @Step("Авторизоваться пользователем {userData.phone}")
    public void authViaPhone(final UserData userData) {
        fillPhone(userData.getPhone());
        sendSms();
        ThreadUtil.simplyAwait(1);
        fillDefaultSMS();
    }
}
