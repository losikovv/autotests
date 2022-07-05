package ru.instamart.reforged.stf.page.seo;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public final class SeoCatalogPage implements StfPage, SeoCatalogCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
    }

    public Cart interactCart() {
        return cart;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    @Step("Открыть карточку товара")
    public void openFirstProductCardOnTaxon() {
        firstProductCard.click();
    }

    @Step("Открыть карточку товара")
    public void openFirstProductCardOnDepartment() {
        firstProductCardOnDepartment.click();
    }

    @Step("Нажимаем кнопку 'Добавить в корзину' первого товара")
    public void addFirstProductOnDepartmentToCart() {
        firstProductAddToCart.click();
    }

    @Step("Нажимаем кнопку 'Убрать из корзины' первого товара")
    public void removeFirstProductOnDepartmentFromCart() {
        firstProductRemoveFromCart.click();
    }

    @Step("Выбрать подкатегорию {0} со страницы каталога")
    public void clickOnSubCategory(String data) {
        catalogSubCategories.clickOnElementWithText(data);
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl() + pageUrl());
    }

    @Step("Выбрать сортировку {text}")
    public void selectSort(final String text) {
        selectSort.selectByText(text);
    }

    @Override
    public String pageUrl() {
        return "/c/new-bakalieia/krupy/griechnievaia";
    }
}
