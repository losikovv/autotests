package ru.instamart.reforged.sber_id_auth;

import ru.instamart.reforged.core.page.Router;
import ru.instamart.reforged.sber_id_auth.auth_page.SberIdAuthPage;

public final class SberIdPageRouter extends Router {

    public static SberIdAuthPage sberId() {
        return (SberIdAuthPage) getPage(SberIdAuthPage.class);
    }
}
