package ru.instamart.reforged.hr_ops_partners.block.main_banner;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface MainBannerCheck extends Check, MainBannerElement {

    @Step("Проверяем, что отображается кнопка '{buttonText}'")
    default void checkActionsButtonVisible(final String buttonText) {
        Kraken.waitAction().shouldBeVisible(vacancyActionsButton, buttonText);
    }

    @Step("Проверяем, что кнопка '{buttonText}' не отображается ")
    default void checkActionsButtonNotVisible(final String buttonText) {
        vacancyActionsButton.should().invisible(buttonText);
    }

    @Step("Проверяем, что заголовок на баннере соответствует ожидаемому: '{expectedText}'")
    default void checkBannerTitle(final String expectedText) {
        Assert.assertEquals(mainBannerTitle.getText(), expectedText,
                "Заголовок баннера отличается от ожидаемого");
    }

    @Step("Проверяем, что подзаголовок на баннере соответствует ожидаемому: '{expectedText}'")
    default void checkBannerSubtitle(final String expectedText) {
        Assert.assertEquals(mainBannerSubtitle.getText(), expectedText,
                "Подзаголовок баннера отличается от ожидаемого");
    }

    @Step("Проверяем, что информация о доходе на баннере соответствует ожидаемой: '{expectedText}'")
    default void checkBannerSalary(final String expectedText) {
        Assert.assertEquals(mainBannerSalary.getText(), expectedText,
                "Информация о доходе на баннере отличается от ожидаемой");
    }

    @Step("Проверяем, что краткая сводная информация на баннере соответствует ожидаемой: '{expectedText}'")
    default void checkBannerShortDescription(final String expectedText) {
        Assert.assertEquals(mainBannerShortDescription.getText(), expectedText,
                "Краткая сводная информация на баннере отличается от ожидаемой");
    }

    @Step("Проверяем, что полная сводная информации о вакансии на баннере соответствует ожидаемой: '{expectedTitle}'")
    default void checkFullDescriptionText(final String expectedTitle) {
        Assert.assertEquals(mainBannerFullDescription.getText(), expectedTitle,
                "Полная сводная информация о вакансии на баннере отличается от ожидаемой");
    }

    @Step("Проверяем, что отображается заголовок блока информации о вакансии 'Что нужно делать'")
    default void checkFullDescriptionTitleVisible() {
        Kraken.waitAction().shouldBeVisible(fullDescriptionTitle);
    }

    @Step("Проверяем, что отображается картинка на баннере")
    default void checkBannerImageVisible() {
        Kraken.waitAction().shouldBeVisible(mainBannerImage);
    }
}
