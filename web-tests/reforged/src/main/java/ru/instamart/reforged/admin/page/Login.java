package ru.instamart.reforged.admin.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.kraken.testdata.UserData;
import ru.instamart.reforged.admin.checkpoint.LoginCheck;
import ru.instamart.reforged.core.component.Button;
import ru.instamart.reforged.core.component.Input;

public final class Login implements AdminPage, LoginCheck {

    private final Input username = new Input(By.name("email"));
    private final Input password = new Input(By.name("password"));
    private final Button submit = new Button(By.xpath("//button[@type='submit']"));

    @Step("Авторизация {0}")
    public void auth(final UserData userData) {
        setUsername(userData.getLogin());
        setPassword(userData.getPassword());
        submit();
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
