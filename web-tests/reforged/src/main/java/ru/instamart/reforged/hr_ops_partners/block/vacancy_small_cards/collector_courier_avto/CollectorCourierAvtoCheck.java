package ru.instamart.reforged.hr_ops_partners.block.vacancy_small_cards.collector_courier_avto;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface CollectorCourierAvtoCheck extends Check, CollectorCourierAvtoElement {

    @Step("Проверяем, что отображается картинка вакансии 'Сборщик-курьер на авто'")
    default void checkVacancyPictureVisible() {
        Kraken.waitAction().shouldBeVisible(vacancyCartImage);
    }

    @Step("Проверяем, что заголовок вакансии 'Сборщик-курьер на авто' соответствует ожидаемому: '{expectedText}'")
    default void checkVacancyCardTitle(final String expectedText) {
        Assert.assertEquals(vacancyCartTitle.getText(), expectedText, "Указанный в заголовке текст отличается от ожидаемого");
    }

    @Step("Проверяем, что описание вакансии 'Сборщик-курьер на авто' соответствует ожидаемой: '{expectedText}'")
    default void checkVacancyCardDescription(final String expectedText) {
        Assert.assertEquals(vacancyCartDescription.getText(), expectedText, "Указанный в описании текст отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается кнопка 'Подробнее' вакансии 'Сборщик-курьер на авто'")
    default void checkMoreInfoButtonVisible() {
        Kraken.waitAction().shouldBeVisible(moreInfo);
    }
}
