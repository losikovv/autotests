package ru.instamart.reforged.stf.page.shop;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.CollectionUtil;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Kraken;
import ru.instamart.reforged.core.enums.ShopUrl;
import ru.instamart.reforged.stf.block.footer.Footer;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.block.helpdesk.HelpDesk;
import ru.instamart.reforged.stf.block.retail_rocket.RetailRocket;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.drawer.category_menu.CategoryMenu;
import ru.instamart.reforged.stf.frame.address.Address;
import ru.instamart.reforged.stf.frame.address.AddressLarge;
import ru.instamart.reforged.stf.frame.auth.auth_modal.AuthModal;
import ru.instamart.reforged.stf.frame.disclaimer.Disclaimer;
import ru.instamart.reforged.stf.frame.order_evaluation_modal.OrderEvaluationModal;
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

    public Disclaimer interactDisclaimer() {
        return disclaimer;
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

    @Step("Нажать на плюс у товара на проде")
    public void plusFirstItemToCartProd() {
        addToCartButtonsProd.clickOnFirst();
    }

    @Step("Нажать на плюс у {itemPosition}-го товара")
    public void plusItemToCartByPosition(final int itemPosition) {
        addToCartButtonsProd.getElements().get(itemPosition - 1).click();
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

    @Step("Вернуть значение имени {itemPosition}-го товара на проде")
    public String getProductTitleByPositionProd(final int itemPosition) {
        return productsCardTitlesFromFirstCategory.getElementText(itemPosition - 1);
    }

    @Step("Нажать на минус у товара - строка №{line}, элемент по порядку №{element}")
    public void minusFirstItemFromCart(String line, String element) {
        minusItemFromCart.click(line, element);
    }

    @Step("Добавить первый товар в избранное")
    public void addFirstItemToFavorite() {
        addToFavoriteButtons.clickOnFirst();
    }

    @Step("Добавить первый товар в избранное")
    public void deleteFirstItemFromFavorite() {
        removeFromFavorite.clickOnFirst();
    }

    @Step("Открыть карточку первого товара на стейдже")
    public void openFirstProductCard() {
        firstProductCard.getActions().moveToElementAndClick();
    }

    @Step("Открыть карточку первого товара на проде")
    public void openFirstProductCardProd(final String taxons) {
        Kraken.jsAction().scrollToTheBottom();
        waitPageLoad();
        productsCard.clickOnFirst(taxons);
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
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
