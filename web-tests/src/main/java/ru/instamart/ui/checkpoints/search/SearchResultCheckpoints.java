package ru.instamart.ui.checkpoints.search;

import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import ru.instamart.ui.checkpoints.BaseUICheckpoints;
import ru.instamart.ui.checkpoints.shipping.ShippingAddressCheckpoints;
import ru.instamart.ui.objectsmap.Elements;

import static ru.instamart.ui.modules.Base.kraken;

public class SearchResultCheckpoints extends BaseUICheckpoints {
    private static final Logger log = LoggerFactory.getLogger(ShippingAddressCheckpoints.class);

    private final SoftAssert softAssert = new SoftAssert();

    @Step("Проверяем, что поисковый запрос не возвращает результатов")
    public void checkIsSearchResultEmpty(String errorMessage){
        log.info("> проверяем, что поисковый запрос не возвращает результатов");
        Assert.assertTrue(kraken.detect().isSearchResultsEmpty(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что поисковый запрос возвращает результы")
    public void checkIsSearchResultNotEmpty(String errorMessage){
        log.info("> проверяем, что поисковый запрос возвращает результы");
        Assert.assertFalse(
                kraken.detect().isSearchResultsEmpty(),
                failMessage(errorMessage)
        );
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что продукт доступен на странице")
    public void checkIsProductAvailable(String errorMessage){
        log.info("> проверяем,  что продукт доступен на странице");
        Assert.assertTrue(
                kraken.detect().isProductAvailable(),
                failMessage(errorMessage)
        );
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что юзер перешел в искомую категорию продуктов")
    public void checkIsTaxonRedirectCorrect(String taxon, String errorMessage){
        log.info("> проверяем,  что юзер перешел в искомую категорию продуктов");
        Assert.assertTrue(
                kraken.detect().isTextElementContainsText(taxon,Elements.Header.Search.taxonResult()),
                failMessage(errorMessage)
        );
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что поисковый саджест отображается")
    public void checkIsSearchSuggestPresent(String errorMessage){
        log.info("> проверяем, что поисковый саджест отображается");
        Assert.assertTrue(kraken.detect().isSearchSuggestsPresent(),
                errorMessage);
        log.info("✓ Успешно");
    }

    @Step("Проверяем, что поисковый саджест содержит совпадающие товары")
    public void checkIsProductSuggestPresent(String errorText){
        log.info("> проверяем, что поисковый саджест содержит совпадающие товары");
        Assert.assertTrue(kraken.detect().isSearchProductSuggestsPresent(),
                errorText);
        log.info("✓ Успешно");
    }



}
