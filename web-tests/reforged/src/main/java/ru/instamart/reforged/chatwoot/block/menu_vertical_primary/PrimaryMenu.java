package ru.instamart.reforged.chatwoot.block.menu_vertical_primary;

import io.qameta.allure.Step;
import ru.instamart.reforged.chatwoot.ChatwootPage;
import ru.instamart.reforged.chatwoot.frame.account_menu.AccountMenu;

public final class PrimaryMenu implements PrimaryMenuCheck {

    public AccountMenu interactAccountMenu() {
        return accountMenu;
    }

    @Step("Нажимаем на кнопку 'Аналитика'")
    public void clickAnalyticsButton() {
        analytics.click();
    }

    @Step("Нажимаем на кнопку пользовательского меню")
    public void clickAccountMenuButton() {
        accountMenuButton.click();
    }

}
