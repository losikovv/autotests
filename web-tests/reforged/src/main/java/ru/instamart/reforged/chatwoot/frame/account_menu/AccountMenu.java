package ru.instamart.reforged.chatwoot.frame.account_menu;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.ChatwootPage;

public final class AccountMenu implements AccountMenuCheck {

    @Step("Выбираем в пользовательском меню статус: '{stateName}'")
    public void clickUserState(final String stateName) {
        stateByName.click(stateName);
    }

    @Step("Нажимаем 'Выйти'")
    public void clickLogout() {
        logout.click();
    }
}
