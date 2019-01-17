package ru.instamart.autotests.appmanager;

import org.openqa.selenium.WebDriver;
import ru.instamart.autotests.application.Elements;
import ru.instamart.autotests.application.Environments;

public class SearchHelper extends HelperBase {

    private ApplicationManager kraken;

    SearchHelper(WebDriver driver, Environments environment, ApplicationManager app) {
        super(driver, environment);
        kraken = app;
    }

    /** Осуществить поиск товара по запросу */
    public void item(String query) {
        printMessage("Поиск товаров по запросу \"" + query + "\"...");
        fillSearchFieldWith(query);
        hitSearchButton();
        kraken.perform().waitingFor(1); // Ожидание загрузки результатов поиска
    }

    /** Заполнить поле поиска */
    public void fillSearchFieldWith(String query) {
        kraken.perform().fillField(Elements.Site.Header.Search.searchField(), query);
        kraken.perform().waitingFor(1); // Ожидание загрузки поисковых саджестов
    }

    /** Нажать кнопку поиска */
    public void hitSearchButton() {
        kraken.perform().click((Elements.Site.Header.Search.searchButton()));
    }

    /** Проверяем наличие категорийного саджеста */
    public boolean isCategorySuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Site.Header.Search.categorySuggest());
    }

    /** Нажать на категорийный саджест */
    public void hitCategorySuggest() {
        kraken.perform().click(Elements.Site.Header.Search.categorySuggest());
    }

    /** Проверяем наличие товарных саджестов */
    public boolean isProductSuggestsPresent() {
        return kraken.detect().isElementPresent(Elements.Site.Header.Search.productSuggest());
    }

    /** Нажать на продуктовый саджест */
    public void hitProductSuggest() {
        kraken.perform().click(Elements.Site.Header.Search.productSuggest());
    }
}
