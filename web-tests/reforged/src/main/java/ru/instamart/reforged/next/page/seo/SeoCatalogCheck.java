package ru.instamart.reforged.next.page.seo;

import io.qameta.allure.Step;
import ru.instamart.kraken.util.StringUtil;
import ru.instamart.reforged.core.Check;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.jsAction;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SeoCatalogCheck extends Check, SeoCatalogElement {

    @Step("Отображается сетка товаров")
    default void checkProductGridVisible() {
        waitAction().shouldBeVisible(productGrid);
    }

    @Step("Отображается товар")
    default void checkProductVisible() {
        waitAction().shouldBeVisible(firstProductCard);
    }

    @Step("Отображается загаловок страницы каталога")
    default void checkCatalogTitleVisible() {
        waitAction().shouldBeVisible(catalogPageTitle);
    }

    @Step("Проверяем, не отображается спиннер")
    default void checkSpinnerIsNotVisible() {
        waitAction().shouldNotBeVisible(spinner);
    }

    @Step("Проверяем, что заглушка загрузки видна")
    default void checkProductsStubVisible() {
        waitAction().shouldBeVisible(productsStub);
    }

    @Step("Проверяем, что заглушка загрузки не видна")
    default void checkProductsStubNotVisible() {
        waitAction().shouldNotBeVisible(productsStub);
    }

    @Step("Проверяем, что сортировка 'Сначала дешевые' работает корректно")
    default void checkPriceAscSortCorrect() {
        jsAction().waitForDocumentReady();
        var tmp = new ArrayList<>();
        seoProductPrices.getElements().forEach(element -> {
            tmp.add(StringUtil.stringToDouble(element.getText()));
        });
        assertEquals(tmp, tmp.stream().sorted().collect(Collectors.toList()), "Сортировка 'Сначала дешевые' работает неправильно");
    }

    @Step("Проверить, что все картинки загружены")
    default void checkSearchImgLoaded() {
        seoProductsImagesCollection.getElements().forEach(element -> {
            productImg.waitImgLoad(element.getAttribute("src"));
        });
    }

    @Step("Проверить, что сортировка '{0}' применена")
    default void checkSortEnabled(String sortText) {
        waitAction().shouldBeVisible(selectSortApplied, sortText);
    }
}
