package ru.instamart.reforged.admin.users;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;

public class UsersPage implements AdminPage, UsersCheck {

    @Step("Заполнить поле email: {0}")
    public void fillEmail(String data) {
        email.fill(data);
    }

    @Step("Заполнить поле телефон: {0}")
    public void fillPhoneNumber(String data) {
        phoneNumber.fill(data);
    }

    @Step("Нажать поиск")
    public void clickOnSubmit() {
        submit.click();
    }

    @Step("Получить email найденного пользователя")
    public String getFoundUserEmail() {
        return userEmail.getText();
    }

    @Override
    public String pageUrl() {
        return "users";
    }
}
