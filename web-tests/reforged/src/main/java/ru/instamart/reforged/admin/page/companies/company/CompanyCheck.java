package ru.instamart.reforged.admin.page.companies.company;

import io.qameta.allure.Step;
import org.testng.Assert;
import ru.instamart.reforged.core.Check;

import static ru.instamart.reforged.core.Kraken.waitAction;

public interface CompanyCheck extends Check, CompanyElement {

    @Step("Проверяем, что открылось окно компании")
    default void checkCompanyPageVisible() {
        waitAction().shouldBeVisible(buttonEditCompanyName);
    }

    @Step("Проверяем, что отображается кнопка подтверждения статуса")
    default void checkConfirmStatusButtonDisplayed() {
        waitAction().shouldBeVisible(confirmStatus);
    }

    @Step("Проверяем, что отображается всплывающее уведомление")
    default void checkNoticePopupDisplayed() {
        waitAction().shouldBeVisible(noticePopup);
    }

    @Step("Проверяем, что не отображается всплывающее уведомление")
    default void checkNoticePopupNotDisplayed() {
        waitAction().shouldNotBeVisible(noticePopup);
    }

    @Step("Проверяем, что отображается всплывающее окно подтверждения действия")
    default void checkConfirmActionModalDisplayed() {
        waitAction().shouldBeVisible(confirmActionModal);
    }

    @Step("Проверяем, что значение в колонке '{actualText}' соответствует ожидаемому: '{expectedText}'")
    default void checkColumnValue(final String actualText, final String expectedText) {
        Assert.assertEquals(actualText, expectedText, "Значение в колонке не соответствует ожидаемому");
    }

    @Step("Проверяем, что текст в окне подтверждения действия соответствует ожидаемому: '{expectedText}'")
    default void checkConfirmActionModalTextEquals(final String expectedText) {
        Assert.assertEquals(confirmActionModalText.getText(), expectedText, "Текст во всплывающем окне подтверждения действия отличается от ожидаемого");
    }

    @Step("Проверяем, что текст всплывающего уведомления соответствует ожидаемому: '{expectedText}'")
    default void checkNoticeTextEquals(final String expectedText) {
        Assert.assertEquals(noticePopup.getText(), expectedText, "Текст всплывающего уведомления отличается от ожидаемого");
    }

    @Step("Проверяем, что отобразился список представителей")
    default void checkCompanyEmployeesDisplayed() {
        waitAction().shouldBeVisible(companyEmployees);
    }

    @Step("Проверяем, что в списке представителей '{expectedEmployeesCount}' сотрудников")
    default void checkCompanyEmployeesListSize(final int expectedEmployeesCount) {
        Assert.assertEquals(companyEmployees.elementCount(), expectedEmployeesCount, "Количество представителей организации отличается от ожидаемого");
    }

    @Step("Проверяем, что у компании отсутствуют представители")
    default void checkCompanyEmployeesNotDisplayed() {
        waitAction().shouldNotBeVisible(companyEmployees);
    }

    @Step("Проверяем, что отобразился список найденных пользователей")
    default void checkFoundedUsersDisplayed() {
        waitAction().shouldBeVisible(foundedUsers);
    }
}
