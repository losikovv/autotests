package ru.instamart.reforged.admin.page.usersEdit;

import io.qameta.allure.Step;
import ru.instamart.reforged.admin.AdminPage;
import ru.instamart.reforged.admin.block.authored_header.AuthoredHeader;
import ru.instamart.reforged.admin.block.flash_alert.FlashAlert;

public final class UsersEditPage implements AdminPage, UsersEditCheck {

    public AuthoredHeader interactAuthoredHeader() {
        return authoredHeader;
    }
    public FlashAlert interactFlashAlert() {
        return alert;
    }

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
        roleAdminCheckbox.click();
    }

    @Step("Снять роль админа")
    public void uncheckAdminRole() {
        roleAdminCheckbox.click();
    }

    @Step("Выбрать B2B")
    public void setB2BUser() {
        b2bUserUnchecked.click();
    }

    @Step("Снять B2B")
    public void unsetB2BUser() {
        b2bUserChecked.click();
    }

    @Step("Нажать на кнопку 'Отвязать'")
    public void clickToBlockPhone() {
        blockPhone.click();
    }

    @Step("Нажать на кнопку 'Заблокировать карты'")
    public void clickToBlockAllCards() {
        blockAllCards.click();
    }

    @Step("Нажать на 'обновить роли' на странице редактирования пользователя")
    public void clickToSaveUserRole() {
        saveRoleChanges.click();
    }

    @Step("Нажать на 'обновить пароль' на странице редактирования пользователя")
    public void clickToSaveUserPassword() {
        savePassordChanges.click();
    }

    @Step("Нажать на 'обновить b2b' на странице редактирования пользователя")
    public void clickToSaveUserB2B() {
        saveB2BChanges.click();
    }

    public void goToPage(final int id) {
        goToPage("users/" + id + "/edit");
    }

    @Override
    public void goToPage() {
        goToPage(1);
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
