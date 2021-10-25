package ru.instamart.reforged.admin.page.login;

import io.qameta.allure.Step;
import ru.instamart.kraken.data.user.UserData;
import ru.instamart.reforged.admin.AdminPage;

import static ru.instamart.reforged.admin.AdminRout.main;

public final class LoginPage implements AdminPage, LoginCheck {

    @Step("Авторизация {0}")
    public void auth(final UserData userData) {
        setUsername(userData.getEmail());
        setPassword(userData.getPassword());
        submit();
        main().interactAuthoredHeader().checkAdminAuth();
    }

    @Step("Заполнить поле email {0}")
    public void setUsername(final String text) {
        username.fill(text);
    }

    @Step("Заполнить поле пароль {0}")
    public void setPassword(final String text) {
        password.fill(text);
    }

    @Step("Отправить форму")
    public void submit() {
        submit.click();
    }

    @Override
    public String pageUrl() {
        return "login";
    }
}
