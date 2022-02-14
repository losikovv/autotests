package ru.instamart.reforged.business.drawer.cart.account_menu;

import io.qameta.allure.Step;

public final class AccountMenu implements AccountMenuElement, AccountMenuCheck {

    @Step("Нажимаем 'Компании' в  меню пользователя")
    public void clickToCompanies() {
        companies.click();
    }

    @Step("Нажимаем 'Профиль' в  меню пользователя")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Нажимаем 'Условия пользования' в меню пользователя")
    public void clickToTerms() {
        terms.click();
    }

    @Step("Нажимаем 'Выйти' в меню пользователя")
    public void clickToLogout() {
        logout.click();
    }

    @Step("Нажимаем 'Доставка' в меню пользователя")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажимаем 'FAQ' в меню пользователя")
    public void clickToFaq() {
        delivery.click();
    }
}
