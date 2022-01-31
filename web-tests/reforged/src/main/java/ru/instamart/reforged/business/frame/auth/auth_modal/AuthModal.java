package ru.instamart.reforged.business.frame.auth.auth_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.reforged.business.frame.Close;
import ru.instamart.reforged.business.frame.auth.AuthSberBusinessID;

public final class AuthModal implements Close, AuthModalCheck {

    private final AuthSberBusinessID authSberBusinessIdPage = new AuthSberBusinessID();

    public AuthSberBusinessID interactAuthSberBusinessIdPage() {
        return authSberBusinessIdPage;
    }

    @Step("Вводим номер телефона {phone}")
    public void fillPhone(final String phone) {
        phoneField.fillField(phone, true);
    }

    @Step("Нажимаем 'Получить код в СМС'")
    public void clickSendSms() {
        sendSms.click();
    }

    @Step("Поставить отметку получения рассылки")
    public void checkPromoMailing() {
        promo.check();
    }

    @Step("Убрать отметку получения рассылки")
    public void uncheckPromoMailing() {
        promo.uncheck();
    }

    @Step("Вводим код СМС {sms}")
    public void fillSMS(final String sms) {
        smsInput.fill(sms);
    }

    @Step("Нажимаем 'Войти по СберБизнес ID'")
    public void clickAuthViaSberBusinessId() {
        sberBusinessIdButton.hoverAndClick();
    }

    @Step("Авторизуемся по коду СМС")
    public void authViaPhone(final UserData userData) {
        fillPhone(userData.getPhone());
        clickSendSms();
        fillSMS(userData.getSmsCode());
    }
}
