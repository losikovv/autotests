package ru.instamart.reforged.stf.page.user.profile;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.stf.page.StfPage;

import static ru.instamart.reforged.stf.page.user.profile.UserProfileElement.*;

public final class UserProfilePage implements StfPage, UserProfileCheck{

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

    @Override
    public String pageUrl() {
        return "user/edit";
    }
}
