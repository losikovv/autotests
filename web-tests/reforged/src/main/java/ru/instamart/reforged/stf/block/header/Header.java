package ru.instamart.reforged.stf.block.header;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.action.JsAction;
import ru.instamart.reforged.stf.drawer.account_menu.AccountMenu;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.TransferCartModal;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.store_selector.StoreSelector;

import static ru.instamart.reforged.stf.page.StfRouter.shop;

public final class Header implements HeaderCheck {

    public Cart interactCart() {
        return cartFrame;
    }

    public Address interactAddress() {
        return addressDrawer;
    }

    public AccountMenu interactAccountMenu() {
        return accountMenu;
    }

    public StoreSelector interactStoreSelector() {
        return storeSelectorDrawer;
    }

    public TransferCartModal interactTransferCartModal() {
        return transferCartModal;
    }

    @Step("Нажать на лого")
    public void clickToLogo() {
        logo.click();
    }

    @Step("Выбрать магазин")
    public void clickToStoreSelector() {
        storeSelector.click();
    }

    @Step("Ввести текст для поиска {0}")
    public void fillSearch(final String text) {
        searchInput.fill(text);
    }

    @Step("Очистить инпут поиска")
    public void clearSearchInput() {
        searchInputResetButton.click();
    }

    @Step("Свайп списка категорий в саджесторе вправо")
    public void swipeScrollTabHeadersRight() {
        scrollTabHeadersRight.click();
    }

    @Step("Свайп списка категорий в саджесторе влево")
    public void swipeScrollTabHeadersLeft() {
        scrollTabHeadersLeft.click();
    }

    @Step("Выбрать первую найденную категорию в саджесторе")
    public void clickOnFirstSuggesterCategory() {
        suggesterTabHeaders.clickOnFirst();
    }

    @Step("Выбрать последнюю категорию в саджесторе")
    public void clickOnLastSuggesterCategory() {
        suggesterTabHeaders.clickOnAll();
    }

    @Step("Выбрать первый результат поиска в саджесторе")
    public void clickOnFirstSuggesterSearchResult() {
        suggesterFirstTabItems.clickOnFirst();
    }

    @Step("Нажимаем кнопку 'Показать все *** результаты' поиска в саджесторе")
    public void clickShowAllSearchResults() {
        showAllResults.click();
    }

    @Step("Получаем текст из кнопки 'Показать все N результатов' в саджесторе")
    public String getTextOnButtonSearchSuggester() {
        return showAllResults.getText();
    }

    @Step("Нажать кнопку поиска")
    public void clickSearchButton() {
        searchButton.click();
    }

    @Step("Открыть мень профиля")
    public void clickToProfile() {
        profile.click();
    }

    @Step("Открыть страницу Избранное")
    public void clickToFavorite() {
        favorite.click();
    }

    @Step("Открыть корзину")
    public void clickToCart() {
        cart.click();
    }

    @Step("Открыть страницу заказов")
    public void clickToOrders() {
        orders.click();
    }

    @Step("Нажать войти")
    public void clickToLogin() {
        login.click();
    }

    @Step("Нажать на Доставка")
    public void clickToDelivery() {
        delivery.click();
    }

    @Step("Нажать на самовывоз")
    public void clickToPickup() {
        pickup.click();
    }

    @Step("Нажать на Выберите адрес доставки(первый выбор адреса)")
    public void clickToSelectAddressFirstTime() {
        firstSelectAddress.click();
    }

    @Step("Нажать на выбор адреса")
    public void clickToSelectAddress() {
        selectAddress.click();
    }

    @Step("Открыть страницу Ваши компании")
    public void clickToForB2B() {
        forB2B.click();
    }

    @Step("Открыть лендинг для брендов")
    public void clickToForBrands() {
        forBrands.click();
    }

    @Step("Открыть страницу Как мы работаем")
    public void clickToHowWeWork() {
        howWeWork.click();
    }

    @Step("Открыть страницу Контакты")
    public void clickToContacts() {
        contacts.click();
    }

    @Step("Открыть страницу FAQ")
    public void clickToHelp() {
        help.click();
    }

    @Step("Открыть страницу Доставка и оплата")
    public void clickToDeliveryAndPayment() {
        deliveryAndPayment.click();
    }

    @Step("Открыть каталог")
    public void clickToCategoryMenu() {
        categoryMenu.click();
    }

    @Step("Получить текст выбранный адрес")
    public String getShippingAddressFromHeader() {
        shop().interactHeader().interactAddress().checkAddressModalIsNotVisible();
        return enteredAddress.getText();
    }

    /* метод clearSessionLogout() нежелательный к использованию, потенциально флакует много тестов */
    @Step("js логаут с очисткой сессии")
    public void clearSessionLogout() {
        JsAction.clearSession();
        Kraken.clearAllCooke();
        Kraken.refresh();
    }

    @Step("Вернуть текущий адрес")
    public String returnCurrentAddress() {
        return enteredAddress.getText();
    }

    @Step("Нажимаем на ссылку 'Покупайте для бизнеса'")
    public void clickBuyForBusiness() {
        buyForBusiness.click();
    }
}
