package ru.instamart.ui.checkpoint.search;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.ui.checkpoint.Checkpoint;
import ru.instamart.ui.Elements;

import static org.testng.Assert.assertTrue;
import static ru.instamart.kraken.util.StringUtil.failMessage;
import static ru.instamart.ui.module.Base.kraken;

public class SearchResultCheckpoints implements Checkpoint {

    @Step("Проверяем, что поисковый запрос не возвращает результатов")
    public void checkIsSearchResultEmpty(String errorMessage){
        log.debug("> проверяем, что поисковый запрос не возвращает результатов");
        assertTrue(kraken.detect().isSearchResultsEmpty(),
                errorMessage);
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что поисковый запрос возвращает результы")
    public void checkIsSearchResultNotEmpty(String errorMessage){
        log.debug("> проверяем, что поисковый запрос возвращает результы");
        Assert.assertFalse(
                kraken.detect().isSearchResultsEmpty(),
                failMessage(errorMessage)
        );
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что продукт доступен на странице")
    public void checkIsProductAvailable(String errorMessage){
        log.debug("> проверяем,  что продукт доступен на странице");
        assertTrue(
                kraken.detect().isProductAvailable(),
                failMessage(errorMessage)
        );
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что продукт доступен на странице поиска")
    public void checkIsProductAvailableOnSearchPage(String errorMessage){
        log.debug("> проверяем,  что продукт доступен на странице");
        assertTrue(
                kraken.detect().isProductAvailableOnSearch(),
                failMessage(errorMessage)
        );
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что юзер перешел в искомую категорию продуктов")
    public void checkIsTaxonRedirectCorrect(String taxon, String errorMessage){
        log.debug("> проверяем,  что юзер перешел в искомую категорию продуктов");
        assertTrue(
                kraken.detect().isTextElementContainsText(taxon,Elements.CategoryPage.title()),
                failMessage(errorMessage)
        );
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что поисковый саджест отображается")
    public void checkIsSearchSuggestPresent(String errorMessage){
        log.debug("> проверяем, что поисковый саджест отображается");
        assertTrue(kraken.detect().isSearchSuggestsPresent(),
                errorMessage);
        log.debug("✓ Успешно");
    }

    @Step("Проверяем, что поисковый саджест содержит совпадающие товары")
    public void checkIsProductSuggestPresent(String errorText){
        log.debug("> проверяем, что поисковый саджест содержит совпадающие товары");
        assertTrue(kraken.detect().isSearchProductSuggestsPresent(),
                errorText);
        log.debug("✓ Успешно");
    }
}
