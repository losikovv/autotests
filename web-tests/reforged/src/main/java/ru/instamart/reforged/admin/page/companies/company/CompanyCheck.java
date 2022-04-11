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

    @Step("Проверяем, что отобразился список менеджеров")
    default void checkCompanyManagersDisplayed() {
        waitAction().shouldBeVisible(companyManagers);
    }

    @Step("Проверяем, что в списке менеджеров '{expectedManagersCount}' сотрудников")
    default void checkCompanyManagersListSize(final int expectedManagersCount) {
        Assert.assertEquals(companyManagers.elementCount(), expectedManagersCount, "Количество менеджеров организации отличается от ожидаемого");
    }

    @Step("Проверяем, что у компании отсутствуют менеджеры")
    default void checkCompanyManagersNotDisplayed() {
        waitAction().shouldNotBeVisible(companyManagers);
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

    @Step("Проверяем, что в списке договоров пусто")
    default void checkContractsListIsEmpty() {
        waitAction().shouldNotBeVisible(contracts);
    }

    @Step("Проверяем, что в списке договоров '{expectedContractsCount}' записей")
    default void checkContractsCount(final int expectedContractsCount) {
        Assert.assertEquals(contracts.elementCount(), expectedContractsCount, "Количество договоров в списке отличается от ожидаемого");
    }

    @Step("Проверяем, что отображается поле ввода номера договора")
    default void checkContractNumberInputVisible() {
        waitAction().shouldBeVisible(contractNumberInput);
    }

    @Step("Проверяем, что номер договора: '{expectedNumber}'")
    default void checkContractNumber(final String expectedNumber) {
        Assert.assertEquals(contractNumber.getText(), "№" + expectedNumber, "Номер договора не соответствует ожидаемому");
    }

    @Step("Проверяем, что дата договора не заполнена")
    default void checkContractDateIsEmpty() {
        waitAction().shouldNotBeVisible(contractDate);
    }

    @Step("Проверяем, что дата договора: '{expectedDate}'")
    default void checkContractDate(final String expectedDate) {
        Assert.assertEquals(contractDate.getText(), expectedDate, "Дата договора не соответствует ожидаемой");
    }

    @Step("Проверяем, что договор действующий")
    default void checkContractNotInArchive() {
        waitAction().shouldBeVisible(archiveLabel);
    }

    @Step("Проверяем, что договор переведен в архив")
    default void checkContractInArchive() {
        waitAction().shouldBeVisible(archiveLabel);
    }

    @Step("Проверяем, что отображается календарь")
    default void checkCalendarVisible() {
        waitAction().shouldBeVisible(calendarToday);
    }
}
