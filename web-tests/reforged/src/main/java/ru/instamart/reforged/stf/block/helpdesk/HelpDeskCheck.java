package ru.instamart.reforged.stf.block.helpdesk;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HelpDeskCheck extends Check, HelpDeskElement {

    @Step("Проверка что виджет HelpDesk отсутствует на странице")
    default void checkHelpDeskWidgetNotVisible() {
        Assert.assertTrue(chatButton.is().invisible());
    }

    @Step("Проверка что окно открыто")
    default void checkHelpDeskOpen() {
        waitAction().shouldBeVisible(chatContainer);
    }

    @Step("Проверка что окно закрыто")
    default void checkHelpDeskClose() {
        Assert.assertTrue(chatContainer.is().invisible());
    }
}
