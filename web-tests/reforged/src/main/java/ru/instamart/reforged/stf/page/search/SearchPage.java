package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

import static ru.instamart.reforged.core.Kraken.waitAction;

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

    @Step("Нажать кнопку 'Добавить в корзину +' у первого найденного продукта")
    public void clickAddToCartFirstSearchResult() {
        firstAddToCartButton.click();
    }

    @Step("Открыть карточку первого найденного продукта")
    public void openFirstProductCard() {
        firstProductCard.click();
    }

    @Step("Вернуть значение цены первого товара на странице поиска")
    public double returnFirstProductPrice() {
        return StringUtil.stringToDoubleParse(searchProductPrices.getElements().get(0).getText());
    }

    @Step("Вернуть значение цены второго товара на странице поиска")
    public double returnSecondProductPrice() {
        return StringUtil.stringToDoubleParse(searchProductPrices.getElements().get(1).getText());
    }

    @Step("Выбрать сортировку {0}")
    public void selectSort(final String text) {
        selectSort.selectByText(text);
    }

    @Step("Выбрать сортировку {0}")
    public void clickToDiscountFilter() {
        discountFilter.click();
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
