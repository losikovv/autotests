package ru.instamart.reforged.admin.page.active_directory.login_page;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.qameta.allure.model.Parameter;
import ru.instamart.reforged.core.page.Page;

public class ActiveDirectoryLoginPage implements Page, ActiveDirectoryLoginPageCheck {

    @Step("Заполнить почту {0}")
    public void fillMail(final String data) {
        mailInput.fill(data);
    }

    @Step("Заполнить пароль")
    public void fillPassword(@Param(value = "password", mode = Parameter.Mode.MASKED) final String data) {
        passwordInput.fill(data);
    }

    @Step("Нажать кнопку 'Подтвердить'")
    public void clickOnLoginButton() {
        loginButton.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
