package ru.instamart.reforged.business.page.user.profile;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface B2BUserProfileCheck extends B2BUserProfileElement, Check {

    @Step("Проверяем, что отображается информация об аккаунте")
    default void checkUserInfoBlockVisible() {
        Kraken.waitAction().shouldBeVisible(userInfoBlock);
    }
}
