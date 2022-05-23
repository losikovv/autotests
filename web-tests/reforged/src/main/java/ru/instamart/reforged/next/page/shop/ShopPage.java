package ru.instamart.reforged.next.page.shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.next.block.footer.Footer;
import ru.instamart.reforged.next.block.header.Header;
import ru.instamart.reforged.next.block.helpdesk.HelpDesk;
import ru.instamart.reforged.next.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.next.drawer.cart.Cart;
import ru.instamart.reforged.next.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.next.frame.address.Address;
import ru.instamart.reforged.next.frame.address.AddressLarge;
import ru.instamart.reforged.next.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.next.frame.order_evaluation_modal.OrderEvaluationModal;
import ru.instamart.reforged.next.frame.product_card.ProductCard;
import ru.instamart.reforged.next.frame.store_modal.StoreModal;
import ru.instamart.reforged.next.page.StfPage;

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

    public AddressLarge interactAddressLarge() {
        return addressLarge;
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

    public OrderEvaluationModal interactOrderEvaluation() {
        return orderEvaluationModal;
    }

    @Step("Открыть окно ввода адреса доставки")
    public void openAddressFrame() {
        openAddress.click();
    }

    /**
     * @param line    - номер линии по порядку, начиная с 0, в которой находится искомый товар,
     * @param element - номер самого элемента по порядку, начиная с 0
     */
    @Step("Нажать на плюс у товара - строка №{line}, элемент по порядку №{element}")
    public void plusItemToCart(String line, String element) {
        plusItemToCart.click(line, element);
    }

    /**
     * @param line    - номер линии по порядку, начиная с 0, в которой находится искомый товар,
     * @param element - номер самого элемента по порядку, начиная с 0
     */
    @Step("Вернуть значение имени товара - строка №{line}, элемент по порядку №{element}")
    public String getProductTitle(String line, String element) {
        //Если текст длиннее 59 символов, то он обрезается и появляется title
        return productTitle.getTitleOrText(line, element);
    }

    @Step("Нажать на минус у товара - строка №{line}, элемент по порядку №{element}")
    public void minusFirstItemFromCart(String line, String element) {
        minusItemFromCart.click(line, element);
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

    @Step("Открываем карточку продукта в магазине '{shop}' по ссылке: '{productPermalink}'")
    public void openProductCardByLink(final ShopUrl shop, final String productPermalink) {
        goToPage(shop.getUrl() + "/" + productPermalink);
    }

    @Override
    public void goToPage() {
        goToPage(ShopUrl.DEFAULT);
    }

    public void goToPage(final ShopUrl shop) {
        goToPage(shop.getUrl());
        cookiesChange(false);
    }

    @Override
    public void goToPageFromTenant() {
        goToPageFromTenant(ShopUrl.DEFAULT, false);
    }

    public void goToPageFromTenant(final boolean isFixedUUID) {
        goToPageFromTenant(ShopUrl.DEFAULT, isFixedUUID);
    }

    public void goToPageFromTenant(final ShopUrl shop, final boolean isFixedUUID) {
        goToPageFromTenant(shop.getUrl());
        cookiesChange(isFixedUUID);
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
