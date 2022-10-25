package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface CollectorCheck extends Check, CollectorElement {

    @Step("Проверяем, что отображается картинка вакансии 'Cборщик заказов'")
    default void checkVacancyPictureVisible() {
        Kraken.waitAction().shouldBeVisible(vacancyCartImage);
    }

    @Step("Проверяем, что заголовок вакансии 'Cборщик заказов' соответствует ожидаемому: '{expectedText}'")
    default void checkVacancyCardTitle(final String expectedText) {
        Assert.assertEquals(vacancyCartTitle.getText(), expectedText, "Указанный в заголовке текст отличается от ожидаемого");
    }

    @Step("Проверяем, что описание вакансии 'Cборщик заказов' соответствует ожидаемой: '{expectedText}'")
    default void checkVacancyCardDescription(final String expectedText) {
        Assert.assertEquals(vacancyCartDescription.getText(), expectedText, "Указанный в описании текст отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка 'Подробнее' вакансии 'Cборщик заказов'")
    default void checkMoreInfoButtonVisible() {
        Kraken.waitAction().shouldBeVisible(moreInfo);
    }
}
