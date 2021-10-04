package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

public class SearchPage implements StfPage, SearchCheck {

    public Header interactHeader() {
        return header;
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

    @Override
    public String pageUrl() {
        return "";
    }
}
