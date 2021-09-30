package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import ru.instamart.reforged.stf.block.header.Header;
import ru.instamart.reforged.stf.page.StfPage;

public class SearchPage implements StfPage, SearchCheck {

    public Header interactHeader() {
        return header;
    }

    @Step("Нажать кнопку 'Добавить в корзину +' у первого найденного продукта")
    public void clickAddToCartFirstSearchResult() {
        firstAddToCartButton.click();
    }

    @Override
    public String pageUrl() {
        return "";
    }
}
