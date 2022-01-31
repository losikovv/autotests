package ru.instamart.reforged.business.page.home;

import ru.instamart.reforged.business.block.header.Header;
import ru.instamart.reforged.business.frame.auth.auth_modal.AuthModal;

public interface BusinessHomeElement {

    Header header = new Header();
    AuthModal authModal = new AuthModal();
}
