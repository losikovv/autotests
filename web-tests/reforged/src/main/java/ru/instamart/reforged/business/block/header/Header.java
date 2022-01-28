package ru.instamart.reforged.business.block.header;

import io.qameta.allure.Step;

public class Header implements HeaderCheck {

    @Step("Нажимаем кнопку 'Войти'")
    public void clickToLogin() {
        login.click();
    }
}
