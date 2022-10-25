package ru.instamart.reforged.hr_ops_partners.page.vacancy;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.kraken.data.hr_ops_partners.vacancy_cards.AdvantageData;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface VacancyCheck extends Check, VacancyElement {

    @Step("Проверяем, что отображается заголовок блока преимуществ 'Почему быть в команде СберМаркета — круто?'")
    default void checkAdvantagesBlockTitleVisible() {
        Kraken.waitAction().shouldBeVisible(advantagesBlockTitle);
    }

    @Step("Проверяем, что указанные преимущества вакансии соответствует ожидаемым: '{expectedAdvantageList}'")
    default void checkVacancyAdvantages(final Set<AdvantageData> expectedAdvantageList) {
        krakenAssert.assertEquals(
                advantageTitles.getTextFromAllElements(),
                expectedAdvantageList.stream().map(AdvantageData::getTitle).collect(Collectors.toSet()),
                "Список заголовков указанных преимуществ вакансии отличается от ожидаемого");
        krakenAssert.assertEquals(
                advantageDescriptions.getTextFromAllElements(),
                expectedAdvantageList.stream().map(AdvantageData::getDescription).collect(Collectors.toSet()),
                "Описания указанных преимуществ отличаются от ожидаемых");
        krakenAssert.assertAll();
    }

    @Step("Проверяем, что отображается заголовок блока отклика 'Чтобы начать зарабатывать'")
    default void checkApplyFormBlockTitleVisible() {
        Kraken.waitAction().shouldBeVisible(applyFormBlockTitle);
    }

    @Step("Проверяем список шагов 'Чтобы начать зарабатывать'")
    default void checkApplyBlockSteps() {
        Assert.assertEquals(applyFormSteps.getTextFromAllElements(),
                new HashSet<>() {{
                    add("1\nВведите фамилию, имя и номер телефона");
                    add("2\nПройдите быстрое интервью");
                    add("3\nПриступайте к работе");
                }});

    }

    @Step("Проверяем, что отображается заголовок блока отклика 'Ещё вы можете работать'")
    default void checkAnotherVacanciesBlocTitleVisible() {
        Kraken.waitAction().shouldBeVisible(anotherVacanciesBlockTitle);
    }

    @Step("Проверяем, что количество карточек других вакансий равно: '{expectedCount}'")
    default void checkAnotherVacancyCardsCount(final int expectedCount){
        Assert.assertEquals(anotherVacancyCards.elementCount(), expectedCount, "Количество отображаемых карточек других ваканчий отличается от ожидаемого");
    }
}
