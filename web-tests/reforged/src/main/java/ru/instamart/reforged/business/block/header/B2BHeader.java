package ru.instamart.reforged.business.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.drawer.cart.account_menu.B2BAccountMenu;
import ru.instamart.reforged.business.frame.B2BTransferCartModal;
import ru.instamart.reforged.business.frame.store_selector.B2BStoreSelector;

public class B2BHeader implements B2BHeaderCheck {

    public B2BAccountMenu interactAccountMenu() {
        return accountMenu;
    }

    public B2BTransferCartModal interactTransferCartModal() {
        return transferCartModal;
    }

    public B2BStoreSelector interactStoreSelector() {
        return storeSelectorDrawer;
    }

    @Step("Нажимаем кнопку 'Указать адрес'")
    public void clickToSelectAddress() {
        selectAddress.click();
    }

    @Step("Нажимаем кнопку 'Войти'")
    public void clickToLogin() {
        login.click();
    }

    @Step("Нажимаем кнопку 'Корзина'")
    public void clickToCart() {
        cart.click();
    }

    @Step("Нажимаем на кнопку 'Профиль'")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Открываем попап выбора магазина")
    public void clickToStoreSelector() {
        storeSelector.click();
    }
}
