package ru.instamart.reforged.business.frame.auth.auth_modal;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.reforged.business.frame.B2BClose;
import ru.instamart.reforged.business.frame.auth.B2BAuthSberBusinessID;

public final class B2BAuthModal implements B2BClose, B2BAuthModalCheck {

    private final B2BAuthSberBusinessID authSberBusinessIdPage = new B2BAuthSberBusinessID();

    public B2BAuthSberBusinessID interactAuthSberBusinessIdPage() {
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
