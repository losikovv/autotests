package ru.instamart.reforged.stf.page.search;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface SearchCheck extends Check, SearchElement {

    @Step("Проверяем, что отображается кнопка добавления в корзину у первого товара в поиске")
    default void checkAddToCartButtonVisible() {
        waitAction().shouldBeVisible(firstAddToCartButton);
    }

    @Step("Проверяем, что сетка найденных товаров не отображается")
    default void checkSearchProductGridNotVisible() {
        waitAction().shouldNotBeVisible(searchProductGrid);
    }

    @Step("Проверяем, что сетка найденных товаров не отображается")
    default void checkTaxonTitle(String title) {
        Kraken.getWebDriver().findElement(By.xpath("//h1[@data-qa='category_header_title' and text()='" + title + "']"));
    }

    @Step("Проверяем, что сетка найденных товаров отображается")
    default void checkSearchProductGridVisible() {
        waitAction().shouldBeVisible(searchProductGrid);
    }

    @Step("Проверяем, что отображается заголовок пустых результатов поиска")
    default void checkEmptySearchPlaceholderVisible() {
        waitAction().shouldBeVisible(emptySearchPlaceHolder);
    }
}
