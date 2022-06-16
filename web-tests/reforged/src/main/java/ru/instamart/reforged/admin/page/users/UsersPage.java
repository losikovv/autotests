package ru.instamart.reforged.admin.page.users;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class UsersPage implements AdminPage, UsersCheck {

    @Step("Заполнить поле поиск по email или телефонному номеру: {0}")
    public void fillSearchByEmailOrPhone(String data) {
        searchUserByEmailOrPhone.fill(data);
    }

    @Step("Нажать поиск")
    public void clickToSearch() {
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

    @Override
    public String pageUrl() {
        return "users";
    }
}
