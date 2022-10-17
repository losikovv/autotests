package ru.instamart.reforged.admin.page.user_companies;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;
import ru.instamart.reforged.core.Kraken;

public interface UserCompaniesCheck extends Check, UserCompaniesElement {

    @Step("Проверяем, что список компаний пуст")
    default void checkCompaniesListIsEmpty() {
        Kraken.waitAction().shouldBeVisible(listIsEmpty);
    }

    @Step("Проверяем, что список компаний не пуст")
    default void checkCompaniesListNotEmpty() {
        Kraken.waitAction().shouldNotBeVisible(listIsEmpty);
    }

    @Step("Проверяем, что количество компаний в таблице равно: '{expectedCount}'")
    default void checkCompaniesCount(final int expectedCount) {
        Assert.assertEquals(table.getRowsCount(), expectedCount, "Количество компаний в таблице отличается от ожидаемого");
    }

    @Step("Проверяем, что название компании в {rowNumber}-й строке: '{companyName}' ")
    default void checkCompanyNameInRow(final int rowNumber, final String companyName) {
        Assert.assertEquals(table.getCompanyName(rowNumber - 1), companyName, "Название компании с указанной строке отличается от ожидаемой");
    }


}

