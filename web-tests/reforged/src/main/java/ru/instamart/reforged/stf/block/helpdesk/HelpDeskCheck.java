package ru.instamart.reforged.stf.block.helpdesk;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HelpDeskCheck extends Check, HelpDeskElement {

    @Step("Проверка что виджет HelpDesk отсутствует на странице")
    default void checkHelpDeskWidgetNotVisible() {
        waitAction().shouldNotBeVisible(chatButton);
    }

    @Step("Проверка что окно открыто")
    default void checkHelpDeskOpen() {
        waitAction().shouldBeVisible(chatContainer);
    }

    @Step("Проверка что окно закрыто")
    default void checkHelpDeskClose() {
        waitAction().shouldNotBeVisible(chatContainer);
    }
}
