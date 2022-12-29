package ru.instamart.reforged.chatwoot.frame.edit_popover;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

public interface EditPopoverCheck extends Check, EditPopoverElement {

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

}
