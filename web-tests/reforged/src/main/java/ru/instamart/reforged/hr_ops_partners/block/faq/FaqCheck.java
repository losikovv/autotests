package ru.instamart.reforged.hr_ops_partners.block.faq;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface FaqCheck extends Check, FaqElement {

    @Step("Проверяем, что заголовок блока: '{expectedText}'")
    default void checkTitle(final String expectedText) {
        Assert.assertEquals(title.getText(), expectedText, "Указанный в заголовке блока текст отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка 'Больше вопросов'")
    default void checkMoreFAQButtonVisible() {
        Kraken.waitAction().shouldBeVisible(moreFaq);
    }

    @Step("Проверяем, что отображается кнопка 'Меньше вопросов'")
    default void checkLessFAQButtonVisible() {
        Kraken.waitAction().shouldBeVisible(lessFaq);
    }

    @Step("Проверяем, что не отображается кнопка 'Больше вопросов'")
    default void checkMoreFAQButtonNotVisible() {
        Assert.assertTrue(moreFaq.is().invisible());
    }

    @Step("Проверяем, что не отображается кнопка 'Меньше вопросов'")
    default void checkLessFAQButtonNotVisible() {
        Assert.assertTrue(lessFaq.is().invisible());
    }

    @Step("Проверяем, что отображается основной список вопросов")
    default void checkMainFAQListVisible() {
        Kraken.waitAction().shouldBeVisible(mainFaqsList);
    }

    @Step("Проверяем, что отображается скрытый под спойлером список вопросов")
    default void checkHiddenFAQListVisible() {
        Kraken.waitAction().shouldBeVisible(hiddenFaqsList);
    }

    @Step("Проверяем, что не отображается скрытый под спойлером список вопросов")
    default void checkHiddenFAQListNotVisible() {
        Assert.assertTrue(hiddenFaqsList.is().invisible());
    }

    @Step("Проверяем, что количество вопросов в основном списке: {expectedCount}")
    default void checkMainFAQListCount(final int expectedCount) {
        Assert.assertEquals(mainFaqsList.elementCount(), expectedCount, "Количество вопросов, отображаемых по умолчанию отличается от ожидаемого");
    }

    @Step("Проверяем, что общее количество вопросов: {expectedCount}")
    default void checkAllFAQListCount(final int expectedCount) {
        Assert.assertEquals(allFaqsList.elementCount(), expectedCount, "Общее количество вопросов отличается от ожидаемого");
    }

    @Step("Проверяем, что ответы на вопросы скрыты")
    default void checkExpandeAnswersNotVisible() {
        Assert.assertTrue(expandedFaqsList.is().invisible());
    }

    @Step("Проверяем, что количество развёрнутых ответов: '{expectedExpandedAnswersCount}'")
    default void checkExpandedAnswersSize(final int expectedExpandedAnswersCount) {
        Assert.assertEquals(expandedFaqsList.elementCount(), expectedExpandedAnswersCount, "Количество развернутых ответов отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается ответ на вопрос '{faqTitle}'")
    default void checkAnswerVisible(final String faqTitle) {
        Kraken.waitAction().shouldBeVisible(faqAnswerByTitle, faqTitle.substring(faqTitle.length() - 15));
    }

    @Step("Проверяем, что не отображается ответ на вопрос '{faqTitle}'")
    default void checkAnswerNotVisible(final String faqTitle) {
        Assert.assertTrue(faqAnswerByTitle.is().invisible(faqTitle.substring(faqTitle.length() - 15)));
    }
}
