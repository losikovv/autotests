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
    public void clickOnSubmit() {
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

    @Step("Получить email пользователя на странице редактирования")
    public String getEditUserEmail() {
        return userEmail.getValue();
    }

    @Step("Нажать на Изменить")
    public void clickToSaveChanges() {
        saveChanges.click();
    }

    @Override
    public String pageUrl() {
        return "users";
    }
}
