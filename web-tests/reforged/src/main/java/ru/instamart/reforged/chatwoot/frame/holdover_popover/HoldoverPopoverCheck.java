package ru.instamart.reforged.chatwoot.frame.holdover_popover;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.chatwoot.frame.date_time_picker.DateTimePicker;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface HoldoverPopoverCheck extends Check, HoldoverPopoverElement {

    @Step("Проверяем, что отображается всплывающее окно редактирования обращения")
    default void checkEditConversationPopoverVisible() {
        editPopover.should().visible();
    }

    @Step("Проверяем, что отображается список тем обращений")
    default void checkTopicsListVisible() {
        topicsList.should().visible();
    }

    @Step("Проверяем, что список тем обращений не отображается")
    default void checkTopicsListNotVisible() {
        topicsList.should().invisible();
    }

    @Step("Проверяем, что отображается всплывающее окно 'Отложить до'")
    default void checkHoldoverConversationPopoverVisible() {
        holdoverPopover.should().visible();
    }

    @Step("Проверяем, что всплывающее окно 'Отложить до' не отображается")
    default void checkHoldoverConversationPopoverNotVisible() {
        holdoverPopover.should().invisible();
    }
}
