package ru.instamart.reforged.hr_ops_partners.block.footer;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface FooterCheck extends Check, FooterElement {

    @Step("Проверяем, что отображается логотип Сбермаркета")
    default void checkLogoVisible() {
        Kraken.waitAction().shouldBeVisible(logo);
    }

    @Step("Проверяем, что отображается заголовок блока соцсетей")
    default void checkSocialsTitleVisible() {
        Kraken.waitAction().shouldBeVisible(socialsTitle);
    }

    @Step("Проверяем, что заголовок блока соцсетей: '{expectedText}'")
    default void checkSocialsTitle(final String expectedText) {
        Assert.assertEquals(socialsTitle.getText(), expectedText, "Заголовок блока соцсетей отличается от ожидаемого");
    }

    @Step("Проверяем, что отображаются иконки соцсетей")
    default void checkSocialIconsVisible() {
        Kraken.waitAction().shouldBeVisible(socialLinks);
    }

    @Step("Проверяем, что количество иконок соцсетей: {expectedCount}")
    default void checkSocialIconsCount(final int expectedCount) {
        Assert.assertEquals(socialLinks.elementCount(), expectedCount, "Количество отображаемых иконок соцсетей отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается блок информации 'По вопросам сотрудничества'")
    default void checkLeftInfoBlockVisible() {
        Kraken.waitAction().shouldBeVisible(textBlockLeft);
    }

    @Step("Проверяем, что в блоке 'Во вопросам сотрудничества' отображается текст: '{expectedText}'")
    default void checkLeftInfoBlockText(final String expectedText) {
        Assert.assertEquals(textBlockLeft.getText(), expectedText, "Текст в блоке 'Во вопросам сотрудничества' отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается блок информации 'Адрес'")
    default void checkRightInfoBlockVisible() {
        Kraken.waitAction().shouldBeVisible(textBlockRight);
    }

    @Step("Проверяем, что в блоке 'Адрес' отображается текст: '{expectedText}'")
    default void checkRightInfoBlockText(final String expectedText) {
        Assert.assertEquals(textBlockRight.getText(), expectedText, "Текст в блоке 'Адрес' отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается блок информации для партнеров")
    default void checkBottomInfoBlockVisible() {
        Kraken.waitAction().shouldBeVisible(partnersInfoBlock);
    }

    @Step("Проверяем, что в блоке информации для партнеров отображается текст: '{expectedText}'")
    default void checkBottomInfoBlockText(final String expectedText) {
        Assert.assertEquals(partnersInfoBlock.getText(), expectedText, "Текст в блоке информации для партнеров отличается от ожидаемого");
    }
}
