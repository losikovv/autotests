package ru.instamart.reforged.business.page.user.profile;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface UserProfileCheck extends UserProfileElement, Check {

    @Step("Проверяем, что отображается информация об аккаунте")
    default void checkUserInfoBlockVisible() {
        Kraken.waitAction().shouldBeVisible(userInfoBlock);
    }
}
