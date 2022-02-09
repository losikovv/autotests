package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.disclaimer.Disclaimer;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public class SearchPage implements StfPage, SearchCheck {

    public Header interactHeader() {
        return header;
    }

    public Cart interactCart() {
        return cart;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    public Disclaimer interactDisclaimerModal() {
        return disclaimer;
    }

    @Step("Нажать кнопку 'Добавить в корзину +' у первого найденного продукта")
    public void clickAddToCartFirstSearchResult() {
        firstAddToCartButton.click();
    }

    @Step("Открыть на просмотр карточку первого найденного продукта")
    public void clickOnFirstSearchResult() {
        searchProductsCollection.clickOnFirst();
    }

    @Step("Открыть карточку первого найденного продукта")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Step("Вернуть значение цены товара #{0} на странице поиска")
    public double returnProductPrice(int number) {
        return StringUtil.stringToDouble(searchProductPrices.getElementText(number));
    }

    @Step("Вернуть значение кол-ва товаров на странице поиска")
    public int returnSearchProductsQuantity() {
        return searchProductsQuantity.getNumericValue();
    }

    @Step("Выбрать сортировку {0}")
    public void selectSort(final String text) {
        selectSort.selectByText(text);
    }

    @Step("Выбрать фильтр 'Товары со скидкой'")
    public void clickToDiscountFilter() {
        discountFilter.click();
    }

    @Step("Выбрать фильтр '{0}'")
    public void clickOnFilter(String filterText) {
        filtersCollection.clickOnElementWithText(filterText);
    }

    @Step("Переходим в подкатегорию {0}")
    public void clickOnSubCategory(final String title) {
        subCategories.clickOnElementWithText(title);
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
