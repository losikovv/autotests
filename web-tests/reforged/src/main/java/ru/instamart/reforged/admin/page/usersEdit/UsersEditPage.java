package ru.instamart.reforged.admin.page.usersEdit;

import io.qameta.allure.Step;
import ru.instamart.kraken.config.EnvironmentProperties;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.page.StfPage;

public final class UsersEditPage implements StfPage, UsersEditEditCheck {

    @Step("Заполнить поле email на странице редактирования пользователя: {0}")
    public void fillUserEmail(String data) {
        userEmail.fill(data);
    }

    @Step("Очистить поле email на странице редактирования пользователя")
    public void clearUserEmail() {
        userEmail.clear();
    }

    @Step("Заполнить поле Пароль на странице редактирования пользователя")
    public void fillPassword(String data) {
        password.fill(data);
    }

    @Step("Заполнить поле Подтверждение пароля на странице редактирования пользователя")
    public void fillPasswordConfirmation(String data) {
        passwordConfirmation.fill(data);
    }

    @Step("Получить email пользователя на странице редактирования пользователя")
    public String getEditUserEmail() {
        return userEmail.getValue();
    }

    @Step("Установить роль админа")
    public void checkAdminRole() {
        roleAdminCheckbox.check();
    }

    @Step("Снять роль админа")
    public void uncheckAdminRole() {
        roleAdminCheckbox.uncheck();
    }

    @Step("Выбрать B2B")
    public void setB2BUser() {
        b2bUser.check();
    }

    @Step("Снять B2B")
    public void unsetB2BUser() {
        b2bUser.uncheck();
    }

    @Step("Нажать на Изменить на странице редактирования пользователя")
    public void clickToSave() {
        saveChanges.click();
    }

    @Override
    public void goToPage() {
        goToPage(1);
    }

    public void goToPage(final int id) {
        Kraken.open(EnvironmentProperties.Env.FULL_ADMIN_URL_WITH_BASIC_AUTH + "users/" + id + "/edit");
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
