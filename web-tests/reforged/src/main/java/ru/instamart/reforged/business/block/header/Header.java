package ru.instamart.reforged.business.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.drawer.cart.account_menu.AccountMenu;
import ru.instamart.reforged.business.frame.TransferCartModal;
import ru.instamart.reforged.business.frame.store_selector.StoreSelector;

public class Header implements HeaderCheck {

    public AccountMenu interactAccountMenu() {
        return accountMenu;
    }

    public TransferCartModal interactTransferCartModal() {
        return transferCartModal;
    }

    public StoreSelector interactStoreSelector() {
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
