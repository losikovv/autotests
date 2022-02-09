package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.frame.store_modal.StoreModal;
import ru.instamart.reforged.stf.page.StfPage;

import java.util.List;

public final class ShopPage implements StfPage, ShopCheck {

    public AuthModal interactAuthModal() {
        return authModal;
    }

    public Header interactHeader() {
        return header;
    }

    public StoreModal interactStoreModal() {
        return storeModal;
    }

    public RetailRocket interactRetailRocket() {
        return retailRocket;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    public Cart interactCart() {
        return cart;
    }

    public Address interactAddress() {
        return address;
    }

    public CategoryMenu interactCategoryMenu() {
        return categoryMenu;
    }

    public Footer interactFooter() {
        return footer;
    }

    public HelpDesk interactHelpDesk() {
        return helpDesk;
    }

    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCart() {
        plusFirstItemToCartAddedAddress.click();
    }

    @Step("Нажать на плюс у второго товара")
    public void plusSecondItemToCart() {
        plusSecondItemToCartAddedAddress.click();
    }

    @Step("Нажать на плюс у первого товара")
    public void plusFirstItemToCartNonLogin() {
        plusFirstItemToCartNonLogin.click();
    }

    @Step("Нажать на плюс у второго товара")
    public void plusSecondItemToCartNonLogin() {
        plusSecondItemToCartNonLogin.click();
    }

    @Step("Вернуть значение имени первого товара")
    public String getFirstProductTitle() {
        //Если текст длиннее 59 символов, то он обрезается и появляется title
        return firstProductTitle.getTitleOrText();
    }

    @Step("Вернуть значение имени второго товара")
    public String getSecondProductTitle() {
        //Если текст длиннее 59 символов, то он обрезается и появляется title
        return secondProductTitle.getTitleOrText();
    }

    @Step("Вернуть значение имени первого товара")
    public String getFirstProductTitleNonLogin() {
        //Если текст длиннее 59 символов, то он обрезается и появляется title
        return firstProductTitleNonLogin.getTitleOrText();
    }

    @Step("Вернуть значение имени второго товара")
    public String getSecondProductTitleNonLogin() {
        //Если текст длиннее 59 символов, то он обрезается и появляется title
        return secondProductTitleNonLogin.getTitleOrText();
    }

    @Step("Нажать на минус у первого товара")
    public void minusFirstItemFromCart() {
        minusFirstItemFromCartAddedAddress.hoverAndClick();
    }

    @Step("Добавить первый товар в избранное")
    public void addFirstItemToFavorite() {
        addFirstItemToFavorite.hoverAndClick();
    }

    @Step("Добавить первый товар в избранное")
    public void deleteFirstItemFromFavorite() {
        deleteFirstItemFromFavorite.hoverAndClick();
    }

    @Step("Открыть карточку первого товара на проде")
    public void openFirstProductCardProd() {
        firstProductCardProd.click();
    }

    @Step("Открыть карточку первого товара на стейдже")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Step("Получаем цену товара")
    public String getPrice() {
        return StringUtil.getLastLine(priceInFirstItemWithoutDiscount.getText());
    }

    @Step("Получаем цену товара без скидки (для товара со скидкой)")
    public String getFullPrice() {
        return StringUtil.getLastLine(fullPriceInFirstItemWithDiscount.getText());
    }

    @Step("Получаем цену товара с учётом скидки (для товара со скидкой)")
    public String getPriceWithDiscount() {
        return StringUtil.getLastLine(discountPriceInFirstItemWithDiscount.getText());
    }

    @Step("Открыть карточку первого товара со скидкой")
    public void openFirstProductCardWithoutDiscount() {
        priceInFirstItemWithoutDiscount.hoverAndClick();
    }

    @Step("Открыть карточку первого товара без скидки")
    public void openFirstProductCardWithDiscount() {
        fullPriceInFirstItemWithDiscount.hoverAndClick();
    }

    @Step("Получаем название первой категории товаров в магазине")
    public String getFirstCategoryTitle() {
        return firstProductsCategoryTitle.getText();
    }

    @Step("Получаем названия продуктов из первой категории товаров магазина")
    public List<String> getFirstCategoryProductNames() {
        //TODO Костыль потому что название продукта длинее 60 символов обрезается, невозможно сравнить в карточке, корзине, истории
        return CollectionUtil.cropItemsByLengthAndSort(firstCategoryProductNames.getTextFromAllElements(), 59);
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl());
        cookiesChange(false);
    }

    /**
     * Метод создан для того, что бы в куках указывать фиксированный UUID пользователя, который заранее добавлен в нужную АБ группу
     *
     * @param isFixedUUID - служит идентификатором для UUID
     */
    public void goToPage(final boolean isFixedUUID) {
        goToPage(ShopUrl.DEFAULT, isFixedUUID);
        cookiesChange(isFixedUUID);
    }

    public void goToPage(final ShopUrl shop, final boolean isFixedUUID) {
        goToPage(shop.getUrl());
        cookiesChange(isFixedUUID);
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
