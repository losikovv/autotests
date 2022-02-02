package ru.instamart.reforged.business.block.header;

import io.qameta.allure.Step;

public class Header implements HeaderCheck {

    @Step("Нажимаем кнопку 'Указать адрес'")
    public void clickToSelectAddress() {
        selectAddress.click();
    }

    @Step("Нажимаем кнопку 'Войти'")
    public void clickToLogin() {
        login.click();
    }

    @Step("Нажимаем кнопку 'Корзина'")
    public void clickToCart() {
        cart.click();
    }
}
