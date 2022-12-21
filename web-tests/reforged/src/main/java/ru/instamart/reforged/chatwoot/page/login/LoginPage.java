package ru.instamart.reforged.chatwoot.page.login;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.ChatwootPage;

public final class LoginPage implements ChatwootPage, LoginCheck {

    @Step("Вводим в поле email: '{email}'")
    public void fillEmail(final String email){
        emailInput.fill(email);
    }

    @Step("Вводим в поле пароль: '{password}'")
    public void fillPassword(final String password){
        passwordInput.fill(password);
    }

    @Step("Нажимаем 'Вход'")
    public void clickSubmit(){
        submit.click();
    }
    @Override
    public void goToPage() {
        goToPage(pageUrl());
    }

    @Override
    public String pageUrl() {
        return "app/login";
    }
}
