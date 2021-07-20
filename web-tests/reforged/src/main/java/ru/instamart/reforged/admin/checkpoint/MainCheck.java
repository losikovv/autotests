package ru.instamart.reforged.admin.checkpoint;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import ru.instamart.reforged.admin.element.MainElement;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

@RequiredArgsConstructor
public class MainCheck implements Check{

    protected final MainElement element;

    @Step("Пользователь авторизовался")
    public void checkAuth() {
        waitAction().shouldBeVisible(element.user()).isDisplayed();
    }



}
