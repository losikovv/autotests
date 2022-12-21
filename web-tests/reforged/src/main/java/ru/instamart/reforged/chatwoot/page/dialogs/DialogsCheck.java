package ru.instamart.reforged.chatwoot.page.dialogs;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface DialogsCheck extends Check, DialogsElement {

    @Step("Проверяем, что отображается выпадающий список значений фильтра по статусу диалога")
    default void checkStatusFilterOptionsListVisible(){
        filterOptions.should().visible();
    }

    @Step("Проверяем, что активна вкладка групп: '{expectedTabName}'")
    default void checkActiveGroupTab(final String expectedTabName) {
        Assert.assertTrue(activeDialogTab.getText().contains(expectedTabName),
                String.format("Активная вкладка: '%s' отличается от ожидаемой: '%s'", activeDialogTab.getText(), expectedTabName));
    }

    @Step("Проверяем, что количество чатов, указанное на вкладке: {expectedCount}")
    default void checkConversationCountOnActiveTab(final String expectedCount) {
        activeDialogTabConversationCount.should().textContains(expectedCount);
    }

    @Step("Проверяем, что список обращений в группе пуст")
    default void checkConversationsListIsEmpty() {
        emptyConversationList.should().visible();
    }

    @Step("Проверяем, что в списке отображаются диалоги")
    default void checkConversationsVisible() {
        conversations.should().visible();
    }

    @Step("Проверяем, что количестов диалогов с списке соответствует ожидаемому: {expectedCount}")
    default void checkConversationsCount(final int expectedCount) {
        Assert.assertTrue(waitAction().isElementCollectionSizeEqual(conversations, expectedCount));
    }

    @Step("Проверяем, что чат с названием '{chatName}' отображается")
    default void checkConversationVisible(final String chatName){
        conversationByName.should().visible(chatName.toLowerCase());
    }

    @Step("Проверяем, что чат с названием '{chatName}' не отображается")
    default void checkConversationNotVisible(final String chatName){
        conversationByName.should().invisible(chatName.toLowerCase());
    }

    @Step("Проверяем, что отображается ссылка 'Загрузить больше диалогов'")
    default void checkLoadMoreConversationVisible() {
        loadMoreConversation.should().visible();
    }

    @Step("Проверяем, что отображается сообщение 'Все диалоги загружены'")
    default void checkLoadCompleteMsgVisible() {
        allConversationWasLoadedMessage.should().visible();
    }

    @Step("Проверяем, что отображается кнопка редактирования обращения")
    default void checkEditConversationButtonVisible() {
        editConversation.should().visible();
    }

    @Step("Проверяем, что отображается всплывающее сообщение об успешном выполнении")
    default void checkNotificationMessageVisible() {
        notificationMessage.should().visible();
    }

    @Step("Проверяем, что отображается тултип о необходимости заполнить тему обращения")
    default void checkNeedFillTopicTooltipVisible() {
        needFillTopicTooltip.should().visible();
    }

    @Step("Проверяем, что текст всплывающего сообщения содержит: '{expectedText}'")
    default void checkNotificationMessageText(final String expectedText) {
        notificationMessage.should().textContains(expectedText);
    }

    @Step("Проверяем, что в заголовке диалога отображается пометка 'Приостановлено до'")
    default void checkHoldoverHeaderLabelVisible() {
        holdoverLabelInHeader.should().visible();
    }

    @Step("Проверяем, что в пометке о приостановке хедера текст: '{expectedText}'")
    default void checkHoldoverHeaderLabelDateTime(final String expectedText) {
        holdoverLabelInHeader.should().textContains(expectedText);
    }

    @Step("Проверяем, что последнее системное сообщение в чате содержит текст: {expectedText}")
    default void checkLastSystemChatMessageContains(final String expectedText) {
        Assert.assertTrue(lastSystemInfoMessage.getText().contains(expectedText),
                "Последнее системное сообщение в чате не содержит ожидаемый текст");
    }

    @Step("Проверяем, что отображается кнопка 'Открыть заново'")
    default void checkReopenConversationVisible() {
        reopenConversation.should().visible();
    }
}
