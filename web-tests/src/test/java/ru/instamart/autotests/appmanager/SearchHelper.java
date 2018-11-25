package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;



// ======= Поиск товаров =======



public class SearchHelper extends HelperBase {
    SearchHelper(WebDriver driver, Environments environment) {
        super(driver, environment);
    }

    /** Заполнить поле поиска */
    public void item(String query) {
        printMessage("Searching products on query \"" + query + "\"...");
        fillSearchFieldWith(query);
        hitSearchButton();
        waitFor(1);
    }

    /** Заполнить поле поиска */
    public void fillSearchFieldWith(String query) {
        fillField(Elements.Site.Header.Search.searchField(), query);
        waitFor(1);
    }

    /** Нажать кнопку поиска */
    public void hitSearchButton() {
        click((Elements.Site.Header.Search.searchButton()));
    }

    /** Проверка пустого результата поиска */
    public boolean isResultsEmpty() {
        if(isElementPresent(Elements.Site.Catalog.emptySearchPlaceholder())){
            printMessage("Empty search results");
            return true;
        } else return false;
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isCategorySuggestsPresent() {
        return isElementPresent(Elements.Site.Header.Search.categorySuggest());
    }

    /** Нажать на категорийный саджест */
    public void hitCategorySuggest() {
        click(Elements.Site.Header.Search.categorySuggest());
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isProductSuggestsPresent() {
        return isElementPresent(Elements.Site.Header.Search.productSuggest());

    }

    /** Нажать на продуктовый саджест */
    public void hitProductSuggest() {
        click(Elements.Site.Header.Search.productSuggest());
    }
}
