package ru.instamart.reforged.business.block.header_multisearch;

import io.qameta.allure.Step;
import ru.instamart.reforged.business.drawer.cart.B2BCart;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.address.AddressLarge;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;

import java.util.Set;

public final class B2BMultisearchHeader implements B2BMultisearchHeaderCheck {

    public Address interactAddress() {
        return addressModal;
    }

    public AddressLarge interactAddressLarge() {
        return addressLargeModal;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    public B2BCart interactCart() {
        return cart;
    }

    @Step("Выбираем 'Доставка'")
    public void switchToDelivery() {
        delivery.click();
    }

    @Step("Выбираем 'Самовывоз'")
    public void switchToPickup() {
        pickup.click();
    }

    @Step("Кликаем за кнопку 'искать' (лупа)")
    public void clickStartSearch() {
        startSearch.click();
    }

    @Step("Кликаем вне саджестора")
    public void clickOutOffSuggester() {
        startSearch.getActions().clickWithOffset(50, 0);
    }

    @Step("Кликаем на кнопку выбора адреса")
    public void clickChangeAddress() {
        addressChange.click();
    }

    @Step("Нажимаем на кнопку 'Избранное'")
    public void clickToFavourites() {
        userFavourites.click();
    }

    @Step("Нажимаем на кнопку открытия корзины")
    public void clickToCart() {
        openCart.click();
    }

    @Step("Вводим в поле межритейлерного поиска: {searchText}")
    public void fillMultisearch(final String searchText) {
        multisearch.fill(searchText);
    }

    @Step("Кликаем на {productPosition}-й товар в саджесторе")
    public void clickProductInSuggester(final int productPosition) {
        productsInActiveTab.getElements().get(productPosition - 1).click();
    }

    @Step("Кликаем на кнопку 'Добавить в корзину' {productPosition}-го товара в саджесторе")
    public void clickAddProductToCartInSuggester(final int productPosition) {
        productsAddToCart.getElements().get(productPosition - 1).click();
    }

    @Step("Выбираем '{retailerPosition}'-го рителера")
    public void switchToRetailerByPosition(final int retailerPosition) {
        retailersInDropdown.getElements().get(retailerPosition - 1).click();
    }

    @Step("Получаем список товаров текущего ритейлера")
    public Set<String> getProductNames(){
        return productsTitleInActiveTab.getTextFromAllElements();
    }

    @Step("Раскрываем пользовательское меню")
    public void openUserActions(){
        userActionsToggle.click();
    }


}
