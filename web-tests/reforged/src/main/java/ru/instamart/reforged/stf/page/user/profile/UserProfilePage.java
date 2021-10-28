package ru.instamart.reforged.stf.page.user.profile;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.frame.EmailFrame;
import ru.instamart.reforged.stf.frame.EmailSuccessFrame;
import ru.instamart.reforged.stf.frame.FullName;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.page.StfPage;

import static ru.instamart.reforged.stf.page.user.profile.UserProfileElement.*;

public final class UserProfilePage implements StfPage, UserProfileCheck {

    public FullName interactFullNameForm() {
        return fullNameFrame;
    }

    public EmailFrame interactEmailFrame() {
        return emailFrame;
    }

    public AuthModal interactAuthModal() {
        return authModal;
    }

    @Step("Нажать на кнопку любимые товары в профиле пользователя")
    public void openFavoritePage() {
        openFavorite.click();
    }

    @Step("Нажать на кнопку заказы в профиле пользователя")
    public void openOrders() {
        openOrders.click();
    }

    @Step("Нажать на кнопку аккаунт в профиле пользователя")
    public void openAccount() {
        openAccount.click();
    }

    @Step("Получить значение поля 'Телефон'")
    public String getPhone() {
        return userPhone.getText();
    }

    @Step("Получить значение поля 'ФИО'")
    public String getName() {
        return userName.getText();
    }

    @Step("Получить значение поля 'Email'")
    public String getEmail() {
        return userEmail.getText();
    }

    @Step("Нажать на кнопку 'Изменить Телефон'")
    public void clickToChangePhone() {
        buttonChangePhone.click();
    }

    @Step("Нажать на кнопку 'Изменить ФИО'")
    public void clickToChangeName() {
        buttonChangeName.click();
    }

    @Step("Нажать на кнопку 'Изменить Email'")
    public void clickToChangeEmail() {
        buttonChangeEmail.click();
    }

    @Override
    public String pageUrl() {
        return "user/edit";
    }
}
