package ru.instamart.reforged.next.block.helpdesk;

import io.qameta.allure.Step;

public final class HelpDesk implements HelpDeskCheck {

    @Step("Открыть чат поддержки")
    public void openChat() {
        chatButton.click();
    }

    @Step("Закрыть чат поддержки")
    public void closeChat() {
        chatButtonClose.click();
    }
}
