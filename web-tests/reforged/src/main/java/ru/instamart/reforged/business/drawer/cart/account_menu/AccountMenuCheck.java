package ru.instamart.reforged.business.drawer.cart.account_menu;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface AccountMenuCheck extends Check, AccountMenuElement {
    @Step("Проверяем, что в вменю пользователя отображается имя пользователя, или телефон")
    default void checkProfileNameExists() {
        waitAction().shouldBeVisible(username);
    }

    @Step("Проверяем, что в меню пользователя присутствует кнопка перехода в профиль")
    default void checkProfileButtonExists() {
        waitAction().shouldBeVisible(profile);
    }

    @Step("Проверяем, что в меню пользователя присутствует кнопка Компании")
    default void checkCompaniesButtonExists() {
        waitAction().shouldBeVisible(companies);
    }

    @Step("Проверяем, что в меню пользователя присутствует кнопка Услоявия пользования")
    default void checkTermsButtonExists() {
        waitAction().shouldBeVisible(terms);
    }

    @Step("Проверяем, что в меню пользователя присутствует кнопка выхода из аккаунта")
    default void checkLogoutButtonExists() {
        waitAction().shouldBeVisible(logout);
    }

    @Step("Проверяем, что меню пользователя открыто")
    default void checkAccountMenuVisible() {
        waitAction().shouldBeVisible(accountMenu);
    }

}
