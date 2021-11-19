package ru.instamart.reforged.stf.drawer.cookie;

import io.qameta.allure.Step;

public final class CookieDrawer implements CookieDrawerCheck {

    @Step("Нажать на кнопку 'Понятно' в алерте cookie")
    public void clickToOk() {
        alertButton.click();
    }

    @Step("Нажать на ссылку с информацией о cookie в алерте")
    public void clickToLink() {
        alertLink.click();
    }
}
