package ru.instamart.reforged.hr_ops_partners.page.home;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

public interface HomeCheck extends Check, HomeElement {

    @Step("Проверяем, что страница загрузилась")
    default void checkPageLoaded() {
        Assert.assertTrue(pageLoaderRoot.is().invisible());
    }

    @Step("Проверяем количество вакансий в списке")
    default void checkVacanciesCount(final int expectedCount) {
        Assert.assertEquals(vacancyCards.elementCount(), expectedCount, "Количество карточек вакансий на странице отличается от ожидаемого");
    }

}
