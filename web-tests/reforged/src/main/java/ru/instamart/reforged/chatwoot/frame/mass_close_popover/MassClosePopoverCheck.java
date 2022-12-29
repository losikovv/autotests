package ru.instamart.reforged.chatwoot.frame.mass_close_popover;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

public interface MassClosePopoverCheck extends Check, MassClosePopoverElement {

    @Step("Проверяем, что отображается всплывающее окно массового закрытия обращений")
    default void checkMassClosePopoverVisible() {
        massClosePopover.should().visible();
    }

    @Step("Проверяем, что отображается список тематик обращений")
    default void checkTopicsListVisible() {
        topicsList.should().visible();
    }

    @Step("Проверяем, что список тематик обращений не отображается")
    default void checkTopicsListNotVisible() {
        topicsList.should().invisible();
    }

    @Step("Проверяем, что отображается сообщение 'Будет завершено ХХХ диалогов'")
    default void checkFilteredChatsCountMessageVisible() {
        filteredChatsCountMessage.should().visible();
    }

    @Step("Проверяем, что количество чатов для массового закрытия при выбранных параметрах фильтров отлично от 0")
    default void checkFilteredChatsCountNotNull(){
        Assert.assertNotEquals(StringUtil.parseNumericFromString(filteredChatsCountMessage.getText()), "0", "Количество отобранных для закрытия чатов равно 0");
    }
}
