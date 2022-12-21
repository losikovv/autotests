package ru.instamart.reforged.chatwoot.frame.account_menu;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface AccountMenuCheck extends Check, AccountMenuElement {

    @Step("Проверяем, что проявилось всплывающее пользовательское меню")
    default void checkAccountMenuDisplayed() {
        accountDropdownMenu.should().visible();
    }

    @Step("Проверяем, что всплывающее пользовательское меню скрылось")
    default void checkAccountMenuNotDisplayed() {
        accountDropdownMenu.should().invisible();
    }

    @Step("Проверяем, что статус: '{stateName}' недоступен")
    default void checkStateNotAvailable(final String stateName) {
        stateByName.should().invisible(stateName);
    }

    @Step("Проверяем, что у пользователя выбран статус: '{stateName}'")
    default void checkActiveUserState(final String stateName) {
        selectedStateByName.should().visible(stateName);
    }

    @Step("Проверяем, что у пользователя заблокирован выбор статуса: '{stateName}'")
    default void checkUserStateDisabled(final String stateName) {
        disabledStateByName.should().visible(stateName);
    }
}
