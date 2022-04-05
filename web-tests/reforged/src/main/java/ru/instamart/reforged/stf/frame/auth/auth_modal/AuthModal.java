package ru.instamart.reforged.stf.frame.auth.auth_modal;

import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.stf.frame.Close;
import ru.instamart.reforged.stf.frame.auth.*;

public final class AuthModal implements Close, AuthModalCheck {

    private final AuthMail authMailWindow = new AuthMail();
    private final AuthFacebook authFacebookWindow = new AuthFacebook();
    private final AuthVk authVkWindow = new AuthVk();
    private final AuthSberBusinessID authSberBusinessIdPage = new AuthSberBusinessID();

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

    public AuthSberBusinessID interactAuthSberBusinessIdPage() {
        return authSberBusinessIdPage;
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

    @Step("Войти через SberBusinessId")
    public void authViaSberBusinessId() {
        sberBusinessIdButton.hoverAndClick();
    }

    //TODO ThreadUtil.simplyAwait убрать после отключения проверки таймаута для повторной смс
    @Issue("B2C-245")
    @Step("Авторизоваться пользователем {userData.phone}")
    public void authViaPhone(final UserData userData) {
        ThreadUtil.simplyAwait(1);
        fillPhone(userData.getPhone());
        sendSms();
        fillSMS(userData.getSmsCode());
    }
}
