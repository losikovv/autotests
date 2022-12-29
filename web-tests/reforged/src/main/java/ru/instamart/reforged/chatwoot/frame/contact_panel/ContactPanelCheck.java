package ru.instamart.reforged.chatwoot.frame.contact_panel;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface ContactPanelCheck extends Check, ContactPanelElement {

    @Step("Проверяем, что отображается панель информации о беседе")
    default void checkContactPanelVisible() {
        contactPanel.should().visible();
    }

    @Step("Проверяем, что отображаемое имя контакта: {expectedName}")
    default void checkAccountName(final String expectedName) {
        contactName.should().textContains(expectedName);
    }
}
