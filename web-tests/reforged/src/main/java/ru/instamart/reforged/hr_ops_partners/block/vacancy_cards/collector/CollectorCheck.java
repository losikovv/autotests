package ru.instamart.reforged.hr_ops_partners.block.vacancy_cards.collector;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.data.hr_ops_partners.vacancy_cards.AdvantageData;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.Set;
import java.util.stream.Collectors;

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

    @Step("Проверяем, что информация о доходе вакансии 'Cборщик заказов' соответствует ожидаемой: '{expectedText}'")
    default void checkVacancyCardSalary(final String expectedText) {
        Assert.assertEquals(vacancyCartSalary.getText(), expectedText, "Указанный в информации о доходе текст отличается от ожидаемого");
    }

    @Step("Проверяем, что указанные преимущества вакансии 'Cборщик заказов' соответствует ожидаемым: '{expectedAdvantageList}'")
    default void checkVacancyCardAdvantages(final Set<AdvantageData> expectedAdvantageList) {
        krakenAssert.assertEquals(
                advantageTitles.getTextFromAllElements(),
                expectedAdvantageList.stream().map(AdvantageData::getTitle).collect(Collectors.toSet()),
                "Список указанных преимуществ вакансии отличается от ожидаемого");
        krakenAssert.assertEquals(
                advantageDescriptions.getTextFromAllElements(),
                expectedAdvantageList.stream().map(AdvantageData::getDescription).collect(Collectors.toSet()),
                "Описания указанных преимуществ отличаются от ожидаемых");
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что отображается кнопка 'Откликнуться' вакансии 'Cборщик заказов'")
    default void checkRespondButtonVisible() {
        Kraken.waitAction().shouldBeVisible(respond);
    }

    @Step("Проверяем, что отображается кнопка 'Подробнее' вакансии 'Cборщик заказов'")
    default void checkMoreInfoButtonVisible() {
        Kraken.waitAction().shouldBeVisible(moreInfo);
    }

    @Step("Проверяем, что кнопка 'Откликнуться' вакансии 'Cборщик заказов' заблокирована")
    default void checkRespondButtonDisabled() {
        Kraken.waitAction().shouldBeVisible(disabledRespond);
    }
}
