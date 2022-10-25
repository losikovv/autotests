package ru.instamart.reforged.hr_ops_partners.frame.regions_modal;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface RegionsModalCheck extends RegionsModalElement, Check {

    @Step("Проверяем, что окно выбора региона отображается")
    default void checkRegionsModalVisible() {
        Kraken.waitAction().shouldBeVisible(modalTitle);
        Kraken.waitAction().shouldNotBeAnimated(modalTitle);
    }

    @Step("Проверяем, что окно выбора региона не отображается")
    default void checkRegionsModalNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(modalTitle);
    }

    @Step("Проверяем, что список найденных регионов не отображается")
    default void checkRegionsListNotVisible() {
        Kraken.waitAction().shouldNotBeVisible(regionsList);
    }

    @Step("Проверяем, что отображается плейсхолдер пустого списка регионов")
    default void checkEmptyRegionsListVisible() {
        Kraken.waitAction().shouldBeVisible(emptyListPlaceholder);
    }

    @Step("Проверяем, что в списке отображается '{expectedCount}' регион(а)")
    default void checkRegionsCountInList(final int expectedCount) {
        Assert.assertEquals(regionsList.elementCount(), expectedCount, "Текущее количество регионов в списке отличается от ожидаемого");
    }

    @Step("Проверяем, что названия регионов в списке содержат текст: '{expectedText}'")
    default void checkFoundedRegionNameContainsText(final String expectedText) {
        regionsList.getTextFromAllElements().forEach(name -> krakenAssert.assertTrue(
                name.contains(expectedText), "Название региона в списке: '" + name + "' не содержит ожидаемый текст: '" + expectedText + "'"));
        krakenAssert.assertAll();
    }
}
