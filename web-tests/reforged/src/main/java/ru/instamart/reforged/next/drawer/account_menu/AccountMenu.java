package ru.instamart.reforged.next.drawer.account_menu;

import io.qameta.allure.Step;

public final class AccountMenu implements AccountMenuElement, AccountMenuCheck {

    @Step("Нажимаем 'Компании' в  меню пользователя")
    public void clickToCompanies() {
        companies.click();
    }

    @Step("Нажать Профиль в  меню пользователя")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Нажать Условия пользования в меню пользователя")
    public void clickToTerms() {
        terms.click();
    }

    @Step("Нажать Выйти в меню пользователя")
    public void clickToLogout() {
        logout.click();
    }

    @Step("Нажать Доставка в меню пользователя")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажать FAQ в меню пользователя")
    public void clickToFaq() {
        delivery.click();
    }
}
