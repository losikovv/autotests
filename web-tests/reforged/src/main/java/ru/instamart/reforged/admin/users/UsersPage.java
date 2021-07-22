package ru.instamart.reforged.admin.users;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

import static ru.instamart.reforged.admin.users.UsersElement.UsersEdit.*;
import static ru.instamart.reforged.admin.users.UsersElement.UsersMain.*;

public class UsersPage implements AdminPage, UsersCheck {

    @Step("Заполнить поле поиск по email: {0}")
    public void fillSearchByEmail(String data) {
        searchEmail.fill(data);
    }

    @Step("Заполнить поле поиск по номеру телефона: {0}")
    public void fillSearchByPhoneNumber(String data) {
        searchPhoneNumber.fill(data);
    }

    @Step("Нажать поиск")
    public void clickToSubmit() {
        submitSearch.click();
    }

    @Step("Получить email найденного пользователя")
    public String getFoundUserEmail() {
        return foundUserEmail.getText();
    }

    @Step("Нажать редактировать профиль пользователя")
    public void clickToEditUser() {
        editUser.click();
    }

    @Step("Заполнить поле email на странице редактирования: {0}")
    public void fillUserEmail(String data) {
        userEmail.fill(data);
    }

    @Step("Очистить поле email")
    public void clearUserEmail() {
        userEmail.clear();
    }

    @Step("Заполнить поле Пароль")
    public void fillPassword(String data) {
        password.fill(data);
    }

    @Step("Заполнить поле Подтверждение пароля")
    public void fillPasswordConfirmation(String data) {
        passwordConfirmation.fill(data);
    }

    @Step("Получить email пользователя на странице редактирования")
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
    public void clickToSaveUserProfileChanges() {
        saveChanges.click();
    }

    @Override
    public String pageUrl() {
        return "users";
    }
}
