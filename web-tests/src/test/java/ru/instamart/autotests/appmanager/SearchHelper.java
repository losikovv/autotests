package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.BrowserType;
import ru.instamart.autotests.configuration.Elements;
import ru.instamart.autotests.configuration.Environments;

public class SearchHelper extends HelperBase {
    private ApplicationManager kraken;

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
        kraken.perform().fillField(Elements.Site.Header.Search.searchField(), query);
        waitFor(1);
    }

    /** Нажать кнопку поиска */
    public void hitSearchButton() {
        kraken.perform().click((Elements.Site.Header.Search.searchButton()));
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isCategorySuggestsPresent() {
        return isElementPresent(Elements.Site.Header.Search.categorySuggest());
    }

    /** Нажать на категорийный саджест */
    public void hitCategorySuggest() {
        kraken.perform().click(Elements.Site.Header.Search.categorySuggest());
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isProductSuggestsPresent() {
        return isElementPresent(Elements.Site.Header.Search.productSuggest());

    }

    /** Нажать на продуктовый саджест */
    public void hitProductSuggest() {
        kraken.perform().click(Elements.Site.Header.Search.productSuggest());
    }
}
