package ru.instamart.reforged.stf.component.user;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.action.JsAction;

import static ru.instamart.reforged.core.service.KrakenDriver.refresh;

public final class User implements UserCheck {

    @Step("js логаут с очисткой сессии")
    public void clearSessionLogout() {
        JsAction.clearSession();
        refresh();
    }
}
