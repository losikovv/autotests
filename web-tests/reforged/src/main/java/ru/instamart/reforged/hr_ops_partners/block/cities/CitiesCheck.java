package ru.instamart.reforged.hr_ops_partners.block.cities;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface CitiesCheck extends Check, CitiesElement {

    @Step("Проверяем, что заголовок блока: '{expectedText}'")
    default void checkTitle(final String expectedText) {
        Assert.assertEquals(title.getText(), expectedText, "Указанный в заголовке блока текст отличается от ожидаемого");
    }

    @Step("Проверяем, что подзаголовок блока: '{expectedText}'")
    default void checkSubtitle(final String expectedText) {
        Assert.assertEquals(subtitle.getText(), expectedText, "Указанный в подзаголовке блока текст отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка 'Показать все'")
    default void checkShowAllButtonVisible() {
        Kraken.waitAction().shouldBeVisible(showAllButton);
    }

    @Step("Проверяем, что отображается кнопка 'Скрыть'")
    default void checkHideButtonVisible() {
        Kraken.waitAction().shouldBeVisible(hideButton);
    }

    @Step("Проверяем, что отображается основной список городов")
    default void checkMainCitiesListVisible() {
        Kraken.waitAction().shouldBeVisible(visibleCities);
    }

    @Step("Проверяем, что отображается скрытый под спойлером список городов")
    default void checkHiddenCitiesListVisible() {
        Kraken.waitAction().shouldBeVisible(hiddenCities);
    }

    @Step("Проверяем, что не отображается скрытый под спойлером список городов")
    default void checkHiddenCitiesListNotVisible() {
        hiddenCities.should().invisible();
    }

    @Step("Проверяем, что количество городов в основном списке: {expectedCount}")
    default void checkMainCitiesListCount(final int expectedCount) {
        Assert.assertEquals(visibleCities.elementCount(), expectedCount, "Количество городов, отображаемых по умолчанию отличается от ожидаемого");
    }

    @Step("Проверяем, что общее количество городов: {expectedCount}")
    default void checkAllCitiesListCount(final int expectedCount) {
        Assert.assertEquals(allCities.elementCount(), expectedCount, "Общее количество городов отличается от ожидаемого");
    }

    @Step("Проверяем, что кнопка 'Показать все' не отображается")
    default void checkShowAllButtonNotVisible() {
        showAllButton.should().invisible();
    }

    @Step("Проверяем, что кнопка 'Скрыть' не отображается")
    default void checkHideButtonNotVisible() {
        hideButton.should().invisible();
    }
}
