package ru.instamart.reforged.stf.page.multiretailer_search.search;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.ThreadUtil;
import ru.instamart.reforged.stf.block.header_multisearch.MultisearchHeader;
import ru.instamart.reforged.stf.drawer.cart.Cart;
import ru.instamart.reforged.stf.frame.product_card.ProductCard;
import ru.instamart.reforged.stf.page.StfPage;

import java.util.Set;

public class MultiSearchPage implements StfPage, MultiSearchCheck {

    public MultisearchHeader interactMultisearchHeader() {
        return header;
    }

    public Cart interactCart() {
        return cart;
    }

    public ProductCard interactProductCard() {
        return productCard;
    }

    @Step("Кликаем на сниппет {productPosition}-го товара")
    public void clickProductSnippetByPosition(final int productPosition) {
        productsSnippets.getElements().get(productPosition - 1).click();
    }

    @Step("Кликаем на кнопку 'Добавить в корзину' {productPosition}-го товара")
    public void clickAddProductToCartByPosition(final int productPosition) {
        productsAddToCartButtons.getElements().get(productPosition - 1).click();
    }

    @Step("Кликаем на кнопку 'Добавить в избранное' {productPosition}-го товара")
    public void clickAddProductToFavouritesByPosition(final int productPosition) {
        productAddToFavouriteButtons.getElements().get(productPosition - 1).click();
    }

    @Step("Выбираем '{retailerPosition}'-го рителера")
    public void switchToRetailerByPosition(final int retailerPosition) {
        retailerCardsList.getElements().get(retailerPosition - 1).click();
    }

    @Step("Выбираем '{categoryPosition}'-го категорию")
    public void selectCategoryByPosition(final int categoryPosition) {
        categoriesFilters.getElements().get(categoryPosition - 1).click();
        ThreadUtil.simplyAwait(1);
    }

    @Step("Получаем список товаров текущего ритейлера")
    public Set<String> getProductNames(){
        return productsTitles.getTextFromAllElements();
    }

    @Override
    public String pageUrl() {
        return "multisearch";
    }
}
