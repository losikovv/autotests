package ru.instamart.reforged.admin.page.retailers.regions;

import io.qameta.allure.Step;
import ru.instamart.reforged.core.Check;

import static org.testng.Assert.assertEquals;
import static ru.instamart.reforged.core.Kraken.waitAction;

public interface RegionsCheck extends Check, RegionsElements {

    @Step("Проверяем, что отображается алерт успешного создания региона")
    default void checkSuccessCreateRegionAlertVisible() {
        waitAction().shouldBeVisible(successCreateRegionAlert);
    }

    @Step("Проверяем, что тестовый регион: {0} добавился в таблицу")
    default void checkRegionInTableVisible(final String cityName) {
        waitAction().shouldBeVisible(city, cityName);
    }

    @Step("Проверяем, что тестовый регион: {0} добавился в таблицу")
    default void checkRegionInTableNotVisible(final String cityName) {
        waitAction().shouldNotBeVisible(city, cityName);
    }

    @Step("Проверяем, что количество кнопок настройки регионов совпадает с кол-вом регионов")
    default void checkRegionSettingsButtonsEqualsRegions() {
        assertEquals(regionSettingsButtons.elementCount(), regionsNameColumn.elementCount(),
                "Количество кнопок настроек регионов не совпадает с количеством регионов");

    }

    @Step("Проверяем, что количество кнопок настройки диспетчеризации совпадает с кол-вом регионов")
    default void checkDispatchSettingsButtonsEqualsRegions() {
        assertEquals(dispatchSettingsButtons.elementCount(), regionsNameColumn.elementCount(),
                "Количество кнопок настройки диспетчеризации не совпадает с количеством регионов");
    }

    @Step("Проверяем что количество номеров в таблице совпадает с кол-вом регионов")
    default void checkQuantityRowsNumbersEqualsRegions() {
        assertEquals(tableRowsNumbers.elementCount(), regionsNameColumn.elementCount(),
                "Количество номеров строк не совпадает с количеством регионов");
    }

    @Step("Проверяем, что заголовок 'Список регионов' отображается")
    default void checkPageTitleVisible() {
        waitAction().shouldBeVisible(pageTitle);
    }

    @Step("Проверяем, что инпут поиска регионов отображается")
    default void checkRegionSearchInputVisible() {
        waitAction().shouldBeVisible(regionSearch);
    }

    @Step("Проверяем, что кнопка добавления нового региона отображается")
    default void checkAddNewRegionButtonVisible() {
        waitAction().shouldBeVisible(addNewRegionButton);
    }

    @Step("Проверяем, что таблица регионов отображается")
    default void checkRegionsTableVisible() {
        waitAction().shouldBeVisible(regionsTable);
    }
}
