package ru.instamart.reforged.chatwoot;

import ru.instamart.reforged.chatwoot.page.analitycs.AnalyticsPage;
import ru.instamart.reforged.chatwoot.page.dialogs.DialogsPage;
import ru.instamart.reforged.chatwoot.page.login.LoginPage;
import ru.instamart.reforged.core.page.Router;

public final class ChatwootRouter extends Router {

    public static LoginPage login() {
        return (LoginPage) getPage(LoginPage.class);
    }

    public static DialogsPage dialogs() {
        return (DialogsPage) getPage(DialogsPage.class);
    }

    public static AnalyticsPage analytics() {
        return (AnalyticsPage) getPage(AnalyticsPage.class);
    }

    private ChatwootRouter() {
    }
}
