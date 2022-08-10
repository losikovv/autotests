package ru.instamart.reforged.active_directory;

import ru.instamart.reforged.active_directory.login_page.ActiveDirectoryLoginPage;
import ru.instamart.reforged.core.page.Router;

public final class ActiveDirectoryRouter extends Router {

    public static ActiveDirectoryLoginPage activeDirectory() {
        return (ActiveDirectoryLoginPage) getPage(ActiveDirectoryLoginPage.class);
    }
}
