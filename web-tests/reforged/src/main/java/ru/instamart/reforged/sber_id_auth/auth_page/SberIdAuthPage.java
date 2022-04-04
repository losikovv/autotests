package ru.instamart.reforged.sber_id_auth.auth_page;

import io.qameta.allure.Step;
import ru.instamart.reforged.sber_id_auth.SberIdPage;

public class SberIdAuthPage implements SberIdPage, SberIdAuthCheck {

    @Step("Ввести номер телефона на странице авторизации через sberID")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Ввести логин на странице авторизации через sberID")
    public void fillLogin(String data) {
        login.fill(data);
    }

    @Step("Ввести пароль на странице авторизации через sberID")
    public void fillPassword(String data) {
        password.fill(data);
    }

    @Step("Нажать войти по логину на странцие авторизации через sberId")
    public void clickToChangeAuthTypeOnLogin() {
        changeAuthTypeOnLogin.click();
    }

    @Step("Нажать подтвердить логин")
    public void clickToSubmitLogin() {
        submitLogin.click();
    }

    @Step("Нажать подтвердить пароль")
    public void clickToSubmitPassword() {
        submitPassword.click();
    }

    @Step("Нажать 'Я получил смс'")
    public void clickToReceivedSms() {
        receivedSms.click();
    }

    @Step("Ввести смс - код:{0}")
    public void enterCode(String code) {
        smsCode.fill(code);
    }

    @Step("Нажать подтвердить смс")
    public void clickToSubmitSmsCode() {
        submitSmsCode.click();
    }


    @Override
    public String pageUrl() {
        return "";
    }
}
