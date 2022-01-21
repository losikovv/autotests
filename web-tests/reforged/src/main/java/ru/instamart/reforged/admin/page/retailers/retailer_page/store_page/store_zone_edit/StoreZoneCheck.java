package ru.instamart.reforged.admin.page.retailers.retailer_page.store_page.store_zone_edit;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.helper.KrakenAssert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface StoreZoneCheck extends Check, StoreZoneElements {

    @Step("Проверяем, что страница зон отображается")
    default void checkZonePageVisible() {
        waitAction().shouldBeVisible(zonePage);
    }

    @Step("Проверяем, что заголовок страницы содержит название магазина {0}")
    default void checkZonePageTitleCorrect(final String title) {
        waitAction().shouldBeVisible(zonePageTitle, title);
    }

    @Step("Сравнение количества кнопок удаления зон с количеством зон")
    default void zoneDeleteButtonsCompare(final int quantity) {
        krakenAssert.assertEquals(deleteZoneInTableButton.elementCount(), quantity);
    }

    @Step("Проверяем, что таблица на странице зон отображается")
    default void checkZonesTableVisible() {
        waitAction().shouldBeVisible(zonesTable);
    }

    @Step("Проверяем, что подсказка на странице зон отображается")
    default void checkHintVisible() {
        waitAction().shouldBeVisible(hint);
    }

    @Step("Проверяем, что кнопка назад на странице зон отображается")
    default void checkBackButtonVisible() {
        waitAction().shouldBeVisible(backButton);
    }

    @Step("Проверяем, что кнопка загрузки на странице зон отображается")
    default void checkDownloadButtonVisible() {
        waitAction().shouldBeVisible(downloadButton);
    }
}
